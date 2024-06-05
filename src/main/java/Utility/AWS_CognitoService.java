
package Utility;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;

import java.util.HashMap;
import java.util.Map;

public class AWS_CognitoService {
    private static AWS_CognitoService instance;
    private String userPoolId;
    private String clientId;
    private AWSCognitoIdentityProvider cognitoClient;

    private AWS_CognitoService() {
        this.userPoolId = "us-east-1_0lSOl9YEH";
        this.clientId = "47971q3thbf24grcbad7niv1m5";
        this.cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
                .withRegion("us-east-1")
                .build();
    }

    public static AWS_CognitoService getInstance() {
        if (instance == null) {
            instance = new AWS_CognitoService();
        }
        return instance;
    }

    public boolean registrarUsuario(String email, String senha) {
        try {
            SignUpRequest signUpRequest = new SignUpRequest()
                    .withClientId(clientId)
                    .withUsername(email)
                    .withPassword(senha);

            SignUpResult result = cognitoClient.signUp(signUpRequest);
            return result.getUserConfirmed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUsuario(String email, String senha) {
        try {
            Map<String, String> authParams = new HashMap<>();
            authParams.put("USERNAME", email);
            authParams.put("PASSWORD", senha);

            InitiateAuthRequest authRequest = new InitiateAuthRequest()
                    .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                    .withAuthParameters(authParams)
                    .withClientId(clientId);

            InitiateAuthResult authResponse = cognitoClient.initiateAuth(authRequest);
            AuthenticationResultType authResult = authResponse.getAuthenticationResult();
            return authResult != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
