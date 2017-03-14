package com.koala.bupt.kview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.koala.bupt.kview.R;

/**
 * Created by machenike on 2017/3/13.
 */

public class ShopButton extends View {
    Paint addPaint;
    Paint delPaint;
    Path addPath;
    Path delPath;
    Region addRegion;
    Region delRegion;
    Paint numTextPaint;
    Paint.FontMetrics mFontMetrics;

    Paint buttonPaint;
    Paint buttonTextPaint;

    float buttonParam;
    ValueAnimator expandAnim;
    ValueAnimator reduceAnim;

    float numParam;
    ValueAnimator addAnim;
    ValueAnimator delAnim;

    boolean isButtonMode = false;

    int buttonBgColor;
    int buttonTextColor;
    int buttonTextSize;
    String buttonText;
    float radius;
    float gap;
    int numTextColor;
    int numTextSize;

    public ShopButton(Context context) {
        super(context);
    }

    public ShopButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShopButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initDefault(context);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShopButton,defStyleAttr,0);
        isButtonMode = array.getBoolean(R.styleable.ShopButton_buttonMode,true);
        buttonBgColor = array.getColor(R.styleable.ShopButton_buttonBgColor,0xff0099ff);
        buttonText = array.getString(R.styleable.ShopButton_buttonText);
        buttonTextColor = array.getColor(R.styleable.ShopButton_buttonTextColor,buttonTextColor);

        addPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        delPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    }

    private void initDefault(Context context) {
        isButtonMode = true;
        buttonBgColor = 0xff0099ff;
        buttonText = "添加购物车";
        buttonTextColor = 0xffffffff;
        buttonTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,14,context.getResources().getDisplayMetrics());
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,14,context.getResources().getDisplayMetrics());
        gap = 5*radius;
        numTextColor = 0xff000000;
        numTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,18,context.getResources().getDisplayMetrics());

    }
}
