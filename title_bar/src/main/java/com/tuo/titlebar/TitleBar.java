package com.tuo.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @describe activity or fragment 标题栏 进行统一多样式封装
 * 作者：Tuo on 2018/4/17 10:57
 * 邮箱：839539179@qq.com
 */

public class TitleBar extends RelativeLayout {


    private TextView leftTv;

    private TextView centerTv;

    private TextView rightTv;

    private TextView tvRedDot;

    private View bottomLine;

    private String mLeftText, mCenterText, mRightText;
    private float mLeftTextSize, mCenterTextSize, mRightTextSize;
    private int mLeftTextColor, mCenterTextColor, mRightTextColor;
    private Drawable mLeftDrawable, mCenterDrawable, mRightDrawable;
    private int mLeftDrawablePadding, mRightDrawablePadding;
    private boolean mHasBottomLine;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_title_bar, this);
        leftTv = this.findViewById(R.id.left_tv);
        centerTv = this.findViewById(R.id.center_tv);
        rightTv = this.findViewById(R.id.right_tv);
        tvRedDot = this.findViewById(R.id.tv_red_dot);
        bottomLine = this.findViewById(R.id.bottom_line);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);

        //左边区域
        mLeftText = a.getString(R.styleable.TitleBar_tb_leftText);
        mLeftTextSize = a.getDimension(R.styleable.TitleBar_tb_leftTextSize, 16);
        mLeftTextColor = a.getColor(R.styleable.TitleBar_tb_leftTextColor, Color.WHITE);
        mLeftDrawable = a.getDrawable(R.styleable.TitleBar_tb_leftTextDrawable);
        mLeftDrawablePadding = a.getDimensionPixelOffset(R.styleable.TitleBar_tb_left_drawable_padding, 6);

        //中间区域
        mCenterText = a.getString(R.styleable.TitleBar_tb_centerText);
        mCenterTextSize = a.getDimension(R.styleable.TitleBar_tb_centerTextSize, 20);
        mCenterTextColor = a.getColor(R.styleable.TitleBar_tb_centerTextColor, Color.WHITE);
        mCenterDrawable = a.getDrawable(R.styleable.TitleBar_tb_centerTextDrawable);

        //右边区域
        mRightText = a.getString(R.styleable.TitleBar_tb_rightText);
        mRightTextSize = a.getDimension(R.styleable.TitleBar_tb_rightTextSize, 16);
        mRightTextColor = a.getColor(R.styleable.TitleBar_tb_rightTextColor, Color.WHITE);
        mRightDrawable = a.getDrawable(R.styleable.TitleBar_tb_rightTextDrawable);
        mRightDrawablePadding = a.getDimensionPixelOffset(R.styleable.TitleBar_tb_right_drawable_padding, 6);

        mHasBottomLine = a.getBoolean(R.styleable.TitleBar_tb_has_bottom_line, false);

        a.recycle();

        initVisibility();
        initListener();
        setLeft(mLeftText, mLeftTextSize, mLeftTextColor, mLeftDrawable, mLeftDrawablePadding);
        setCenter(mCenterText, mCenterTextSize, mCenterTextColor, mCenterDrawable);
        setRight(mRightText, mRightTextSize, mRightTextColor, mRightDrawable, mRightDrawablePadding);
    }

    private void initVisibility() {
        bottomLine.setVisibility(mHasBottomLine ? View.VISIBLE : View.GONE);
    }

    /**
     * 监听事件，因为ButterKnife在library中R2.id不等于R.id
     */
    private void initListener() {
        leftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTitleBarListener != null)
                    mOnTitleBarListener.onLeftClick(v);
            }
        });
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTitleBarListener != null)
                    mOnTitleBarListener.onRightClick(v);
            }
        });
    }

    /**
     * 设置左边View
     *
     * @param text
     * @param textSize
     * @param textColor
     * @param drawable
     */
    public void setLeft(CharSequence text, float textSize, int textColor, Drawable drawable, int leftDrawablePadding) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            leftTv.setCompoundDrawables(drawable, null, null, null);
        }
        leftTv.setText(text);
        leftTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        leftTv.setCompoundDrawablePadding(leftDrawablePadding);
        leftTv.setTextColor(textColor);
        leftTv.setClickable(!TextUtils.isEmpty(text) || drawable != null);
        if (text == null && drawable == null) {
            leftTv.setVisibility(GONE);
        }
    }

    /**
     * 设置中间View
     *
     * @param text
     * @param textSize
     * @param textColor
     * @param drawable
     */
    public void setCenter(CharSequence text, float textSize, int textColor, Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            centerTv.setCompoundDrawables(drawable, null, null, null);
        }
        centerTv.setText(text);
        centerTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        centerTv.setTextColor(textColor);
        centerTv.setClickable(!TextUtils.isEmpty(text) || drawable != null);
    }


    /**
     * 设置右边view
     *
     * @param text
     * @param textSize
     * @param textColor
     * @param drawable
     */
    public void setRight(CharSequence text, float textSize, int textColor, Drawable drawable, int rightDrawablePadding) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            rightTv.setCompoundDrawables(null, null, drawable, null);
        }
        rightTv.setText(text);
        rightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        rightTv.setCompoundDrawablePadding(rightDrawablePadding);
        rightTv.setTextColor(textColor);
        rightTv.setClickable(!TextUtils.isEmpty(text) || drawable != null);
        if (text == null && drawable == null) {
            rightTv.setVisibility(GONE);
        }
    }

    /**
     * 设置红点显示隐藏
     *
     * @param visibility
     */
    public void setRedDotVisibility(int visibility) {
        tvRedDot.setVisibility(visibility);
    }

    //--------------------------- 监听接口 ------------------------------
    private OnTitleBarListener mOnTitleBarListener;

    public void setOnTitleBarListener(OnTitleBarListener l) {
        this.mOnTitleBarListener = l;
    }

    public interface OnTitleBarListener {
        void onLeftClick(View v);

        void onRightClick(View v);
    }

    public TextView getLeftTv() {
        return leftTv;
    }

    public TextView getCenterTv() {
        return centerTv;
    }

    public TextView getRightTv() {
        return rightTv;
    }

}