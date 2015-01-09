package br.com.tiago.drone.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 8017424701716372558L;

	@Id
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String passWord;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "endereco")
	private String endereco;

	public User() {
	}

	public User(Long id, String nome, String userName) {
		this.id = id;
		this.nome = nome;
		this.userName = userName;
		this.passWord = "";
		this.cpf = "";
		this.endereco = "";
	}

	public User(Long id, String nome, String userName, String passWord,
			String cpf, String endereco) {
		this.id = id;
		this.nome = nome;
		this.userName = userName;
		this.passWord = passWord;
		this.cpf = cpf;
		this.endereco = endereco;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nome=" + nome + ", userName=" + userName
				+ ", passWord=" + passWord + ", cpf=" + cpf + ", endereco="
				+ endereco + "]";
	}
}
