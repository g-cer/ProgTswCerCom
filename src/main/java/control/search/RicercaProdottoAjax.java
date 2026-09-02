package control.search;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

@WebServlet("/ajax/search")
public class RicercaProdottoAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		
		String query = req.getParameter("query");
		
		if (query != null && !query.isEmpty()) {
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			
			Collection<ProdottoBean> results;
			try {
				results = prodottoDAO.doSearchByName(query, 7);
			} catch (SQLException e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
				e.printStackTrace();
				return;
			}
			
			Gson gson = new Gson();
			String json = gson.toJson(results);
			
			out.print(json);
		} else {
			out.print("[]");
		}
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
