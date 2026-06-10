# ✈️ BRADAR - Sistema di Prenotazione Voli

Sistema di prenotazione voli sviluppato in **Java** con interfaccia grafica **JavaFX** e **Scene Builder**. Stile simile al sistema ufficiale Air France.

## 🚀 Funzionalità

- Ricerca voli tra diverse città (Partenza/Destinazione)
- Selezione data andata, numero passeggeri e classe di viaggio
- Scambio rapido tra partenza e destinazione
- Tabella dinamica con i voli disponibili
- Inserimento dati personali del passeggero (nome, email, telefono, data nascita)
- Validazione campi (età ≥ 18 anni, email valida, ecc.)
- Conferma prenotazione con messaggio di successo
- Visualizzazione lista prenotazioni effettuate
- Design professionale con gradienti e ombre

## 🛠️ Tecnologie usate

- Java 21
- JavaFX 21
- Scene Builder
- Maven

## 📁 Struttura del progetto
src/main/java/application/
├── Main.java # Avvio applicazione
├── Launcher.java # Entry point
├── Controller.java # Logica principale
├── Volo.java # Modello Volo
├── DatiPersonaliController.java # Form dati passeggero
├── SuccessoController.java # Conferma prenotazione
├── PrenotazioneVoli.fxml # Schermata principale
├── DatiPersonali.fxml # Form dati
└── SuccessoPrenotazione.fxml # Schermata successo


text

## ▶️ Come eseguire

1. Clona il repository
2. Apri con IntelliJ IDEA
3. Esegui `Launcher.java` o `Main.java`

## 👨‍💻 Autore

Progetto realizzato come sistema di prenotazione voli professionale.

## 📝 Note

- Validazione completa dei dati inseriti
- Prezzi dinamici in base a distanza e classe
- Interfaccia utente intuitiva e moderna
In sintesi: il codice fa un sistema completo per prenotare voli con ricerca, inserimento dati e conferma, tutto con una bella interfaccia grafica. ✅
