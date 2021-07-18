package org.mule.extension.multi.authenticator;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;
import org.junit.Test;

public class MultiAuthenticatorOperationsTestCase extends MuleArtifactFunctionalTestCase {

    /**
     * Specifies the mule config xml with the flows that are going to be executed in the tests, this file lives in the test resources.
     */
    @Override
    protected String getConfigFile() {

        return "test-multi-authenticator-extension-app.xml";
    }

    @Test
    public void testBasicHttpSHA256RightCredentials() throws Exception {
        String payloadValue = ((String) flowRunner("test-basic-http-SHA256-right-credentials-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Authentication success using right credentials with SHA256"));
    }

    @Test
    public void testBasicHttpSHA256WrongUser() throws Exception {
        String payloadValue = ((String) flowRunner("test-basic-http-SHA256-wrong-user-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Authentication failed using wrong user with SHA256"));
    }

    @Test
    public void testBasicHttpSHA512RightCredentials() throws Exception {
        String payloadValue = ((String) flowRunner("test-basic-http-SHA512-right-credentials-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Authentication success using right credentials with SHA512"));
    }

    @Test
    public void testBasicHttpSHA512WrongUser() throws Exception {
        String payloadValue = ((String) flowRunner("test-basic-http-SHA512-wrong-user-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Authentication failed using wrong user with SHA512"));
    }

    @Test
    public void testBasicHttpSHA256RightCredentialsAndExternalMap() throws Exception {
        String payloadValue = ((String) flowRunner("test-basic-http-SHA256-right-credentials-external-map-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Authentication success using right credentials with SHA256 and external map"));
    }

    @Test
    public void testBasicHttpSHA256WrongPassword() throws Exception {
        String payloadValue = ((String) flowRunner("test-basic-http-SHA256-wrong-password-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Authentication failed using wrong password with SHA256"));
    }

    @Test
    public void testBasicHttpSHA512WrongPassword() throws Exception {
        String payloadValue = ((String) flowRunner("test-basic-http-SHA512-wrong-password-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Authentication failed using wrong password with SHA512"));
    }

    @Test
    public void testBasicHttpWrongAuthType() throws Exception {
        String payloadValue = ((String) flowRunner("test-basic-http-wrong-auth-type-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Authentication failed using wrong authentication type"));
    }

    @Test
    public void testBasicHttpBase64BadEncode() throws Exception {
        String payloadValue = ((String) flowRunner("test-basic-http-wrong-base64-encode-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Authentication failed using bad Base64 encoding"));
    }

    @Test
    public void testBasicHttpNullAuthHeader() throws Exception {
        String payloadValue = ((String) flowRunner("test-basic-http-null-auth-header-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Authentication failed using null auth header"));
    }

    @Test
    public void testApiKeySHA1RightCredentials() throws Exception {
        String payloadValue = ((String) flowRunner("test-api-key-SHA1-right-credentials-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("API Key Authentication success using right credentials with SHA1"));
    }

    @Test
    public void testApiKeySHA1WrongCredentials() throws Exception {
        String payloadValue = ((String) flowRunner("test-api-key-SHA1-wrong-credentials-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("API Key Authentication failed using wrong API Key with SHA1"));
    }

    @Test
    public void testApiKeySHA1RightCredentialsAndExternalList() throws Exception {
        String payloadValue = ((String) flowRunner("test-api-key-SHA1-right-credentials-external-list-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("API Key Authentication success using right credentials with SHA1 and external list"));
    }

    @Test
    public void testGenerateJWTSHA512() throws Exception {
        String payloadValue = ((String) flowRunner("test-generate-jwt-SHA512-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue.matches("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$"), is(true));
    }

    @Test
    public void testVerifyJWTSHA256RightSecret() throws Exception {
        String payloadValue = ((String) flowRunner("test-verify-jwt-SHA256-right-secret-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, containsString("JWT generation success using SHA256 and right secret"));
    }

    @Test
    public void testVerifyJWTSHA256ExpiredToken() throws Exception {
        String payloadValue = ((String) flowRunner("test-verify-jwt-SHA256-expired-token-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Verify JWT failed using expired token with SHA256"));
    }

    @Test
    public void testVerifyJWTSHA512InvalidSecret() throws Exception {
        String payloadValue = ((String) flowRunner("test-verify-jwt-SHA512-invalid-secret-flow").run()
                .getMessage()
                .getPayload()
                .getValue());
        assertThat(payloadValue, is("Verify JWT failed using invalid secret with SHA512"));
    }
}