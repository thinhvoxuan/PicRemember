package angel.it.picremember;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TableLayout.LayoutParams;


public class MainActivity extends Activity {

	Button btStart, btOption, btHighScore, btQuit;
	
	String Color = "Default";
	
	Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		initTheme();
	}
	private void init() {
		btStart = (Button) findViewById(R.id.btStart);
		btStart.setOnClickListener(new myEvent());

		btOption = (Button) findViewById(R.id.btOption);
		btOption.setOnClickListener(new myEvent());

		btHighScore = (Button) findViewById(R.id.btHighScore);
		btHighScore.setOnClickListener(new myEvent());

		btQuit = (Button) findViewById(R.id.btExit);
		btQuit.setOnClickListener(new myEvent());
		
	}
	private void initTheme() {
		SharedPreferences prfs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Color = prfs.getString("Color", "Default");
		if (Color.contains("Default"))
			changeToDefault();
		if (Color.contains("Blue"))
			changeToBlue();
		if (Color.contains("Green"))
			changeToGreen();
	}

	protected void changeToDefault() {
		// bt color
		Button[] bts = {btStart, btOption, btHighScore, btQuit };
		for (int i = 0; i < bts.length; i++) {
			bts[i].setBackgroundColor(getResources().getColor(
					R.color.btColorDefault));
		}
		// background color
		LinearLayout lo = (LinearLayout) findViewById(R.id.loMain);
		lo.setBackgroundColor(getResources().getColor(android.R.color.background_light));
	}

	protected void changeToBlue() {
		Button[] bts = { btStart, btOption, btHighScore, btQuit };
		// bt color
		for (int i = 0; i < bts.length; i++) {
			bts[i].setBackgroundColor(getResources().getColor(
					R.color.btColorBlue));
		}
		// background color
		LinearLayout lo = (LinearLayout) findViewById(R.id.loMain);
		lo.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
	}

	private void changeToGreen() {
		Button[] bts = { btStart, btOption, btHighScore, btQuit };
		for (int i = 0; i < bts.length; i++) {
			bts[i].setBackgroundColor(getResources().getColor(
					R.color.btColorGreen));
		}
		LinearLayout lo = (LinearLayout) findViewById(R.id.loMain);
		lo.setBackgroundColor(getResources().getColor(R.color.DarkColor));
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				String result = data.getStringExtra("result");
				// Toast.makeText(this, "result "+ result,
				// Toast.LENGTH_LONG).show();
				if (result.contains("Default"))
					changeToDefault();
				if (result.contains("Blue"))
					changeToBlue();
				if (result.contains("Green"))
					changeToGreen();
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.VietNamlang:
			Toast.makeText(MainActivity.this, "Chức năng đã bị tắt bởi A.iT", Toast.LENGTH_LONG).show();
			//setLocale("vi");
			break;
		case R.id.Englishlang:
			setLocale("en");
			break;
		}
		return true;
	}

	class myEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent;
			switch (v.getId()) {
			case R.id.btStart:
				intent = new Intent(MainActivity.this, PlayActivity.class);
				startActivity(intent);
				break;
			case R.id.btHighScore:
				intent = new Intent(MainActivity.this, HighScoreActivity.class);
				startActivity(intent);
				break;
			case R.id.btOption:
				intent = new Intent(MainActivity.this, OptionActivity.class);
				startActivityForResult(intent, 1);
				break;
			case R.id.btExit:
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("Notice");
				builder.setMessage("Do you want to exit application?");
				builder.setPositiveButton("Yes yes yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
								System.exit(0);
							}

						});
				builder.setNegativeButton("No  no no",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}

						});
				builder.create().show();
				break;
			}
		}

	}
	public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }
}
