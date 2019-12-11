package cn.bjtu.stms.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return 如果字符串是空, 则返回 true; 否则返回 false
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str))
            return true;
        return false;
    }

    /**
     * 判断字符串是否为空(trim)之后
     *
     * @param str
     * @return 如果是空, 则返回 true; 否则返回 false
     */
    public static boolean isTrimEmpty(String str) {
        if (str == null || "".equals(str))
            return true;
        if ("".equals(str.trim()))
            return true;
        return false;
    }

    /**
     * 用正则表达式判断字符串是否为数字（含负数）
     */
    public static boolean isNumeric(String str) {
        String regEx = "^-?[0-9]+$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        return mat.find() ? true : false;
    }

    /**
     * 用正则表达式判断字符串是否为数字（不含负数）
     */
    public static boolean isPositiveNumeric(String str) {
        if (isTrimEmpty(str)){
            return false;
        }
        String regEx = "^[0-9]+$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        return mat.find() ? true : false;
    }

    /**
     * 用正则表达式判断字符串是否为手机号
     */
    public static boolean isTelephone(String str) {
        if (isTrimEmpty(str)){
            return false;
        }
        String regEx = "^1(3|4|5|6|7|8|9)\\d{9}$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        return mat.find() ? true : false;
    }

    /**
     * 用正则表达式判断字符串是否为手机号(允许中文名，如：刘老板llb@qq.com )
     */
    public static boolean isEmail(String str) {
        if (isTrimEmpty(str)){
            return false;
        }
        String regEx = "^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        return mat.matches();
    }

    /**
     * 用正则表达式判断字符串是否为网络链接url
     */
    public static boolean isURL(String str) {
        if (isTrimEmpty(str)){
            return false;
        }
        String regEx = "^((http:\\/\\/)|(https:\\/\\/))?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z09])?\\.)+[a-zA-Z]{2,6}(\\/)";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        return mat.find() ? true : false;
    }


}
