package Controller;

import DAO.ProdutoDAO;
import Model.Produto;
import Model.Carrinho;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CaixaController {
    private ProdutoDAO produtoDAO;
    private Carrinho carrinho;
    private Scanner scanner = new Scanner(System.in);

    public CaixaController() {
        try {
            produtoDAO = new ProdutoDAO();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            produtoDAO = null;  // Handle null appropriately in your code
        }
        carrinho = new Carrinho();
    }

    public List<Produto> listarProdutos() throws SQLException {
        return produtoDAO.getTodosProdutos();
    }

    public void adicionarAoCarrinho(Produto produto, int quantidade) {
        carrinho.adicionarProduto(produto, quantidade);
    }

    public void finalizarCompra() {
        carrinho.finalizarCompra();
    }

    public double getTotalCarrinho() {
        return carrinho.getTotal();
    }

    public Produto buscarProdutoPorId(int id) {
        try {
            return produtoDAO.getProdutoPorId(id);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o produto: " + e.getMessage());
            return null;
        }
    }

    public void resetarCarrinho() {
        carrinho.resetarCarrinho();
    }

    public void adicionarSaldoProduto(int id, int quantidade) {
        try {
            Produto produto = buscarProdutoPorId(id);
            if (produto != null) {
                ProdutoDAO.atualizarQuantidadeProduto(id, produto.getQuantidade() + quantidade);
            } else {
                System.out.println("Produto com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar saldo ao produto: " + e.getMessage());
        }
    }

    public void criarProduto(String nome, double preco, int quantidade) {
        produtoDAO.adicionarProduto(new Produto(0, nome, preco, quantidade));
    }
    
    public void listarProdutosNaView() {
        try {
            List<Produto> produtos = listarProdutos();
            for (Produto produto : produtos) {
                System.out.println(produto.getId() + " - " + produto.getNome() + " - " + produto.getPreco() + " - " + produto.getQuantidade());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
    }

    public void adicionarAoCarrinhoNaView() {
        int id = lerInteiro("ID do produto: ");
        Produto produto = buscarProdutoPorId(id);
        if (produto != null) {
            int quantidade = lerInteiro("Quantidade: ");
            if (quantidade > 0 && quantidade <= produto.getQuantidade()) {
                adicionarAoCarrinho(produto, quantidade);
                System.out.println("Produto adicionado ao carrinho.");
            } else {
                System.out.println("Quantidade inválida ou insuficiente.");
            }
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    public void finalizarCompraNaView() {
        finalizarCompra();
        resetarCarrinho();
    }

    public void gerenciarProdutosNaView() {
        System.out.println("Escolha uma opção de gerenciamento:");
        System.out.println("1. Adicionar Saldo a um Produto Existente");
        System.out.println("2. Criar Novo Produto");
        System.out.println("0. Voltar");
        int opcao = lerInteiro("Opção: ");

        switch (opcao) {
            case 1:
                adicionarSaldoProdutoExistenteNaView();
                break;
            case 2:
                criarNovoProdutoNaView();
                break;
            case 0:
                return;
            default:
                System.out.println("Opção inválida. Por favor, tente novamente.");
                System.out.println("Opção inválida.");
        }
    }

    public void adicionarSaldoProdutoExistenteNaView() {
        int id = lerInteiro("ID do produto para adicionar saldo: ");
        int quantidade = lerInteiro("Quantidade a adicionar: ");
        adicionarSaldoProduto(id, quantidade);
    }

    public void criarNovoProdutoNaView() {
        String nome = lerString("Nome do Produto: ");
        double preco = lerDouble("Preco do Produto: ");
        int quantidade = lerInteiro("Quantidade : ");
        criarProduto(nome, preco, quantidade);
    }

    public int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int num = scanner.nextInt();
                scanner.nextLine(); // consume the newline
                return num;
            } else {
                scanner.nextLine(); // clear the invalid input
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            }
        }
    }

    public String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double num = scanner.nextDouble();
                scanner.nextLine(); // consume the newline
                return num;
            } else {
                scanner.nextLine(); // clear the invalid input
                System.out.println("Entrada inválida. Por favor, insira um valor numérico.");
            }
        }
    }
}
