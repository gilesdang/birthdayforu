package com.augmentum.giles.happyforu.com.dot;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.augmentum.giles.happyforu.R;
import com.augmentum.giles.happyforu.com.fireview.Animation;

public abstract class Dot {
    int x = 30; // �õ�ĺ����
    int y = 30; // �õ�������

    int color; // �õ����ɫ

    int pace = 30; // �õ�������ٶ�
    int size = 6; // ��Ĵ�С

    final Point endPoint = new Point(); // ��¼�õ�����λ��

    // ��¼�õ��״̬�������Ƿ��Ǳ���״̬,1������״̬��2�Ǳ�ը״
    // ̬�ĳ�ʼ����3�Ǳ�ը״̬��4����ʧ״̬
    public int state = 1;
    public int circle = 1;

    LittleDot[] ld = new LittleDot[200];

    int WALLOP = 20; // �豬ը��ÿ�������ܵ��ĳ������10N
    int GRAVITY = 20;

    Animation mFireAnim = null;
    Context mContext;
    
    int maxCircle;

    public Dot(Context context, int color, int endX, int endY) {
        circle = 1;
        state = 1;
        pace = 25;

        this.x = endX;
        this.y = 800;
        this.color = color;
        endPoint.x = endX;
        endPoint.y = endY;
        mContext = context;
         new loadFireSrc().start();// ���߳�ȥ����ͼƬ��ݣ������������������Ļһ��һ����
//        new AnmiLoad().execute();
         
         maxCircle = new Random().nextInt(6) + 5;
    }

    class loadFireSrc extends Thread {
        public void run() {
            mFireAnim = new Animation(mContext, new int[] { R.drawable.trail1,
                    R.drawable.trail2, R.drawable.trail3, R.drawable.trail4,
                    R.drawable.trail5, R.drawable.trail6 }, true);
        }
    }


    public void rise() {
        // ��������ĵ����
        if (mFireAnim != null) {
            y -= pace;
            // ȷ��x�᲻��
            x = x * 1;

            if (y <= endPoint.y) {
                y = endPoint.y;

            }

            if (x <= endPoint.x) {
                x = endPoint.x;
            }

        }
        
    }

    // �ж��Ƿ�ը
    public boolean whetherBlast() {
        // �ж��Ƿ�ը
        if (y <= endPoint.y && x <= endPoint.x) {
            return true;
        }
        return false;
    }

    // ��ʼ����ը�����
    public abstract LittleDot[] initBlast();

    // ���?ը�����
    public abstract LittleDot[] blast();

    public void myPaint(Canvas canvas, Vector<Dot> lList) {
        Paint mPaint = new Paint();
        mPaint.setColor(color);
        RectF oval = new RectF(x, y, x + size, y + size);
        if (state == 1) {
            if (mFireAnim != null) {
                mFireAnim.DrawAnimation(canvas, mPaint, x, y);
            }
        }
        if (state == 2) {
            canvas.drawOval(oval, mPaint);
            LittleDot[] ld2 = initBlast();
            ld = new LittleDot[ld2.length];
            ld = ld2;

            for (int i = 0; i < ld.length / 4; i++) {
                mPaint.setColor(ld[i].color);
                oval = new RectF(ld[i].x, ld[i].y, ld[i].x + 2, ld[i].y + 2);
                canvas.drawOval(oval, mPaint);
            }
            state = 3;

        } else if (state == 3) {
            if (circle <= maxCircle) {
                circle++;
                this.ld = blast();
                for (int i = 0; i < ld.length; i++) {
                    mPaint.setColor(ld[i].color);
                    oval = new RectF(ld[i].x, ld[i].y, ld[i].x + 2, ld[i].y + 2);
                    canvas.drawOval(oval, mPaint);
                }
            } else {
                //�����ڿ��У�������˸
                for (int i = 0; i < ld.length; i++) {
                    mPaint.setColor(ld[i].color);
                    mPaint.setAlpha(20 + (int) (Math.random() * 0xff));
                    oval = new RectF(ld[i].x, ld[i].y, ld[i].x + 2, ld[i].y + 2);
                    canvas.drawOval(oval, mPaint);
                }
            }

        } else if (state == 4) {
            synchronized (lList) {
                lList.remove(this);
            }
        }
    }

    public String toString() {
        return "�õ����ڵ�λ����x��" + x + ",y=" + y + "," + "��ը����x=" + endPoint.x
                + ",y=" + endPoint.y + "��ɫ��" + color;
    }
}
