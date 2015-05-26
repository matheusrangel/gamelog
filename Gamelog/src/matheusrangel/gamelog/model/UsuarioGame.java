package matheusrangel.gamelog.model;

import java.io.Serializable;

public class UsuarioGame implements Serializable {

	private static final long serialVersionUID = 1L;
	private int usuarioId, gameId, status;
	
	public UsuarioGame() {
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
