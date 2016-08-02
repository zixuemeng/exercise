package com.joany.librarytitanic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by joany on 2016/7/26.
 */
public class TitanicTextView extends TextView {

    private Matrix matrix;
    private Drawable wave;
    /**
     * wave 顶部y坐标
     */
    private float offsetY;
    private BitmapShader bitmapShader;
    private float maskX, maskY;

    private AnimationSetupCallback animationSetupCallback;

    public interface AnimationSetupCallback {
        void onSetupAnimation(TitanicTextView titanicTextView);
    }

    public void setAnimationSetupCallback(AnimationSetupCallback animationSetupCallback) {
        this.animationSetupCallback = animationSetupCallback;
    }

    /**
     * 动画过程中更新maskX值
     * @param maskX
     */
    public void setMaskX(float maskX) {
        this.maskX = maskX;
        invalidate();
    }

    public void setMaskY(float maskY) {
        this.maskY = maskY;
        invalidate();
    }

    public TitanicTextView(Context context) {
        this(context,null);
    }

    public TitanicTextView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TitanicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        matrix = new Matrix();
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        createShader();
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);
        createShader();
    }

    private void createShader() {

        if(wave == null) {
            wave = getResources().getDrawable(R.drawable.wave);
        }
        int waveW = wave.getIntrinsicWidth();
        int waveH = wave.getIntrinsicHeight();

        Bitmap b = Bitmap.createBitmap(waveW, waveH, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        c.drawColor(getCurrentTextColor());

        wave.setBounds(0, 0, waveW, waveH);
        wave.draw(c);

        bitmapShader = new BitmapShader(b, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        getPaint().setShader(bitmapShader);

        offsetY = (getHeight() - waveH)/2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createShader();
        if(animationSetupCallback != null) {
            animationSetupCallback.onSetupAnimation(TitanicTextView.this);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(bitmapShader != null) {
            if(getPaint().getShader() == null) {
                getPaint().setShader(bitmapShader);
            }

            matrix.setTranslate(maskX,maskY+offsetY);

            bitmapShader.setLocalMatrix(matrix);
        } else {
            getPaint().setShader(null);
        }

        super.onDraw(canvas);
    }
}
