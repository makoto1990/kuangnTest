package com.kuangn.ts_server.rest.resources.types;

/**
 * Created by Weng on 2016/6/17.
 */
public class FancyBoolean {
    private static final FancyBoolean FALSE = new FancyBoolean(false);
    private static final FancyBoolean TRUE = new FancyBoolean(true);
    private boolean value;

    private FancyBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    public static FancyBoolean valueOf(String value) {
        String s = value.toLowerCase();
        if (s.equals("true") || s.equals("yes") || s.equals("y")) {
            return FancyBoolean.TRUE;
        } else {
            return FancyBoolean.FALSE;
        }
    }
}
