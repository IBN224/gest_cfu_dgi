//package dni.gov.gn.gestcfu.util;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//import org.apache.poi.ss.usermodel.BorderStyle;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.CreationHelper;
//import org.apache.poi.ss.usermodel.FillPatternType;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.sid.entities.ResultSuiviKilometrique;
//
//
//
//public class ExcelGenerator {
//
//
//	 public static ByteArrayInputStream releveToExcel(List<ResultSuiviKilometrique> relevecompteurs,
//		 											 String date1, String date2) throws IOException {
//		    String[] COLUMNs = {"N°", "Numero PARC", "DATE DU", "DATE AU", "KMS/VEH", "EQUIPEMENT"};
//
//
//		    try(
//		        Workbook workbook = new XSSFWorkbook();
//		        ByteArrayOutputStream out = new ByteArrayOutputStream();
//		    ){
//		      CreationHelper createHelper = workbook.getCreationHelper();
//
//		      Sheet sheet = workbook.createSheet("Rapport Global");
//
//		      sheet.setColumnWidth(0, 1000);
//		      sheet.setColumnWidth(1, 5000);
//		      sheet.setColumnWidth(2, 3000);
//		      sheet.setColumnWidth(3, 3000);
//		      sheet.setColumnWidth(4, 4000);
//		      sheet.setColumnWidth(5, 8000);
//		      Font headerFont = workbook.createFont();
//		      headerFont.setBold(true);
//		      headerFont.setColor(IndexedColors.BLACK.getIndex());
//
//		      CellStyle headerCellStyle = workbook.createCellStyle();
//		      headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
//		      headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
//		      headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
//		      headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
//		      headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//	          headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//	          headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
//	          headerFont.setFontHeight((short) 280);
//		      headerCellStyle.setFont(headerFont);
//
//		      // Row for Header
//		      Row headerRow = sheet.createRow(0);
//
//		      // Header
//		      for (int col = 0; col < COLUMNs.length; col++) {
//		        Cell cell = headerRow.createCell(col);
//		        cell.setCellValue(COLUMNs[col]);
//		        cell.setCellStyle(headerCellStyle);
//
//		      }
//
//		      // CellStyle for Age
//		      CellStyle ageCellStyle = workbook.createCellStyle();
//		      ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
//		      //
//		      CellStyle bodyCellStyle = workbook.createCellStyle();
//		      bodyCellStyle.setBorderTop(BorderStyle.THIN);
//		      bodyCellStyle.setBorderBottom(BorderStyle.THIN);
//		      bodyCellStyle.setBorderLeft(BorderStyle.THIN);
//		      bodyCellStyle.setBorderRight(BorderStyle.THIN);
//		      bodyCellStyle.setAlignment(HorizontalAlignment.CENTER);
//		      //
//		      Font simpleFont = workbook.createFont();
//		      simpleFont.setBold(true);
//		      CellStyle bodyCellStyleSimple = workbook.createCellStyle();
//		      bodyCellStyleSimple.setBorderTop(BorderStyle.THIN);
//		      bodyCellStyleSimple.setBorderBottom(BorderStyle.THIN);
//		      bodyCellStyleSimple.setBorderLeft(BorderStyle.THIN);
//		      bodyCellStyleSimple.setBorderRight(BorderStyle.THIN);
//		      bodyCellStyleSimple.setAlignment(HorizontalAlignment.CENTER);
//		      bodyCellStyleSimple.setFont(simpleFont);
//		      //
//		      Font numParcFont = workbook.createFont();
//		      numParcFont.setBold(true);
//		      CellStyle bodyCellStyleNumParc = workbook.createCellStyle();
//		      bodyCellStyleNumParc.setBorderTop(BorderStyle.THIN);
//		      bodyCellStyleNumParc.setBorderBottom(BorderStyle.THIN);
//		      bodyCellStyleNumParc.setBorderLeft(BorderStyle.THIN);
//		      bodyCellStyleNumParc.setBorderRight(BorderStyle.THIN);
//		      bodyCellStyleNumParc.setAlignment(HorizontalAlignment.CENTER);
//		      bodyCellStyleNumParc.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//		      bodyCellStyleNumParc.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//		      bodyCellStyleNumParc.setFont(numParcFont);
//		      int rowIdx = 1, count = 1;
//		      //double totalPv=0;
//		      for (ResultSuiviKilometrique releve : relevecompteurs) {
//		        Row row = sheet.createRow(rowIdx++);
//		       // totalPv += (double) releve[2];
//
//		        Cell cell = row.createCell(0);
//		        cell.setCellValue(count++);
//		        cell.setCellStyle(bodyCellStyle);
//		        Cell cell1 = row.createCell(1);
//		        cell1.setCellValue(releve.getNumParc());
//		        //cell1.setCellStyle(bodyCellStyle);
//		        cell1.setCellStyle(bodyCellStyleNumParc);
//		        Cell cell2 = row.createCell(2);
//		        cell2.setCellValue(date1);
//		        cell2.setCellStyle(bodyCellStyle);
//		        Cell cell3 = row.createCell(3);
//		        cell3.setCellValue(date2);
//		        cell3.setCellStyle(bodyCellStyle);
//		        Cell cell4 = row.createCell(4);
//		        cell4.setCellValue(String.valueOf(releve.getReleveParcouru()));
//		        cell4.setCellStyle(bodyCellStyleSimple);
//		        Cell cell5 = row.createCell(5);
//		        cell5.setCellValue(releve.getTypeCamion() +"  "+ releve.getGamme());
//		        cell5.setCellStyle(bodyCellStyle);
//
//
//
////		        Cell ageCell = row.createCell(3);
////		        ageCell.setCellValue(customer.getAge());
////		        ageCell.setCellStyle(ageCellStyle);
//		      }
////		      Row row = sheet.createRow(rowIdx);
////		      //Row rowtotal = sheet.createRow(rowIdx);
////		      row.createCell(2).setCellValue("Total");
////		      row.createCell(3).setCellValue(String.valueOf(totalPv));
//
//		      workbook.write(out);
//		      return new ByteArrayInputStream(out.toByteArray());
//		    }
//		  }
//
//
//}
