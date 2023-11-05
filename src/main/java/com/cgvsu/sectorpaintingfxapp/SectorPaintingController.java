package com.cgvsu.sectorpaintingfxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SectorPaintingController {
    private final Sector sector = new Sector(300,300,100,0,90);
    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    @FXML
    private TextField xCenterField, yCenterField, radiusField, startAngleField, lengthField, startRed, startGreen, startBlue, endRed, endGreen, endBlue;
    @FXML
    private void paint(ActionEvent event) {
        try
        {
            sector.setCenterX(Double.parseDouble(xCenterField.getText()));
            sector.setCenterY(Double.parseDouble(yCenterField.getText()));
            sector.setRadius(Double.parseDouble(radiusField.getText()));
            sector.setStartAngle(Double.parseDouble(startAngleField.getText()));
            sector.setLength(Double.parseDouble(lengthField.getText()));
            sector.setEndAngle();
            sector.setStartColor(Integer.parseInt(startRed.getText()), Integer.parseInt(startGreen.getText()), Integer.parseInt(startBlue.getText()));
            sector.setEndColor(Integer.parseInt(endRed.getText()), Integer.parseInt(endGreen.getText()), Integer.parseInt(endBlue.getText()));
            sector.drawSector(canvas);
        }
        catch (NullPointerException | IllegalArgumentException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect input");
            alert.setContentText("Input correct values!");
            alert.showAndWait();
        }
    }
    @FXML
    private void clear(ActionEvent event) {
        if (canvas != null)
            canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public void initialize() {
        sector.drawSector(canvas);
    }
}
