package com.proposta.aceita.matchservice.util;

import java.util.List;
import java.util.Map;

public final class CheckUtils {

    private CheckUtils() {
    }

    public static boolean stringIsNullOrEmpty(String string) {
        return null == string || string.trim().isEmpty();
    }

    public static boolean mapIsNullOrEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    public static boolean listIsNullOrEmpty(List<?> list) {
        return null == list || list.isEmpty();
    }

}