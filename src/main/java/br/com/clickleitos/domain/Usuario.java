package br.com.clickleitos.domain;

import br.com.clickleitos.domain.enums.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;

	@Column(unique = true)
	private String email;
	@Column(unique = true)
	@JsonIgnore
	private String senha;

	private String urlProfile;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PROFILES")
	private Set<Integer> profiles = new HashSet<>();

	@OneToMany(mappedBy = "usuario")
	private Set<Unidade> unidades = new HashSet<>();

	public Usuario(Long id) {
		addProfile(Profile.USUARIO);
	}

	public Usuario() {

	}

	public Usuario(Long id, String nome, String senha, String email) {
		super();
		this.id = id;
		this.senha = senha;
		this.email = email;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Unidade> getUnidades() {
		return unidades;
	}

	public Set<Profile> getProfiles() {
		return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}
}
