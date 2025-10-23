package com.ti2cc;

import java.util.List;
import java.util.Scanner;

public class Principal {
    
    public static void main(String[] args) {
        ProdutoDAO dao = new ProdutoDAO();
        dao.conectar();
        Scanner scanner = new Scanner(System.in);
        int opcao;
        
        do {
            System.out.println("\n==== MENU PRODUTO ====");
            System.out.println("1) Listar");
            System.out.println("2) Inserir");
            System.out.println("3) Excluir");
            System.out.println("4) Atualizar");
            System.out.println("5) Sair");
            System.out.print("Escolha: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch(opcao) {
                case 1:
                    listarProdutos(dao);
                    break;
                case 2:
                    inserirProduto(dao, scanner);
                    break;
                case 3:
                    excluirProduto(dao, scanner);
                    break;
                case 4:
                    atualizarProduto(dao, scanner);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while(opcao != 5);
        
        dao.close();
        scanner.close();
    }
    
    private static void listarProdutos(ProdutoDAO dao) {
        System.out.println("\n==== LISTA DE PRODUTOS ====");
        List<Produto> produtos = dao.listar();
        for(Produto p : produtos) {
            System.out.println(p.toString());
        }
    }
    
    private static void inserirProduto(ProdutoDAO dao, Scanner scanner) {
        System.out.println("\n==== INSERIR PRODUTO ====");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        
        Produto produto = new Produto(0, nome, preco, quantidade);
        if(dao.inserir(produto)) {
            System.out.println("Produto inserido com sucesso!");
        } else {
            System.out.println("Erro ao inserir produto!");
        }
    }
    
    private static void excluirProduto(ProdutoDAO dao, Scanner scanner) {
        System.out.println("\n==== EXCLUIR PRODUTO ====");
        System.out.print("ID do produto a excluir: ");
        int id = scanner.nextInt();
        
        if(dao.excluir(id)) {
            System.out.println("Produto excluído com sucesso!");
        } else {
            System.out.println("Erro ao excluir produto!");
        }
    }
    
    private static void atualizarProduto(ProdutoDAO dao, Scanner scanner) {
        System.out.println("\n==== ATUALIZAR PRODUTO ====");
        System.out.print("ID do produto a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo preço: ");
        double preco = scanner.nextDouble();
        System.out.print("Nova quantidade: ");
        int quantidade = scanner.nextInt();
        
        Produto produto = new Produto(id, nome, preco, quantidade);
        if(dao.atualizar(produto)) {
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar produto!");
        }
    }
}