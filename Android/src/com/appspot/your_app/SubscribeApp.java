package com.appspot.your_app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appspot.your_app.subscribe.Subscribe;
import com.appspot.your_app.subscribe.model.SubscribeApiMessagesEmailAddressMessage;
import com.appspot.your_app.subscribe.model.SubscribeApiMessagesRequestMessage;
import com.appspot.your_app.subscribe.model.SubscribeApiMessagesResponseMessage;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class SubscribeApp extends Activity {

	Context context;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendemaill);
		Button btnsend = (Button) findViewById(R.id.sendbtn);
		context = this;
		//btnsend Listener for getting all values from user and send to the server through
		btnsend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String subject = ((EditText) findViewById(R.id.subject)).getText().toString();
				String email_addresses = ((EditText) findViewById(R.id.email)).getText().toString();
				String body = ((EditText) findViewById(R.id.body)).getText().toString();

				new SendingAsyncTask().execute(subject, body, email_addresses);
				//Asyntask for background processing
			}
		});

	}

	private class SendingAsyncTask extends AsyncTask <String, Void, SubscribeApiMessagesResponseMessage> {
		private ProgressDialog pd;
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(context);
			pd.setMessage("Sending Email...");
			pd.show();
		}

		protected SubscribeApiMessagesResponseMessage doInBackground(String...params) {
			SubscribeApiMessagesResponseMessage response = null;
			try {
				Subscribe.Builder builder = new Subscribe.Builder(
						AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
				builder.setApplicationName(getString(R.string.app_name));

				Subscribe service = builder.build();
				SubscribeApiMessagesRequestMessage Subscrib =
						new SubscribeApiMessagesRequestMessage();

				//Subscribe object for set message, body and email
				Subscrib.setBody(params[1]);
				Subscrib.setSubject(params[0]);
				Subscrib.setPrivateKey(getString(R.string.private_key));
				Subscrib.setSender(getString(R.string.email_sender));
				SubscribeApiMessagesEmailAddressMessage Email =
						new SubscribeApiMessagesEmailAddressMessage();

				// Should refer to EmailAddressMessage object in python file
				Email.setEmailAddress(params[2]);

				List <SubscribeApiMessagesEmailAddressMessage> ListofEmail =
						new ArrayList <SubscribeApiMessagesEmailAddressMessage> ();
				ListofEmail.add(Email);
				Subscrib.setEmailAddresses(ListofEmail);
				response = service.send().emails(Subscrib).execute();
			} catch (Exception e) {

			}
			return response;
		}

		protected void onPostExecute(SubscribeApiMessagesResponseMessage email) {
			//Clear the progress dialog and the fields
			pd.dismiss();
			if (email.getSuccess()) {
				Toast.makeText(getBaseContext(), getString(R.string.mail_sent),
											 Toast.LENGTH_SHORT).show();
			}
		}
	}


}
