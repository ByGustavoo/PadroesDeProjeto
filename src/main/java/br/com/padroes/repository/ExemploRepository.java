package br.com.padroes.repository;

import br.com.padroes.Moeda;
import br.com.padroes.Exemplo;
import br.com.padroes.repository.mapper.ProdutoMapper;
import br.com.padroes.repository.dto.SalvarProdutoDTO;
import br.com.padroes.repository.service.ProdutoService;
import br.com.padroes.repository.exceptions.ProdutoJaCadastradoException;
import br.com.padroes.repository.exceptions.ProdutoNaoEncontradoException;

import java.math.BigDecimal;

public class ExemploRepository implements Exemplo {

    @Override
    public String titulo() {
        return "Repository + DTO + Injeção de Dependência";
    }

    @Override
    public void executar() {
        ProdutoRepository produtoRepository = new ProdutoRepositoryEmMemoria();
        var produtoService = new ProdutoService(new ProdutoMapper(), produtoRepository);

        var teclado = produtoService.salvar(new SalvarProdutoDTO("Teclado mecânico", new BigDecimal("349.90"), 15));
        produtoService.salvar(new SalvarProdutoDTO("Mouse sem fio", new BigDecimal("89.90"), 0));
        produtoService.salvar(new SalvarProdutoDTO("Monitor 27\"", new BigDecimal("1899.00"), 4));

        IO.println("Produtos devolvidos pelo service como DTO (a entidade não sai da camada):");
        produtoService.listar().forEach(produto -> IO.println("  %-17s %12s  %s".formatted(produto.nome(), Moeda.formatar(produto.preco()), produto.disponivel() ? "disponível" : "esgotado")));

        IO.println("Busca por id: " + produtoService.buscar(teclado.id()).nome());

        try {
            produtoService.salvar(new SalvarProdutoDTO("teclado MECÂNICO", new BigDecimal("299.90"), 3));
        } catch (ProdutoJaCadastradoException excecao) {
            IO.println("Cadastro duplicado rejeitado: " + excecao.getMessage());
        }

        produtoService.deletar(teclado.id());

        try {
            produtoService.buscar(teclado.id());
        } catch (ProdutoNaoEncontradoException excecao) {
            IO.println("Busca após deletar: " + excecao.getMessage());
        }
    }
}