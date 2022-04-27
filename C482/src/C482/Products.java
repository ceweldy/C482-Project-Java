package C482;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Products {

    private final IntegerProperty ID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty stock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    private static ObservableList<Parts> associatedParts = FXCollections.observableArrayList();

    public Products(){
        ID = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        stock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }

    public static boolean deleteAssociatedParts(ObservableList<Parts> parts){
        if(associatedParts.contains(parts)){
            associatedParts.remove(parts);
            return true;
        }
        return false;
    }

    // Getters
    public int getID(){
        return this.ID.get();
    }
    public String getName(){
        return this.name.get();
    }
    public double getPrice(){
        return this.price.get();
    }
    public int getStock(){
        return this.stock.get();
    }
    public int getMin(){
        return this.min.get();
    }
    public int getMax(){
        return this.max.get();
    }
    public ObservableList getAssociatedParts(){
        return associatedParts;
    }

    // Get Property for TableView
    public IntegerProperty IDProperty(){
        return ID;
    }
    public StringProperty nameProperty(){
        return name;
    }
    public DoubleProperty priceProperty(){
        return price;
    }
    public IntegerProperty stockProperty(){
        return stock;
    }

    // Setters
    public void setID(int ID){
        this.ID.set(ID);
    }
    public void setName(String name){
        this.name.set(name);
    }
    public void setPrice(double price){
        this.price.set(price);
    }
    public void setStock(int stock){
        this.stock.set(stock);
    }
    public void setMin(int min){
        this.min.set(min);
    }
    public void setMax(int max){
        this.max.set(max);
    }
    public void setAssociatedParts(ObservableList<Parts> parts){
        this.associatedParts = parts;
    }
}
