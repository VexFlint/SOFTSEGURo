
package Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.ProdutoDAO;

public class Carrinho {
    private List<Produto> itens;
    private double total;

    public Carrinho() {
        itens = new ArrayList<>();
        total = 0;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        if (produto.getQuantidade() >= quantidade) {
            produto.setQuantidadeComprada(quantidade);
            itens.add(produto); // Consider adding logic to check if product is already in cart
            total += produto.getPreco() * quantidade;
        } else {
            System.out.println("Quantidade solicitada não disponível.");
        }
    }

    
    public void finalizarCompra() {
        for (Produto produto : itens) {
            try {
                ProdutoDAO.atualizarQuantidadeProduto(produto.getId(), produto.getQuantidade() - produto.getQuantidadeComprada());
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar o banco de dados: " + e.getMessage());
            }
        }
        
        total = 0;
        for (Produto produto : itens) {
            total += produto.getPreco() * produto.getQuantidadeComprada();
        }
        System.out.println("Compra finalizada com sucesso. Total: " + total);

        resetarCarrinho();
    }


    public void resetarCarrinho() {
        itens.clear();
        total = 0;
    }

    public List<Produto> getItens() {
        return itens;
    }

    public double getTotal() {
        return total;
    }
}
