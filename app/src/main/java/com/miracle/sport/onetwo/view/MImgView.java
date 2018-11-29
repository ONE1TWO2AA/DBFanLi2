package com.miracle.sport.onetwo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.miracle.R;


public class MImgView extends android.support.v7.widget.AppCompatImageView {
    boolean isEnableOffset = false;
    AccelerateDecelerateInterpolator adi = new AccelerateDecelerateInterpolator();

    public MImgView(Context context) {
        super(context);
    }

    public MImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isEnableOffset() {
        return isEnableOffset;
    }

    public void setEnableOffset(boolean enableOffset) {
        isEnableOffset = enableOffset;
        setScaleType(isEnableOffset ? ScaleType.MATRIX : ScaleType.FIT_CENTER);
    }

    Rect gr = new Rect();
    Point gp = new Point();

    public void updateProgress(Rect rc){
        if(getVisibility() == GONE || !isEnableOffset)
            return;
        this.getGlobalVisibleRect(gr,gp);
        Matrix matrix = getImageMatrix();
//        Log.d("TAG", hashCode()+" updateProgress() called with: rc = " + rc.top + " b "+ rc.bottom + " -- "+gr.top + " b "+ gr.bottom);

        if((rc.height() - getMeasuredHeight()) == 0){
            //out off screen
        }else{
            float pr = ((float)gr.top - rc.top) / ((float)rc.height() - getMeasuredHeight());
            pr = adi.getInterpolation(pr);
            pr = Math.max(0,pr);
            pr = Math.min(pr,1);
            if(getDrawable() != null){
                float imgH = getMeasuredHeight() * 1.3f;
                float imgHS = imgH / getDrawable().getIntrinsicHeight();
//                Log.d("TAG", hashCode()+" updateProgress: M "+getMeasuredHeight() + " OI "+getDrawable().getIntrinsicHeight() + " STH " + imgH + " IMGHS " + imgHS);
//                Log.d("TAG", hashCode()+" updateProgress:  div " + rc.height() +" - " + getMeasuredHeight() + "   ---  " + pr + " imgH "+imgH+" scaleY:"+(imgH / getDrawable().getIntrinsicHeight()));
//                Log.d("TAG", "updateProgress: (imgH - getMeasuredHeight()) "  + (imgH - getMeasuredHeight()) + "  res " +Math.abs(1-pr) * -(imgH - getMeasuredHeight()));
//                Log.d("TAG", "updateProgress: " + matrix);
                matrix.setScale( (float)getMeasuredWidth() / getDrawable().getIntrinsicWidth(),imgHS);
                matrix.postTranslate(0,(pr) * -(imgH - getMeasuredHeight()));
            }
        }
        setImageMatrix(matrix);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void finalize() throws Throwable {
        Log.d("TAG", "finalize() called " + hashCode());
        super.finalize();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        String other = "";
        if(drawable != null){
            other = drawable.getBounds().width() + " x " + drawable.getBounds().height() + "  - " + drawable.getIntrinsicWidth() + " x "+ drawable.getIntrinsicHeight() + " - " + drawable.getMinimumWidth() + " x " + drawable.getMinimumHeight() + " - " + getTag(R.id.action);
            Matrix matrix = getImageMatrix();
            matrix.reset();
            setImageMatrix(matrix);
        }
        Log.d("TAG", "setImageDrawable() called with: drawable = [" + drawable + "] " + other);
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        Log.d("TAG", "setImageBitmap() called with: bm = [" + bm + "]");
        super.setImageBitmap(bm);
    }

    @Override
    public void setTag(Object tag) {
        Log.d("TAG",  hashCode() + "setTag() called with: tag = [" + tag + "]");
        super.setTag(tag);
    }
}
