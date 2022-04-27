package C482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProducts implements Initializable {

    // TextFields
    @FXML
    private TextField productID;
    @FXML
    private TextField productName;
    @FXML
    private TextField productInventory;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productMin;
    @FXML
    private TextField productMax;
    @FXML
    private TextField searchBar;

    // Tables
    @FXML
    private TableView<Parts> addPartsTableView;
    @FXML
    private TableColumn<Parts, Integer> addPartsIdTableColumn;
    @FXML
    private TableColumn<Parts, String> addPartsNameTableColumn;
    @FXML
    private TableColumn<Parts, Integer> addPartsInventoryTableColumn;
    @FXML
    private TableColumn<Parts, Double> addPartsPriceTableColumn;
    @FXML
    private TableView<Parts> removePartsTableView;
    @FXML
    private TableColumn<Parts, Integer> removePartsIdTableColumn;
    @FXML
    private TableColumn<Parts, String> removePartsNameTableColumn;
    @FXML
    private TableColumn<Parts, Integer> removePartsInventoryTableColumn;
    @FXML
    private TableColumn<Parts, Double> removePartsPriceTableColumn;

    // Unique product ID
    private int ID;

    // Parts in the Product
    private ObservableList<Parts> partsInProduct = FXCollections.observableArrayList();

    // Update Tables
    private void updateAddPartsTable(){
        addPartsTableView.setItems(Inventory.getAllParts());
    }
    private void updateRemovePartsTable(){
        removePartsTableView.setItems(partsInProduct);
    }


    // Add and Remove Parts
    @FXML
    private void addPart(ActionEvent event){
        Parts p = addPartsTableView.getSelectionModel().getSelectedItem();
        partsInProduct.add(p);
        updateRemovePartsTable();
    }

    @FXML
    private void removePart(ActionEvent event){
        Parts p = removePartsTableView.getSelectionModel().getSelectedItem();
        partsInProduct.remove(p);
        updateRemovePartsTable();
    }

    // Search Bar
    @FXML
    private void searchPartsTable(KeyEvent event){
        String search = searchBar.getText();
        ObservableList<Parts> newPartList = FXCollections.observableArrayList();

        if(search == ""){
            updateAddPartsTable();
        }
        else if(Inventory.lookupPart(search) == -1){
            addPartsTableView.setItems(newPartList);
        }
        else{
            Parts newPart;
            for(int i=0; i<Inventory.getAllParts().size(); i++){
                if(Inventory.getAllParts().get(i).getName().contains(search)){
                    newPart = Inventory.getAllParts().get(i);
                    newPartList.add(newPart);
                }
                else if(Inventory.checkForInt(search)){
                    if(Integer.parseInt(search) == Inventory.getAllParts().get(i).getID()){
                        newPart = Inventory.getAllParts().get(i);
                        newPartList.add(newPart);
                    }
                }
            }
            addPartsTableView.setItems(newPartList);
        }
    }

    // Save Button
    @FXML
    void save(ActionEvent event) throws IOException{
        // Input Variables
        String name = productName.getText();
        String inventory = productInventory.getText();
        String price = productPrice.getText();
        String min = productMin.getText();
        String max = productMax.getText();

        // Exception Handling
        try{
            String error = null;
            if(name == null){
                error = error + "A name is required. ";
            }
            if(Integer.parseInt(inventory) < 0){
                error = error + "Inventory must be a positive number. ";
            }
            if(Double.parseDouble(price) < 0){
                error = error + "Price must be a positive number. ";
            }
            if(Integer.parseInt(min) > Integer.parseInt(inventory) || Integer.parseInt(min) < 0 || Integer.parseInt(min) > Integer.parseInt(max)){
                error = error + "Minimum inventory must be less than or equal to the inventory, greater than 0, and less than the max. ";
            }
            if(Integer.parseInt(max) < Integer.parseInt(inventory) || Integer.parseInt(max) < 0 || Integer.parseInt(max) < Integer.parseInt(min)){
                error = error + "Maximum inventory must be greater than or equal to the inventory, greater than 0, and greater than the minimum. ";
            }
            if(partsInProduct.isEmpty()){
                error = error + "A part must be added to the product";
            }

            if(error != null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error Adding New Part");
                alert.setContentText(error);
                alert.showAndWait();
                error = null;
            }
            else{
                Products product = new Products();
                product.setID(ID);
                product.setName(name);
                product.setStock(Integer.parseInt(inventory));
                product.setPrice(Double.parseDouble(price));
                product.setMin(Integer.parseInt(min));
                product.setMax(Integer.parseInt(max));
                product.setAssociatedParts(partsInProduct);
                Inventory.addProduct(product);

                // Change scene back to main screen
                Parent savePart = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(savePart);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Error Adding New Product");
            alert.setContentText("Please fill out all sections.");
            alert.showAndWait();
        }
    }

    // Cancel Button
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Cancel");
        a.setContentText("Would you like to cancel?");
        Optional<ButtonType> choice = a.showAndWait();
        if(choice.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent cancelPart = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(cancelPart);
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartsIdTableColumn.setCellValueFactory(data -> data.getValue().IDProperty().asObject());
        addPartsNameTableColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        addPartsInventoryTableColumn.setCellValueFactory(data -> data.getValue().stockProperty().asObject());
        addPartsPriceTableColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

        removePartsIdTableColumn.setCellValueFactory(data -> data.getValue().IDProperty().asObject());
        removePartsNameTableColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        removePartsInventoryTableColumn.setCellValueFactory(data -> data.getValue().stockProperty().asObject());
        removePartsPriceTableColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

        ID = Inventory.getAllParts().size();
        productID.setText("Generated ID: " + ID);
        updateAddPartsTable();
        updateRemovePartsTable();
    }
}
