package com.drawing.tam.drawing;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.graphics.Bitmap; //type Bitmap
import android.graphics.Canvas; //for type Canvas
import android.graphics.Paint; //for type Paint
import android.graphics.Path; //for drawing path type Path
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

    private Path drawPath; //drawing path
    private Paint drawPaint, canvasPaint; //drawing and canvas paint
    private int paintColor = 0xFF660000; //initial color
    private Canvas drawCanvas; //canvas
    private Bitmap canvasBitmap; //canvas bitmap

    //constructor
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //view given size
        super.onSizeChanged(w, h, oldw, oldh);
        //instantiate the drawing canvas and bitmap with height and width
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { //on finger touch
        //detect the x and y positions to find exact coordinate
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setupDrawing() {
        //instantiating the drawing Path and Paint objects
        drawPath = new Path();
        drawPaint = new Paint();
        //set the initial color
        drawPaint.setColor(paintColor);
        //set the initial path properties
        drawPaint.setAntiAlias(true); //makes smoother
        drawPaint.setStrokeWidth(20); //set size
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        //instantiating the canvas Paint object
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    public void setColor(String newColor) {
        //set color
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);

    }
}
