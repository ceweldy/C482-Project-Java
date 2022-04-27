package C482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
public class MainScreen implements Initializable {

    // Tables
    @FXML
    private TableView<Parts> partsTableView;
    @FXML
    private TableColumn<Parts, Integer> partsIdTableColumn;
    @FXML
    private TableColumn<Parts, String> partsNameTableColumn;
    @FXML
    private TableColumn<Parts, Integer> partsInventoryTableColumn;
    @FXML
    private TableColumn<Parts, Double> partsPriceTableColumn;

    @FXML
    private TableView<Products> productsTableView;
    @FXML
    private TableColumn<Products, Integer> productsIdTableColumn;
    @FXML
    private TableColumn<Products, String> productsNameTableColumn;
    @FXML
    private TableColumn<Products, Integer> productsInventoryTableColumn;
    @FXML
    private TableColumn<Products, Double> productsPriceTableColumn;

    // TextFields
    @FXML
    private TextField searchParts;
    @FXML
    private TextField searchProducts;

    // Update Tables on change
    private void updatePartsTable(){
        partsTableView.setItems(Inventory.getAllParts());
    }
    private void updateProductsTable(){
        productsTableView.setItems(Inventory.getAllProducts());
    }

    // Modify Part/Product Index Integer
    private static Parts modifyPart;
    private static int partsIndex;
    public static int getPartsIndex(){
        return partsIndex;
    }
    private static Products modifyProduct;
    private static int productsIndex;
    public static int getProductsIndex(){
        return productsIndex;
    }

    // Search Bars
    @FXML
    private void searchPartsTable(KeyEvent event){
        String search = searchParts.getText();
        ObservableList<Parts> newPartList = FXCollections.observableArrayList();

        if(search == ""){
            updatePartsTable();
        }
        else if(Inventory.lookupPart(search) == -1){
            partsTableView.setItems(newPartList);
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
            partsTableView.setItems(newPartList);
        }
    }

    @FXML
    private void searchProductsTable(){
        String search = searchProducts.getText();
        ObservableList<Products> newProductList = FXCollections.observableArrayList();

        if(search == ""){
            updateProductsTable();
        }
        else if(Inventory.lookupProduct(search) == -1){
            productsTableView.setItems(newProductList);
        }
        else{
            Products newProduct;
            for(int i=0; i<Inventory.getAllParts().size(); i++){
                if(Inventory.getAllParts().get(i).getName().contains(search)){
                    newProduct = Inventory.getAllProducts().get(i);
                    newProductList.add(newProduct);
                }
                else if(Inventory.checkForInt(search)){
                    if(Integer.parseInt(search) == Inventory.getAllParts().get(i).getID()){
                        newProduct = Inventory.getAllProducts().get(i);
                        newProductList.add(newProduct);
                    }
                }
            }
            productsTableView.setItems(newProductList);
        }
    }

    // Add Parts or Products
    @FXML
    private void addParts(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("AddParts.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addProducts(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("AddProducts.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Modify Parts or Products
    @FXML
    private void modifyParts(ActionEvent event) throws IOException{
        modifyPart = partsTableView.getSelectionModel().getSelectedItem();
        partsIndex = Inventory.getAllParts().indexOf(modifyPart);
        Parent root = FXMLLoader.load(getClass().getResource("ModifyParts.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyProducts(ActionEvent event) throws IOException{
        modifyProduct = productsTableView.getSelectionModel().getSelectedItem();
        productsIndex = Inventory.getAllProducts().indexOf(modifyProduct);
        Parent root = FXMLLoader.load(getClass().getResource("ModifyProducts.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Delete Parts or Products
    @FXML
    private void deleteParts(ActionEvent event){
        Parts selectedItem = partsTableView.getSelectionModel().getSelectedItem();
        if(Inventory.deletePart(selectedItem)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Part Deleted");
            alert.showAndWait();
            updatePartsTable();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Error deleting part: Part is in a product.");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteProducts(ActionEvent event){
        Products selectedItem = productsTableView.getSelectionModel().getSelectedItem();

        if(selectedItem.getAssociatedParts().isEmpty()){
            Inventory.deleteProduct(selectedItem);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Product Deleted");
            alert.showAndWait();
            updateProductsTable();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error deleting product: Product contains parts.");
            alert.setContentText("Deleting parts within the Product");
            alert.showAndWait();
            ObservableList<Parts> deleteAllParts = FXCollections.observableArrayList();
            selectedItem.setAssociatedParts(deleteAllParts);
            Inventory.deleteProduct(selectedItem);
            updateProductsTable();
        }
    }

    // Exit Button
    @FXML
    private void exit(ActionEvent event){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Exit");
        a.setContentText("Would you like to exit?");
        Optional<ButtonType> choice = a.showAndWait();
        if(choice.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    // Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        partsIdTableColumn.setCellValueFactory(data -> data.getValue().IDProperty().asObject());
        partsNameTableColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        partsInventoryTableColumn.setCellValueFactory(data -> data.getValue().stockProperty().asObject());
        partsPriceTableColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

        productsIdTableColumn.setCellValueFactory(data -> data.getValue().IDProperty().asObject());
        productsNameTableColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        productsInventoryTableColumn.setCellValueFactory(data -> data.getValue().stockProperty().asObject());
        productsPriceTableColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());

        updatePartsTable();
        updateProductsTable();
    }
}
