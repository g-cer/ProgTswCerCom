package control;

import model.acquisto.AcquistoBean;
import model.acquisto.AcquistoDAO;
import model.acquisto.AcquistoProdotto;
import model.ordine.OrdineBean;
import model.ordine.OrdineDAO;
import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;
import model.utente.UtenteBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@WebServlet("/user/Profilo")
public class Profilo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UtenteBean utenteBean = (UtenteBean) req.getSession().getAttribute("utente");

        OrdineDAO ordineDAO = new OrdineDAO();
        AcquistoDAO acquistoDAO = new AcquistoDAO();
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Map<OrdineBean, List<AcquistoProdotto>> map = null;
        
        try {
	    	//Recupera gli ordini dell'utente in sessione
	        Collection<OrdineBean> ordini = ordineDAO.doRetrieveByUsername(utenteBean.getUsername());
	        
	        // Costruisci una TreeMap ordinata per ID ordini, ad ogni ordine -> lista di AcquistoProdotto
	        map = new TreeMap<>(Comparator.comparingInt(OrdineBean::getID));
	        
	        //Aggiungi nella mappa gli ordini con la lista di AcquistoProdotto
	        
	        for (OrdineBean o : ordini) {
	        	Collection<AcquistoBean> acquisti = acquistoDAO.doRetrieveByOrdine(o.getID());
	        	List<AcquistoProdotto> acquistiProdottiList = new ArrayList<>();
	        	
	        	for (AcquistoBean acquisto : acquisti) {
	                ProdottoBean prodotto;
					prodotto = prodottoDAO.doRetrieveByKey(acquisto.getIDProdotto());
	                acquistiProdottiList.add(new AcquistoProdotto(acquisto, prodotto));
	            }
	            map.put(o, acquistiProdottiList);
	        }
        } catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}
        
        req.setAttribute("ordini", map);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/pages/views/profilo.jsp");
        requestDispatcher.forward(req,resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
