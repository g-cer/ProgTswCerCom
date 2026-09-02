package control;

import control.utente.Login;
import model.prodotto.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Catalogo")
public class Catalogo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	ProdottoDAO prodottoDAO = new ProdottoDAO();

    	String searchQuery = (String) req.getParameter("search");
    	
    	try {
	    	if (searchQuery == null) {
				req.setAttribute("prodotti", prodottoDAO.doRetrieveAll(req.getParameter("ordine")));
	    	} else {
	    		req.setAttribute("prodotti", prodottoDAO.doSearchByName(searchQuery, 0));
	    	}
    	} catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}

        Integer tipoUtente = (Integer) req.getSession().getAttribute("tipoUtente");

        //Reindirizza l'utente al catalogo specifico in base al tipo salvato in sessione
        if (tipoUtente != null && tipoUtente.equals(Login.ADMIN)) {
        	req.getRequestDispatcher("/pages/views/catalogoAdmin.jsp").forward(req, resp);
        }
        else {
        	req.getRequestDispatcher("/pages/views/catalogo.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
