package com.example.apps.baseProject.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Transformation;
import com.example.apps.baseProject.App;
import com.example.apps.baseProject.R;
import com.example.apps.baseProject.baseLib.utils.CommonTool;
import com.example.apps.baseProject.baseLib.utils.ILog;
import com.example.apps.baseProject.baseLib.utils.StringUtils;

/**
 * Created by yangchun on 16/6/14.
 */
public class ImageLoader {

    private static int maxWidth;
    private static double wBili, hBili;
    static {
        int target_width = StringUtils.getInt(CommonTool.getMetaData(App.getInstance(),
                "design_width"));
        int target_height = StringUtils.getInt(CommonTool.getMetaData(App.getInstance(),
                "design_height"));
        int mWidth = CommonTool.getScreenWidth(App.getInstance());
        int mHeight = CommonTool.getScreenHeight(App.getInstance());
        maxWidth = mWidth / 2;
        wBili = ((double) mWidth) / ((double) target_width);
        hBili = ((double)mHeight / ((double)target_height));
    }

    /**
     *
     * @param img
     * @param url
     * @param type
     */
    public static final int TYPE_TOP = 1;
    public static final int TYPE_BOTTOM = 2;
    public static final int TYPE_LEFT = 3;
    public static final int TYPE_RIGHT = 4;
    public static final int TYPE_ALL = 5;

    public static void loadWidthCorner(ImageView img, String url, int width, int height,
                                       int radius, int type){
        RoundedCornersTransformation transformation = null;
        if (type < TYPE_TOP || type > TYPE_RIGHT){
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.ALL);
        }
        else if (type == 1){
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.TOP);
        }
        else if (type == 2){
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.BOTTOM);
        }
        else if (type == 3){
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.LEFT);
        }
        else if (type == 4){
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.RIGHT);
        }
        load(img, url, width, height, transformation);
    }

    public static void loadWidthCorner(ImageView img, String url, int radius, int type){
        loadWidthCorner(img, url, -1, -1, radius, type);
    }

    public static void loadCircle(ImageView img, String url){
        loadCircle(img, url, -1, -1);
    }

    public static void loadCircle(ImageView img, String url, int width, int height){
        Transformation transformation = new CropCircleTransformation(img.getContext());
        load(img, url, width, height, transformation);
    }

    public static void load(ImageView img, String url, int width, int height, Transformation transformation){
        if (StringUtils.isEmpty(url) || img == null){
            ILog.e("===ImageLoader===", "url or img is null ...");
            return;
        }
        height *= hBili;
        width *= wBili;
        if (width > maxWidth) {
            height = height * maxWidth / width;
            width = maxWidth;
            ILog.i("===resize===", " width is " + width + " height is " + height);
        }
        Context context = img.getContext();
        if (!canLoad(context)) return;

        String[] urls = url.split("\\?");

        //  this is gif
        if(urls[0].endsWith(".gif")) {
            Object placeholder = img.getTag(R.string.glide_tag_placeholder);
            DrawableRequestBuilder<String> builder = Glide.with(context).load(url);
            if (width > 0 && height > 0) builder.override(width, height);
            if (placeholder != null){
                builder.placeholder((int)placeholder);
                Object error = img.getTag(R.string.glide_tag_failed);
                builder.error(error == null ? (int)placeholder : (int)error);
                Object nullImg = img.getTag(R.string.glide_tag_null);
                builder.fallback(nullImg == null ? (int)placeholder : (int)nullImg);
            }
            builder.into(img);
            return;
        }
        BitmapRequestBuilder<String, Bitmap> builder;
        try {
            builder = Glide.with(context).load(url).asBitmap();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        if (width > 1 && height > 1) {
            builder.override(width, height);
        }if (transformation != null){
            builder.format(DecodeFormat.PREFER_ARGB_8888).transform(transformation);
        }
        Object placeholder = img.getTag(R.string.glide_tag_placeholder);
        if (placeholder != null){
            builder.placeholder((int)placeholder);
            Object error = img.getTag(R.string.glide_tag_failed);
            builder.error(error == null ? (int)placeholder : (int)error);
            Object nullImg = img.getTag(R.string.glide_tag_null);
            builder.fallback(nullImg == null ? (int)placeholder : (int)nullImg);
        }
        builder.into(img);
    }

    private static boolean canLoad(Context context){
        if (context == null)return false;
        if (context instanceof Activity && ((Activity) context).isFinishing()) return false;
        return true;
    }

    public static void load(ImageView img, String url, int width, int height){
        load(img, url, width, height, null);
    }

    public static void load(ImageView img, String url){
        load(img, url, -1, -1, null);
    }
}
