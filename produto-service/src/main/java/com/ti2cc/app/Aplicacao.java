package com.ti2cc.app;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.ti2cc.model.Produto;
import com.ti2cc.service.ProdutoService;

public class Aplicacao {
    private static ProdutoService produtoService = new ProdutoService();
    private static Gson gson = new Gson();
    
    public static void main(String[] args) {
        port(4567);
        
        // Configurar arquivos estáticos - CORRIGIDO
        staticFiles.location("/public");
        staticFiles.expireTime(600L); // 10 minutos
        
        // Adicionar rota raiz para redirecionar para o formulário
        get("/", (req, res) -> {
            res.redirect("/formulario.html");
            return null;
        });
        
        // Configurar CORS
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            
            return "OK";
        });
        
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.type("application/json");
        });
        
        // Rotas da API
        get("/produtos", (req, res) -> {
            return produtoService.listarProdutos();
        });
        
        get("/produto/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            return produtoService.getProduto(id);
        });
        
        post("/produto", (req, res) -> {
            Produto produto = gson.fromJson(req.body(), Produto.class);
            boolean success = produtoService.inserirProduto(produto);
            return "{\"success\": " + success + "}";
        });
        
        put("/produto/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Produto produto = gson.fromJson(req.body(), Produto.class);
            produto.setId(id);
            boolean success = produtoService.atualizarProduto(produto);
            return "{\"success\": " + success + "}";
        });
        
        delete("/produto/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            boolean success = produtoService.excluirProduto(id);
            return "{\"success\": " + success + "}";
        });
        
        System.out.println("Servidor rodando na porta 4567...");
        System.out.println("Formulário: http://localhost:4567/formulario.html");
        System.out.println("API Produtos: http://localhost:4567/produtos");
    }
}