package br.com.clickleitos.services.exceptions;

public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DatabaseException(String mensagem) {
		super(mensagem);
	}

}
