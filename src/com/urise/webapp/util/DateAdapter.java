package com.urise.webapp.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;


public class DateAdapter extends XmlAdapter<String, YearMonth> {

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM");

    @Override
    public String marshal(YearMonth dateTime) {
        return dateTime.format(dateFormat);
    }

    @Override
    public YearMonth unmarshal(String dateTime) {
        return YearMonth.parse(dateTime, dateFormat);
    }
}
