package control.prodotto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;

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

@WebServlet("/admin/SaveProdotto")
@MultipartConfig
public class SaveProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String PATH = MediaStorage.directory(req.getServletContext()) + File.separator;
        
        String nome  = req.getParameter("nome");
        String tipo = req.getParameter("tipo");
        int IVA = (int) Float.parseFloat(req.getParameter("IVA"));
        BigDecimal prezzo = new BigDecimal(req.getParameter("prezzo"));
        int quantita = Integer.parseInt(req.getParameter("quantita"));
        String descrizione = req.getParameter("descrizione");
        Part grafica = req.getPart("grafica");

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        
        String nomeFile = "";
        int extensionIndex = grafica.getSubmittedFileName().lastIndexOf(".");
        try {
			nomeFile = prodottoDAO.getMaxID() + tipo + grafica.getSubmittedFileName().substring(extensionIndex);
		} catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}
        
        String mime = req.getServletContext().getMimeType(nomeFile);
        if (!"image/jpeg".equals(mime)) {
        	resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
        			"L'immagine da caricare deve avere estensione jpg/jpeg");
        	return;
        }
        
        try (OutputStream outputStream = new FileOutputStream(PATH + nomeFile); 
        		InputStream inputStream = grafica.getInputStream()) {
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProdottoBean prodotto = new ProdottoBean();
        prodotto.setNome(nome);
        prodotto.setTipo(tipo);
        prodotto.setPrezzo(prezzo);
        prodotto.setIVA(IVA);
        prodotto.setDescrizione(descrizione);
        prodotto.setGrafica(nomeFile);
        prodotto.setQuantitaInStock(quantita);

        try {
			prodottoDAO.doSave(prodotto);
		} catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}

        resp.sendRedirect(req.getContextPath() + "/Catalogo");
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
