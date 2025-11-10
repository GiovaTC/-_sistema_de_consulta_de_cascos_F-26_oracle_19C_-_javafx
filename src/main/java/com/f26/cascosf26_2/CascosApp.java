package com.f26.cascosf26_2;

import com.f26.cascosf26_2.dao.CascoDAO;
import com.f26.cascosf26_2.model.Casco;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class CascosApp extends Application {

    private TableView<Casco> tabla = new TableView<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Consulta de Cascos KASA F-26");

        TableColumn<Casco, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(f -> new javafx.beans.property.SimpleIntegerProperty(f.getValue().getIdCasco()).asObject());

        TableColumn<Casco, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getNombreCasco()));

        TableColumn<Casco, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getModelo()));

        TableColumn<Casco, String> colProv = new TableColumn<>("Provisiones");
        colProv.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getProvisiones()));

        TableColumn<Casco, String> colDocs = new TableColumn<>("Documentos");
        colDocs.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getDocumentos()));

        tabla.getColumns().addAll(colId, colNombre, colModelo, colProv, colDocs);

        Button btnCargar = new Button("Consultar cascos");
        btnCargar.setOnAction(e -> {
            CascoDAO dao = new CascoDAO();
            tabla.getItems().setAll(dao.consultarCascos());
        });

        VBox root = new VBox(10, btnCargar, tabla);
        root.setPadding(new javafx.geometry.Insets(10));
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}