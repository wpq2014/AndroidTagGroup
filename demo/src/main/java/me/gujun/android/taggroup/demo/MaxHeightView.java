package me.gujun.android.taggroup.demo;

/**
 * Created by carbs on 2016/5/12.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 先判断是否设定了mMaxHeight，如果设定了mMaxHeight，则直接使用mMaxHeight的值，
 * 如果没有设定mMaxHeight，则判断是否设定了mMaxRatio，如果设定了mMaxRatio的值 则使用此值与屏幕高度的乘积作为最高高度
 *
 * @author Carbs
 */
public class MaxHeightView extends FrameLayout {

    private static final float DEFAULT_MAX_RATIO = 0.6f;
    private static final float DEFAULT_MAX_HEIGHT = 0f;

    private float mMaxRatio = DEFAULT_MAX_RATIO;// 优先级高
    private float mMaxHeight = DEFAULT_MAX_HEIGHT;// 优先级低

    public MaxHeightView(Context context) {
        super(context);
        init();
    }

    public MaxHeightView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightView);;
        try {
            mMaxRatio = a.getFloat(R.styleable.MaxHeightView_mhv_HeightRatio, DEFAULT_MAX_RATIO);
            mMaxHeight = a.getDimension(R.styleable.MaxHeightView_mhv_HeightDimen, DEFAULT_MAX_HEIGHT);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        if (mMaxHeight <= 0) {
            mMaxHeight = mMaxRatio * (float) getScreenHeight();
        } else {
            mMaxHeight = Math.min(mMaxHeight, mMaxRatio * (float) getScreenHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

//        Log.e("onMeasure", heightMode + ", " + heightSize + ", " + mMaxHeight);
        heightSize = heightSize >= mMaxHeight ? (int) mMaxHeight : heightSize;
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

}