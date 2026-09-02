package control.prodotto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;
import config.MediaStorage;

@WebServlet("/admin/ModificaProdotto")
@MultipartConfig
public class ModificaProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int ID = Integer.parseInt(req.getParameter("id"));

        ProdottoDAO prodottoDAO = new ProdottoDAO();

        ProdottoBean prodottoBean;
		try {
			prodottoBean = prodottoDAO.doRetrieveByKey(ID);
		} catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}
        req.setAttribute("prodotto", prodottoBean);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/pages/views/modifica.jsp");
        requestDispatcher.forward(req,resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String PATH = MediaStorage.directory(req.getServletContext()) + File.separator;
        
        int ID = Integer.parseInt(req.getParameter("id"));
        String nome  = req.getParameter("nome");
        BigDecimal prezzo = new BigDecimal(req.getParameter("prezzo"));
        int IVA = (int) Float.parseFloat(req.getParameter("IVA"));
        String descrizione = req.getParameter("descrizione");
        String pathGrafica = req.getParameter("path");
        Part grafica = req.getPart("grafica");
        int quantita = Integer.parseInt(req.getParameter("quantita"));
        
        String tipo = req.getParameter("tipo");
        if (tipo == null)
            tipo = req.getParameter("tipoVecchio");

        ProdottoDAO prodottoDAO = new ProdottoDAO();

        if (!grafica.getSubmittedFileName().equals("")) {
            //Aggiunta nuova grafica
            grafica = req.getPart("grafica");
            String nomeFile;
            int extensionIndex = grafica.getSubmittedFileName().lastIndexOf(".");

            nomeFile = ID + tipo + grafica.getSubmittedFileName().substring(extensionIndex);

            pathGrafica = nomeFile;
            
            String mime = req.getServletContext().getMimeType(nomeFile);
            if (!"image/jpeg".equals(mime)) {
            	resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            			"L'immagine da caricare deve avere estensione jpg/jpeg");
            	return;
            }

            //Eliminazione vecchia grafica
            File f = new File(PATH);
            String[] matching = f.list();
            if (matching != null) {
                for (String s : matching) {
                    if (s.startsWith(ID + tipo))
                        Files.delete(Path.of(PATH + s));
                }
            }

            try (OutputStream outputStream = new FileOutputStream(PATH + nomeFile); InputStream inputStream = grafica.getInputStream()) {
                inputStream.transferTo(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ProdottoBean prodotto = new ProdottoBean();
        prodotto.setID(ID);
        prodotto.setNome(nome);
        prodotto.setPrezzo(prezzo);
        prodotto.setIVA(IVA);
        prodotto.setTipo(tipo);
        prodotto.setDescrizione(descrizione);
        prodotto.setGrafica(pathGrafica);
        prodotto.setQuantitaInStock(quantita);

        try {
			prodottoDAO.doUpdate(prodotto);
		} catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}

        resp.sendRedirect(req.getContextPath() + "/Catalogo");
    }
}
