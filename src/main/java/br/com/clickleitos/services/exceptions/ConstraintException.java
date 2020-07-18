package br.com.clickleitos.services.exceptions;

public class ConstraintException extends RuntimeException{
    private final static  long serialVersionUID = 1L;

    public ConstraintException(String s) {
        super(s);
    }
}
