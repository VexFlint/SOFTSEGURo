
package Controller;

import Utility.AWS_CognitoService;
import View.CaixaView;

import java.util.Scanner;

public class UserController {
    private AWS_CognitoService cognitoService = AWS_CognitoService.getInstance();

    public void registrar(Scanner scanner) {
        System.out.print("Digite o email: ");
        String email = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        boolean sucesso = cognitoService.registrarUsuario(email, senha);
        if (sucesso) {
            System.out.println("Registro de usuário bem-sucedido.");
            new CaixaView().iniciar();
        } else {
            System.out.println("Enviado a confirmação de cadastro ao Email.");
        }
    }

    public void login(Scanner scanner) {
        System.out.print("Digite o email: ");
        String email = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        boolean sucesso = cognitoService.loginUsuario(email, senha);
        if (sucesso) {
            System.out.println("Autenticação bem-sucedida.");
            new CaixaView().iniciar();
        } else {
            System.out.println("Falha na autenticação.");
        }
    }
}
