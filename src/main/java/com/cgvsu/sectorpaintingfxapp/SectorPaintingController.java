package com.cgvsu.sectorpaintingfxapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public class SectorPaintingController {
    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    double centerX;
    double centerY;
    double radius;
    double startAngle;
    double length;

    public void initialize() {
        drawSector();
    }

    private void drawSector() {

    }

//    private boolean isPointInSector(int x, int y) {
//
//    }
}
