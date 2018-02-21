package fxkerho;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;
import fxkerho.HintaController;
import fxkerho.LisaaJasenController;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kuntosali.Jasen;
import kuntosali.Kuntosali;
import kuntosali.SailoException;

/** KUNTOSALIN JÄSENREKISTERI
 * Controller-luokka pääikkunalle. 
 * @author Toni Tiainen ja Ville Nykänen
 * @version 1.5.2017
 */
public class KuntosaliGUIController implements Initializable {
	
	
	LisaaJasenController jasenCtrl;
	HintaController hintaCtrl;
	KuntosaliGUIController kuntoCtrl;
	
	/**
	 * @param url
	 * @param bundle 
	 */
	public void initialize(URL url, ResourceBundle bundle) {
		//alusta();
	}



	@FXML
	private TextField textHakuehto;

	@FXML
	private ListChooser<Jasen> chooserJasenet;
	
	@FXML
    void handleHae() throws SailoException {
		String haku = textHakuehto.getText();
		haku = haku.toLowerCase();
		haeListaan(1, haku);
    }
	
	@FXML
    void handleLataa() throws SailoException {
		haeListaan(1, "");
    }

	@FXML
	void handleApua() {
		Dialogs.showMessageDialog("Luo ensin uusi Hinta, jonka jälkeen voit luoda uuden jäsenen 'Lisää Jäsen' valikosta. ");
	}

	@FXML
	void handleMuokkaaJasen() {
		naytaJasen();
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("LisaaJasen.fxml"));
			final Pane root = (Pane)ldr.load();
			final Scene jasenScene = new Scene(root);
			jasenCtrl = (LisaaJasenController)ldr.getController();
			jasenCtrl.setKuntosali(kuntosali, kuntoCtrl);
			jasenCtrl.onkoUusi(false);
			jasenCtrl.setKestot();
			jasenCtrl.setJasen(jasenKohdalla);
			Stage jasenStage = new Stage();
			jasenStage.setScene(jasenScene);
			jasenScene.getStylesheets().add(getClass().getResource("kerho.css").toExternalForm());
			jasenStage.setTitle("Muokkaa jäsenen tietoja");
			
			jasenStage.show();
		} 
		catch(NullPointerException e) {
			Dialogs.showMessageDialog("Muokattavaa jäsentä ei valittu. \nError-message: " + e.getMessage());
			return;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handlePoistaJasen() throws SailoException {
		naytaJasen();
		if (jasenKohdalla != null) {
			kuntosali.poista(jasenKohdalla);
		}
		haeListaan(0, "");
	}
	
	@FXML
    void handleLisaaHinta() {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("LisaaHintaryhma.fxml"));
			final Pane root = (Pane)ldr.load();
			final Scene hintaScene = new Scene(root);
			hintaCtrl = (HintaController)ldr.getController();
			hintaCtrl.setKuntosali(kuntosali);
			Stage jasenStage = new Stage();
			jasenStage.setScene(hintaScene);
			hintaScene.getStylesheets().add(getClass().getResource("kerho.css").toExternalForm());
			jasenStage.setTitle("Lisaa uusi hintaryhmä");
			
			jasenStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

	@FXML
	void handleSulje() {
		tallenna();
		Platform.exit();
	}
	

	@FXML
	void handleTallenna() {
		tallenna();
	}

	@FXML
	void handleTietoja() {
		ModalController.showModal(KuntosaliGUIController.class.getResource("infoa.fxml"), "Tietoja", null, "");
	}

	@FXML
	void handleTulosta() {
		naytaJasen();
		Dialogs.showMessageDialog("Jäsennumero: " + jasenKohdalla.getTunnus() + "\n"
									+ "Etunimi: " + jasenKohdalla.getNimi(false) + "\n" + 
									"Sukunimi: " + jasenKohdalla.getSukunimi(false) + "\n" + 
									"Henkilötunnus: " + jasenKohdalla.getHetu() + "\n" + 
									"Puhelinnumero: " + jasenKohdalla.getPuhelinnumero() + "\n" + 
									"Katuosoite: " + jasenKohdalla.getKatuosoite() + "\n" + 
									"Postinumero: " + jasenKohdalla.getPostinumero() + "\n" + 
									"Hinta: " + jasenKohdalla.getHinta() + " e/kk");
	}

	@FXML
	void handleUusiJasen() {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("LisaaJasen.fxml"));
			final Pane root = (Pane)ldr.load();
			final Scene jasenScene = new Scene(root);
			jasenCtrl = (LisaaJasenController)ldr.getController();
			jasenCtrl.setKuntosali(kuntosali, kuntoCtrl);
			jasenCtrl.onkoUusi(true);
			jasenCtrl.setKestot();
			Stage jasenStage = new Stage();
			jasenStage.setScene(jasenScene);
			jasenScene.getStylesheets().add(getClass().getResource("kerho.css").toExternalForm());
			jasenStage.setTitle("Lisaa Jäsen");
			
			jasenStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//========================================================================================================================

	protected Kuntosali kuntosali;

	private Jasen jasenKohdalla;
	

	/** Sijoittaa jasenKohdalla-muuttujaksi sen jäsenen, mikä kulloinkin on valittu.
	 *  Antaa virheilmoituksen jos jäsentä ei ole valittu, mutta jäsenen tietoja haluttaisiin käyttää.
	 */
	protected void naytaJasen() {
		jasenKohdalla = chooserJasenet.getSelectedObject();
		if (jasenKohdalla == null) Dialogs.showMessageDialog("Muokattavaa jäsentä ei valittu");
	}

	/**
	 * Hakee jäsenet pääikkunan listaan.
	 * @param jnro Jäsenen indeksi, joka halutaan ottaa valituksi heti.
	 * @param haku Hakuehto, jota käytetään jos käyttäjä haluaa hakea tiettyä jäsentä tietorakenteesta.
	 *  		   Jos hakua ei haluta käyttää, viedään tyhjä merkkijono.
	 * @throws SailoException 
	 */
	protected void haeListaan(int jnro, String haku) throws SailoException {
		chooserJasenet.clear();
		int index = 0;
		
		Collection<Jasen> jasenet;
		jasenet = kuntosali.etsi(haku);
		int i = 0;
		for (Jasen jasen:jasenet) {
		    if (jasen.getTunnus() == jnro) index = i;
		    chooserJasenet.add(jasen.tiedotListaan(), jasen);
		    i++;
		}
		chooserJasenet.setSelectedIndex(index);
	}

	/** Asetetaan mainissa luotu kuntosali Controllerin kuntosaliksi.
	 * @param kuntosali 
	 * @param kuntoCtrl 
	 * @param nimi 
	 * @throws SailoException 
	 */
	public void setKuntosali (Kuntosali kuntosali, KuntosaliGUIController kuntoCtrl, String nimi) throws SailoException {
		this.kuntosali = kuntosali;
		try {
			kuntosali.lataaTiedostosta(nimi);
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Vanhoja tietoja ei voitu lukea. \n Error-message: " + e.getMessage());
		}
		haeListaan(-1, "");
		this.kuntoCtrl = kuntoCtrl;
	}

	/** Lisätään uusi jäsen kuntosaliin.
	 * @param uusi Lisättävä jäsen
	 * @throws SailoException Poikkeuksesta johtuva virheilmoitus
	 */
	public void lisaa(Jasen uusi) throws SailoException {
		kuntosali.lisaa(uusi);
	}
	
	/** Tallentaa kuntosalin tiedot ulkoiseen tekstitiedostoon, mistä ne voidaan lukea. 
	 * 
	 */
	public void tallenna() {
		kuntosali.tallenna();
	}
}

