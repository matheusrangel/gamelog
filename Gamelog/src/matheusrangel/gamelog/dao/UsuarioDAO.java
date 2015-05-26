package matheusrangel.gamelog.dao;

import java.util.ArrayList;
import java.util.List;
import matheusrangel.gamelog.model.Usuario;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {
	private SQLiteDatabase banco;
	private static final String TABELA = "usuario";
	private List<Usuario> usuarios;
	
	public UsuarioDAO(Context context) {
		this.banco = new DB(context).getWritableDatabase();
		this.usuarios = new ArrayList<Usuario>();
	}
	
	public void insert(Usuario user){
		ContentValues cv = new ContentValues();
		cv.put("nome", user.getNome());
		cv.put("email", user.getEmail());
		cv.put("senha", user.getSenha());
	
		this.banco.insert(TABELA, null, cv);
	}
	
	public void remove(int id) {
		this.banco.delete(TABELA, "id = ?", new String[]{Integer.toString(id)});
	}
	
	public List<Usuario> findAll(){
		String[] colunas = {"id", "nome", "email", "senha"};
		Cursor cursor = this.banco.query(TABELA, colunas, null, null, null, null, null);
		this.usuarios.clear();
		
		if (cursor.getCount() > 0){
			cursor.moveToFirst();
			do{
				Usuario user = new Usuario();
				user.setId(cursor.getInt(cursor.getColumnIndex("id")));
				user.setNome(cursor.getString(cursor.getColumnIndex("nome")));
				user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
				user.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
				this.usuarios.add(user);
			}while (cursor.moveToNext());
		}
		
		return this.usuarios;
	}
	
	public Usuario findByEmail(String email) {
		String[] param = {email};
		Cursor cursor = this.banco.query(TABELA, null, "email = ?", param, null, null, null);
		this.usuarios.clear();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				Usuario user = new Usuario();
				user.setId(cursor.getInt(cursor.getColumnIndex("id")));
				user.setNome(cursor.getString(cursor.getColumnIndex("nome")));
				user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
				user.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
				this.usuarios.add(user);
			} while (cursor.moveToNext());
		} else {
			return null;
		}
		
		return usuarios.get(0);
	}
	
	
}
