package Model;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private int quantidade;
    private int quantidadeComprada;

    public Produto(int id, String nome, double preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto() {

    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadeComprada() { return quantidadeComprada; }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidadeComprada(int quantidadeComprada) { this.quantidadeComprada = quantidadeComprada;}
}