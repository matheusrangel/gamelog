package matheusrangel.gamelog.fragments;

import java.util.ArrayList;
import java.util.List;

import matheusrangel.gamelog.MainActivity;
import matheusrangel.gamelog.R;
import matheusrangel.gamelog.dao.GameDAO;
import matheusrangel.gamelog.dao.UsuarioGameDAO;
import matheusrangel.gamelog.fragments.JogosZerados.OnDataPass;
import matheusrangel.gamelog.model.Game;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

@SuppressLint("InflateParams")
public class JogosDesejados extends Fragment {
	private ListView listDesejados;
	private GameDAO gameDAO;
	private UsuarioGameDAO usuarioGameDAO;
	private List<Game> gamesDesejados = new ArrayList<Game>();
	ArrayAdapter<Game> adapter;
	OnDataPass2 dataPasser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
		View view = inflater.inflate(R.layout.layout_desejados, null);
		listDesejados = (ListView) view.findViewById(R.id.listDesejados);
		
		gameDAO = new GameDAO(getActivity());
		gamesDesejados = gameDAO.findGamesDesejados(((MainActivity) getActivity()).getUsuarioAtual());
		if (gamesDesejados == null) {
			adapter = new ArrayAdapter<Game>(getActivity(), android.R.layout.simple_list_item_1);
		} else {
			adapter = new ArrayAdapter<Game>(getActivity(), android.R.layout.simple_list_item_1, gamesDesejados);
		}
		listDesejados.setAdapter(adapter);
		listDesejados.setTextFilterEnabled(true);
		listDesejados.setOnItemLongClickListener(new OnLongClickListItem());
		
		return listDesejados;
	}
	
	public class OnLongClickListItem implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			Game game = gamesDesejados.get(position);
			usuarioGameDAO = new UsuarioGameDAO(getActivity());
			usuarioGameDAO.remove(((MainActivity) getActivity()).getUsuarioAtual().getId(), game.getId());
			gamesDesejados.remove(position);
			adapter.notifyDataSetChanged();
			
			return false;
		}
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		passData(listDesejados);
	}
	
	public interface OnDataPass2 {
		public void onDataPass(ListView listView);
	}
	
	@Override
	public void onAttach(Activity a) {
		super.onAttach(a);
		dataPasser = (OnDataPass2) a;
	}
	
	public void passData(ListView listView) {
		dataPasser.onDataPass(listView);
	}
}
