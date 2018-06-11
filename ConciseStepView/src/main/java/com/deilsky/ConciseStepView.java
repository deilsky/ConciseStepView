package com.deilsky;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deilsky.concise_stepview.R;

import java.util.ArrayList;

public class ConciseStepView {

    public interface OnStepClickListener {
        void onStepClick(ConciseData data);
    }

    private OnStepClickListener onStepClickListener;

    public ConciseStepView setOnStepClickListener(OnStepClickListener listener) {
        this.onStepClickListener = listener;
        return this;
    }

    private Context mContext;
    /**
     * 根布局
     */
    private HorizontalScrollView mHorizontalScrollView;
    /**
     * 第一层布局
     */
    private LinearLayout mLinearLayout;
    /**
     * 数据
     */
    private ArrayList<ConciseData> mSteps;
    /**
     * 步长
     */
    private int mStepSize = 0;
    /**
     * 未选中 线 颜色
     */
    private int default_line_color;
    /**
     * 未选中 文字颜色
     */
    private int default_step_color;
    /**
     * 选中 线 颜色
     */
    private int current_line_color;
    /**
     * 选中 文字颜色
     */
    private int current_step_color;
    private int mWidth;

    private int mHeight;
    private int mStepWidth = 0;

    private int default_drawable;
    private int current_drawable;
    private int mTextSize = 12;
    private int mLineHeight = 5;

    // 每一个stepView
    private LinearLayout stepLayout;
    // stepView中的 图片所在布局
    private RelativeLayout drawableLayout;
    // stepView中的文字
    private TextView stepText;
    // stepView中的图片
    private ImageView imageView;
    // 左右两条线
    private View leftLine, rightLine;

    private ConciseStepView() {
        default_line_color = Color.GRAY;
        default_step_color = Color.GRAY;
        current_line_color = Color.GREEN;
        current_step_color = Color.GREEN;
    }

    public static ConciseStepView create() {
        return new ConciseStepView();
    }

    /**
     * 加载布局
     *
     * @param mTarget HorizontalScrollView
     * @return ConciseStepView
     */
    public ConciseStepView attach(HorizontalScrollView mTarget) {
        mHorizontalScrollView = mTarget;
        mContext = mTarget.getContext();
        mLinearLayout = new LinearLayout(mContext);
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return this;
    }

    /**
     * 字号
     *
     * @param textSize 字号
     * @return ConciseStepView
     */
    public ConciseStepView stepTextSize(int textSize) {
//        mTextSize = measureSP(textSize);
        mTextSize = textSize;
        return this;
    }

    /**
     * 线高
     *
     * @param lineHeight 线高
     * @return ConciseStepView
     */
    public ConciseStepView stepLineHeight(int lineHeight) {
        mLineHeight = measureDP(lineHeight);
        return this;
    }

    /**
     * 加载数据
     *
     * @param steps 加载数据
     * @return
     */
    public ConciseStepView steps(ArrayList<ConciseData> steps) {
        mSteps = steps;
        mStepSize = mSteps.size();
        return this;
    }

    /**
     * 设置未选中样式
     *
     * @param line_color  线
     * @param step_color  文字
     * @param drawableRes 图片
     * @return ConciseStepView
     */
    public ConciseStepView defaults(@ColorRes int line_color, @ColorRes int step_color, @DrawableRes int drawableRes) {
        default_line_color = line_color;
        default_step_color = step_color;
        default_drawable = drawableRes;
        return this;
    }

    /**
     * 选中样式
     *
     * @param line_color  线
     * @param step_color  文字
     * @param drawableRes 图片
     * @return ConciseStepView
     */
    public ConciseStepView currents(@ColorRes int line_color, @ColorRes int step_color, @DrawableRes int drawableRes) {
        current_line_color = line_color;
        current_step_color = step_color;
        current_drawable = drawableRes;
        return this;
    }

    /**
     * 每个步 宽度
     *
     * @param width 宽度
     * @return
     */
    public ConciseStepView stepViewWidth(int width) {
        mStepWidth = width;
        return this;
    }

    /**
     * 生成
     */
    public ConciseStepView build() {
        ViewTreeObserver vto = mHorizontalScrollView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHorizontalScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mWidth = mHorizontalScrollView.getWidth();
                mHeight = mHorizontalScrollView.getHeight();
                if (mStepSize <= 3) {
                    mStepWidth = mWidth / mStepSize;
                }
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mLinearLayout.setLayoutParams(lp);
                mHorizontalScrollView.addView(mLinearLayout);
                initDefaults();
            }
        });
        return this;
    }


    private ConciseStepView initDefaults() {
        mLinearLayout.setBackgroundColor(Color.WHITE);
        int height = mHeight / 3 * 2;

        boolean isFinish;
        // 每一个stepView的layoutParams
        LinearLayout.LayoutParams stepParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mHeight);
        stepParams.width = mStepWidth;
        // 每一个stepView 图片所在布局的layoutParams
        LinearLayout.LayoutParams topViewParams = new LinearLayout.LayoutParams(mStepWidth, height);
        topViewParams.gravity = Gravity.CENTER;
        // 每一个stepView 文字所有布局的layoutParams
        LinearLayout.LayoutParams stepTextParams = new LinearLayout.LayoutParams(mStepWidth, mHeight - height);
        stepTextParams.gravity = Gravity.CENTER;
        // 每一个stepView 图片的layoutParams
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(height, height);
        imageParams.width = height;
        imageParams.height = height;
        imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        // 左边线 layoutParams
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, mLineHeight);
        leftParams.addRule(RelativeLayout.CENTER_VERTICAL);
        // 右边线 layoutParams
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, mLineHeight);
        rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
        ConciseData mStep;
        for (int i = 0; i < mStepSize; i++) {
            mStep = mSteps.get(i);
            isFinish = mStep.isFinish();
            stepLayout = new LinearLayout(mContext);
            stepLayout.setOrientation(LinearLayout.VERTICAL);
            stepLayout.setLayoutParams(stepParams);

            drawableLayout = new RelativeLayout(mContext);
            drawableLayout.setLayoutParams(topViewParams);
            imageView = new ImageView(mContext);
            imageView.setId(R.id.step_image);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setImageResource(isFinish ? current_drawable : default_drawable);
            imageView.setLayoutParams(imageParams);
            drawableLayout.addView(imageView);
            if (i == 0) {
                rightLine = new View(mContext);
                onDrawRight(isFinish, rightLine, rightParams);
            } else if (i == (mStepSize - 1)) {
                leftLine = new View(mContext);
                onDrawLeft(isFinish, leftLine, leftParams);
            } else {
                int left = (i - 1);
                int right = (i + 1);
                boolean _isFinishLeft;
                boolean _isFinishRight;
                _isFinishLeft = mSteps.get(left).isFinish();
                _isFinishRight = mSteps.get(right).isFinish();
                leftLine = new View(mContext);
                onDrawLeft((_isFinishLeft && isFinish), leftLine, leftParams);
                rightLine = new View(mContext);
                onDrawRight((_isFinishRight && isFinish), rightLine, rightParams);
            }

            stepText = new TextView(mContext);
            stepText.setTextColor(isFinish ? mContext.getResources().getColor(current_step_color) : mContext.getResources().getColor(default_step_color));
            stepText.setText(mStep.getName());
            stepText.setMaxLines(1);
            stepText.setTextSize(mTextSize);
            stepTextParams.gravity = Gravity.CENTER;
            stepText.setGravity(Gravity.CENTER);
            stepText.setLayoutParams(stepTextParams);
            stepLayout.addView(drawableLayout);
            stepLayout.addView(stepText);
            stepLayout.setOnClickListener(click);
            stepLayout.setTag(i);
            mLinearLayout.addView(stepLayout);
        }
        return this;
    }

    private void onDrawRight(boolean isFinish, View right, RelativeLayout.LayoutParams rightParams) {
        rightParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        right.setBackgroundColor(isFinish ? mContext.getResources().getColor(current_line_color) : mContext.getResources().getColor(default_line_color));
        right.setLayoutParams(rightParams);
        drawableLayout.addView(right);
    }

    private void onDrawLeft(boolean isFinish, View left, RelativeLayout.LayoutParams leftParams) {
        leftParams.addRule(RelativeLayout.LEFT_OF, imageView.getId());
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        left.setBackgroundColor(isFinish ? mContext.getResources().getColor(current_line_color) : mContext.getResources().getColor(default_line_color));
        left.setLayoutParams(leftParams);
        drawableLayout.addView(left);
    }

    public void update(ArrayList<ConciseData> datas) {
        steps(datas);
        mLinearLayout.removeAllViews();
        initDefaults();
    }
    private ConciseData findData(int index) {
        return mSteps.get(index);
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = Integer.parseInt(v.getTag().toString());
            onStepClickListener.onStepClick(findData(index));
        }
    };

    private int measureDP(int value) {
        int res = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, mContext.getResources().getDisplayMetrics());
        Log.d("res", "measure:" + value + "," + res);
        return res;
    }
}
