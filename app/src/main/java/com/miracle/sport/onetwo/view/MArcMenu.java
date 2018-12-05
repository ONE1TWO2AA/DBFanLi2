package com.miracle.sport.onetwo.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MArcMenu extends FrameLayout {
    public static String TAG = ""+MArcMenu.class.toString();
    VelocityTracker velocityTracker;
    OverScroller overScroller;
    Paint p1;
    Paint p2;
    Paint p3;
//    View target;

    int mTouchSlop;
    int mMaximumVelocity;

    public MArcMenu(Context context) {
        super(context);
        init();
    }

    public MArcMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
        velocityTracker = VelocityTracker.obtain();
        p1 = new Paint();
        p1.setStrokeWidth(4f);
        p1.setColor(0x80FF0000);

        p2 = new Paint();
        p2.setStrokeWidth(4f);
        p2.setColor(0x800000FF);

        p3 = new Paint();
        p3.setStrokeWidth(4f);
        p3.setColor(0x8000ff00);

        overScroller = new OverScroller(getContext());

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
//        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
//        mOverscrollDistance = configuration.getScaledOverscrollDistance();
//        mOverflingDistance = configuration.getScaledOverflingDistance();
//        mVerticalScrollFactor = configuration.getScaledVerticalScrollFactor();

//        camera.rotate(30,0,0);
//        target = inflate(getContext(), R.layout.item, null);
//        target.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "target",Toast.LENGTH_SHORT).show();
//            }
//        });
//        addView(target);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    PointF nowPoint = new PointF();
    PointF velocityPoint = new PointF();
    ArrayList<PointF> touchPl = new ArrayList<>();
    ArrayList<PointF> velocityPl = new ArrayList<>();
    ArrayList<PointF> scrollerPl = new ArrayList<>();

    PointF downMotionPoint;
    int movedLen = 0;

    boolean mIsBeingDragged = false;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE) && (mIsBeingDragged)) {
            return true;
        }

        if (super.onInterceptTouchEvent(ev)) {
            return true;
        }

        PointF nowPoint = new PointF(ev.getX(), ev.getY());
        switch (action){
            case MotionEvent.ACTION_DOWN:
                downMotionPoint = nowPoint;
                mIsBeingDragged = !overScroller.isFinished();
                touchPl.clear();
                velocityPl.clear();
                velocityTracker.clear();
                overScroller.abortAnimation();
                break;
            case MotionEvent.ACTION_MOVE:
                movedLen = (int) Math.hypot(nowPoint.x - downMotionPoint.x, nowPoint.y - downMotionPoint.y);
                if(movedLen > mTouchSlop){
                    mIsBeingDragged = true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_UP:
                mIsBeingDragged = false;
                break;
            default:
                break;
        }

        return mIsBeingDragged;
    }

    boolean isneedTouchEvent = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() called with: event = [" + event + "]");
        boolean superres = super.onTouchEvent(event);
//        if(event.getAction() == MotionEvent.ACTION_DOWN){
//            touchPl.clear();
//            velocityPl.clear();
//            velocityTracker.clear();
//        }
        velocityTracker.addMovement(event);
        velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
        PointF t = new PointF(event.getX(), event.getY());
        touchPl.add(t);
        velocityPl.add(new PointF(t.x + velocityTracker.getXVelocity(), t.y + velocityTracker.getYVelocity()));

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            lt = null;
        }

        if(!superres){
            if(event.getAction() != MotionEvent.ACTION_UP){
                isAutoScroll = false;
                isneedTouchEvent = true;
            }else{
                isAutoScroll = true;
                scrollerPl.clear();
                isFirstAfterTouchUp = true;
                if(touchPl.size() > 0) {
                    PointF last = touchPl.get(touchPl.size() - 2);
                }
                if(getChildCount() > 0) {
//                    overScroller.fling((int) t.x, (int) t.y, (int) velocityTracker.getXVelocity(), (int) velocityTracker.getYVelocity(), 0, getMeasuredWidth(), 0,  getMeasuredHeight(), 100, 100);
                    overScroller.fling((int) t.x, (int) t.y, (int) velocityTracker.getXVelocity(), (int) velocityTracker.getYVelocity(), Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE,  Integer.MAX_VALUE, 0, 0);
                }
                isneedTouchEvent = false;
            }
        }else{
            isneedTouchEvent = false;
        }
        postInvalidateOnAnimation(); // mChoreographer.postCallback(Choreographer.CALLBACK_ANIMATION, this, null);
        requestLayout();

        if(isneedTouchEvent)
            return true;
        return superres;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(overScroller.computeScrollOffset()) {
            postInvalidateOnAnimation();
            requestLayout();
        }else{
        }
    }

    int maxChildBound = 0;
    float rad = 40;
    PointF center;
    float angleGap = 0;

    float moveScaleFactor = 100;
    float moveRadias = 0; //abs
    float moveLen = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childWMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth() ,MeasureSpec.AT_MOST);
        int childHMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() ,MeasureSpec.AT_MOST);
//        target.measure(childWMeasureSpec, childHMeasureSpec);
        maxChildBound = 0;
        for(int i = 0; i < getChildCount(); i++){
            View cv = getChildAt(i);
            cv.measure(childWMeasureSpec ,childHMeasureSpec);
            maxChildBound = cv.getMeasuredWidth() > maxChildBound ? cv.getMeasuredWidth() : maxChildBound;
            maxChildBound = cv.getMeasuredHeight() > maxChildBound ? cv.getMeasuredHeight() : maxChildBound;
        }
        if(onMaxChildeChangeLis != null)
            onMaxChildeChangeLis.onchange(maxChildBound);
        center = new PointF(getMeasuredWidth() / 2,getMeasuredHeight() / 2);
        rad = Math.min(center.x, center.y) * 0.8f;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        target.layout(10,10,100,100);
        //add track points
        lastPoint = lt;
        if(isAutoScroll){
            if(!overScroller.isFinished()){
                PointF tmp = new PointF(overScroller.getCurrX(), overScroller.getCurrY());//scroller 用于canvas.translate
                Log.i(TAG, "onLayout: overScrollerXY  " + overScroller.getCurrX() + "," + overScroller.getCurrY());
                scrollerPl.add(tmp);
                lt = tmp;
            }
        }else{
            if (touchPl.size() > 0) {
                lt = touchPl.get(touchPl.size() - 1);
            }
        }

        //layout child's params
        angleGap = (float) ((Math.PI*2) / (getChildCount() > 0 ? getChildCount() : 1));

        moveLen = 0;
        if(lt != null){
            if(lastPoint != null){
                //cacu move motion camera
                camera = new Camera();

                int RY = (int) clamp(-maxRotateY,(lt.x - lastPoint.x) ,maxRotateY);
                Log.i(TAG, "onLayout: RYRY " + RY);
                int toRy = currentRy + RY;
                toRy = (int) clamp(-maxRotateY, toRy ,maxRotateY);
                RY = toRy - currentRy;
                Log.i(TAG, "onLayout: currentRy : "  +currentRy  + " toRy " + toRy + " RY " + RY);
                currentRy += RY;
                camera.rotateY(currentRy);

                int RX = (int) clamp(-maxRotateX, -(lt.y - lastPoint.y) ,maxRotateX);
                Log.i(TAG, "onLayout: RXRX " + RX);
                int toRx = currentRx + RX;
                toRx = (int) clamp(-maxRotateX, toRx ,maxRotateX);
                RX = toRx - currentRx;
                Log.i(TAG, "onLayout: currentRx : "  +currentRx  + " toRx " + toRx + " RX " + RX);
                currentRx += RX;
                camera.rotateX(currentRx);

                //cacu moved len
                float res = twoDotRadias(lastPoint ,lt , center);
                moveLen = PointF.length(lt.x - lastPoint.x, lt.y - lastPoint.y);
                if(twoDotLen(center, lt) > rad || !overScroller.isFinished()){//点击范围在圆外，则按照移动距离转换为圆上弧线，在转换为旋转角度的办法，解决在圆外滑动如果以直接计算角度造成滚动过于缓慢而不便于操作的问题
                    float tmprad = line2cicelarc2Radias(lt, lastPoint, rad);
                    if(res > 0){// +
                        moveRadias += tmprad;
                    }
                    else{ // -
                        moveRadias -= tmprad;
                    }
                }else{//点击点在圆内，则按照实际移动角度旋转
                    Log.i(TAG, "onLayout: twoDotRadias " + res);
                    moveRadias += res;
                }
                Log.i(TAG, "onLayout: moveRadias " + moveRadias);
            }
        }
        moveRadias %= (2*Math.PI);


        //layout child
        layoutPoints.clear();
        for(int i = 0 ; i < getChildCount() ; i++){
            float nowradias = angleGap * i + moveRadias;
//            PointF layoutPoint = rad2ciclerPoint(nowradias, center, rad);
//            PointF layoutPoint = rad2TranglePoint(nowradias, center, rad);
            PointF layoutPoint = rad2RectPoint(nowradias, center);
            layoutPoints.add(layoutPoint);
            View child = getChildAt(i);
            int halfCW = (child.getMeasuredWidth()/2);
            int halfCH = (child.getMeasuredHeight()/2);

            int l = (int)(layoutPoint.x - halfCW);
            int t = (int)(layoutPoint.y - halfCH);
            int r = (int)(layoutPoint.x + halfCW);
            int b = (int)(layoutPoint.y + halfCH);
            child.layout(l,t,r,b);
        }
    }
    int maxRotateX = 40;
    int maxRotateY = 30;
    int currentRx = 0;
    int currentRy = 0;
    Camera camera = new Camera();
    Camera camera2 = new Camera();

    PointF lastPoint;
    PointF lt;
    boolean isFirstAfterTouchUp = false;
    boolean isAutoScroll = false;

    List<PointF> layoutPoints = new LinkedList<>();

    public interface onMaxChildeChangeLis{
        void onchange(int newLen);
    }

    onMaxChildeChangeLis onMaxChildeChangeLis;

    public MArcMenu.onMaxChildeChangeLis getOnMaxChildeChangeLis() {
        return onMaxChildeChangeLis;
    }

    public void setOnMaxChildeChangeLis(MArcMenu.onMaxChildeChangeLis onMaxChildeChangeLis) {
        this.onMaxChildeChangeLis = onMaxChildeChangeLis;
    }

    private float clamp(float min, float val, float max){
        return Math.max(min, Math.min(max, val));
    }

    private float clamp(int min, int val, int max){
        return Math.max(min, Math.min(max, val));
    }

    private PointF rad2ciclerPoint(float radias, PointF center, float rad){
        PointF resP = new PointF();
        float y = (float) (rad * Math.sin(radias));
        resP.y = y + center.y;
        float x = (float) (rad * Math.cos(radias));
        resP.x = x + center.x;
        return resP;
    }

    private PointF rad2TranglePoint(float radias, PointF center, float rad){
        PointF resP = new PointF();

        int count = 3;
        float radGap = (float) (Math.PI*2 / count);
        Path path = new Path();
        for(int i = 0 ;i < count; i++){
            PointF tmp = new PointF((float)(rad * Math.sin(radGap * i)), (float)(rad * Math.cos(radGap * i)));
            if(i == 0)
                path.moveTo(tmp.x, tmp.y);
            else{
                path.lineTo(tmp.x, tmp.y);
            }
        }
        path.close();

        float per;
        if(radias < 0)
            radias = (float) ((Math.PI*2) + radias);
        per = (float) (radias % (Math.PI*2) / (Math.PI*2));//先回绕，再占全路径的百分比
        android.graphics.PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, true);
        float[] out = new float[2];
        pathMeasure.getPosTan(pathMeasure.getLength() * per,out,null);

        resP.x = out[0] + center.x;
        resP.y = out[1] + center.y;
        return resP;
    }

    Path path;
    PathMeasure pathMeasure;
    private PointF rad2RectPoint(float radias, PointF center){
        PointF resP = new PointF();

//        if(path == null){
            int padding = maxChildBound / 2 + 6;
            Log.i(TAG, "rad2RectPoint: maxChildBound " + maxChildBound);
            path = new Path();
            path.moveTo(padding, padding);
            path.lineTo(getMeasuredWidth() - padding, padding);
            path.lineTo(getMeasuredWidth() - padding, getMeasuredHeight() - padding);
            path.lineTo(padding, getMeasuredHeight() - padding);
//            path.moveTo(padding, padding);
//            path.lineTo(getMeasuredWidth() - padding , padding);
//            path.lineTo(getMeasuredWidth() - padding, getMeasuredHeight() - padding);
//            path.lineTo(padding , getMeasuredHeight() - padding);
            path.close();

            pathMeasure = new PathMeasure();
            pathMeasure.setPath(path, true);
//        }

        float per;
        if(radias < 0)
            radias = (float) ((Math.PI*2) + radias);
        per = (float) (radias % (Math.PI*2) / (Math.PI*2));//先回绕，再计算占全路径的百分比

        float[] out = new float[2];
        pathMeasure.getPosTan(pathMeasure.getLength() * per, out,null);

        resP.x = out[0];
        resP.y = out[1];
        return resP;
    }

    private float line2cicelarc2Radias(PointF start, PointF end, float radias){
        PointF toOrigin = new PointF(end.x - start.x , end.y - start.y);
        float tmprad = toOrigin.length() / radias;
        return tmprad;
    }

    private float line2cicelarc2RadiasWithDir(PointF start, PointF end, float radias, PointF center){
        PointF toOrigin = new PointF(end.x - start.x , end.y - start.y);
        float tmprad = toOrigin.length() / radias;
        return tmprad;
    }

    private float twoDotLen(PointF d1, PointF d2){
        return new PointF(d2.x - d1.x, d2.y - d1.y).length();
    }

    private float twoDotRadias(PointF start, PointF end, PointF center){
        float endRadias = dotInCicleRadias(center, end);
        float startRadias = dotInCicleRadias(center, start);
        Log.i(TAG, "twoDotRadias: endRadias - startRadias : " + endRadias +" - " + startRadias + " = " + (endRadias - startRadias));
        return endRadias - startRadias;
    }

    /**
     *           -y
     *         |
     *         |
     *   -x ---.---> +x
     *  center |
     *         | +y
     *         V
     * @return
     */
    private float dotInCicleRadias(PointF center, PointF dot){
        PointF inUnitCicleP = new PointF(dot.x - center.x , dot.y - center.y);
        int qua = getQuadrant(center, dot);
        switch (qua){
            case 0: //0-PI/2
                return (float) Math.asin(inUnitCicleP.y / inUnitCicleP.length());
            case 1: //PI/2 - PI
                return (float) Math.acos(inUnitCicleP.x / inUnitCicleP.length());
            case 2: //PI - 3PI/2
                Log.i(TAG, "dotInCicleRadias: 2 : " + Math.asin(inUnitCicleP.y / inUnitCicleP.length()));
                return (float) (Math.PI - Math.asin(inUnitCicleP.y / inUnitCicleP.length()));
            case 3: //3PI/2 - 2PI
                Log.i(TAG, "dotInCicleRadias: 3 : " + Math.asin(inUnitCicleP.y / inUnitCicleP.length()));
                return (float) (2*Math.PI + Math.asin(inUnitCicleP.y / inUnitCicleP.length()));
        }
        return 0f;
    }

    PointF tmp1;

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.save();
//        camera.applyToCanvas(canvas);
        super.onDraw(canvas);
//        canvas.restore();
//        canvas.save();

        //draw line
        int i = 0;
        for(PointF p : touchPl){
            if(i - 1 >= 0){
                PointF tmp = touchPl.get(i-1);
                PointF lv = velocityPl.get(i-1);
                canvas.drawLine(tmp.x, tmp.y, p.x, p.y , p1);
                PointF nv = velocityPl.get(i);
                canvas.drawLine(lv.x , lv.y , nv.x, nv.y, p2);
            }
            i++;
        }
        Log.d(TAG, "onDraw() called with: canvas = [" + canvas + "]" + overScroller.computeScrollOffset());

        i = 0;
        for(PointF p : scrollerPl){
            if(i - 1 >= 0){
                PointF tmp = scrollerPl.get(i-1);
                canvas.drawLine(tmp.x, tmp.y, p.x, p.y , p3);
            }
            i++;
        }

        //draw cicel
        float tmp = p2.getStrokeWidth();
        p2.setStrokeWidth(10);
        for(PointF layoutP : layoutPoints){
            canvas.drawPoint(center.x, center.y, p2);
            canvas.drawPoint(layoutP.x ,layoutP.y ,p2);
        }
        p2.setStrokeWidth(tmp);
    }

    private int getQuadrant(PointF center, PointF dot){
        PointF inUnitCicleP = new PointF(dot.x - center.x , dot.y - center.y);
        if(inUnitCicleP.x > 0){
            if(inUnitCicleP.y > 0)
                return 0;
            return 3;
        }else{
            if(inUnitCicleP.y > 0)
                return 1;
            return 2;
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.d(TAG, "drawChild() called with: canvas = [" + canvas + "], child = [" + child + "], drawingTime = [" + drawingTime + "] + "+canvas.getWidth() + "x" + canvas.getHeight());
//        int centerX = child.getWidth() / 2 + child.getLeft();
//        int centerY = child.getHeight() / 2 + child.getTop();
//        canvas.save();
//        int ctx = child.getLeft() + (child.getWidth() / 2);
//        int cty = child.getTop() + (child.getHeight() / 2);
//        canvas.translate(ctx, cty);
//        camera.applyToCanvas(canvas);
//        canvas.translate(-ctx, -cty);
//        Paint p = new Paint();
//        p.setColor(Color.RED);
//        p.setStyle(Paint.Style.FILL);
//        Rect rr = new Rect();
//        child.getDrawingRect(rr);
//        rr.set(child.getLeft(),child.getTop(),child.getRight(),child.getBottom());
//        Log.i(TAG, "drawChild:  child : " + String.format("%s,%s,%s,%s",child.getLeft(),child.getTop(),child.getRight(),child.getBottom()));
//        rr.inset(-10,-10);
//        canvas.drawRect(rr, p);
        boolean result = super.drawChild(canvas, child, drawingTime);
//        canvas.restore();

//        result = true;
        return result;
    }
}
