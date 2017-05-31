package Zoo;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //Wszystko Do Stanowiska

    @FXML
    private TextField idField, nazwaField, pensjaField;
    @FXML
    private TableView<StanowiskoData> listofStanowisko;
    @FXML
    private TableColumn<StanowiskoData, String> stanidColumn;
    @FXML
    private TableColumn<StanowiskoData, String> stannazwaColumn;
    @FXML
    private TableColumn<StanowiskoData, String> stanpensjaColumn;
    @FXML
    private Button listAllStanowiskoBtn;
    @FXML
    private ObservableList<StanowiskoData> stanowiskoList;

    private dbConnection db;


    //Zwierzęta
    @FXML
    private TextField idZwierzeField, TypField, imieZwField;
    @FXML
    private DatePicker DataZwField;
    @FXML
    private Tab Zwierzeta_tab;
    @FXML
    private TableView<ZwierzeData> listofZwierze;
    @FXML
    private TableColumn<ZwierzeData, String> ZwierzeidColumn;
    @FXML
    private TableColumn<ZwierzeData, String> ZwierzeimieColumn;
    @FXML
    private TableColumn<ZwierzeData, String> zwierzeTypColumn;
    @FXML
    private TableColumn<ZwierzeData, String> zwierzeDataColumn;
    @FXML
    private Button listAllZwierzeBtn;
    @FXML
    private ObservableList<ZwierzeData> ZwierzeList;

    //konto
    @FXML
    private TextField PasswordField, UsernameField, idKontoField, PracIDField;
    @FXML
    private Tab Kontotab;
    @FXML
    private TableView<KontoData> listofKonto;
    @FXML
    private TableColumn<KontoData, String> idKontoColumn;
    @FXML
    private TableColumn<KontoData, String> usernameColumn;
    @FXML
    private TableColumn<KontoData, String> passwordColumn;
    @FXML
    private TableColumn<KontoData, String> PracIDColumn;
    @FXML
    private ObservableList<KontoData> KontoList;
    @FXML
    private Button listAllKontoBtn;
    //Opieka

    @FXML
    private TextField opiekunField, zwierzeField, zwierzenowyField, opiekunnowyField;
    @FXML
    private Tab Opiekatab;
    @FXML
    private TableView<OpiekaData> listofOpieka;
    @FXML
    private TableColumn<OpiekaData, String> idOpiekunColumn;
    @FXML
    private TableColumn<OpiekaData, String> idZwierzeColumn;
    @FXML
    private ObservableList<OpiekaData> OpiekaList;
    @FXML
    private Button listAllOpiekaBtn;
    //Pracownicy

    @FXML
    private TextField idPracownikField, imiePracownikField, NazwiskoPracownikField, stanIDField;
    @FXML
    private DatePicker DataUrPracField;
    @FXML
    private Tab Pracownik_tab;
    @FXML
    private TableView<PracownikData> listofPracownik;
    @FXML
    private TableColumn<PracownikData, String> PracownikidColumn;
    @FXML
    private TableColumn<PracownikData, String> PracownikimieColumn;
    @FXML
    private TableColumn<PracownikData, String> PracownikNazwiskoColumn;
    @FXML
    private TableColumn<PracownikData, String> PracownikDataColumn;
    @FXML
    private TableColumn<PracownikData, String> Stanowisko_IDColumn;
    @FXML
    private Button listAllPracownikBtn;
    @FXML
    private ObservableList<PracownikData> PracownikList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.db = new dbConnection();

        ObservableList<StanowiskoData> selectedRow = listofStanowisko.getSelectionModel().getSelectedItems();
        selectedRow.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) throws NullPointerException{
                try{
                    StanowiskoData selectedRow = listofStanowisko.getSelectionModel().getSelectedItem();
                    String sid = findProperID();
                    idField.setText(sid);

                    nazwaField.setText(selectedRow.getNazwa());
                    pensjaField.setText(selectedRow.getPensja());
                }catch(NullPointerException e){
                    idField.setText(null);
                    nazwaField.setText(null);
                    pensjaField.setText(null);
                }
            }
        });
    }

    public String findProperID(){
        try{
            StanowiskoData getSelectedRow = this.listofStanowisko.getSelectionModel().getSelectedItem();
            String nazwast = getSelectedRow.getNazwa();
            String pen = getSelectedRow.getPensja();
            String sID = null;

            try{
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT ID from stanowisko where nazwa_stanowiska = ? AND zarobek = ?;");
                ps.setString(1, nazwast);
                ps.setString(2, pen);
                ResultSet res = ps.executeQuery();
                while(res.next()){
                    sID = res.getString(1);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return sID;
        }catch(NullPointerException e){

        }
        return null;
    }

    private int idCounter = 1;
    @FXML
    public void loadStanowiskoData(ActionEvent event){
        String query = "Select * from stanowisko;";
        try{
            Connection conn = dbConnection.getConnection();
            this.stanowiskoList = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(query);
            while(rs.next()){
                this.stanowiskoList.add(new StanowiskoData(
                                                            String.valueOf(idCounter++),
                                                            rs.getString(2),
                                                            rs.getString(3)));

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        this.stanidColumn.setCellValueFactory(new PropertyValueFactory<StanowiskoData, String>("ID"));
        this.stannazwaColumn.setCellValueFactory(new PropertyValueFactory<StanowiskoData, String>("nazwa"));
        this.stanpensjaColumn.setCellValueFactory(new PropertyValueFactory<StanowiskoData, String>("pensja"));
        this.listofStanowisko.setItems(null);
        this.listofStanowisko.setItems(this.stanowiskoList);
        idCounter = 1;
    }

    @FXML
    public void addStanowisko(ActionEvent event){
        String query = "Insert into stanowisko(nazwa_stanowiska, zarobek) values (?,?);";
        try{
            Connection conn = dbConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(query);

            stm.setString(1, String.valueOf(Controller.this.nazwaField.getText()));
            stm.setString(2, String.valueOf(Controller.this.pensjaField.getText()));

            stm.execute();
            conn.close();
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void DeleteStanowisko(ActionEvent event){
        StanowiskoData getRow = listofStanowisko.getSelectionModel().getSelectedItem();
        try{
            Connection conn = dbConnection.getConnection();
            if(!getRow.toString().equals("")){
                String fn = getRow.getNazwa();
                String ln = getRow.getPensja();
                String sID = null;

                PreparedStatement ps = conn.prepareStatement("SELECT ID from stanowisko where nazwa_stanowiska = ? and zarobek = ?;");
                ps.setString(1, fn);
                ps.setString(1, ln);
                ResultSet res = ps.executeQuery();

                while(res.next()){
                    sID = res.getString(1);
                }
                String delQuery = "Delete from stanowisko where ID ="+idField.getText()+";";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie usuniecia");
                alert.setHeaderText("Chcesz usunac stanowisko?");
                alert.setContentText(""+ getRow.getNazwa()+" "+ getRow.getPensja());
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==ButtonType.OK){
                    PreparedStatement stm = conn.prepareStatement(delQuery);
                    stm.execute();

                    listAllStanowiskoBtn.fire();
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void editStanowisko(ActionEvent event){
        String updateQuery = "Update stanowisko set nazwa_stanowiska = ?, zarobek = ? where ID = ?";

        try{
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(updateQuery);
            ps.setString(1, String.valueOf(Controller.this.nazwaField.getText()));
            ps.setString(2, String.valueOf(Controller.this.pensjaField.getText()));
            ps.setString(3, String.valueOf(Controller.this.idField.getText()));

            ps.execute();

            conn.close();
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }
    //Koniec Funkcjonalności dla Stanowiska

    //Funkcje Dla zwierząt
    private int Counter2 = 1;
    @FXML
    public void loadZwierzeData(){
        String query = "SELECT * FROM zwierze;";
        try{
            Connection conn = dbConnection.getConnection();
            this.ZwierzeList = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(query);

            while(rs.next()){
                this.ZwierzeList.add(new ZwierzeData(String.valueOf(Counter2++),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        this.ZwierzeidColumn.setCellValueFactory(new PropertyValueFactory<ZwierzeData, String>("ID"));
        this.ZwierzeimieColumn.setCellValueFactory(new PropertyValueFactory<ZwierzeData, String>("imie"));
        this.zwierzeTypColumn.setCellValueFactory(new PropertyValueFactory<ZwierzeData, String>("typ"));
        this.zwierzeDataColumn.setCellValueFactory(new PropertyValueFactory<ZwierzeData, String>("data"));

        this.listofZwierze.setItems(null);
        this.listofZwierze.setItems(this.ZwierzeList);

        Counter2 =1;
    }

    @FXML
    public void AddZwierze(){
        String insertquery = "Insert into zwierze(imie, typ, data_przybycia) values(?, ?, ?);";
        try{
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(insertquery);
            ps.setString(1, String.valueOf(Controller.this.imieZwField.getText()));
            ps.setString(2, String.valueOf(Controller.this.TypField.getText()));
            ps.setString(3, String.valueOf(Controller.this.DataZwField.getEditor().getText()));
            ps.execute();
            conn.close();
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void DeleteZwierze(){
        ZwierzeData getrow = listofZwierze.getSelectionModel().getSelectedItem();
        try{
            Connection conn = dbConnection.getConnection();

            if(!getrow.toString().equals("")){
                String imie = getrow.getimie();
                String typ = getrow.gettyp();
                String data = getrow.getdata();
                String sID = null;

                String deleteQuery = "Delete from zwierze where ID =" + idZwierzeField.getText() + ";";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzeine usuniecia");
                alert.setHeaderText("Czy chesz usunac zwierze?");
                alert.setContentText("" + getrow.getimie()+" "+getrow.gettyp()+"?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==ButtonType.OK){
                    PreparedStatement stm = conn.prepareStatement(deleteQuery);
                    stm.execute();

                    listAllZwierzeBtn.fire();
                }
            }
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void editZwierze(ActionEvent event){
        String query = "Update zwierze set imie=?, typ=?, data_przybycia=? where ID =?";
        try{
            Connection conn = dbConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, String.valueOf(Controller.this.imieZwField.getText()));
            stm.setString(2, String.valueOf(Controller.this.TypField.getText()));
            stm.setString(3, String.valueOf(Controller.this.DataZwField.getEditor().getText()));
            stm.setString(4, String.valueOf(Controller.this.idZwierzeField.getText()));

            stm.execute();
            conn.close();
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }

    public String findProperZwierzeID(){
        try{
            ZwierzeData getSelectedRow = this.listofZwierze.getSelectionModel().getSelectedItem();
            String imie = getSelectedRow.getimie();
            String typ = getSelectedRow.gettyp();
            String data = getSelectedRow.getdata();
            String sID = null;

            try{
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT ID from zwierze where imie = ? AND typ =? AND data_przybycia =?;");
                ps.setString(1, imie);
                ps.setString(2, typ);
                ps.setString(3, data);
                ResultSet res = ps.executeQuery();
                while(res.next()){
                    sID = res.getString(1);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return sID;
        }catch (NullPointerException e){

        }
        return null;
    }


    //Pracownicy
    private int counter3=1;
    @FXML
    public void LoadallPracownik(ActionEvent envet){
        String query = "SELECT ID, imie, nazwisko, data_ur, stanowisko_id from pracownik;";
        try{
            Connection conn = dbConnection.getConnection();
            this.PracownikList = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(query);

            while(rs.next()){
                this.PracownikList.add(new PracownikData(String.valueOf(counter3++),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                        ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        this.PracownikidColumn.setCellValueFactory(new PropertyValueFactory<PracownikData, String>("ID"));
        this.PracownikimieColumn.setCellValueFactory(new PropertyValueFactory<PracownikData, String>("imie"));
        this.PracownikNazwiskoColumn.setCellValueFactory(new PropertyValueFactory<PracownikData, String>("nazw"));
        this.PracownikDataColumn.setCellValueFactory(new PropertyValueFactory<PracownikData, String>("dataur"));
        this.Stanowisko_IDColumn.setCellValueFactory(new PropertyValueFactory<PracownikData, String>("stanID"));

        this.listofPracownik.setItems(null);
        this.listofPracownik.setItems(this.PracownikList);

        counter3 = 1;
    }

    @FXML
    public void loadKontoData(){
        String query = "SELECT * FROM konto;";
        try{
            Connection conn = dbConnection.getConnection();
            this.KontoList = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(query);

            while(rs.next()){
                this.KontoList.add(new KontoData(String.valueOf(Counter2++),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        this.idKontoColumn.setCellValueFactory(new PropertyValueFactory<KontoData, String>("ID"));
        this.usernameColumn.setCellValueFactory(new PropertyValueFactory<KontoData, String>("imie"));
        this.passwordColumn.setCellValueFactory(new PropertyValueFactory<KontoData, String>("typ"));
        this.PracIDColumn.setCellValueFactory(new PropertyValueFactory<KontoData, String>("data"));

        this.listofKonto.setItems(null);
        this.listofKonto.setItems(this.KontoList);

        Counter2 =1;
    }

    @FXML
    public void AddKonto(){
        String insertquery = "Insert into konto(username, password, pracownik_id) values(?, ?, ?);";
        try{
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(insertquery);
            ps.setString(1, String.valueOf(Controller.this.UsernameField.getText()));
            ps.setString(2, String.valueOf(Controller.this.PasswordField.getText()));
            ps.setString(3, String.valueOf(Controller.this.PracIDField.getText()));
            ps.execute();
            conn.close();
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void DeleteKonto(){
        KontoData getrow = listofKonto.getSelectionModel().getSelectedItem();
        try{
            Connection conn = dbConnection.getConnection();

            if(!getrow.toString().equals("")){
                String imie = getrow.getimie();
                String typ = getrow.gettyp();
                String data = getrow.getdata();
                String sID = null;

                String deleteQuery = "Delete from konto where ID =" + idKontoField.getText() + ";";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzeine usuniecia");
                alert.setHeaderText("Czy chesz usunac konto?");
                alert.setContentText("" + getrow.getimie()+" "+getrow.gettyp()+"?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==ButtonType.OK){
                    PreparedStatement stm = conn.prepareStatement(deleteQuery);
                    stm.execute();

                    listAllKontoBtn.fire();
                }
            }
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void editKonto(ActionEvent event){
        String query = "Update konto set username=?, password=?, pracownik_id=? where ID =?";
        try{
            Connection conn = dbConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, String.valueOf(Controller.this.UsernameField.getText()));
            stm.setString(2, String.valueOf(Controller.this.PasswordField.getText()));
            stm.setString(3, String.valueOf(Controller.this.PracIDField.getText()));
            stm.setString(4, String.valueOf(Controller.this.idKontoField.getText()));

            stm.execute();
            conn.close();
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }

    public void AddOpieka(){
        String insertquery = "Insert into zwierze_opiekun(opiekun_ID, zwierze_id) values(?, ?);";
        try{
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(insertquery);
            ps.setString(1, String.valueOf(Controller.this.opiekunField.getText()));
            ps.setString(2, String.valueOf(Controller.this.zwierzeField.getText()));
            ps.execute();
            conn.close();
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }
    public String findProperKontoID(){
        try{
            KontoData getSelectedRow = this.listofKonto.getSelectionModel().getSelectedItem();
            String imie = getSelectedRow.getimie();
            String typ = getSelectedRow.gettyp();
            String data = getSelectedRow.getdata();
            String sID = null;

            try{
                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT ID from konto where username = ? AND password =? AND pracownik_id  =?;");
                ps.setString(1, imie);
                ps.setString(2, typ);
                ps.setString(3, data);
                ResultSet res = ps.executeQuery();
                while(res.next()){
                    sID = res.getString(1);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return sID;
        }catch (NullPointerException e){

        }
        return null;
    }


    @FXML
    public void loadOpiekaData(){
        String query = "select * from zwierze_opiekun;";
        try{//select (Select imie from pracownik where opiekun_id = ID), (SELECT imie from zwierze where zwierze_id = ID)
            Connection conn = dbConnection.getConnection();
            this.OpiekaList = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(query);

            while(rs.next()){
                this.OpiekaList.add(new OpiekaData(rs.getString(1),
                        rs.getString(2)
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        this.idOpiekunColumn.setCellValueFactory(new PropertyValueFactory<OpiekaData, String>("ID"));
        this.idZwierzeColumn.setCellValueFactory(new PropertyValueFactory<OpiekaData, String>("imie"));
        this.listofOpieka.setItems(null);
        this.listofOpieka.setItems(this.OpiekaList);

        Counter2 =1;
    }


    @FXML
    public void DeleteOpieka(){
        OpiekaData getrow = listofOpieka.getSelectionModel().getSelectedItem();
        try{
            Connection conn = dbConnection.getConnection();

            if(!getrow.toString().equals("")){

                String deleteQuery = "Delete from zwierze_opiekun where opiekun_ID =" + opiekunField.getText() + " AND zwierze_ID ="+ zwierzeField.getText()+";";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzeine usuniecia");
                alert.setHeaderText("Czy chesz usunac opiekę?");
                alert.setContentText("Tak czy nie ?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==ButtonType.OK){
                    PreparedStatement stm = conn.prepareStatement(deleteQuery);
                    stm.execute();

                    listAllZwierzeBtn.fire();
                }
            }
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void editOpieka(ActionEvent event){
        String query = "Update zwierze_opiekun set opiekun_ID=?, zwierze_id=? where opiekun_ID =? AND zwierze_id = ?;";
        try{
            Connection conn = dbConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, String.valueOf(Controller.this.opiekunnowyField.getText()));
            stm.setString(2, String.valueOf(Controller.this.zwierzenowyField.getText()));
            stm.setString(3, String.valueOf(Controller.this.opiekunField.getText()));
            stm.setString(4, String.valueOf(Controller.this.zwierzeField.getText()));

            stm.execute();
            conn.close();
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void selectedTab(){
        if(Zwierzeta_tab.isSelected()){
            ObservableList<ZwierzeData> sRow = listofZwierze.getSelectionModel().getSelectedItems();

            sRow.addListener(new ListChangeListener() {
                @Override
                public void onChanged(Change c) throws NullPointerException {
                    try{
                        ZwierzeData selectedR = listofZwierze.getSelectionModel().getSelectedItem();
                        String sid = findProperZwierzeID();
                        idZwierzeField.setText(sid);
                        imieZwField.setText(selectedR.getimie());
                        TypField.setText(selectedR.gettyp());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        try{
                            DataZwField.setValue(LocalDate.parse(selectedR.getdata(), formatter));
                        }catch(DateTimeParseException | NullPointerException e){
                            DataZwField.setValue(null);
                        }

                    }catch(NullPointerException e){
                        idZwierzeField.setText(null);
                        imieZwField.setText(null);
                        TypField.setText(null);
                        DataZwField.setValue(null);
                    }
                }
            });
        }
        if(Kontotab.isSelected()){
            ObservableList<KontoData> sRow = listofKonto.getSelectionModel().getSelectedItems();

            sRow.addListener(new ListChangeListener() {
                @Override
                public void onChanged(Change c) throws NullPointerException {
                    try{
                        KontoData selectedR = listofKonto.getSelectionModel().getSelectedItem();
                        String sid = findProperKontoID();
                        idKontoField.setText(sid);
                        UsernameField.setText(selectedR.getimie());
                        PasswordField.setText(selectedR.gettyp());
                        PracIDField.setText(selectedR.getdata());
                    }catch(NullPointerException e){
                        idKontoField.setText(null);
                        UsernameField.setText(null);
                        PasswordField.setText(null);
                         PracIDField.setText(null);
                    }
                }
            });
        }
        if(Opiekatab.isSelected()){
            ObservableList<OpiekaData> sRow = listofOpieka.getSelectionModel().getSelectedItems();

            sRow.addListener(new ListChangeListener() {
                @Override
                public void onChanged(Change c) throws NullPointerException {
                    try{
                        OpiekaData selectedR = listofOpieka.getSelectionModel().getSelectedItem();
                        opiekunField.setText(selectedR.getID());
                        zwierzeField.setText(selectedR.getimie());
                    }catch(NullPointerException e){
                        idKontoField.setText(null);
                        UsernameField.setText(null);
                    }
                }
            });
        }
    }


}
