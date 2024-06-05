
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Model.Usuario;
import Utility.DatabaseConnection;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;

public class UsuarioDAO {

    private DatabaseConnection conexao;
    private String query;
    private PreparedStatement ps;
    private ResultSet rs;

    private String USER_POOL_ID = "us-east-1_0lSOl9YEH";
    private String CLIENT_ID = "47971q3thbf24grcbad7niv1m5";
    private Map<String, String> authparams = new HashMap<String, String>();

    public UsuarioDAO() {
        this.conexao = DatabaseConnection.getInstacia();
    }

    private AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();

    public boolean loginUsuario(Usuario usuario) {
        authparams.put("USERNAME", usuario.getEmail());
        authparams.put("PASSWORD", usuario.getSenha());

        try {
            InitiateAuthRequest authRequest = new InitiateAuthRequest()
                    .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                    .withAuthParameters(authparams)
                    .withClientId(CLIENT_ID);

            InitiateAuthResult authResponse = cognitoClient.initiateAuth(authRequest);
            AuthenticationResultType authResult = authResponse.getAuthenticationResult();
            if (authResult != null) {
                System.out.println("Usuario logado com sucesso no Cognito");
                return true;
            } else {
                System.out.println("Usuario ou senha invalidos");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
