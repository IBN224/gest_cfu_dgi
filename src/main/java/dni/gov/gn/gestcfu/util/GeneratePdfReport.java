package dni.gov.gn.gestcfu.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dni.gov.gn.gestcfu.entities.BulletinImpot;
import dni.gov.gn.gestcfu.entities.Quittance;
import dni.gov.gn.gestcfu.repositories.QuittanceRespository;
import dni.gov.gn.gestcfu.services.ApplicationContextHolder;
import dni.gov.gn.gestcfu.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneratePdfReport {


	public static ByteArrayInputStream quittancePdf(Quittance quittance) {

		QuittanceRespository quittanceRespository = ApplicationContextHolder.getContext().getBean(QuittanceRespository.class);

        Document document = new Document(PageSize.A4, 36, 36, 90, 36);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimpleDateFormat tg2 = new SimpleDateFormat("dd/MM/yyyy");
		// Create a new Locale
		Locale gn = new Locale("fr", "GN");
		// Create a Currency instance for the Locale
		//Currency gnf = Currency.getInstance(gn);
		// Create a formatter given the Locale
		NumberFormat gnfFormat = NumberFormat.getCurrencyInstance(gn);
        
        try {
        	
        	PdfWriter writer = PdfWriter.getInstance(document, out);     	
        	   	
        	// add header and footer and background
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);
        	document.open();

			double totalMontant=0;
			if(quittance.getType().equals("AIM")){
				for (BulletinImpot impot : quittance.getImposition().getBulletin().getBulletinImpots()){
					totalMontant = totalMontant+impot.getMontant();
				}
			}

        	Font mainFont = FontFactory.getFont("Arial", 13, BaseColor.BLACK);

			/** Conakry le ...**/
			Font mainFontConakry = FontFactory.getFont("Arial", 12, BaseColor.BLACK);
			Paragraph paraConakry = new Paragraph("Conakry, le "+tg2.format(new Date()),
									FontFactory.getFont("Arial", 12, Font.ITALIC, BaseColor.BLACK));
			paraConakry.setAlignment(Element.ALIGN_RIGHT);
			//paraConakry.setIndentationLeft(50);
			//paraConakry.setIndentationRight(0);
			paraConakry.setSpacingAfter(10);
			document.add(paraConakry);

			/*** DUPLICATA N ...***/

			Paragraph paraDuplica = new Paragraph(!quittance.getDuplicata().equals("0")?"DUPLICATA N°"+quittance.getDuplicata():"", mainFontConakry);
			paraDuplica.setAlignment(Element.ALIGN_CENTER);
			paraDuplica.setSpacingAfter(10);
			document.add(paraDuplica);

			/** QUITTANCE N... ***/
			PdfPTable tabQuittanceNum = new PdfPTable(1);
			tabQuittanceNum.setWidthPercentage(60);
			//tabQuittanceNum.setSpacingBefore(10f);
			tabQuittanceNum.setSpacingAfter(10);

			PdfPCell cellQuit = new PdfPCell(new Paragraph("QUITTANCE N° "+quittance.getCode()+" Du "+UtilService.formateToStringDate(quittance.getDateQuittance()),
											 FontFactory.getFont("Arial", 14, BaseColor.BLACK)));
			//cellQuit.setBackgroundColor(BaseColor.GRAY);
			//cellQuit.setPaddingLeft(10);
			cellQuit.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellQuit.setVerticalAlignment(Element.ALIGN_CENTER);
			cellQuit.setBorderColor(BaseColor.BLACK);
			cellQuit.setBorderWidth(2f);
			cellQuit.setExtraParagraphSpace(5f);
			tabQuittanceNum.addCell(cellQuit);
			document.add(tabQuittanceNum);


			/** MAIN TABLE... ***/
			Font mainFontLibelle = FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.BLACK);
			PdfPTable tabMain = new PdfPTable(1);
			tabMain.setWidthPercentage(100);
//			tabMain.setSpacingBefore(10f);
//			tabMain.setSpacingAfter(10f);

			PdfPCell cellContri = new PdfPCell();
			//cellQuit.setPaddingLeft(10);
			cellContri.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellContri.setVerticalAlignment(Element.ALIGN_LEFT);
			cellContri.setBorderColor(BaseColor.BLACK);

			/******* tab1 **********/
			Paragraph para1 = new Paragraph();
			para1.setExtraParagraphSpace(5f);
			Chunk libelleContri = new Chunk("Contribuable : ", mainFontLibelle);
			Chunk valueContri = new Chunk((quittance.getType().equals("DMU")
					? quittance.getDeclaration().getBatiment().getContribuable().getCode()
					: quittance.getImposition().getBulletin().getBatiment().getContribuable().getCode())+"             ", mainFont);
			Chunk valueBatim = new Chunk(quittance.getType().equals("DMU")
					? quittance.getDeclaration().getBatiment().getContribuable().getSigle()
					: quittance.getImposition().getBulletin().getBatiment().getContribuable().getSigle(), mainFont);
			para1.add(libelleContri);
			para1.add(valueContri);
			para1.add(valueBatim);
			cellContri.addElement(para1);
			tabMain.addCell(cellContri);

			/******* tab2 **********/
			PdfPCell cellBat = new PdfPCell();
			//cellQuit.setPaddingLeft(10);
			cellBat.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellBat.setVerticalAlignment(Element.ALIGN_LEFT);
			cellBat.setBorderColor(BaseColor.BLACK);

			PdfPTable tableBat = new PdfPTable(4);
			tableBat.setWidthPercentage(100);

			PdfPCell cellLibelleBat = new PdfPCell(new Paragraph("Batiment N° : ", mainFontLibelle));
			cellLibelleBat.setBorder(0);
			tableBat.addCell(cellLibelleBat);
			PdfPCell cellValueBat = new PdfPCell(new Paragraph(quittance.getType().equals("DMU")
					? quittance.getDeclaration().getBatiment().getCode()
					: quittance.getImposition().getBulletin().getBatiment().getCode(), mainFont));
			cellValueBat.setBorder(0);
			cellValueBat.setPaddingLeft(-45f);
			tableBat.addCell(cellValueBat);
			PdfPCell cellLibelleCom = new PdfPCell(new Paragraph("Commune : ", mainFontLibelle));
			cellLibelleCom.setPaddingLeft(55f);
			cellLibelleCom.setBorder(0);
			tableBat.addCell(cellLibelleCom);
			PdfPCell cellValueCom = new PdfPCell(new Paragraph(quittance.getType().equals("DMU")
					? quittance.getDeclaration().getBatiment().getSecteurBatiment().getQuartierOuDistrict().getSousPrefOuCom().getLibelle()
					: quittance.getImposition().getBulletin().getBatiment().getSecteurBatiment().getQuartierOuDistrict().getSousPrefOuCom().getLibelle(), mainFont));
			cellValueCom.setBorder(0);
			tableBat.addCell(cellValueCom);
			PdfPCell cellLibelleEtatBat = new PdfPCell(new Paragraph("Etat              : ", mainFontLibelle));
			cellLibelleEtatBat.setBorder(0);
			tableBat.addCell(cellLibelleEtatBat);
			PdfPCell cellEtatValue = new PdfPCell(new Paragraph(quittance.getType().equals("DMU")
					? quittance.getDeclaration().getBatiment().getEtatBatiment()
					: quittance.getImposition().getBulletin().getBatiment().getEtatBatiment(), mainFont));
			cellEtatValue.setPaddingLeft(-45f);
			cellEtatValue.setBorder(0);
			tableBat.addCell(cellEtatValue);
			PdfPCell cellLibelleQuart = new PdfPCell(new Paragraph("Quartier     : ", mainFontLibelle));
			cellLibelleQuart.setPaddingLeft(55f);
			cellLibelleQuart.setBorder(0);
			tableBat.addCell(cellLibelleQuart);
			PdfPCell cellValueQuart = new PdfPCell(new Paragraph(quittance.getType().equals("DMU")
					? quittance.getDeclaration().getBatiment().getSecteurBatiment().getQuartierOuDistrict().getLibelle()
					: quittance.getImposition().getBulletin().getBatiment().getSecteurBatiment().getQuartierOuDistrict().getLibelle(), mainFont));
			cellValueQuart.setBorder(0);
			tableBat.addCell(cellValueQuart);
			cellBat.addElement(tableBat);
			tabMain.addCell(cellBat);

			/******* tab3 **********/
			PdfPCell cellDetail = new PdfPCell();
			//cellQuit.setPaddingLeft(10);
			cellDetail.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellDetail.setVerticalAlignment(Element.ALIGN_LEFT);
			cellDetail.setBorderColor(BaseColor.BLACK);

			PdfPTable tableDetail = new PdfPTable(4);
			tableDetail.setWidthPercentage(100);

			PdfPCell cellRef = new PdfPCell();
			cellRef.setColspan(4);
			Chunk paraRef = new Chunk("Référence du titre de recette", mainFontLibelle);
			paraRef.setUnderline(1f,-3f);
			cellRef.addElement(paraRef);
			cellRef.setBorder(0);
			tableDetail.addCell(cellRef);
			PdfPCell cellLibelleRestPayer = new PdfPCell(new Paragraph("Reste à payer : ", mainFontLibelle));
			cellLibelleRestPayer.setColspan(3);
			cellLibelleRestPayer.setBorder(0);
			cellLibelleRestPayer.setPaddingLeft(295f);
			tableDetail.addCell(cellLibelleRestPayer);
			PdfPCell cellRestPayerValue = new PdfPCell(new Paragraph(quittance.getType().equals("DMU")
					? gnfFormat.format(quittance.getDeclaration().getMontant() - quittance.getMontantPayer())
					: gnfFormat.format(totalMontant - quittance.getMontantPayer()), mainFont));
			cellRestPayerValue.setBorder(0);
			tableDetail.addCell(cellRestPayerValue);
			PdfPCell cellLibelleMode = new PdfPCell(new Paragraph("Mode de règlement : ", mainFontLibelle));
			cellLibelleMode.setBorder(0);
			tableDetail.addCell(cellLibelleMode);
			PdfPCell cellValueMode = new PdfPCell(new Paragraph(quittance.getModeReglement(), mainFont));
			cellValueMode.setBorder(0);
			cellValueMode.setPaddingLeft(0f);
			tableDetail.addCell(cellValueMode);
			PdfPCell cellLibelleBank = new PdfPCell(new Paragraph("Banque           : ", mainFontLibelle));
			cellLibelleBank.setPaddingLeft(37f);
			cellLibelleBank.setBorder(0);
			tableDetail.addCell(cellLibelleBank);
			PdfPCell cellValueBank = new PdfPCell(new Paragraph(!quittance.getModeReglement().equals("Autre")
					?quittance.getBank():"",  mainFont));
			cellValueBank.setBorder(0);
			tableDetail.addCell(cellValueBank);
			PdfPCell cellLibelleCheckOuRecu = new PdfPCell(new Paragraph("Checque N°     : ", mainFontLibelle));
			cellLibelleCheckOuRecu.setColspan(3);
			cellLibelleCheckOuRecu.setBorder(0);
			cellLibelleCheckOuRecu.setPaddingLeft(295f);
			tableDetail.addCell(cellLibelleCheckOuRecu);
			PdfPCell cellCheckOuRecuValue = new PdfPCell(new Paragraph(quittance.getChequeOuRecue(), mainFont));
			cellCheckOuRecuValue.setBorder(0);
			tableDetail.addCell(cellCheckOuRecuValue);
			PdfPCell cellLibelleObser = new PdfPCell(new Paragraph("Observation             : ", mainFontLibelle));
			cellLibelleObser.setColspan(3);
			cellLibelleObser.setBorder(0);
			cellLibelleObser.setPaddingLeft(0f);
			tableDetail.addCell(cellLibelleObser);
			PdfPCell cellObserValue = new PdfPCell(new Paragraph(quittance.getObservation(), mainFont));
			cellObserValue.setBorder(0);
			cellObserValue.setPaddingLeft(-260f);
			tableDetail.addCell(cellObserValue);
			cellDetail.addElement(tableDetail);
			tabMain.addCell(cellDetail);

			/******* tab4 **********/

			PdfPTable tablePeriod = new PdfPTable(7);
			tablePeriod.setWidthPercentage(100.2f);
			tablePeriod.setHorizontalAlignment(100);

			Font tableHeaderPeriod = FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.BLACK);
			Font tableBodyPeriod = FontFactory.getFont("Arial", 10, BaseColor.BLACK);

			float[] columnwidthsPeriod = {2f, 8f, 2f, 2f, 2f, 2f,5f};
			tablePeriod.setWidths(columnwidthsPeriod);

			PdfPCell cellPeriod = new PdfPCell();
			cellPeriod.setPaddingLeft(-0.2f);
			cellPeriod.setPaddingBottom(-0.2f);
			cellPeriod.setPaddingTop(-0.2f);

			PdfPCell cellPaie = new PdfPCell();
			cellPaie.setBorder(0);
			cellPaie.setColspan(2);
			Chunk paraPaie = new Chunk("Titre de paiement", mainFontLibelle);
			paraPaie.setUnderline(1f,-3f);
			cellPaie.addElement(paraPaie);
			tablePeriod.addCell(cellPaie);

			PdfPCell cellEmpty = new PdfPCell();
			cellEmpty.setBorder(0);
//			tablePeriod.addCell(cellEmpty); /** cell empty to occupy the place**/
			PdfPCell cellTitlePeriod = new PdfPCell(new Paragraph("PERIODE", tableHeaderPeriod));
			cellTitlePeriod.setColspan(4);
			cellTitlePeriod.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellTitlePeriod.setVerticalAlignment(Element.ALIGN_CENTER);
			cellTitlePeriod.setBorderColor(BaseColor.BLACK);
			cellTitlePeriod.setExtraParagraphSpace(5f);
			tablePeriod.addCell(cellTitlePeriod);
			PdfPCell cellMontant = new PdfPCell(new Paragraph("MONTANT\n PAYE", tableHeaderPeriod));
			cellMontant.setRowspan(3);
			cellMontant.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellMontant.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellMontant.setBorderColor(BaseColor.BLACK);
			//cellMontant.setExtraParagraphSpace(5f);
			tablePeriod.addCell(cellMontant);
			PdfPCell cellTypeQ = new PdfPCell(new Paragraph(quittance.getType().equals("DMU")
					? quittance.getDeclaration().getCode()+" dmu"
					: quittance.getImposition().getCode()+" avis d'imposition", mainFont));
			cellTypeQ.setPaddingLeft(40f);
			cellTypeQ.setRowspan(2);
			cellTypeQ.setColspan(2);
			cellTypeQ.setBorder(0);
			tablePeriod.addCell(cellTypeQ);
			PdfPCell cellPeriodDu = new PdfPCell(new Paragraph("DU", tableHeaderPeriod));
			cellPeriodDu.setColspan(2);
			cellPeriodDu.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellPeriodDu.setVerticalAlignment(Element.ALIGN_CENTER);
			cellPeriodDu.setBorderColor(BaseColor.BLACK);
			cellPeriodDu.setExtraParagraphSpace(5f);
			tablePeriod.addCell(cellPeriodDu);
			PdfPCell cellPeriodAu = new PdfPCell(new Paragraph("AU", tableHeaderPeriod));
			cellPeriodAu.setColspan(2);
			cellPeriodAu.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellPeriodAu.setVerticalAlignment(Element.ALIGN_CENTER);
			cellPeriodAu.setBorderColor(BaseColor.BLACK);
			cellPeriodAu.setExtraParagraphSpace(5f);
			tablePeriod.addCell(cellPeriodAu);
			PdfPCell cellMonthDu = new PdfPCell(new Paragraph("Période", tableHeaderPeriod));
			cellMonthDu.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellMonthDu.setVerticalAlignment(Element.ALIGN_CENTER);
			cellMonthDu.setBorderColor(BaseColor.BLACK);
			cellMonthDu.setExtraParagraphSpace(5f);
			tablePeriod.addCell(cellMonthDu);
			PdfPCell cellMonthdAu = new PdfPCell(new Paragraph("Année", tableHeaderPeriod));
			cellMonthdAu.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellMonthdAu.setVerticalAlignment(Element.ALIGN_CENTER);
			cellMonthdAu.setBorderColor(BaseColor.BLACK);
			cellMonthdAu.setExtraParagraphSpace(5f);
			tablePeriod.addCell(cellMonthdAu);
			PdfPCell cellYearDu = new PdfPCell(new Paragraph("Période", tableHeaderPeriod));
			cellYearDu.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellYearDu.setVerticalAlignment(Element.ALIGN_CENTER);
			cellYearDu.setBorderColor(BaseColor.BLACK);
			cellYearDu.setExtraParagraphSpace(5f);
			tablePeriod.addCell(cellYearDu);
			PdfPCell cellYearAu = new PdfPCell(new Paragraph("Année", tableHeaderPeriod));
			cellYearAu.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellYearAu.setVerticalAlignment(Element.ALIGN_CENTER);
			cellYearAu.setBorderColor(BaseColor.BLACK);
			cellYearAu.setExtraParagraphSpace(5f);
			tablePeriod.addCell(cellYearAu);
			/** content table period loop**/
			double currentMontant=0, newMontant=0, montantPayer=quittance.getMontantPayer();

			if(quittance.getType().equals("DMU")){
				tablePeriod.addCell(cellEmpty);/** cell empty to occupy the place**/
				tablePeriod.addCell(cellEmpty);/** cell empty to occupy the place**/
				PdfPCell cellMonthdDuValue = new PdfPCell(new Paragraph(String.valueOf(quittance.getDeclaration().getPeriodDebutMois()), tableBodyPeriod));
				cellMonthdDuValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellMonthdDuValue.setVerticalAlignment(Element.ALIGN_CENTER);
				cellMonthdDuValue.setBorderColor(BaseColor.BLACK);
				cellMonthdDuValue.setExtraParagraphSpace(5f);
				tablePeriod.addCell(cellMonthdDuValue);
				PdfPCell cellYearDuValue = new PdfPCell(new Paragraph(String.valueOf(quittance.getDeclaration().getPeriodDebutAnnee()), tableBodyPeriod));
				cellYearDuValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellYearDuValue.setVerticalAlignment(Element.ALIGN_CENTER);
				cellYearDuValue.setBorderColor(BaseColor.BLACK);
				cellYearDuValue.setExtraParagraphSpace(5f);
				tablePeriod.addCell(cellYearDuValue);
				PdfPCell cellMonthdAuValue = new PdfPCell(new Paragraph(String.valueOf(quittance.getDeclaration().getPeriodFinMois()), tableBodyPeriod));
				cellMonthdAuValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellMonthdAuValue.setVerticalAlignment(Element.ALIGN_CENTER);
				cellMonthdAuValue.setBorderColor(BaseColor.BLACK);
				cellMonthdAuValue.setExtraParagraphSpace(5f);
				tablePeriod.addCell(cellMonthdAuValue);
				PdfPCell cellYearAuValue = new PdfPCell(new Paragraph(String.valueOf(quittance.getDeclaration().getPeriodFinAnnee()), tableBodyPeriod));
				cellYearAuValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellYearAuValue.setVerticalAlignment(Element.ALIGN_CENTER);
				cellYearAuValue.setBorderColor(BaseColor.BLACK);
				cellYearAuValue.setExtraParagraphSpace(5f);
				tablePeriod.addCell(cellYearAuValue);
				PdfPCell cellMontantValue = new PdfPCell(new Paragraph("090 025 523 250 GNF", tableBodyPeriod));
				cellMontantValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellMontantValue.setVerticalAlignment(Element.ALIGN_CENTER);
				cellMontantValue.setBorderColor(BaseColor.BLACK);
				cellMontantValue.setExtraParagraphSpace(5f);
				tablePeriod.addCell(cellMontantValue);
			}else
				{/** if AIM**/
				for (BulletinImpot impot : quittance.getImposition().getBulletin().getBulletinImpots()) {
					PdfPCell cellImpotCodeValue = new PdfPCell(new Paragraph(String.valueOf(impot.getImpot().getCode()), tableBodyPeriod));
					cellImpotCodeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellImpotCodeValue.setVerticalAlignment(Element.ALIGN_CENTER);
					cellImpotCodeValue.setBorderColor(BaseColor.BLACK);
					cellImpotCodeValue.setExtraParagraphSpace(5f);
					tablePeriod.addCell(cellImpotCodeValue);
					PdfPCell cellImpotValue = new PdfPCell(new Paragraph(impot.getImpot().getLibelleLong(), tableBodyPeriod));
					cellImpotValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellImpotValue.setVerticalAlignment(Element.ALIGN_CENTER);
					cellImpotValue.setBorderColor(BaseColor.BLACK);
					cellImpotValue.setExtraParagraphSpace(5f);
					tablePeriod.addCell(cellImpotValue);
					PdfPCell cellMonthdDuValue = new PdfPCell(new Paragraph(String.valueOf(impot.getBulletin().getPeriodDebutMois()), tableBodyPeriod));
					cellMonthdDuValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellMonthdDuValue.setVerticalAlignment(Element.ALIGN_CENTER);
					cellMonthdDuValue.setBorderColor(BaseColor.BLACK);
					cellMonthdDuValue.setExtraParagraphSpace(5f);
					tablePeriod.addCell(cellMonthdDuValue);
					PdfPCell cellYearDuValue = new PdfPCell(new Paragraph(String.valueOf(impot.getBulletin().getPeriodDebutAnnee()), tableBodyPeriod));
					cellYearDuValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellYearDuValue.setVerticalAlignment(Element.ALIGN_CENTER);
					cellYearDuValue.setBorderColor(BaseColor.BLACK);
					cellYearDuValue.setExtraParagraphSpace(5f);
					tablePeriod.addCell(cellYearDuValue);
					PdfPCell cellMonthdAuValue = new PdfPCell(new Paragraph(String.valueOf(impot.getBulletin().getPeriodFinMois()), tableBodyPeriod));
					cellMonthdAuValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellMonthdAuValue.setVerticalAlignment(Element.ALIGN_CENTER);
					cellMonthdAuValue.setBorderColor(BaseColor.BLACK);
					cellMonthdAuValue.setExtraParagraphSpace(5f);
					tablePeriod.addCell(cellMonthdAuValue);
					PdfPCell cellYearAuValue = new PdfPCell(new Paragraph(String.valueOf(impot.getBulletin().getPeriodFinAnnee()), tableBodyPeriod));
					cellYearAuValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellYearAuValue.setVerticalAlignment(Element.ALIGN_CENTER);
					cellYearAuValue.setBorderColor(BaseColor.BLACK);
					cellYearAuValue.setExtraParagraphSpace(5f);
					tablePeriod.addCell(cellYearAuValue);

					if(montantPayer>impot.getMontant()){
						//System.out.println("1111111111111111");
						currentMontant = impot.getMontant();
						newMontant = (montantPayer - impot.getMontant()) - newMontant ;
						montantPayer = newMontant;
						System.out.println(montantPayer);
					}else if(montantPayer<impot.getMontant()){
						//System.out.println("2222222\n22222222");
						currentMontant = montantPayer;
						montantPayer = 0;
					}else if(montantPayer<0){
						//System.out.println("333333333333333333333");
						currentMontant = 0;
					}
					PdfPCell cellMontantValue = new PdfPCell(new Paragraph(gnfFormat.format(currentMontant), tableBodyPeriod));
					cellMontantValue.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellMontantValue.setVerticalAlignment(Element.ALIGN_CENTER);
					cellMontantValue.setBorderColor(BaseColor.BLACK);
					cellMontantValue.setExtraParagraphSpace(5f);
					tablePeriod.addCell(cellMontantValue);
				}
			}
			/** end loop**/
			PdfPCell cellTotalLibelle = new PdfPCell(new Paragraph("Total", tableHeaderPeriod));
			cellTotalLibelle.setColspan(6);
			cellTotalLibelle.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellTotalLibelle.setVerticalAlignment(Element.ALIGN_CENTER);
			cellTotalLibelle.setBorderColor(BaseColor.BLACK);
			cellTotalLibelle.setExtraParagraphSpace(5f);
			tablePeriod.addCell(cellTotalLibelle);
			PdfPCell cellTotalValue = new PdfPCell(new Paragraph(gnfFormat.format(quittance.getMontantPayer()), tableHeaderPeriod));
			cellTotalValue.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellTotalValue.setVerticalAlignment(Element.ALIGN_CENTER);
			cellTotalValue.setBorderColor(BaseColor.BLACK);
			cellTotalValue.setExtraParagraphSpace(5f);
			tablePeriod.addCell(cellTotalValue);

			cellPeriod.addElement(tablePeriod);
			tabMain.addCell(cellPeriod);


			document.add(tabMain);

////			Paragraph paragraph = new Paragraph("Rapport Global du 25/01/2023 au ", mainFont);
////			paragraph.setAlignment(Element.ALIGN_LEFT);
////			paragraph.setIndentationLeft(50);
////			paragraph.setIndentationRight(50);
////			paragraph.setSpacingAfter(10);
////			document.add(paragraph);
//
//
//			PdfPTable table = new PdfPTable(4);
//			table.setWidthPercentage(100);
//			table.setSpacingBefore(10f);
//			table.setSpacingAfter(10);
//
////			PdfContentByte cb = writer.getDirectContent();
////        	PdfGState gState = new PdfGState();
////        	gState.setFillOpacity(0.1f);
////        	cb.setGState(gState);
//
//
//
//
//			Font tableHeader = FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.WHITE);
//			Font tableBody = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
//
//			float[] columnwidths = {2f, 2f, 2f, 2f};
//			table.setWidths(columnwidths);
//
//			PdfPCell cell = new PdfPCell(new Paragraph("N°", tableHeader));
//			cell.setBackgroundColor(BaseColor.GRAY);
//			cell.setPaddingLeft(10);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setVerticalAlignment(Element.ALIGN_CENTER);
//			cell.setBorderColor(BaseColor.BLACK);
//			cell.setExtraParagraphSpace(5f);
//			table.addCell(cell);
//
//			PdfPCell cell1 = new PdfPCell(new Paragraph("N°PARC", tableHeader));
//			cell1.setBackgroundColor(BaseColor.GRAY);
//			cell1.setPaddingLeft(10);
//			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell1.setVerticalAlignment(Element.ALIGN_CENTER);
//			cell1.setBorderColor(BaseColor.BLACK);
//			cell1.setExtraParagraphSpace(5f);
//			table.addCell(cell1);
//
//			PdfPCell cell2 = new PdfPCell(new Paragraph("IMMATRICULATION", tableHeader));
//			cell2.setBackgroundColor(BaseColor.GRAY);
//			cell2.setPaddingLeft(10);
//			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell2.setVerticalAlignment(Element.ALIGN_CENTER);
//			cell2.setBorderColor(BaseColor.BLACK);
//			cell2.setExtraParagraphSpace(5f);
//			table.addCell(cell2);
//
//			PdfPCell cell3 = new PdfPCell(new Paragraph("PV", tableHeader));
//			cell3.setBackgroundColor(BaseColor.GRAY);
//			cell3.setPaddingLeft(10);
//			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell3.setVerticalAlignment(Element.ALIGN_CENTER);
//			cell3.setBorderColor(BaseColor.BLACK);
//			cell3.setExtraParagraphSpace(5f);
//			table.addCell(cell3);
//
//            int count =0;
//            double totalPv=0;
//            for (Quittance releve : releveCompteurs) {
//            	count ++;
//            	totalPv += (double) releve.getMontantPayer();
//
//            	PdfPCell cellvalue = new PdfPCell(new Paragraph(String.valueOf(count), tableBody));
//				cellvalue.setBackgroundColor(BaseColor.WHITE);
//				cellvalue.setPaddingLeft(10);
//				cellvalue.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cellvalue.setVerticalAlignment(Element.ALIGN_CENTER);
//				cellvalue.setBorderColor(BaseColor.BLACK);
//				cellvalue.setExtraParagraphSpace(5f);
//				table.addCell(cellvalue);
//
//				PdfPCell cell1value = new PdfPCell(new Paragraph(String.valueOf(releve.getBank()), tableBody));
//				cell1value.setBackgroundColor(BaseColor.WHITE);
//				cell1value.setPaddingLeft(10);
//				cell1value.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell1value.setVerticalAlignment(Element.ALIGN_CENTER);
//				cell1value.setBorderColor(BaseColor.BLACK);
//				cell1value.setExtraParagraphSpace(5f);
//				table.addCell(cell1value);
//
//				PdfPCell cell2value = new PdfPCell(new Paragraph(String.valueOf(releve.getBank()), tableBody));
//				cell2value.setBackgroundColor(BaseColor.WHITE);
//				cell2value.setPaddingLeft(10);
//				cell2value.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell2value.setVerticalAlignment(Element.ALIGN_CENTER);
//				cell2value.setBorderColor(BaseColor.BLACK);
//				cell2value.setExtraParagraphSpace(5f);
//				table.addCell(cell2value);
//
//				PdfPCell cell3value = new PdfPCell(new Paragraph(String.valueOf(releve.getBank()), tableBody));
//				cell3value.setBackgroundColor(BaseColor.WHITE);
//				cell3value.setPaddingLeft(10);
//				cell3value.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell3value.setVerticalAlignment(Element.ALIGN_CENTER);
//				cell3value.setBorderColor(BaseColor.BLACK);
//				cell3value.setExtraParagraphSpace(5f);
//				table.addCell(cell3value);
//
//
//            }
//            // total row
//            Font fontcell = FontFactory.getFont("Times New Roman", 11, Font.BOLD ,BaseColor.WHITE);
//
//	            PdfPCell cell4value = new PdfPCell(new Paragraph(String.valueOf(totalPv), fontcell));
//	            PdfPCell cellTotal = new PdfPCell(new Paragraph("TOTAL", fontcell));
//	            cellTotal.setColspan(3);
//	            cellTotal.setBackgroundColor(BaseColor.GRAY);
//	            cellTotal.setPaddingLeft(10);
//	            cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cellTotal.setVerticalAlignment(Element.ALIGN_CENTER);
//	            cellTotal.setBorderColor(BaseColor.BLACK);
//	            cellTotal.setExtraParagraphSpace(5f);
//	            table.addCell(cellTotal);
//
//	            cell4value.setBackgroundColor(BaseColor.GRAY);
//				cell4value.setPaddingLeft(10);
//				cell4value.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell4value.setVerticalAlignment(Element.ALIGN_CENTER);
//				cell4value.setBorderColor(BaseColor.BLACK);
//				cell4value.setExtraParagraphSpace(5f);
//	            table.addCell(cell4value);
//
//
////            PdfWriter.getInstance(document, out);
////            document.open();
//
//	            // transparency for cells in PdfPtable
//	            PdfContentByte content = writer.getDirectContent();
//				content.saveState();
//				PdfGState documentGs = new PdfGState();
//				documentGs.setFillOpacity(0.8f);//9
//				documentGs.setStrokeOpacity(1f);
//				content.setGState(documentGs);
//				//document.add(table);
//				content.restoreState();
//
//            //document.add(table);

            document.close();

//            Quittance quittance1 = quittance;
//            quittance1.setDuplicata(String.valueOf((Integer.parseInt(quittance1.getDuplicata()) + 1) - 1));
//            quittanceRespository.save(quittance1);

        } catch (DocumentException ex) {
        
            Logger.getLogger(GeneratePdfReport.class.getName()).log(Level.SEVERE, null, ex);
        }



        return new ByteArrayInputStream(out.toByteArray());
    }
	
	
	
}
