package model.acquisto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AcquistoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	int IDAcquisto, IDOrdine, IDProdotto, quantita;
    String immagine;
    BigDecimal prezzoAq;
    int ivaAq;
    
    public int getIDAcquisto() {
        return IDAcquisto;
    }

    public void setIDAcquisto(int IDAcquisto) {
        this.IDAcquisto = IDAcquisto;
    }

    public int getIDOrdine() {
        return IDOrdine;
    }

    public void setIDOrdine(int IDOrdine) {
        this.IDOrdine = IDOrdine;
    }

    public int getIDProdotto() {
        return IDProdotto;
    }

    public void setIDProdotto(int IDProdotto) {
        this.IDProdotto = IDProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public BigDecimal getPrezzoAq() {
        return prezzoAq;
    }

    public void setPrezzoAq(BigDecimal prezzoAq) {
        this.prezzoAq = prezzoAq;
    }
    
    public int getIvaAq() {
        return ivaAq;
    }

    public void setIvaAq(int ivaAq) {
        this.ivaAq = ivaAq;
    }

	public String toString() {
		return "AcquistoBean [IDAcquisto=" + IDAcquisto + ", IDOrdine=" + IDOrdine + ", IDProdotto=" + IDProdotto
				+ ", quantita=" + quantita + ", immagine=" + immagine + ", prezzoAq=" + prezzoAq + ", ivaAq=" + ivaAq + "]";
	}
}
