package com.koala.bupt.kview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
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


    private int width;
    private int heigth;

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
        buttonTextSize = (int) array.getDimension(R.styleable.ShopButton_buttonTextSize,buttonTextSize);
        radius = array.getDimension(R.styleable.ShopButton_radius,radius);
        gap = array.getDimension(R.styleable.ShopButton_gapBetweenCircle,gap);
        numTextColor = array.getColor(R.styleable.ShopButton_numTextColor,numTextColor);
        numTextSize = (int) array.getDimension(R.styleable.ShopButton_numTextSize,numTextSize);

        addPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        delPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        addPath = new Path();
        delPath = new Path();
        addRegion = new Region();
        delRegion = new Region();
        numTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFontMetrics = numTextPaint.getFontMetrics();
        buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        buttonTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        initAnim();
    }

    private void initAnim() {
        addAnim = ValueAnimator.ofFloat(1,0);
        numParam = 1;
        addAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                numParam = animation.getAnimatedFraction();
            }
        });

        addAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                invalidate();
            }
        });

        expandAnim = ValueAnimator.ofFloat(1,0);
        buttonParam = 1;
        expandAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                buttonParam = animation.getAnimatedFraction();
            }
        });

        expandAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                invalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                addAnim.start();
            }
        });

        reduceAnim = ValueAnimator.ofFloat(0,1);
        reduceAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                buttonParam = animation.getAnimatedFraction();
            }
        });

        reduceAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                invalidate();
            }
        });
        delAnim = ValueAnimator.ofFloat(0,1);
        delAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                numParam = animation.getAnimatedFraction();
            }
        });
        delAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                invalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                reduceAnim.start();
            }
        });


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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        heigth = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (wMode){
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                int compute = (int) (getPaddingLeft()+radius*2+gap+radius*2+getPaddingRight());
                wSize = Math.min(wSize,compute);
                break;
            case MeasureSpec.UNSPECIFIED:
                compute = (int) (getPaddingLeft()+radius*2+gap+radius*2+getPaddingRight());
                wSize = Math.min(wSize,compute);
                break;
            default:
                break;
        }

        switch (hMode){
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                int compute = (int) (getPaddingTop()+radius*2+getPaddingBottom());
                hSize = Math.min(hSize,compute);
                break;
            case MeasureSpec.UNSPECIFIED:
                compute = (int) (getPaddingTop()+radius*2+getPaddingBottom());
                hSize = Math.min(hSize,compute);
                break;
            default:
                break;
        }

        setMeasuredDimension(wSize,hSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isButtonMode){
            buttonPaint.setStyle(Paint.Style.FILL);
            buttonPaint.setColor(buttonBgColor);
            canvas.drawRoundRect(new RectF(getPaddingLeft(),getPaddingTop(),width,heigth),6,6,buttonPaint);
            buttonTextPaint.setStyle(Paint.Style.STROKE);
            buttonTextPaint.setColor(buttonTextColor);
            canvas.drawText(buttonText,width/2-buttonPaint.measureText(buttonText)/2,heigth/2-buttonTextPaint.ascent()/2-buttonTextPaint.descent(),buttonTextPaint);
        }
    }
}
