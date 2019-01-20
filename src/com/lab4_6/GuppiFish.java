package com.lab4_6;

import java.util.Random;

public class GuppiFish extends Direction implements iBehavior,Runnable{
    Random rnd = new Random();
    private int moving = rnd.nextInt(3);
    public GuppiFish(int x, int y, int speed) {
        super(x, y, speed);
    }
   @Override
    public void move() {
        x = speed;
        switch(moving) {
            case 0:
                y += speed;
                break;
            case 1:
                y += speed*2;
                //y -= speed;
                break;
            case 2:
                y += speed*3;
                break;
            case 3:
                y += speed*4;
                //y += speed;
                break;
        }
        moving = rnd.nextInt(3);
    }

    @Override
    public void run(){
        move();
    }
}
