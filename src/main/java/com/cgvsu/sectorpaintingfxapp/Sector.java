package com.cgvsu.sectorpaintingfxapp;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Sector {
    private double centerX = 0;
    private double centerY = 0;
    private double radius = 0;
    private double startAngle = 0;
    private double length = 0;
    private double endAngle = startAngle + length;
    private Color startColor = Color.rgb(255, 0, 0);
    private Color endColor = Color.BLUE;

    public Sector(double centerX, double centerY, double radius, double startAngle, double length) {
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

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getEndAngle() {
        return endAngle;
    }

    public void setEndAngle() {
        this.endAngle = startAngle + length;
    }

    public void drawSector(Canvas canvas) {
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        try {
            for (int y = (int) (centerY - radius); y < centerY + radius; y++) {
                for (int x = (int) (centerX - radius); x < centerX + radius; x++) {
                    if (isPointInSector(x, y)) {
                        double ratio = 0.008D * distance(x, y);
                        pixelWriter.setColor(x, y, interpolate(startColor, endColor, ratio));
                    }
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sector outside the canvas");
            alert.setContentText("Sector outside the canvas!");
            alert.showAndWait();
            return;
        }
        canvas.getGraphicsContext2D().drawImage(writableImage, 0, 0);
    }

    private boolean isPointInSector(int x, int y) {
        double angle = Math.toDegrees(Math.atan2(centerY - y, x - centerX));
        if (angle < 0) {
            angle += 360;
        }

        double dist = distance(x, y);

        while (startAngle < 0) {
            startAngle += 360;
            endAngle = startAngle + length;
        }

        if (startAngle > 360) {
            startAngle %= 360;
            endAngle = startAngle + length;
        }

        if (endAngle > 360) {
            if (angle >= startAngle || angle <= endAngle - 360) {
                return dist <= radius;
            }
        }

        return angle >= startAngle && angle <= endAngle && dist <= radius;
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

    private double distance(int x, int y) {
        return Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(centerY - y, 2));
    }
}
