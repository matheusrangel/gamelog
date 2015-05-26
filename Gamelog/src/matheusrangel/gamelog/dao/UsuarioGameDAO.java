package matheusrangel.gamelog.dao;

import java.util.ArrayList;
import java.util.List;

import matheusrangel.gamelog.MainActivity;
import matheusrangel.gamelog.model.Game;
import matheusrangel.gamelog.model.Usuario;
import matheusrangel.gamelog.model.UsuarioGame;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioGameDAO {
	
	private SQLiteDatabase banco;
	private static final String TABELA = "usuario_game";
	private List<UsuarioGame> usuariogameList;
	private GameDAO gameDAO;
	
	
	public UsuarioGameDAO(Context context) {
		this.banco = new DB(context).getWritableDatabase();
		this.usuariogameList = new ArrayList<UsuarioGame>();
		
	}
	
	public void insert(Usuario user, Game game, int status){
		ContentValues cv = new ContentValues();
		cv.put("usuario_id", user.getId());
		cv.put("game_id", game.getId());
		cv.put("status", status);
		
		this.banco.insert(TABELA, null, cv);
	}
	
	public void remove(int userId, int gameId) {
		String[] params = {Integer.toString(userId), Integer.toString(gameId)};
		this.banco.delete(TABELA, "usuario_id = ? and game_id = ?", params);
	}
	
	public void update(Usuario user, Game game, int status) {
		ContentValues cv = new ContentValues();
		cv.put("usuario_id", user.getId());
		cv.put("game_id", game.getId());
		cv.put("status", status);
		String[] params = {Integer.toString(user.getId()), Integer.toString(game.getId())};
		this.banco.update(TABELA, cv, "usuario_id = ? and game_id = ?", params);
	}
	
	public List<UsuarioGame> findAll() {
		String[] colunas = {"usuario_id", "game_id", "status"};
		Cursor cursor = this.banco.query(TABELA, colunas, null, null, null, null, null);
		this.usuariogameList.clear();
		
		if (cursor.getCount() > 0){
			cursor.moveToFirst();
			do{
				UsuarioGame usuariogame = new UsuarioGame();
				usuariogame.setUsuarioId(cursor.getInt(cursor.getColumnIndex("usuario_id")));
				usuariogame.setGameId(cursor.getInt(cursor.getColumnIndex("game_id")));
				usuariogame.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
				this.usuariogameList.add(usuariogame);
			}while (cursor.moveToNext());
		}
		
		return this.usuariogameList;
	}
	
	public Integer getGameStatusbyUsuario(Game game, Usuario user) {
		String[] colunas = {"status"};
		String[] params = {Integer.toString(user.getId()), Integer.toString(game.getId())};
		Cursor cursor = this.banco.query(TABELA, colunas, "usuario_id = ? and game_id = ?", params, null, null, null);
		Integer status = null;
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				status = cursor.getInt(cursor.getColumnIndex("status"));
			} while (cursor.moveToNext());
		} else {
			return null;
		}
		
		return status;
	}
	
}






















