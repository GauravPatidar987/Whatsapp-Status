package com.wh;
import android.app.*;
import android.view.*;
import android.os.*;
import android.widget.*;
import android.net.*;
import android.view.View.*;

public class ViewStatusFragment extends Fragment implements OnTouchListener
{
	ProgressBar progress;
	ImageView im;
	Uri uri;
	private boolean wai;
	private Thread thread;
	private Handler mHandler;
	private int currentProgress=0;
	private Mcall cca;
	public ViewStatusFragment(Uri uri, Mcall cca)
	{
		this.uri = uri;
		this.cca = cca;
	}
	@Override
	public boolean onTouch(View p1, MotionEvent event)
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
		}
		return true;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		View v=inflater.inflate(R.layout.fragment_view_status, container, false);	
		progress = v.findViewById(R.id.progressBar);
		im = v.findViewById(R.id.mainImageView);
		v.setOnTouchListener(this);
		setup();
		return v;	

	}
	public void setup()
	{
		mHandler = new Handler();
		Uri u=uri;
		im.setImageURI(u);
		thread = new Thread(new Runnable(){
				@Override
				public void run()
				{
					
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

					cca.changeUi(cca.getUi()+1);
				}});
		thread.start();
	}
}
