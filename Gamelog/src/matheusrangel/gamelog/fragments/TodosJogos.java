package matheusrangel.gamelog.fragments;

import java.util.List;

import matheusrangel.gamelog.MainActivity;
import matheusrangel.gamelog.R;
import matheusrangel.gamelog.dao.GameDAO;
import matheusrangel.gamelog.dao.UsuarioGameDAO;
import matheusrangel.gamelog.fragments.JogosZerados.OnDataPass;
import matheusrangel.gamelog.model.Game;
import matheusrangel.gamelog.model.Usuario;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("InflateParams")
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class TodosJogos extends Fragment {
	private ListView listJogos;
	private List<Game> games;
	private GameDAO gameDAO;
	private Game game;
	private ArrayAdapter<String> adapterOptions;
	private AlertDialog alertdialog;
	private UsuarioGameDAO usuarioGameDAO;
	OnDataPass3 dataPasser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
		View view = inflater.inflate(R.layout.layout_jogos, null);
		listJogos = (ListView) view.findViewById(R.id.listJogos);
		gameDAO = new GameDAO(getActivity());
		games = gameDAO.findAll();
		ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(getActivity(), android.R.layout.simple_list_item_1, games);
		listJogos.setAdapter(adapter);
		listJogos.setTextFilterEnabled(true);
		listJogos.setOnItemClickListener(new OnClickListItem()); //Aplica um listener para a lista de jogos
		
		return listJogos;
	}
	

	public class OnClickListItem implements OnItemClickListener { //Listener dos itens da lista geral de jogos, cria um alertdialog com as opcoes
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			game = games.get(position);
			usuarioGameDAO = new UsuarioGameDAO(getActivity());
			if (usuarioGameDAO.getGameStatusbyUsuario(game, ((MainActivity) getActivity()).getUsuarioAtual()) == null) {
				String[] options = {"Zerei!", "Quero Jogar!"};
				
				adapterOptions = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, options);
				ListView optionsList = new ListView(getActivity());
				optionsList.setAdapter(adapterOptions);

				AlertDialog.Builder janela = new AlertDialog.Builder(getActivity());
				janela.setTitle(game.getTitulo());
				janela.setView(optionsList);
				alertdialog = janela.create();
				alertdialog.show();
				optionsList.setOnItemClickListener(new OnClickAlertDialogItem());
			}
		}
	}

	public class OnClickAlertDialogItem implements OnItemClickListener { //Listener do alertdialog  e implementa notificação
		
		NotificationManager manager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
		Notification.Builder builder = new Notification.Builder(getActivity());
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			int zerei = 0, queroJogar = 1;
			Usuario usuario = ((MainActivity) getActivity()).getUsuarioAtual();
			if (adapterOptions.getItemId(position) == zerei) {
				builder.setTicker("Parabéns!");
				builder.setContentTitle("Você zerou " + game.getTitulo());
				builder.setContentText("Que bom!");
				builder.setSmallIcon(R.drawable.joystick);
				builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_like));
				usuarioGameDAO.insert(usuario, game, zerei);
			} else if (adapterOptions.getItemId(position) == queroJogar) {
				builder.setTicker("Adicionado!");
				builder.setContentTitle("Você deseja jogar " + game.getTitulo());
				builder.setContentText(game.getTitulo() + " será adicionado a sua lista de desejos.");
				builder.setSmallIcon(R.drawable.joystick);
				builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_heart));
				usuarioGameDAO.insert(usuario, game, queroJogar);
			}
			Notification notification = builder.build();
			notification.vibrate = new long[]{150, 300, 150, 600};
			manager.notify(1, notification);
			Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone toque = RingtoneManager.getRingtone(getActivity(), som);
			toque.play();
			alertdialog.dismiss();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		passData(listJogos);
	}
	
	public interface OnDataPass3 {
		public void onDataPass(ListView listView);
	}
	
	@Override
	public void onAttach(Activity a) {
		super.onAttach(a);
		dataPasser = (OnDataPass3) a;
	}
	
	public void passData(ListView listView) {
		dataPasser.onDataPass(listView);
	}

	
}
