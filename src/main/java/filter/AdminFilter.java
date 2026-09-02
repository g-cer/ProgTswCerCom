package filter;

import java.io.IOException;
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

@WebFilter("/admin/*")
public class AdminFilter extends HttpFilter implements Filter {
       
    public AdminFilter() {
        super();
    }

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hresp = (HttpServletResponse) resp;
		
		Integer tipoUtente = (Integer) hreq.getSession().getAttribute("tipoUtente");

		if (tipoUtente == null || !tipoUtente.equals(Login.ADMIN)) {
			hresp.sendRedirect(hreq.getContextPath() + "/");
		} else {			
			chain.doFilter(req, resp);
		}
	}

}
