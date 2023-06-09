package com.wh;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.content.*;
import java.util.*;
import android.net.*;
import android.view.*;

public class ViewStatusActivity extends Activity implements Mcall
{
	ProgressBar progress;
	ImageView im;
	List<Uri> list;
	public int ci=0;
	boolean rf=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_status);
		list = AddStatusActivity.list;
		changeUi(ci);
		//getFragmentManager().beginTransaction().replace(R.id.frg_container,new ViewStatusFragment(list.get(ci),this)).commit();
	}
	@Override
	public void changeUi(int i)
	{
		if(i>=0&&i<list.size())
			ci=i;
			getFragmentManager().beginTransaction().replace(R.id.frg_container,new ViewStatusFragment(list.get(i),this)).commit();
	}
	@Override
	public int getUi()
	{
		return ci;
	}
}
