package control.prodotto;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CarrelloModel;

@WebServlet("/RimuoviProdotto")
public class CartRimuoviProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        CarrelloModel carrello;

        synchronized (session) {
            carrello = (CarrelloModel) session.getAttribute("carrello");
        }

        int ID = Integer.parseInt(req.getParameter("ID"));

        carrello.rimuovi(ID);
        resp.sendRedirect(req.getContextPath() + "/pages/carrello.jsp");
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
