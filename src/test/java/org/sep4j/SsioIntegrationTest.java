package org.sep4j;

import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * the integration test
 *
 * @author chenjianjx
 *
 */

public class SsioIntegrationTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void saveIfNoErrorTest() throws InvalidFormatException, IOException {
		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("fake", "Not Real");

		ITRecord record = new ITRecord();

		Collection<ITRecord> records = asList(record);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		List<DatumError> datumErrors = new ArrayList<DatumError>();

		// save it
		Ssio.saveIfNoDatumError(headerMap, records, outputStream, null, datumErrors);

		byte[] spreadsheet = outputStream.toByteArray();
		assertEquals(0, spreadsheet.length);
		assertEquals(1, datumErrors.size());

	}

	@Test
	public void saveTest_ValidAndInvalid() throws InvalidFormatException, IOException {
		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("primInt", "Primitive Int");
		headerMap.put("fake", "Not Real");

		ITRecord record = new ITRecord();
		record.setPrimInt(123);

		Collection<ITRecord> records = asList(record);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String datumErrPlaceholder = "!!ERROR!!";
		List<DatumError> datumErrors = new ArrayList<DatumError>();

		// save it
		Ssio.save(headerMap, records, outputStream, datumErrPlaceholder, datumErrors);
		byte[] spreadsheet = outputStream.toByteArray();

		// do a save for human eye check
		FileUtils.writeByteArrayToFile(createFile("saveTest_ValidAndInvalid"), spreadsheet);

		// then parse it
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions ***/
		Sheet sheet = workbook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);
		Row dataRow = sheet.getRow(1);

		Cell cell00 = headerRow.getCell(0);
		Cell cell01 = headerRow.getCell(1);
		Cell cell10 = dataRow.getCell(0);
		Cell cell11 = dataRow.getCell(1);

		// size
		assertEquals(1, sheet.getLastRowNum());
		assertEquals(2, headerRow.getLastCellNum()); // note cell num is
															// 1-based
		assertEquals(2, dataRow.getLastCellNum());

		// types
//		Assert.assertEquals(Cell.CELL_TYPE_STRING, cell00.getCellType());
//		Assert.assertEquals(Cell.CELL_TYPE_STRING, cell01.getCellType());
//		Assert.assertEquals(Cell.CELL_TYPE_STRING, cell10.getCellType());
//		Assert.assertEquals(Cell.CELL_TYPE_STRING, cell11.getCellType());
		// types
		assertEquals(CellType.STRING, cell00.getCellType());
		assertEquals(CellType.STRING, cell01.getCellType());
		assertEquals(CellType.STRING, cell10.getCellType());
		assertEquals(CellType.STRING, cell11.getCellType());

		// texts
		assertEquals("Primitive Int", cell00.getStringCellValue());
		assertEquals("Not Real", cell01.getStringCellValue());
		assertEquals("123", cell10.getStringCellValue());
		assertEquals("!!ERROR!!", cell11.getStringCellValue());

		// errors
		DatumError datumError = datumErrors.get(0);
		assertEquals(1, datumErrors.size());
		assertEquals(0, datumError.getRecordIndex());
		assertEquals("fake", datumError.getPropName());
		assertTrue(datumError.getCause().getMessage().contains("no getter method"));

	}


	@Test
	public void saveAndAppendTest_File() throws InvalidFormatException, IOException {
		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("primInt", "Primitive Int");
		headerMap.put("fake", "Not Real");
		headerMap.put("str", "Str");

		ITRecord record = new ITRecord();
		record.setPrimInt(123);
		record.setStr("first row string");

		Collection<ITRecord> records = asList(record);
		File theFile = createFile("saveAndAppendTest_File");
		String datumErrPlaceholder = "!!ERROR!!";
		List<DatumError> datumErrors = new ArrayList<DatumError>();

		// save it
		Ssio.save(headerMap, records, theFile, datumErrPlaceholder, datumErrors);



		// then parse it
		byte[] spreadsheet = FileUtils.readFileToByteArray(theFile);
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions ***/
		Sheet sheet = workbook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);
		Row dataRow = sheet.getRow(1);

		Cell cell00 = headerRow.getCell(0);
		Cell cell01 = headerRow.getCell(1);
		Cell cell02 = headerRow.getCell(2);
		Cell cell10 = dataRow.getCell(0);
		Cell cell11 = dataRow.getCell(1);
		Cell cell12 = dataRow.getCell(2);

		// texts
		assertEquals("Primitive Int", cell00.getStringCellValue());
		assertEquals("Not Real", cell01.getStringCellValue());
		assertEquals("Str", cell02.getStringCellValue());
		assertEquals("123", cell10.getStringCellValue());
		assertEquals("!!ERROR!!", cell11.getStringCellValue());
		assertEquals("first row string", cell12.getStringCellValue());

		// errors
		DatumError datumError = datumErrors.get(0);
		assertEquals(1, datumErrors.size());
		assertEquals(0, datumError.getRecordIndex());
		assertEquals("fake", datumError.getPropName());
		assertTrue(datumError.getCause().getMessage().contains("no getter method"));

		// now append 2 records
		ITRecord record2 = new ITRecord();
		record2.setPrimInt(456);
		record2.setStr("row2 string");

		ITRecord record3 = new ITRecord();
		record3.setPrimInt(789);
		record3.setStr("row3 string");

		// append them
		String datumErrPlaceholder2 = "!!APPENED-ROW-ERROR!!";
		List<DatumError> datumErrors2 = new ArrayList<>();
		Ssio.appendTo(headerMap, asList(record2, record3), theFile, datumErrPlaceholder2, datumErrors2);
		// parse again
		spreadsheet = FileUtils.readFileToByteArray(theFile);
		workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions again ***/
		sheet = workbook.getSheetAt(0);
		headerRow = sheet.getRow(0);
		dataRow = sheet.getRow(1);

		assertEquals(3, sheet.getLastRowNum());

		// the original rows should not be changed
		cell00 = headerRow.getCell(0);
		cell01 = headerRow.getCell(1);
		cell02 = headerRow.getCell(2);
		cell10 = dataRow.getCell(0);
		cell11 = dataRow.getCell(1);
		cell12 = dataRow.getCell(2);

		assertEquals("Primitive Int", cell00.getStringCellValue());
		assertEquals("Not Real", cell01.getStringCellValue());
		assertEquals("Str", cell02.getStringCellValue());
		assertEquals("123", cell10.getStringCellValue());
		assertEquals("!!ERROR!!", cell11.getStringCellValue());
		assertEquals("first row string", cell12.getStringCellValue());

		// now the new row
		Row dataRow2 = sheet.getRow(2);
		Cell cell20 = dataRow2.getCell(0);
		Cell cell21 = dataRow2.getCell(1);
		Cell cell22 = dataRow2.getCell(2);

		Row dataRow3 = sheet.getRow(3);
		Cell cell30 = dataRow3.getCell(0);
		Cell cell31 = dataRow3.getCell(1);
		Cell cell32 = dataRow3.getCell(2);

		assertEquals("456", cell20.getStringCellValue());
		assertEquals("!!APPENED-ROW-ERROR!!", cell21.getStringCellValue());
		assertEquals("row2 string", cell22.getStringCellValue());

		assertEquals("789", cell30.getStringCellValue());
		assertEquals("!!APPENED-ROW-ERROR!!", cell31.getStringCellValue());
		assertEquals("row3 string", cell32.getStringCellValue());

		// new errors
		assertEquals(2, datumErrors2.size());

		DatumError firstAppendingError = datumErrors2.get(0);
		assertEquals(0, firstAppendingError.getRecordIndex());
		assertEquals("fake", firstAppendingError.getPropName());
		assertTrue(firstAppendingError.getCause().getMessage().contains("no getter method"));


		DatumError secondAppendingError = datumErrors2.get(1);
		assertEquals(1, secondAppendingError.getRecordIndex());
		assertEquals("fake", secondAppendingError.getPropName());
		assertTrue(secondAppendingError.getCause().getMessage().contains("no getter method"));

		//append again,  ignoring errors
		ITRecord record4 = new ITRecord();
		record4.setPrimInt(123456789);
		record4.setStr("row4 string");

		// append them
		Ssio.appendTo(headerMap, asList(record4), theFile);
		// parse again
		spreadsheet = FileUtils.readFileToByteArray(theFile);
		workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions again ***/
		sheet = workbook.getSheetAt(0);
		assertEquals(4, sheet.getLastRowNum());


		Row dataRow4 = sheet.getRow(4);
		Cell cell40 = dataRow4.getCell(0);
		Cell cell41 = dataRow4.getCell(1);
		Cell cell42 = dataRow4.getCell(2);

		assertEquals("123456789", cell40.getStringCellValue());
		assertEquals("", cell41.getStringCellValue());
		assertEquals("row4 string", cell42.getStringCellValue());
	}


	@Test
	public void saveAndAppendMapsTest_File() throws InvalidFormatException, IOException {
		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("primInt", "Primitive Int");
		headerMap.put("fake", "Not Real");
		headerMap.put("str", "Str");

		LinkedHashMap<String, Object> record = new LinkedHashMap<String, Object>();
		record.put("primInt", 123);
		record.put("str",  "first row string");

		Collection<Map<String, Object>> records = asList(record);
		File theFile = createFile("saveAndAppendMapsTest_File");
		String datumErrPlaceholder = "!!ERROR!!";
		List<DatumError> datumErrors = new ArrayList<DatumError>();

		// save it
		Ssio.saveMaps(headerMap, records, theFile, datumErrPlaceholder, datumErrors);

		// then parse it
		byte[] spreadsheet = FileUtils.readFileToByteArray(theFile);
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions ***/
		Sheet sheet = workbook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);
		Row dataRow = sheet.getRow(1);

		Cell cell00 = headerRow.getCell(0);
		Cell cell01 = headerRow.getCell(1);
		Cell cell02 = headerRow.getCell(2);
		Cell cell10 = dataRow.getCell(0);
		Cell cell11 = dataRow.getCell(1);
		Cell cell12 = dataRow.getCell(2);

		// texts
		assertEquals("Primitive Int", cell00.getStringCellValue());
		assertEquals("Not Real", cell01.getStringCellValue());
		assertEquals("Str", cell02.getStringCellValue());
		assertEquals("123", cell10.getStringCellValue());
		assertEquals("", cell11.getStringCellValue());
		assertEquals("first row string", cell12.getStringCellValue());

		// errors
		assertEquals(0, datumErrors.size());

		// now append 2 records
		LinkedHashMap<String, Object> record2 = new LinkedHashMap<String, Object>();
		record2.put("primInt", 456);
		record2.put("str",  "row2 string");

		LinkedHashMap<String, Object> record3 = new LinkedHashMap<String, Object>();
		record3.put("primInt",789);
		record3.put("str",  "row3 string");

		// append them
		String datumErrPlaceholder2 = "!!APPENED-ROW-ERROR!!";
		List<DatumError> datumErrors2 = new ArrayList<>();
		Ssio.appendMapsTo(headerMap, asList(record2, record3), theFile, datumErrPlaceholder2, datumErrors2);
		// parse again
		spreadsheet = FileUtils.readFileToByteArray(theFile);
		workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions again ***/
		sheet = workbook.getSheetAt(0);
		headerRow = sheet.getRow(0);
		dataRow = sheet.getRow(1);

		assertEquals(3, sheet.getLastRowNum());

		// the original rows should not be changed
		cell00 = headerRow.getCell(0);
		cell01 = headerRow.getCell(1);
		cell02 = headerRow.getCell(2);
		cell10 = dataRow.getCell(0);
		cell11 = dataRow.getCell(1);
		cell12 = dataRow.getCell(2);

		assertEquals("Primitive Int", cell00.getStringCellValue());
		assertEquals("Not Real", cell01.getStringCellValue());
		assertEquals("Str", cell02.getStringCellValue());
		assertEquals("123", cell10.getStringCellValue());
		assertEquals("", cell11.getStringCellValue());
		assertEquals("first row string", cell12.getStringCellValue());

		// now the new row
		Row dataRow2 = sheet.getRow(2);
		Cell cell20 = dataRow2.getCell(0);
		Cell cell21 = dataRow2.getCell(1);
		Cell cell22 = dataRow2.getCell(2);

		Row dataRow3 = sheet.getRow(3);
		Cell cell30 = dataRow3.getCell(0);
		Cell cell31 = dataRow3.getCell(1);
		Cell cell32 = dataRow3.getCell(2);

		assertEquals("456", cell20.getStringCellValue());
		assertEquals("", cell21.getStringCellValue());
		assertEquals("row2 string", cell22.getStringCellValue());

		assertEquals("789", cell30.getStringCellValue());
		assertEquals("", cell31.getStringCellValue());
		assertEquals("row3 string", cell32.getStringCellValue());

		// new errors
		assertEquals(0, datumErrors2.size());
	}




	@Test
	public void saveTest_IgnoringErrors() throws InvalidFormatException, IOException {
		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("fake", "Not Real");

		ITRecord record = new ITRecord();

		Collection<ITRecord> records = asList(record);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// save it
		Ssio.save(headerMap, records, outputStream);
		byte[] spreadsheet = outputStream.toByteArray();

		// do a save for human eye check
		FileUtils.writeByteArrayToFile(createFile("saveTest_IngoringErrors"), spreadsheet);

		// then parse it
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions ***/
		Sheet sheet = workbook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);
		Row dataRow = sheet.getRow(1);

		Cell cell00 = headerRow.getCell(0);
		Cell cell10 = dataRow.getCell(0);

		// size
		assertEquals(1, sheet.getLastRowNum());
		assertEquals(1, headerRow.getLastCellNum()); // note cell num is
															// 1-based
		assertEquals(1, dataRow.getLastCellNum());

		// types
//		Assert.assertEquals(Cell.CELL_TYPE_STRING, cell00.getCellType());
//		Assert.assertEquals(Cell.CELL_TYPE_STRING, cell10.getCellType());
		// types
		assertEquals(CellType.STRING, cell00.getCellType());
		assertEquals(CellType.STRING, cell10.getCellType());

		// texts
		assertEquals("Not Real", cell00.getStringCellValue());
		assertEquals("", cell10.getStringCellValue());

	}

	@Test
	public void saveTest_IgnoringErrors_File() throws InvalidFormatException, IOException {
		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("primInt", "Primitive Int");
		headerMap.put("fake", "Not Real");

		ITRecord record = new ITRecord();
		record.setPrimInt(123);

		Collection<ITRecord> records = asList(record);
		File outputFile = createFile("saveTest_IgnoringErrors_File");

		// save it
		Ssio.save(headerMap, records, outputFile);



		// then parse it
		byte[] spreadsheet = FileUtils.readFileToByteArray(outputFile);
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions ***/
		Sheet sheet = workbook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);
		Row dataRow = sheet.getRow(1);

		Cell cell00 = headerRow.getCell(0);
		Cell cell01 = headerRow.getCell(1);
		Cell cell10 = dataRow.getCell(0);
		Cell cell11 = dataRow.getCell(1);


		// texts
		assertEquals("Primitive Int", cell00.getStringCellValue());
		assertEquals("Not Real", cell01.getStringCellValue());
		assertEquals("123", cell10.getStringCellValue());
		assertEquals("", cell11.getStringCellValue());

	}


	@Test
	public void saveMapsTest_IgnoringErrors_File() throws InvalidFormatException, IOException {
		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("primInt", "Primitive Int");
		headerMap.put("fake", "Not Real");

		Map<String, Object> record = new LinkedHashMap<>();
		record.put("primInt", 123);
		record.put("fake", "someValueAnyway");

		Collection<Map<String, Object>> records = asList(record);
		File outputFile = createFile("saveMapsTest_IgnoringErrors_File");

		// save it
		Ssio.saveMaps(headerMap, records, outputFile);


		// then parse it
		byte[] spreadsheet = FileUtils.readFileToByteArray(outputFile);
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions ***/
		Sheet sheet = workbook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);
		Row dataRow = sheet.getRow(1);

		Cell cell00 = headerRow.getCell(0);
		Cell cell01 = headerRow.getCell(1);
		Cell cell10 = dataRow.getCell(0);
		Cell cell11 = dataRow.getCell(1);


		// texts
		assertEquals("Primitive Int", cell00.getStringCellValue());
		assertEquals("Not Real", cell01.getStringCellValue());
		assertEquals("123", cell10.getStringCellValue());
		assertEquals("someValueAnyway", cell11.getStringCellValue());

	}


	@Test
	public void saveTest_UsingGeneratedHeader_File() throws InvalidFormatException, IOException, ParseException {
		HeaderUtilsTestRecord record = new HeaderUtilsTestRecord();
		record.setPrimIntProp(1);
		record.setIntObjProp(100);
		record.setStrProp("some string");
		record.setDateProp("2000-01-01 00:00:00");
		record.setWriteOnlyProp("would not be saved");



		Collection<HeaderUtilsTestRecord> records = asList(record);
		File outputFile = createFile("saveTest_UsingGeneratedHeader_File");
		// save it
		Ssio.save(HeaderUtilsTestRecord.class, records, outputFile);


		// then parse it
		byte[] spreadsheet = FileUtils.readFileToByteArray(outputFile);
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions ***/
		Sheet sheet = workbook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);
		Row dataRow = sheet.getRow(1);

		List<String> headerCells = getAllCells(headerRow).stream().map(c -> c.getStringCellValue()).collect(Collectors.toList());
		List<Object> dataCells = getAllCells(dataRow).stream().map(c -> getStringOrDateValue(c)).collect(Collectors.toList());
		Map<String, Object> keyValueMap = new LinkedHashMap<>();
		for (int i = 0; i < headerCells.size(); i++) {
			keyValueMap.put(headerCells.get(i), dataCells.get(i));
		}

		assertEquals(6, keyValueMap.size());
		assertEquals("1", keyValueMap.get("Prim Int Prop"));
		assertEquals("100", keyValueMap.get("Int Obj Prop"));
		assertEquals("some string", keyValueMap.get("Str Prop"));
		assertTrue(keyValueMap.containsKey("Date Prop"));
		assertEquals("2000-01-01 00:00:00", keyValueMap.get("Date Prop Str"));
		Assert.assertNull(keyValueMap.get("Read Only Prop"));

	}

	@Test
	public void saveMapsTest_UsingGeneratedHeader_File() throws InvalidFormatException, IOException, ParseException {
		Map<String, Object> record = new LinkedHashMap<>();
		record.put("primIntProp", 1);
		record.put("intObjProp", 100);
		record.put("strProp", "some string");
		record.put("dateProp", "2000-01-01 00:00:00");


		Collection<Map<String, Object>> records = asList(record);
		File outputFile = createFile("saveMapsTest_UsingGeneratedHeader_File");
		// save it
		Ssio.saveMaps(asList("primIntProp", "intObjProp", "strProp", "dateProp"), records, outputFile);


		// then parse it
		byte[] spreadsheet = FileUtils.readFileToByteArray(outputFile);
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions ***/
		Sheet sheet = workbook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);
		Row dataRow = sheet.getRow(1);

		List<String> headerCells = getAllCells(headerRow).stream().map(c -> c.getStringCellValue()).collect(Collectors.toList());
		List<Object> dataCells = getAllCells(dataRow).stream().map(c -> getStringOrDateValue(c)).collect(Collectors.toList());
		Map<String, Object> keyValueMap = new LinkedHashMap<>();
		for (int i = 0; i < headerCells.size(); i++) {
			keyValueMap.put(headerCells.get(i), dataCells.get(i));
		}

		assertEquals(4, keyValueMap.size());
		assertEquals("1", keyValueMap.get("Prim Int Prop"));
		assertEquals("100", keyValueMap.get("Int Obj Prop"));
		assertEquals("some string", keyValueMap.get("Str Prop"));
		assertEquals("2000-01-01 00:00:00", keyValueMap.get("Date Prop"));


	}


	private Object getStringOrDateValue(Cell cell) {
		if (cell == null) {
			return null;
		}
//
//		if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
//			return null;
//		}
//
//		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//			return String.valueOf(cell.getBooleanCellValue());
//		}
//
//		if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
//			return null;
//		}
//
//		if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
//			return null;
//		}
//
//		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//			if (DateUtil.isCellDateFormatted(cell)) {
//				return cell.getDateCellValue();
//			} else {
//				double v = cell.getNumericCellValue();
//				return String.valueOf(v);
//			}
//		}
//
//		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//			String s = cell.getStringCellValue();
//			return StringUtils.trimToNull(s);
//		}
		if (cell.getCellType() == CellType.BLANK) {
			return null;
		}

		if (cell.getCellType() == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		}

		if (cell.getCellType() == CellType.ERROR) {
			return null;
		}

		if (cell.getCellType() == CellType.FORMULA) {
			return null;
		}

		if (cell.getCellType() == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				double v = cell.getNumericCellValue();
				return String.valueOf(v);
			}
		}

		if (cell.getCellType() == CellType.STRING) {
			String s = cell.getStringCellValue();
			return StringUtils.trimToNull(s);
		}
		return null;
	}

	private List<Cell> getAllCells(Row row) {
		List<Cell> cells = new ArrayList<>();
		Iterator<Cell> it = row.cellIterator();
		while (it.hasNext()) {
			Cell cell = it.next();
			cells.add(cell);
		}
		return cells;
	}


	@Test
	public void saveTest_HeadersOnly() throws InvalidFormatException, IOException {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// save it
		Ssio.save(ITRecord.getHeaderMap(), null, outputStream);
		byte[] spreadsheet = outputStream.toByteArray();

		// do a save for human eye check
		FileUtils.writeByteArrayToFile(createFile("saveTest_HeadersOnly"), spreadsheet);

		// then parse it
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions ***/
		Sheet sheet = workbook.getSheetAt(0);
		Row headerRow = sheet.getRow(0);

		// size
		assertEquals(0, sheet.getLastRowNum());
		assertEquals(ITRecord.getHeaderMap().size(), headerRow.getLastCellNum());

	}

	@Test
	public void saveTest_BigNumber() throws InvalidFormatException, IOException {
		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		headerMap.put("bigInteger", "Big Int");
		headerMap.put("bigDecimal", "Big Decimal");

		String bigIntegerStr = "" + Long.MAX_VALUE;
		String bigDecimalStr = Long.MAX_VALUE + "." + Long.MAX_VALUE;
		ITRecord record = new ITRecord();
		record.setBigInteger(new BigInteger(bigIntegerStr));
		record.setBigDecimal(new BigDecimal(bigDecimalStr));

		Collection<ITRecord> records = asList(record);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		List<DatumError> datumErrors = new ArrayList<DatumError>();

		// save it
		Ssio.save(headerMap, records, outputStream, null, datumErrors);
		byte[] spreadsheet = outputStream.toByteArray();

		// do a save for human eye check
		FileUtils.writeByteArrayToFile(createFile("saveTest_BigNumber"), spreadsheet);

		// then parse it
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(spreadsheet));

		/*** do assertions ***/
		Sheet sheet = workbook.getSheetAt(0);
		Row dataRow = sheet.getRow(1);

		Cell cell10 = dataRow.getCell(0);
		Cell cell11 = dataRow.getCell(1);

		// texts
		assertEquals(bigIntegerStr, cell10.getStringCellValue());
		assertEquals(bigDecimalStr, cell11.getStringCellValue());

		// errors
		assertEquals(0, datumErrors.size());

	}

	@Test(expected = InvalidHeaderRowException.class)
	public void parseTest_InvalidHeader() throws InvalidFormatException, InvalidHeaderRowException {
		ByteArrayInputStream in = toByteArrayInputStreamAndClose(this.getClass().getResourceAsStream("/testFiles/parse-test-all-headers-wrong.xlsx"));
		Ssio.parse(ITRecord.getReverseHeaderMap(), in, null, ITRecord.class);
	}

	@Test
	public void parseTest_Excel97() throws InvalidFormatException, InvalidHeaderRowException {
		ByteArrayInputStream in = toByteArrayInputStreamAndClose(this.getClass().getResourceAsStream("/testFiles/parse-test-excel97.xls"));
		List<ITRecord> list = Ssio.parse(ITRecord.getReverseHeaderMap(), in, null, ITRecord.class);
		assertEquals((short) 1, list.get(0).getPrimShort());
	}

	@Test
	public void parseTest_DataHalfCorrect() throws InvalidFormatException, InvalidHeaderRowException {
		ByteArrayInputStream in = toByteArrayInputStreamAndClose(this.getClass().getResourceAsStream("/testFiles/parse-test-data-half-correct.xlsx"));
		List<CellError> cellErrors = new ArrayList<CellError>();
		List<ITRecord> records = Ssio.parse(ITRecord.getReverseHeaderMap(), in, cellErrors, ITRecord.class);

		ITRecord record = records.get(0);
		assertEquals(1, records.size());
		assertEquals(123, record.getPrimInt());
		Assert.assertNull(record.getStr());

		CellError error = cellErrors.get(0);
		assertEquals(1, cellErrors.size());
		assertEquals(2, error.getRowIndexOneBased());
		assertEquals(3, error.getColumnIndexOneBased());
		assertTrue(error.getCause().getMessage().contains("suitable setter"));
		assertTrue(error.getCause().getMessage().contains("abc"));
	}

	@Test
	public void parseTest_File() throws InvalidFormatException, InvalidHeaderRowException {
		ByteArrayInputStream in = toByteArrayInputStreamAndClose(this.getClass().getResourceAsStream("/testFiles/parse-test-data-half-correct.xlsx"));
		File inputFile = createFile("parseTest_File");
		copyInputToFileAndClose(in, inputFile);
		List<CellError> cellErrors = new ArrayList<CellError>();
		List<ITRecord> records = Ssio.parse(ITRecord.getReverseHeaderMap(), inputFile, cellErrors, ITRecord.class);

		assertEquals(1, records.size());
		assertEquals(1, cellErrors.size());
	}

	@Test
	public void parseTest_IgnoringErrors() throws InvalidFormatException, InvalidHeaderRowException {
		ByteArrayInputStream in = toByteArrayInputStreamAndClose(this.getClass().getResourceAsStream("/testFiles/parse-test-data-half-correct.xlsx"));
		List<ITRecord> records = Ssio.parseIgnoringErrors(ITRecord.getReverseHeaderMap(), in, ITRecord.class);

		ITRecord record = records.get(0);
		assertEquals(1, records.size());
		assertEquals(123, record.getPrimInt());
	}

	@Test
	public void parseTest_UsingGeneratedReverseHeader_FileAsInput() throws InvalidFormatException, InvalidHeaderRowException, ParseException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ByteArrayInputStream in = toByteArrayInputStreamAndClose(this.getClass().getResourceAsStream("/testFiles/parse-test-for-generated-reverse-header.xlsx"));
		File inputFile = createFile("parseTest_UsingGeneratedReverseHeader_FileAsInput");
		copyInputToFileAndClose(in, inputFile);

		List<HeaderUtilsTestRecord> records = Ssio.parseIgnoringErrors(inputFile, HeaderUtilsTestRecord.class);

		HeaderUtilsTestRecord record = records.get(0);
		assertEquals(1, records.size());

		assertEquals(1, record.getPrimIntProp());
		assertEquals(Integer.valueOf(100), record.getIntObjProp());
		assertEquals(DateUtils.parseDate("2000-01-01 00:00:00", new String[] {"yyyy-MM-dd HH:mm:ss"}), record.getDateProp());
		Assert.assertNull(record.getReadOnlyProp());

		Field writedOnlyPropField = record.getClass().getDeclaredField("writeOnlyProp");
		writedOnlyPropField.setAccessible(true);

		assertEquals("write-only string", writedOnlyPropField.get(record));

	}

	@Test
	public void parseTest_IgnoringErrors_File() throws InvalidFormatException, InvalidHeaderRowException {
		ByteArrayInputStream in = toByteArrayInputStreamAndClose(this.getClass().getResourceAsStream("/testFiles/parse-test-data-half-correct.xlsx"));
		File inputFile = createFile("parseTest_IgnoringErrors_File_As_Input");
		copyInputToFileAndClose(in, inputFile);

		List<ITRecord> records = Ssio.parseIgnoringErrors(ITRecord.getReverseHeaderMap(), inputFile, ITRecord.class);

		ITRecord record = records.get(0);
		assertEquals(1, records.size());
		assertEquals(123, record.getPrimInt());
	}


	@Test
	public void parseToMapsTest_IgnoringErrors_File() throws InvalidFormatException, InvalidHeaderRowException {
		ByteArrayInputStream in = toByteArrayInputStreamAndClose(this.getClass().getResourceAsStream("/testFiles/parse-test-data-half-correct.xlsx"));
		File inputFile = createFile("parseTest_IgnoringErrors_File_As_Input");
		copyInputToFileAndClose(in, inputFile);

		List<Map<String, String>> records = Ssio.parseToMapsIgnoringErrors(ITRecord.getReverseHeaderMap(), inputFile);

		Map<String, String> record = records.get(0);
		assertEquals(1, records.size());
		assertEquals("123", record.get("primInt"));  // note it's String
		assertEquals("abc", record.get("primLong"));  //For map, it's not a data type error
		assertEquals("2010-11-12T13:14:15.000", record.get("date"));
	}

	@Test
	public void parseTest_AllStringCells() throws InvalidFormatException, InvalidHeaderRowException {
		ByteArrayInputStream in = toByteArrayInputStreamAndClose(this.getClass().getResourceAsStream("/testFiles/parse-test-all-string-cells-input.xlsx"));
		List<CellError> cellErrors = new ArrayList<CellError>();
		List<ITRecord> records = Ssio.parse(ITRecord.getReverseHeaderMap(), in, cellErrors, ITRecord.class);

		// assertions
		ITRecord record = records.get(0);
		assertEquals(1, records.size());
		assertEquals(0, cellErrors.size());

		assertEquals(12, record.getPrimShort());
		assertEquals(2323, record.getPrimInt());
		assertEquals(1213l, record.getPrimLong());
		assertEquals(342.34f, record.getPrimFloat());
		assertEquals(0.34, record.getPrimDouble());
		assertEquals(true, record.isPrimBoolean());

		assertEquals(new Short("23"), record.getObjShort());
		assertEquals(new Integer(234), record.getObjInt());
		assertEquals(new Long(982), record.getObjLong());
		assertEquals(new Float(483.323f), record.getObjFloat());
		assertEquals(new Double(23903.234), record.getObjDouble());
		assertEquals(new Boolean(false), record.getObjBoolean());

		assertEquals(new BigInteger("123456789123456789"), record.getBigInteger());
		assertEquals(new BigDecimal("123456789.123456789"), record.getBigDecimal());
		assertEquals("abc", record.getStr());
		assertEquals("2014-11-29 16:18:47", record.getDateStr());

	}


	@Test
	public void parseTest_FreeTypeCells() throws InvalidFormatException, InvalidHeaderRowException {
		ByteArrayInputStream in = toByteArrayInputStreamAndClose(this.getClass().getResourceAsStream("/testFiles/parse-test-all-free-type-input.xlsx"));
		List<CellError> cellErrors = new ArrayList<CellError>();
		List<ITRecord> records = Ssio.parse(ITRecord.getReverseHeaderMap(), in, cellErrors, ITRecord.class);

		// assertions
		ITRecord record = records.get(0);
		assertEquals(1, records.size());
		assertEquals(0, cellErrors.size());

		assertEquals(12, record.getPrimShort());
		assertEquals(2323, record.getPrimInt());
		assertEquals(1213l, record.getPrimLong());
		// //floats and doubles won't be accurate
		assertEquals(342, (int) record.getPrimFloat());
		assertEquals(0, (int) record.getPrimDouble());
		assertEquals(true, record.isPrimBoolean());

		assertEquals(new Short("23"), record.getObjShort());
		assertEquals(new Integer(234), record.getObjInt());
		assertEquals(new Long(982), record.getObjLong());
		// //floats and doubles won't be accurate
		assertEquals(483, record.getObjFloat().intValue());
		assertEquals(23903, record.getObjDouble().intValue());
		assertEquals(new Boolean(false), record.getObjBoolean());

		assertEquals(new BigInteger("123456789123456000"), record.getBigInteger());
		assertEquals(123456789, record.getBigDecimal().intValue());
		assertEquals("abc", record.getStr());
		assertEquals("2014-11-29 16:18:47", record.getDateStr());

	}

	// read an outside input stream as bytes and then close it, so as to avoid
	// try/finally snippet code in every parsing test
	// method
	private ByteArrayInputStream toByteArrayInputStreamAndClose(InputStream in) {
		try {
			byte[] bytes = IOUtils.toByteArray(in);
			return new ByteArrayInputStream(bytes);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			IOUtils.closeQuietly(in);
		}

	}

	// copy an input stream to File and then close it, so as to avoid
	// try/finally snippet code in every parsing test
	// method
	private void copyInputToFileAndClose(InputStream in, File file) {
		FileOutputStream fileOutput = null;
		try {
			fileOutput = new FileOutputStream(file);
			IOUtils.copy(in, fileOutput);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(fileOutput);
		}

	}

	private File createFile(String prefix) {
		File dir = new File(System.getProperty("java.io.tmpdir"), "/org.sep4j-it-test");
		dir.mkdirs();
		String filename = prefix + "-" + System.nanoTime() + ".xlsx";
		File file = new File(dir, filename);
		System.out.println("File created: " + file.getAbsolutePath());
		return file;
	}



	@SuppressWarnings("unused")
	private static class ITRecord {
		private static LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		static {
			headerMap.put("primShort", "Primitive Short");
			headerMap.put("primInt", "Primitive Int");
			headerMap.put("primLong", "Primitive Long");
			headerMap.put("primFloat", "Primitive Float");
			headerMap.put("primDouble", "Primitive Double");
			headerMap.put("primBoolean", "Primitive Boolean");

			headerMap.put("objShort", "Object Short");
			headerMap.put("objInt", "Object Int");
			headerMap.put("objLong", "Object Long");
			headerMap.put("objFloat", "Object Float");
			headerMap.put("objDouble", "Object Double");
			headerMap.put("objBoolean", "Object Boolean");

			headerMap.put("bigInteger", "Big Integer");
			headerMap.put("bigDecimal", "Big Decimal");
			headerMap.put("str", "String");
			headerMap.put("dateStr", "Date String");
		}
		private short primShort;
		private int primInt;
		private long primLong;
		private float primFloat;
		private double primDouble;
		private boolean primBoolean;

		private Short objShort;
		private Integer objInt;
		private Long objLong;
		private Float objFloat;
		private Double objDouble;
		private Boolean objBoolean;

		private BigInteger bigInteger;
		private BigDecimal bigDecimal;

		private String str;
		private Date date;

		public static LinkedHashMap<String, String> getHeaderMap() {
			return new LinkedHashMap<String, String>(headerMap);
		}

		public static Map<String, String> getReverseHeaderMap() {
			LinkedHashMap<String, String> map = reverse(headerMap);
			map.remove("Date String");
			map.put("Date", "date");
			return map;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getDateStr() {
			if (date == null) {
				return null;
			}
			return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
		}

		public void setDate(String s) {
			if (s == null) {
				return;
			}
			try {
				Date d = DateUtils.parseDate(s, new String[] { "yyyy-MM-dd HH:mm:ss" });
				this.setDate(d);
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}

		}

		public short getPrimShort() {
			return primShort;
		}

		public void setPrimShort(short primShort) {
			this.primShort = primShort;
		}

		public int getPrimInt() {
			return primInt;
		}

		public void setPrimInt(int primInt) {
			this.primInt = primInt;
		}

		public long getPrimLong() {
			return primLong;
		}

		public void setPrimLong(long primLong) {
			this.primLong = primLong;
		}

		public float getPrimFloat() {
			return primFloat;
		}

		public void setPrimFloat(float primFloat) {
			this.primFloat = primFloat;
		}

		public double getPrimDouble() {
			return primDouble;
		}

		public void setPrimDouble(double primDouble) {
			this.primDouble = primDouble;
		}

		public boolean isPrimBoolean() {
			return primBoolean;
		}

		public void setPrimBoolean(boolean primBoolean) {
			this.primBoolean = primBoolean;
		}

		public Short getObjShort() {
			return objShort;
		}

		public void setObjShort(Short objShort) {
			this.objShort = objShort;
		}

		public Integer getObjInt() {
			return objInt;
		}

		public void setObjInt(Integer objInt) {
			this.objInt = objInt;
		}

		public Long getObjLong() {
			return objLong;
		}

		public void setObjLong(Long objLong) {
			this.objLong = objLong;
		}

		public Float getObjFloat() {
			return objFloat;
		}

		public void setObjFloat(Float objFloat) {
			this.objFloat = objFloat;
		}

		public Double getObjDouble() {
			return objDouble;
		}

		public void setObjDouble(Double objDouble) {
			this.objDouble = objDouble;
		}

		public Boolean getObjBoolean() {
			return objBoolean;
		}

		public void setObjBoolean(Boolean objBoolean) {
			this.objBoolean = objBoolean;
		}

		public BigDecimal getBigDecimal() {
			return bigDecimal;
		}

		public void setBigDecimal(BigDecimal bigDecimal) {
			this.bigDecimal = bigDecimal;
		}

		public BigInteger getBigInteger() {
			return bigInteger;
		}

		public void setBigInteger(BigInteger bigInteger) {
			this.bigInteger = bigInteger;
		}

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}

	}

	private static <K, V> LinkedHashMap<V, K> reverse(Map<K, V> origMap) {
		LinkedHashMap<V, K> newMap = new LinkedHashMap<V, K>();
		if (origMap == null) {
			origMap = new HashMap<K, V>();
		}
		for (Map.Entry<K, V> entry : origMap.entrySet())
			newMap.put(entry.getValue(), entry.getKey());
		return newMap;
	}

}
