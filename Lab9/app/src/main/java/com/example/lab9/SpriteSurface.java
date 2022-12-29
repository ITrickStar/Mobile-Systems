package com.example.lab9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SpriteSurface extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = SpriteSurface.class.getSimpleName();

    private final MainThread thread;
    private final ImageAnimated img;

    public SpriteSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        img = new ImageAnimated(
                BitmapFactory.decodeResource(getResources(), R.drawable.link)
                , 0, 0
                , 30, 30
                , 7, 7);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        thread.setRunning(false);
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            img.draw(canvas);
        } else {
            surfaceDestroyed(getHolder());
        }
    }

    public void update() {
        img.update(System.currentTimeMillis());
    }

    public static class MainThread extends Thread {
        private static final String TAG = MainThread.class.getSimpleName();

        private final SurfaceHolder surfaceHolder;
        private final SpriteSurface gamePanel;
        private boolean running;

        public MainThread(SurfaceHolder surfaceHolder, SpriteSurface gamePanel) {
            super();
            this.surfaceHolder = surfaceHolder;
            this.gamePanel = gamePanel;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @SuppressLint("WrongCall")
        @Override
        public void run() {
            Canvas canvas;
            Log.d(TAG, "Starting game loop");

            while (running) {
                canvas = null;
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        this.gamePanel.update();
                        this.gamePanel.onDraw(canvas);
                    }
                } catch (Exception e) {
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    public class ImageAnimated {
        private final Bitmap bitmap;
        private final Rect sourceRect;
        private final int frameNr;
        private final int framePeriod;
        private final int spriteWidth;
        private final int spriteHeight;
        private final int x;
        private final int y;
        private int currentFrame;
        private long frameTicker;

        public ImageAnimated(Bitmap bitmap, int x, int y, int width, int height, int fps, int frameCount) {
            this.bitmap = bitmap;
            this.x = x;
            this.y = y;
            currentFrame = 0;
            frameNr = frameCount;
            spriteWidth = bitmap.getWidth() / frameCount;
            spriteHeight = bitmap.getHeight();
            sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
            framePeriod = 1000 / fps;
            frameTicker = 0l;
        }

        public void update(long gameTime) {
            if (gameTime > frameTicker + framePeriod) {
                frameTicker = gameTime;
                currentFrame++;
                if (currentFrame >= frameNr) {
                    currentFrame = 0;
                }
            }
            this.sourceRect.left = currentFrame * spriteWidth;
            this.sourceRect.right = this.sourceRect.left + spriteWidth;
        }

        public void draw(Canvas canvas) {
            Rect destRect = new Rect(x, y, x + spriteWidth, y + spriteHeight);
            canvas.drawBitmap(bitmap, sourceRect, destRect, null);

//            Paint paint = new Paint();
//            paint.setARGB(50, 0, 255, 0);
//            canvas.drawRect(20 + (currentFrame * destRect.width()), 150, 20 + (currentFrame * destRect.width()) + destRect.width(), 150 + destRect.height(), paint);
        }
    }
}