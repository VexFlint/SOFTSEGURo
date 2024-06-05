package View;

import Controller.CaixaController;

public class CaixaView {
    private CaixaController controller;

    public CaixaView() {
        controller = new CaixaController();
    }

    public void iniciar() {
        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Listar Produtos");
            System.out.println("2. Adicionar ao Carrinho");
            System.out.println("3. Finalizar Compra");
            System.out.println("9. Gerenciar Produtos");
            System.out.println("0. Sair");
            int opcao = controller.lerInteiro("Opção: ");

            switch (opcao) {
                case 1:
                    controller.listarProdutosNaView();
                    break;
                case 2:
                    controller.adicionarAoCarrinhoNaView();
                    break;
                case 3:
                    controller.finalizarCompraNaView();
                    break;
                case 9:
                    controller.gerenciarProdutosNaView();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        }
    }
}
