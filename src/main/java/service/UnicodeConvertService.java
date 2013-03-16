package service;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-16
 * Time: 下午5:59
 * To change this template use File | Settings | File Templates.
 */
public class UnicodeConvertService {

    public static void main(String[] args){
        String codeString = "\u5409";
        System.out.println(codeString);
        String convertedString = convertUnicodeToChinese(codeString);
        System.out.println(convertedString);
    }

    public static String convertUnicodeToChinese(String codeString){
        //unicode转化汉字
        final StringBuffer buffer = new StringBuffer();
        String tempStr = "";
        String operStr = codeString;
        if (operStr != null && operStr.indexOf("\\u") == -1)
            return buffer.append(operStr).toString();
        if (operStr != null && !operStr.equals("")
                && !operStr.startsWith("\\u")) {
            tempStr = operStr.substring(0, operStr.indexOf("\\u"));
            operStr = operStr.substring(operStr.indexOf("\\u"), operStr.length());// operStr字符一定是以unicode编码字符打头的字符串
        }
        buffer.append(tempStr);
        // 循环处理,处理对象一定是以unicode编码字符打头的字符串
        while (operStr != null && !operStr.equals("")&& operStr.startsWith("\\u")) {
            tempStr = operStr.substring(0, 6);
            operStr = operStr.substring(6, operStr.length());
            String charStr = "";
            charStr = tempStr.substring(2, tempStr.length());
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            if (operStr.indexOf("\\u") == -1) {
                buffer.append(operStr);
            } else { // 处理operStr使其打头字符为unicode字符
                tempStr = operStr.substring(0, operStr.indexOf("\\u"));
                operStr = operStr.substring(operStr.indexOf("\\u"), operStr.length());
                buffer.append(tempStr);
            }
        }
        return buffer.toString();
    }
}
