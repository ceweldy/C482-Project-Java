package C482;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;


public class Outsourced extends Parts {

    private final StringProperty companyName;

    public Outsourced(){
        super();
        companyName = new SimpleStringProperty();
    }


    // Getter
    public String getCompanyName(){
        return this.companyName.get();
    }

    // Setter
    public void setCompanyName(String name){
        this.companyName.set(name);
    }
}
