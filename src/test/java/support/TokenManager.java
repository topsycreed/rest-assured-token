package support;

import enums.UserRole;

import java.util.EnumMap;
import controllers.TokenClient;

public class TokenManager {
    private static final ThreadLocal<EnumMap<UserRole, String>> threadTokens =
            ThreadLocal.withInitial(() -> new EnumMap<>(UserRole.class));

    public static String getToken(UserRole role) {
        return threadTokens.get().computeIfAbsent(role, TokenManager::fetchToken);
    }

    private static String fetchToken(UserRole role) {
        return switch (role) {
            case GUEST -> TokenClient.getGuestToken();
            case ADMIN -> throw new IllegalArgumentException("Not implemented");
        };
    }

    public static void clear() {
        threadTokens.remove();
    }
}
