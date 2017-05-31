package Zoo;

/**
 * Created by msegun on 30.05.17.
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.net.SocketImpl;

public class KontoData {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty imie;
    private final SimpleStringProperty typ;
    private final SimpleStringProperty data;

    public KontoData(String id, String imie, String typ, String data){
        this.ID = new SimpleStringProperty(id);
        this.imie = new SimpleStringProperty(imie);
        this.typ = new SimpleStringProperty(typ);
        this.data = new SimpleStringProperty(data);
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

    public String gettyp(){
        return typ.get();
    }

    public SimpleStringProperty typProperty(){
        return typ;
    }

    public void settyp(String typ){
        this.typ.set(typ);
    }

    public String getdata(){
        return data.get();
    }

    public SimpleStringProperty dataProperty(){
        return data;
    }

    public void setdata(String data){
        this.data.set(data);
    }
}
