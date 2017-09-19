package cn.yj.market.frame.util;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import cn.yj.market.frame.constants.DateTimeFormatStringConstants;
import cn.yj.market.frame.vo.MarketGoodsUnitPrice;

public final class CoreUtils {
	private CoreUtils(){}

    public static boolean isDate(String val) {

        Date dval = parseDate(val);

        if (dval == null)
            return false;

        return true;

    }

    public static Date parseDate(String str) {

        if (StringUtils.isBlank(str))

            return null;

        try {

            return DateUtils.parseDate(str,
                new String[] {DateTimeFormatStringConstants.YYYY_MM_DD});

        } catch (ParseException e) {

            return null;
        }

    }
    
    public static Date parseDateTime(String str) {
    	if (StringUtils.isBlank(str)){
    		return null;
    	}
    	try {
    		return DateUtils.parseDate(str,
    				new String[] {DateTimeFormatStringConstants.YYYY_MM_DD_HH_MI_SS});
    	} catch (ParseException e) {
    		return null;
    	}
    }

    /**
     * 比较日期d1在d2之前，则返回true
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static boolean dateBefor(Date d1, Date d2) {
        if (d1 == null) {
            return false;
        }
        if (d2 == null) {
            return false;
        }
        return d1.before(d2);
    }

    public static long ton2kg(long ton) {
        if (ton <= 0) {
            return 0;
        }
        return ton * 1000l;
    }

    /**
     * 获取BigDecimal的String
     * 
     * @param val
     * @return
     */
    public static String getString(BigDecimal val) {
        return (val == null ? "" : val.toPlainString());
    }

    /**
     * 返回Date字符串，格式：yyyy-MM-dd
     * 
     * @param time
     * @return
     */
    public static String dateFormat(Date time) {
        return timeFormat(time, DateTimeFormatStringConstants.YYYY_MM_DD);
    }

    /**
     * 返回DateTime字符串，格式：yyyy-MM-dd HH:mm:ss
     * 
     * @param time
     * @return
     */
    public static String timeFormat(Date time) {
        return timeFormat(time,
            DateTimeFormatStringConstants.YYYY_MM_DD_HH_MI_SS);
    }

    /**
     * 按给定格式，返回DateTime字符串，如果不指定格式，默认返回yyyy-MM-dd HH:mm:ss格式
     * 
     * @param time
     * @param formatStr
     *            如果参数为空，使用默认格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String timeFormat(Date time, String formatStr) {
        if (time == null) {
            return "";
        }
        if (StringUtils.isBlank(formatStr)) {
            formatStr = DateTimeFormatStringConstants.YYYY_MM_DD_HH_MI_SS;
        }
        return DateFormatUtils.format(time, formatStr);
    }

    /**
     * 验证给定字符串是否是Integer
     * 
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证给定字符串是否是Long
     * 
     * @param str
     * @return
     */
    public static boolean isLong(String str) {
        try {
            Long.parseLong(str);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Long parseLong(String val) {
        if (isLong(val)) {
            return Long.parseLong(val);
        }
        return null;
    }
    
    /**
     * 验证给定字符串是否是Long
     * 
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
    	try {
    		Double.parseDouble(str);
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
    }
    
    public static Double parseDouble(String val) {
    	if (isDouble(val)) {
    		return Double.parseDouble(val);
    	}
    	return null;
    }
    
    public static String toDigitalString(BigDecimal val) {
        if (val == null)
            return "0.00";

        NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        ((DecimalFormat) format).applyPattern("############.#########");
        return format.format(val);
    }

    public static String getExceptionContext(Exception ex) {

        StringBuffer context = new StringBuffer();
        StackTraceElement[] sts = ex.getStackTrace();

        context.append(ex.getMessage() + "\r\n");
        for (StackTraceElement ste : sts) {
            context.append(ste.getClassName() + " " + ste.getMethodName() + " "
                + ste.getLineNumber() + "\r\n");
        }
        Throwable cause = ex.getCause();
        if (cause != null) {
            sts = cause.getStackTrace();

            context.append(cause.getMessage() + "\r\n");
            for (StackTraceElement ste : sts) {
                context.append(ste.getClassName() + " " + ste.getMethodName()
                    + " " + ste.getLineNumber() + "\r\n");
            }
        }
        return context.toString();

    }

    /**
     * MD5加密
     * 
     * @param s
     * @return
     */
    public final static String generateMD5(String s) {
        char hexDigits[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
                'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回给定日期指定天数之前的日期
     * 
     * @param dt
     * @param beforeDays
     * @return
     */
    public static Date beforeDate(Date dt, int beforeDays) {
        if (dt == null) {
            return null;
        }
        if (beforeDays < 1) {
            return dt;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 19);
        return c.getTime();
    }

    /**
     * 返回给定日期指定天数之后的日期
     * 
     * @param dt
     * @param beforeDays
     * @return
     */
    public static Date afterDate(Date date, int day) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, day);

        return calendar.getTime();

    }

    /**
     * 获取客户端请求IP
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 获取本地IP
     * @return
     */
    public static String getIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress(); //获得本机IP 
		} catch (Exception e) {
			return "Unknow";
		}
	}

    public static String formatString(Object val) {
        if (val == null)
            return "";

        if (val instanceof Date)
            return formatDate((Date) val);

        return StringUtils.stripToEmpty(val.toString());

    }

    public static String formatDate(Date val) {

        if (val == null)
            return "";

        return DateFormatUtils.ISO_DATE_FORMAT.format(val);

    }

    // 日期差
    public static Long daysBetween(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        setTimeToMidnight(calendar1);
        setTimeToMidnight(calendar2);
        long calendar1ms = calendar1.getTimeInMillis();
        long calendar2ms = calendar2.getTimeInMillis();
        long intervalMs = calendar2ms - calendar1ms;
        return Long.valueOf(millisecondsToDays(intervalMs));
    }

    // 毫秒换算成天数
    private static int millisecondsToDays(long intervalMs) {
        return (int) (intervalMs / (1000 * 86400));
    }

    // 两个日期毫秒差
    private static void setTimeToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    /**
     * 生成会员编号
     * @param seq
     * @return
     */
	public static String generateMemberNo(Long seq) {
		if (seq == null || seq.longValue() < 1) {
			return null ;
		}
		String baseString = "00000000000000" ;
		LocalDate date = LocalDate.now() ;
		StringBuilder noBuilder = new StringBuilder(baseString);
		noBuilder.append(seq) ;
		String seqString = noBuilder.substring(noBuilder.length() - 14) ;
		noBuilder.replace(0, noBuilder.length(), "").append(date.getYear());
		if (date.getMonthValue() < 10) {
			noBuilder.append("0");
		}
		noBuilder.append(date.getMonthValue());
		noBuilder.append(seqString);
		return noBuilder.toString();
	}
	
	/**
	 * 生成会员编号
	 * @param seq
	 * @return
	 */
	public static String generateOrderNo(Long seq) {
		if (seq == null) {
			return null ;
		}
		String baseString = "00000000000000" ;
		LocalDateTime date = LocalDateTime.now() ;
		StringBuilder noBuilder = new StringBuilder(baseString);
		noBuilder.append(seq) ;
		String seqString = noBuilder.substring(noBuilder.length() - 8) ;
		noBuilder.replace(0, noBuilder.length(), "").append(date.getYear());
		if (date.getMonthValue() < 10) {
			noBuilder.append("0");
		}
		noBuilder.append(date.getMonthValue());
		if (date.getDayOfMonth() < 10) {
			noBuilder.append("0") ;
		}
		noBuilder.append(date.getDayOfMonth()) ;
		if (date.getHour() < 10) {
			noBuilder.append("0") ;
		}
		noBuilder.append(date.getHour()) ;
		if (date.getMinute() < 10) {
			noBuilder.append("0") ;
		}
		noBuilder.append(date.getMinute()) ;
		noBuilder.append(seqString);
		return noBuilder.toString();
	}
	
	public static String formatMoney(BigDecimal ct) {
		if(ct == null){
			return "0.00" ;
		}
		if (ct.compareTo(BigDecimal.ZERO) == 0) {
			return "0.00" ;
		}
		DecimalFormat df = new DecimalFormat("#.00");  
		return df.format(ct.doubleValue()) ;
	}
	
	public static void main(String[] args) {
		System.out.println(generateMemberNo(666666666L));
	}

	public static long getGoodsMinUnitCount(
			List<MarketGoodsUnitPrice> goodsUnitPrices, Long count,
			String punit) {
		if (goodsUnitPrices == null || goodsUnitPrices.isEmpty()) {
			return 0 ;
		}
		if (count == null) {
			return 0 ;
		}
		if (StringUtils.isBlank(punit)) {
			return 0 ;
		}
		Collections.sort(goodsUnitPrices, new Comparator<MarketGoodsUnitPrice>() {
			@Override
			public int compare(MarketGoodsUnitPrice o1,
					MarketGoodsUnitPrice o2) {
				if (o1 == null) {
					return 1 ;
				}
				if (o2 == null) {
					return -1 ;
				}
				return o1.getGoodsUnitRate().compareTo(o2.getGoodsUnitRate());
			}
		});
		boolean find = false ;
		for (MarketGoodsUnitPrice up : goodsUnitPrices) {
			int ff = up.getGoodsUnitRate() ;
			if (up.getGoodsUnitName().equals(punit)) {
				find = true ;
				ff = 1 ;
			}
			if (find) {
				count = count * ff ;
			}
		}
		return count;
	}
}
