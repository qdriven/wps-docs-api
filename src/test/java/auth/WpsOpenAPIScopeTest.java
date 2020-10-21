package auth;

import io.qmeta.wps.auth.WpsOpenAPIScope;

class WpsOpenAPIScopeTest {

    @org.junit.jupiter.api.Test
    void allScopes() {
        System.out.println(WpsOpenAPIScope.allScopes());
    }
}