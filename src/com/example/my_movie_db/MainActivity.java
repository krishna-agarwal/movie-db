package com.example.my_movie_db;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{
	
	EditText EtMvName;
	Button BtSearch;
	Bundle bundle = new Bundle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EtMvName = (EditText)findViewById(R.id.et_mvname);
		BtSearch = (Button)findViewById(R.id.bt_search);
		
		BtSearch.setOnClickListener(this);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String mv_name = EtMvName.getText().toString();
		
		Intent i = new Intent(MainActivity.this,MvResult.class);
		bundle.putString("movie", mv_name);
		i.putExtras(bundle);
		startActivity(i);
	}

}
