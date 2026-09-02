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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/user/ModificaPagamento")
public class ModificaPagamento extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	UtenteBean utenteBean = (UtenteBean) req.getSession().getAttribute("utente");

        String nomeCarta = req.getParameter("nomeCartaNuova");
        String cognomeCarta = req.getParameter("cognomeCartaNuova");
        String numeroCarta = req.getParameter("numCartaNuova");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataScadenza = LocalDate.parse(req.getParameter("dataScadNuova"), formatter);
        String CVV = req.getParameter("CVVNuovo");

        utenteBean.setNomeCarta(nomeCarta);
        utenteBean.setCognomeCarta(cognomeCarta);
        utenteBean.setNumCarta(numeroCarta);
        utenteBean.setDataScadenza(dataScadenza);
        utenteBean.setCvv(CVV);

        UtenteDAO utenteDAO = new UtenteDAO();
        
        try {
			utenteDAO.doUpdate(utenteBean);
		} catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}
        
        if (req.getParameter("modificaPagamento").equals("profilo"))
            resp.sendRedirect(req.getContextPath() + "/user/Profilo");
        
        else if (req.getParameter("modificaPagamento").equals("checkout"))
            resp.sendRedirect(req.getContextPath() + "/user/Checkout");
    }
}
