-- Dati di esempio per il database KeyItaly
--
-- 21 prodotti (corrispondenti alle immagini in immaginiCatalogo/), gli utenti
-- di prova e uno storico ordini gia' popolato per poter provare il profilo
-- utente e il pannello di amministrazione senza dover creare tutto a mano.
--
-- ATTENZIONE: le password sono in chiaro e i dati delle carte sono inventati.
-- Sono dati dimostrativi, non utilizzare questo schema in produzione.

-- Il client MySQL dell'immagine ufficiale usa latin1 di default: senza
-- questa riga il testo accentato di questo file verrebbe ricodificato.
SET NAMES utf8mb4;

INSERT INTO Utente VALUES ('admin','admin','admin','admin','admin','2023-05-12',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'admin'),('gianni','gianni','gianni','cerchia','gianni@gmail.com','2023-05-12',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'user');

INSERT INTO Prodotto (ID, nome, prezzo, IVA, tipo, grafica, descrizione, quantitaInStock)
VALUES 
(1, 'Tastiera Meccanica RGB', 89.99, 4, 'keyboard', '1keyboard.jpeg', 'Tastiera meccanica di alta qualità con illuminazione RGB personalizzabile. Perfetta per gaming e lavoro intensivo.', 20),
(2, 'Tastiera Compatta Wireless', 99.50, 5, 'keyboard', '2keyboard.jpeg', 'Tastiera compatta e senza fili, ideale per chi ama la praticità senza rinunciare alla qualità e alla comodità.', 20),
(3, 'Tastiera Ergonomica Professionale', 109.75, 5, 'keyboard', '3keyboard.jpeg', 'Progettata per il massimo comfort e l\'efficienza. Perfetta per sessioni di lavoro prolungate senza affaticamento.', 20),
(4, 'Tastiera Gaming Illuminata', 119.25, 5, 'keyboard', '4keyboard.jpeg', 'Prestazioni elevate e retroilluminazione intensa per giocatori esigenti. Con switch meccanici per una risposta rapida e precisa.', 20),
(5, 'Tastiera Meccanica Silenziosa', 94.50, 4, 'keyboard', '5keyboard.jpeg', 'Silenziosa e responsiva, perfetta per l\'uso in ambienti tranquilli senza disturbare chi ti circonda.', 20),
(6, 'Tastiera RGB Wireless', 99.99, 4, 'keyboard', '6keyboard.jpeg', 'Libertà senza fili con personalizzazione RGB per un\'esperienza di utilizzo unica e accattivante.', 20),
(7, 'Tastiera compatta retroilluminata', 109.00, 5, 'keyboard', '7keyboard.jpeg', 'Design compatto con retroilluminazione per un look elegante e una visibilità ottimale in ogni situazione.', 20),
(8, 'Tastiera Ergonomica con Poggiapolsi', 119.50, 5, 'keyboard', '8keyboard.jpeg', 'Supporto ottimale per il polso integrato per massimo comfort durante lunghe sessioni di utilizzo.', 20),
(9, 'Tastiera per Gaming Resistente all\'Acqua', 129.75, 5, 'keyboard', '9keyboard.jpeg', 'Resistente all\'acqua per una sicurezza aggiuntiva contro eventuali incidenti. Ideale per chiunque, dai gamer ai professionisti.', 20),
(10, 'Switch Tattili per Tastiere', 29.99, 3, 'switch', '10switch.jpeg', 'Feedback tattile preciso e responsivo per una digitazione confortevole e precisa.', 20),
(11, 'Switch Lineari per Tastiere', 24.50, 3, 'switch', '11switch.jpeg', 'Linearità e fluidità nel movimento per una risposta rapida e uniforme ad ogni pressione.', 20),
(12, 'Switch Silenziosi per Tastiere', 22.75, 3, 'switch', '12switch.jpeg', 'Silenziosi e discreti, perfetti per chi lavora in ambienti condivisi senza disturbi.', 20),
(13, 'Switch Meccanici RGB', 34.99, 4, 'switch', '13switch.jpeg', 'Switch meccanici con illuminazione RGB per un look accattivante e una sensazione di qualità.', 20),
(14, 'Switch Tattili Retroilluminati', 31.50, 4, 'switch', '14switch.jpeg', 'Sensazione tattile con illuminazione per un\'esperienza di digitazione che unisce stile e funzionalità.', 20),
(15, 'Switch Lineari Performance', 27.25, 3, 'switch', '15switch.jpeg', 'Prestazioni elevate con switch lineari per chi cerca la massima efficienza e velocità nella digitazione.', 20),
(16, 'Copritasti in PBT', 12.99, 2, 'keycap', '16keycap.jpeg', 'Materiali di alta qualità per una durata superiore e una sensazione al tatto migliorata.', 20),
(17, 'Copritasti Personalizzati', 15.50, 2, 'keycap', '17keycap.jpeg', 'Personalizza la tua tastiera con stile. Disponibili in vari colori e design per ogni gusto.', 20),
(18, 'Copritasti Trasparenti', 18.75, 2, 'keycap', '18keycap.jpeg', 'Stile trasparente per una retroilluminazione vibrante e un tocco moderno alla tua tastiera.', 20),
(19, 'Set Copritasti Ergonomici', 14.25, 2, 'keycap', '19keycap.jpeg', 'Design ergonomico per una digitazione confortevole e una distribuzione uniforme della pressione.', 20),
(20, 'Copritasti Double Shot', 16.99, 2, 'keycap', '20keycap.jpeg', 'Durata e stile con doppia iniezione per una qualità costruttiva superiore e una vita più lunga.', 20),
(21, 'Copritasti con Profilo Alto', 20.50, 3, 'keycap', '21keycap.jpeg', 'Sensazione premium con copritasti a profilo alto per una digitazione confortevole e precisa.', 20);

INSERT INTO Utente (username, pwd, nome, cognome, email, dataNascita, nomeCarta, cognomeCarta, numCarta, dataScadenza, CVV, cap, via, citta, tipo)
VALUES 
('alice93', 'passwordAlice', 'Alice', 'Rossi', 'alice@email.com', '1993-05-15', 'Alice', 'Rossi', '1234567890123456', '2025-12-31', '123', '00100', 'Via Roma 1', 'Roma', 'user'),
('bob85', 'passwordBob', 'Bob', 'Verdi', 'bob@email.com', '1985-10-20', 'Bob', 'Verdi', '9876543210987654', '2024-08-31', '456', '00123', 'Via Garibaldi 2', 'Milano', 'user'),
('carla87', 'passwordCarla', 'Carla', 'Bianchi', 'carla@email.com', '1987-07-02', 'Carla', 'Bianchi', '4567890123456789', '2023-06-30', '789', '00145', 'Via Dante 3', 'Firenze', 'user'),
('davide88', 'passwordDavide', 'Davide', 'Ferrari', 'davide@email.com', '1988-03-20', 'Davide', 'Ferrari', '1111222233334444', '2023-10-31', '111', '00132', 'Via Verdi 4', 'Napoli', 'user'),
('elisa90', 'passwordElisa', 'Elisa', 'Russo', 'elisa@email.com', '1990-12-10', 'Elisa', 'Russo', '5555666677778888', '2025-05-31', '222', '00128', 'Via Rossi 5', 'Torino', 'user'),
('fabio85', 'passwordFabio', 'Fabio', 'Galli', 'fabio@email.com', '1985-06-05', 'Fabio', 'Galli', '9999888877776666', '2024-12-31', '333', '00136', 'Via Bianchi 6', 'Bologna', 'user');

INSERT INTO Ordine (username, prezzoTotale, dataConsegna, dataOrdine, nomeConsegna, cognomeConsegna, cap, via, citta)
VALUES 
('alice93', 89.99, '2024-07-15', '2024-07-01', 'Alice', 'Rossi', '00100', 'Via Roma 1', 'Roma'),
('bob85', 119.25, '2024-07-20', '2024-07-02', 'Bob', 'Verdi', '00123', 'Via Garibaldi 2', 'Milano'),
('carla87', 22.75, '2024-07-10', '2024-07-03', 'Carla', 'Bianchi', '00145', 'Via Dante 3', 'Firenze'),
('davide88', 109.75, '2024-07-18', '2024-07-04', 'Davide', 'Ferrari', '00132', 'Via Verdi 4', 'Napoli'),
('elisa90', 94.50, '2024-07-22', '2024-07-05', 'Elisa', 'Russo', '00128', 'Via Rossi 5', 'Torino'),
('fabio85', 31.50, '2024-07-12', '2024-07-06', 'Fabio', 'Galli', '00136', 'Via Bianchi 6', 'Bologna'),
('alice93', 99.99, '2024-07-25', '2024-07-10', 'Alice', 'Rossi', '00100', 'Via Roma 1', 'Roma'),
('bob85', 30.00, '2024-07-28', '2024-07-11', 'Bob', 'Verdi', '00123', 'Via Garibaldi 2', 'Milano'),
('carla87', 16.99, '2024-07-30', '2024-07-12', 'Carla', 'Bianchi', '00145', 'Via Dante 3', 'Firenze'),
('davide88', 129.75, '2024-08-02', '2024-07-15', 'Davide', 'Ferrari', '00132', 'Via Verdi 4', 'Napoli'),
('elisa90', 27.25, '2024-08-05', '2024-07-16', 'Elisa', 'Russo', '00128', 'Via Rossi 5', 'Torino'),
('fabio85', 20.50, '2024-08-08', '2024-07-17', 'Fabio', 'Galli', '00136', 'Via Bianchi 6', 'Bologna');

INSERT INTO Acquisto (IDOrdine, IDProdotto, quantita, immagine, prezzoAq, ivaAq)
VALUES 
(1, 1, 1, '1keyboard.jpeg', 89.99, 4),
(1, 16, 2, '16keycap.jpeg', 25.98, 2),
(2, 4, 1, '4keyboard.jpeg', 119.25, 5),
(2, 13, 1, '13switch.jpeg', 34.99, 4), 
(3, 12, 1, '12switch.jpeg', 22.75, 3),
(4, 3, 1, '3keyboard.jpeg', 109.75, 5),
(4, 18, 1, '18keycap.jpeg', 18.75, 2),
(5, 5, 1, '5keyboard.jpeg', 94.50, 4),
(5, 14, 1, '14switch.jpeg', 31.50, 4),
(6, 10, 2, '10switch.jpeg', 59.98, 6),
(6, 20, 1, '20keycap.jpeg', 16.99, 2),
(7, 2, 1, '2keyboard.jpeg', 99.99, 5),
(7, 17, 1, '17keycap.jpeg', 30.00, 5),
(8, 21, 1, '21keycap.jpeg', 20.50, 3),
(8, 8, 1, '8keyboard.jpeg', 119.50, 5),
(9, 11, 1, '11switch.jpeg', 50.00, 5),
(9, 19, 1, '19keycap.jpeg', 14.25, 2),
(10, 6, 1, '6keyboard.jpeg', 99.99, 4),
(10, 12, 1, '12switch.jpeg', 22.75, 3),
(11, 15, 1, '15switch.jpeg', 27.25, 3),
(11, 4, 1, '4keyboard.jpeg', 119.25, 5),
(12, 3, 1, '3keyboard.jpeg', 109.75, 5),
(12, 13, 1, '13switch.jpeg', 34.99, 4);

UPDATE Ordine SET prezzoTotale = (
    SELECT SUM(prezzoAq)
    FROM Acquisto
    WHERE Acquisto.IDOrdine = Ordine.ID
) WHERE ID IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
