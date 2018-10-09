package com.example.venki.timeline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class TimelineRecyclerView extends View {

    private Drawable mMarker;
    private Drawable mStartLine;
    private Drawable mEndLine;
    private int mMarkerSize;
    private int mLineSize;
    private int mLinePadding;
    private boolean mMarkerInCenter;
    private Rect mBounds;

    private Context mContext;
    public TimelineRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
     //   Log.e("venki","in TimeLineRecyclerView");
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.timeline_view);
        mMarker = typedArray.getDrawable(R.styleable.timeline_view_marker);
        mStartLine = typedArray.getDrawable(R.styleable.timeline_view_line);
        mEndLine = typedArray.getDrawable(R.styleable.timeline_view_line);
        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.timeline_view_markerSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, mContext.getResources().getDisplayMetrics()));
        mLineSize = typedArray.getDimensionPixelSize(R.styleable.timeline_view_lineSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, mContext.getResources().getDisplayMetrics()));
 //       mLineOrientation = typedArray.getInt(R.styleable.timeline_view_lineOrientation, 1);
        mLinePadding = typedArray.getDimensionPixelSize(R.styleable.timeline_view_lineSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mContext.getResources().getDisplayMetrics()));
        mMarkerInCenter = typedArray.getBoolean(R.styleable.timeline_view_markerInCenter, true);
        typedArray.recycle();

        if (mMarker == null)
            mMarker = mContext.getResources().getDrawable(R.drawable.marker);

        if (mStartLine == null && mEndLine == null) {
            mStartLine = new ColorDrawable(mContext.getResources().getColor(android.R.color.holo_blue_light));
            mEndLine = new ColorDrawable(mContext.getResources().getColor(android.R.color.holo_blue_bright));
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.e("venki","in onMeasure");
        int w = mMarkerSize + getPaddingLeft() + getPaddingRight();
        int h = mMarkerSize + getPaddingTop() + getPaddingBottom();

        int widthSize = resolveSizeAndState(w,widthMeasureSpec,0);
        int heightSize = resolveSizeAndState(h,heightMeasureSpec,0);
        setMeasuredDimension(widthSize,heightSize);
        initDrawable();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
  //      Log.e("venki","in onSizeChanged");
        initDrawable();

    }

    private void initDrawable() {
        int pLeft = getPaddingLeft();
        int pRight = getPaddingRight();
        int pTop = getPaddingTop();
        int pBottom = getPaddingBottom();

        int width = getWidth();// Width of current custom view
        int height = getHeight();

        int cWidth = width - pLeft - pRight;// Circle width
        int cHeight = height - pTop - pBottom;

        int markSize = Math.min(mMarkerSize, Math.min(cWidth, cHeight));

    //    Log.e("RecyclerView","mMarkerInCenter:"+mMarkerInCenter);
        if (mMarkerInCenter) { //Marker in center is true

            if (mMarker != null) {
                mMarker.setBounds((width / 2) - (markSize / 2), (height / 2) - (markSize / 2), (width / 2) + (markSize / 2), (height / 2) + (markSize / 2));
                mBounds = mMarker.getBounds();
            }

        } else { //Marker in center is false

            if (mMarker != null) {
                mMarker.setBounds(pLeft, pTop, pLeft + markSize, pTop + markSize);
                mBounds = mMarker.getBounds();
            }
        }
    //    Log.e("venki", "Rect Bounds:" + mBounds.toString());
        int centerX = mBounds.centerX();
        int lineLeft = centerX - (mLineSize >> 1);
        //Vertical Line
        if (mStartLine != null) {
            mStartLine.setBounds(lineLeft, 0, mLineSize + lineLeft, mBounds.top - mLinePadding);
        }

        if (mEndLine != null) {
            mEndLine.setBounds(lineLeft, mBounds.bottom + mLinePadding, mLineSize + lineLeft, height);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    //    Log.e("venki","in onDraw");
        if(mMarker != null) {
            mMarker.draw(canvas);
        }

        if(mStartLine != null) {
            mStartLine.draw(canvas);
        }

        if(mEndLine != null) {
            mEndLine.draw(canvas);
        }

    }


 /**
     * Sets marker.
     *
     * @param marker will set marker drawable to timeline
     */
    public void setMarker(Drawable marker) {
        mMarker = marker;
        initDrawable();
    }

    /**
     * Sets marker.
     *
     * @param marker will set marker drawable to timeline
     * @param color  with a color
     */
    public void setMarker(Drawable marker, int color) {
        mMarker = marker;
        mMarker.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        initDrawable();
    }

    /**
     * Sets marker color.
     *
     * @param color the color
     */
    public void setMarkerColor(int color) {
        mMarker.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        initDrawable();
    }

    /**
     * Sets start line.
     *
     * @param color    the color
     * @param viewType the view type
     */
    public void setStartLine(int color, int viewType) {
        mStartLine = new ColorDrawable(color);
        initLine(viewType);
    }

    /**
     * Sets end line.
     *
     * @param color    the color
     * @param viewType the view type
     */
    public void setEndLine(int color, int viewType) {
        mEndLine = new ColorDrawable(color);
        initLine(viewType);
    }

    /**
     * Sets marker size.
     *
     * @param markerSize the marker size
     */
    public void setMarkerSize(int markerSize) {
        mMarkerSize = markerSize;
        initDrawable();
    }

    /**
     * Sets line size.
     *
     * @param lineSize the line size
     */
    public void setLineSize(int lineSize) {
        mLineSize = lineSize;
        initDrawable();
    }

    /**
     * Sets line padding
     * @param padding the line padding
     */
    public void setLinePadding(int padding) {
        mLinePadding = padding;
        initDrawable();
    }

    private void setStartLine(Drawable startLine) {
        mStartLine = startLine;
        initDrawable();
    }

    private void setEndLine(Drawable endLine) {
        mEndLine = endLine;
        initDrawable();
    }

    /**
     * Init line.
     *
     * @param viewType the view type
     */
    public void initLine(int viewType) {
        if(viewType == LineType.BEGIN) {
            setStartLine(null);
        } else if(viewType == LineType.END) {
            setEndLine(null);
        } else if(viewType == LineType.ONLYONE) {
            setStartLine(null);
            setEndLine(null);
        }
        initDrawable();
    }

    /**
     * Gets timeline view type.
     *
     * @param position   the position
     * @param total_size the total size
     * @return the time line view type
     */
    public static int getTimeLineViewType(int position, int total_size) {
        if(total_size == 1) {
            return LineType.ONLYONE;
        } else if(position == 0) {
            return LineType.BEGIN;
        } else if(position == total_size - 1) {
            return LineType.END;
        } else {
            return LineType.NORMAL;
        }
    }

}
