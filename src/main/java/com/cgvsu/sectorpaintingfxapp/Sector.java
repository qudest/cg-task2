package com.cgvsu.sectorpaintingfxapp;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
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

        if (length <= 0) {
            return;
        } else if (length <= Math.PI) {
            draw(pixelWriter, canvas, startAngle, endAngle);
        } else {
            draw(pixelWriter, canvas, startAngle, startAngle + Math.PI);
            draw(pixelWriter, canvas, startAngle + Math.PI, endAngle);
        }

        canvas.getGraphicsContext2D().drawImage(writableImage, 0, 0);
    }

    private void draw(PixelWriter pixelWriter, Canvas canvas, double startAngle, double endAngle) {
        for (int y = centerY - radius; y <= centerY + radius; y++) {
            int x_left = (int) Math.round(centerX - Math.sqrt(radius * radius - Math.pow((y - centerY), 2)));
            int x_right = (int) Math.round(centerX + Math.sqrt(radius * radius - Math.pow((y - centerY), 2)));
            for (int x = x_left; x <= x_right ; x++) {
                if (isPointInSector(x, y, canvas, startAngle, endAngle)) {
                    double ratio = (distance(x, y) / (double) radius) * 0.8;
                    pixelWriter.setColor(x, y, interpolate(startColor, endColor, ratio));
                }
            }
        }
    }

    private boolean isPointInSector(double x, double y, Canvas canvas, double startAngle, double endAngle) {
        if (x < 0 || y < 0 || x >= canvas.getWidth() || y >= canvas.getHeight()) {
            return false;
        }

        Point O = new Point(centerX, centerY);
        Point P = new Point(x, y);
        Point A = new Point((centerX - radius * Math.cos(startAngle)), (centerY + radius * Math.sin(startAngle)));
        Point B = new Point((centerX - radius * Math.cos(endAngle)), (centerY + radius * Math.sin(endAngle)));

        Vector2D OP = new Vector2D(P.getX() - O.getX(), P.getY() - O.getY());
        Vector2D OA = new Vector2D(A.getX() - O.getX(), A.getY() - O.getY());
        Vector2D OB = new Vector2D(B.getX() - O.getX(), B.getY() - O.getY());

        double M = Vector2D.crossProduct(OA, OP);
        double N = Vector2D.crossProduct(OP, OB);

        // M⃗ = OA⃗ × OP⃗
        // N⃗ = OP⃗ × OB

        return M >= 0 && N >= 0;
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
