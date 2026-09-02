package control;

import model.acquisto.AcquistoProdotto;
import model.ordine.OrdineBean;
import model.utente.UtenteBean;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Map;

@WebServlet("/user/StampaFattura")
public class StampaFattura extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recupera il documento pdf/fattura.pdf
        try (InputStream fatturaStream = req.getServletContext().getResourceAsStream("/pdf/fattura.pdf");
        		PDDocument document = PDDocument.load(fatturaStream)) {
        	
        	UtenteBean utenteBean = (UtenteBean) req.getSession().getAttribute("utente");
        	OrdineBean ordine = null;
        	Collection<AcquistoProdotto> acquisti = null;
        	Map<OrdineBean, Collection<AcquistoProdotto>> ordini = (Map<OrdineBean, Collection<AcquistoProdotto>>) req.getSession().getAttribute("ordini");
        	int IDOrdine = Integer.parseInt(req.getParameter("IDOrdine"));
        	
        	for (Map.Entry<OrdineBean, Collection<AcquistoProdotto>> entry : ordini.entrySet()) {
        		OrdineBean ordineBean = entry.getKey();
        		
        		if (IDOrdine == ordineBean.getID()) {
        			ordine = ordineBean;
        			acquisti = entry.getValue();
        		}
        	}
        	
        	//Recupera la prima pagina
        	PDPage page = document.getPage(0);
        	
        	//Apri uno stream per aggiungere contenuto al PDF esistente
        	PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
        	
        	InputStream fontStream = req.getServletContext().getResourceAsStream("/pdf/fonts/Montserrat-Regular.ttf");
        	PDType0Font font = PDType0Font.load(document, fontStream);
        	fontStream.close();
        	
        	contentStream.setFont(font, 19);
        	contentStream.setNonStrokingColor(Color.WHITE);
        	
        	contentStream.beginText();
        	contentStream.newLineAtOffset(496, 771);
        	contentStream.showText(String.valueOf(ordine.getID()));
        	contentStream.endText();
        	
        	contentStream.beginText();
        	contentStream.newLineAtOffset(422, 744);
        	contentStream.showText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        	contentStream.endText();
        	
        	contentStream.setFont(font, 13);
        	contentStream.setNonStrokingColor(Color.BLACK);
        	
        	contentStream.beginText();
        	contentStream.newLineAtOffset(69, 622);
        	contentStream.showText(utenteBean.getNome() + " " + utenteBean.getCognome());
        	contentStream.endText();
        	
        	contentStream.beginText();
        	contentStream.newLineAtOffset(69, 603);
        	contentStream.showText("Via " + utenteBean.getVia());
        	contentStream.endText();
        	
        	contentStream.beginText();
        	contentStream.newLineAtOffset(69, 585);
        	contentStream.showText(utenteBean.getCap() + " " + utenteBean.getCitta());
        	contentStream.endText();
        	
        	contentStream.setFont(font, 12);
        	float coord = 476;
        	for (AcquistoProdotto a : acquisti) {
        		contentStream.beginText();
        		contentStream.newLineAtOffset(70, coord);
        		contentStream.showText(String.valueOf(a.getProdotto().getNome()));
        		contentStream.endText();
        		
        		contentStream.beginText();
        		contentStream.newLineAtOffset(346, coord);
        		contentStream.showText(String.valueOf(a.getAcquisto().getQuantita()));
        		contentStream.endText();
        		
        		contentStream.beginText();
        		contentStream.newLineAtOffset(407, coord);
        		contentStream.showText( a.getAcquisto().getPrezzoAq() + "\u20ac");
        		contentStream.endText();
        		
        		contentStream.beginText();
        		contentStream.newLineAtOffset(480, coord);
        		contentStream.showText((a.getAcquisto().getPrezzoAq().multiply(BigDecimal.valueOf(a.getAcquisto().getQuantita())) + "\u20ac"));
        		contentStream.endText();
        		
        		coord -= 37;
        	}
        	
        	contentStream.setFont(font, 14);
        	contentStream.beginText();
        	contentStream.newLineAtOffset(461, 201);
        	contentStream.showText(ordine.getPrezzoTotale() + "\u20ac");
        	contentStream.endText();
        	
        	contentStream.close();
        	
        	//Restituisci il pdf come risposta
        	ServletOutputStream out = resp.getOutputStream();        	
        	resp.setContentType("application/pdf");
        	resp.setCharacterEncoding("UTF-8");
        	resp.setHeader("Content-Disposition", "inline; filename=Fattura N" + String.valueOf(ordine.getID()) + ".pdf");
        	document.save(out);
        } 
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
