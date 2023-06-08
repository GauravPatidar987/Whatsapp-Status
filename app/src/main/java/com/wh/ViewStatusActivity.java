package com.wh;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.content.*;
import java.util.*;
import android.net.*;
import android.view.*;

public class ViewStatusActivity extends Activity
{
	ProgressBar progress;
	ImageView im;
	List<Uri> list;
	public static int ci=0;
	boolean rf=true;
	private Context c;
	private boolean wai;
	private int currentProgress=0;
	private Thread thread;
	private Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_status);

		progress = findViewById(R.id.progressBar);
		im = findViewById(R.id.mainImageView);
		c = this;
		list = AddStatusActivity.list;
		mHandler = new Handler();
		thread = new Thread(new Runnable(){

				@Override
				public void run()
				{
					ci=1;
					Uri u=list.get(ci);
					im.setImageURI(u);
					
					currentProgress = 0;
					while (currentProgress <= 100)
					{
						try
						{
							if (!wai)
							{
								currentProgress += 20;
							}
							Thread.sleep(500);

						}
						catch (InterruptedException e)
						{

						}
						mHandler.post(new Runnable() {
								@Override
								public void run()
								{
									ProgressBarAnimation anim = new ProgressBarAnimation(progress, progress.getProgress(), currentProgress);
									anim.setDuration(500);
									progress.startAnimation(anim);

								}
							});
					}

				}});
		thread.start();
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int c=event.getAction();
		switch (c)
		{
			case MotionEvent.ACTION_DOWN:		
				wai = true;
				break;
			case MotionEvent.ACTION_UP:
				wai = false;
				break;
		}return super.onTouchEvent(event);
	}
}
