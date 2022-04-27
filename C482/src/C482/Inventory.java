package C482;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


public class Inventory {

    private static ObservableList<Parts> parts = FXCollections.observableArrayList();
    private static ObservableList<Products> products = FXCollections.observableArrayList();


    // Add Parts or Products
    public static void addPart(Parts p){
        parts.add(p);
    }

    public static void addProduct(Products p){
        products.add(p);
    }


    // Lookup Parts or Products
    public static boolean checkForInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public static int lookupPart(String s){
        s = s.toLowerCase();
        int index = 0;
        boolean found = false;

        for(int i=0; i<parts.size(); i++){
            if(parts.get(i).getName().toLowerCase().contains(s) && (s != "")){
                index = i;
                found = true;
            }
            else if(checkForInt(s)){
                if(Integer.parseInt(s) == parts.get(i).getID()){
                    index = i;
                    found = true;
                }
            }
        }

        if(found){
            return index;
        }
        else{
            return -1; // Not Found
        }
    }

    public static int lookupProduct(String s){
        s = s.toLowerCase();
        int index = 0;
        boolean found = false;

        for(int i=0; i<products.size(); i++){
            if(products.get(i).getName().toLowerCase().contains(s) && (s != "")){
                index = i;
                found = true;
            }
            else if(checkForInt(s)){
                if(Integer.parseInt(s) == products.get(i).getID()){
                    index = i;
                    found = true;
                }
            }
        }
        if(found){
            return index;
        }
        else{
            return -1; // Not Found
        }
    }


    // Update Parts or Products
    public static void updatePart(int partIndex, Parts p){
        parts.set(partIndex, p);
    }

    public static void updateProduct(int productIndex, Products p){
        products.set(productIndex, p);
    }


    // Delete Parts or Products
    public static boolean deletePart(Parts p){
        boolean delete = false;
        boolean canDelete = true;
        for(int i=0; i<products.size(); i++){
            if(products.get(i).getAssociatedParts().contains(parts)){
                canDelete = false;
            }
        }
        if(canDelete == true){
            delete = true;
            parts.remove(p);
        }
        return delete;
    }

    public static boolean deleteProduct(Products p){
        boolean delete = false;
        boolean canDelete = true;
        for(int i=0; i<products.size(); i++){
            if(products.get(i).getID() == p.getID()){
                if(!products.get(i).getAssociatedParts().isEmpty()) {
                    canDelete = false;
                }
            }
        }
        if(canDelete == true){
            delete = true;
            products.remove(p);
        }
        return delete;
    }


    // Get All Parts or Products
    public static ObservableList<Parts> getAllParts(){
        return parts;
    }

    public static ObservableList<Products> getAllProducts(){
        return products;
    }


}
