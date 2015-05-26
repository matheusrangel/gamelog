package matheusrangel.gamelog.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
	
	private static String dbName = "gamelog.sqlite";
	private static int versao = 1;

	public DB(Context context) {
		super(context, dbName, null, versao);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql_game = "CREATE TABLE game(id integer primary key autoincrement not null, titulo string, cover string, lancamento date, nota double); ";
		String sql_usuario = "CREATE TABLE usuario(id integer primary key autoincrement not null, nome string, email string, senha string);";
		String sql_usuario_game = "CREATE TABLE usuario_game(usuario_id integer not null, game_id integer not null, status integer, FOREIGN KEY(usuario_id) REFERENCES usuario(id), PRIMARY KEY(usuario_id, game_id), FOREIGN KEY(game_id) REFERENCES game(id));";
		
		
		String games = "INSERT INTO game(titulo, lancamento, nota) VALUES "
				+ "('Super Mario World', 1990-01-01, 0.0),"
				+ "('The Witcher 3', 2015-05-19, 0.0),"
				+ "('Half-Life 2', 2004-11-16, 0.0),"
				+ "('BioShock', 2007-08-21, 0.0),"
				+ "('Super Mario Kart', 1992-04-12, 0.0),"
				+ "('Resident Evil', 1996-03-22, 0.0);";
		
		String[] statements = {sql_game, sql_usuario, sql_usuario_game};
		for (String sql : statements) {
			db.execSQL(sql);
		}
		db.execSQL(games);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String drop_game = "DROP TABLE game;";
		String drop_usuario = "DROP TABLE usuario;";
		String drop_usuario_game = "DROP TABLE usuario_game;";
		
		String[] statements = {drop_game, drop_usuario, drop_usuario_game};
		for (String sql : statements) {
			db.execSQL(sql);
		}
		this.onCreate(db);
	}

}
