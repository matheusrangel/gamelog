package matheusrangel.gamelog.fragments;


import java.util.ArrayList;
import java.util.List;

import matheusrangel.gamelog.MainActivity;
import matheusrangel.gamelog.R;
import matheusrangel.gamelog.dao.GameDAO;
import matheusrangel.gamelog.dao.UsuarioGameDAO;
import matheusrangel.gamelog.model.Game;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("InflateParams")
public class JogosZerados extends Fragment {
	private ListView listZerados;
	private GameDAO gameDAO;
	private UsuarioGameDAO usuarioGameDAO;
	private List<Game> gamesZerados = new ArrayList<Game>();
	ArrayAdapter<Game> adapter;
	OnDataPass dataPasser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
		View view = inflater.inflate(R.layout.layout_zerados, null);
		listZerados = (ListView) view.findViewById(R.id.listZerados);
		
		gameDAO = new GameDAO(getActivity());
		gamesZerados = gameDAO.findGamesZerados(((MainActivity) getActivity()).getUsuarioAtual());
		if (gamesZerados ==  null) {
			adapter = new ArrayAdapter<Game>(getActivity(), android.R.layout.simple_list_item_1);
		} else {
			adapter = new ArrayAdapter<Game>(getActivity(), android.R.layout.simple_list_item_1, gamesZerados);
		}
		listZerados.setAdapter(adapter);
		listZerados.setTextFilterEnabled(true);
		listZerados.setOnItemLongClickListener(new OnLongClickListItem());
		
		return listZerados;
	}
	
	public class OnLongClickListItem implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			Game game = gamesZerados.get(position);
			usuarioGameDAO = new UsuarioGameDAO(getActivity());
			usuarioGameDAO.remove(((MainActivity) getActivity()).getUsuarioAtual().getId(), game.getId());
			gamesZerados.remove(position);
			adapter.notifyDataSetChanged();
			
			return false;
		}
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		passData(listZerados);
	}
	
	public interface OnDataPass {
		public void onDataPass(ListView listView);
	}
	
	@Override
	public void onAttach(Activity a) {
		super.onAttach(a);
		dataPasser = (OnDataPass) a;
	}
	
	public void passData(ListView listView) {
		dataPasser.onDataPass(listView);
	}
	
}
