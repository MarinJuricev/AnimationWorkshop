package netovski.test.animationworkshop;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;


public class SimpleLoader extends View {

    private Paint mBackgroundPaint = new Paint();
    private Paint mFillPaint = new Paint();
    private RectF bounds = new RectF();

    ValueAnimator progressAnimator;
    ValueAnimator bounceAnimatior;

    private final float radiusDelta = 0.17f;
    private final float radiusPadding = 0.25f;

    private float mStrokeWidth;
    private float DEFAULT_STROKE_WIDTH = 4;
    private float radius;
    private float centerX;
    private float centerY;
    private float scaleCirclePercentage = 1;
    private float progress = 0.0f;
    private float startProgress = -90f;

    public SimpleLoader(Context context) {
        super(context);
        init();
    }

    public SimpleLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithXml(context, attrs);
    }

    public SimpleLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithXml(context, attrs);
    }

    public SimpleLoader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithXml(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(bounds, 0, 360f, false, mBackgroundPaint);
        canvas.drawArc(bounds, startProgress, progress, false, mFillPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        radius = Math.min(h, w) / 2.0f;
        centerX = w / 2.0f;
        centerY = h / 2.0f;

        bounds.set(centerX - radius + radiusPadding * radius + radiusDelta * radius * scaleCirclePercentage,
                centerY - radius + radiusPadding * radius + radiusDelta * radius * scaleCirclePercentage,
                centerX + radius - radiusPadding * radius - radiusDelta * radius * scaleCirclePercentage,
                centerY + radius - radiusPadding * radius - radiusDelta * radius * scaleCirclePercentage);
    }

    public void init() {
        mBackgroundPaint.setAntiAlias(true);
        mFillPaint.setAntiAlias(true);

        mBackgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        mFillPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mFillPaint.setStyle(Paint.Style.STROKE);
        // TODO Change to dp
        mBackgroundPaint.setStrokeWidth(10f);
        mFillPaint.setStrokeWidth(10f);
    }

    public void bounce(){

        bounceAnimatior = ValueAnimator.ofFloat(1.0f, 0.8f, 0.26f, 0.89f);

        bounceAnimatior.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                setScaleCirclePercentage(scale);
            }
        });

        bounceAnimatior.setRepeatMode(ValueAnimator.REVERSE);
        bounceAnimatior.setRepeatCount(ValueAnimator.INFINITE);

        bounceAnimatior.setDuration(1000);

        bounceAnimatior.start();
    }

    void setScaleCirclePercentage(float percentage){
        if (percentage < 0){
            percentage = 0;
        }else if (percentage > 1){
            percentage = 1;
        }
        this.scaleCirclePercentage = percentage;
        bounds.set(centerX - radius + radiusPadding*radius + radiusDelta*radius*scaleCirclePercentage,
                centerY - radius + radiusPadding*radius + radiusDelta*radius*scaleCirclePercentage,
                centerX + radius - radiusPadding*radius - radiusDelta*radius*scaleCirclePercentage,
                centerY + radius - radiusPadding*radius - radiusDelta*radius*scaleCirclePercentage);
        invalidate();
    }

    public void startProgressAnimation() {
        progressAnimator = progressAnimator.ofFloat(0f, 360f);

        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        progressAnimator.setDuration(5000);
        progressAnimator.start();
    }

    public void initWithXml(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SimpleLoader,
                0, 0);

        try {
            float density = getResources().getDisplayMetrics().density;
            mStrokeWidth = a.getDimension(R.styleable.SimpleLoader_loader_border_width, density * DEFAULT_STROKE_WIDTH);
        } finally {
            a.recycle();
        }
    }
}
