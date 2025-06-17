package com.mvp.customerprocessor.config.filter;

import java.util.UUID;

public class UserContextHolder {

    private static final ThreadLocal<UUID> userIdHolder = new ThreadLocal<>();

    public static void setUserId(UUID userId) {
        userIdHolder.set(userId);
    }

    public static UUID getUserId() {
        return userIdHolder.get();
    }

    public static boolean isEmpty() {
        return userIdHolder.get() == null;
    }

    public static void clear() {
        userIdHolder.remove();
    }
}
