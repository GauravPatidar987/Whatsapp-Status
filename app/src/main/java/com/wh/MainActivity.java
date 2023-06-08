package com.wh;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.net.*;
import android.widget.*;
import android.text.*;
import android.graphics.*;
import java.sql.*;

public class MainActivity extends Activity 
{
	int REQ_GALLERY=6;
	Uri uri;
	ProgressBar progress;
	ImageView im;
	Thread thread,thread2;
	boolean wai=false;
	Context c;
	int currentProgress=0;
	int currentTime=6;
	
	TextView textView,text_exp,ttxt_end,tc;
	long s_time,e_time,r_time;
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
        text_exp=findViewById(R.id.main_tv_ex);
		ttxt_end=findViewById(R.id.main_tv_exp);
		tc=findViewById(R.id.main_tv_counter);
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
				s_time=System.currentTimeMillis();
				e_time=s_time+1000*60*5;
				r_time=s_time;
				progress.setVisibility(View.VISIBLE);
				final Handler mHandler=new Handler();
				final Handler mHandler2=new Handler();
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
												setGrText(textView,s+" "+currentTime);
												setUI();
											}
										});
									
								}
							}
						}
					});
				thread.start();
				thread2 = new Thread(new Runnable(){

						@Override
						public void run()
						{
							synchronized (this)
							{
								while (s_time!=e_time)
								{
									try
									{
										if (!wai){
											r_time+=1000;
										}
										Thread.sleep(1000);

									}
									catch (InterruptedException e)
									{

									}
									mHandler2.post(new Runnable() {
											@Override
											public void run()
											{
												tc.setVisibility(View.VISIBLE);
												setGrText(tc,getRemainTime(r_time));

											}
										});
								}
							}
						}
					});
				thread2.start();}}
	}
public void setGrText(TextView textView1,String ss){
	TextPaint paint = textView1.getPaint();
	StringBuilder ssb=new StringBuilder();
	ssb.append(ss);
	float width = paint.measureText(ssb.toString());
	Shader textShader = new LinearGradient(0, 0, width, textView1.getTextSize(),
										   new int[]{
											   Color.parseColor("#F97C3C"),
											   Color.parseColor("#FDB54E"),
											   Color.parseColor("#64B678"),
											   Color.parseColor("#478AEA"),
											   Color.parseColor("#8446CC"),
										   }, null, Shader.TileMode.CLAMP);
	textView1.getPaint().setShader(textShader);
	textView1.setVisibility(View.VISIBLE);
	textView1.setText(ssb.toString());
	}
	public void setUI(){
	text_exp.setVisibility(View.VISIBLE);
	text_exp.setText(getRemainTime(s_time));
	ttxt_end.setVisibility(View.VISIBLE);
	ttxt_end.setText(getRemainTime(e_time));
	
}
public void setD_UI(){
	tc.setVisibility(View.VISIBLE);
	
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
public String getRemainTime(long stime){
	StringBuilder sb=new StringBuilder();
	sb.append(new Date(stime).toLocaleString());
	return sb.toString();
}

}
