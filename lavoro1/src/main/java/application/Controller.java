package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Controller {

    @FXML private ComboBox<String> cmbPartenza;
    @FXML private ComboBox<String> cmbDestinazione;
    @FXML private DatePicker dateAndata;
    @FXML private DatePicker dateRitorno;
    @FXML private ComboBox<String> cmbPasseggeri;
    @FXML private ComboBox<String> cmbClasse;
    @FXML private Button btnCerca;
    @FXML private Button btnPrenota;
    @FXML private Button btnVisualizza;
    @FXML private Button btnReset;
    @FXML private Button btnScambia;
    @FXML private TableView<Volo> tabellaVoli;
    @FXML private ListView<String> listaPrenotazioni;
    @FXML private Label lblMessaggio;

    private ObservableList<Volo> voliDisponibili;
    private ObservableList<String> prenotazioni;
    private Random random;

    @FXML
    public void initialize() {
        voliDisponibili = FXCollections.observableArrayList();
        prenotazioni = FXCollections.observableArrayList();
        random = new Random();

        tabellaVoli.setItems(voliDisponibili);
        listaPrenotazioni.setItems(prenotazioni);

        // Configura colonne tabella
        TableColumn<Volo, String> colCompagnia = (TableColumn<Volo, String>) tabellaVoli.getColumns().get(0);
        TableColumn<Volo, String> colPartenza = (TableColumn<Volo, String>) tabellaVoli.getColumns().get(1);
        TableColumn<Volo, String> colDestinazione = (TableColumn<Volo, String>) tabellaVoli.getColumns().get(2);
        TableColumn<Volo, String> colData = (TableColumn<Volo, String>) tabellaVoli.getColumns().get(3);
        TableColumn<Volo, String> colOrario = (TableColumn<Volo, String>) tabellaVoli.getColumns().get(4);
        TableColumn<Volo, String> colPrezzo = (TableColumn<Volo, String>) tabellaVoli.getColumns().get(5);
        TableColumn<Volo, String> colClasse = (TableColumn<Volo, String>) tabellaVoli.getColumns().get(6);
        TableColumn<Volo, String> colPasseggeri = (TableColumn<Volo, String>) tabellaVoli.getColumns().get(7);

        colCompagnia.setCellValueFactory(new PropertyValueFactory<>("compagnia"));
        colPartenza.setCellValueFactory(new PropertyValueFactory<>("partenza"));
        colDestinazione.setCellValueFactory(new PropertyValueFactory<>("destinazione"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colOrario.setCellValueFactory(new PropertyValueFactory<>("orario"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        colClasse.setCellValueFactory(new PropertyValueFactory<>("classe"));
        colPasseggeri.setCellValueFactory(new PropertyValueFactory<>("passeggeri"));

        dateAndata.setValue(LocalDate.now().plusDays(7));
        dateRitorno.setValue(LocalDate.now().plusDays(14));
        cmbPasseggeri.setValue("1");
        cmbClasse.setValue("Economy");

        // Popola combo box
        cmbPartenza.getItems().addAll("Parigi (CDG)", "Nizza (NCE)", "Marsiglia (MRS)", "Lione (LYS)", "Tolosa (TLS)", "Bordeaux (BOD)");
        cmbDestinazione.getItems().addAll("Roma (FCO)", "Milano (MXP)", "Venezia (VCE)", "Firenze (FLR)", "Napoli (NAP)", "Palermo (PMO)");
        cmbPasseggeri.getItems().addAll("1", "2", "3", "4", "5", "6");
        cmbClasse.getItems().addAll("Economy", "Premium Economy", "Business", "First Class");

        lblMessaggio.setText("Benvenuto su Air France!");
    }

    @FXML
    public void cercaVoli(ActionEvent event) {
        try {
            String partenza = cmbPartenza.getValue();
            String destinazione = cmbDestinazione.getValue();
            LocalDate dataAndataVal = dateAndata.getValue();
            String numPasseggeri = cmbPasseggeri.getValue();
            String classe = cmbClasse.getValue();

            if (partenza == null || destinazione == null) {
                mostraErrore("Seleziona partenza e destinazione!");
                return;
            }

            if (partenza.equals(destinazione)) {
                mostraErrore("Partenza e destinazione non possono essere uguali!");
                return;
            }

            if (dataAndataVal == null || dataAndataVal.isBefore(LocalDate.now())) {
                mostraErrore("Data non valida!");
                return;
            }

            voliDisponibili.clear();

            String[] orari = {"06:00", "08:30", "11:15", "14:45", "17:20", "20:10", "22:50"};
            String[] compagnie = {"Air France", "AF", "Air France Express", "SkyTeam"};

            for (int i = 0; i < 5; i++) {
                String orario = orari[random.nextInt(orari.length)];
                int prezzo = 150 + random.nextInt(300);

                Volo volo = new Volo(
                        compagnie[random.nextInt(compagnie.length)],
                        partenza,
                        destinazione,
                        dataAndataVal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        orario,
                        String.valueOf(prezzo),
                        classe,
                        numPasseggeri
                );
                voliDisponibili.add(volo);
            }

            lblMessaggio.setText("Trovati " + voliDisponibili.size() + " voli!");

        } catch (Exception e) {
            mostraErrore(e.getMessage());
        }
    }

    @FXML
    public void prenotaVolo(ActionEvent event) {
        Volo voloSelezionato = tabellaVoli.getSelectionModel().getSelectedItem();

        if (voloSelezionato == null) {
            mostraErrore("Seleziona un volo dalla tabella!");
            return;
        }

        try {
            File fxmlFile = new File("src/main/java/application/DatiPersonali.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Parent root = loader.load();

            DatiPersonaliController datiController = loader.getController();
            datiController.setVolo(voloSelezionato);
            datiController.setPrenotazioniLista(prenotazioni);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 600));
            stage.setTitle("Air France - Dati Passeggero");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mostraErrore("Errore: " + e.getMessage());
        }
    }

    @FXML
    public void visualizzaPrenotazioni(ActionEvent event) {
        if (prenotazioni.isEmpty()) {
            mostraErrore("Nessuna prenotazione effettuata!");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Le tue prenotazioni");
            alert.setHeaderText("Air France - Prenotazioni effettuate");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < prenotazioni.size(); i++) {
                sb.append(i+1).append(". ").append(prenotazioni.get(i)).append("\n\n");
            }
            alert.setContentText(sb.toString());
            alert.showAndWait();
        }
    }

    @FXML
    public void resetForm(ActionEvent event) {
        cmbPartenza.setValue(null);
        cmbDestinazione.setValue(null);
        dateAndata.setValue(LocalDate.now().plusDays(7));
        dateRitorno.setValue(LocalDate.now().plusDays(14));
        cmbPasseggeri.setValue("1");
        cmbClasse.setValue("Economy");
        voliDisponibili.clear();
        lblMessaggio.setText("Form resettato!");
    }

    @FXML
    public void scambiaCitta(ActionEvent event) {
        String p = cmbPartenza.getValue();
        String d = cmbDestinazione.getValue();
        if (p != null && d != null) {
            cmbPartenza.setValue(d);
            cmbDestinazione.setValue(p);
            lblMessaggio.setText("Città scambiate!");
        }
    }

    private void mostraErrore(String msg) {
        lblMessaggio.setText("⚠️ " + msg);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Operazione non riuscita");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}