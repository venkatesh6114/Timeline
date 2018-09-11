package com.example.venki.timeline;

import android.content.Context;
import android.content.res.TypedArray;
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
    private int mLineOrientation;
    private int mLinePadding;
    private boolean mMarkerInCenter;

    private Context mContext;
    public TimelineRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        Log.e("venki","in TimeLineRecyclerView");
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.timeline_view);
        mMarker = typedArray.getDrawable(R.styleable.timeline_view_marker);
        mStartLine = typedArray.getDrawable(R.styleable.timeline_view_line);
        mEndLine = typedArray.getDrawable(R.styleable.timeline_view_line);
        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.timeline_view_markerSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, mContext.getResources().getDisplayMetrics()));
        mLineSize = typedArray.getDimensionPixelSize(R.styleable.timeline_view_lineSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, mContext.getResources().getDisplayMetrics()));
        mLineOrientation = typedArray.getInt(R.styleable.timeline_view_lineOrientation, 1);
        mLinePadding = typedArray.getDimensionPixelSize(R.styleable.timeline_view_lineSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mContext.getResources().getDisplayMetrics()));
        mMarkerInCenter = typedArray.getBoolean(R.styleable.timeline_view_markerInCenter, true);
        typedArray.recycle();

        if (mMarker == null)
            mMarker = mContext.getResources().getDrawable(R.drawable.marker);

        if (mStartLine == null && mEndLine == null) {
            mStartLine = new ColorDrawable(mContext.getResources().getColor(android.R.color.darker_gray));
            mEndLine = new ColorDrawable(mContext.getResources().getColor(android.R.color.darker_gray));
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("venki","in onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("venki","in onSizeChanged");

    }
}
