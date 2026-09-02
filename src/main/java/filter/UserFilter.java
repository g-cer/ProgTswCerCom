package filter;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.utente.Login;

//Filter mapping eseguito con @WebFilter invece che nel web.xml
@WebFilter("/user/*")
public class UserFilter extends HttpFilter implements Filter {
       
    public UserFilter() {
        super();
    }

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hresp = (HttpServletResponse) resp;
		
		Integer tipoUtente = (Integer) hreq.getSession().getAttribute("tipoUtente");

		if (tipoUtente == null || !tipoUtente.equals(Login.REGISTRATO)) {
			hresp.sendRedirect(hreq.getContextPath() + "/Login");
		} else {			
			chain.doFilter(req, resp);
		}
	}

}
