package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;

public class SuccessoController {

    @FXML private Text txtMessaggio;
    @FXML private Text txtDettagliVolo;
    @FXML private Text txtEmailInfo;

    public void setDati(String nome, String cognome, String email, String telefono, Volo volo) {
        txtMessaggio.setText(String.format("🎫 Caro/a %s %s,\nprenotazione BRADAR confermata con successo!", nome, cognome));

        txtDettagliVolo.setText(String.format(
                "✈️ VOLO BRADAR\n" +
                        "📍 %s → %s\n" +
                        "📅 Data: %s alle %s\n" +
                        "🎫 Classe: %s\n" +
                        "👥 %s passeggeri\n" +
                        "💰 Prezzo: %s €",
                volo.getPartenza(),
                volo.getDestinazione(),
                volo.getData(),
                volo.getOrario(),
                volo.getClasse(),
                volo.getPasseggeri(),
                volo.getPrezzo()
        ));

        txtEmailInfo.setText(String.format(
                "📧 È stata inviata una email all'indirizzo %s\n" +
                        "con le istruzioni per il pagamento e il biglietto elettronico.\n\n" +
                        "📞 Riceverai un SMS al numero %s con il codice prenotazione BRADAR.",
                email, telefono
        ));
    }

    @FXML
    public void tornaHome(ActionEvent event) {
        try {
            File fxmlFile = new File("src/main/java/application/PrenotazioneVoli.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Parent root = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 800));
            stage.setTitle("BRADAR - Sistema Prenotazioni Voli");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mostraErrore("Errore nel caricamento della home: " + e.getMessage());
        }
    }

    @FXML
    public void visualizzaPrenotazioni(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("BRADAR - Le tue prenotazioni");
        alert.setHeaderText("Informazioni prenotazione");
        alert.setContentText("La tua prenotazione è stata salvata nel sistema.\n" +
                "Controlla la tua email per i dettagli completi del volo BRADAR.\n\n" +
                "Per visualizzare tutte le tue prenotazioni, torna alla home e clicca su 'VISUALIZZA PRENOTAZIONI'.");
        alert.showAndWait();
    }

    private void mostraErrore(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("BRADAR - Errore");
        alert.setHeaderText("Operazione non riuscita");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}