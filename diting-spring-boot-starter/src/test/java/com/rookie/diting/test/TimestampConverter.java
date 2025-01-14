package com.rookie.diting.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Classname TimestampConverter
 * @Description TODO
 * @Date 2025/1/14 14:20
 * @Created by liujiapeng
 */
public class TimestampConverter {

    // Windows NT 时间起点（1601-01-01 00:00:00 UTC）到 Unix 时间起点（1970-01-01 00:00:00 UTC）的毫秒差
    private static final long WINDOWS_NT_EPOCH_OFFSET = 11644473600000L;

    public static void main(String[] args) {
        // 示例 Windows NT 时间（100 纳秒间隔数）
        long windowsNtTime = 134124480000000000L;

        // 转换为 Java 时间（毫秒）
        long javaTime = (windowsNtTime / 10_000) - WINDOWS_NT_EPOCH_OFFSET;

        // 创建 Date 对象
        Date date = new Date(javaTime);

        // 格式化日期为 GMT 时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置为 GMT 时区

        // 输出 GMT 时间
        String gmtTime = sdf.format(date);
        System.out.println("转换后的 GMT 时间: " + gmtTime);
    }
}
