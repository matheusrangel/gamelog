package matheusrangel.gamelog;

import java.util.Calendar;

import matheusrangel.gamelog.dao.UsuarioDAO;
import matheusrangel.gamelog.fragments.JogosDesejados;
import matheusrangel.gamelog.fragments.JogosDesejados.OnDataPass2;
import matheusrangel.gamelog.fragments.JogosZerados;
import matheusrangel.gamelog.fragments.JogosZerados.OnDataPass;
import matheusrangel.gamelog.fragments.TodosJogos;
import matheusrangel.gamelog.fragments.TodosJogos.OnDataPass3;
import matheusrangel.gamelog.model.Usuario;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

@SuppressWarnings("deprecation")
public class MainActivity extends FragmentActivity implements OnDataPass, OnDataPass2, OnDataPass3 {

	private ListView listView;
	private UsuarioDAO usuarioDAO;
	private Usuario usuario;
	private SharedPreferences sessao;
	FragmentManager fm = getSupportFragmentManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab tabJogos = actionBar.newTab();
		tabJogos.setText("Jogos");
		tabJogos.setTabListener(new NavegacaoTabs(new TodosJogos()));
		actionBar.addTab(tabJogos);

		Tab tabZerados = actionBar.newTab();
		tabZerados.setText("Zerados");
		tabZerados.setTabListener(new NavegacaoTabs(new JogosZerados()));
		actionBar.addTab(tabZerados);

		Tab tabDesejados = actionBar.newTab();
		tabDesejados.setText("Quero Jogar");
		tabDesejados.setTabListener(new NavegacaoTabs(new JogosDesejados()));
		actionBar.addTab(tabDesejados);
		
		criarAlarme();
	}

	@Override
	public boolean onMenuItemSelected(int panel, MenuItem item) { //Listener dos botoes da ActionBar
		switch (item.getItemId()) {
		case R.id.share:
			Intent intentShare = new Intent(Intent.ACTION_SEND);
			intentShare.setType("text/plain");
			intentShare.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareApp));
			startActivity(Intent.createChooser(intentShare, getString(R.string.chooseApp)));
			break;

		case R.id.logoff:
			SharedPreferences sessao = getSharedPreferences("sessao", Context.MODE_PRIVATE);
			Editor editor = sessao.edit();
			editor.clear();
			editor.commit();
			moveTaskToBack(true);
			MainActivity.this.finish();
			break;
		}

		return true;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) { //Cria o SearchView e aplica um listener
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		searchView.setOnQueryTextListener(new SearchFilter());

		return true;
	}

	private class SearchFilter implements OnQueryTextListener { //Listener do SearchView
		
		@Override
		public boolean onQueryTextSubmit(String query) {
			return false;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			if (TextUtils.isEmpty(newText)) {
				listView.clearTextFilter();
			} else {
				listView.setFilterText(newText.toString());
			}
			return false;
		}
	}

	public Usuario getUsuarioAtual() { //Retorna o usuario que esta logado
		sessao = getSharedPreferences("sessao", Context.MODE_PRIVATE);
		if (sessao.contains("email") && sessao.contains("senha")) {
			usuarioDAO = new UsuarioDAO(MainActivity.this);
			usuario = usuarioDAO.findByEmail(sessao.getString("email", null));
		} else {
			return null;
		}

		return usuario;
	}

	private class NavegacaoTabs implements ActionBar.TabListener { //Navegacao das Tabs
		private Fragment fragment;

		public NavegacaoTabs(Fragment fragment) {
			this.fragment = fragment;
		}


		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
			fts.replace(R.id.fragmentContainer, fragment);
			fts.commit();
		}

		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			listView.clearTextFilter();
			FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
			fts.remove(fragment);
			fts.commit();
		}

		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
			// Ação que ocorrera quando houver click na tab que ja esta aberta.
		}

	}

	public void criarAlarme() { //Cria Alarme
		boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null);
		if (alarmeAtivo) {
			Intent intent = new Intent("ALARME_DISPARADO");
			PendingIntent pendIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

			Calendar tempo = Calendar.getInstance();
			tempo.setTimeInMillis(System.currentTimeMillis());
			tempo.add(Calendar.SECOND, 3);

			AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarme.setRepeating(AlarmManager.RTC_WAKEUP, tempo.getTimeInMillis(), 8000, pendIntent);
		}
	}

	@Override
	public void onDestroy() { //Cancelar o alarme quando a activity for destruida
		super.onDestroy();
		Intent intent = new Intent("ALARME_DISPARADO");
		PendingIntent pendIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
		AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarme.cancel(pendIntent);
	}

	@Override
	public void onDataPass(ListView listViewFragment) {
		listView = listViewFragment;
	}
}
