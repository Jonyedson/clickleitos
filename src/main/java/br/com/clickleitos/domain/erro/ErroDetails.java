package br.com.clickleitos.domain.erro;

import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.domain.audit.AuditEvent;

import javax.persistence.*;

@Entity
@Table(name = "erro")
public class ErroDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String details;

    private String ambiente;

    private String origem;

    private String nivel;

    private boolean arquivodo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id.usuario", referencedColumnName = "id")
    private Usuario usuario;

    public ErroDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public boolean isArquivodo() {
        return arquivodo;
    }

    public void setArquivodo(boolean arquivodo) {
        this.arquivodo = arquivodo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
