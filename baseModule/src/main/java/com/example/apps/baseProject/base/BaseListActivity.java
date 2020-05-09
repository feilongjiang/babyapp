package com.example.apps.baseProject.base;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by yc on 2015/12/11 0011. 分页功能的activity
 */
public abstract class BaseListActivity<T> extends BaseActivity {

    public IRecyclerView mIRecyclerView;
    public View noData;

    public IAdapter<T> adapter;
    public List<T> data;

    public int pageNo = 1;
    public boolean showNoData = true;

    @Override
    public final void doResume() {

        if (handler == null) {

            init();
        }

        //  刷新数据
        if (mIRecyclerView != null) {
            mIRecyclerView.onRefresh();
        }
    }

    @Override
    protected final void doInit() {
        init();
    }

    @Override
    public void toolBarDoubleClick(){
        scrollToPosition(0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fastandroiddev_activity_list_common;
    }

    public int getRecyclerViewId(){return R.id.list;}
    public int getNoDataId(){return R.id.noData;}

    public final void init() {

        mIRecyclerView = (IRecyclerView) findViewById(getRecyclerViewId());
        noData = findViewById(getNoDataId());

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

    public abstract Class getType();

    public T getById(int position){

        if (position < 0 || position >= data.size()){
            return null;
        }
        return data.get(position);
    }

    @Override
    public void onFailed(int code, String msg, String taskId) {

        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        if (handler == null) return;
        handler.post(hasNoData);
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
        onGetData(result);
        if (result instanceof IResponseData){

            List<T> objectsList = ((IResponseData<T>)result).getData();
            if (objectsList != null && !objectsList.isEmpty()){
                data.addAll(objectsList);
                handler.post(newData);
            }else{
                handler.post(hasNoData);
            }
        }
        afterSuccess(object, taskId);
    }

    public void afterSuccess(String object, String taskId){

    }

    public void onGetData(Object object){};

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
            if (!getMoreData()) {
                handler.post(hasNoData);
            }
        }

        @Override
        public void onLoadMore() {

            pageNo++;
            getMoreData();
        }
    };

    public boolean clickNoDataToRefresh(){
        return true;
    }

    //  获取更多数据
    public abstract boolean getMoreData();

    public abstract IAdapter<T> buildAdapter(Context mContext, List<T> data);

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter = null;
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

    /**
     * 滚动到某个item所在的位置
     * @param position item的位置
     */
    public final void scrollToPosition(int position){

        if (mIRecyclerView == null || mIRecyclerView.getRecyclerView() == null
                || data == null || data.isEmpty()) return;
        mIRecyclerView.getRecyclerView().smoothScrollToPosition(position);
    }

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
