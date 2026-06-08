package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Volo {
    private final StringProperty compagnia;
    private final StringProperty partenza;
    private final StringProperty destinazione;
    private final StringProperty data;
    private final StringProperty orario;
    private final StringProperty prezzo;
    private final StringProperty classe;
    private final StringProperty passeggeri;

    public Volo(String compagnia, String partenza, String destinazione,
                String data, String orario, String prezzo,
                String classe, String passeggeri) {
        this.compagnia = new SimpleStringProperty(compagnia);
        this.partenza = new SimpleStringProperty(partenza);
        this.destinazione = new SimpleStringProperty(destinazione);
        this.data = new SimpleStringProperty(data);
        this.orario = new SimpleStringProperty(orario);
        this.prezzo = new SimpleStringProperty(prezzo);
        this.classe = new SimpleStringProperty(classe);
        this.passeggeri = new SimpleStringProperty(passeggeri);
    }

    public String getCompagnia() { return compagnia.get(); }
    public String getPartenza() { return partenza.get(); }
    public String getDestinazione() { return destinazione.get(); }
    public String getData() { return data.get(); }
    public String getOrario() { return orario.get(); }
    public String getPrezzo() { return prezzo.get(); }
    public String getClasse() { return classe.get(); }
    public String getPasseggeri() { return passeggeri.get(); }

    @Override
    public String toString() {
        return String.format("%s - %s ➜ %s | %s | %s €",
                compagnia.get(), partenza.get(), destinazione.get(), data.get(), prezzo.get());
    }
}