package com.api.my_finances.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.my_finances.models.Transaction;
import com.api.my_finances.repositorys.TransactionRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void exportTransactionsToExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transações");

        Row headerRow = sheet.createRow(0);
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("ID");
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("Descrição");
        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("Valor");
        Cell headerCell4 = headerRow.createCell(3);
        headerCell4.setCellValue("Categoria");
        Cell headerCell5 = headerRow.createCell(4);
        headerCell5.setCellValue("Data");

        List<Transaction> transactions = transactionRepository.findAll();

        int rowNum = 1;
        for (Transaction transaction : transactions) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(transaction.getId());
            row.createCell(1).setCellValue(transaction.getDescription());
            row.createCell(2).setCellValue(transaction.getValue());
            row.createCell(3).setCellValue(transaction.getCategory().getName());
            row.createCell(4).setCellValue(transaction.getCreatedAt().toString());
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream("transacoes.xlsx")) {
            workbook.write(fileOut);
        }

        workbook.close();
    }
}
