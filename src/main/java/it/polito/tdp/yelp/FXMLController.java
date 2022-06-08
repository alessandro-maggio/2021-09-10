/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnDistante"
    private Button btnDistante; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcolaPercorso"
    private Button btnCalcolaPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB1"
    private ComboBox<Business> cmbB1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB2"
    private ComboBox<Business> cmbB2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	if(cmbCitta.getValue()==null) {
        	txtResult.clear();
        	txtResult.appendText("Selezionare una citta!\n");
        	return;
        }
    		
    		String result= this.model.creaGrafo(cmbCitta.getValue());
        	txtResult.setText(result);
        	cmbB1.getItems().clear();
        	cmbB2.getItems().clear();
        	cmbB1.getItems().addAll(this.model.getVertici());
        	cmbB2.getItems().addAll(this.model.getVertici());

    }

    @FXML
    void doCalcolaLocaleDistante(ActionEvent event) {
    	
    	try {
    		
    		String result=  this.model.getPiuDistante(cmbB1.getValue());
        	txtResult.clear();
        	txtResult.setText(result);
    		
    	}catch(NullPointerException e) {
    		txtResult.clear();
    		txtResult.appendText("Selezionare il Locale (b1)!\nSe non si visulaizza nessun locale, creare prima il grafo.");
    	}
    	
    	

    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	try {
    		
    		if(cmbB2.getValue()==null) {
    			txtResult.clear();
    			txtResult.appendText("Selezionare Locale(b2)!\nSe non si visualizza la lista, creare prima il grafo");
    			return;
    		}
    		
    		txtResult.clear();
        	
        	double soglia= Double.parseDouble(txtX2.getText());
        	
        	Business b1= cmbB1.getValue();
        	Business b2= cmbB2.getValue();
        	
        	
        	List<Business> percorso= this.model.cercaPercorso(b1, b2, soglia);
        	
        	for(Business b: percorso) {
        		txtResult.appendText(b.getBusinessName()+"\n");
        	}
        	
        	txtResult.appendText("Chilometri totali percorsi: "+this.model.getKmTOT()+"\n");
    		
    	}catch(NumberFormatException e) {
    		txtResult.clear();
    		txtResult.appendText("Inserire un valore di soglia valido!\n");
    	}
    	
    	
    	

    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDistante != null : "fx:id=\"btnDistante\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB1 != null : "fx:id=\"cmbB1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB2 != null : "fx:id=\"cmbB2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbCitta.getItems().addAll(this.model.getCitta());
    	
    }
}
