package com.wzq.core.interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author: wangzhenqing
 * @date: 2015-09-02 16:28:22
 * @description: 令牌化是指在分隔符的基础上将一个字符串分割为若干个子字符串。
 * 例如，分隔符；分割字符串ac;bd;def;e为四个子字符串ac，bd，def和e。
 */
public class TokenizeDemo {
    private static void tokenize(String string, String regex) {
        String[] tokens = string.split(regex);
        System.out.println(Arrays.toString(tokens));
    }

    private static void tokenizeUsingScanner(String string, String regex) {
        Scanner scanner = new Scanner(string);
        scanner.useDelimiter(regex);
        List<String> matches = new ArrayList<String>();
        while (scanner.hasNext()) {
            matches.add(scanner.next());
        }
        System.out.println(matches);
    }

    public static void main(String[] args) {
        tokenize("ac;bd;def;e", ";");//[ac, bd, def, e]
        tokenizeUsingScanner("ac;bd;def;e", ";");//[ac, bd, def, e]
    }

}
