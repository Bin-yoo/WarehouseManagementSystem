package me.zhengjie.utlis;

import java.io.UnsupportedEncodingException;

/**
 * @author LiangBin
 * @Description 获取拼音码
 * @date 2021/12/16 17:46
 */
public class PinYinCodeUtil {

    private final static char[] en_char_Upper = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'W', 'X', 'Y', 'Z' };
    private final static int[] posValue = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472,
            3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };
    private final static int[] posValue2 = { 45217, 45253, 45761, 46318, 46826, 47010, 47297, 47614, 48119, 49062,
            49324, 49896, 50371, 50614, 50622, 50906, 51387, 51446, 52218, 52698, 52980, 53689, 54481, 55290 };

    /**
     * 获取字符串的大小写拼音码，true为大写
     *
     * @param strChinese
     * @param bUpCase
     * @return
     */
    public static String getPYIndexStr(String strChinese, boolean bUpCase) {
        try {
            StringBuffer buffer = new StringBuffer();

            byte[] b = strChinese.getBytes("GBK");

            int i = 0;
            while (i < b.length) {
                if ((b[i] & 0xFF) > 128) {
                    int char1 = b[(i++)] & 0xFF;
                    char1 <<= 8;
                    int chart = char1 + (b[i] & 0xFF);
                    buffer.append(getPYIndexChar((char) chart, bUpCase));
                } else {
                    char c = (char) b[i];
                    if (!Character.isJavaIdentifierPart(c))
                        c = '?';
                    buffer.append(c);
                }
                i++;
            }
            return buffer.toString();
        } catch (Exception e) {
            System.out.println("取中文拼音有错" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取字符
     *
     * @param strChinese
     * @param bUpCase
     * @return
     */
    private static char getPYIndexChar(char strChinese, boolean bUpCase) {
        int charGBK = strChinese;
        char result = '~';
        if (charGBK > 45216 && charGBK < 55290) {
            for (int i = 0; i < 23; i++) {
                if (charGBK >= posValue2[i] && charGBK < posValue2[i + 1]) {
                    result = en_char_Upper[i];
                    break;
                }
            }
        }
        if (!bUpCase)
            result = Character.toLowerCase(result);
        return result;
    }

    /**
     * 获取字符串的大小写拼音码，true为大写
     *
     * @param str
     * @param bUpCase
     * @return
     */
    public static String getPYIndexStr2(String strChinese, boolean bUpCase) {
        if (strChinese == null || strChinese.trim().length() == 0) {
            return "";
        }
        String result = "";
        for (int i = 0; i < strChinese.length(); i++) {
            try {
                result = result + getFirstLetter(strChinese.substring(i, i + 1), bUpCase);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 取得给定汉字的首字母,true为大写
     *
     * @param chinese
     * @param bUpCase
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getFirstLetter(String strChinese, boolean bUpCase) throws UnsupportedEncodingException {
        if (strChinese == null || strChinese.trim().length() == 0) {
            return "";
        }
        strChinese = new String(strChinese.getBytes("GB2312"), "ISO8859-1");
        if (strChinese.length() > 1) {
            int ch_area_code = (int) strChinese.charAt(0) - 160;// 汉字区码
            int ch_bit_code = (int) strChinese.charAt(1) - 160; // 汉字位码
            int ch_code = ch_area_code * 100 + ch_bit_code; // 汉字区位码
            if (ch_code > 1600 && ch_code < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (ch_code >= posValue[i] && ch_code < posValue[i + 1]) {
                        char a = en_char_Upper[i];
                        if (!bUpCase)
                            a = Character.toLowerCase(a);
                        strChinese = String.valueOf(a);
                        break;
                    }
                }
            } else {
                strChinese = new String(strChinese.getBytes("ISO8859"), "GB2312");
                strChinese = strChinese.substring(0, 1);
            }
        }
        return strChinese;
    }

}
