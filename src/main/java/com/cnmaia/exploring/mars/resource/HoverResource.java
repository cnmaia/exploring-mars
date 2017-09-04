package com.cnmaia.exploring.mars.resource;

/**
 * Created by cmaia on 9/3/17
 */
public class HoverResource {
    private String name;
    private char facingDirection;
    private int x;
    private int y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(char facingDirection) {
        this.facingDirection = facingDirection;
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
}
