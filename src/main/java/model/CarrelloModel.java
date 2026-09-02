package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;
import model.prodotto.ProdottoOrdine;

public class CarrelloModel {
	private ArrayList<ProdottoOrdine> carrello;

    public CarrelloModel() {
        carrello = new ArrayList<>();
    }

    public List<ProdottoOrdine> getCarrello() {
        return carrello;
    }

    public void setCarrello(List<ProdottoOrdine> carrello) {
        this.carrello = (ArrayList<ProdottoOrdine>) carrello;
    }

    public synchronized void aggiungi(int ID) throws SQLException {
        for (ProdottoOrdine p : carrello) {
            if (p.getProdottoBean().getID() == ID) {
                p.incrementaQuantita();
                return;
            }
        }
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        
        ProdottoBean prodottoBean = prodottoDAO.doRetrieveByKey(ID);
        carrello.add(new ProdottoOrdine(prodottoBean));
    }

    public synchronized void setQuantita(int ID, int quantita) {
        for (ProdottoOrdine p : carrello) {
            if (p.getProdottoBean().getID() == ID) {
                if (p.getQuantita() <= 0 || quantita == 0)
                    carrello.remove(p);
                else
                    p.setQuantita(quantita);
                return;
            }
        }
    }

    public synchronized void rimuovi(int ID) {
        carrello.removeIf(p -> p.getProdottoBean().getID() == ID);
    }
}