package br.com.atlas.repository.exceptions;

// Exceção de negócio: o produto buscado não existe.
public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}