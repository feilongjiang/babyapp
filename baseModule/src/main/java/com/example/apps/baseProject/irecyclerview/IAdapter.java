package com.example.apps.baseProject.irecyclerview;

import android.content.Context;
import android.os.Handler;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apps.baseProject.R;
import com.example.apps.baseProject.baseLib.utils.ILog;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by yangchun on 16/4/15.  通用的RecyclerView adapter
 */
public abstract class IAdapter<T> extends RecyclerView.Adapter<CommonHolder> {

    private List<T> mData;
    private Context mContext;
    private View mHeaderView, mFooterView;
    private int position;
    private RecyclerView recyclerView;
    private Handler handler;
    public static final int TYPE_FOOTER = -10, TYPE_HEADER = -20;

    public IAdapter(List<T> mData, Context mContext) {

        this.mData = mData;
        this.mContext = mContext;
        handler = new Handler();
    }

    public final Context getContext(){
        return mContext;
    }

    public final void removeItem(int position){

        if (mData == null || position < 0 || position >= mData.size()) return;
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public final boolean isFooterVisible() {
        return position >= getItemCount() -2;
    }

    /**
     * 返回最后可见的item的position
     */
    public final int getLastPosition(){

        return position;
    }

    public final void setFooterView(View mFooterView) {

        if (mFooterView == null){
            return;
        }
        this.mFooterView = mFooterView;
    }

    public final void setHeaderView(View mHeaderView) {

        if (mHeaderView == null){
            return;
        }
        this.mHeaderView = mHeaderView;
    }

    public final int getDataSize(){

        return mData == null ? 0 : mData.size();
    }

    @Override
    public final int getItemViewType(int position) {

        if (position == 0 && mHeaderView != null) {
            return TYPE_HEADER;
        }
        if (position + 1 == getItemCount() && mFooterView != null) {
            return TYPE_FOOTER;
        } else {
            return mHeaderView == null ? position : position-1;
        }
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

    @Override
    public final int getItemCount() {
        int size = getDataSize();
        if (mFooterView != null) size++;
        if (mHeaderView != null) size++;
        return size;
    }

    @Override
    public final CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case TYPE_FOOTER:
                return new CommonHolder(mFooterView, mContext, false);

            case TYPE_HEADER:
                return new CommonHolder(mHeaderView, mContext, false);

            default:
                parent.setTag(R.string.tag_item_position, viewType);
                CommonHolder holder = createViewHolder(mContext, parent);
                AutoUtils.autoSize(holder.itemView);
                return holder;
        }
    }

    public abstract CommonHolder createViewHolder(Context mContext, ViewGroup parent);

    public abstract void createView(CommonHolder commonHolder, T object);

    @Override
    public final void onBindViewHolder(CommonHolder holder, int position) {

        this.position = position;
        if ((position == 0 && mHeaderView != null) || (position == getItemCount() -1 && mFooterView != null)){

            ILog.i("===object===", "is footer or header not createView ...");
        }else {
            if (holder != null) holder.setItemPosition(position);
            createView(holder, getObject(mHeaderView == null ? position : position-1));  //  扣除header占用的位置
        }
    }

    /**
     * 处理之前的任务，比如itemchanged、datachanged、itemremoved等 一般不需要手动触发
     */
    public final void doEvent(){
        if (recyclerView == null || recyclerView.isComputingLayout()) {
            doDelayEvent();
            return;
        }
        if (handler != null) handler.removeCallbacksAndMessages(null);
        int size = changedItem == null ? 0 : changedItem.size();
        for (int i = 0; i < size; i++){
            int position = changedItem.indexOfKey(i);
            int type = changedItem.indexOfValue(i);
            switch (type){
                case 1:
                    itemChanged(position);
                    break;
                case 2:
                    removeItem(position);
                    break;
                case 3:
                    itemInsert(position);
                    break;
                case 4:
                    dataChanged();
                    break;

            }
        }
    }

    private void doDelayEvent(){
        if (mData == null || mContext == null){
            ILog.i("===IAdapter===", "mData or mContext is null, destroy handler ...");
            mData = null;
            mContext = null;
            if (handler != null){
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }
            return;
        }
        if (handler == null) return;
        if (changedItem == null || changedItem.size() < 1) handler.removeCallbacksAndMessages(null);
        else handler.postDelayed(run, 737);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            doEvent();
        }
    };

    SparseIntArray changedItem = new SparseIntArray(); //  key is position and value is type, 1->changed, 2->remove, 3->insert, 4->DataChanged
    public final void itemChanged(int position){
        if (recyclerView != null && !recyclerView.isComputingLayout()) notifyItemChanged(position);
        else {
            changedItem.put(position, 1);
            doDelayEvent();
        }
    }

    public final void itemRemove(int position){
        if (recyclerView != null && !recyclerView.isComputingLayout()) notifyItemRemoved(position);
        else {
            changedItem.put(position, 2);
            doDelayEvent();
        }
    }

    public final void itemInsert(int position){
        if (recyclerView != null && !recyclerView.isComputingLayout()) notifyItemInserted(position);
        else {
            changedItem.put(position, 3);
            doDelayEvent();
        }
    }

    public final void dataChanged(){
        if (recyclerView != null && !recyclerView.isComputingLayout()) notifyDataSetChanged();
        else {
            changedItem.put(4, 0);
            doDelayEvent();
        }
    }

    public final T getObject(int position) {

        if (mData == null || position < 0 || position >= mData.size()) {

            return null;
        }
        return mData.get(position);
    }
}