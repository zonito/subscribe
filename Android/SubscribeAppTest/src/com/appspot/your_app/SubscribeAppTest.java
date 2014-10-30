package com.appspot.your_app.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.appspot.your_app.R;
import com.appspot.your_app.SubscribeApp;
import com.appspot.your_app.subscribe.Subscribe;
import com.appspot.your_app.subscribe.model.SubscribeApiMessagesEmailAddressMessage;
import com.appspot.your_app.subscribe.model.SubscribeApiMessagesRequestMessage;
import com.appspot.your_app.subscribe.model.SubscribeApiMessagesResponseMessage;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import android.content.Context;
import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.EditText;
/**
 * Class defination for SubscribeAppTest
 * 
 * <p>
 * Unit test class for SubscribeApp
 * </p>
 * 
 * <p>
 * These class contains three unit cases to test subscription application. The test cases covers UI testing as well as function testing.
 * </p>
 * 
 * @author Ruchita Dhariya
 *
 */
public class SubscribeAppTest extends ActivityInstrumentationTestCase2<SubscribeApp>{

	private SubscribeApp subscribeApp;
	private EditText email;
	private EditText subject;
	private EditText body;
	private Button btnSend;
	Context context;
	SubscribeApiMessagesResponseMessage responseMessage;
	CountDownLatch signal = new CountDownLatch(1);
	
	public SubscribeAppTest() {
		super(SubscribeApp.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		//Create an intent to launch target Activity
		subscribeApp = getActivity();
		//get context for the target
		context = getInstrumentation().getTargetContext();
		responseMessage = null;
		email=(EditText) subscribeApp.findViewById(R.id.email);
		subject=(EditText) subscribeApp.findViewById(R.id.subject);
		body=(EditText) subscribeApp.findViewById(R.id.body);
		btnSend = (Button) subscribeApp.findViewById(R.id.sendbtn);
	}

	/**
	 * Tests the preconditions of this test fixture.
	 */
	@MediumTest
	public void testPreconditions() {
		//Start the activity under test in isolation, without values for savedInstanceState and
		//lastNonConfigurationInstance

		assertNotNull("mLaunchActivity is null", getActivity());
		assertNotNull("EmailEditText is null", email);
		assertNotNull("subject is null", subject);
		assertNotNull("body is null", body);
		assertNotNull("send button is null", btnSend);
	}

	/**
	 * Tests the correctness of the initial text.
	 */
	@MediumTest
	public void testTextView_labelText() {
		//It is good practice to read the string from your resources in order to not break
		//multiple tests when a string changes.
		final String expected_email = subscribeApp.getString(R.string.placeholder_emailaddress);
		final String actual_email = email.getHint().toString();
		assertEquals("Email contains wrong text", expected_email, actual_email);
		final String expected_subject = subscribeApp.getString(R.string.placeholder_subject);
		final String actual_subject = subject.getHint().toString();
		assertEquals("Email contains wrong text", expected_subject, actual_subject);
		final String expected_body = subscribeApp.getString(R.string.placeholder_body);
		final String actual_body = body.getHint().toString();
		assertEquals("Email contains wrong text", expected_body, actual_body);
		final String expected_btnlbl = subscribeApp.getString(R.string.btn_sendemail);
		final String actual_btnlbl = btnSend.getText().toString();
		assertEquals("Send button label is wrong", expected_btnlbl, actual_btnlbl);
	}

	/**
	 * Tests the correct data is received
	 */
	@LargeTest
	public void testResponse(){
		final String expected_to_email = "";
		final String expected_subject = "Junit Test";
		final String expected_body = "Good Luck!";
		try {
			runTestOnUiThread(new Runnable() {
				public void run() {
					new SendingAsyncTask().execute(expected_subject, expected_body, expected_to_email);
					// Asynctask for background processing
				}
			});
			signal.await(20, TimeUnit.SECONDS);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		assertEquals("Email Sending is fail.", Boolean.TRUE, responseMessage.getSuccess());
	}

	private class SendingAsyncTask extends AsyncTask <String, Void, SubscribeApiMessagesResponseMessage> {

		protected SubscribeApiMessagesResponseMessage doInBackground(String...params) {
			SubscribeApiMessagesResponseMessage response = null;
			try {

				Subscribe.Builder builder = new Subscribe.Builder(
						AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
				builder.setApplicationName(context.getString(R.string.app_name));

				Subscribe service = builder.build();
				SubscribeApiMessagesRequestMessage Subscrib =
						new SubscribeApiMessagesRequestMessage();

				//Subscribe object for set message, body and email
				Subscrib.setBody(params[1]);
				Subscrib.setSubject(params[0]);
				Subscrib.setPrivateKey(context.getString(R.string.private_key));
				Subscrib.setSender(context.getString(R.string.email_sender));
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
				e.printStackTrace();
			}
			return response;
		}

		protected void onPostExecute(SubscribeApiMessagesResponseMessage email) {
			//Clear the progress dialog and the fields
			responseMessage = email;
		}
	}
}
