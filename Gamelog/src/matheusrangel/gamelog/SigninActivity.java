package matheusrangel.gamelog;

import matheusrangel.gamelog.dao.UsuarioDAO;
import matheusrangel.gamelog.model.Usuario;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SigninActivity extends Activity {

	private EditText etEmail, etSenha;
	private Button btnSignin, btnSignup;
	private UsuarioDAO usuarioDAO;
	SharedPreferences sessao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		getActionBar().setTitle(R.string.title_activity_signin);

		this.etEmail = (EditText) findViewById(R.id.etLoginEmail);
		this.etSenha = (EditText) findViewById(R.id.etLoginPassword);
		this.btnSignin = (Button) findViewById(R.id.btnSignin);
		this.btnSignup = (Button) findViewById(R.id.btnRedirectSignup);

		this.btnSignin.setOnClickListener(new OnClickSignin());
		this.btnSignup.setOnClickListener(new OnClickSignup());
	}

	@Override
	protected void onResume() {
		sessao = getSharedPreferences("sessao", Context.MODE_PRIVATE);
		if (sessao.contains("email") && sessao.contains("senha")) {
			Intent intent = new Intent(SigninActivity.this, MainActivity.class);
			SigninActivity.this.startActivity(intent);
		}
		super.onResume();
	}

	public class OnClickSignin implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.equals(btnSignin)) {
				usuarioDAO = new UsuarioDAO(getApplicationContext());
				Usuario usuario = usuarioDAO.findByEmail(etEmail.getText().toString());
				if (usuario != null) {
					if (usuario.getSenha().equals(etSenha.getText().toString())) {
						Editor editor = sessao.edit();
						editor.putString("email", usuario.getEmail());
						editor.putString("senha", usuario.getSenha());
						editor.commit();
						Toast.makeText(SigninActivity.this, "Logado com sucesso!", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(SigninActivity.this, MainActivity.class);
						SigninActivity.this.startActivity(intent);
					} else {
						Toast.makeText(SigninActivity.this, "Senha Incorreta!", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(SigninActivity.this, "Email não encontrado", Toast.LENGTH_SHORT).show();
				}
			}
		}

	}

	public class OnClickSignup implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.equals(btnSignup)) {
				Intent intent = new Intent(SigninActivity.this, CadastroActivity.class);
				SigninActivity.this.startActivity(intent);
			}
		}

	}
}
