package model.prodotto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProdottoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int ID, IVA, quantitaInStock;
    private String nome, tipo, grafica, descrizione;
    private BigDecimal prezzo;
	
    public int getID() {
		return ID;
	}
	public void setID(int id) {
		ID = id;
	}
	public int getIVA() {
        return IVA;
    }
    public void setIVA(int IVA) {
        this.IVA = IVA;
    }
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getGrafica() {
		return grafica;
	}
	public void setGrafica(String grafica) {
		this.grafica = grafica;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getQuantitaInStock() {
		return quantitaInStock;
	}
	public void setQuantitaInStock(int quantitaInStock) {
		this.quantitaInStock = quantitaInStock;
	}
	public String toString() {
		return "ProdottoBean [ID=" + ID + ", IVA=" + IVA + ", quantitaInStock=" + quantitaInStock + ", nome=" + nome
				+ ", tipo=" + tipo + ", grafica=" + grafica + ", descrizione=" + descrizione + ", prezzo=" + prezzo
				+ "]";
	}
}
