package framework;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BillNo extends Thread {

	private static long orderNum = 0l;
	private static String date;

	/**
	 * Éú³É¶©µ¥±àºÅ
	 * 
	 */
	public static synchronized String getBillNo() {
		String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());
		if (date == null || !date.equals(str)) {
			date = str;
			orderNum = 0l;
		}
		orderNum++;
		long orderNo = Long.parseLong((date)) * 10000;
		orderNo += orderNum;
		;
		return orderNo + "";
	}

}