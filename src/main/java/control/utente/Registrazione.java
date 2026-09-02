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

@WebServlet("/Registrazione")
public class Registrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	//Il ruolo non viene mai letto dalla request: un POST costruito a mano
    	//potrebbe altrimenti registrare un amministratore
    	final String TIPO_PREDEFINITO = "user";

    	//Recupera i parametri dalla request inviata da login.jsp (form di registrazione)
    	String username = req.getParameter("usernameReg");
        String password = req.getParameter("passwordReg");
        String nome = req.getParameter("nomeReg");
        String cognome = req.getParameter("cognomeReg");
        String email = req.getParameter("emailReg");
        LocalDate dataNascita = LocalDate.parse(req.getParameter("dataNascitaReg"));

        UtenteDAO utenteDAO = new UtenteDAO();

        try {
    	//Se esiste un utente con lo stesso username o email nel database, negare la registrazione
	        if (utenteDAO.doRetrieveByKey(username) != null ||
	        	utenteDAO.doRetrieveByEmail(email) != null) {
	            	req.getSession().setAttribute("utentePresente", true);
	        } 
	        else {
	        	//Se non esiste, registra il nuovo Bean nel database
	            req.getSession().setAttribute("utentePresente", false);
	            UtenteBean utenteBean = new UtenteBean();
	            utenteBean.setUsername(username);
	            utenteBean.setPwd(password);
	            utenteBean.setNome(nome);
	            utenteBean.setCognome(cognome);
	            utenteBean.setEmail(email);
	            utenteBean.setDataNascita(dataNascita);
	            utenteBean.setTipo(TIPO_PREDEFINITO);
	            
	            utenteDAO.doSave(utenteBean);
	        }
        } catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}
        
        resp.sendRedirect(req.getContextPath() + "/Login");
    }
}
