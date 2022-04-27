package C482;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class AddParts implements Initializable {

    @FXML
    private Label sourceLabel;
    @FXML
    private TextField partSource;
    @FXML
    private TextField partID;
    @FXML
    private TextField partName;
    @FXML
    private TextField partInventory;
    @FXML
    private TextField partPrice;
    @FXML
    private TextField partMin;
    @FXML
    private TextField partMax;
    @FXML
    private RadioButton partInHouse;
    @FXML
    private RadioButton partOutsource;

    // inHouse Part boolean and unique part ID
    private boolean inHouse;
    private int ID;

    // Outsourced Part
    @FXML
    void outsourcedPart(ActionEvent event){
        sourceLabel.setText("Company");
        partSource.setPromptText("Company Name of Part");
        inHouse = false;
    }

    // InHouse Part
    @FXML
    void inHousePart(ActionEvent event){
        sourceLabel.setText("Machine ID");
        partSource.setPromptText("Machine ID of Part");
        inHouse = true;
    }

    // Save Button
    @FXML
    void save(ActionEvent event) throws IOException{
        // Input Variables
        String name = partName.getText();
        String inventory = partInventory.getText();
        String price = partPrice.getText();
        String min = partMin.getText();
        String max = partMax.getText();
        String source = partSource.getText();

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

            if(error != null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Error Adding New Part");
                alert.setContentText(error);
                alert.showAndWait();
                error = null;
            }
            else{
                if(inHouse){
                    int machineID = Integer.parseInt(source);
                    InHouse part = new InHouse();
                    part.setID(ID);
                    part.setName(name);
                    part.setStock(Integer.parseInt(inventory));
                    part.setPrice(Double.parseDouble(price));
                    part.setMin(Integer.parseInt(min));
                    part.setMax(Integer.parseInt(max));
                    part.setMachineId(machineID);
                    Inventory.addPart(part);
                }
                else{
                    Outsourced part = new Outsourced();
                    part.setID(ID);
                    part.setName(name);
                    part.setStock(Integer.parseInt(inventory));
                    part.setPrice(Double.parseDouble(price));
                    part.setMin(Integer.parseInt(min));
                    part.setMax(Integer.parseInt(max));
                    part.setCompanyName(source);
                    Inventory.addPart(part);
                }

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
            alert.setHeaderText("Error Adding New Part");
            alert.setContentText("Please fill out all sections.");
            alert.showAndWait();
        }
    }

    // Cancel Button
    @FXML
    private void cancel(ActionEvent event) throws IOException{
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
        ToggleGroup tg = new ToggleGroup();
        partOutsource.setToggleGroup(tg);
        partInHouse.setToggleGroup(tg);

        ID = Inventory.getAllParts().size();
        partID.setText("Generated ID: " + ID);
    }
}
