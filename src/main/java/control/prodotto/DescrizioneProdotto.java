package control.prodotto;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

@WebServlet("/DescrizioneProdotto")
public class DescrizioneProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
		int ID = Integer.parseInt(req.getParameter("id"));
        
		ProdottoDAO prodottoDAO = new ProdottoDAO();

        ProdottoBean prodottoBean;
		try {
			prodottoBean = prodottoDAO.doRetrieveByKey(ID);
		} catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}
        req.setAttribute("prodottoBean", prodottoBean);
        
        //Il percorso (path) fornito è relativo all'URL della richiesta corrente.
        //Può essere relativo alla servlet attuale se il percorso inizia con un '/', stessa cosa dell'usare un getServletContext()
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/pages/views/descrizione.jsp");
        requestDispatcher.forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

