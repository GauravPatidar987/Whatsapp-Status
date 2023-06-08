package com.wh;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.net.*;
import android.widget.*;
import android.text.*;
import android.graphics.*;

public class MainActivity extends Activity 
{
	int REQ_GALLERY=6;
	Uri uri;
	ProgressBar progress;
	ImageView im;
	Thread thread;
	boolean wai=false;
	Context c;
	int currentProgress=0;
	int currentTime=6;
	TextView textView;
	String s="Remaining Time";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		im = findViewById(R.id.mainImageView);
		progress = findViewById(R.id.progressBar);
		progress.setVisibility(View.INVISIBLE);
        c = this;

		textView = findViewById(R.id.main_tv);
        
		
	}

	public void myStatus(View v)
	{
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, REQ_GALLERY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_GALLERY && resultCode == RESULT_OK)
		{
			if (data != null && data.getData() != null)
			{
				uri = data.getData();
				//Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
				im.setImageURI(uri);
				progress.setVisibility(View.VISIBLE);
				final Handler mHandler=new Handler();

				thread = new Thread(new Runnable(){

						@Override
						public void run()
						{
							synchronized (this)
							{
								while (currentProgress <= 100)
								{
									try
									{
										if (!wai){
											currentProgress += 20;
											currentTime--;
}
										Thread.sleep(1000);




									}
									catch (InterruptedException e)
									{

									}
									mHandler.post(new Runnable() {
											@Override
											public void run()
											{
												ProgressBarAnimation anim = new ProgressBarAnimation(progress, progress.getProgress(), currentProgress);
												anim.setDuration(1000);
												progress.startAnimation(anim);
												//progress.setProgress(currentProgress);
												setGrText();
											}
										});
								}
							}
						}
					});
				thread.start();
			}
		}
	}
public void setGrText(){
	TextPaint paint = textView.getPaint();
	StringBuilder ssb=new StringBuilder();
	ssb.append(s+" "+currentTime);
	float width = paint.measureText(ssb.toString());
	Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
										   new int[]{
											   Color.parseColor("#F97C3C"),
											   Color.parseColor("#FDB54E"),
											   Color.parseColor("#64B678"),
											   Color.parseColor("#478AEA"),
											   Color.parseColor("#8446CC"),
										   }, null, Shader.TileMode.CLAMP);
	textView.getPaint().setShader(textShader);
	textView.setVisibility(View.VISIBLE);
	textView.setText(ssb.toString());
}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int c=event.getAction();
		long down_time;
		long up_time;
		switch (c)
		{
			case MotionEvent.ACTION_DOWN:
				down_time = System.currentTimeMillis();
				//Toast.makeText(this, event.getAction() + ":" + down_time, Toast.LENGTH_LONG).show();


				wai = true;


				break;
			case MotionEvent.ACTION_UP:
				up_time = System.currentTimeMillis();
				//Toast.makeText(this, event.getAction() + ":" + up_time, Toast.LENGTH_LONG).show();
				wai = false;
				break;
		}return super.onTouchEvent(event);
	}


}
