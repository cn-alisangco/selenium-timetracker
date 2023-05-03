package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader2 {
	private static Map<String, HashMap<String, String>> testDataMap = new LinkedHashMap<String, HashMap<String, String>>();
	
	public ExcelReader2(String filePath, String sheetName){
		System.out.println(filePath);
		getSheetDataMap(filePath, sheetName);
	}
	
	private static void getSheetDataMap(String filePath, String sheetName){
		List<String> columnNames = new ArrayList<String>();

		try {
			FileInputStream dataFile = new FileInputStream(filePath);
			XSSFWorkbook wbook = new XSSFWorkbook(dataFile);
			XSSFSheet sheet = wbook.getSheet(sheetName);
			wbook.close();

			// GET COLUMN HEADERS AS KEYS TO HASHMAP
			Iterator<Row> rows = sheet.iterator();
			Row rowHeader = rows.next();
			Iterator<Cell> header = rowHeader.iterator();
			Cell headerValue;

			while(header.hasNext()) {
				headerValue = header.next();
				columnNames.add(getValue(headerValue));
			}

			// CONVERTS LIST TO ARRAY
			String[] headerNames = columnNames.toArray(new String[0]);

			// GETS DATA
			Row dataRow;
			Iterator<Cell> dataCell;
			Cell cellValue;
			int columnIdx = 0;
			int columnNum = headerNames.length;

			while(rows.hasNext()) { 
				// TOP TO BOTTOM MOVEMENT
				dataRow = rows.next();
				dataCell = dataRow.iterator();

				// LEFT TO RIGHT MOVEMENT
				HashMap<String, String> dataMap = new HashMap<String, String>();
				columnIdx = 0; 		// REINITIALIZE INDEX
				
				while(dataCell.hasNext() || columnIdx < columnNum) {
					try {
						cellValue = dataCell.next();
						// ASSIGNS K,V PAIR TO DATAMAP
						dataMap.put(headerNames[columnIdx], getValue(cellValue));
					}
					catch(NoSuchElementException err) {

						// ASSIGNS BLANK VALUE TO KEY
						dataMap.put(headerNames[columnIdx], "");	
					}
					columnIdx++;
				}
				testDataMap.put(dataMap.get("id"), dataMap);
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String testData(String id, String k) {
		if(testDataMap.get(id).get(k) == null) {
			throw new Error("Undefined data");
		}
		
		return testDataMap.get(id).get(k);
	}
	
	private static String getValue(Cell cellData) {
		String value = null;

		switch(cellData.getCellType()) {
		case STRING:
			value = cellData.getStringCellValue();
			break;
		case NUMERIC:
			value = NumberToTextConverter.toText(cellData.getNumericCellValue());
			break;
		case BOOLEAN:
			value = Boolean.toString(cellData.getBooleanCellValue());
			break;
		case BLANK:
			value = "";
			break;
		default:
			// do nothing
			break;
		}
		return value;
	} 
}
