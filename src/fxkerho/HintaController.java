package fxkerho;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.*;
import javafx.scene.control.TextField;
import kuntosali.Hintaryhma;
import kuntosali.Kuntosali;
import kuntosali.SailoException;

/** KUNTOSALIN JÄSENREKISTERI
 * Controller-luokka hintaryhmien lisäykselle. 
 * @author Toni Tiainen ja Ville Nykänen
 * @version 1.5.2017
 */
public class HintaController {

	    @FXML
	    private TextField hintaKesto;

	    @FXML
	    private TextField hintaAikuinen;

	    @FXML
	    private TextField hintaNuori;

	    @FXML
	    private TextField hintaOpiskelija;

	    @FXML
	    void handlePeruuta() {
	    	ModalController.closeStage(hintaAikuinen);
	    }

	    @FXML
	    void handleTallenna() {
	    	LisaaHinnat();
	    	ModalController.closeStage(hintaAikuinen);
	    }
	    
//=============================================================================
	    
	Kuntosali kuntosali;

	/** Lisää hinnat hintaryhmään.
	 * 
	 */
	private void LisaaHinnat() {
			Hintaryhma uusi = new Hintaryhma();
			uusi.rekisteroi();
			uusi.lisaaHinnat(
					hintaAikuinen.getText(), 
					hintaNuori.getText(), 
					hintaOpiskelija.getText(), 
					hintaKesto.getText()
							);
			try {
				kuntosali.lisaaHinta(uusi);
			} catch (SailoException e) {
				Dialogs.showMessageDialog("Ongelmia uuden hintaryhmän luomisessa." + e.getMessage());
				return;
			}
		}

	/** Pääohjelma. Käytettiin testaamiseen, mutta testit siirrettiin kuntosali-luokkaan.
	 * @param args ei kaytossa
	 */
	public static void main(String[] args) {
		// Testit siirretty kuntosali-luokkaan.

	}

	/** Asettaa mainissa luodun kuntosalin controllerin kuntosaliksi.
	 * @param kuntosali kuntosali
	 */
	public void setKuntosali(Kuntosali kuntosali) {
		this.kuntosali = kuntosali;
		
	}
}
