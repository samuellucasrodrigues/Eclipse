package com.ti2cc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection conexao;
    
    public ProdutoDAO() {
        conexao = null;
    }
    
    public boolean conectar() {
        String driverName = "org.postgresql.Driver";                    
        String serverName = "localhost";
        String mydatabase = "teste";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
        String username = "ti2cc";
        String password = "ti@cc";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) { 
            System.err.println("Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
        }
        return status;
    }
    
    public boolean close() {
        boolean status = false;
        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }
    
    public boolean inserir(Produto produto) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO produto (nome, preco, quantidade) "
                           + "VALUES ('" + produto.getNome() + "', " + produto.getPreco() 
                           + ", " + produto.getQuantidade() + ");");
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public boolean atualizar(Produto produto) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            String sql = "UPDATE produto SET nome = '" + produto.getNome() + "', preco = "  
                       + produto.getPreco() + ", quantidade = " + produto.getQuantidade()
                       + " WHERE id = " + produto.getId();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public boolean excluir(int id) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM produto WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();
        try {
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM produto");
            while(rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getInt("quantidade")
                );
                produtos.add(produto);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return produtos;
    }
}