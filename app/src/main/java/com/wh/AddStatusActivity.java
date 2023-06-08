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
	public static List<Uri> list=new ArrayList<>();
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_status);
	}
	public void addStatus(View v)
	{
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, REQ_PICK_IMAGE);
	}
	public void viewStatus(View v)
	{
		if (list.size() > 0)
		{
ViewStatusActivity.ci=0;
			Intent i=new Intent(this, ViewStatusActivity.class);
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
				uri = data.getData();
				list.add(uri);
				Toast.makeText(this, "status uploaded", Toast.LENGTH_LONG).show();
			}
		}
	}
}
