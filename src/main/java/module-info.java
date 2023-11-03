module com.cgvsu.sectorpaintingfxapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cgvsu.sectorpaintingfxapp to javafx.fxml;
    exports com.cgvsu.sectorpaintingfxapp;
}