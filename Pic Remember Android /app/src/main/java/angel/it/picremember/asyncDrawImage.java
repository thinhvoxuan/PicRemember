package angel.it.picremember;

import java.util.Random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class asyncDrawImage extends AsyncTask<Integer, Integer[], Void> {

	Activity act;
	int Level = 12, imgInRow = 4;
	boolean isClick = false, start = true, isComplete = false;
	int Time = 0;
	int imgWidth = 100, imgHeight = 100;
	ImageView imgLastClick;
	boolean soundClick = true, soundConrect = true;
	Integer[] hinh = { R.drawable.h2, R.drawable.h7x, R.drawable.h93x, R.drawable.h3, R.drawable.h6x, R.drawable.h91x, 
			R.drawable.h4, R.drawable.h5, R.drawable.h6, R.drawable.h7, R.drawable.h4x, R.drawable.h2x,
			R.drawable.h3x, R.drawable.h5x,R.drawable.h90,R.drawable.h90x,R.drawable.h1,
			R.drawable.h1x, R.drawable.h94x, R.drawable.h91, R.drawable.h92, R.drawable.h92x,
			R.drawable.h93, R.drawable.h94 };

	public asyncDrawImage(Activity Act) {
		act = Act;
	}

	@Override
	protected Void doInBackground(Integer... params) {
		try {
			SharedPreferences prfs = PreferenceManager
					.getDefaultSharedPreferences(act);
			Level = prfs.getInt("Level", 12);
			soundClick = prfs.getBoolean("soundClick", true);
			soundConrect = prfs.getBoolean("soundConrect", true);
			if (Level == 12)
				imgInRow = 4;
			if (Level == 24)
				imgInRow = 6;
			if (Level == 48)
				imgInRow = 8;
			imgHeight = (params[1] - 100) / imgInRow;
			imgWidth = (params[2] - 20) / imgInRow;
		} catch (Exception ex) {
			Log.d("Error background", ex.toString());
		}
		Integer[] in = new Integer[Level];
		Integer[] inXH = new Integer[Level];
		for (int i = 0; i < Level; i++)
			inXH[i] = 0;
		int row = Level / imgInRow;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < imgInRow; j++) {
				in[i * imgInRow + j] = new Random().nextInt(Level / 2);
				while (inXH[in[i * imgInRow + j]] > 1)
					in[i * imgInRow + j] = new Random().nextInt(Level / 2);
				inXH[in[i * imgInRow + j]]++;
			}
		}
		publishProgress(in);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Toast.makeText(act, "Let's Start...", Toast.LENGTH_SHORT).show();

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer[]... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		LinearLayout layout = null;
		LayoutParams primg = new LayoutParams();
		primg.width = imgWidth;
		primg.height = imgHeight;
		primg.leftMargin = 2;
		primg.rightMargin = 2;
		LayoutParams prlo = new LayoutParams();
		prlo.gravity = Gravity.CENTER;
		for (int i = 0; i < values[0].length; i++) {
			if (i < imgInRow)
				layout = (LinearLayout) act.findViewById(R.id.layoutImage1);
			else if (i < imgInRow * 2 && i >= imgInRow)
				layout = (LinearLayout) act.findViewById(R.id.layoutImage2);
			else if (i < imgInRow * 3 && i >= imgInRow * 2)
				layout = (LinearLayout) act.findViewById(R.id.layoutImage3);
			else if (i < imgInRow * 4 && i >= imgInRow * 3)
				layout = (LinearLayout) act.findViewById(R.id.layoutImage4);
			else if (i < imgInRow * 5 && i >= imgInRow * 4)
				layout = (LinearLayout) act.findViewById(R.id.layoutImage5);
			else if (i >= imgInRow * 5)
				layout = (LinearLayout) act.findViewById(R.id.layoutImage6);
			layout.setLayoutParams(prlo);
			ImageView img = new ImageView(act);
			img.setLayoutParams(primg);
			img.setId(hinh[i]);
			img.setImageResource(R.drawable.hdefault);
			img.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					PlayActivity.Click++;
					if (soundClick) {
						MediaPlayer sClick = MediaPlayer.create(act, R.raw.pop);
						sClick.start();
					}
					ImageView imgv = ((ImageView) v);
					imgv.setImageResource(imgv.getId());

					if (start) {
						imgLastClick = imgv;
						start = false;
					} else {
						try
						{
						if ((imgv.getId()== imgLastClick.getId()+1) 
								|| (imgv.getId()== imgLastClick.getId()-1)) {
							imgv.setImageResource(imgv.getId());
							// play sound
							if (soundConrect) {
								MediaPlayer sConrect = MediaPlayer.create(act,
										R.raw.yahoo);
								sConrect.start();
							}
							// Animation
							Animation[] anim = new Animation[10];
							anim[0] = AnimationUtils.loadAnimation(act,
									R.anim.rotate);
							anim[1] = AnimationUtils.loadAnimation(act,
									R.anim.alpha);
							int ran = new Random().nextInt(2);
							imgv.startAnimation(anim[ran]);
							imgLastClick.startAnimation(anim[ran]);
							//

							imgLastClick.setImageResource(imgLastClick.getId());
							imgv.setVisibility(LinearLayout.INVISIBLE);
							imgLastClick.setVisibility(LinearLayout.INVISIBLE);
							start = true;
							PlayActivity.imgCount += 2;

						} else {
							imgLastClick = imgv;
							//
							LinearLayout layout1 = (LinearLayout) act
									.findViewById(R.id.layoutImage1);
							for (int count = 0; count < layout1.getChildCount(); count++) {
								if (layout1.getChildAt(count).getClass() == ImageView.class
										&& ((ImageView) layout1
												.getChildAt(count)) != imgv) {
									((ImageView) layout1.getChildAt(count))
											.setImageResource(R.drawable.hdefault);
								}
							}
							//
							LinearLayout layout2 = (LinearLayout) act
									.findViewById(R.id.layoutImage2);
							for (int count = 0; count < layout2.getChildCount(); count++) {
								if (layout2.getChildAt(count).getClass() == ImageView.class
										&& ((ImageView) layout2
												.getChildAt(count)) != imgv) {
									((ImageView) layout2.getChildAt(count))
											.setImageResource(R.drawable.hdefault);
								}
							}
							//
							LinearLayout layout3 = (LinearLayout) act
									.findViewById(R.id.layoutImage3);
							for (int count = 0; count < layout3.getChildCount(); count++) {
								if (layout3.getChildAt(count).getClass() == ImageView.class
										&& ((ImageView) layout3
												.getChildAt(count)) != imgv) {
									((ImageView) layout3.getChildAt(count))
											.setImageResource(R.drawable.hdefault);
								}
							}
							LinearLayout layout4 = (LinearLayout) act
									.findViewById(R.id.layoutImage4);
							for (int count = 0; count < layout4.getChildCount(); count++) {
								if (layout4.getChildAt(count).getClass() == ImageView.class
										&& ((ImageView) layout4
												.getChildAt(count)) != imgv) {
									((ImageView) layout4.getChildAt(count))
											.setImageResource(R.drawable.hdefault);
								}
							}
							LinearLayout layout5 = (LinearLayout) act
									.findViewById(R.id.layoutImage5);
							for (int count = 0; count < layout5.getChildCount(); count++) {
								if (layout5.getChildAt(count).getClass() == ImageView.class
										&& ((ImageView) layout5
												.getChildAt(count)) != imgv) {
									((ImageView) layout5.getChildAt(count))
											.setImageResource(R.drawable.hdefault);
								}
							}

							LinearLayout layout6 = (LinearLayout) act
									.findViewById(R.id.layoutImage6);
							for (int count = 0; count < layout4.getChildCount(); count++) {
								if (layout6.getChildAt(count).getClass() == ImageView.class
										&& ((ImageView) layout6
												.getChildAt(count)) != imgv) {
									((ImageView) layout6.getChildAt(count))
											.setImageResource(R.drawable.hdefault);
								}
							}

						}
						}
						catch(Exception ex)
						{
							Log.d("error", ex.toString());
						}
					}
				}
			});
			layout.addView(img);
		}
	}
}
