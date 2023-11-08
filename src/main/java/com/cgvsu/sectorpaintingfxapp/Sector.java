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

        for (int y = centerY - radius; y <= centerY + radius; y++) {
            for (int x = centerX - radius; x <= centerX + radius; x++) {
                if (isPointInSector(x, y, canvas)) {
                    double ratio = (distance(x, y) / (double) radius) * 0.8;
                    pixelWriter.setColor(x, y, interpolate(startColor, endColor, ratio,1));
                }
            }
        }
        canvas.getGraphicsContext2D().drawImage(writableImage, 0, 0);
    }

    private boolean isPointInSector(int x, int y, Canvas canvas) {
        if (x < 0 || y < 0 || x >= canvas.getWidth() || y >= canvas.getHeight()) {
            return false;
        }

        if (y == centerY && x == centerX) {
            return true;
        }

        double angle = Math.atan2(centerY - y, x - centerX);
        double dist = distance(x, y);

        if (angle < 0) {
            angle += 2 * Math.PI;
        }

        while (startAngle < 0) {
            startAngle += 2 * Math.PI;
            endAngle = startAngle + length;
        }

        if (startAngle > 2 * Math.PI) {
            startAngle %= 2 * Math.PI;
            endAngle = startAngle + length;
        }

        if (endAngle >= 2 * Math.PI) {
            if (angle >= startAngle || angle <= endAngle - 2 * Math.PI) {
                return dist <= radius;
            }
        }

        return angle >= startAngle && angle <= endAngle && dist <= radius;
    }

    private Color interpolate(Color startColor, Color endColor, double ratio, double alpha) {
        if (ratio <= 0.0) {
            return startColor;
        } else if (ratio >= 1.0) {
            return endColor;
        }
        double red = (startColor.getRed() + (endColor.getRed() - startColor.getRed()) * ratio);
        double green = (startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * ratio);
        double blue = (startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * ratio);
        return Color.color(red, green, blue, alpha);
    }

    private int distance(int x, int y) {
        return (int) Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(centerY - y, 2));
    }
}
