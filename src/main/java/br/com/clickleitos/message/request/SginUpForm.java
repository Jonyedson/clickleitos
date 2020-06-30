package br.com.clickleitos.message.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SginUpForm {

    @NotBlank
    @Size(min = 3, max = 50)
    private String nome;

    @NotBlank
    @Size(min = 3, max = 50)
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    private String senha;



}
