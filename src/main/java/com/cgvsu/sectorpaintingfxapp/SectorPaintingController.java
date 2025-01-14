package com.cgvsu.sectorpaintingfxapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SectorPaintingController {
    private final Sector sector = new Sector();
    private final Sector[] testSectors = new Sector[]{
            new Sector(100, 200, 100, Math.toRadians(0), Math.toRadians(90)),
            new Sector(400, 200, 100, Math.toRadians(90), Math.toRadians(90)),
            new Sector(550, 100, 100,  Math.toRadians(180), Math.toRadians(90)),
            new Sector(600, 100, 100,  Math.toRadians(270), Math.toRadians(90)),
            new Sector(250, 350, 100,  Math.toRadians(135), Math.toRadians(45)),
            new Sector(350, 350, 100,  Math.toRadians(90), Math.toRadians(45)),
            new Sector(450, 350, 100,  Math.toRadians(45), Math.toRadians(45)),
            new Sector(550, 350, 100,  Math.toRadians(0), Math.toRadians(45))
    };

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    @FXML
    private TextField xCenterField, yCenterField, radiusField, startAngleField, lengthField, startRed, startGreen, startBlue, endRed, endGreen, endBlue;

    @FXML
    private void paint() {
        try {
            sector.setCenterX(Integer.parseInt(xCenterField.getText()));
            sector.setCenterY(Integer.parseInt(yCenterField.getText()));
            sector.setRadius(Integer.parseInt(radiusField.getText()));
            sector.setStartAngle(Math.toRadians(Double.parseDouble(startAngleField.getText())));
            sector.setLength(Math.toRadians(Double.parseDouble(lengthField.getText())));
            sector.setStartColor(Integer.parseInt(startRed.getText()), Integer.parseInt(startGreen.getText()), Integer.parseInt(startBlue.getText()));
            sector.setEndColor(Integer.parseInt(endRed.getText()), Integer.parseInt(endGreen.getText()), Integer.parseInt(endBlue.getText()));
            sector.drawSector(canvas);
        } catch (NullPointerException | IllegalArgumentException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect input");
            alert.setContentText("Input correct values!");
            alert.showAndWait();
        }
    }

    @FXML
    private void clear() {
        if (canvas != null)
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void initialize() {
        for (Sector sector : testSectors) {
            sector.drawSector(canvas);
        }
    }
}
