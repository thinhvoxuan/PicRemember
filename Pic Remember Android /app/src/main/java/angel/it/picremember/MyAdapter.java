package angel.it.picremember;

import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class MyAdapter extends ArrayAdapter<HighScoreData>{

	private Activity context;
	private int layout;
	private List<HighScoreData>list;
	Integer[] rate = { R.drawable.s1, R.drawable.s2, R.drawable.s3,
			R.drawable.s4, R.drawable.s5, R.drawable.s6, R.drawable.s7,
			R.drawable.s8, R.drawable.s9,};
	public MyAdapter(Context context, int resource,
			List<HighScoreData> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context=(Activity) context;
		this.list=objects;
		this.layout=resource;
		
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater flater =context.getLayoutInflater();
		
		View row = flater.inflate(layout, parent,false);
		ImageView rateimg= (ImageView) row.findViewById(R.id.imageView1);
		//
//		TabHost tab = (TabHost)row.findViewById(android.R.id.tabhost);
//		Toast.makeText(context,tab.getCurrentTab()+"",Toast.LENGTH_LONG).show();
//		SharedPreferences prfs = PreferenceManager
//				.getDefaultSharedPreferences(context);
//		Level = prfs.getInt("Level", 12);
		TextView txtName;
		TextView txtScore;
		HighScoreData data;
		//Toast.makeText(context, Level+ "", Toast.LENGTH_LONG).show();
		switch (HighScoreActivity.Level) {
		case 12:
			txtName=(TextView) row.findViewById(R.id.txtName12);
			txtScore=(TextView) row.findViewById(R.id.txtScore12);
			
			 data=list.get(position);
			//txt1.setText(data.getId()+"");
			 txtName.setText(data.getUsername());
			//txt3.setText(data.getLevel()+"");
			 txtScore.setText(data.getScore()+"");
			rateimg.setImageResource(rate[position]);
			break;
		case 24:
			txtName=(TextView) row.findViewById(R.id.txtName24);
			txtScore=(TextView) row.findViewById(R.id.txtScore24);
			
			 data=list.get(position);
			//txt1.setText(data.getId()+"");
			 txtName.setText(data.getUsername());
			//txt3.setText(data.getLevel()+"");
			txtScore.setText(data.getScore()+"");
			rateimg.setImageResource(rate[position]);
			
			break;
		case 48:
			txtName=(TextView) row.findViewById(R.id.txtName48);
			txtScore=(TextView) row.findViewById(R.id.txtScore48);
			
			 data=list.get(position);
			//txt1.setText(data.getId()+"");
			 txtName.setText(data.getUsername());
			//txt3.setText(data.getLevel()+"");
			txtScore.setText(data.getScore()+"");
			rateimg.setImageResource(rate[position]);
			break;

		default:
			break;
		}
		return row;
		
		
	}

	


	
	

}
