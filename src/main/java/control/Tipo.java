package control;

import model.prodotto.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Tipo")
public class Tipo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        try {
			req.setAttribute("prodotti", prodottoDAO.doRetrieveByTipo(req.getParameter("tipo")));
		} catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}

        req.getRequestDispatcher("/pages/views/catalogo.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
