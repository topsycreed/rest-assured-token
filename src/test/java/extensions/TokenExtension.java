package extensions;

import enums.UserRole;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import support.TokenManager;

public class TokenExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        TokenManager.getToken(UserRole.GUEST);
    }
}
