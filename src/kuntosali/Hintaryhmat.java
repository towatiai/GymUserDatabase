package kuntosali;

import java.sql.*;
import java.util.*;


import static kuntosali.Kanta.alustaKanta;

/** KUNTOSALIN JÄSENREKISTERI
 * Tietorakenneluokka kaikkien hintaryhmien hallinnalle.
 * @author Toni Tiainen ja Ville Nykänen
 * @version 1.5.2017
 */
public class Hintaryhmat  {

	private Kanta kanta;
	private Hintaryhma apuhintaryhma = new Hintaryhma();
	
	
	/**
	 * @param nimi tietokannan nimi
	 * @throws SailoException Jos tietokannan luomisessa on ongelmia.
	 * 
	 */
	public Hintaryhmat (String nimi) throws SailoException {
		kanta = alustaKanta(nimi);
        try ( Connection con = kanta.annaKantayhteys() ) {
            DatabaseMetaData meta = con.getMetaData();
            try ( ResultSet taulu = meta.getTables(null, null, "Hintaryhmat", null) ) {
                if ( !taulu.next() ) {
                    // Luodaan harrastukset taulu
                    try ( PreparedStatement sql = con.prepareStatement(apuhintaryhma.annaLuontilauseke()) ) {
                        sql.execute();
                    }
                }
            }
                
        } catch ( SQLException e ) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
	}
	
	
	/** Lisaa hinnan listaan.
	 * @param hinta Hinta
	 * @throws SailoException 
	 */
	public void lisaa (Hintaryhma hinta) throws SailoException {
		try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = hinta.annaLisayslauseke(con) ) {
            sql.executeUpdate();
            
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
	}
	

	/** Testipääohjelma. Testit siirretty kuntosali-luokkaan.
	 * @param args ei kaytossa
	 */
	public static void main(String[] args) {
		// Testit siirretty kuntosali-luokkaan.

	}


	/**
	 * @return palauttaa listan hintaryhmistä
	 * @throws SailoException 
	 */
	public List<Hintaryhma> getKestot() throws SailoException {
		List<Hintaryhma> loydetyt = new ArrayList<Hintaryhma>();

		try ( Connection con = kanta.annaKantayhteys();
				PreparedStatement sql = con.prepareStatement("SELECT * FROM Hintaryhmat")
				) {
			try ( ResultSet tulokset = sql.executeQuery() ) { 
					while ( tulokset.next() ) {
						Hintaryhma hinta = new Hintaryhma();
						hinta.parse(tulokset);
						loydetyt.add(hinta);
					}
					tulokset.close();
			}
		} catch (SQLException e) {
			throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
		}
		return loydetyt;
	}

}
