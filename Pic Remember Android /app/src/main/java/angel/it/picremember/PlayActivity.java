package angel.it.picremember;

import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class PlayActivity extends Activity {

	public static int Click = 0;
	public static int Time = 0;
	public static int imgCount = 0;
	double Score = 0;

	String Color = "Default";
	TextView tvTime, tvTimeTitle, tvClick, tvClickTitle;
	ProgressBar pbLoad;

	boolean isComplete = false, isPlay = true;

	Handler handler;
	Thread thTime;

	int Level = 12;
	int Type = 1;

	int imgHeight = 100;
	int imgWidth = 100;

	SQLiteDatabase db;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menuplay, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.itMenuQuit:
			finish();
			break;
		case R.id.itMenuPause:
			break;
		}
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actplay);
		SharedPreferences prfs = PreferenceManager
				.getDefaultSharedPreferences(PlayActivity.this);
		Level = prfs.getInt("Level", 12);
		Type = prfs.getInt("Type", 1);
		if(Type==1)
		{
		if (Level == 12)
			Time = 40;
		else if (Level == 24)
			Time = 80;
		else if (Level == 48)
			Time = 120;
		}
		else
		{
			Time=0;
		}
		Click = 0;
		imgCount = 0;
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvTimeTitle = (TextView) findViewById(R.id.tvTimeTitle);
		tvClick = (TextView) findViewById(R.id.tvClick);
		tvClickTitle = (TextView) findViewById(R.id.tvClickTitle);
		initData();

		initTheme();
		imgHeight = getWindowManager().getDefaultDisplay().getHeight();
		imgWidth = getWindowManager().getDefaultDisplay().getWidth();

		handler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				TextView tvClick = (TextView) findViewById(R.id.tvClick);
				tvClick.setText(Click + "");
				switch (msg.what) {
				case 0:
					TextView tvTime = (TextView) findViewById(R.id.tvTime);
					tvTime.setText(msg.arg1 + "");
					break;
				case 4:
					// game over
					 final Dialog dialogOver = new Dialog(PlayActivity.this);
					 dialogOver.setContentView(R.layout.gameoverdialog);
					 dialogOver.setTitle("Oh owssss");
					 dialogOver.show();
					 Button btOverReplay = (Button) dialogOver.findViewById(R.id.btGameOverReplay);
					 btOverReplay.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							finish();
							doReplay();
						}
					});
					break;
				case 3:
					final Dialog dialog = new Dialog(PlayActivity.this);
					dialog.setContentView(R.layout.scorealertdialogcustom);
					dialog.setTitle("Congratulation");
					TextView tvScore = (TextView) dialog
							.findViewById(R.id.tvScoreAlert);
					tvScore.setText("Your Score is " + Score);
					dialog.show();
					ImageView imgstar1 = (ImageView) dialog
							.findViewById(R.id.ivStar1);
					ImageView imgstar2 = (ImageView) dialog
							.findViewById(R.id.ivStar2);
					ImageView imgstar3 = (ImageView) dialog
							.findViewById(R.id.ivSad);
					int tmp = 0;
					if (Level == 12)
						tmp = 4000;
					else if (Level == 24)
						tmp = 6000;
					else if (Level == 48)
						tmp = 8000;
					if (Score > tmp) {
						imgstar1.setImageResource(R.drawable.ttar);
					}
					if (Score > tmp * 2) {
						imgstar2.setImageResource(R.drawable.ttar);
					}
					if (Score > tmp * 4) {
						imgstar3.setImageResource(R.drawable.ttar);
					}
					Button btSaveScore = (Button) dialog
							.findViewById(R.id.btSaveScoreAlert);
					btSaveScore.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							EditText etName = (EditText) dialog
									.findViewById(R.id.etUName);
							doSavecore(etName, Score, Level);
							finish();
						}
					});
					Button btReplay = (Button) dialog
							.findViewById(R.id.btGameOverReplay);
					btReplay.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							doReplay();
							finish();
						}
					});
					break;
				}
			}
		};

		doplay();

		thTime = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (isPlay) {
					Message msg = handler.obtainMessage();
					msg.arg1 = Time;
					msg.what = 0;
					handler.sendMessage(msg);
					if(Type==1)
						Time--;
					else
						Time++;
					if (imgCount == Level) {
						isPlay = false;
						isComplete = true;
					}
					if (Time == 0 && Type==1) {
						isPlay = false;
					}
					SystemClock.sleep(1000);
				}
				if (isPlay == false && Type==1) {
					Message msg1 = handler.obtainMessage();
					msg1.obj = "Game Over";
					msg1.what = 4;
					handler.sendMessage(msg1);
				}
				if (isComplete) {
					switch (Level) {
					case 12:
						Score = 10000 / Time * Click;
						break;
					case 24:
						Score = 20000 / Time * Click;
						break;
					case 48:
						Score = 30000 / Time * Click;
						break;
					}
					Message msg = handler.obtainMessage();
					msg.obj = Score;
					msg.what = 3;
					handler.sendMessage(msg);
				}
			}
		});
		thTime.start();
	}

	private void initTheme() {
		SharedPreferences prfs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Color = prfs.getString("Color", "Default");
		if (Color.contains("Default"))
			changeToDefault();
		if (Color.contains("Green"))
			changeToGreen();
		if (Color.contains("Blue"))
			changeToBlue();
	}

	private void changeToGreen() {
		// TODO Auto-generated method stub
		LinearLayout lo = (LinearLayout) findViewById(R.id.layoutPlayParent);
		lo.setBackgroundColor(getResources().getColor(R.color.DarkColor));

		TextView[] tvs = { tvTime, tvTimeTitle, tvClick, tvClickTitle };
		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setTextColor(getResources().getColor(R.color.RedColor));
		}

	}

	private void changeToBlue() {
		// TODO Auto-generated method stub
		LinearLayout lo = (LinearLayout) findViewById(R.id.layoutPlayParent);
		lo.setBackgroundColor(getResources().getColor(R.color.DarkColor));

		TextView[] tvs = { tvTime, tvTimeTitle, tvClick, tvClickTitle };
		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setTextColor(getResources().getColor(R.color.btColorBlue));
		}
	}

	private void changeToDefault() {
		LinearLayout lo = (LinearLayout) findViewById(R.id.layoutPlayParent);
		lo.setBackgroundColor(getResources().getColor(android.R.color.background_light));

		TextView[] tvs = { tvTime, tvTimeTitle, tvClick, tvClickTitle };
		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setTextColor(getResources().getColor(R.color.DarkColor));
		}
	}

	private void doSavecore(EditText etName, double score, int level) {
		ContentValues values = new ContentValues();
		values.put("UserName", etName.getText().toString());
		values.put("Level", level);
		values.put("Score", score);
		if (db.insert("HighScore", null, values) == -1) {
			Toast.makeText(PlayActivity.this, "fail save", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(PlayActivity.this, "done!", Toast.LENGTH_SHORT)
					.show();
		}
		finish();
	}

	private void doplay() {
		asyncDrawImage asyncImage = new asyncDrawImage(PlayActivity.this);
		asyncImage.execute(6, imgHeight, imgWidth);
	}

	private void doReplay() {
		// TODO Auto-generated method stub
		finish();
	}

	public boolean isTableExists(SQLiteDatabase database, String tableName) {
		Cursor cursor = database.rawQuery(
				"select DISTINCT tbl_name from sqlite_master where tbl_name = '"
						+ tableName + "'", null);
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				cursor.close();
				return true;
			}
			cursor.close();
		}
		return false;
	}

	private void initData() {
		try {
			db = openOrCreateDatabase("picremember.db",
					SQLiteDatabase.CREATE_IF_NECESSARY, null);
			if (db != null) {
				if (isTableExists(db, "HighScore") == false) {
					Toast.makeText(this, "Creating table for first time", Toast.LENGTH_LONG).show();
					db.setVersion(1);
					db.setLocale(Locale.getDefault());
					String HighScore = "create table HighScore ("
							+ "id integer primary key autoincrement,"
							+ "UserName text, " + "Level integer, "
							+ "Score integer)";
					db.execSQL(HighScore);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}

	}

}
