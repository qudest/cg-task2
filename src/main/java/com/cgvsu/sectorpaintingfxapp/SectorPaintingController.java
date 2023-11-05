package com.cgvsu.sectorpaintingfxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SectorPaintingController {
    private final Sector sector = new Sector();
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
            sector.setCenterX(Double.parseDouble(xCenterField.getText()));
            sector.setCenterY(Double.parseDouble(yCenterField.getText()));
            sector.setRadius(Double.parseDouble(radiusField.getText()));
            sector.setStartAngle(Double.parseDouble(startAngleField.getText()));
            sector.setLength(Double.parseDouble(lengthField.getText()));
            sector.setEndAngle(sector.getStartAngle()+sector.getLength());
            sector.drawSector(canvas);
        }
        catch (NullPointerException | NumberFormatException ex)
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
        //sector.drawSector(canvas);
    }
}
