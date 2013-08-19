package com.example.my_movie_db;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowDialog extends Activity implements OnClickListener {
	
	TextView tv;
	String overview,director,writer,actors;
	Button bClose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_dialog);
		
		
		Bundle b = this.getIntent().getExtras();
		overview = b.getString("overView");
		director = b.getString("Director");
		writer = b.getString("Writer");
		actors = b.getString("Actors");
		
			
		tv = (TextView)findViewById(R.id.tv_data);
		bClose = (Button)findViewById(R.id.b_close);
		
		if(overview!=null)
			tv.setText(overview);
		else{
			tv.setText("Director :\n   "+director+"\nWriter :\n   "+writer+"\nActors : \n   "+actors);
		}
		
		bClose.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		this.finish();
		
	}
	
	

}
