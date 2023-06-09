package com.wh;
import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.net.*;
import java.util.*;
import android.widget.*;

public class AddStatusActivity extends Activity
{

	private static final int REQ_PICK_IMAGE = 0;
	private Uri uri;
	SQLiteHelper helper;
	public static List<Uri> list=new ArrayList<>();
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_status);
		helper = new SQLiteHelper(this);
	}
	public void addStatus(View v)
	{
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);	
		startActivityForResult(i, REQ_PICK_IMAGE);
	}
	public void viewStatus(View v)
	{

		if (helper.getStatusCount() > 0)
		{
			Intent i=new Intent(this, ViewStatusActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
		}
		else
		{
			Toast.makeText(this, "please upload status first", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_PICK_IMAGE && resultCode == RESULT_OK)
		{
			if (data != null && data.getData() != null)
			{
				Status s=new Status();

				uri = data.getData();
				s.setStatus(uri.toString());
				helper.addStatus(s);

				//list.add(uri);
				Toast.makeText(this, "status uploaded", Toast.LENGTH_LONG).show();

			}
		}
	}
}
