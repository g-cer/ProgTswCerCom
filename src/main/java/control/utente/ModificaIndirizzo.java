package control.utente;

import model.utente.UtenteBean;
import model.utente.UtenteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user/ModificaIndirizzo")
public class ModificaIndirizzo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Modifica l'indirizzo dell'user (in sessione) nel database
    	UtenteBean utenteBean = (UtenteBean) req.getSession().getAttribute("utente");
        
        String viaNuova = req.getParameter("viaNuova");
        String cittaNuova = req.getParameter("cittaNuova");
        String capNuova = req.getParameter("capNuova");

        utenteBean.setVia(viaNuova);
        utenteBean.setCitta(cittaNuova);
        utenteBean.setCap(capNuova);

        UtenteDAO utenteDAO = new UtenteDAO();

        try {
			utenteDAO.doUpdate(utenteBean);
		} catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}

        resp.sendRedirect(req.getContextPath() + "/user/Profilo");
    }
}
