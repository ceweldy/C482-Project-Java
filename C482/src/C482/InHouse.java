package C482;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class InHouse extends Parts {

    private final IntegerProperty ID;

    public InHouse(){
        super();
        ID = new SimpleIntegerProperty();
    }


    // Getter
    public int getMachineId(){
        return this.ID.get();
    }

    // Setter
    public void setMachineId(int ID){
        this.ID.set(ID);
    }
}
