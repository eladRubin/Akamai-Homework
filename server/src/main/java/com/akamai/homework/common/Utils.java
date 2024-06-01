package com.akamai.homework.common;

import lombok.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Utils {
    static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String getSimpleDateFormat(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }
}
