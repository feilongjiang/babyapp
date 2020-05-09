package com.example.apps.baseProject.base;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apps.baseProject.R;
import com.example.apps.baseProject.baseLib.utils.CommonTool;
import com.example.apps.baseProject.baseLib.utils.IJson;
import com.example.apps.baseProject.baseLib.utils.ILog;
import com.example.apps.baseProject.irecyclerview.IAdapter;
import com.example.apps.baseProject.irecyclerview.IRecyclerView;
import com.example.apps.baseProject.irecyclerview.PullToRefreshListener;
import com.example.apps.baseProject.net.IResponseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2015/12/11 0011.    列表Fragment的父类
 */
public abstract class BaseListFragment<T> extends BaseFragment {

    public IRecyclerView mIRecyclerView;
    public View noData;
    public IAdapter<T> adapter;
    public List<T> data;
    public int pageNo = 1;
    public boolean showNoData = true;

    @Override
    public final void doInit() {

        init();
    }

    public final void refresh() {

        doResume();
    }

    public void toolBarClick(){

    }

    public void toolBarDoubleClick(){
        scrollToPosition(0);
    }

    @Override
    public final void doResume() {

        //  刷新数据
        if (mIRecyclerView != null) {
            mIRecyclerView.onRefresh();
        }
    }

    public abstract Class getType();

    @Override
    public int getLayoutId() {
        return R.layout.fastandroiddev_fragment_list_common;
    }

    @Override
    public final void onSuccess(String object, String taskId) {
        if (handler == null) return;
        if (data == null) {
            handler.post(hasNoData);
            return;
        }
        if (pageNo == 1){
            data.clear();
        }

        Object result = IJson.fromJson(object, getType());
        beforeSuccess(result, taskId);
        if (result instanceof IResponseData){

            List<T> objectsList = parseData(result);
            objectsList = objectsList == null ? ((IResponseData<T>)result).getData() : objectsList;
            if (objectsList != null && !objectsList.isEmpty()){
                data.addAll(objectsList);
                handler.post(newData);
            }else{
                handler.post(hasNoData);
            }
        }
        afterSuccess(object, taskId);
    }

    public void beforeSuccess(Object object, String taskId){}

    public void afterSuccess(String object, String taskId){

    }

    public List<T> parseData(Object object){
        return null;
    }

    public T getById(int position){

        if (position < 0 || position >= data.size()){
            return null;
        }
        return data.get(position);
    }

    @Override
    public void onFailed(int code, String msg, String taskId) {

        if (handler == null) return;
        handler.post(hasNoData);
    }

    public int getRecyclerViewId(){return R.id.list;}
    public int getNoDataId(){return R.id.noData;}

    public final void init() {

        mIRecyclerView = (IRecyclerView) mView.findViewById(getRecyclerViewId());
        noData = mView.findViewById(getNoDataId());

        if (noData != null && clickNoDataToRefresh()){
            noData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIRecyclerView.onRefresh();
                }
            });
        }

        handler = new Handler();
        pageNo = 1;
        mIRecyclerView.setHasMore(true);
        mIRecyclerView.setPullToRefreshListener(refreshListener);
        data = new ArrayList<>();
        adapter = buildAdapter(mContext, data);
        if(adapter == null){
            ILog.e("===BaseListActivity===", "adapter can not be null ...");
            return;
        }
        mIRecyclerView.setAdapter(adapter);
    }

    private PullToRefreshListener refreshListener = new PullToRefreshListener() {
        @Override
        public void onRefresh() {

            if (noData == null) {  //  发生异常
                handler.post(hasNoData);
                return;
            }
            noData.setVisibility(View.GONE);  //  隐藏没有数据时，显示的view

            pageNo = 1;
            mIRecyclerView.setHasMore(true);
            if (!getMoreData() && handler != null) {
                handler.post(hasNoData);
            }
        }

        @Override
        public void onLoadMore() {

            pageNo++;
            getMoreData();
        }
    };

    //  获取更多数据
    public abstract boolean getMoreData();

    public boolean clickNoDataToRefresh(){
        return true;
    }

    public abstract IAdapter<T> buildAdapter(Context mContext, List<T> data);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (data != null) {
            data = null;
        }if (mIRecyclerView != null){
            mIRecyclerView = null;
        }if (noData != null){
            noData = null;
        }
    }

    public final void setNoDataImg(int resId){

        if (noData == null) return;
        ImageView imgView = (ImageView) noData.findViewById(R.id.noDataImg);
        if (imgView != null)
            imgView.setImageBitmap(CommonTool.drawableToBitmap(getResources().getDrawable(resId)));
    }

    /**
     * 滚动到某个item所在的位置
     * @param position item的位置
     */
    public final void scrollToPosition(int position){

        if (mIRecyclerView == null || mIRecyclerView.getRecyclerView() == null
                || data == null || data.isEmpty()) return;
        mIRecyclerView.getRecyclerView().smoothScrollToPosition(position);
    }

    public final void setNoDataMsg(int resId){

        setNoDataMsg(getResString(resId));
    }

    public final void setNoDataMsg(String msg){

        if (noData == null) return;
        TextView msgView = (TextView) noData.findViewById(R.id.noDataMsg);
        if (msgView != null) msgView.setText(msg);
    }

    public final Runnable newData = new Runnable() {
        @Override
        public void run() {
            if (mIRecyclerView == null) return;
            mIRecyclerView.onLoadEnd();
            mIRecyclerView.onRefreshEnd();
            if ((data == null || !data.isEmpty()) && noData != null) {
                noData.setVisibility(View.GONE);
            }
            if (mContext != null && adapter != null && data != null) {
                mIRecyclerView.notifyDataSetChanged();
            }
        }
    };

    public final Runnable hasNoData = new Runnable() {
        @Override
        public void run() {
            if (mIRecyclerView == null) return;
            mIRecyclerView.onLoadEnd();
            mIRecyclerView.onRefreshEnd();

            mIRecyclerView.setHasMore(false);

            if (adapter != null){
                mIRecyclerView.notifyDataSetChanged();
            }

            if (showNoData && adapter != null && adapter.getItemCount() < 2 && noData != null) {
                noData.setVisibility(View.VISIBLE);
            }
        }
    };
}
