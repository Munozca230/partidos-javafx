package com.example.sena;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.ConnectException;
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
    @FXML
    private Text numeroPartidos;
    private ObservableList<Partido> partidosList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa las columnas de la tabla
        equipoContrincanteColumn.setCellValueFactory(new PropertyValueFactory<>("equipoContrincante"));
        marcadosColumn.setCellValueFactory(new PropertyValueFactory<>("golesMarcados"));
        recibidosColumn.setCellValueFactory(new PropertyValueFactory<>("golesRecibidos"));
        avgGolesColumn.setCellValueFactory(new PropertyValueFactory<>("avgGoles"));
        lugarColumn.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        amarillasColColumn.setCellValueFactory(new PropertyValueFactory<>("amarillasColombia"));
        rojasColColumn.setCellValueFactory(new PropertyValueFactory<>("rojasColombia"));
        amarillasConColumn.setCellValueFactory(new PropertyValueFactory<>("amarillasContrincante"));
        rojasConColumn.setCellValueFactory(new PropertyValueFactory<>("rojasContrincante"));
        tipoPartidoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoPartido"));
        camisaColumn.setCellValueFactory(new PropertyValueFactory<>("colorCamisa"));

        cargarActualizarItems();
    }

    private void cargarPartidosDesdeBD(String query) {
        try {
            Connection conn = iniciarConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            partidosList.clear();
            while (rs.next()) {
                Partido partido = new Partido();
                partido.setId(rs.getLong("id"));
                partido.setEquipoContrincante(rs.getString("equipo_contrincante"));
                partido.setGolesRecibidos(rs.getInt("goles_recibidos"));
                partido.setGolesMarcados(rs.getInt("goles_marcados"));
                partido.setAvgGoles((double) (rs.getInt("goles_recibidos") + rs.getInt("goles_marcados")) /2);
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
            porcentajeGanadosCol.setText(cargarDatoPartido("SELECT ((COUNT(ID) FILTER (WHERE goles_marcados > goles_recibidos) * 100) / COUNT(ID))::text AS porcentaje FROM partidos")+"%");
            equipoGoles.setText(cargarDatoPartido("\tSELECT \n" +
                    "\t  CASE WHEN team_goals >= (SELECT SUM(goles_marcados) FROM partidos) THEN equipo_contrincante ELSE 'Colombia' END AS result\n" +
                    "\tFROM (\n" +
                    "\t  SELECT equipo_contrincante, SUM(goles_recibidos) AS team_goals\n" +
                    "\t  FROM partidos\n" +
                    "\t  GROUP BY equipo_contrincante\n" +
                    "\t) subquery\n" +
                    "\tWHERE team_goals = (SELECT MAX(team_goals) FROM (\n" +
                    "\t  SELECT equipo_contrincante, SUM(goles_recibidos) AS team_goals\n" +
                    "\t  FROM partidos\n" +
                    "\t  GROUP BY equipo_contrincante\n" +
                    "\t) subquery);\n" +
                    "\n"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Connection iniciarConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/partidos", "postgres", "123456");
    }

    @FXML
    private void agregarPartido() {
        String equipoContrincanteValue = equipoContrincante.getText();
        int golesRecibidosValue = Integer.parseInt(golCon.getText());
        int golesMarcadosValue = Integer.parseInt(golCol.getText());
        String lugarValue = lugar.getText();
        LocalDate fechaValue = fecha.getValue();
        int amarillasColombiaValue = Integer.parseInt(amarillasCol.getText());
        int amarillasContrincanteValue = Integer.parseInt(amarillaCon.getText());
        int rojasColombiaValue = Integer.parseInt(rojaCol.getText());
        int rojasContrincanteValue = Integer.parseInt(rojaCon.getText());
        String tipoPartidoValue = splitTipo.getText();
        String colorCamisaValue = colorCamisa.getText();

        Partido nuevoPartido = new Partido(equipoContrincanteValue, golesRecibidosValue, golesMarcadosValue,
                lugarValue, fechaValue, amarillasColombiaValue, amarillasContrincanteValue,
                rojasColombiaValue, rojasContrincanteValue, tipoPartidoValue, colorCamisaValue);

        insertPartido(nuevoPartido);

        equipoContrincante.clear();
        golCon.clear();
        golCol.clear();
        lugar.clear();
        fecha.setValue(null);
        amarillasCol.clear();
        amarillaCon.clear();
        rojaCol.clear();
        rojaCon.clear();
        splitTipo.setText("Tipo de partido");
        colorCamisa.clear();

        cargarActualizarItems();
    }

    public void insertPartido(Partido partido) {
        try {
            Connection conn = iniciarConexion();
            String query = "INSERT INTO partidos (equipo_contrincante, goles_recibidos, goles_marcados, lugar, fecha, amarillas_colombia, amarillas_contrincante, rojas_colombia, rojas_contrincante, tipo_partido, color_camisa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, partido.getEquipoContrincante());
            statement.setInt(2, partido.getGolesRecibidos());
            statement.setInt(3, partido.getGolesMarcados());
            statement.setString(4, partido.getLugar());
            statement.setDate(5, java.sql.Date.valueOf(partido.getFecha()));
            statement.setInt(6, partido.getAmarillasColombia());
            statement.setInt(7, partido.getAmarillasContrincante());
            statement.setInt(8, partido.getRojasColombia());
            statement.setInt(9, partido.getRojasContrincante());
            statement.setString(10, partido.getTipoPartido());
            statement.setString(11, partido.getColorCamisa());

            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cargarActualizarItems() {
        cargarPartidosDesdeBD("SELECT * FROM partidos");
        table.setItems(partidosList);
        numeroPartidos.setText(String.valueOf(partidosList.size()));
    }

    public void mostrarOrdenDesc() {
        cargarPartidosDesdeBD("SELECT * FROM partidos ORDER BY goles_marcados DESC");
        table.setItems(partidosList);
        numeroPartidos.setText(String.valueOf(partidosList.size()));
    }

    public String cargarDatoPartido(String query) {
        try {
            Connection conn = iniciarConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getString(1);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf("0.0");
    }
    @FXML
    private void seleccionarTipoPartido(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String tipoPartido = menuItem.getText();
        splitTipo.setText(tipoPartido);
    }



}
