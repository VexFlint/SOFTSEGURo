
package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
import View.LoginView;

public class LoginController {

    private UsuarioDAO usuarioDAO;
    private LoginView loginView;

    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
        this.loginView = new LoginView();
    }

    public void iniciar() {
        while (true) {
            String[] credenciais = loginView.obterCredenciais();
            String username = credenciais[0];
            String password = credenciais[1];

            Usuario usuario = new Usuario();
            usuario.setEmail(username);
            usuario.setSenha(password);

            if (usuarioDAO.loginUsuario(usuario)) {
                loginView.exibirMensagem("Login bem-sucedido!");
                break; // Sair do loop após login bem-sucedido
            } else {
                loginView.exibirMensagem("Falha no login. Por favor, tente novamente.");
            }
        }
    }
}
