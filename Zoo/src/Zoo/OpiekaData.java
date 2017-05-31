package Zoo;

/**
 * Created by msegun on 30.05.17.
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.net.SocketImpl;

public class OpiekaData {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty imie;

    public OpiekaData(String id, String imie){
        this.ID = new SimpleStringProperty(id);
        this.imie = new SimpleStringProperty(imie);
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

    public String getimie(){
        return imie.get();
    }

    public SimpleStringProperty imieProperty(){
        return imie;
    }

    public void setimie(String imie){
        this.imie.set(imie);
    }

}
