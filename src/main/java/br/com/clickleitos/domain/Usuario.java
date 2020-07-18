package br.com.clickleitos.domain;

import br.com.clickleitos.domain.audit.AuditEvent;
import br.com.clickleitos.domain.enums.Profile;
import br.com.clickleitos.domain.erro.ErroDetails;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
public class Usuario extends AuditEvent<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 60)
	private String nome;
	@NotBlank
	@Size(min = 11, max = 11)
	private String cpf;
	@NotBlank
	@Size(min = 6, max = 100)
	@Unique
	private String senha;
	@NotEmpty
	@Size(min = 1, max = 100)
	@Email
	@Unique
	private String email;

	private Boolean ativo;


	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PROFILES")
	private Set<Integer> profiles = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "id.unidade")
	private Unidade unidade;

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "usuario")
	private Set<ErroDetails> erros;
	public Usuario( ) {

	}

	public Usuario(String email) {
		this.email = email;
	}

	public Usuario(Long id, String nome, String cpf, String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.ativo = false;
		addProfile(Profile.ADMIN_UNIDADE);
	}
	public Usuario(Long id, String nome, String cpf, String email, String senha, Unidade unidade) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.unidade = unidade;
		this.ativo = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getStatus() {
		return ativo;
	}

	public void setStatus(Boolean status) {
		this.ativo = status;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Set<Profile> getProfiles() {
		return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
	}

	public void setProfiles(Set<Integer> profiles) {
		this.profiles = profiles;
	}
	public void addProfile(Profile profile) {
		profiles.add(profile.getCode());
	}

	public Set<Integer> getProfilesNum() {
		return profiles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
