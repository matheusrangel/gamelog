package matheusrangel.gamelog.dao;

import java.util.ArrayList;
import java.util.List;

import matheusrangel.gamelog.MainActivity;
import matheusrangel.gamelog.model.Game;
import matheusrangel.gamelog.model.Usuario;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GameDAO {
	private SQLiteDatabase banco;
	private static final String TABELA = "game";
	private List<Game> games;
	
	public GameDAO(Context context) {
		this.banco = new DB(context).getWritableDatabase();
		this.games = new ArrayList<Game>();
	}
	
	public void insert(Game game) {
		ContentValues cv = new ContentValues();
		cv.put("titulo", game.getTitulo());
		cv.put("lancamento", game.getLancamento().getTimeInMillis());
		cv.put("nota", game.getNota());
		cv.put("cover", game.getCover());
		
		this.banco.insert(TABELA, null, cv);
	}
	
	public void remove(int id) {
		this.banco.delete(TABELA, "id = ?", new String[]{Integer.toString(id)});
	}
	
	public List<Game> findAll() {
		String[] colunas = {"id", "titulo", "lancamento", "nota", "cover"};
		Cursor cursor = this.banco.query(TABELA, colunas, null, null, null, null, null);
		this.games.clear();
		
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				Game game = new Game();
				game.setId(cursor.getInt(cursor.getColumnIndex("id")));
				game.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
				game.setLancamento(cursor.getLong(cursor.getColumnIndex("lancamento")));
				game.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
				game.setCover(cursor.getString(cursor.getColumnIndex("cover")));
				games.add(game);
			} while (cursor.moveToNext());
		} else {
			return null;
		}
		
		return this.games;
	}
	
	public Game findById(int id) {
		String[] param = {Integer.toString(id)};
		Cursor cursor = this.banco.query(TABELA, null, "id = ?", param, null, null, null);
		this.games.clear();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				Game game = new Game();
				game.setId(cursor.getInt(cursor.getColumnIndex("id")));
				game.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
				game.setLancamento(cursor.getLong(cursor.getColumnIndex("lancamento")));
				game.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
				game.setCover(cursor.getString(cursor.getColumnIndex("cover")));
				games.add(game);
			} while (cursor.moveToNext());
		} else {
			return null;
		}
		
		return this.games.get(0);
	}
	
	public List<Game> findGamesZerados(Usuario user) {
		String[] colunas = {"game_id"};
		String[] params = {Integer.toString(user.getId())};
		Cursor cursor = this.banco.query("usuario_game", colunas, "usuario_id = ? and status = 0", params, null, null, null);
		List<Game> gamesZerados = new ArrayList<Game>();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				Game game = new Game();
				game = findById((cursor.getInt(cursor.getColumnIndex("game_id"))));
				gamesZerados.add(game);
			} while (cursor.moveToNext());
		} else {
			return null;
		}
		
		return gamesZerados;
	}
	
	public List<Game> findGamesDesejados(Usuario user) {
		String[] colunas = {"game_id"};
		String[] params = {Integer.toString(user.getId())};
		Cursor cursor = this.banco.query("usuario_game", colunas, "usuario_id = ? and status = 1", params, null, null, null);
		List<Game> gamesDesejados = new ArrayList<Game>();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				Game game = new Game();
				game = findById((cursor.getInt(cursor.getColumnIndex("game_id"))));
				gamesDesejados.add(game);
			} while (cursor.moveToNext());
		} else {
			return null;
		}
		
		return gamesDesejados;
	}
	
	
}
