package dni.gov.gn.gestcfu.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dni.gov.gn.gestcfu.entities.BulletinImpot;
import dni.gov.gn.gestcfu.entities.Imposition;
import dni.gov.gn.gestcfu.services.FrenchNumberToWords;
import dni.gov.gn.gestcfu.services.UtilService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GeneratePdfAim {


	public static ByteArrayInputStream impositionPdf(Imposition imposition) {


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

			double total = 0;
			for (BulletinImpot montant : imposition.getBulletin().getBulletinImpots()){
				total = total + montant.getMontant();
			}

        	// add header and footer and background
            HeaderFooterPageEventAim event = new HeaderFooterPageEventAim("N°Avis: "+imposition.getCode()+", MNT: "+gnfFormat.format(total)+", Nif: "+
																				imposition.getBulletin().getBatiment().getContribuable().getCode());
            writer.setPageEvent(event);
        	document.open();


        	Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
			Font mainFontLibelle = FontFactory.getFont("Arial", 9, Font.BOLD, BaseColor.BLACK);
			Font mainFontTitle = FontFactory.getFont("Arial", 8, BaseColor.BLACK);
			PdfPCell cellEmpty = new PdfPCell();
			cellEmpty.setBorder(0);

			/*** AIM title ...***/
			Paragraph paraAIM = new Paragraph("Avis d'imposition CFU 2023", FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.BLACK));
			paraAIM.setAlignment(Element.ALIGN_CENTER);
			paraAIM.setSpacingAfter(8f);
			document.add(paraAIM);

			Paragraph paraUnite = new Paragraph();
			Chunk libelleUnite = new Chunk("Unité des Impôts                             : ", mainFontLibelle);
			Chunk valueUnite = new Chunk("Ratoma", mainFont);
			paraUnite.add(libelleUnite);
			paraUnite.add(valueUnite);
			document.add(paraUnite);

			Paragraph paraNumAim = new Paragraph();
			Chunk libelleNumAim = new Chunk("Numéro d'Avis d'imposition           : ", mainFontLibelle);
			Chunk valueNumAim = new Chunk(imposition.getCode()+"                                  ", mainFont);
			paraNumAim.add(libelleNumAim);
			paraNumAim.add(valueNumAim);
			Chunk libelleDateAim = new Chunk("Date d'émission           : ", mainFontLibelle);
			Chunk valueDateAim = new Chunk(UtilService.formateToStringDate(imposition.getDateImposition()), mainFont);
			paraNumAim.add(libelleDateAim);
			paraNumAim.add(valueDateAim);
			paraNumAim.setSpacingAfter(8f);
			document.add(paraNumAim);

			/*** Identification title ...***/
			PdfPTable tabIdent = new PdfPTable(3);
			tabIdent.setWidthPercentage(100);
			tabIdent.setWidths(new float[]{2f, 2f, 24f});

			cellEmpty.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tabIdent.addCell(cellEmpty);
			PdfPCell cellIdentImage = new PdfPCell();
			cellIdentImage.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellIdentImage.setBorder(0);
			Image home = Image.getInstance("src/main/resources/static/home.png");
			home.setWidthPercentage(0f);
			cellIdentImage.addElement(home);
			tabIdent.addCell(cellIdentImage);
			PdfPCell cellIdent = new PdfPCell();
			cellIdent.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellIdent.setBorder(0);
			Paragraph paraIdent = new Paragraph("IDENTIFICATION DU BATIMENT", mainFontTitle);
			paraIdent.setSpacingAfter(5f);
			paraIdent.setSpacingBefore(5f);
			paraIdent.setIndentationLeft(-10f);
			cellIdent.addElement(paraIdent);
			tabIdent.addCell(cellIdent);
			document.add(tabIdent);

			PdfPTable tabIdentContent = new PdfPTable(3);
			tabIdentContent.setWidthPercentage(100);
			tabIdentContent.setSpacingAfter(5f);
			tabIdentContent.setWidths(new float[]{3f, 2f, 2f});

			PdfPCell cellTypeImpot = new PdfPCell();
			cellTypeImpot.setBorder(0);
			Paragraph paraTypeImpot = new Paragraph();
			Chunk libelleType = new Chunk("Type d'imposition   : ", mainFontLibelle);
			Chunk valueType = new Chunk("Avis d'imposition", mainFont);
			paraTypeImpot.add(libelleType);
			paraTypeImpot.add(valueType);
			cellTypeImpot.addElement(paraTypeImpot);
			tabIdentContent.addCell(cellTypeImpot);
			PdfPCell cellTypeBat = new PdfPCell();
			cellTypeBat.setBorder(0);
			Paragraph paraTypeBat = new Paragraph();
			Chunk libelleTypeBat = new Chunk("Type  : ", mainFontLibelle);
			Chunk valueTypeBat = new Chunk(imposition.getBulletin().getBatiment().getTypeBatiment().getLibelle(), mainFont);
			paraTypeBat.add(libelleTypeBat);
			paraTypeBat.add(valueTypeBat);
			cellTypeBat.addElement(paraTypeBat);
			tabIdentContent.addCell(cellTypeBat);
			PdfPCell cellCommuneBat = new PdfPCell();
			cellCommuneBat.setBorder(0);
			Paragraph paraCommuneBat = new Paragraph();
			Chunk libelleCommuneBat = new Chunk("Commune  : ", mainFontLibelle);
			Chunk valueCommuneBat = new Chunk(imposition.getBulletin().getBatiment().getSecteurBatiment().getQuartierOuDistrict().getSousPrefOuCom().getLibelle(), mainFont);
			paraCommuneBat.add(libelleCommuneBat);
			paraCommuneBat.add(valueCommuneBat);
			cellCommuneBat.addElement(paraCommuneBat);
			tabIdentContent.addCell(cellCommuneBat);

			PdfPCell cellBatNum = new PdfPCell();
			cellBatNum.setBorder(0);
			Paragraph paraBatNum = new Paragraph();
			Chunk libelleBatNum = new Chunk("Batiment N°             : ", mainFontLibelle);
			Chunk valueBatNum = new Chunk(imposition.getBulletin().getBatiment().getCode(), mainFont);
			paraBatNum.add(libelleBatNum);
			paraBatNum.add(valueBatNum);
			cellBatNum.addElement(paraBatNum);
			tabIdentContent.addCell(cellBatNum);
			PdfPCell cellEtatBat = new PdfPCell();
			cellEtatBat.setBorder(0);
			Paragraph paraEtatBat = new Paragraph();
			Chunk libelleEtatBat = new Chunk("Etat   : ", mainFontLibelle);
			Chunk valueEtatBat = new Chunk(imposition.getBulletin().getBatiment().getEtatBatiment(), mainFont);
			paraEtatBat.add(libelleEtatBat);
			paraEtatBat.add(valueEtatBat);
			cellEtatBat.addElement(paraEtatBat);
			tabIdentContent.addCell(cellEtatBat);
			PdfPCell cellQuartBat = new PdfPCell();
			cellQuartBat.setBorder(0);
			Paragraph paraQuartBat = new Paragraph();
			Chunk libelleQuartBat = new Chunk("Quartier     : ", mainFontLibelle);
			Chunk valueQuartBat = new Chunk(imposition.getBulletin().getBatiment().getSecteurBatiment().getQuartierOuDistrict().getLibelle(), mainFont);
			paraQuartBat.add(libelleQuartBat);
			paraQuartBat.add(valueQuartBat);
			cellQuartBat.addElement(paraQuartBat);
			tabIdentContent.addCell(cellQuartBat);

			PdfPCell cellBatLong = new PdfPCell();
			cellBatLong.setBorder(0);
			Paragraph paraBatLong = new Paragraph();
			Chunk libelleBatLong = new Chunk("Longitude                : ", mainFontLibelle);
			Chunk valueBatLong = new Chunk(imposition.getBulletin().getBatiment().getLongitude()!=null
					? imposition.getBulletin().getBatiment().getLongitude()
					: "", mainFont);
			paraBatLong.add(libelleBatLong);
			paraBatLong.add(valueBatLong);
			cellBatLong.addElement(paraBatLong);
			tabIdentContent.addCell(cellBatLong);
			PdfPCell cellBatLat = new PdfPCell();
			cellBatLat.setBorder(0);
			Paragraph paraBatLat = new Paragraph();
			Chunk libelleBatLat = new Chunk("Latitude   : ", mainFontLibelle);
			Chunk valueBatLat = new Chunk(imposition.getBulletin().getBatiment().getLatitude()!=null
					? imposition.getBulletin().getBatiment().getLatitude()
					: "", mainFont);
			paraBatLat.add(libelleBatLat);
			paraBatLat.add(valueBatLat);
			cellBatLat.addElement(paraBatLat);
			tabIdentContent.addCell(cellBatLat);
			PdfPCell cellSectBat = new PdfPCell();
			cellSectBat.setBorder(0);
			Paragraph paraSectBat = new Paragraph();
			Chunk libelleSectBat = new Chunk("Secteur      : ", mainFontLibelle);
			Chunk valueSectBat = new Chunk(imposition.getBulletin().getBatiment().getSecteurBatiment().getLibelle(), mainFont);
			paraSectBat.add(libelleSectBat);
			paraSectBat.add(valueSectBat);
			cellSectBat.addElement(paraSectBat);
			tabIdentContent.addCell(cellSectBat);

			PdfPCell cellEquipBat = new PdfPCell();
			cellEquipBat.setBorder(0);
			cellEquipBat.setColspan(3);
			Paragraph paraEquipBat = new Paragraph();
			Chunk libelleEquipBat = new Chunk("Equipe                     : ", mainFontLibelle);
			Chunk valueEquipBat = new Chunk("Inconnue", mainFont);
			paraEquipBat.add(libelleEquipBat);
			paraEquipBat.add(valueEquipBat);
			cellEquipBat.addElement(paraEquipBat);
			tabIdentContent.addCell(cellEquipBat);

			document.add(tabIdentContent);


			/*** Identification title contribuable ...***/
			PdfPTable tabIdentContri = new PdfPTable(3);
			tabIdentContri.setWidthPercentage(100);
			tabIdentContri.setWidths(new float[]{2f, 2f, 24f});

			cellEmpty.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tabIdentContri.addCell(cellEmpty);
			PdfPCell cellContriImage = new PdfPCell();
			cellContriImage.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellContriImage.setBorder(0);
			Image user = Image.getInstance("src/main/resources/static/user.png");
			user.setWidthPercentage(0f);
			cellContriImage.addElement(user);
			tabIdentContri.addCell(cellContriImage);
			PdfPCell cellIdentContri = new PdfPCell();
			cellIdentContri.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellIdentContri.setBorder(0);
			Paragraph paraIdentContri = new Paragraph("IDENTIFICATION DU CONTRIBUABLE", mainFontTitle);
			paraIdentContri.setSpacingAfter(5f);
			paraIdentContri.setSpacingBefore(5f);
			paraIdentContri.setIndentationLeft(-10f);
			cellIdentContri.addElement(paraIdentContri);
			tabIdentContri.addCell(cellIdentContri);
			document.add(tabIdentContri);

			PdfPTable tabIdentContentContri = new PdfPTable(3);
			tabIdentContentContri.setWidthPercentage(100);
			tabIdentContentContri.setSpacingAfter(5f);
			tabIdentContentContri.setWidths(new float[]{3f, 2f, 2f});

			PdfPCell cellCodeContri = new PdfPCell();
			cellCodeContri.setBorder(0);
			Paragraph paraCodeContri = new Paragraph();
			Chunk libelleCodeContri = new Chunk("Code                        : ", mainFontLibelle);
			Chunk valueCodeContri = new Chunk(imposition.getBulletin().getBatiment().getContribuable().getCode(), mainFont);
			paraCodeContri.add(libelleCodeContri);
			paraCodeContri.add(valueCodeContri);
			cellCodeContri.addElement(paraCodeContri);
			tabIdentContentContri.addCell(cellCodeContri);
			PdfPCell cellRaisonSocial = new PdfPCell();
			cellRaisonSocial.setBorder(0);
			cellRaisonSocial.setColspan(2);
			Paragraph paraRaisonSocial = new Paragraph();
			Chunk libelleRaisonSocial = new Chunk("Raison Sociale  : ", mainFontLibelle);
			Chunk valueRaisonSocial = new Chunk(imposition.getBulletin().getBatiment().getContribuable().getRaisonSocial(), mainFont);
			paraRaisonSocial.add(libelleRaisonSocial);
			paraRaisonSocial.add(valueRaisonSocial);
			cellRaisonSocial.addElement(paraRaisonSocial);
			tabIdentContentContri.addCell(cellRaisonSocial);

			PdfPCell cellSecteurAct = new PdfPCell();
			cellSecteurAct.setBorder(0);
			Paragraph paraSecteur = new Paragraph();
			Chunk libelleSecteur = new Chunk("Secteur d'activité   : ", mainFontLibelle);
			Chunk valueSecteur = new Chunk("Inconnu", mainFont);
			paraSecteur.add(libelleSecteur);
			paraSecteur.add(valueSecteur);
			cellSecteurAct.addElement(paraSecteur);
			tabIdentContentContri.addCell(cellSecteurAct);
			PdfPCell cellCommuneContri = new PdfPCell();
			cellCommuneContri.setBorder(0);
			Paragraph paraCommuneContri = new Paragraph();
			Chunk libelleCommuneContri = new Chunk("Commune          : ", mainFontLibelle);
			Chunk valueCommuneContri = new Chunk(imposition.getBulletin().getBatiment().getContribuable().getSecteurOuLocalite()!=null
					? imposition.getBulletin().getBatiment().getContribuable().getSecteurOuLocalite().getQuartierOuDistrict().getSousPrefOuCom().getLibelle()
					: "", mainFont);
			paraCommuneContri.add(libelleCommuneContri);
			paraCommuneContri.add(valueCommuneContri);
			cellCommuneContri.addElement(paraCommuneContri);
			tabIdentContentContri.addCell(cellCommuneContri);
			PdfPCell cellQuartContri = new PdfPCell();
			cellQuartContri.setBorder(0);
			Paragraph paraQuartContri = new Paragraph();
			Chunk libelleQuartContri = new Chunk("Quartier  : ", mainFontLibelle);
			Chunk valueQuartContri = new Chunk(imposition.getBulletin().getBatiment().getContribuable().getSecteurOuLocalite()!=null
					? imposition.getBulletin().getBatiment().getContribuable().getSecteurOuLocalite().getQuartierOuDistrict().getLibelle()
					: "", mainFont);
			paraQuartContri.add(libelleQuartContri);
			paraQuartContri.add(valueQuartContri);
			cellQuartContri.addElement(paraQuartContri);
			tabIdentContentContri.addCell(cellQuartContri);

			PdfPCell cellPhoneContri = new PdfPCell();
			cellPhoneContri.setBorder(0);
			//cellPhoneContri.setColspan(2);
			Paragraph paraPhoneContri = new Paragraph();
			Chunk libellePhoneContri = new Chunk("Téléphone               : ", mainFontLibelle);
			Chunk valuePhoneContri = new Chunk(imposition.getBulletin().getBatiment().getContribuable().getTelephone(), mainFont);
			paraPhoneContri.add(libellePhoneContri);
			paraPhoneContri.add(valuePhoneContri);
			cellPhoneContri.addElement(paraPhoneContri);
			tabIdentContentContri.addCell(cellPhoneContri);
			PdfPCell cellSectuerContri = new PdfPCell();
			cellSectuerContri.setBorder(0);
			cellSectuerContri.setColspan(2);
			Paragraph paraSectuerContri = new Paragraph();
			Chunk libelleSectuerContri = new Chunk("Secteur              : ", mainFontLibelle);
			Chunk valueSectuerContri = new Chunk(imposition.getBulletin().getBatiment().getContribuable().getSecteurOuLocalite()!=null
					? imposition.getBulletin().getBatiment().getContribuable().getSecteurOuLocalite().getLibelle()
					: "", mainFont);
			paraSectuerContri.add(libelleSectuerContri);
			paraSectuerContri.add(valueSectuerContri);
			cellSectuerContri.addElement(paraSectuerContri);
			tabIdentContentContri.addCell(cellSectuerContri);

			document.add(tabIdentContentContri);

			/************ mandataire info ***************/
			PdfPTable tabManda= new PdfPTable(3);
			tabManda.setWidthPercentage(100);
			tabManda.setSpacingAfter(5f);
			tabManda.setWidths(new float[]{2f, 2f, 2f});

			PdfPCell cellManda = new PdfPCell();
			cellManda.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellManda.setBorder(0);
			cellManda.setColspan(3);
			Paragraph paraManda = new Paragraph("INFORMATION DU MANDATAIRE", FontFactory.getFont("Arial", 7, Font.ITALIC, BaseColor.BLACK));
			paraManda.setSpacingAfter(5f);
			paraManda.setSpacingBefore(5f);
			cellManda.addElement(paraManda);
			tabManda.addCell(cellManda);

			PdfPCell cellNomPrenom = new PdfPCell();
			cellNomPrenom.setBorder(0);
			cellNomPrenom.setColspan(2);
			Paragraph paraNomPrenom = new Paragraph();
			Chunk libelleNomPrenom = new Chunk("Prénom & Nom   ", FontFactory.getFont("Arial", 9, Font.ITALIC, BaseColor.BLACK));
			Chunk valueNomPrenom = new Chunk(imposition.getBulletin().getBatiment().getPrenomContact()+" "+imposition.getBulletin().getBatiment().getNomContact(), FontFactory.getFont("Arial", 9, Font.ITALIC, BaseColor.BLACK));
			paraNomPrenom.add(libelleNomPrenom);
			paraNomPrenom.add(valueNomPrenom);
			cellNomPrenom.addElement(paraNomPrenom);
			tabManda.addCell(cellNomPrenom);
			PdfPCell cellRelation = new PdfPCell();
			cellRelation.setBorder(0);
			Paragraph paraRelation = new Paragraph();
			Chunk libelleRelation = new Chunk("Nature Rélation    ", FontFactory.getFont("Arial", 9, Font.ITALIC, BaseColor.BLACK));
			Chunk valueRelation = new Chunk(imposition.getBulletin().getBatiment().getNatureRelation(), FontFactory.getFont("Arial", 9, Font.ITALIC, BaseColor.BLACK));
			paraRelation.add(libelleRelation);
			paraRelation.add(valueRelation);
			cellRelation.addElement(paraRelation);
			tabManda.addCell(cellRelation);

			PdfPCell cellCommuneManda = new PdfPCell();
			cellCommuneManda.setBorder(0);
			Paragraph paraCommuneManda = new Paragraph();
			Chunk libelleCommuneManda = new Chunk("Commune          : ", FontFactory.getFont("Arial", 9, Font.ITALIC, BaseColor.BLACK));
			Chunk valueCommuneManda = new Chunk(imposition.getBulletin().getBatiment().getQuartierContact()!=null
											   ? imposition.getBulletin().getBatiment().getQuartierContact().getSousPrefOuCom().getLibelle()
											   : "", FontFactory.getFont("Arial", 9, Font.ITALIC, BaseColor.BLACK));
			paraCommuneManda.add(libelleCommuneManda);
			paraCommuneManda.add(valueCommuneManda);
			cellCommuneManda.addElement(paraCommuneManda);
			tabManda.addCell(cellCommuneManda);
			PdfPCell cellQuartManda = new PdfPCell();
			cellQuartManda.setBorder(0);
			Paragraph paraQuartManda = new Paragraph();
			Chunk libelleQuartManda = new Chunk("Quartier  : ", FontFactory.getFont("Arial", 9, Font.ITALIC, BaseColor.BLACK));
			Chunk valueQuartManda = new Chunk(imposition.getBulletin().getBatiment().getQuartierContact()!=null
											  ? imposition.getBulletin().getBatiment().getQuartierContact().getLibelle()
											  : "", FontFactory.getFont("Arial", 9, Font.ITALIC, BaseColor.BLACK));
			paraQuartManda.add(libelleQuartManda);
			paraQuartManda.add(valueQuartManda);
			cellQuartManda.addElement(paraQuartManda);
			tabManda.addCell(cellQuartManda);
			PdfPCell cellTelephoneManda = new PdfPCell();
			cellTelephoneManda.setBorder(0);
			Paragraph paraTelephoneManda = new Paragraph();
			Chunk libelleTelephoneManda = new Chunk("Téléphone  : ", FontFactory.getFont("Arial", 9, Font.ITALIC, BaseColor.BLACK));
			Chunk valueTelephoneManda = new Chunk(imposition.getBulletin().getBatiment().getTelephoneContact()!=null
												? imposition.getBulletin().getBatiment().getTelephoneContact()
												: "", FontFactory.getFont("Arial", 9, Font.ITALIC, BaseColor.BLACK));
			paraTelephoneManda.add(libelleTelephoneManda);
			paraTelephoneManda.add(valueTelephoneManda);
			cellTelephoneManda.addElement(paraTelephoneManda);
			tabManda.addCell(cellTelephoneManda);

			document.add(tabManda);

			/*** Information sur le paiement ...***/
			PdfPTable tabPaiement = new PdfPTable(3);
			tabPaiement.setWidthPercentage(100);
			tabPaiement.setWidths(new float[]{2f, 2f, 24f});

			cellEmpty.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tabPaiement.addCell(cellEmpty);
			PdfPCell cellPaiementImage = new PdfPCell();
			cellPaiementImage.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellPaiementImage.setBorder(0);
			Image paiement = Image.getInstance("src/main/resources/static/pay.png");
			paiement.setWidthPercentage(0f);
			cellPaiementImage.addElement(paiement);
			tabPaiement.addCell(cellPaiementImage);
			PdfPCell cellPaiement = new PdfPCell();
			cellPaiement.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellPaiement.setBorder(0);
			Paragraph paraPaiement = new Paragraph("INFORMATION SUR LE PAIEMENT", mainFontTitle);
			paraPaiement.setSpacingAfter(5f);
			paraPaiement.setSpacingBefore(5f);
			paraPaiement.setIndentationLeft(-10f);
			cellPaiement.addElement(paraPaiement);
			tabPaiement.addCell(cellPaiement);
			document.add(tabPaiement);

			PdfPTable tabPaiementContent = new PdfPTable(4);
			tabPaiementContent.setWidthPercentage(100);
			tabPaiementContent.setSpacingAfter(5f);
			tabPaiementContent.setWidths(new float[]{8f, 4f, 2f, 4f});

			PdfPCell cellPaiementMontant = new PdfPCell();
			cellPaiementMontant.setBorder(0);
			cellPaiementMontant.setColspan(4);
			Paragraph paraPaiementMontant = new Paragraph();
			Chunk libellePaiementMontant = new Chunk("Montant à payer       ", mainFontLibelle);
			Chunk valuePaiementMontant = new Chunk(gnfFormat.format(total), mainFont);
			paraPaiementMontant.add(libellePaiementMontant);
			paraPaiementMontant.add(valuePaiementMontant);
			cellPaiementMontant.addElement(paraPaiementMontant);
			tabPaiementContent.addCell(cellPaiementMontant);

			PdfPCell cellPaiementLetter = new PdfPCell();
			cellPaiementLetter.setBorder(0);
			cellPaiementLetter.setColspan(4);
			Paragraph paraPaiementLetter = new Paragraph();
			Chunk libellePaiementLetter = new Chunk("En toutes lettres       ", mainFontLibelle);
			Chunk valuePaiementLetter = new Chunk(FrenchNumberToWords.convert((long) total), mainFont);
			paraPaiementLetter.add(libellePaiementLetter);
			paraPaiementLetter.add(valuePaiementLetter);
			cellPaiementLetter.addElement(paraPaiementLetter);
			tabPaiementContent.addCell(cellPaiementLetter);

			PdfPCell cellDeadLne = new PdfPCell();
			cellDeadLne.setBorder(0);
			cellDeadLne.setColspan(4);
			Paragraph paraDeadLne = new Paragraph();
			paraDeadLne.setSpacingAfter(5f);
			Chunk libelleDeadLne = new Chunk("Date limite de paiement       ", FontFactory.getFont("Arial", 10, Font.BOLD, BaseColor.RED));
			Chunk valueDeadLne = new Chunk("Dans 15 Jours, a partie de la date de dépôt", FontFactory.getFont("Arial", 11, BaseColor.RED));
			paraDeadLne.add(libelleDeadLne);
			paraDeadLne.add(valueDeadLne);
			cellDeadLne.addElement(paraDeadLne);
			tabPaiementContent.addCell(cellDeadLne);


			cellEmpty.setColspan(4);
			tabPaiementContent.addCell(cellEmpty);

			PdfPCell cellNatureImpot = new PdfPCell();
			cellNatureImpot.setBorder(0);
			cellNatureImpot.setColspan(3);
			Paragraph paraNatureImpot = new Paragraph("Nature d'impôt", mainFontLibelle);
			cellNatureImpot.addElement(paraNatureImpot);
			tabPaiementContent.addCell(cellNatureImpot);
			PdfPCell cellMontant = new PdfPCell();
			cellMontant.setBorder(0);
			Paragraph paraMontant = new Paragraph("Montant", mainFontLibelle);
			cellMontant.addElement(paraMontant);
			tabPaiementContent.addCell(cellMontant);

			/****************** loop for ***********/
			for (BulletinImpot impot : imposition.getBulletin().getBulletinImpots()) {
				PdfPCell cellNatureImpotValue = new PdfPCell();
				cellNatureImpotValue.setBorder(0);
				cellNatureImpotValue.setColspan(3);
				Paragraph paraNatureImpotValue = new Paragraph(impot.getImpot().getCode()+"-"+impot.getImpot().getLibelleLong(), mainFont);
				cellNatureImpotValue.addElement(paraNatureImpotValue);
				tabPaiementContent.addCell(cellNatureImpotValue);
				PdfPCell cellMontantValue = new PdfPCell();
				cellMontantValue.setBorder(0);
				Paragraph paraMontantValue = new Paragraph(gnfFormat.format(impot.getMontant()), mainFont);
				cellMontantValue.addElement(paraMontantValue);
				tabPaiementContent.addCell(cellMontantValue);
			}


			PdfPCell cellTotalLibelle = new PdfPCell(new Paragraph("Total", mainFontLibelle));
			cellTotalLibelle.setColspan(3);
			cellTotalLibelle.setBorder(0);
			cellTotalLibelle.setPaddingLeft(350f);
			tabPaiementContent.addCell(cellTotalLibelle);
			PdfPCell cellTotalValue = new PdfPCell(new Paragraph(gnfFormat.format(total), mainFontLibelle));
			cellTotalValue.setBorder(0);
			cellTotalValue.setBorderColor(BaseColor.BLACK);
			tabPaiementContent.addCell(cellTotalValue);

			document.add(tabPaiementContent);


			/*** mode paiement ...***/
			Font mainFontModePaiement = FontFactory.getFont("Arial", 9, BaseColor.BLACK);
			PdfPTable tabModePaiement = new PdfPTable(3);
			tabModePaiement.setWidthPercentage(100);
			tabModePaiement.setWidths(new float[]{2f, 2f, 24f});

			PdfPCell cellEmptyModePaiement= new PdfPCell();
			cellEmptyModePaiement.setBorder(0);
			cellEmptyModePaiement.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tabModePaiement.addCell(cellEmptyModePaiement);
			PdfPCell cellModePaiementImage = new PdfPCell();
			cellModePaiementImage.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellModePaiementImage.setBorder(0);
			Image modePaiement = Image.getInstance("src/main/resources/static/mode.png");
			modePaiement.setWidthPercentage(0f);
			cellModePaiementImage.addElement(modePaiement);
			tabModePaiement.addCell(cellModePaiementImage);
			PdfPCell cellModePaiement = new PdfPCell();
			cellModePaiement.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cellModePaiement.setBorder(0);
			Paragraph paraModePaiement = new Paragraph("MODES DE PAIEMENT", mainFontTitle);
			paraModePaiement.setSpacingAfter(5f);
			paraModePaiement.setSpacingBefore(5f);
			paraModePaiement.setIndentationLeft(-10f);
			cellModePaiement.addElement(paraModePaiement);
			tabModePaiement.addCell(cellModePaiement);

			document.add(tabModePaiement);

			PdfPTable tabModePaiementContent = new PdfPTable(2);
			tabModePaiementContent.setWidthPercentage(100);
			tabModePaiementContent.setWidths(new float[]{4f, 4f});

			PdfPCell cellModePaiement1 = new PdfPCell();
			cellModePaiement1.setBorder(0);
			Paragraph paraModePaiement1 = new Paragraph("1 - BCRG/N°COMPTE : 2011000140-RSI", mainFontModePaiement);
			cellModePaiement1.addElement(paraModePaiement1);
			tabModePaiementContent.addCell(cellModePaiement1);
			PdfPCell cellModePaiement2 = new PdfPCell();
			cellModePaiement2.setBorder(0);
			Paragraph paraModePaiement2 = new Paragraph("2 - MOBILE MONEY : *440*4*7#", mainFontModePaiement);
			cellModePaiement2.addElement(paraModePaiement2);
			tabModePaiementContent.addCell(cellModePaiement2);

			PdfPCell cellModePaiement3 = new PdfPCell();
			cellModePaiement3.setBorder(0);
			Paragraph paraModePaiement3 = new Paragraph("3 - ORANGE MONEY : *144*4*8*1*1#", mainFontModePaiement);
			cellModePaiement3.addElement(paraModePaiement3);
			tabModePaiementContent.addCell(cellModePaiement3);
			PdfPCell cellModePaiement4 = new PdfPCell();
			cellModePaiement4.setBorder(0);
			Paragraph paraModePaiement4 = new Paragraph("4 - CREDIT RURAL : *1111*4*1#", mainFontModePaiement);
			cellModePaiement4.addElement(paraModePaiement4);
			tabModePaiementContent.addCell(cellModePaiement4);

			PdfPCell cellModePaiement5 = new PdfPCell();
			cellModePaiement5.setBorder(0);
			cellModePaiement5.setColspan(2);
			Paragraph paraModePaiement5 = new Paragraph("5 - PAYCARD : www.cfuguinne.com", mainFontModePaiement);
			cellModePaiement5.addElement(paraModePaiement5);
			tabModePaiementContent.addCell(cellModePaiement5);

			document.add(tabModePaiementContent);


			/** Conakry le ...**/
			Paragraph paraConakry = new Paragraph("Conakry, le "+tg2.format(new Date()),
					FontFactory.getFont("Arial", 10, Font.ITALIC, BaseColor.BLACK));
			paraConakry.setAlignment(Element.ALIGN_RIGHT);
			paraConakry.setSpacingBefore(0f);
			paraConakry.setSpacingAfter(5f);
			document.add(paraConakry);

			PdfPTable tabReceiver = new PdfPTable(2);
			tabReceiver.setWidthPercentage(100);
			//tabReceiver.setWidths(new float[]{4f, 4f});

			PdfPCell cellReceiver = new PdfPCell();
			cellReceiver.setBorder(0);
			Paragraph paraReceiver = new Paragraph("RECEVEUR(SE)",
					FontFactory.getFont("Arial", 9, Font.BOLD, BaseColor.BLACK));
			paraReceiver.setAlignment(Element.ALIGN_LEFT);
			cellReceiver.addElement(paraReceiver);
			tabReceiver.addCell(cellReceiver);
			PdfPCell cellChef = new PdfPCell();
			cellChef.setBorder(0);
			Paragraph paraChef = new Paragraph("LA CHEFFE DE SERVICE RECOUVREMENT",
					FontFactory.getFont("Arial", 9, Font.BOLD, BaseColor.BLACK));
			paraChef.setAlignment(Element.ALIGN_RIGHT);
			cellChef.addElement(paraChef);
			tabReceiver.addCell(cellChef);

			document.add(tabReceiver);



            document.close();


        } catch (DocumentException | IOException ex) {
        
            Logger.getLogger(GeneratePdfAim.class.getName()).log(Level.SEVERE, null, ex);
        }



        return new ByteArrayInputStream(out.toByteArray());
    }
	
	
	
}
