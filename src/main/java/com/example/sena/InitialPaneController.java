package com.example.sena;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.sql.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InitialPaneController implements Initializable {
    @FXML
    private TableView<Partido> table;
    @FXML
    private TableColumn<Partido, Integer> equipoContrincanteColumn;
    @FXML
    private TableColumn<Partido, Integer> marcadosColumn;
    @FXML
    private TableColumn<Partido, Integer> recibidosColumn;
    @FXML
    private TableColumn<Partido, Double> avgGolesColumn;
    @FXML
    private TableColumn<Partido, String> lugarColumn;
    @FXML
    private TableColumn<Partido, LocalDate> fechaColumn;
    @FXML
    private TableColumn<Partido, Integer> amarillasColColumn;
    @FXML
    private TableColumn<Partido, Integer> rojasColColumn;
    @FXML
    private TableColumn<Partido, Integer> amarillasConColumn;
    @FXML
    private TableColumn<Partido, Integer> rojasConColumn;
    @FXML
    private TableColumn<Partido, String> tipoPartidoColumn;
    @FXML
    private TableColumn<Partido, String> camisaColumn;
    @FXML
    private Button buttonAgregar;
    @FXML
    private TextField equipoContrincante;
    @FXML
    private DatePicker fecha;
    @FXML
    private TextField golCol;
    @FXML
    private TextField golCon;
    @FXML
    private TextField lugar;
    @FXML
    private TextField amarillasCol;
    @FXML
    private TextField amarillaCon;
    @FXML
    private TextField rojaCol;
    @FXML
    private TextField rojaCon;
    @FXML
    private TextField colorCamisa;
    @FXML
    private SplitMenuButton splitTipo;
    @FXML
    private Button buttonMostrarTodos;
    @FXML
    private Button buttonOrdenDesc;
    @FXML
    private Text equipoGoles;
    @FXML
    private Text porcentajeGanadosCol;
    private ObservableList<Partido> partidosList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa las columnas de la tabla
        equipoContrincanteColumn.setCellValueFactory(new PropertyValueFactory<>("equipoContrincante"));
        marcadosColumn.setCellValueFactory(new PropertyValueFactory<>("golesMarcados"));
        recibidosColumn.setCellValueFactory(new PropertyValueFactory<>("golesRecibidos"));
        lugarColumn.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        amarillasColColumn.setCellValueFactory(new PropertyValueFactory<>("amarillasColombia"));
        rojasColColumn.setCellValueFactory(new PropertyValueFactory<>("rojasColombia"));
        amarillasConColumn.setCellValueFactory(new PropertyValueFactory<>("amarillasContrincante"));
        rojasConColumn.setCellValueFactory(new PropertyValueFactory<>("rojasContrincante"));
        tipoPartidoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoPartido"));
        camisaColumn.setCellValueFactory(new PropertyValueFactory<>("colorCamisa"));


        // Carga los datos de la base de datos en la lista
        cargarPartidosDesdeBD();

        // Asigna la lista a la tabla
        table.setItems(partidosList);
    }

    private void cargarPartidosDesdeBD() {
        System.out.println("hola");
        try {
            Connection conn = iniciarConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM partidos");

            while (rs.next()) {
                Partido partido = new Partido();
                partido.setId(rs.getLong("id"));
                partido.setEquipoContrincante(rs.getString("equipo_contrincante"));
                partido.setGolesRecibidos(rs.getInt("goles_recibidos"));
                partido.setGolesMarcados(rs.getInt("goles_marcados"));
                partido.setLugar(rs.getString("lugar"));
                partido.setFecha(rs.getDate("fecha").toLocalDate());
                partido.setAmarillasColombia(rs.getInt("amarillas_colombia"));
                partido.setRojasColombia(rs.getInt("rojas_colombia"));
                partido.setAmarillasContrincante(rs.getInt("amarillas_contrincante"));
                partido.setRojasContrincante(rs.getInt("rojas_contrincante"));
                partido.setTipoPartido(rs.getString("tipo_partido"));
                partido.setColorCamisa(rs.getString("color_camisa"));

                partidosList.add(partido);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Connection iniciarConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/partidos", "postgres", "123456");
    }
}