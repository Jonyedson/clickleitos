package br.com.clickleitos.message.request;


import br.com.clickleitos.domain.Unidade;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class SignUpForm {

    @NotBlank
    @Size(min = 3, max = 50)
    private String nome;

    @NotBlank
    @Size(min = 3, max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    private String senha;

    @NotBlank
    @Size(min = 11,max = 11)
    private String cpf;

    private Unidade unidade;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cnpj) {
        this.cpf = cnpj;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }
}
