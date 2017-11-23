package com.shinaier.laundry.snlfactory.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 张家洛 on 2017/7/29.
 */

public class TimeUtils {
    /**
     * 时间戳转换成yyyy.MM.dd HH:mm:ss格式的时间
     * @param time
     * @return
     */
    public static String formatTime(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }
}
