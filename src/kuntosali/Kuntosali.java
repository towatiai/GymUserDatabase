/**
 * 
 */
package kuntosali;

import java.util.Collection;
import java.util.List;

/** KUNTOSALIN JÄSENREKISTERI
 * Tietorakenteen pääluokka. Sisältää jäsenet sekä hintaryhmät, sekä toimii niiden yhdistäjänä.
 * @author Toni Tiainen ja Ville Nykänen
 * @version 1.5.2017
 */
public class Kuntosali {
	
	private Jasenet jasenet;
	private Hintaryhmat hintaryhmat;

	/**
	 * @param jasen Jasen joka halutaan lisata
	 * @throws SailoException poikkeus joka heitetaan jos lisaysta ei voida tehda
	 */
	public void lisaa(Jasen jasen) throws SailoException {
		jasenet.lisaa(jasen);
	}
	
	
	/** 
	 * @param args ei kaytossa
	 */
	public static void main(String[] args) {
		//Testit siirretty JUnittiin
	}


	/** Lisaa hinnan hintaryhmiin.
	 * @param uusi Lisattava hinta.
	 * @exception SailoException poikkeus 
	 */
	public void lisaaHinta(Hintaryhma uusi) throws SailoException{
		hintaryhmat.lisaa(uusi);
	}
	

	/** Lataa kuntosalin jäsenet ja hintaryhmät ulkoisesta tiedostosta. 
	 * @param nimi tietokannan nimi
	 * @throws SailoException
	 */
	public void lataaTiedostosta(String nimi) throws SailoException {
		jasenet = new Jasenet(nimi);
		hintaryhmat = new Hintaryhmat(nimi);
	}

	
	/**
	 * @return palauttaa listan hintaryhmistä
	 * @throws SailoException 
	 */
	public List<Hintaryhma> getKestot() throws SailoException {
		return hintaryhmat.getKestot();
	}

	/**
	 * @param haku
	 * @return palauttaa kokoelma jäsenistä
	 * @throws SailoException 
	 */
	public Collection<Jasen> etsi(String haku) throws SailoException {
		return jasenet.etsi(haku);
	}

	/** Manuaalinen tallennus ei käytössä tässä tietokantaversiossa.
	 * 
	 */
	public void tallenna() {
		return;
	}


	/** Päivittää muokattavan jäsenen tiedot
	 * @param muokattavaJasen
	 * @throws SailoException 
	 */
	public void paivitaTiedot(Jasen muokattavaJasen) throws SailoException {
		jasenet.paivitaTiedot(muokattavaJasen);
		
	}


	/** Poistaa valitun jäsenen
	 * @param jasenKohdalla
	 * @throws SailoException 
	 */
	public void poista(Jasen jasenKohdalla) throws SailoException {
		jasenet.poista(jasenKohdalla);
		
	}

}
