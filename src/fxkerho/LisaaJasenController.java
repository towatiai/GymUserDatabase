package fxkerho;

import java.util.List;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import kuntosali.Jasen;
import kuntosali.Kuntosali;
import kuntosali.Hintaryhma;
import kuntosali.SailoException;
import fxkerho.KuntosaliGUIController;

/** KUNTOSALIN JÄSENREKISTERI
 * Controller-luokka jäsenien lisäykselle. 
 * @author Toni Tiainen ja Ville Nykänen
 * @version 1.5.2017
 */
public class LisaaJasenController implements ModalControllerInterface<String> {
	
	Kuntosali kuntosali;
	KuntosaliGUIController kuntoCtrl;
	Jasen muokattavaJasen = null;
	Boolean uusiJasen;
	String hinta;
	
	List<Hintaryhma> hintaryhmat;
	
	/** Asettaa tälle kontrollerille oliomuuttujat, jotta voidaan käyttää niiden metodeja. 
	 * @param kuntosali Kuntosali-tietorakenne
	 * @param kuntoCtrl KuntosaliGUIController
	 */
	public void setKuntosali(Kuntosali kuntosali, KuntosaliGUIController kuntoCtrl) {
		this.kuntoCtrl = kuntoCtrl;
		this.kuntosali = kuntosali;
	}

    @FXML
    private TextField textSukunimi;

    @FXML
    private TextField textHetu;

    @FXML
    private TextField textKatuosoite;

    @FXML
    private TextField textPostinumero;

    @FXML
    private TextField textPuhelinnumero;

    @FXML
    private TextField textLiittymispaiva;

    @FXML
    private TextField textHinta;

    @FXML
    private TextField textEtunimi;
    
    @FXML
    private ComboBoxChooser<String> comboRyhma;

    @FXML
    private ComboBoxChooser<String> comboKesto = new ComboBoxChooser<>();
    

    /**
     * 
     */
    @FXML
    public void handleLataaHinta() {
    	try {
    		hinta = hintaryhmat.get(comboKesto.getSelectedIndex()).getHinta(comboRyhma.getSelectedIndex());
    		textHinta.setText(hinta);
    	} catch (ArrayIndexOutOfBoundsException e) {
    		System.err.println(e.getMessage());
    		return;
    	}
    }
    

    @FXML
    void handlePeruuta() throws SailoException {
    	ModalController.closeStage(textEtunimi);
    	kuntoCtrl.haeListaan(1, "");
    }

    @FXML
    void handleTallenna() throws SailoException {
    	if (tarkista()) return;
    	handleLataaHinta();
    	if (uusiJasen) uusiJasen();
    	else muokkaaJasen();
    	kuntoCtrl.haeListaan(1, "");
    	ModalController.closeStage(textEtunimi);
    }

	@Override
	public String getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleShown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefault(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//============================================================================================================

	/**
	 * Lisää tiedot jäseneen.
	 * @param jasen Jäsen, jonka tiedot halutaan lisätä.
	 */
	private void lisaaTiedot (Jasen jasen) {
		jasen.LisaaTiedot(
				textEtunimi.getText(),
				textSukunimi.getText(),
				textHetu.getText(),
				textKatuosoite.getText(),
				textPostinumero.getText(),
				textPuhelinnumero.getText(),
				textLiittymispaiva.getText(),
				hinta);
	}
	
	/**
	 * Tarkistaa, ovatko annetut tiedot käyttökelvolliset.
	 * @return Palauttaa true, jos tiedoissa on virheitä, false, jos ei ole.
	 */
	private boolean tarkista() {
		
		if (textEtunimi.getText().matches(".*\\d+.*") || textSukunimi.getText().matches(".*\\d+.*")) {
			Dialogs.showMessageDialog("Nimissä ei saa olla numeroita.");
			return true;
		}
		
		if (!textPuhelinnumero.getText().matches("^-?\\d+$")) {
			Dialogs.showMessageDialog("Puhelinnumerossa ei saa olla kirjaimia.");
			return true;
		}
		
		String[] TEMP = textLiittymispaiva.getText().split("\\.");
		int[] pvm = new int[TEMP.length];
		try {
			for (int i = 0; i < TEMP.length; i++) {
				pvm[i] = Integer.parseInt(TEMP[i]);
			}
		} catch (@SuppressWarnings("unused") NumberFormatException e) {
			Dialogs.showMessageDialog("Päivämäärä ei kelpaa.");
			return true;
		}
		if (pvm.length != 3) {
			Dialogs.showMessageDialog("Päivämäärä ei kelpaa.");
			return true;
		}
		if (pvm[0] > 28 && pvm[1] == 2 && pvm[2] % 4 != 0 && pvm[2] % 100 != 0) {
			Dialogs.showMessageDialog("Päivämäärä ei kelpaa.");
			return true;
		}
		if ((pvm[1] == 4 || 
				pvm[1] == 6 || 
				pvm[1] == 9 || 
				pvm[1] == 11) && pvm[0] > 30) {
			Dialogs.showMessageDialog("Päivämäärä ei kelpaa.");
			return true;
		}
		if (pvm[0] > 31 || pvm[0] < 1) {
			Dialogs.showMessageDialog("Päivämäärä ei kelpaa.");
			return true;
		}
		if (pvm[1] > 12 || pvm[1] < 1) {
			Dialogs.showMessageDialog("Päivämäärä ei kelpaa.");
			return true;
		}
		if (pvm[2] < 1800) {
			Dialogs.showMessageDialog("Päivämäärä ei kelpaa.");
			return true;
		}
		
		return false;
	}


	/**
	 * Lisätään jo olemassa olevaan jäseneen uudet tiedot.
	 * @throws SailoException 
	 */
	protected void muokkaaJasen() throws SailoException {
		lisaaTiedot(muokattavaJasen);
		kuntosali.paivitaTiedot(muokattavaJasen);
	}
	
	/**
	 * Luodaan uusi jäsen ja viedään sille halutut tiedot.
	 */
	protected void uusiJasen(){
		Jasen uusi = new Jasen();
		//uusi.luoTestiMake(); //Jos halutaan testata automaattisilla tiedonlisäyksillä. Muista kytkeä tarkastus pois päältä!!
		lisaaTiedot(uusi);
		try {
			kuntosali.lisaa(uusi);
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Ongelmia uuden jäsenen luomisessa." + e.getMessage());
			return;
		}
		
	}
	

	/**Tuo muokattavan jäsenen tiedot tähän controlleriin
	 * @param jasenKohdalla
	 */
	public void setJasen(Jasen jasenKohdalla) {
		muokattavaJasen = jasenKohdalla;
		this.textEtunimi.setText(muokattavaJasen.getNimi(false));
		this.textSukunimi.setText(muokattavaJasen.getSukunimi(false));
		this.textHetu.setText(muokattavaJasen.getHetu());
		this.textKatuosoite.setText(muokattavaJasen.getKatuosoite());
		this.textLiittymispaiva.setText(muokattavaJasen.getLiittymispaiva());
		this.textPostinumero.setText(muokattavaJasen.getPostinumero());
		this.textPuhelinnumero.setText(muokattavaJasen.getPuhelinnumero());
		this.hinta = muokattavaJasen.getHinta();
		this.textHinta.setText(hinta);
	}

	/** Antaa luokalle tiedon onko lisättävä jäsen uusi, vai halutaanko muokata jo olemassa olevan jäsenen tietoja
	 * @param onUusi totuusarvo
	 */
	public void onkoUusi(boolean onUusi) {
		uusiJasen = onUusi;
	}
	
	/** Laittaa ComboBoxiin luodut kestot
	 * @throws SailoException 
     */
    public void setKestot() throws SailoException {
    	comboKesto.clear();
    	hintaryhmat = kuntosali.getKestot();
    	String[] kestot = new String[hintaryhmat.size()];
    	if (!hintaryhmat.isEmpty()) {
    		for (int i = 0; i < hintaryhmat.size(); i++) {
    			kestot[i] = Integer.toString(hintaryhmat.get(i).getKesto());
    		}
    		comboKesto.setRivit(kestot);
    		this.textHinta.setText(hintaryhmat.get(0).getHinta(0));
    	} else {
    		Dialogs.showMessageDialog("Huom! Hintavaihtoehtoja ei asetettu!");
    	}
    }
}
