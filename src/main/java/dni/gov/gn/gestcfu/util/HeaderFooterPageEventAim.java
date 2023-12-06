package dni.gov.gn.gestcfu.util;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dni.gov.gn.gestcfu.services.QRCodeGenerator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

public class HeaderFooterPageEventAim extends PdfPageEventHelper{

	private PdfTemplate t;
    private Image total;
    private String qrText;

    public HeaderFooterPageEventAim(String qrText){
        this.qrText = qrText;
    }

    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer);
        addFooter(writer);
        
        try {
    	    Image background = Image.getInstance("src/main/resources/static/dgi.png");
            // This scales the image to the page,
            // use the image's width & height if you don't want to scale.
            float width = 400  ;//document.getPageSize().getWidth();
            float height = 400 ; //document.getPageSize().getHeight();
//            writer.getDirectContentUnder()
//                    .addImage(background, width, 0, 0, height, 100, 200);

            PdfGState gstate = new PdfGState();
            gstate.setFillOpacity(0.2f);
            gstate.setStrokeOpacity(0.2f);
            PdfContentByte contentUnder = writer.getDirectContentUnder();
            contentUnder.setGState(gstate);
            contentUnder.addImage(background, width, 0, 0, height, 100, 200);


        }catch (DocumentException | IOException e) {
    		// TODO: handle exception
    	}
    }

    private void addHeader(PdfWriter writer){
        PdfPTable header = new PdfPTable(3);
        try {
            // set defaults
            header.setWidths(new int[]{4, 20, 4});
            header.setTotalWidth(527);
            header.setLockedWidth(true);
            //header.getDefaultCell().setFixedHeight(40);
            header.getDefaultCell().setBorder(0);
           // header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            // add image
            Image logo = Image.getInstance("src/main/resources/static/dgi.png");
//            logo.scalePercent(7.5f, 7.5f);
//            logo.scaleAbsolute(826, 1100);
//            logo.scaleToFit(826, 1100);
            header.addCell(logo);

            // add text
            PdfPCell text = new PdfPCell();
            text.setBorder(0);
            text.setPaddingLeft(130);
            text.setHorizontalAlignment(Element.ALIGN_CENTER);
            text.setBorder(0);
            //text.setBorderColor(BaseColor.LIGHT_GRAY);
            text.addElement(new Phrase("REPUBLIQUE DE GUINÉE \n MINISTERE DU BUDGET", new Font(Font.FontFamily.HELVETICA, 9)));
            Paragraph paraDirection = new Paragraph("Direction Générale des Impôts", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD));
            paraDirection.setIndentationLeft(-8f);
            text.addElement(paraDirection);
            Paragraph paraTrait = new Paragraph("-----", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD));
            paraTrait.setIndentationLeft(40f);
            text.addElement(paraTrait);

            header.addCell(text);


            // add image
            Image logoQr = Image.getInstance(QRCodeGenerator.generateQRCodeImage(qrText,350,350, ""));
            //logoQr.scalePercent(7.5f, 7.5f);
           //logoQr.scaleAbsolute(826, 1100);
//            logo.scaleToFit(826, 1100);
            header.addCell(logoQr);

            PdfPCell cellEmpty = new PdfPCell();
            cellEmpty.setBorder(0);
            header.addCell(cellEmpty);
            header.addCell(cellEmpty);

            PdfPCell textBranding = new PdfPCell();
            textBranding.setBorder(0);
            textBranding.setPaddingLeft(15f);
            textBranding.setPaddingTop(-8f);
            Image branding = Image.getInstance("src/main/resources/static/branding.jpg");
            branding.scalePercent(3.5f, 3.5f);
//            logo.scaleAbsolute(826, 1100);
//            logo.scaleToFit(826, 1100);
            textBranding.addElement(branding);
            header.addCell(textBranding);

            // write content
            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
        } catch(DocumentException | WriterException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    private void addFooter(PdfWriter writer){
        PdfPTable footer = new PdfPTable(3);
        try {
            // set defaults
            footer.setWidths(new int[]{2, 24, 1});
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(40);
            footer.getDefaultCell().setBorder(Rectangle.TOP);
            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

         // add image
//            Image logo = Image.getInstance("src/main/resources/static/dgi.png");
//            PdfPCell textimage = new PdfPCell();
//            textimage.setPaddingTop(5);
//            textimage.setPaddingLeft(20);
//            textimage.setBorder(Rectangle.TOP);
//            textimage.setBorderColor(BaseColor.LIGHT_GRAY);
//            logo.setWidthPercentage(20);
//            textimage.addElement(logo);
           // footer.addCell(textimage);
            
//            // add copyright
//            footer.addCell(new Phrase("\u00A9 renault-truck.com", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            
            Font mainFont = FontFactory.getFont("HELVETICA", 7, BaseColor.RED);
//            PdfPCell text1 = new PdfPCell();
//            //text1.setPaddingTop(35);
//            //text1.setPaddingLeft(-445);
//            text1.setBorder(Rectangle.TOP);
//            text1.setBorderColor(BaseColor.LIGHT_GRAY);
//            text1.addElement(new Phrase("guinea.renault-trucks.com",mainFont));
            //footer.addCell(text1);
            
         // add text
            PdfPCell text = new PdfPCell();
            text.setColspan(2);
            text.setPaddingTop(1);
            //text.setPaddingLeft(-350);
            text.setHorizontalAlignment(Element.ALIGN_CENTER);
            text.setBorder(Rectangle.TOP);
            text.setBorderColor(BaseColor.LIGHT_GRAY);
            Paragraph paraText1 = new Paragraph("Payer ses impôts est une obligation citoyenne à laquelle nul ne peut et ne doit, quelque soit son status, se soustraire.",
                    new Font(Font.FontFamily.HELVETICA, 8));
            paraText1.setIndentationLeft(50f);
            text.addElement(paraText1);
            Paragraph paraText2 = new Paragraph("La Direction Générale Des impôts vous remercie pour votre civisme fiscal.",
                    new Font(Font.FontFamily.HELVETICA, 8));
            paraText2.setIndentationLeft(120f);
            text.addElement(paraText2);
            footer.addCell(text);
            

            // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            //footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
            footer.addCell(totalPageCount);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, 34, 50, canvas);
            canvas.endMarkedContentSequence();
        } catch(DocumentException  de) {
            throw new ExceptionConverter(de);
        }
    }
    
    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                totalWidth, 6, 0);
    }
  
    
    
    
}
