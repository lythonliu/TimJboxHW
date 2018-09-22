/*
 * Created by 动脑科技-Tim on 17-7-21 下午6:05
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-7-21 下午6:05
 */

package com.dn.tim.timjboxhw.widget;

import org.jbox2d.collision.PolygonDef;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.graphics.Bitmap;

public class WorldBodyFactory {
    /**
     * 创建多边形物体
     * @param x
     * @param y
     * @param w
     * @param h
     * @param angle
     * @param density
     * @return
     */
    public static  Body createPolygon(World world ,float x, float y, float w, float h, float angle, float density){

        PolygonDef pd = new PolygonDef();
        pd.density = density;//密度
        pd.friction = 0.8f;//摩擦力
        pd.restitution = 0.7f;//恢复力
        pd.setAsBox(w / 2 / Constant.RATE , h / 2 / Constant.RATE );

        BodyDef bd = new BodyDef();
        bd.position.set((x+w/2)/Constant.RATE, ( y +h/2)/Constant.RATE);
        bd.angle = (float) (angle * Math.PI / 180);

        Body body = world.createBody(bd);
        body.m_userData = new MyRect(x, y, w, h);
        body.createShape(pd);
        body.setMassFromShapes();
        return body;
    }

    /**
     *
     * @param world
     * @param x
     * @param y
     * @param w
     * @param h
     * @param angle
     * @param density
     * @param bmp
     * @return
     */
    public static Body createStone(World world, float x,float y, float w,
                                   float h,float angle , float density,Bitmap bmp){
        PolygonDef def = new PolygonDef();
        def.density = 2.0f;
        def.friction = 0.8f;
        def.restitution = 0.4f;
        def.setAsBox(w / 2 / Constant.RATE , h / 2 / Constant.RATE );
        //def.filter.groupIndex = 1;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((x + w / 2)/Constant.RATE, ( y + h / 2 )/Constant.RATE);
        bodyDef.angle = (float)(angle * Math.PI/180);
        Body body = world.createBody(bodyDef);
        if(body!=null){
            body.m_userData = new MyStone(x, y, w, h, bmp);
            body.createShape(def);
            body.setMassFromShapes();
        }
        return body;

    }
}
