package matheusrangel.gamelog;

import matheusrangel.gamelog.dao.UsuarioDAO;
import matheusrangel.gamelog.model.Usuario;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends Activity {

	private EditText etNome, etEmail, etSenha;
	private Button btnSalvar;
	private UsuarioDAO usuarioDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(getString(R.string.title_activity_cadastro));
		actionBar.setDisplayHomeAsUpEnabled(true);

		this.etNome = (EditText) findViewById(R.id.etName);
		this.etEmail = (EditText) findViewById(R.id.etEmail);
		this.etSenha = (EditText) findViewById(R.id.etPassword);
		this.btnSalvar = (Button) findViewById(R.id.btnSignup);

		this.btnSalvar.setOnClickListener(new OnClickSalvar());
	}

	public class OnClickSalvar implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.equals(btnSalvar)) {
				usuarioDAO = new UsuarioDAO(getApplicationContext());
				
				Usuario usuario = new Usuario();
				usuario.setNome(etNome.getText().toString());
				usuario.setEmail(etEmail.getText().toString());
				usuario.setSenha(etSenha.getText().toString());
				
				if (usuarioDAO.findByEmail(usuario.getEmail()) != null) {
					Toast.makeText(getApplicationContext(), "Email em uso!", Toast.LENGTH_LONG).show();
				} else {
					usuarioDAO.insert(usuario);
					Toast.makeText(getApplicationContext(), "Cadastro efetuado!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(CadastroActivity.this, SigninActivity.class);
					CadastroActivity.this.startActivity(intent);
				}
			}
		}
	}
	
	@Override
	public boolean onMenuItemSelected(int panel, MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, SigninActivity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
			break;
		}
		return true;
	}
}
