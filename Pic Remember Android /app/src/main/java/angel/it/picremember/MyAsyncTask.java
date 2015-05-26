package angel.it.picremember;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout.LayoutParams;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

	Activity act;
	Integer[] hinh = { R.drawable.h2, R.drawable.h7x, R.drawable.h93x, R.drawable.h3, R.drawable.h6x, R.drawable.h91x, 
			R.drawable.h4, R.drawable.h5, R.drawable.h6, R.drawable.h7, R.drawable.h4x, R.drawable.h2x,
			R.drawable.h3x, R.drawable.h5x,R.drawable.h90,R.drawable.h90x,R.drawable.h1,
			R.drawable.h1x, R.drawable.h94x, R.drawable.h91, R.drawable.h92, R.drawable.h92x,
			R.drawable.h93, R.drawable.h94 };
	public MyAsyncTask(Activity Act)
	{
		act = Act;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		Toast.makeText(act, "Bắt đầu vẽ", Toast.LENGTH_SHORT).show();
	}


	@Override
	protected Void doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		for(int i=0;i<params[0];i++)
		{
			SystemClock.sleep(100);
			publishProgress(i);
		}
		return null;
	}
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		LinearLayout loVe = new LinearLayout(act);
		loVe.setOrientation(LinearLayout.VERTICAL);
		
		
		Button bt = new Button(act);
		
		LayoutParams pr = new LayoutParams();
		pr.width = 100;
		pr.height = 100;
			pr.gravity = Gravity.LEFT;
		
		bt.setText("Nút Thứ " + values[0]);
		ImageView img = new ImageView(act);
		img.setImageResource(hinh[values[0]]);
		img.setLayoutParams(pr);
		loVe.addView(img);
		LinearLayout prent;
		prent = (LinearLayout) act.findViewById(R.id.layoutPlayParent);
		prent.addView(loVe);
	}



	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Toast.makeText(act, "Vẽ thành công", Toast.LENGTH_SHORT).show();
	}

}
