
package View;

import java.util.Scanner;

public class LoginView {

    private Scanner scanner;

    public LoginView() {
        this.scanner = new Scanner(System.in);
    }

    public String[] obterCredenciais() {
        System.out.println("Por favor, faça login para continuar.");

        System.out.print("Usuário: ");
        String username = scanner.nextLine();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        return new String[]{username, password};
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}
