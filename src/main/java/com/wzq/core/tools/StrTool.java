package com.wzq.core.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2015-12-04 12:34:54
 * Description: 字符串操作
 */

public class StrTool {

    /**
     * 替换字符串
     */
    public static String replaceStr(String str, String oldStr, String newStr) {
        if (str == null || newStr == null || oldStr == null || oldStr.trim().length() == 0)
            return str;
        int iE = str.indexOf(oldStr);
        int iB = 0;
        String temp = "";
        while (iE != -1) {
            temp += str.substring(iB, iE) + newStr;
            iB = iE + oldStr.length();
            iE = str.indexOf(oldStr, iB);
        }
        if (iB < str.length())
            temp += str.substring(iB);
        return temp;
    }


    /**
     * 字串到整型数的转换。
     */
    public static int str2int(String str) {
        if (str == null)
            return 0;
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }



    /**字符串转为数组*/
    public static String[] str2spc(String strIn, String strSpc) {
        if (strIn == null)
            strIn = "";
        else
            strIn = strIn.trim();

        //String strTemp = "";
        int i = 0;
        StringTokenizer stIn1 = new StringTokenizer(strIn, strSpc);
        for (i = 0; stIn1.hasMoreTokens(); i++)
            stIn1.nextToken();

        StringTokenizer stIn2 = new StringTokenizer(strIn, strSpc);
        String[] strArr = new String[i];
        for (i = 0; stIn2.hasMoreTokens(); i++) {
            strArr[i] = stIn2.nextToken();
        }

        return strArr;
    }

    /**
     * 将数组的每个元素转换成String 对于null转换为"" 后用joinStr把每个元素串起来
     * <br>返回的字符串不会以joinStr结尾
     * @param array
     * @param joinStr
     * @return
     */
    public static String joinArray(Object[] array, String joinStr, boolean trim){
        if(array == null)
            return "";

        StringBuffer ret = new StringBuffer();
        for(int i=0; i<array.length; i++){
            Object a = array[i];
            if(a == null)
                ret.append("");
            else{
                String ts = String.valueOf(a);
                ret.append(trim ? ts.trim() : ts);
            }

            if(i<array.length - 1)
                ret.append(joinStr);
        }

        return ret.toString();
    }

    /**
     * 把一段文本 按照每行的内容 直接转化为一个包含每行文本内容的字符串数组
     * @param text	文本
     * @param trimLine	是否把每行数据都trim一下
     * @return
     */
    public static String[] lines(String text, boolean trimLine){
        if(text.equals(""))
            return new String[0];

        String spl = "(?:" + Pattern.quote("\n") + ")|(?:" + Pattern.quote("\r\n") + ")";
        String[] ret = text.split(spl);

        if(trimLine)
            for(int i=0; i<ret.length; i++)
                ret[i] = ret[i].trim();

        return ret;
    }

    /**
     * 把一段文本 按照每行的内容 直接转化为一个包含每行文本内容的字符串数组
     * <br>并且对每行进行一下trim
     * @param text
     * @return
     */
    public static String[] lines(String text){
        return lines(text, true);
    }

    /**
     * 分隔字符串 不使用java的正则
     * @param source
     * @param div
     * @return
     */
    public static String[] split(String source, String div) {
        return split(source, div, true);
    }

    /**
     * 分隔字符串 不使用java的正则
     * <br>并且 如果trimSubstr为true的话
     * <br>对分割后的子串进行trim
     * @param source
     * @param div
     * @param trimSubstr
     * @return
     */
    public static String[] split(String source, String div,
                                 boolean trimSubstr){
        return split(source, new String[]{div}, trimSubstr);
    }

    /**
     * 分割字符串 不使用java的正则 同时trim分割后的串
     * @param source 源串
     * @param divs 分割符
     * @return
     */
    public static String[] split(String source, String[] divs){
        return split(source, divs, true);
    }

    /**
     * 分割字符串 不使用java的正则
     * <br>并且 如果trimSubstr为true的话
     * <br>对分割后的子串进行trim
     * @param source 源字符串
     * @param divs 多个分割符（如果没有传入分割符 那么返回源串）
     * @param trimSubstr 是否对分割后的进行trim
     * @return
     */
    public static String[] split(String source, String[] divs,
                                 boolean trimSubstr){
        if(divs.length <= 0)
            return new String[]{source};

        String spliter = "(";
        for(int i=0; i<divs.length; i++)
            spliter +=
                    (trimSubstr ? "\\s*" : "") +
                            Pattern.quote(divs[i]) +
                            (trimSubstr ? "\\s*" : "") +
                            "|";
        // 去掉最后一个"|"
        spliter = spliter.substring(0, spliter.length() - 1) + ")";

        return source.split(spliter);
    }

    /**
     * 简单替换 对于substr不使用正则，对于replacer不使用“$”等替换规则
     * @param str 源字符串
     * @param substr 要被替换的字符串
     * @param replacer 替换成的字符串
     * @return
     */
    public static String replace(String str, String substr, String replacer) {
        return str.replaceAll(
                Pattern.quote(substr),
                Matcher.quoteReplacement(replacer)
        );
    }

    /**
     * trim字符串 即使字符串是null
     * @param strIn
     * @return
     */
    public static String trim(String strIn){
        return strIn == null ? "" : strIn.trim();
    }

    /**
     * 替换 对于给定的替换进行逐字比对的替换 不会进行回朔替换 对于开头有交集的替换 按照参数的先后替换
     * <br>例如 对于Pubfun.replace("abcde", true, Arrays.asList(new Object[]{
     * <br>new Object[]{"ab", 1},new Object[]{"a", 2}
     * <br>}));
     * <br>将返回"1cde"
     * <br>注意第三个参数 必需是List中每个元素都是一个有两个元素的任意数组
     */
    public static String replace(String str, boolean caseSensitive, List replacer){
        if(replacer.size() <= 0)
            return str;

        StringBuffer sbRegex = new StringBuffer();			// 匹配的正则
        List groupMtc = new ArrayList(); 	// 每个正则的内容
        for(int i=0, s = replacer.size(); i<s; i++){
            Object f = ((Object[])replacer.get(i))[0];
            String sf = f == null ? "" : f.toString();
            sbRegex.append("(").append(Pattern.quote(sf)).append(")|");

            if(!caseSensitive)
                groupMtc.add(sf.toLowerCase());
            else
                groupMtc.add(sf);
        }

        StringBuffer ret = new StringBuffer();
        Pattern p;
        if(caseSensitive)
            p = Pattern.compile(trimEnd(sbRegex, "|").toString());
        else
            p = Pattern.compile(trimEnd(sbRegex, "|").toString(), Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        while(m.find()){
            String group = m.group();
            if(!caseSensitive)
                group = group.toLowerCase();

            Object s = ((Object[])replacer.get(groupMtc.indexOf(group)))[1];
            String ss = s == null ? "" : s.toString();
            m.appendReplacement(ret, Matcher.quoteReplacement(ss));
        }
        m.appendTail(ret);

        return ret.toString();
    }

    /**
     * 截去末尾的strTrim字符
     * @param strIn
     * @param strTrim
     * @return
     */
    public static String trimEnd(String strIn, String strTrim){
        if(strIn.endsWith(strTrim))
            return strIn.substring(0, strIn.length() - strTrim.length());

        return strIn;
    }

    /**
     * 截去末尾的endLength长度
     * @param strIn
     * @param endLength
     * @return
     */
    public static String trimEnd(String strIn, int endLength){
        if(endLength <= strIn.length() && endLength >= 0)
            return strIn.substring(0, strIn.length() - endLength);

        return strIn;
    }

    /**
     * 截去末尾的strTrim字符
     * @param strIn
     * @param strTrim
     * @return
     */
    public static CharSequence trimEnd(CharSequence strIn, CharSequence strTrim){
        if(strIn.length() >= strTrim.length() && strTrim.length() > 0 &&
                strIn.subSequence(strIn.length() - strTrim.length(), strIn.length()).equals(strTrim))
            return strIn.subSequence(0, strIn.length() - strTrim.length());

        return strIn;
    }

    /**
     * 截去末尾的endLength长度
     * @param strIn
     * @param endLength
     * @return
     */
    public static CharSequence trimEnd(CharSequence strIn, int endLength){
        if(endLength <= strIn.length() && endLength >= 0)
            return strIn.subSequence(0, strIn.length() - endLength);

        return strIn;
    }

    /**
     * 截去末尾的strTrim字符
     * @param strIn
     * @param strTrim
     * @return
     */
    public static StringBuffer trimEnd(StringBuffer strIn, String strTrim){
        int i = strIn.lastIndexOf(strTrim);
        if(i >= 0 && i == strIn.length() - strTrim.length())
            return strIn.delete(strIn.length() - strTrim.length(), strIn.length());

        return strIn;
    }

    /**
     * 截去末尾的endLength长度
     * @param strIn
     * @param endLength
     * @return
     */
    public static StringBuffer trimEnd(StringBuffer strIn, int endLength){
        if(endLength <= strIn.length() && endLength >= 0)
            return strIn.delete(strIn.length() - endLength, strIn.length());

        return strIn;
    }

    /**
     * 将 null 字符串 处理为""
     * @param strIn
     * @return
     */
    public static String trimNull(String strIn){
        if(strIn == null)
            return "";
        return strIn;
    }

    /**
     * 返回一个包含有指定数量个相同字符的字符串
     * @param strIn	需要重复的字符
     * @param count 个数
     * @return
     */
    public static String repeatStr(String strIn, int count){
        StringBuffer sb = new StringBuffer("");
        for(int i=0; i<count; i++)
            sb.append(strIn);

        return sb.toString();
    }
}