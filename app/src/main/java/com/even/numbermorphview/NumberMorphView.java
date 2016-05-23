package com.even.numbermorphview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * NumberMorphView for Android
 * Created by even on 16/5/17.
 */
public class NumberMorphView extends View {
    private float screenWidth = 0;
    private float screenHeight = 0;

    //private properties
    private float[][][] endpoints_original = new float[10][5][2];
    private float[][][] controlPoints1_original = new float[10][4][2];
    private float[][][] controlPoints2_original = new float[10][4][2];

    private float[][][] endpoints_scaled = new float[10][5][2];
    private float[][][] controlPoints1_scaled = new float[10][4][2];
    private float[][][] controlPoints2_scaled = new float[10][4][2];

    private Paint paint;
    private Path path;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private ArrayList<Path> paths = new ArrayList<>();

    private int currentIndex = 0;
    private int priorNum = 0;
    private double currentFrame = 0;
    private double period = 1000;
    protected boolean isFinished = false;

    public Interpolator interpolator = new OvershootInterpolator(1.3f);

    public NumberMorphView(Context context) {
        this(context, null);
    }

    public NumberMorphView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberMorphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle(context, attrs);
        init();
    }

    private void initStyle(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberMorphView);
        paint = new Paint();
        path = new Path();

        int numColor = typedArray.getColor(R.styleable.NumberMorphView_numColor, Color.BLACK);
        int backgroundColor = typedArray.getColor(R.styleable.NumberMorphView_numBackgroundColor, Color.TRANSPARENT);
        float strokeWidth = typedArray.getDimension(R.styleable.NumberMorphView_strokeWidth, 10);
        int capValue = typedArray.getInteger(R.styleable.NumberMorphView_strokeCap, 1);
        int joinValue = typedArray.getInteger(R.styleable.NumberMorphView_strokeJoin, 1);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(getCapFromInt(capValue));
        paint.setStrokeJoin(getJoinFromInt(joinValue));
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(numColor);

        setBackgroundColor(backgroundColor);

        typedArray.recycle();
    }

    private Paint.Cap getCapFromInt(int value) {
        Paint.Cap cap;
        switch (value) {
            case 0:
                cap = Paint.Cap.BUTT;
                break;
            case 1:
                cap = Paint.Cap.ROUND;
                break;
            case 2:
                cap = Paint.Cap.SQUARE;
                break;
            default:
                cap = Paint.Cap.ROUND;
        }
        return cap;
    }

    private Paint.Join getJoinFromInt(int value) {
        Paint.Join join;
        switch (value) {
            case 0:
                join = Paint.Join.MITER;
                break;
            case 1:
                join = Paint.Join.ROUND;
                break;
            case 2:
                join = Paint.Join.BEVEL;
                break;
            default:
                join = Paint.Join.ROUND;
        }
        return join;
    }

    private void init() {
        endpoints_original[0] = new float[][]{{500f, 800f}, {740f, 400f}, {500f, 0f}, {260f, 400f}, {500f, 800f}};
        endpoints_original[1] = new float[][]{{383f, 712f}, {500f, 800f}, {500f, 0f}, {500f, 800f}, {383f, 712f}};
        endpoints_original[2] = new float[][]{{300f, 640f}, {700f, 640f}, {591f, 369f}, {300f, 0f}, {700f, 0f}};
        endpoints_original[3] = new float[][]{{300f, 600f}, {700f, 600f}, {500f, 400f}, {700f, 200f}, {300f, 200f}};
        endpoints_original[4] = new float[][]{{650f, 0f}, {650f, 140f}, {650f, 800f}, {260f, 140f}, {760f, 140f}};
        endpoints_original[5] = new float[][]{{645f, 800f}, {400f, 800f}, {300f, 480f}, {690f, 285f}, {272f, 92f}};
        endpoints_original[6] = new float[][]{{640f, 800f}, {321f, 458f}, {715f, 144f}, {257f, 146f}, {321f, 458f}};
        endpoints_original[7] = new float[][]{{275f, 800f}, {725f, 800f}, {586f, 544f}, {424f, 262f}, {275f, 0f}};
        endpoints_original[8] = new float[][]{{500f, 400f}, {500f, 0f}, {500f, 400f}, {500f, 800f}, {500f, 400f}};
        endpoints_original[9] = new float[][]{{679f, 342f}, {743f, 654f}, {285f, 656f}, {679f, 342f}, {360f, 0f}};

        controlPoints1_original[0] = new float[][]{{650f, 800f}, {740f, 200f}, {350f, 0f}, {260f, 600f}};
        controlPoints1_original[1] = new float[][]{{383f, 712f}, {500f, 488f}, {500f, 488f}, {383f, 712f}};
        controlPoints1_original[2] = new float[][]{{335f, 853f}, {710f, 538f}, {477f, 213f}, {450f, 0f}};
        controlPoints1_original[3] = new float[][]{{300f, 864f}, {700f, 400f}, {500f, 400f}, {700f, -64f}};
        controlPoints1_original[4] = new float[][]{{650f, 50f}, {650f, 340f}, {502f, 572f}, {350f, 140f}};
        controlPoints1_original[5] = new float[][]{{550f, 800f}, {400f, 800f}, {495f, 567f}, {717f, 30f}};
        controlPoints1_original[6] = new float[][]{{578f, 730f}, {492f, 613f}, {634f, -50f}, {208f, 264f}};
        controlPoints1_original[7] = new float[][]{{350f, 800f}, {676f, 700f}, {538f, 456f}, {366f, 160f}};
        controlPoints1_original[8] = new float[][]{{775f, 400f}, {225f, 0f}, {225f, 400f}, {775f, 800f}};
        controlPoints1_original[9] = new float[][]{{746f, 412f}, {662f, 850f}, {164f, 398f}, {561f, 219f}};

        controlPoints2_original[0] = new float[][]{{740f, 600f}, {650f, 0f}, {260f, 200f}, {350f, 800f}};
        controlPoints2_original[1] = new float[][]{{500f, 800f}, {500f, 312f}, {500f, 312f}, {500f, 800f}};
        controlPoints2_original[2] = new float[][]{{665f, 853f}, {658f, 461f}, {424f, 164f}, {544f, 1f}};
        controlPoints2_original[3] = new float[][]{{700f, 864f}, {500f, 400f}, {700f, 400f}, {300f, -64f}};
        controlPoints2_original[4] = new float[][]{{650f, 100f}, {650f, 600f}, {356f, 347f}, {680f, 140f}};
        controlPoints2_original[5] = new float[][]{{450f, 800f}, {300f, 480f}, {675f, 460f}, {410f, -100f}};
        controlPoints2_original[6] = new float[][]{{455f, 602f}, {840f, 444f}, {337f, -46f}, {255f, 387f}};
        controlPoints2_original[7] = new float[][]{{500f, 800f}, {634f, 631f}, {487f, 372f}, {334f, 102f}};
        controlPoints2_original[8] = new float[][]{{775f, 0f}, {225f, 400f}, {225f, 800f}, {775f, 400f}};
        controlPoints2_original[9] = new float[][]{{792f, 536f}, {371f, 840f}, {475f, 195f}, {432f, 79f}};

        for (int digit = 0; digit < 10; digit++) {
            for (int pointIndex = 0; pointIndex < 5; pointIndex++) {
                endpoints_original[digit][pointIndex][1] = 800 - endpoints_original[digit][pointIndex][1];

                if (pointIndex < 4) {
                    controlPoints1_original[digit][pointIndex][1] = 800 - controlPoints1_original[digit][pointIndex][1];
                    controlPoints2_original[digit][pointIndex][1] = 800 - controlPoints2_original[digit][pointIndex][1];
                }
            }
        }
    }

    private void scalePoints(float width, float height) {
        for (int digit = 0; digit < 10; digit++) {
            for (int pointIndex = 0; pointIndex < 5; pointIndex++) {
                endpoints_scaled[digit][pointIndex][0] = (float) (endpoints_original[digit][pointIndex][0] / 1000.0 *
                        1.4 - 0.2) * width;
                endpoints_scaled[digit][pointIndex][1] = (float) (endpoints_original[digit][pointIndex][1] / 800.0 *
                        0.6 + 0.2) * height;

                if (pointIndex < 4) {
                    controlPoints1_scaled[digit][pointIndex][0] = (float)
                            (controlPoints1_original[digit][pointIndex][0] / 1000.0 * 1.4 - 0.2) * width;
                    controlPoints1_scaled[digit][pointIndex][1] = (float)
                            (controlPoints1_original[digit][pointIndex][1] / 800.0 * 0.6 + 0.2) * height;

                    controlPoints2_scaled[digit][pointIndex][0] = (float)
                            (controlPoints2_original[digit][pointIndex][0] / 1000.0 * 1.4 - 0.2) * width;
                    controlPoints2_scaled[digit][pointIndex][1] = (float)
                            (controlPoints2_original[digit][pointIndex][1] / 800.0 * 0.6 + 0.2) * height;
                }
            }
        }
    }

    private void initializePaths() {
        if (paths == null) {
            paths = new ArrayList<>();
        }
        paths.clear();

        for (int digit = 0; digit < 10; digit++) {
            Path path = new Path();
            float[][] p = endpoints_scaled[digit];
            float[][] cp1 = controlPoints1_scaled[digit];
            float[][] cp2 = controlPoints2_scaled[digit];

            path.moveTo(p[0][0], p[0][1]);
            for (int i = 1; i < 5; i++) {
                path.cubicTo(cp1[i - 1][0], cp1[i - 1][1], cp2[i - 1][0], cp2[i - 1][1], p[i][0], p[i][1]);
            }
            paths.add(path);
        }
    }

    private void updateAnimationFrame() {
        float[][] pCur = endpoints_scaled[priorNum];
        float[][] cp1Cur = controlPoints1_scaled[priorNum];
        float[][] cp2Cur = controlPoints2_scaled[priorNum];

        int nextIndex = currentIndex;
        float[][] pNext = endpoints_scaled[nextIndex];
        float[][] cp1Next = controlPoints1_scaled[nextIndex];
        float[][] cp2Next = controlPoints2_scaled[nextIndex];

        path.reset();
        float factor = interpolator.getInterpolation((float) (currentFrame >= 30 ? 30 : currentFrame) / 30f);

        path.moveTo(pCur[0][0] + (pNext[0][0] - pCur[0][0]) * factor, pCur[0][1] + (pNext[0][1] - pCur[0][1]) * factor);
        for (int i = 1; i < 5; i++) {
            float ex = pCur[i][0] + (pNext[i][0] - pCur[i][0]) * factor;
            float ey = pCur[i][1] + (pNext[i][1] - pCur[i][1]) * factor;

            int iMinus = i - 1;
            float cp1x = cp1Cur[iMinus][0] + (cp1Next[iMinus][0] - cp1Cur[iMinus][0]) * factor;
            float cp1y = cp1Cur[iMinus][1] + (cp1Next[iMinus][1] - cp1Cur[iMinus][1]) * factor;

            float cp2x = cp2Cur[iMinus][0] + (cp2Next[iMinus][0] - cp2Cur[iMinus][0]) * factor;
            float cp2y = cp2Cur[iMinus][1] + (cp2Next[iMinus][1] - cp2Cur[iMinus][1]) * factor;

            path.cubicTo(cp1x, cp1y, cp2x, cp2y, ex, ey);
        }
        currentFrame += period;

        if (currentFrame >= 30) {
            drawDigitWithoutAnimation(nextIndex);
        }
    }

    protected void drawDigitWithoutAnimation(int currentDigit) {
        float[][] p = endpoints_scaled[currentDigit];
        float[][] cp1 = controlPoints1_scaled[currentDigit];
        float[][] cp2 = controlPoints2_scaled[currentDigit];

        path.reset();

        path.moveTo(p[0][0], p[0][1]);
        for (int i = 1; i < 5; i++) {
            path.cubicTo(cp1[i - 1][0], cp1[i - 1][1], cp2[i - 1][0], cp2[i - 1][1], p[i][0], p[i][1]);
        }
    }

    protected void updateNumber() {
        if (currentFrame <= 30) {
            postInvalidate();
        } else {
            currentFrame += period;
            if (currentFrame >= 60) {
                isFinished = true;
                currentFrame = 0;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (screenWidth != getWidth() || screenHeight != getHeight()) {
            screenWidth = getWidth();
            screenHeight = getHeight();
            scalePoints(screenWidth, screenHeight);
            initializePaths();
        }

        updateAnimationFrame();
        canvas.drawPath(path, paint);
        super.onDraw(canvas);
    }

    protected int getCurrentIndex() {
        return currentIndex;
    }

    protected void setCurrentIndex(int currentIndex) {
        priorNum = this.currentIndex;
        this.currentIndex = currentIndex;
    }

    @SuppressWarnings("unused")
    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }

    /**
     * --------------------Interpolator-------------------
     **/
    public interface Interpolator {
        float getInterpolation(float x);
    }

    public static class LinearInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float x) {
            return x;
        }
    }

    public static class OvershootInterpolator implements Interpolator {
        private float tension;

        public OvershootInterpolator() {
            this(2.0f);
        }

        public OvershootInterpolator(float tension) {
            this.tension = tension;
        }

        @Override
        public float getInterpolation(float x) {
            float x2 = x - 1.0f;
            return x2 * x2 * ((tension + 1) * x2 + tension) + 1.0f;
        }
    }

    public static class SpringInterpolator implements Interpolator {
        private float tension;
        private static final float PI = 3.141592653f;

        public SpringInterpolator() {
            this(0.3f);
        }

        public SpringInterpolator(float tension) {
            this.tension = tension;
        }

        @Override
        public float getInterpolation(float x) {
            return (float) (Math.pow(2, -10 * x) * Math.sin((x - tension / 4) * (2 * PI) / tension) + 1);

        }
    }

    public static class BounceInterpolator implements Interpolator {
        public BounceInterpolator() {
        }

        private float bounce(float t) {
            return t * t * 8;
        }

        @Override
        public float getInterpolation(float x) {
            if (x < 0.3535) {
                return bounce(x);
            } else if (x < 0.7408) {
                return bounce(x - 0.54719f) + 0.7f;
            } else if (x < 0.9644) {
                return bounce(x - 0.8526f) + 0.9f;
            } else {
                return bounce(x - 1.0435f) + 0.95f;
            }
        }
    }

    public static class AnticipateOvershootInterpolator implements Interpolator {
        private float tension;

        public AnticipateOvershootInterpolator() {
            this(2.0f);
        }

        public AnticipateOvershootInterpolator(float tension) {
            this.tension = tension;
        }

        private float anticipate(float x, float tension) {
            return x * x * ((tension + 1) * x - tension);
        }

        private float overshoot(float x, float tension) {
            return x * x * ((tension + 1) * x + tension);
        }

        @Override
        public float getInterpolation(float x) {
            if (x < 0.5) {
                return 0.5f * anticipate(x * 2.0f, tension);
            } else {
                return 0.5f * (overshoot(x * 2.0f - 2.0f, tension) + 2.0f);
            }
        }
    }

    public static class CubicHermiteInterpolator implements Interpolator {
        private float tangent0;
        private float tangent1;

        public CubicHermiteInterpolator() {
            this(2.2f, 2.2f);
        }

        public CubicHermiteInterpolator(float tangent0, float tangent1) {
            this.tangent0 = tangent0;
            this.tangent1 = tangent1;
        }

        private float cubicHermite(float t, float start, float end, float tangent0, float tangent1) {
            float t2 = t * t;
            float t3 = t2 * t;
            return (2 * t3 - 3 * t2 + 1) * start + (t3 - 2 * t2 + t) * tangent0 + (-2 * t3 + 3 * t2) * end + (t3 -
                    t2) * tangent1;
        }

        @Override
        public float getInterpolation(float x) {
            return cubicHermite(x, 0, 1, tangent0, tangent1);

        }
    }
}