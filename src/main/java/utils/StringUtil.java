package utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 从一个字符串中匹配出数字，找到了返回对应数字
     * 若未找到，则返回-1
     * @param sourceString
     * @param index
     * @return
     */
    public static int getNumByString(String sourceString,int index){
        Pattern pattern = Pattern.compile("\\d{1,}");
        Matcher matcher = pattern.matcher(sourceString);
        List<Integer> list = new ArrayList<Integer>();
        while(matcher.find()){
            //判断数字是否大于65535
            String group = matcher.group();
            BigInteger bigInteger = new BigInteger(group);
            if(bigInteger.compareTo(new BigInteger("65535")) == -1){
                //小于65535的数字才能保存为int
                list.add(Integer.valueOf(group));
            }
        }
        if(list.size() > 0 && index < list.size()){
            return list.get(index);
        }else {
            System.out.println("你要查找的第" + (index + 1) + "组数字不存在");
            return -1;
        }
    }
}
