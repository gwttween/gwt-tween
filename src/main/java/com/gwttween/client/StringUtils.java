package com.gwttween.client;

public class StringUtils {

    /**
     * Calling method {@code  toString} for Object
     * <br>
     * Example of usage:
     * <br>
     * {@code Utils.messageFormat("I have {0} apples and I can give you {1} apples", 5 , 2) }
     * <br> result is: {@code "I have 5 apples and I can give you 2 apples"}
     * @param text String
     * @param args for every object there is a call .toString()
     * @return String in described format
     */
    public static String format(String text, Object... args) {
        String result = text;
        if (args == null) {
            return result;
        }
        for (int i = 0; i < args.length; i++) {
            result = result.replace("{" + i + "}", args[i].toString());
        }
        return result;
    }
}
