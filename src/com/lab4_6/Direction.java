package com.lab4_6;

public abstract class Direction {
    protected int x;
    protected int y;
    protected int speed;

    public Direction() {
        x = 10;
        y = 10;
        speed = 10;
    }

    public Direction(int X, int Y, int Speed) {
        super();
        x = X;
        y = Y;
        speed = Speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
