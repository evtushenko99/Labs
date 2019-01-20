package com.lab4_6;

import java.util.Random;

public class GoldFish extends Direction implements iBehavior,Runnable{
    Random rnd = new Random();
    private int moving = rnd.nextInt(3);
    public GoldFish(int x, int y, int speed) {
        super(x, y, speed);
    }
    @Override
    public void move() {
       // y += speed;
        switch(moving) {
            case 0:
                x += speed;
                break;
            case 1:
                x += speed*2;
                break;
            case 2:
                x += speed*3;
                break;
            case 3:
                x += speed*4;

                break;
        }
        moving = rnd.nextInt(3);
    }

    @Override
    public void run(){
        move();
    }
}
