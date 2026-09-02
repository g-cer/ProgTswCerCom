package filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Forza UTF-8 sul corpo delle richieste.
 *
 * Senza questa impostazione il container decodifica i parametri di una POST
 * come ISO-8859-1, e i campi con caratteri accentati (nomi, indirizzi,
 * descrizioni dei prodotti) arrivano corrotti al database.
 */
@WebFilter("/*")
public class EncodingFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;
	private static final String ENCODING = "UTF-8";

	protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		if (req.getCharacterEncoding() == null) {
			req.setCharacterEncoding(ENCODING);
		}
		chain.doFilter(req, resp);
	}
}
