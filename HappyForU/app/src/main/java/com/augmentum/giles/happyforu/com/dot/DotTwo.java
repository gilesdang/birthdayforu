package com.augmentum.giles.happyforu.com.dot;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class DotTwo extends Dot {

	public DotTwo(Context context, int color, int endX, int entY) {
		super(context, color, endX, entY);
		// TODO Auto-generated constructor stub
		pace = 20;
	}

	@Override
	public LittleDot[] initBlast() {
		// TODO Auto-generated method stub
		final int ONE = 20; // ���Ǳ�ը�ĵ�һȦ�İ뾶

		int red = (int) (Math.random() * 255);
		int green = (int) (Math.random() * 255);
		int blue = (int) (Math.random() * 255);

		int col = 0xff000000 | red << 16 | green << 8 | blue;

		Random rand = new Random();
		int blastX = endPoint.x - ONE * circle;
		int blastY = endPoint.y - ONE * circle;

		if (Math.random() < 0.3) {
			for (int i = 0; i < ld.length; i++) {
				ld[i] = new LittleDot(rand.nextInt(ONE * circle * 2) + blastX,
						rand.nextInt(ONE * circle * 2) + blastY, col);
				ld[i].setPace(WALLOP, endPoint.x, endPoint.y);
			}
		} else {
			for (int i = 0; i < ld.length; i++) {
				ld[i] = new LittleDot(rand.nextInt(ONE * circle * 2) + blastX,
						rand.nextInt(ONE * circle * 2) + blastY, color);
				ld[i].setPace(WALLOP, endPoint.x, endPoint.y);
			}// for
		}

		color = 0xff000000 | 225 << 16 | 203 << 8 | 114;
		size = 10;
		state = 3;
		return ld;
	}

	@Override
	public LittleDot[] blast() {
		// TODO Auto-generated method stub
		// ���?ը�����
		final int EVERY = 25;// ��ը��ÿȦ�İ뾶
		Random rand = new Random();
		for (int i = 0; i < ld.length; i++) {
			// ǰ�����Ȧ���һ�밴������Ĵ��������㱬ը����黨��λ��
			if (circle <= maxCircle / 2) {
				if (ld[i].x < endPoint.x) {
					ld[i].x -= rand.nextInt(EVERY);
				} else {
					ld[i].x += rand.nextInt(EVERY);
				}

				if (ld[i].y < endPoint.y) {
					ld[i].y -= rand.nextInt(EVERY);
				} else {
					ld[i].y += rand.nextInt(EVERY);
				}
			}
			// ����ľ��黨�Ϳ�ʼ��׹
			else {
				if (ld[i].x < endPoint.x) {
					ld[i].x -= rand.nextInt(EVERY);
				}

				else {
					ld[i].x += rand.nextInt(EVERY);
				}

				ld[i].y += rand.nextInt(EVERY);
			}
		}
		return ld;
	}

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
			if (circle < 5) {
				circle++;
				this.ld = blast();
				for (int i = 0; i < ld.length; i++) {
					mPaint.setColor(ld[i].color);
					mPaint.setAlpha(1000);
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
}
