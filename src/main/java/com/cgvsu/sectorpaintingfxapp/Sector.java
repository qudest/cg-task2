package com.cgvsu.sectorpaintingfxapp;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Sector {
    private int centerX = 0;
    private int centerY = 0;
    private int radius = 0;
    private double startAngle = 0;
    private double length = 0;
    private double endAngle = 0;
    private Color startColor = Color.RED;
    private Color endColor = Color.BLUE;

    public Sector(int centerX, int centerY, int radius, double startAngle, double length) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.startAngle = startAngle;
        this.length = length;
        this.endAngle = startAngle + length;
    }

    public Sector() {
    }

    public Color getStartColor() {
        return startColor;
    }

    public void setStartColor(int r, int g, int b) {
        this.startColor = Color.rgb(r, g, b);
    }

    public Color getEndColor() {
        return endColor;
    }

    public void setEndColor(int r, int g, int b) {
        this.endColor = Color.rgb(r, g, b);
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
        this.endAngle = startAngle + length;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
        this.endAngle = startAngle + length;
    }

    public double getEndAngle() {
        return endAngle;
    }

    public void drawSector(Canvas canvas) {
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = centerY - radius; y < centerY + radius; y++) {
            int x_left = (int) (centerX - Math.sqrt(radius * radius - Math.pow((y - centerY), 2)));
            int x_right = (int) (centerX + Math.sqrt(radius * radius - Math.pow((y - centerY), 2)));
            for (int x = x_left; x < x_right; x++) {
                if (isPointInSector(x, y, canvas)) {
                    double ratio = (distance(x, y) / (double) radius) * 0.8;
                    pixelWriter.setColor(x, y, interpolate(startColor, endColor, ratio));
                }
            }
        }
        canvas.getGraphicsContext2D().drawImage(writableImage, 0, 0);
    }

    private boolean isPointInSector(int x, int y, Canvas canvas) {
        if (x < 0 || y < 0 || x >= canvas.getWidth() || y >= canvas.getHeight()) {
            return false;
        }

        Point O = new Point(centerX, centerY);
        Point P = new Point(x, y);
        Point A = new Point((int) (centerX - radius * Math.cos(startAngle)), (int) (centerY + radius * Math.sin(startAngle)));
        Point B = new Point((int) (centerX - radius * Math.cos(endAngle)), (int) (centerY + radius * Math.sin(endAngle)));

        Vector2D OP = new Vector2D(P.getX() - O.getX(), P.getY() - O.getY());
        Vector2D OA = new Vector2D(A.getX() - O.getX(), A.getY() - O.getY());
        Vector2D OB = new Vector2D(B.getX() - O.getX(), B.getY() - O.getY());

        double Mz = (OA.getX() * OP.getY() - OA.getY() * OP.getX());
        double Nz = (OP.getX() * OB.getY() - OP.getY() * OB.getX());

        // (aX*bY − aYb*X)

        // M⃗ = OA⃗ × OP⃗
        // N⃗ = OP⃗ × OB

        // OP = {Px - Ox, Py - Oy} == b
        // OA = {Ax - Ox, Ay - Oy} == a

        // OP = {Px - Ox, Py - Oy} == a
        // OB = {Bx - Ox, By - Oy == b


        return Mz >= 0 && Nz >= 0 || Mz <= 0 && Nz <= 0;
    }

    private Color interpolate(Color startColor, Color endColor, double ratio) {
        if (ratio <= 0.0) {
            return startColor;
        } else if (ratio >= 1.0) {
            return endColor;
        }
        double red = (startColor.getRed() + (endColor.getRed() - startColor.getRed()) * ratio);
        double green = (startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * ratio);
        double blue = (startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * ratio);
        return Color.color(red, green, blue);
    }

    private int distance(int x, int y) {
        return (int) Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(centerY - y, 2));
    }
}
