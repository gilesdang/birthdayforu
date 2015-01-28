package com.augmentum.giles.happyforu.com.fireview;

import java.io.InputStream;
import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.augmentum.giles.happyforu.R;
import com.augmentum.giles.happyforu.com.dot.Dot;
import com.augmentum.giles.happyforu.com.dot.DotFactory;
import com.augmentum.giles.happyforu.com.dot.LittleDot;

public class MyView extends View {

    final String LOG_TAG = MyView.class.getSimpleName();

    public static final int ID_SOUND_UP = 0;
    public static final int ID_SOUND_BLOW = 1;
    public static final int ID_SOUND_MULTIPLE = 2;
    private int WIDTH = 480;
    private int HEIGHT = 800;
    final static int TIME = 5; // Ȧ��

    private Vector<Dot> lList = new Vector<Dot>();

    LittleDot[] ld = new LittleDot[200];
    private DotFactory df = null;

    boolean running = true;

    Bitmap backGroundBitmap;

    Context mContext;

    public static SoundPlay soundPlay;

    public MyView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        WIDTH= wm.getDefaultDisplay().getWidth();
        HEIGHT = wm.getDefaultDisplay().getHeight();
        df = new DotFactory();
        new MyThread().start();
        mContext = context;
        backGroundBitmap = ReadBitMap(mContext, R.drawable.night);
        backGroundBitmap = resizeImage(backGroundBitmap, WIDTH, HEIGHT);
        initSound(mContext);
    }

    public MyView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        WIDTH= wm.getDefaultDisplay().getWidth();
        HEIGHT = wm.getDefaultDisplay().getHeight();
        df = new DotFactory();
        new MyThread().start();
        mContext = context;
        backGroundBitmap = ReadBitMap(mContext, R.drawable.night);
        backGroundBitmap = resizeImage(backGroundBitmap, WIDTH, HEIGHT);
        initSound(mContext);

    }

    public static void initSound(Context context) {
        soundPlay = new SoundPlay();
        soundPlay.initSounds(context);
        soundPlay.loadSfx(context, R.raw.up, ID_SOUND_UP);
        soundPlay.loadSfx(context, R.raw.blow, ID_SOUND_BLOW);
        soundPlay.loadSfx(context, R.raw.multiple, ID_SOUND_MULTIPLE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(backGroundBitmap, 0, 0, null);
        synchronized (lList) {
            for (int i = 0; i < lList.size(); i++) {
                lList.get(i).myPaint(canvas, lList);
            }
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Dot dot = null;
            int rand = (int) (Math.random() * 99);

            dot = df.makeDot(mContext, rand, (int) event.getX(),
                    (int) event.getY());
            synchronized (lList) {
                lList.add(dot);
                soundPlay.play(ID_SOUND_UP, 0);
            }
        }

        return true;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Bitmap ReadBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // ��ȡ��ԴͼƬ
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public Bitmap resizeImage(Bitmap mBitmap, int w, int h) {
        Bitmap BitmapOrg = mBitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap tmp = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height,
                matrix, true);
        return tmp;
    }

    class MyThread extends Thread {
        // �½�һ��������������ػ�
        // ���ڿ����̻��ڿ���������ʱ��
        int times = 0;

        public void run() {
            Dot dot = null;
            while (running) {

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    System.out.println(e);
                }

                synchronized (lList) {
                    // ��ֹ������̻��������50��
                    while (lList.size() > 50) {
                        System.out.println("��ǰ��Ŀ����50");
                        for (int i = 0; i < 10; i++) {
                            lList.remove(i);
                        }
                    }

                    // �Զ�����̻�
                    if (lList.size() <= 2) {
                        Dot tmp = null;
                        int rand = (int) (Math.random() * 99);
                        Random random = new Random();
                        tmp = df.makeDot(mContext, rand, random.nextInt(WIDTH),
                                50 + random.nextInt(300));
                        lList.add(tmp);
                    }

                }

                for (int i = 0; i < lList.size(); i++) {
                    dot = (Dot) lList.get(i);
                    if (dot.state == 1 && !dot.whetherBlast()) {
                        dot.rise();
                    }
                    // �����whetherBlast()���ص���true����ô�ͰѸ�dot��state����Ϊ2
                    else if (dot.state == 1 && dot.state != 2) {
                        dot.state = 2;
                        soundPlay.play(ID_SOUND_BLOW, 0);
                    } else if (dot.state == 3) {

                    }
                    // �涨��ÿ����ը�������TIMEȦ������ͻ���ʧ
                    if (dot.circle >= TIME) {
                        // �ڿ�������һ�����ʧ
                        if (times >= 10) {
                            dot.state = 4;
                            times = 0;
                        } else {
                            times++;
                        }
                        // dot.state = 4;
                    }
                }
            }
        }
    }
}
