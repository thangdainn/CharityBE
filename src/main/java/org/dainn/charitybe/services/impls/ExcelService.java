package org.dainn.charitybe.services.impls;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dainn.charitybe.dtos.DonationDTO;
import org.dainn.charitybe.dtos.StatisticDTO;
import org.dainn.charitybe.dtos.StatisticItemDTO;
import org.dainn.charitybe.services.IExcelService;
import org.dainn.charitybe.utils.DateUtil;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ExcelService implements IExcelService {

    @Override
    public byte[] generateStatisticExcel(StatisticDTO data, Date fromDate, Date toDate) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Campaigns");

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Statistic from " + DateUtil.dateToString(fromDate) + " to " + DateUtil.dateToString(toDate));

        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCell.setCellStyle(titleStyle);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

        Row header = sheet.createRow(1);
        header.createCell(0).setCellValue("Campaign Name");
        header.createCell(1).setCellValue("Owner");
        header.createCell(2).setCellValue("Target Amount");
        header.createCell(3).setCellValue("Current Amount");
        header.createCell(4).setCellValue("Start Date");
        header.createCell(5).setCellValue("End Date");
        header.createCell(6).setCellValue("Status");
        header.createCell(7).setCellValue("Total Donations");

        int rowNum = 2;
        for (StatisticItemDTO item : data.getData()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getCampaigns().getName());
            row.createCell(1).setCellValue(item.getOwner());
            row.createCell(2).setCellValue(item.getCampaigns().getTargetAmount().toString());
            row.createCell(3).setCellValue(item.getCampaigns().getCurrentAmount().toString());
            row.createCell(4).setCellValue(DateUtil.dateToString(item.getCampaigns().getStartDate()));
            row.createCell(5).setCellValue(DateUtil.dateToString(item.getCampaigns().getEndDate()));
            row.createCell(6).setCellValue(item.getCampaigns().getStatus().toString());
            row.createCell(7).setCellValue(item.getTotalDonations());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("Error occurred while generating excel file: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public byte[] generateDonationExcel(List<DonationDTO> data, String campaignName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Donations");

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(campaignName);

        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCell.setCellStyle(titleStyle);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

        Row header = sheet.createRow(1);
        header.createCell(0).setCellValue("Donor");
        header.createCell(1).setCellValue("Amount");
        header.createCell(2).setCellValue("Donate Date");

        int rowNum = 2;
        for (DonationDTO donation : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(donation.getIsAnonymous() ? "Anonymous" : donation.getName());
            row.createCell(1).setCellValue(donation.getAmount().toString());
            row.createCell(2).setCellValue(DateUtil.dateToString(donation.getCreatedDate()));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("Error occurred while generating excel file: {}", e.getMessage());
            return null;
        }
    }

}
