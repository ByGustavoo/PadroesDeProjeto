package br.com.atlas.repository.exceptions;

// Exceção de negócio: já existe um produto com o mesmo nome.
public class ProdutoJaCadastradoException extends RuntimeException {

    public ProdutoJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}