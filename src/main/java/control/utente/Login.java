package control.utente;

import model.CarrelloModel;
import model.utente.UtenteBean;
import model.utente.UtenteDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int ADMIN = 0;
    public static final int REGISTRATO = 1;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	UtenteBean utente = (UtenteBean) req.getSession().getAttribute("utente");
    	if (utente != null) {
			resp.sendRedirect(req.getContextPath() + "/");
		} else {	
			req.getRequestDispatcher("/pages/views/login.jsp").forward(req, resp);
		}
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	//Recupera i dati digitati nel form di accesso in login.jsp
    	String username = req.getParameter("username");
        String password = req.getParameter("password");

            String redirectedPage;
            try {
	            int tipoUtente = checkUser(username, password);
	            
	            if(tipoUtente >= 0) {
		            //Recupera l'utente dal database attraverso l'interfaccia DAO e lo inserisce in sessione
		            UtenteDAO utenteDAO = new UtenteDAO();
		            UtenteBean utenteBean = utenteDAO.doRetrieveByKey(username);
		            req.getSession().setAttribute("utente", utenteBean);
	            }
	            
	            //Inserisci tipo utente nella sessione
	            switch (tipoUtente) {
	                case ADMIN:
	                    req.getSession().setAttribute("tipoUtente", ADMIN);
	                    redirectedPage = "";
	                    break;
	                case REGISTRATO:
	                    req.getSession().setAttribute("tipoUtente", REGISTRATO);
	                    redirectedPage = "";
	                    break;
	                default:
	                    redirectedPage = "Login";
	            }
            } catch (SQLException e) {
    			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
    			e.printStackTrace();
    			return;
    		}
            resp.sendRedirect(req.getContextPath() + "/" + redirectedPage);
    }

    private int checkUser(String username, String password) throws SQLException {
    	UtenteDAO utenteDAO = new UtenteDAO();
        UtenteBean utenteBean = utenteDAO.doRetrieveByKey(username);

        //Recupera il tipo di utente
        if (utenteBean == null || !(utenteBean.getPwd().equals(password)))
            return -1;
        else {
            if (utenteBean.getTipo().equals("admin"))
                return ADMIN;
            else
                return REGISTRATO;
        }
    }
}
