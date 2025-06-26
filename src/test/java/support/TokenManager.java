package support;

import enums.UserRole;

import java.util.EnumMap;
import controllers.TokenClient;

public class TokenManager {
    private static final ThreadLocal<UserRole> currentRole = new ThreadLocal<>();
    private static final ThreadLocal<EnumMap<UserRole, String>> threadTokens =
            ThreadLocal.withInitial(() -> new EnumMap<>(UserRole.class));

    public static void setCurrentRole(UserRole role) {
        currentRole.set(role);
    }

    public static String getToken() {
        UserRole role = currentRole.get();
        if (role == null) {
            throw new IllegalStateException("""
                🧪 [TokenManager Error] User role was not set.
                
                💡 Make sure you added @ExtendWith(GuestTokenExtension.class) or @ExtendWith(AuthTokenExtension.class).
                
                🔧 Examples:
                   @ExtendWith(GuestTokenExtension.class)
                   class MyTest { ... }
                """);
        }
        return getToken(role);
    }

    public static String getToken(UserRole role) {
        return threadTokens.get().computeIfAbsent(role, TokenManager::fetchToken);
    }

    private static String fetchToken(UserRole role) {
        return switch (role) {
            case GUEST -> TokenClient.getGuestToken();
            case AUTH -> TokenClient.getAuthToken();
        };
    }

    @SuppressWarnings("unused")
    public static void clear() {
        currentRole.remove();
        threadTokens.remove();
    }
}
