package com.ti2cc.service;

import java.util.List;
import com.google.gson.Gson;
import com.ti2cc.dao.ProdutoDAO;
import com.ti2cc.model.Produto;

public class ProdutoService {
    private ProdutoDAO dao;
    private Gson gson;
    
    public ProdutoService() {
        dao = new ProdutoDAO();
        dao.conectar();
        gson = new Gson();
    }
    
    public String listarProdutos() {
        List<Produto> produtos = dao.listar();
        return gson.toJson(produtos);
    }
    
    public String getProduto(int id) {
        Produto produto = dao.getProduto(id);
        return gson.toJson(produto);
    }
    
    public boolean inserirProduto(Produto produto) {
        return dao.inserir(produto);
    }
    
    public boolean atualizarProduto(Produto produto) {
        return dao.atualizar(produto);
    }
    
    public boolean excluirProduto(int id) {
        return dao.excluir(id);
    }
}