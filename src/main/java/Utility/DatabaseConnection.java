
package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/supermercado";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection con;
    private static DatabaseConnection conexao;

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o banco de dados estabelecida.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstacia() {
        if (conexao == null) {
            conexao = new DatabaseConnection();
        }
        return conexao;
    }

    public static Connection getConnection() {
        if (con == null) {
            System.out.println("Conexão nula. Tentando criar nova conexão.");
            new DatabaseConnection();
        }
        return con;
    }
}
