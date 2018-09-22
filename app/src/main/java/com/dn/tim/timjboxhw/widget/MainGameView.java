/*
 * Created by 动脑科技-Tim on 17-7-21 下午6:04
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-7-21 下午6:04
 */

package com.dn.tim.timjboxhw.widget;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dn.tim.timjboxhw.R;

public class MainGameView extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    private Context mContext;
    private SurfaceHolder holder;
    private Paint paint;
    private Canvas canvas;

    private World world;

    private Bitmap bitmapBg;
    private Bitmap bmpStone;
    private Thread thread;

    private boolean flag = false;

    public MainGameView(Context context) {
        super(context);
        mContext = context;
        this.setKeepScreenOn(true);//保持屏幕常亮
        holder = this.getHolder();//获取控制器
        holder.addCallback(this);//添加监听
        paint = new Paint();//实例化画笔
        paint.setStyle(Style.FILL_AND_STROKE);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        //创建一个物理世界
        AABB aabb = new AABB();
        aabb.lowerBound = new Vec2(-100, -100);
        aabb.upperBound = new Vec2(100, 100);
        Vec2 gravity = new Vec2(0, 10);
        world = new World(aabb, gravity, true);

    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        bitmapBg = BitmapFactory.decodeResource(getResources(), R.mipmap.background);
        bmpStone = BitmapFactory.decodeResource(getResources(), R.mipmap.tile1);
        WorldBodyFactory.createStone(world, getWidth()/2, getHeight()/2,
                bmpStone.getWidth(), bmpStone.getHeight(),
                0, 1.0f,
                bmpStone);//创建一个小石头
        WorldBodyFactory.createPolygon(world, 0, getHeight()-5,getWidth() , 30, 0, 0);
        thread = new Thread(this);//地面
        flag = true;
        thread.start();
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
    }
    public void surfaceDestroyed(SurfaceHolder arg0) {

        flag = false;

    }
    /**
     * 绘图
     */
    private void draw() {
        try{
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);//清屏
            //绘制背景
            canvas.drawBitmap(bitmapBg, 0, 0, paint);
            //遍历绘制Body
            Body body = world.getBodyList();
            for(int i = 1; i < world.getBodyCount(); i++){
                if ((body.m_userData) instanceof MyRect) {
                    MyRect rect = (MyRect) (body.m_userData);
                    rect.drawRect(canvas, paint);
                }if ((body.m_userData) instanceof MyStone) {
                    MyStone title = (MyStone) (body.m_userData);
                    title.drawStone(canvas, paint);
                }
                body = body.m_next;
            }
        }catch(Exception e){

        }finally{
            try {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            } catch (Exception e2) {

            }
        }
    }

    /**
     * 逻辑
     */
    private void logic() {
        world.step(Constant.stepTime, Constant.iteraTions);
        ///遍历Body，进行Body与图形之间的传递数据
        Body body = world.getBodyList();
        for (int i = 1; i < world.getBodyCount(); i++) {
            //判定m_userData中的数据是否为MyRect实例
            if ((body.m_userData) instanceof MyRect) {
                MyRect rect = (MyRect) (body.m_userData);
                rect.setX(body.getPosition().x * Constant.RATE - rect.getW() / 2);
                rect.setY(body.getPosition().y *Constant.RATE - rect.getH() / 2);
                rect.setAngle((float) (body.getAngle() * 180 / Math.PI));
            } else if ((body.m_userData) instanceof MyStone) {
                //判定m_userData中的数据是否为MyTile实例
                MyStone tile = (MyStone) (body.m_userData);
                tile.setX(body.getPosition().x * Constant.RATE - tile.getW() / 2);
                tile.setY(body.getPosition().y * Constant.RATE- tile.getH() / 2);
                tile.setAngle((float) (body.getAngle() * 180 / Math.PI));
            }
            body = body.m_next;
        }
        conut++;

    }

    public void run() {
        while (flag) {
            logic();
            draw();
            try {
                Thread.sleep((long)Constant.stepTime * 1000);
            } catch (Exception ex) {
            }
        }

    }
    private int conut = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN
                &&conut > 100){
            WorldBodyFactory.createStone(world, getWidth()/2, bmpStone.getHeight(), bmpStone.getWidth(), bmpStone.getHeight(),
                    0f, 0.5f, bmpStone);
            conut = 0;

        }
        return super.onTouchEvent(event);
    }
}
