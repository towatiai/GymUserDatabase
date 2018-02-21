/**
 * 
 */
package kuntosali;

/** KUNTOSALIN JÄSENREKISTERI
 * Poikkeusluokka tietorakenteen poikkeustilanteiden käsittelemiseen.
 * @author Toni Tiainen ja Ville Nykänen
 * @version 1.5.2017
 */
public class SailoException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/** Poikkeuksen muodostaja, jolle tuodaan poikkeuksessa käytettävä viesti
	 * @param viesti 
	 */
	public SailoException(String viesti) {
		super(viesti);
	}

}
