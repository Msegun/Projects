package Zoo;

/**
 * Created by msegun on 30.05.17.
 */
import javafx.beans.property.SimpleStringProperty;

public class PracownikData {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty imie;
    private final SimpleStringProperty nazw;
    private final SimpleStringProperty dataur;
    private final SimpleStringProperty stanID;

    public PracownikData(String id, String imie, String nazwisko, String datauro, String stanID){
        this.ID = new SimpleStringProperty(id);
        this.imie = new SimpleStringProperty(imie);
        this.nazw = new SimpleStringProperty(nazwisko);
        this.dataur = new SimpleStringProperty(datauro);
        this.stanID = new SimpleStringProperty(stanID);
    }

    public String getID(){
        return ID.get();
    }
    public SimpleStringProperty IDproperty(){
        return ID;
    }

    public void setID(String ID){
        this.ID.set(ID);
    }
    public String getimie(){
        return imie.get();
    }
    public SimpleStringProperty imieProperty(){
        return imie;
    }

    public void setimie(String imie){
        this.imie.set(imie);
    }
    public String getnazwisko(){
        return nazw.get();
    }
    public SimpleStringProperty Nazwiskoproperty(){
        return nazw;
    }

    public void setnazwisko(String nazwisko){
        this.nazw.set(nazwisko);
    }
    public String getdataur(){
        return dataur.get();
    }
    public SimpleStringProperty Dataurproperty(){
        return dataur;
    }

    public void setdataur(String dataur){
        this.dataur.set(dataur);
    }
    public String getStanID(){
        return stanID.get();
    }
    public SimpleStringProperty StanIDproperty(){
        return stanID;
    }

    public void setStanID(String sID){
        this.stanID.set(sID);
    }

}
