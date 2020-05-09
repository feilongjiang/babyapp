package com.example.apps.baseProject.irecyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps.baseProject.baseLib.utils.CommonTool;
import com.example.apps.baseProject.baseLib.utils.ILog;

/**
 * Created by yangchun on 16/4/15.  通用的CommonHolder
 */
public class CommonHolder extends RecyclerView.ViewHolder{

    public View itemView;
    private Context mContext;
    private SparseArray<View> mView;
    private int position;

    public CommonHolder(View itemView, Context context, boolean fillParent){
        super(itemView);
        this.itemView = itemView;
        this.mContext = context;
        mView = new SparseArray<>();

        if (!fillParent) return;

        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewParent parent = itemView.getParent();
        if (parent instanceof FrameLayout){
            lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }if (parent instanceof RelativeLayout){
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        this.itemView.setLayoutParams(lp);
    }
    
    public CommonHolder(View itemView, Context mContext) {
        this(itemView, mContext, true);
    }

    /**
     * 设置当前的model在list的id，通常用于 notifyItemChanged(holder.getItemPosition());
     */
    protected final void setItemPosition(int position){

        this.position = position;
    }

    /**
     * 获取当前的model在list的id，一般用于 notifyItemChanged(holder.getItemPosition());
     */
    public final int getItemPosition(){

        return position;
    }

    public static CommonHolder getInstance(@LayoutRes int id, Context mContext, ViewGroup parent){
        return getInstance(id, mContext, parent, false);
    }

    public static CommonHolder getInstance(@LayoutRes int id, Context mContext, ViewGroup parent, boolean fillParent){
        
        if (mContext == null){
            return null;
        }
        return new CommonHolder(LayoutInflater.from(mContext).inflate(id, parent, false), mContext, fillParent);
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mView.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mView.put(viewId, view);
        }

        if (view == null){
            ILog.e("===view is null, id is " + viewId);
        }
        return (T) view;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param resId
     * @return
     */
    public CommonHolder setText(int viewId, int resId) {
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(mContext.getResources().getString(resId));
        }
        return this;
    }
    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param resId
     * @return
     */
    public CommonHolder setText(int viewId, int resId, String oldChar, Object newChar) {
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(mContext.getResources().getString(resId).replace(oldChar, "" + newChar));
        }
        return this;
    }
    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public CommonHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text == null ? "" : text);
        }
        return this;
    }

    /**
     * 设置 View的 tag 值
     *
     * @param viewId
     * @param tag
     * @return
     */
    public CommonHolder setTag(int viewId, String tag) {
        View tv = getView(viewId);
        if (tv != null) {
            tv.setTag(tag);
        }
        return this;
    }

    public CommonHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageResource(resId);
        }
        return this;
    }

    public CommonHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageBitmap(bitmap);
        }
        return this;
    }

    public CommonHolder setImageBitmap(int viewId, int resId) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageBitmap(CommonTool.drawableToBitmap
                    (mContext.getResources().getDrawable(resId)));
        }
        return this;
    }

    public CommonHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageDrawable(drawable);
        }
        return this;
    }

    public CommonHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundResource(backgroundRes);
        }
        return this;
    }

    public CommonHolder setTextColor(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setTextColor(mContext.getResources().getColor(textColorRes));
        }
        return this;
    }

    public CommonHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public CommonHolder setInvisible(int viewId, boolean invisible) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(invisible ? View.INVISIBLE : View.VISIBLE);
        }
        return this;
    }

    public CommonHolder setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        if (view != null) {
            view.setChecked(checked);
        }
        return this;
    }

    public Context getContext(){

        return mContext;
    }

    public CommonHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    public CommonHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnLongClickListener(listener);
        }
        return this;
    }
}