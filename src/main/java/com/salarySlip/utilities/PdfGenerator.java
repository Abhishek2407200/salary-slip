package com.salarySlip.utilities;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.salarySlip.entity.Employee;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Component
public class PdfGenerator {
    public ByteArrayInputStream generateSalarySlip(Employee employee) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(generateTable(employee));
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private Element generateTable(Employee employee) {
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Salary Slip"));
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        table.addCell("Employee Name");
        table.addCell(employee.getEmpName());

        table.addCell("Employee Type");
        table.addCell(employee.getEmpType());

        table.addCell("Basic Salary");
        table.addCell(String.valueOf(employee.getBasicSalary()));

        table.addCell("HRA");
        table.addCell(String.valueOf(employee.getHra()));

        table.addCell("PA");
        table.addCell(String.valueOf(employee.getPa()));

        table.addCell("Bonuses");
        table.addCell(String.valueOf(employee.getBonuses()));

        table.addCell("Allowances");
        table.addCell(String.valueOf(employee.getAllowances()));

        table.addCell("Tax");
        table.addCell(String.valueOf(employee.getTax()));

        table.addCell("PF");
        table.addCell(String.valueOf(employee.getPf()));

        table.addCell("Gross Salary");
        table.addCell(String.valueOf(employee.getGrossSalary()));

        return table;
    }


}
