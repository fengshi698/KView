package com.koala.bupt.kview.ShopButton;

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

    boolean isButtonMode = true;
    boolean isTextShow = true;

    int buttonBgColor;
    int buttonTextColor;
    int buttonTextSize;
    String buttonText;
    float radius;
    float gap;
    int numTextColor;
    int numTextSize;
    int delColor;
    int lineWidth;
    private int count = 0;


    private int width;
    private int heigth;

    public ShopButton(Context context) {
        super(context);
        initDefault(context);
    }

    public ShopButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefault(context);
    }

    public ShopButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initDefault(context);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShopButton, defStyleAttr, 0);
        isButtonMode = array.getBoolean(R.styleable.ShopButton_buttonMode, true);
        buttonBgColor = array.getColor(R.styleable.ShopButton_buttonBgColor, 0xff0099ff);
        buttonText = array.getString(R.styleable.ShopButton_buttonText);
        buttonTextColor = array.getColor(R.styleable.ShopButton_buttonTextColor, buttonTextColor);
        buttonTextSize = (int) array.getDimension(R.styleable.ShopButton_buttonTextSize, buttonTextSize);
        radius = array.getDimension(R.styleable.ShopButton_radius, radius);
        gap = array.getDimension(R.styleable.ShopButton_gapBetweenCircle, gap);
        numTextColor = array.getColor(R.styleable.ShopButton_numTextColor, numTextColor);
        numTextSize = (int) array.getDimension(R.styleable.ShopButton_numTextSize, numTextSize);

        initAnim();
    }

    private void initAnim() {
        addAnim = ValueAnimator.ofFloat(1, 0);
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

        addAnim.setDuration(300);

        reduceAnim = ValueAnimator.ofFloat(0, 1);
        reduceAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                buttonParam = animation.getAnimatedFraction();
                invalidate();
            }
        });
        reduceAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isTextShow = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (count > 0) {
                    isButtonMode = false;
                    if (addAnim != null && !addAnim.isRunning()) {
                        addAnim.start();
                    }
                }
            }
        });
        reduceAnim.setDuration(300);


        expandAnim = ValueAnimator.ofFloat(1, 0);
        expandAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                buttonParam = animation.getAnimatedFraction();
                invalidate();
            }
        });

        expandAnim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (count == 0) {
                    isTextShow = true;
                    isButtonMode = true;
                }

            }
        });
        expandAnim.setDuration(300);

        delAnim = ValueAnimator.ofFloat(0, 1);
        delAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                numParam = animation.getAnimatedFraction();
                invalidate();
            }
        });
        delAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (count == 0) {
                    addAnim.start();
                }
            }
        });
        delAnim.setDuration(300);
    }

    private void initDefault(Context context) {
        isButtonMode = true;
        buttonBgColor = 0xff0099ff;
        buttonText = "添加购物车";
        buttonTextColor = 0xffffffff;
        buttonTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics());
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics());
        gap = 5 * radius;
        numTextColor = 0xff000000;
        numTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, context.getResources().getDisplayMetrics());
        delColor = 0xff999999;
        lineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
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
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                gap = wSize - 4 * radius - getPaddingLeft() - getPaddingRight();
                break;
            case MeasureSpec.AT_MOST:
                int compute = (int) (getPaddingLeft() + radius * 2 + gap + radius * 2 + getPaddingRight());
                wSize = Math.max(wSize, compute);
                break;
            case MeasureSpec.UNSPECIFIED:
                compute = (int) (getPaddingLeft() + radius * 2 + gap + radius * 2 + getPaddingRight());
                wSize = Math.max(wSize, compute);
                break;
            default:
                break;
        }

        switch (hMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                int compute = (int) (getPaddingTop() + radius * 2 + getPaddingBottom());
                hSize = Math.min(hSize, compute);
                break;
            case MeasureSpec.UNSPECIFIED:
                compute = (int) (getPaddingTop() + radius * 2 + getPaddingBottom());
                hSize = Math.min(hSize, compute);
                break;
            default:
                break;
        }

        setMeasuredDimension(wSize, hSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isButtonMode) {
            buttonPaint.setStyle(Paint.Style.FILL);
            buttonPaint.setColor(buttonBgColor);
            canvas.drawRoundRect(new RectF(getPaddingLeft()+buttonParam*(radius*2+gap), getPaddingTop(), width, heigth), 12, 12, buttonPaint);
            if (isTextShow) {
                buttonTextPaint.setStyle(Paint.Style.STROKE);
                buttonTextPaint.setColor(buttonTextColor);
                buttonTextPaint.setTextSize(buttonTextSize);
                canvas.drawText(buttonText, width / 2 - buttonTextPaint.measureText(buttonText) / 2,
                        heigth / 2 - buttonTextPaint.ascent() / 2 - buttonTextPaint.descent()/2, buttonTextPaint);
            }
        } else {
            delPaint.setColor(delColor);
            delPaint.setStyle(Paint.Style.STROKE);
            delPaint.setStrokeWidth(lineWidth);
            delPath.addCircle(getPaddingLeft()+radius,getPaddingTop()+radius,radius, Path.Direction.CW);
            delRegion.setPath(delPath,new Region(getPaddingLeft(),getPaddingTop(),width-getPaddingRight(),heigth-getPaddingTop()));
            canvas.drawPath(delPath,delPaint);
            canvas.save();
            canvas.translate(getPaddingLeft()+radius,getPaddingTop()+radius);
            canvas.drawLine(-radius/2,0,radius/2,0,delPaint);
            canvas.restore();
        }
    }
}
