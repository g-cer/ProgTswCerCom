package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.utente.Login;
import model.acquisto.AcquistoBean;
import model.acquisto.AcquistoDAO;
import model.acquisto.AcquistoProdotto;
import model.ordine.OrdineBean;
import model.ordine.OrdineDAO;
import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

@WebServlet("/admin/OrdiniAdmin")
public class OrdiniAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	OrdineDAO ordineDAO = new OrdineDAO();
    	AcquistoDAO acquistoDAO = new AcquistoDAO();
    	ProdottoDAO prodottoDAO = new ProdottoDAO();
    	String username = request.getParameter("username");
        String dataInizio = request.getParameter("dataInizio");
        String dataFine = request.getParameter("dataFine");
        
        List<OrdineBean> ordini = null;
        Map<OrdineBean, List<AcquistoProdotto>> map = null;
        
        try {
	        if ((username == null || username.isEmpty()) && (dataInizio == null || dataInizio.isEmpty()) && (dataFine == null || dataFine.isEmpty())) {
	            // Se non ci sono filtri, recupera tutti gli ordini ordinati per data
				ordini = (List<OrdineBean>) ordineDAO.doRetrieveAll("dataOrdine");
	        } else {
	            // Se ci sono filtri, recupera gli ordini filtrati
	            ordini = ordineDAO.getFilteredOrders(username, dataInizio, dataFine);
	        }
	        
	        //Costruisci una TreeMap ordinata per ID ordini, ad ogni ordine -> lista di AcquistoProdotto
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
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}
        
        request.setAttribute("ordini", map);

        Integer tipoUtente = (Integer) request.getSession().getAttribute("tipoUtente");
        if (tipoUtente != null && tipoUtente.equals(Login.ADMIN))
            request.getRequestDispatcher("/pages/views/ordiniAdmin.jsp").forward(request, response);
        else
            response.sendRedirect(request.getContextPath() + "/");
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}