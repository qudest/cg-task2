package com.cgvsu.sectorpaintingfxapp;

public class Vector2D {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double crossProduct(Vector2D v1, Vector2D v2) {
        return v1.x * v2.y - v1.y * v2.x;
    }
}
