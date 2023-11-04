package com.cgvsu.sectorpaintingfxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;

public class SectorPaintingController {
    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    @FXML
    private TextField xCenterField;
    @FXML
    private TextField yCenterField;
    @FXML
    private TextField radiusField;
    @FXML
    private TextField startAngleField;
    @FXML
    private TextField lengthField;
    @FXML
    private void paint(ActionEvent event) {
        try
        {
            centerX = Double.parseDouble(xCenterField.getText());
            centerY = Double.parseDouble(yCenterField.getText());
            radius = Double.parseDouble(radiusField.getText());
            startAngle = Double.parseDouble(startAngleField.getText());
            length = Double.parseDouble(lengthField.getText());
            endAngle = startAngle + length;
            drawSector();
        }
        catch (NullPointerException | NumberFormatException ex)
        {
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        if (canvas != null)
            canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    double centerX;
    double centerY;
    double radius;
    double startAngle;
    double length;
    double endAngle = startAngle + length;

    public void initialize() {
        drawSector();
    }

    private void drawSector() {
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = (int) (centerY-radius); y < centerY+radius; y++) {
            for (int x = (int) (centerX -radius); x < centerX +radius; x++) {
                if (isPointInSector(x, y)) {
                    pixelWriter.setColor(x, y, Color.RED);
                }
            }
        }
        canvas.getGraphicsContext2D().drawImage(writableImage, 0, 0);
    }

    private boolean isPointInSector(int x, int y) {
        double angle = Math.toDegrees(Math.atan2(y - centerY, x - centerX));
        if (angle < 0) {
            angle += 360;
        }

        double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));

        // Корректировка углов для обеспечения правильного диапазона от 0 до 360 градусов
        if (startAngle > 360) {
            startAngle %= 360;
            endAngle = (startAngle + length) % 360;
        }

        // Проверка на переход через 360 градусов
        if (endAngle > 360) {
            if (angle >= startAngle || angle <= endAngle - 360) {
                return distance <= radius;
            }
        }

        return angle >= startAngle && angle <= endAngle && distance <= radius;
    }
}
