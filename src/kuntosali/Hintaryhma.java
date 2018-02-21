package kuntosali;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import fi.jyu.mit.fxgui.Dialogs;

/** KUNTOSALIN JÄSENREKISTERI
 * Tietorakenneluokka yksittäisen hintaryhmän hallinnalle. 
 * @author Toni Tiainen ja Ville Nykänen
 * @version 1.5.2017
 */
public class Hintaryhma {
	
    private int kesto;                                      //Kuukausina
	private List<Integer> hinta = new ArrayList<Integer>(); // Hinnat: Aikuinen, Nuori, Opiskelija
	private int ID;
	private static int seuraavaID = 0;
	

	/** Testipääohjelma. Testit siirretty kuntosali-luokkaan.
	 * @param args ei kaytossa
	 */
	public static void main(String[] args) {
		//Testit siirretty kuntosali-luokkaan.
	}


	/** Lisää hinnat.
	 * @param hintaOpiskelija          Opiskelijahinta 
	 * @param hintaNuori               Nuoren hinta
	 * @param hintaAikuinen            Aikuisen hinta
	 * @param hintaKesto               Hinta, joka tulee sopimuksen kestosta
	 * @throws NumberFormatException e ilmoitus, joka naytetaan virhetilanteessa
	 */
	public void lisaaHinnat(String hintaAikuinen, String hintaNuori, String hintaOpiskelija, String hintaKesto) {
		try {
			this.hinta.add(Integer.parseInt(hintaAikuinen));
			this.hinta.add(Integer.parseInt(hintaNuori));
			this.hinta.add(Integer.parseInt(hintaOpiskelija));
			this.kesto = Integer.parseInt(hintaKesto);
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Ongelmia tietojen syötössä" + e.getMessage());
			return;
		}
	}
	


	/** 
	 * @return Palauttaa sopimuksen keston.
	 */
	public int getKesto() {
		return this.kesto;
	}


	/**
	 * @return Palauttaa alkion ID:n
	 */
	public int alkioID() {
		return this.ID;
	}


	/** Rekisteröi hinnan.
	 * 
	 */
	public void rekisteroi() {
		this.ID = seuraavaID;
		seuraavaID++;
	}


	/**
	 * Tulostaa sopimuksen keston
	 */
	public void tulosta() {
		System.out.print("ID: " + this.ID + "\nKesto: " + this.kesto + "\n" + "Hinnat: " + Arrays.toString(hinta.toArray()) + "\n");	
	}
	
	
	public String toString() {
		String hintatxt = ID + "|" + kesto;
		for (int i = 0; i < hinta.size(); i++) {
			hintatxt += "|" + Integer.toString(hinta.get(i));
		}
		return hintatxt;
		
	}


	/**
	 * @param ryhma        Hintaryhma
	 * @return Palauttaa hinnan string -muodossa
	 */
	public String getHinta(int ryhma) {
		return hinta.get(ryhma).toString();
	}


	/**
	 * @return palauttaa tietokannan luontilausekkeen
	 */
	public String annaLuontilauseke() {
		return "CREATE TABLE Hintaryhmat (" +
                "ryhmaID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "kesto INTEGER NOT NULL, " +
                "aikuinen INTEGER NOT NULL, " + 
                "nuori INTEGER NOT NULL, " +
                "opiskelija INTEGER NOT NULL" +
                ")";
	}


	/**
	 * @param con yhteys
	 * @return palauttaa lisäyslausekkeen
	 * @throws SQLException poikkeus joka heitetään jos jokin menee vikaan
	 */
	public PreparedStatement annaLisayslauseke(Connection con) throws SQLException {
		PreparedStatement sql = con.prepareStatement(
        		"INSERT INTO Hintaryhmat ("
                + "ryhmaID, "
                + "kesto, "
                + "aikuinen, "
                + "nuori, "
                + "opiskelija) "
                + "VALUES (?, ?, ?, ?, ?)");
        sql.setInt(2, kesto);
        sql.setInt(3, hinta.get(0));
        sql.setInt(4, hinta.get(1));
        sql.setInt(5, hinta.get(2));
        
        return sql;
	}


	/** Lukee tiedoston ja tallentaa siinä olevat tiedot olion tietoihin
	 * @param rivi
	 * @throws SQLException jos jotain menee vikaan
	 */
	public void parse(ResultSet rivi) throws SQLException {
        ID = rivi.getInt("ryhmaID");
        kesto = rivi.getInt("kesto");
        hinta.add(rivi.getInt("aikuinen"));
        hinta.add(rivi.getInt("nuori"));
        hinta.add(rivi.getInt("opiskelija"));
    }
}