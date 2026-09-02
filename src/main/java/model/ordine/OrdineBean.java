package model.ordine;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class OrdineBean implements Serializable {
	private static final long serialVersionUID = 1L;
	String username, cap, via, citta, nomeConsegna, cognomeConsegna;
    int ID;
    LocalDate dataConsegna, dataOrdine;
    BigDecimal prezzoTotale;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getNomeConsegna() {
        return nomeConsegna;
    }

    public void setNomeConsegna(String nomeConsegna) {
        this.nomeConsegna = nomeConsegna;
    }

    public String getCognomeConsegna() {
        return cognomeConsegna;
    }

    public void setCognomeConsegna(String cognomeConsegna) {
        this.cognomeConsegna = cognomeConsegna;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public LocalDate getDataConsegna() {
        return dataConsegna;
    }

    public void setDataConsegna(LocalDate dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public LocalDate getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(LocalDate dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public BigDecimal getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(BigDecimal prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

	public String toString() {
		return "OrdineBean [username=" + username + ", cap=" + cap + ", via=" + via + ", citta=" + citta
				+ ", nomeConsegna=" + nomeConsegna + ", cognomeConsegna=" + cognomeConsegna + ", ID=" + ID
				+ ", dataConsegna=" + dataConsegna + ", dataOrdine=" + dataOrdine + ", prezzoTotale=" + prezzoTotale
				+ "]";
	}
}