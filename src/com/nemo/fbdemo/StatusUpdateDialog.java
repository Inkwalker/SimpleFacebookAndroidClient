package com.nemo.fbdemo;

import java.util.Arrays;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.ReauthorizeRequest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StatusUpdateDialog extends DialogFragment {
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.status_dialog, null);
        final EditText edit = (EditText)view.findViewById(R.id.status_text);
        final Context context = getActivity();
        
        builder.setView(view)
        // Add action buttons
               .setPositiveButton(getString(R.string.dialog_send), new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                	   final Session session = Session.getActiveSession();
                	   if (session != null && session.isOpened()) {
       			    		ReauthorizeRequest rr = new ReauthorizeRequest(getActivity(), Arrays.asList("publish_stream"));
       			    		session.reauthorizeForPublish(rr);

       			    		Request request = Request.newStatusUpdateRequest(session, edit.getText().toString(), new Request.Callback() {
       						
       			    			@Override
       							public void onCompleted(Response response) {
       			    				Toast.makeText(context, context.getString(R.string.sent_message), Toast.LENGTH_LONG).show();
       							}
       			    		});
       			        
       			    		Request.executeBatchAsync(request);
       			    	}
                   }
               })
               .setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   StatusUpdateDialog.this.getDialog().cancel();
                   }
               });      
        return builder.create();
    }
}
