package kuntosali;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * @author Toni
 *
 */
public class KuntosaliTest {

	/**
	 * @param args
	 * @throws SailoException 
	 */
	public static void main(String[] args) throws SailoException {
		String tiedostonimi = "kuntosaliTEST";
		
		new File(tiedostonimi + ".db").delete();
		Kuntosali kuntosali = new Kuntosali();
		kuntosali.lataaTiedostosta(tiedostonimi);
		
		Jasen make = new Jasen();
		Jasen mikko = new Jasen();
		make.luoTestiMake();
		mikko.luoTestiMake();
		
		kuntosali.lisaa(make);
		kuntosali.lisaa(mikko);
		
		Hintaryhma hinta = new Hintaryhma();
		hinta.rekisteroi();
		hinta.lisaaHinnat("50", "40", "30", "12");
		kuntosali.lisaaHinta(hinta);
		
		System.out.println("======== Testit: ========");
		
		Collection<Jasen> jasenet = kuntosali.etsi("");
		List<Hintaryhma> hinnat = kuntosali.getKestot();
		
		for (Jasen jasen : jasenet) {
			jasen.tulosta(System.out);
		}
		
		for (Hintaryhma kesto : hinnat) {
			kesto.tulosta();
		}
		
		new File(tiedostonimi + ".db").delete();
	}

}
