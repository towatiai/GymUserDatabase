/**
 * 
 */
package kuntosali;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static kuntosali.Kanta.alustaKanta;

/** KUNTOSALIN JÄSENREKISTERI
 * Tietorakenneluokka kaikkien jäsenten hallinnoimiseen.
 * @author Toni Tiainen ja Ville Nykänen
 * @version 1.5.2017
 */
public class Jasenet {
	
	private String tiedostonnimi = "kuntosali"; //Ei käytössä vielä. Käytetään tiedoston tulostamiseen
	
	private Kanta kanta;
	private static Jasen apujasen = new Jasen();
	
	/**
     * Tarkistetaan että kannassa jäsenten tarvitsema taulu
     * @param nimi tietokannan nimi
     * @throws SailoException jos jokin menee pieleen
     */
    public Jasenet(String nimi) throws SailoException {
        kanta = alustaKanta(nimi);
        try ( Connection con = kanta.annaKantayhteys() ) {
            // Hankitaan tietokannan metadata ja tarkistetaan siitä onko
            // Jasenet nimistä taulua olemassa.
            // Jos ei ole, luodaan se. Ei puututa tässä siihen, onko
            // mahdollisesti olemassa olevalla taululla oikea rakenne,
            // käyttäjä saa kuulla siitä virheilmoituksen kautta
            DatabaseMetaData meta = con.getMetaData();
            
            try ( ResultSet taulu = meta.getTables(null, null, "Jasenet", null) ) {
                if ( !taulu.next() ) {
                    // Luodaan Jasenet taulu
                    try ( PreparedStatement sql = con.prepareStatement(apujasen.annaLuontilauseke()) ) {
                        sql.execute();
                    }
                }
            }
            
        } catch ( SQLException e ) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }


	/**Tarkistaa mahtuuko jäsen jäsenet-taulukkoon. Jos ei, kasvatetaan taulukon kokoa. Lisää jäsenen taulukkoon.
	 * @param jasen jasen
	 * @throws SailoException poikkeus 
	 */
	public void lisaa(Jasen jasen) throws SailoException {
		try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = jasen.annaLisayslauseke(con) ) {
			sql.executeUpdate();
            try ( ResultSet rs = sql.getGeneratedKeys() ) {
            	jasen.tarkistaId(rs);
            }
            
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
	}
	
	
	/**
	 * @return Palauttaa tiedostonimen
	 */
	public String getTiedostonNimi() {
		return tiedostonnimi + ".txt";
	}
	


	/** SUodattaa hakua vastaavat jäsenet ja palauttaa ne kokoelmassa (Collection)
	 * @param haku
	 * @return palauttaa kokoelman hakua vastaavista jäsenistä
	 * @throws SailoException 
	 */
	public Collection<Jasen> etsi(String haku) throws SailoException {
		String ehto = haku;
		String lauseke = "SELECT * FROM Jasenet WHERE etunimi LIKE ? OR sukunimi LIKE ?";
		if (haku.matches("^-?\\d+$")) lauseke = "SELECT * FROM Jasenet WHERE hetu LIKE ?";
		try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement(lauseke) ) {
            ArrayList<Jasen> loytyneet = new ArrayList<Jasen>();
            
            sql.setString(1, "%" + ehto + "%");
            if (!haku.matches("^-?\\d+$")) sql.setString(2, "%" + ehto + "%");
            try ( ResultSet tulokset = sql.executeQuery() ) {
                while ( tulokset.next() ) {
                    Jasen j = new Jasen();
                    j.parse(tulokset);
                    loytyneet.add(j);
                }
            }
            return loytyneet;
        } catch ( SQLException e ) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
	}


	/** Päivittää muokatun jäsenen tiedot tietokantaan
	 * @param muokattavaJasen
	 * @throws SailoException 
	 */
	public void paivitaTiedot(Jasen muokattavaJasen) throws SailoException {
		try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = muokattavaJasen.annaPaivityslauseke(con) ) {
            sql.executeUpdate();
            sql.close();
            
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
	}


	/** Poistaa jäsenen tietokannasta
	 * @param poistettava 
	 * @throws SailoException 
	 */
	public void poista(Jasen poistettava) throws SailoException {
		try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = poistettava.annaPoistolauseke(con) ) {
            sql.executeUpdate();
            sql.close();
            
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
	}

}
