package Zoo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Created by msegun on 29.05.17.
 */

public class StanowiskoData {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty nazwa;
    private final SimpleStringProperty pensja;

    public StanowiskoData(String id, String nazwa, String pensja){
        this.ID = new SimpleStringProperty(id);
        this.nazwa = new SimpleStringProperty(nazwa);
        this.pensja = new SimpleStringProperty(pensja);
    }

    public String getID(){
        return ID.get();
    }

    public SimpleStringProperty IDProperty(){
        return ID;
    }

    public void setID(String ID){
        this.ID.set(ID);
    }

    public String getNazwa(){
        return nazwa.get();
    }

    public SimpleStringProperty NazwaProperty(){
        return nazwa;
    }

    public void setNazwa(String nazwa){
        this.nazwa.set(nazwa);
    }

    public String getPensja(){
        return pensja.get();
    }

    public SimpleStringProperty pensjaProperty(){
        return pensja;
    }

    public void setPensja(String pensja){
        this.pensja.set(pensja);
    }
}
