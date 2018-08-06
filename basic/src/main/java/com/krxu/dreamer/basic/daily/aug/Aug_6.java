package com.krxu.dreamer.basic.daily.aug;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/6
 * @description: 编写一个方法，计算一个字符串中，第一个不重复的字符在当前字符串中的索引。
 */
public class Aug_6 {

    public static void main(String[] args) {
        String a = "abuacdeaudbdfcefhph";
        char[] chars = a.toCharArray();

        Integer unLikeCharIndex = null;
        for (int i = 0; i < chars.length; i++) {
            boolean has = false;
            for (int j = 0; j < chars.length; j++) {
                if(i == j){
                    continue;
                }
                if (chars[i] == chars[j]) {
                    has = true;
                    break;
                }
            }
            if(!has){
                unLikeCharIndex = i;
                break;
            }
        }

        System.out.println(unLikeCharIndex);
    }
}
