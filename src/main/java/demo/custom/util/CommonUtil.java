package demo.custom.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CommonUtil {

    public static String getTimeStamp()
    {
		String rtnStr = null;

		// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyyyMMddhhmmssSSS";
		
		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
	    rtnStr = sdfCurrent.format(ts.getTime());

		return rtnStr;
    }
}