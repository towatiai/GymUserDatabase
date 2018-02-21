package fxkerho;
import javafx.application.Application;
import javafx.stage.Stage;
import kuntosali.Kuntosali;
import fxkerho.KuntosaliGUIController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/** KUNTOSALIN JÄSENREKISTERI
 * Pääohjelma. Käynnistää ohjelman.
 * @author Toni Tiainen ja Ville Nykänen
 * @version 1.5.2017
 */
public class KuntosaliMain extends Application {
	/**
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KerhoGUIView.fxml"));
			final Pane root = (Pane)ldr.load();
			final KuntosaliGUIController kuntosaliCtrl = (KuntosaliGUIController)ldr.getController();
			final Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("kerho.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Kuntosali");
			
			Kuntosali kuntosali = new Kuntosali();
			kuntosaliCtrl.setKuntosali(kuntosali, kuntosaliCtrl, "kuntosali");
			
			primaryStage.show();
			
		    } catch(Exception e) {
		        e.printStackTrace();
		        }
	}
	
	/**
	 * @param args kaynnistaa sovelluksen
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
