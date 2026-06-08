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
        txtMessaggio.setText(String.format("Caro/a %s %s,\nprenotazione confermata con successo!", nome, cognome));
        txtDettagliVolo.setText(String.format(
                "Dettagli volo:\n" +
                        "✈️ %s\n" +
                        "📍 %s → %s\n" +
                        "📅 %s alle %s\n" +
                        "🎫 Classe: %s\n" +
                        "👥 %s passeggeri\n" +
                        "💰 %s €",
                volo.getCompagnia(),
                volo.getPartenza(),
                volo.getDestinazione(),
                volo.getData(),
                volo.getOrario(),
                volo.getClasse(),
                volo.getPasseggeri(),
                volo.getPrezzo()
        ));
        txtEmailInfo.setText(String.format(
                "📧 È stata inviata una mail all'indirizzo %s\n" +
                        "con le istruzioni per il pagamento e il biglietto elettronico.\n\n" +
                        "📞 Riceverai un SMS al numero %s con il codice prenotazione.",
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
            stage.setTitle("Air France - Sistema Prenotazioni Voli");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void visualizzaPrenotazioni(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Prenotazioni");
        alert.setHeaderText("Air France - Le tue prenotazioni");
        alert.setContentText("Controlla la tua email per i dettagli completi del volo e le istruzioni per il pagamento.");
        alert.showAndWait();
    }
}