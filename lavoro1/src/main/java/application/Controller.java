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
import java.util.*;

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
    private Map<String, String> aeroporti;

    // 250+ città con i loro aeroporti
    private final String[][] CITTA_AEROPORTI = {
            // Italia
            {"Roma", "Fiumicino (FCO)"}, {"Milano", "Malpensa (MXP)"}, {"Napoli", "Capodichino (NAP)"},
            {"Torino", "Caselle (TRN)"}, {"Palermo", "Falcone Borsellino (PMO)"}, {"Bologna", "Guglielmo Marconi (BLQ)"},
            {"Firenze", "Peretola (FLR)"}, {"Venezia", "Marco Polo (VCE)"}, {"Catania", "Fontanarossa (CTA)"},
            {"Bari", "Karol Wojtyła (BRI)"}, {"Genova", "Cristoforo Colombo (GOA)"}, {"Verona", "Valerio Catullo (VRN)"},
            {"Pisa", "Galileo Galilei (PSA)"}, {"Trieste", "Friuli Venezia Giulia (TRS)"}, {"Cagliari", "Elmas (CAG)"},
            {"Lamezia Terme", "Sant'Eufemia (SUF)"}, {"Ancona", "Raffaello Sanzio (AOI)"}, {"Rimini", "Federico Fellini (RMI)"},
            {"Perugia", "San Francesco d'Assisi (PEG)"}, {"Brindisi", "Salento (BDS)"},

            // Francia
            {"Parigi", "Charles de Gaulle (CDG)"}, {"Parigi", "Orly (ORY)"}, {"Nizza", "Côte d'Azur (NCE)"},
            {"Marsiglia", "Provence (MRS)"}, {"Lione", "Saint-Exupéry (LYS)"}, {"Tolosa", "Blagnac (TLS)"},
            {"Bordeaux", "Mérignac (BOD)"}, {"Nantes", "Atlantique (NTE)"}, {"Strasburgo", "Entzheim (SXB)"},
            {"Montpellier", "Méditerranée (MPL)"}, {"Lilla", "Lesquin (LIL)"}, {"Rennes", "Saint-Jacques (RNS)"},

            // Spagna
            {"Madrid", "Barajas (MAD)"}, {"Barcellona", "El Prat (BCN)"}, {"Valencia", "Manises (VLC)"},
            {"Siviglia", "San Pablo (SVQ)"}, {"Malaga", "Costa del Sol (AGP)"}, {"Bilbao", "Loiu (BIO)"},
            {"Palma di Maiorca", "Son Sant Joan (PMI)"}, {"Alicante", "Elche (ALC)"}, {"Granada", "Federico García Lorca (GRX)"},
            {"Saragozza", "Zaragoza (ZAZ)"}, {"Las Palmas", "Gran Canaria (LPA)"}, {"Tenerife", "Tenerife Sur (TFS)"},

            // Germania
            {"Berlino", "Brandenburg (BER)"}, {"Monaco", "Franz Josef Strauss (MUC)"}, {"Francoforte", "Rhein-Main (FRA)"},
            {"Amburgo", "Helmut Schmidt (HAM)"}, {"Colonia", "Konrad Adenauer (CGN)"}, {"Stoccarda", "Manfred Rommel (STR)"},
            {"Dusseldorf", "DUS"}, {"Lipsia", "Leipzig/Halle (LEJ)"}, {"Norimberga", "NUE"}, {"Hannover", "Langenhagen (HAJ)"},
            {"Dresda", "Dresden (DRS)"}, {"Brema", "Bremen (BRE)"},

            // Regno Unito
            {"Londra", "Heathrow (LHR)"}, {"Londra", "Gatwick (LGW)"}, {"Manchester", "MAN"}, {"Birmingham", "BHX"},
            {"Glasgow", "GLA"}, {"Edimburgo", "EDI"}, {"Liverpool", "LPL"}, {"Bristol", "BRS"}, {"Leeds", "LBA"},
            {"Newcastle", "NCL"}, {"Belfast", "BFS"},

            // Paesi Bassi
            {"Amsterdam", "Schiphol (AMS)"}, {"Rotterdam", "The Hague (RTM)"}, {"Eindhoven", "EIN"}, {"Groninga", "GRQ"},

            // Belgio
            {"Bruxelles", "Zaventem (BRU)"}, {"Anversa", "ANR"}, {"Liegi", "LGG"}, {"Charleroi", "CRL"},

            // Svizzera
            {"Zurigo", "Kloten (ZRH)"}, {"Ginevra", "Cointrin (GVA)"}, {"Basilea", "EuroAirport (BSL)"}, {"Berna", "BRN"},
            {"Losanna", "LSG"}, {"Lugano", "LUG"},

            // Austria
            {"Vienna", "Schwechat (VIE)"}, {"Salisburgo", "SZG"}, {"Innsbruck", "INN"}, {"Graz", "GRZ"}, {"Linz", "LNZ"},

            // Portogallo
            {"Lisbona", "Humberto Delgado (LIS)"}, {"Porto", "Francisco Sá Carneiro (OPO)"}, {"Faro", "FAO"},
            {"Funchal", "Funchal (FNC)"}, {"Ponta Delgada", "Azzorre (PDL)"},

            // Grecia
            {"Atene", "Eleftherios Venizelos (ATH)"}, {"Salonicco", "Makedonia (SKG)"}, {"Heraklion", "Nikos Kazantzakis (HER)"},
            {"Rodi", "Diagoras (RHO)"}, {"Corfù", "Ioannis Kapodistrias (CFU)"}, {"Zante", "Zakynthos (ZTH)"},
            {"Candia", "Chania (CHQ)"}, {"Kalamata", "KLX"}, {"Santorini", "JTR"},

            // Turchia
            {"Istanbul", "Istanbul (IST)"}, {"Istanbul", "Sabiha Gökçen (SAW)"}, {"Ankara", "Esenboğa (ESB)"},
            {"Antalya", "AYT"}, {"Smirne", "Adnan Menderes (ADB)"}, {"Bodrum", "BJV"}, {"Dalaman", "DLM"},

            // Stati Uniti
            {"New York", "JFK"}, {"New York", "Newark (EWR)"}, {"Los Angeles", "LAX"}, {"Chicago", "O'Hare (ORD)"},
            {"Miami", "MIA"}, {"San Francisco", "SFO"}, {"Las Vegas", "LAS"}, {"Boston", "BOS"}, {"Washington", "Dulles (IAD)"},
            {"Atlanta", "ATL"}, {"Dallas", "DFW"}, {"Denver", "DEN"}, {"Seattle", "SEA"}, {"Orlando", "MCO"}, {"Houston", "IAH"},
            {"Philadelphia", "PHL"}, {"Phoenix", "PHX"}, {"Detroit", "DTW"}, {"Minneapolis", "MSP"}, {"San Diego", "SAN"},

            // Canada
            {"Toronto", "Pearson (YYZ)"}, {"Vancouver", "YVR"}, {"Montreal", "Trudeau (YUL)"}, {"Calgary", "YYC"},
            {"Ottawa", "YOW"}, {"Edmonton", "YEG"}, {"Quebec", "YQB"},

            // Sud America
            {"San Paolo", "Guarulhos (GRU)"}, {"Rio de Janeiro", "Galeão (GIG)"}, {"Brasilia", "BSB"},
            {"Buenos Aires", "Ezeiza (EZE)"}, {"Santiago", "Arturo Merino Benítez (SCL)"}, {"Lima", "Jorge Chávez (LIM)"},
            {"Bogotà", "El Dorado (BOG)"}, {"Caracas", "Simón Bolívar (CCS)"}, {"Montevideo", "Carrasco (MVD)"},

            // Asia
            {"Tokyo", "Narita (NRT)"}, {"Tokyo", "Haneda (HND)"}, {"Osaka", "Kansai (KIX)"}, {"Seoul", "Incheon (ICN)"},
            {"Pechino", "Capital (PEK)"}, {"Shanghai", "Pudong (PVG)"}, {"Hong Kong", "HKG"}, {"Singapore", "SIN"},
            {"Bangkok", "Suvarnabhumi (BKK)"}, {"Kuala Lumpur", "KUL"}, {"Mumbai", "Chhatrapati Shivaji (BOM)"},
            {"New Delhi", "Indira Gandhi (DEL)"}, {"Dubai", "DXB"}, {"Abu Dhabi", "AUH"}, {"Doha", "DOH"},
            {"Tel Aviv", "Ben Gurion (TLV)"}, {"Riyad", "King Khalid (RUH)"}, {"Istambul", "IST"},

            // Africa
            {"Il Cairo", "International (CAI)"}, {"Casablanca", "Mohammed V (CMN)"}, {"Tunisi", "Carthage (TUN)"},
            {"Algeri", "Houari Boumediene (ALG)"}, {"Johannesburg", "O.R. Tambo (JNB)"}, {"Città del Capo", "CPT"},
            {"Nairobi", "Jomo Kenyatta (NBO)"}, {"Addis Abeba", "Bole (ADD)"}, {"Lagos", "Murtala Muhammed (LOS)"},
            {"Dakar", "Blaise Diagne (DSS)"},

            // Australia/Oceania
            {"Sydney", "Kingsford Smith (SYD)"}, {"Melbourne", "Tullamarine (MEL)"}, {"Brisbane", "BNE"},
            {"Perth", "PER"}, {"Auckland", "AKL"}, {"Wellington", "WLG"}
    };

    @FXML
    public void initialize() {
        voliDisponibili = FXCollections.observableArrayList();
        prenotazioni = FXCollections.observableArrayList();
        random = new Random();
        aeroporti = new HashMap<>();

        // Popola città con aeroporti
        Set<String> cittaSet = new LinkedHashSet<>();
        for (String[] ca : CITTA_AEROPORTI) {
            String citta = ca[0];
            String aeroporto = ca[1];
            cittaSet.add(citta);
            aeroporti.put(citta, aeroporto);
        }

        ObservableList<String> cittaList = FXCollections.observableArrayList(cittaSet);
        cmbPartenza.setItems(cittaList);
        cmbDestinazione.setItems(cittaList);

        tabellaVoli.setItems(voliDisponibili);
        listaPrenotazioni.setItems(prenotazioni);

        // Configura colonne tabella
        for (TableColumn<?, ?> col : tabellaVoli.getColumns()) {
            if (col.getText().equals("Compagnia")) col.setCellValueFactory(new PropertyValueFactory<>("compagnia"));
            if (col.getText().equals("Partenza")) col.setCellValueFactory(new PropertyValueFactory<>("partenza"));
            if (col.getText().equals("Destinazione")) col.setCellValueFactory(new PropertyValueFactory<>("destinazione"));
            if (col.getText().equals("Data")) col.setCellValueFactory(new PropertyValueFactory<>("data"));
            if (col.getText().equals("Orario")) col.setCellValueFactory(new PropertyValueFactory<>("orario"));
            if (col.getText().equals("Prezzo")) col.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
            if (col.getText().equals("Classe")) col.setCellValueFactory(new PropertyValueFactory<>("classe"));
            if (col.getText().equals("Passeggeri")) col.setCellValueFactory(new PropertyValueFactory<>("passeggeri"));
        }

        dateAndata.setValue(LocalDate.now().plusDays(7));
        dateRitorno.setValue(LocalDate.now().plusDays(14));
        cmbPasseggeri.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6"));
        cmbPasseggeri.setValue("1");
        cmbClasse.setItems(FXCollections.observableArrayList("Economy", "Premium Economy", "Business", "First Class"));
        cmbClasse.setValue("Economy");

        cmbPartenza.setEditable(true);
        cmbDestinazione.setEditable(true);

        lblMessaggio.setText("✈️ Benvenuto su BRADAR! Seleziona partenza e destinazione (250+ città disponibili)");
    }

    @FXML
    public void cercaVoli(ActionEvent event) {
        try {
            String partenza = cmbPartenza.getEditor().getText();
            String destinazione = cmbDestinazione.getEditor().getText();
            LocalDate dataAndataVal = dateAndata.getValue();
            String numPasseggeri = cmbPasseggeri.getValue();
            String classe = cmbClasse.getValue();

            if (partenza == null || partenza.trim().isEmpty()) {
                mostraErrore("Seleziona una città di partenza!");
                return;
            }
            if (destinazione == null || destinazione.trim().isEmpty()) {
                mostraErrore("Seleziona una città di destinazione!");
                return;
            }

            // Normalizza nomi città
            partenza = normalizzaCitta(partenza);
            destinazione = normalizzaCitta(destinazione);

            if (!aeroporti.containsKey(partenza)) {
                mostraErrore("Città di partenza non trovata! Verifica il nome.");
                return;
            }
            if (!aeroporti.containsKey(destinazione)) {
                mostraErrore("Città di destinazione non trovata! Verifica il nome.");
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
            String[] compagnie = {"BRADAR", "BRADAR Express", "BRADAR Air", "BRADAR International", "BRADAR Cargo", "Sky BRADAR"};

            for (int i = 0; i < 6; i++) {
                String orario = orari[random.nextInt(orari.length)];
                int distanza = Math.abs(partenza.hashCode() - destinazione.hashCode()) % 500 + 100;
                int prezzoBase = calcolaPrezzo(distanza, classe);
                int prezzo = prezzoBase + random.nextInt(150);

                String partenzaConAeroporto = partenza + " (" + aeroporti.get(partenza) + ")";
                String destinazioneConAeroporto = destinazione + " (" + aeroporti.get(destinazione) + ")";

                Volo volo = new Volo(
                        compagnie[random.nextInt(compagnie.length)],
                        partenzaConAeroporto,
                        destinazioneConAeroporto,
                        dataAndataVal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        orario,
                        String.valueOf(prezzo),
                        classe,
                        numPasseggeri
                );
                voliDisponibili.add(volo);
            }

            lblMessaggio.setText("✅ Trovati " + voliDisponibili.size() + " voli da " + partenza + " a " + destinazione);

        } catch (Exception e) {
            mostraErrore("Errore: " + e.getMessage());
        }
    }

    private int calcolaPrezzo(int distanza, String classe) {
        int base = 50 + distanza / 10;
        switch (classe) {
            case "Premium Economy": return base * 2;
            case "Business": return base * 3;
            case "First Class": return base * 5;
            default: return base;
        }
    }

    private String normalizzaCitta(String input) {
        for (String citta : aeroporti.keySet()) {
            if (citta.equalsIgnoreCase(input.trim()) || input.trim().toLowerCase().contains(citta.toLowerCase())) {
                return citta;
            }
        }
        return input.trim();
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
            stage.setTitle("BRADAR - Dati Passeggero");
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
            alert.setHeaderText("BRADAR - Prenotazioni effettuate");
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
        cmbPartenza.getEditor().clear();
        cmbDestinazione.getEditor().clear();
        dateAndata.setValue(LocalDate.now().plusDays(7));
        dateRitorno.setValue(LocalDate.now().plusDays(14));
        cmbPasseggeri.setValue("1");
        cmbClasse.setValue("Economy");
        voliDisponibili.clear();
        lblMessaggio.setText("🔄 Form resettato! Inserisci nuove città.");
    }

    @FXML
    public void scambiaCitta(ActionEvent event) {
        String partenza = cmbPartenza.getEditor().getText();
        String destinazione = cmbDestinazione.getEditor().getText();
        if (partenza != null && !partenza.trim().isEmpty() &&
                destinazione != null && !destinazione.trim().isEmpty()) {
            cmbPartenza.getEditor().setText(destinazione);
            cmbDestinazione.getEditor().setText(partenza);
            lblMessaggio.setText("🔄 Città scambiate!");
        } else {
            mostraErrore("Inserisci entrambe le città prima di scambiare.");
        }
    }

    private void mostraErrore(String msg) {
        lblMessaggio.setText("⚠️ " + msg);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("BRADAR - Errore");
        alert.setHeaderText("Operazione non riuscita");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}