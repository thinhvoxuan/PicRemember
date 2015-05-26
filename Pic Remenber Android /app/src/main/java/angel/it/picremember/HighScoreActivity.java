package angel.it.picremember;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.text.TextUtilsCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;


public class HighScoreActivity extends Activity {
	SQLiteDatabase db = null;
	TabHost tab;
	ListView lv;
	List<HighScoreData> list;
	MyAdapter adapter;
	public static int Level=12;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acthighscore);
		loadTab();
		initData();
		getData();
		
		
		
	}
	private void getData() {
		// TODO Auto-generated method stub
		if(Level==12)
			lv=(ListView) findViewById(R.id.listView12);
		if(Level==24)
			lv=(ListView) findViewById(R.id.listView24);
		if(Level==48)
			lv=(ListView) findViewById(R.id.listview48);
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
//				TextView tv = (TextView) arg1.findViewById(R.id.txtName12);
//				Toast.makeText(HighScoreActivity.this, tv.getText()+"", Toast.LENGTH_SHORT).show();
				
				return false;
			}
		});
		if(db!=null)
		{
			String colum[]=new String[]{"id" ,"UserName", "Level","Score"};
			Cursor cursor=db.query("HighScore",colum, "Level ="+Level, null, "id", null, "Score DESC");
			//Cursor cursor=db.startManagingCursor(cursor);
			list= new ArrayList<HighScoreData>();
			
			cursor.moveToFirst();
			while(!cursor.isAfterLast())
			{
				HighScoreData data=new HighScoreData();
				data.setId(cursor.getInt(0));
				data.setUsername(cursor.getString(1));
				data.setLevel(cursor.getInt(2));
				data.setScore(cursor.getInt(3));
				list.add(data);
				cursor.moveToNext();
			}
			
			cursor.close();
			SharedPreferences prfs = PreferenceManager
					.getDefaultSharedPreferences(HighScoreActivity.this);
			Level = prfs.getInt("Level", 12);
			switch (Level) {
			case 12:
				adapter=new MyAdapter(HighScoreActivity.this, R.layout.layout_custom12, list);
				lv.setAdapter(adapter);
				break;
			case 24:
				adapter=new MyAdapter(HighScoreActivity.this, R.layout.layout_custom24, list);
				lv.setAdapter(adapter);
				break;
			case 48:
				adapter=new MyAdapter(HighScoreActivity.this, R.layout.layout_custom48, list);
				lv.setAdapter(adapter);
				break;

			default:
				break;
			}
			
		}
	}

	private void loadTab() {
		tab = (TabHost) findViewById(android.R.id.tabhost);
		tab.setup();

		TabHost.TabSpec tsp;

		tsp = tab.newTabSpec("t1");
		tsp.setContent(R.id.tab1);
		tsp.setIndicator("Easy");
		
		tab.addTab(tsp);

		tsp = tab.newTabSpec("t2");
		tsp.setContent(R.id.tab2);
		tsp.setIndicator("Normal");
		tab.addTab(tsp);
		tsp = tab.newTabSpec("t3");
		tsp.setContent(R.id.tab3);
		tsp.setIndicator("Hard");
		tab.addTab(tsp);
		tab.setCurrentTab(0);
		tab.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if(tabId.contains("t1"))
				{ 	
					Level=12;
					getData();
				}
				if(tabId.contains("t2"))
				{
					Level=24;
					getData();
				}
				if(tabId.contains("t3"))
				{
					Level=48;
					getData();
				}
			}
		});
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
				if (isTableExists(db, "HighScore")==false) {
					db.setVersion(1);
					db.setLocale(Locale.getDefault());
					String HighScore = "create table HighScore ("
							+ "id integer primary key autoincrement,"
							+ "UserName text, " + "Level integer, "
							+ "Score integer)";
					db.execSQL(HighScore);
					Toast.makeText(HighScoreActivity.this, "OK OK",
							Toast.LENGTH_LONG).show();
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}

	}

}
