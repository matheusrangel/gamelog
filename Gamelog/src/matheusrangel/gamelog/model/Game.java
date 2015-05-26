package matheusrangel.gamelog.model;

import java.io.Serializable;
import java.util.Calendar;

public class Game implements Serializable {
	
	private int id;
	private String titulo, cover;
	private Calendar lancamento;
	private double nota;
	
	public Game() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public Calendar getLancamento() {
		return lancamento;
	}
	public void setLancamento(Calendar lancamento) {
		this.lancamento = lancamento;
	}
	public void setLancamento(long lancamento) {
		this.lancamento = Calendar.getInstance();
		this.lancamento.setTimeInMillis(lancamento);
	}
	public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
	}
	@Override
	public String toString() {
		return String.format("%s", this.titulo);
	}
	
}
