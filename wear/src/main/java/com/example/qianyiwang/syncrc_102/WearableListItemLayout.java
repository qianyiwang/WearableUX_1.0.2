package com.example.qianyiwang.syncrc_102;

/**
 * Created by qianyiwang on 2/10/17.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WearableListItemLayout extends LinearLayout
        implements WearableListView.OnCenterProximityListener {

    private static final float NO_ALPHA = 1f, PARTIAL_ALPHA = 0.40f;
    private static final float NO_X_TRANSLATION = 0f, X_TRANSLATION = 20f;

    private CircledImageView mCircle;
    private TextView itemName;
//    private final int mUnselectedCircleColor, mSelectedCircleColor;
    private float mBigCircleRadius;
    private float mSmallCircleRadius;

    public WearableListItemLayout(Context context) {
        this(context, null);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);

//        mUnselectedCircleColor = Color.parseColor("#80000000");
//        mSelectedCircleColor = Color.parseColor("#80000000");
        mSmallCircleRadius = getResources().
                getDimensionPixelSize(R.dimen.small_circle_radius);
        mBigCircleRadius = getResources().
                getDimensionPixelSize(R.dimen.big_circle_radius);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircle = (CircledImageView) findViewById(R.id.circle);
        itemName = (TextView)findViewById(R.id.name);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        if (animate) {
            animate().alpha(NO_ALPHA).translationX(X_TRANSLATION).start();
        }
//        mCircle.setCircleColor(mSelectedCircleColor);
        mCircle.setCircleRadius(mBigCircleRadius);
        itemName.setTypeface(null, Typeface.BOLD);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        if (animate) {
            animate().alpha(PARTIAL_ALPHA).translationX(NO_X_TRANSLATION).start();
        }
//        mCircle.setCircleColor(mUnselectedCircleColor);
        mCircle.setCircleRadius(mSmallCircleRadius);
        itemName.setTypeface(null, Typeface.NORMAL);
    }
}
