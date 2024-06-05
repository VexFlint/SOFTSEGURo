
package DAO;

import Model.Produto;
import Utility.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class ProdutoDAO {
    private static Connection connection;
    private static final String arquivoerro = "path/to/logfile.log";
    // Singleton pattern for database connection
    public ProdutoDAO() throws SQLException {
        if (ProdutoDAO.connection == null) {
            ProdutoDAO.connection = DatabaseConnection.getConnection();
        }
    }

    private static void logSecure(String message) {
        try {
            Files.write(Paths.get(arquivoerro), (message + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public static void atualizarQuantidadeProduto(int id, int novaQuantidade) throws SQLException {
        String query = "UPDATE produtos SET quantidade = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logSecure("Failed to update product quantity: " + e.getMessage()); // Secure logging
            throw e;
        }
    }

    public Produto getProdutoPorId(int id) throws SQLException {
        String query = "SELECT * FROM produtos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setPreco(rs.getDouble("preco"));
                    produto.setQuantidade(rs.getInt("quantidade"));
                    return produto;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public void adicionarProduto(Produto produto) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)");
            stmt.setString(1, produto.getNome()); // Assume string normalization is handled earlier
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            logSecure("Error adding product: " + e.getMessage()); // Secure logging
            throw new RuntimeException("Database error when adding product", e);
        }
    }


    public List<Produto> getTodosProdutos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String query = "SELECT * FROM produtos";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                produtos.add(new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                ));
            }
        } catch (SQLException e) {
            logSecure("Failed to retrieve all products: " + e.getMessage()); // Secure logging
            throw e;
        }
        return produtos;
    }

}
