package model.acquisto;

import model.prodotto.ProdottoBean;

public class AcquistoProdotto {
    private AcquistoBean acquisto;
    private ProdottoBean prodotto;

    public AcquistoProdotto(AcquistoBean acquisto, ProdottoBean prodotto) {
        this.acquisto = acquisto;
        this.prodotto = prodotto;
    }

    public AcquistoBean getAcquisto() {
        return acquisto;
    }

    public void setAcquisto(AcquistoBean acquisto) {
        this.acquisto = acquisto;
    }

    public ProdottoBean getProdotto() {
        return prodotto;
    }

    public void setProdotto(ProdottoBean prodotto) {
        this.prodotto = prodotto;
    }
}
