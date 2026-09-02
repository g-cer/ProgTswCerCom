package control.prodotto;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.prodotto.ProdottoDAO;

@WebServlet("/admin/DeleteProdotto")
public class DeleteProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int ID = Integer.parseInt(req.getParameter("ID"));

        ProdottoDAO prodottoDAO = new ProdottoDAO();

	    try {
	    	//Cancellazione logica: il prodotto resta a database con tipo 'Eliminato'
	    	//perche' e' ancora referenziato dagli ordini gia' effettuati.
	    	//Per lo stesso motivo la sua immagine non viene rimossa dal disco.
			if (!prodottoDAO.deleteProdotto(ID)) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Prodotto non trovato");
				return;
			}
		} catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}

	    resp.sendRedirect(req.getContextPath() + "/Catalogo");
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
