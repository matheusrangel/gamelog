package matheusrangel.gamelog.model;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome, email , senha;
	private List<Game> jogosZerados, querJogar;
	
	
	public Usuario() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public List<Game> getJogosZerados() {
		return jogosZerados;
	}
	public void setJogosZerados(List<Game> jogosZerados) {
		this.jogosZerados = jogosZerados;
	}
	public void setJogoZerado(Game jogo) {
		this.jogosZerados.add(jogo);
	}
	public List<Game> getQuerJogar() {
		return querJogar;
	}
	public void setQuerJogar(List<Game> querJogar) {
		this.querJogar = querJogar;
	}
	public void setQueroJogar(Game jogo) {
		this.querJogar.add(jogo);
	}
	
}
