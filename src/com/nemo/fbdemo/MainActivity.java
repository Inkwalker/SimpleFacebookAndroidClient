package com.nemo.fbdemo;

import android.os.Bundle;
import android.view.Menu;
import com.facebook.FacebookActivity;
import com.facebook.Request;
import com.facebook.SessionState;
import com.facebook.GraphUser;
import com.facebook.Response;
import android.widget.TextView;

public class MainActivity extends FacebookActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.openSession();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onSessionStateChange(SessionState state, Exception exception) {
    	// user has either logged in or not ...
    	  if (state.isOpened()) {
    	    // make request to the /me API
    	    Request request = Request.newMeRequest(
    	      this.getSession(),
    	      new Request.GraphUserCallback() {
    	        // callback after Graph API response with user object
    	        @Override
    	        public void onCompleted(GraphUser user, Response response) {
    	          if (user != null) {
    	            TextView welcome = (TextView) findViewById(R.id.welcome);
    	            welcome.setText("Hello " + user.getName() + "!");
    	          }
    	        }
    	      }
    	    );
    	    Request.executeBatchAsync(request);
    	  }
    }
}
