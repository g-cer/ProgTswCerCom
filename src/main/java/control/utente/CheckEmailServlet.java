package control.utente;

import model.utente.UtenteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

@WebServlet("/CheckEmail")
public class CheckEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("emailReg");
        UtenteDAO utenteDAO = new UtenteDAO();
        Map<String, Boolean> responseMap = new HashMap<>();
        
        try {
	        if (utenteDAO.doRetrieveByEmail(email) != null) {
	            responseMap.put("emailExists", true);
	        } else {
	            responseMap.put("emailExists", false);
	        }
        } catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new Gson().toJson(responseMap));
    }
}
