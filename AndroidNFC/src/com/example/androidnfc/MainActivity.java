package com.example.androidnfc;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements 
	CreateNdefMessageCallback, OnNdefPushCompleteCallback{
	
	TextView textInfo;
	
	NfcAdapter nfcAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textInfo = (TextView)findViewById(R.id.info);

		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if(nfcAdapter==null){
			Toast.makeText(MainActivity.this, 
				"nfcAdapter==null, no NFC adapter exists", 
				Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(MainActivity.this, 
					"Set Callback(s)", 
					Toast.LENGTH_LONG).show();
			nfcAdapter.setNdefPushMessageCallback(this, this);
			nfcAdapter.setOnNdefPushCompleteCallback(this, this);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = getIntent();
		String action = intent.getAction();
		if(action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)){
			Parcelable[] parcelables = 
				intent.getParcelableArrayExtra(
						NfcAdapter.EXTRA_NDEF_MESSAGES);
			NdefMessage inNdefMessage = (NdefMessage)parcelables[0];
			NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
			NdefRecord NdefRecord_0 = inNdefRecords[0];
			String inMsg = new String(NdefRecord_0.getPayload());
			textInfo.setText(inMsg);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
	}

	@Override
	public void onNdefPushComplete(NfcEvent event) {
		
		final String eventString = "onNdefPushComplete\n" + event.toString();
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), 
						eventString, 
						Toast.LENGTH_LONG).show();
			}
		});

	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		NdefRecord rtdUriRecord = NdefRecord.createUri("http://android-er.blogspot.com/");

		NdefMessage ndefMessageout = new NdefMessage(rtdUriRecord);
		return ndefMessageout;
	}

}
