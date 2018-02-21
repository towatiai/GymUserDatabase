/**
 * 
 */
package kuntosali;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/** KUNTOSALIN JÄSENREKISTERI
 * Tietorakenneluokka yksittäisen jäsenen lisäykseen, poistamiseen ja tietojen muokkaamiseen.
 * @author Toni Tiainen ja Ville Nykänen
 * @version 1.5.2017
 */
public class Jasen {
	
	private int tunnusNro;
	
	private String etunimi			= "";
	private String sukunimi			= "";
	private String hetu				= "";
	private String katuosoite		= "";
	private String postinumero		= "";
	private String puhelinnumero	= "";
	private String liittymispaiva	= "";
	private String hinta 			= "";
	
	private static int seuraavaNro	= 1;


	/** Kaytettiin testijasenen luomiseen.
	 * 
	 */
	public void luoTestiMake() {
		Random rand = new Random();
		etunimi = "Make" + Integer.toString(rand.nextInt(100));
		sukunimi = "Sali";
		hetu = "050989-268S";
		katuosoite = "Ankkakuja 6";
		postinumero = "12345";
		puhelinnumero = "12-1234";
		liittymispaiva = "12.5.2016";
		}
	
	
	/**
	 * @param pienella 
	 * @return palauttaa jasenen etunimen
	 */
	public String getNimi(Boolean pienella) {
		if (pienella) return this.etunimi.toLowerCase();
		return this.etunimi;
	}
	
	/**
	 * @param pienella 
	 * @return Palauttaa jasenen sukunimen
	 */
	public String getSukunimi(Boolean pienella) {
		if (pienella) return this.sukunimi.toLowerCase();
		return this.sukunimi;
	}
	
	/**
	 * @return palauttaa jasenen hetun
	 */
	public String getHetu() {
		return this.hetu;
	}
	
	/**
	 * @return palauttaa jasenen katuosoitteen
	 */
	public String getKatuosoite() {
		return this.katuosoite;
	}
	
	/**
	 * @return palauttaa jasenen postinumeron
	 */
	public String getPostinumero() {
		return this.postinumero;
	}
	
	/**
	 * @return palauttaa jasenen puhelinnumeron
	 */
	public String getPuhelinnumero() {
		return this.puhelinnumero;
	}
	
	/**
	 * @return palauttaa jasenen liittymispaivan
	 */
	public String getLiittymispaiva() {
		return this.liittymispaiva;
	}
	
	/**
	 * @return Palauttaa jasenen hinnan
	 */
	public String getHinta() {
		return this.hinta;
	}
	
	
	/**
	 * @return palauttaa jasenen tunnusnumeron
	 */
	public int getTunnus() {
		return this.tunnusNro;
	}
	
	

	/** Tulostaa janenen tiedot haluttuun kanavaan
	 * @param out Tulostuskanava mihin tulostetaan
	 */
	public void tulosta(PrintStream out) {
		String rivi = "";
		rivi += this.tunnusNro + "|";
		rivi += this.etunimi + "|";
		rivi += this.sukunimi + "|";
		rivi += this.hetu + "|";
		rivi += this.katuosoite + "|";
		rivi += this.postinumero + "|";
		rivi += this.puhelinnumero + "|";
		rivi += this.liittymispaiva + "|";
		rivi += this.hinta;
		
		out.println(rivi);
	}
	
	
	/** Testiohjelma Jasen-luokalle. Ei kaytossa enaa.
	 * @param args ei kaytossa
	 */
	public static void main(String[] args) {
		// Testit siirretty kuntosali-luokkaan.
	}

	/** Lisataan jasenen tiedot
	 * @param textEtunimi          Etunimi
	 * @param textSukunimi         Sukunimi    
	 * @param textHetu             Henkilotunnus
	 * @param textKatuosoite       Osoite
	 * @param textPostinumero      Postinro
	 * @param textPuhelinnumero    Puhleinro
	 * @param textLiittymispaiva   Liittymispaiva
	 * @param kesto                Sopimuksen kesto
	 */
	public void LisaaTiedot(String textEtunimi,
			String textSukunimi, 
			String textHetu,
			String textKatuosoite,
			String textPostinumero,
			String textPuhelinnumero,
			String textLiittymispaiva,
			String kesto
			) {
		this.etunimi = textEtunimi;
		this.sukunimi = textSukunimi;
		this.hetu = textHetu;
		this.katuosoite = textKatuosoite;
		this.postinumero = textPostinumero;
		this.puhelinnumero = textPuhelinnumero;
		this.liittymispaiva = textLiittymispaiva;
		this.hinta = kesto;
	}

	/**
	 * @return palauttaa formatoidun listanimen
	 */
	public String tiedotListaan() {
		String listaetunimi = String.format("%-28s", this.etunimi);
		String listasukunimi = String.format("%-30s", this.sukunimi);
		String listaHetu = String.format("%-30s", this.hetu);
		return listaetunimi + listasukunimi + listaHetu + this.hinta + " e/kk" ;
	}
	
	@Override
	public String toString() {
		String rivi = "";
		rivi += this.tunnusNro + "|";
		rivi += this.etunimi + "|";
		rivi += this.sukunimi + "|";
		rivi += this.hetu + "|";
		rivi += this.katuosoite + "|";
		rivi += this.postinumero + "|";
		rivi += this.puhelinnumero + "|";
		rivi += this.liittymispaiva + "|";
		rivi += this.hinta;
		
		return rivi;
	}


	/** 
     * Antaa tietokannan luontilausekkeen jäsentaululle
     * @return jäsentaulun luontilauseke
     */
    public String annaLuontilauseke() {
        return "CREATE TABLE Jasenet (" +
                "tunnusNro INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "etunimi VARCHAR(100) NOT NULL, " +
                "sukunimi VARCHAR(100) NOT NULL, " + 
                "hetu VARCHAR(11), " +
                "katuosoite VARCHAR(100), " +
                "postinumero VARCHAR(5), " +
                "puhelinnumero VARCHAR(100), " +
                "liittymispaiva VARCHAR(11), " +
                "hinta DECIMAL(10,2)" +
                ")";
    }

	/** Tarkistaa, täsmääkö jäsenen ID tietokannan automaattisesti antamaan ID:hen
	 * @param rs
	 */
	public void tarkistaId(ResultSet rs) {
		try {
			if ( !rs.next() ) return;
			int id = rs.getInt(1);
			if ( id == tunnusNro ) return;
	        setTunnusNro(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}
	
	/**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
	
	
	/**
     * Antaa jäsenen lisäyslausekkeen
     * @param con tietokantayhteys
     * @return jäsenen lisäyslauseke
     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
     */
    public PreparedStatement annaLisayslauseke(Connection con) throws SQLException {
        PreparedStatement sql = con.prepareStatement(
        		"INSERT INTO Jasenet ("
                + "tunnusNro, "
                + "etunimi, "
                + "sukunimi, "
                + "hetu, "
                + "katuosoite, "
                + "postinumero, "
                + "puhelinnumero, "
                + "liittymispaiva, "
                + "hinta) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        
        // Syötetään kentät näin välttääksemme SQL injektiot.
        // Käyttäjän syötteitä ei ikinä vain kirjoiteta kysely
        // merkkijonoon tarkistamatta niitä SQL injektioiden varalta!
        if ( tunnusNro != 0 ) sql.setInt(1, tunnusNro); else sql.setString(1, null);
        sql.setString(2, etunimi);
        sql.setString(3, sukunimi);
        sql.setString(4, hetu);
        sql.setString(5, katuosoite);
        sql.setString(6, postinumero);
        sql.setString(7, puhelinnumero);
        sql.setString(8, liittymispaiva);
        sql.setString(9, hinta);
        
        return sql;
    }

	/** Lukee tulokset, ja lisää niistä luetut tiedot jäsenelle
	 * @param tulokset tietokannasta luetut tiedot
	 * @throws SQLException
	 */
	public void parse(ResultSet tulokset) throws SQLException {
		tunnusNro = tulokset.getInt("tunnusNro");
		etunimi = tulokset.getString("etunimi");
		sukunimi = tulokset.getString("sukunimi");
		hetu = tulokset.getString("hetu");
		katuosoite = tulokset.getString("katuosoite");
		postinumero = tulokset.getString("postinumero");
		puhelinnumero = tulokset.getString("puhelinnumero");
		liittymispaiva = tulokset.getString("liittymispaiva");
		hinta = tulokset.getString("hinta");
	}

	/**
	 * @return palauttaa nimen
	 */
	public String getNimi() {
		return etunimi + sukunimi;
	}

	/** 
	 * @param con
	 * @return Palauttaa päivityslausekkeen jolla päivitetään tietokantaa
	 * @throws SQLException 
	 */
	public PreparedStatement annaPaivityslauseke(Connection con) throws SQLException {
		PreparedStatement sql = con.prepareStatement(
				"UPDATE Jasenet "
				+ "SET etunimi = ?, "
				+ "sukunimi = ?, "
				+ "hetu = ?, "
				+ "katuosoite = ?,"
				+ "postinumero = ?, "
				+ "puhelinnumero = ?, "
				+ "liittymispaiva = ?, "
				+ "hinta = ? "
				+ "WHERE tunnusNro = ?");
        
        sql.setString(1, etunimi);
        sql.setString(2, sukunimi);
        sql.setString(3, hetu);
        sql.setString(4, katuosoite);
        sql.setString(5, postinumero);
        sql.setString(6, puhelinnumero);
        sql.setString(7, liittymispaiva);
        sql.setString(8, hinta);
        sql.setInt(9, tunnusNro);
        
        return sql;
	}

	/**
	 * @param con
	 * @return Palauuttaa poistolausekkeen
	 * @throws SQLException
	 */
	public PreparedStatement annaPoistolauseke(Connection con) throws SQLException {
		PreparedStatement sql = con.prepareStatement(
				"DELETE FROM Jasenet WHERE tunnusNro = ?"
				);
		sql.setInt(1, tunnusNro);
		return sql;
	}
}
