package control;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import config.MediaStorage;

@WebServlet("/api/GetImage")
public class GetImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Controllo per protezione da path traversal
		final Path baseDirPath = MediaStorage.directory(req.getServletContext());
		final Path resolvedPath = baseDirPath.resolve(req.getParameter("nome")).normalize();
		if (!resolvedPath.startsWith(baseDirPath)) {
			resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		// Recupera il tipo mime dall'estensione del path
		String mime = req.getServletContext().getMimeType(resolvedPath.toString());
		if (!Files.exists(resolvedPath) || !"image/jpeg".equals(mime)) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		resp.setContentType(mime);
		resp.setContentLength((int)Files.size(resolvedPath));

		InputStream in = Files.newInputStream(resolvedPath);
		ServletOutputStream out = resp.getOutputStream();

		// Copia i contenuti del file nell'OutputStream della HttpServletResponse
		// utilizzando un buffer di 10KB
		byte[] buf = new byte[10240];
		int count = 0;
		while ((count = in.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		out.close();
		in.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
