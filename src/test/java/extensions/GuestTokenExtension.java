package extensions;

import enums.UserRole;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import support.TokenManager;

public class GuestTokenExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        TokenManager.setCurrentRole(UserRole.GUEST);
        TokenManager.getToken();
    }
}
