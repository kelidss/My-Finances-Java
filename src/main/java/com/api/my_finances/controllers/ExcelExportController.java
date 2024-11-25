package com.api.my_finances.controllers;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.my_finances.models.Transaction;
import com.api.my_finances.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class ExcelExportController {

    @Autowired
    private TransactionService transactionService;  

    @GetMapping("/exportar-excel")
    public ResponseEntity<byte[]> exportarExcel() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transações");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Descrição");
        headerRow.createCell(1).setCellValue("Valor");
        headerRow.createCell(2).setCellValue("Categoria");
        headerRow.createCell(3).setCellValue("Data");

        List<Transaction> transactions = transactionService.listAll();  

        int rowNum = 1;
        for (Transaction transaction : transactions) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(transaction.getDescription());
            row.createCell(1).setCellValue(transaction.getValue());
            row.createCell(2).setCellValue(transaction.getCategory().getName());  
            row.createCell(3).setCellValue(transaction.getCreatedAt().toString());
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        byte[] excelFile = byteArrayOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_financeiro.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelFile);
    }
}
