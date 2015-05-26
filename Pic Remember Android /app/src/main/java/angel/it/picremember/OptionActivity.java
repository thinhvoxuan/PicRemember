package angel.it.picremember;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.style.BulletSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class OptionActivity extends Activity {

	Button btSave, btBack;
	RadioGroup radgrlv, radgrCl, radgrType;
	RadioButton radEasy, radNormal, radHard, radDf, radGr, radBl, radTime,
			radVs;

	CheckBox sClick, sConrect;
	int Level = 12;
	String Color = "Default";
	int Type = 1; // 1 is Time count - 2 is Vs

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actoption);

		radEasy = (RadioButton) findViewById(R.id.rdEasy);
		radNormal = (RadioButton) findViewById(R.id.radNormal);
		radHard = (RadioButton) findViewById(R.id.rdHard);

		radDf = (RadioButton) findViewById(R.id.rdDefault);
		radGr = (RadioButton) findViewById(R.id.rdGreen);
		radBl = (RadioButton) findViewById(R.id.rdBlue);

		radTime = (RadioButton) findViewById(R.id.rdTime);
		radVs = (RadioButton) findViewById(R.id.radVs);

		sClick = (CheckBox) findViewById(R.id.cbSoundClick);
		sConrect = (CheckBox) findViewById(R.id.cbSoundConrect);

		btSave = (Button) findViewById(R.id.btOptionSave);
		btBack = (Button) findViewById(R.id.btOptionBack);

		radgrType = (RadioGroup) findViewById(R.id.radgrType);
		radgrType.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rdTime:
					Type = 1;
					break;
				case R.id.radVs:
					Type = 2;
					break;
				}
			}
		});

		radgrlv = (RadioGroup) findViewById(R.id.radgrLevel);

		radgrlv.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rdEasy:
					Level = 12;
					break;
				case R.id.radNormal:
					Level = 24;
					break;
				case R.id.rdHard:
					Level = 48;
					break;
				}
			}
		});

		radgrCl = (RadioGroup) findViewById(R.id.radgrColor);

		radgrCl.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rdDefault:
					Color = "Default";
					break;
				case R.id.rdGreen:
					Color = "Green";
					break;
				case R.id.rdBlue:
					Color = "Blue";
					break;
				}
			}
		});
		btBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences.Editor etprfs = PreferenceManager
						.getDefaultSharedPreferences(OptionActivity.this)
						.edit();
				etprfs.putInt("Level", Level);
				etprfs.putInt("Type", Type);
				etprfs.putString("Color", Color);
				etprfs.putBoolean("soundClick", sClick.isChecked());
				etprfs.putBoolean("soundConrect", sConrect.isChecked());
				etprfs.commit();
				Toast.makeText(OptionActivity.this, "Done!!", Toast.LENGTH_LONG)
						.show();
				Intent itrs = new Intent();
				itrs.putExtra("result", Color);
				setResult(RESULT_OK, itrs);
				finish();
			}
		});
		init();
		initTheme();
	}

	private void init() {
		SharedPreferences prfs = PreferenceManager
				.getDefaultSharedPreferences(this);
		// Load level
		Level = prfs.getInt("Level", 12);
		Type = prfs.getInt("Type", 1);
		switch (Level) {
		case 12:
			radEasy.setChecked(true);
			break;
		case 24:
			radNormal.setChecked(true);
			break;
		case 48:
			radHard.setChecked(true);
			break;
		}
		// Type
		switch (Type) {
		case 1:
			radTime.setChecked(true);
			break;
		case 2:
			radVs.setChecked(true);
			break;
		}
		// Load Color
		Color = prfs.getString("Color", "Default");
		if (Color.contains("Default"))
			radDf.setChecked(true);
		if (Color.contains("Green"))
			radGr.setChecked(true);
		if (Color.contains("Blue"))
			radBl.setChecked(true);

		// load sound
		sClick.setChecked(prfs.getBoolean("soundClick", true));
		sConrect.setChecked(prfs.getBoolean("soundConrect", true));
	}

	private void initTheme() {
		SharedPreferences prfs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Color = prfs.getString("Color", "Default");
		// Toast.makeText(this,"c"+ Color+"c", Toast.LENGTH_LONG).show();

		if (Color.contains("Default"))
			changeToDefault();
		if (Color.contains("Blue"))
			changeToBlue();
		if (Color.contains("Green"))
			changeToGreen();
	}

	protected void changeToDefault() {
		// background color
		LinearLayout lo = (LinearLayout) findViewById(R.id.loOptionMain);
		lo.setBackgroundColor(getResources().getColor(
				android.R.color.background_light));

	}

	protected void changeToBlue() {
		// background color

	}

	private void changeToGreen() {
		LinearLayout lo = (LinearLayout) findViewById(R.id.loOptionMain);
		lo.setBackgroundColor(getResources().getColor(R.color.DarkColor));
	}
}
