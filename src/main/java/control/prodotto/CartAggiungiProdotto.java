package control.prodotto;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CarrelloModel;

@WebServlet("/AggiungiProdotto")
public class CartAggiungiProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        CarrelloModel carrello;

        //Accesso alla sessione thread-safe,solo un thread alla volta può eseguire il codice all'interno del blocco
        synchronized (session) {
            carrello = (CarrelloModel) session.getAttribute("carrello");
            if (carrello == null) {
                carrello = new CarrelloModel();
                session.setAttribute("carrello", carrello);
            }
        }

        int ID = Integer.parseInt(req.getParameter("ID"));
        String quantita = req.getParameter("quantita");

        if (quantita == null) {
            try {
				carrello.aggiungi(ID);
			} catch (SQLException e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
				e.printStackTrace();
				return;
			}
            resp.sendRedirect(req.getContextPath() + "/pages/carrello.jsp");
        }
        else {
            carrello.setQuantita(ID, Integer.parseInt(quantita));
        }
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
