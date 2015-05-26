package matheusrangel.gamelog;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class Broadcast extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		geraNotificacao(context, new Intent(context, MainActivity.class), "Opa!", "Funcionou!", "Que bom!");
	}

	public void geraNotificacao(Context context, Intent intent, String ticker, String titulo, String texto) {
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification.Builder builder = new Notification.Builder(context);
		PendingIntent pendIntent = PendingIntent.getActivity(context, 0, intent, 0);

		builder.setTicker(ticker);
		builder.setContentTitle(titulo);
		builder.setContentText(texto);
		builder.setSmallIcon(R.drawable.joystick);
		builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_like));
		builder.setContentIntent(pendIntent);
		
		Notification notification = builder.build();
		notification.vibrate = new long[]{150, 300, 150, 600};
		manager.notify(2, notification);
		Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone toque = RingtoneManager.getRingtone(context, som);
		toque.play();
	}
}


