package io.github.prepayments.app.util;

import java.time.format.DateTimeFormatter;
import java.util.List;

public interface DatedExcelViewMapper<Date, ExcelView, DTO> {

    ExcelView toExcelView(DTO dto, Date dateInstance, DateTimeFormatter dtf);

    DTO toDto(ExcelView excelView, DateTimeFormatter dtf);

    List<ExcelView> toExcelView(List<DTO> dtoList, Date dateInstance, DateTimeFormatter dtf);

    List<DTO> toDto(List<ExcelView> excelViews, DateTimeFormatter dtf);
}
