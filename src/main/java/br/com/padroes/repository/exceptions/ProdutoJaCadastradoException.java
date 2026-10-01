package br.com.padroes.repository.exceptions;

public class ProdutoJaCadastradoException extends RuntimeException {

    public ProdutoJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}