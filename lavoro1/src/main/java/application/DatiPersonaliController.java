package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;

public class DatiPersonaliController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCognome;
    @FXML private DatePicker dateNascita;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    @FXML private TextField txtConfermaEmail;
    @FXML private Label lblMessaggio;

    private Volo volo;
    private ObservableList<String> prenotazioniLista;

    public void setVolo(Volo volo) {
        this.volo = volo;
    }

    public void setPrenotazioniLista(ObservableList<String> lista) {
        this.prenotazioniLista = lista;
    }

    @FXML
    public void confermaPrenotazione(ActionEvent event) {
        if (!valida()) return;

        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();

        String prenotazione = String.format("BRADAR - %s %s: %s → %s | %s | %s € | %s",
                nome, cognome, volo.getPartenza(), volo.getDestinazione(),
                volo.getData(), volo.getPrezzo(), email);
        prenotazioniLista.add(prenotazione);

        try {
            File fxmlFile = new File("src/main/java/application/SuccessoPrenotazione.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Parent root = loader.load();

            SuccessoController successo = loader.getController();
            successo.setDati(nome, cognome, email, telefono, volo);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 700, 500));
            stage.setTitle("BRADAR - Prenotazione Confermata");
            stage.show();

            Stage current = (Stage) txtNome.getScene().getWindow();
            current.close();

        } catch (Exception e) {
            e.printStackTrace();
            lblMessaggio.setText("Errore: " + e.getMessage());
        }
    }

    private boolean valida() {
        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        LocalDate data = dateNascita.getValue();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String conferma = txtConfermaEmail.getText().trim();

        if (nome.isEmpty()) { lblMessaggio.setText("Inserisci il nome"); return false; }
        if (cognome.isEmpty()) { lblMessaggio.setText("Inserisci il cognome"); return false; }
        if (data == null) { lblMessaggio.setText("Inserisci la data di nascita"); return false; }

        int eta = Period.between(data, LocalDate.now()).getYears();
        if (eta < 18) { lblMessaggio.setText("Devi avere almeno 18 anni"); return false; }

        if (telefono.isEmpty()) { lblMessaggio.setText("Inserisci il telefono"); return false; }
        if (!email.contains("@") || !email.contains(".")) { lblMessaggio.setText("Email non valida"); return false; }
        if (!email.equals(conferma)) { lblMessaggio.setText("Le email non corrispondono"); return false; }

        return true;
    }

    @FXML
    public void annulla(ActionEvent event) {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
}