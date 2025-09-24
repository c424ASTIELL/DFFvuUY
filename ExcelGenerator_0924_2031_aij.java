// 代码生成时间: 2025-09-24 20:31:36
package com.example.demo;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to generate Excel files.
 */
@Controller("/api/excel")
public class ExcelGenerator {

    private static final String SHEET_NAME = "Sheet1";

    @Get("/create")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public void createExcelFile(OutputStream outputStream, @Nullable String[] data) throws IOException {
        try {
            // Create a workbook
            Workbook workbook = new XSSFWorkbook();

            // Dynamically add data to the sheet
            if (data != null && data.length > 0) {
                // Assuming data is an array of rows, each row is a comma-separated string
                for (String row : data) {
                    Map<String, String> rowData = parseRow(row);
                    addRow(workbook, rowData);
                }
            } else {
                // Add a default empty row if no data is provided
                addRow(workbook, Collections.emptyMap());
            }

            // Write the workbook to the output stream
            workbook.write(outputStream);
            workbook.close();

            // Flush the output stream
            outputStream.flush();
        } catch (Exception e) {
            // Handle exceptions and log errors
            throw new RuntimeException("Error generating Excel file", e);
        }
    }

    /**
     * Parses a comma-separated string into a map of column headers to values.
     * @param row The row data to parse.
     * @return A map representing the row data.
     */
    private Map<String, String> parseRow(String row) {
        String[] columns = row.split(",");
        Map<String, String> rowData = new HashMap<>();

        for (int i = 0; i < columns.length; i++) {
            rowData.put("Column" + (i + 1), columns[i].trim());
        }

        return rowData;
    }

    /**
     * Adds a row to the workbook using the provided map of data.
     * @param workbook The workbook to add the row to.
     * @param rowData The data for the row.
     */
    private void addRow(Workbook workbook, Map<String, String> rowData) {
        // Create a sheet if it doesn't exist
        if (!workbook.getSheet(SHEET_NAME).isPresent()) {
            workbook.createSheet(SHEET_NAME);
        }

        // Get the sheet
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet(SHEET_NAME).orElseThrow();

        // Create a row and cells
        org.apache.poi.ss.usermodel.Row row = sheet.createRow(workbook.getSheet(SHEET_NAME).getPhysicalNumberOfRows());

        int cellIndex = 0;
        for (Map.Entry<String, String> entry : rowData.entrySet()) {
            org.apache.poi.ss.usermodel.Cell cell = row.createCell(cellIndex++);
            cell.setCellValue(entry.getValue());
        }
    }
}
