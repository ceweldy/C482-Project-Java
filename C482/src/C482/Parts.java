package C482;

import javafx.beans.property.*;

public class Parts {

    private final IntegerProperty ID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty stock;
    private final IntegerProperty min;
    private final IntegerProperty max;

    public Parts(){ // Constructor
        ID = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        stock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
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
    public IntegerProperty minProperty(){
        return min;
    }
    public IntegerProperty maxProperty(){
        return max;
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

}
