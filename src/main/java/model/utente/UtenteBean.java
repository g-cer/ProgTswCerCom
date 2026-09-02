package model.utente;

import java.io.Serializable;
import java.time.LocalDate;

public class UtenteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private LocalDate dataNascita, dataScadenza;
    private String username, pwd, nome, cognome, email, nomeCarta, cognomeCarta, numCarta, cvv, cap, via, citta, tipo;
	
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNomeCarta() {
		return nomeCarta;
	}
	public void setNomeCarta(String nomeCarta) {
		this.nomeCarta = nomeCarta;
	}
	public String getCognomeCarta() {
		return cognomeCarta;
	}
	public void setCognomeCarta(String cognomeCarta) {
		this.cognomeCarta = cognomeCarta;
	}
	public String getNumCarta() {
		return numCarta;
	}
	public void setNumCarta(String numCarta) {
		this.numCarta = numCarta;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	public LocalDate getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(LocalDate dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String toString() {
		return "UtenteBean [username=" + username + ", pwd=" + pwd + ", cognome="
				+ cognome + ", nome=" + nome + ", tipo=" + tipo + ", email=" + email + ", nomeCarta="
				+ nomeCarta + ", cognomeCarta=" + cognomeCarta + ", numCarta=" + numCarta
				+  ", dataScadenza=" + dataScadenza + ", cvv=" + cvv + ", cap=" + cap + ", via=" + via + ", citta=" + citta + ", dataNascita=" + dataNascita + "]";
	}
}
