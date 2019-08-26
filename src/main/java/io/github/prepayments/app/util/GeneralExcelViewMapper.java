package io.github.prepayments.app.util;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * A word of caution is that the date state is ignored
 */
public interface GeneralExcelViewMapper<Date, ExcelView, DTO> extends DatedExcelViewMapper<Date, ExcelView, DTO>, ExcelViewMapper<ExcelView, DTO> {

    @Override
    default ExcelView toExcelView(DTO dto, Date dateInstance, DateTimeFormatter dtf) {
        return toExcelView(dto, dtf);
    }

    @Override
    default List<ExcelView> toExcelView(List<DTO> dtos, Date dateInstance, DateTimeFormatter dtf) {
        return toExcelView(dtos, dtf);
    }
}
