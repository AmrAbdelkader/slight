package com.masdar.slook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.masdar.R;
import com.samsung.android.sdk.look.Slook;
import com.samsung.android.sdk.look.smartclip.SlookSmartClip;
import com.samsung.android.sdk.look.smartclip.SlookSmartClipCroppedArea;
import com.samsung.android.sdk.look.smartclip.SlookSmartClipDataElement;
import com.samsung.android.sdk.look.smartclip.SlookSmartClipMetaTag;

public class SLookComment extends Activity {

	private Slook sLook;
	private RelativeLayout infoLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slook_comment);
		
		sLook = new Slook();
		
		infoLayout = (RelativeLayout) findViewById(R.id.information);
//		try{
//			sLook.initialize(this);
//		}catch(SsdkUnsupportedException exc){
//			Log.d("s-look-Exception", exc.getMessage());
//		}
		TextView tv = (TextView) findViewById(R.id.text_static);  
		SlookSmartClip sc = new SlookSmartClip(infoLayout);   
		
		sc.setDataExtractionListener(new SlookSmartClip.DataExtractionListener() {
			
			@Override
			public int onExtractSmartClipData(View arg0, SlookSmartClipDataElement arg1, SlookSmartClipCroppedArea arg2) {
				
				return -1;
			}
		});
		sc.clearAllMetaTag();   
		sc.addMetaTag(new SlookSmartClipMetaTag(SlookSmartClipMetaTag.TAG_TYPE_URL, "http://www.samsung.com"));   
		sc.addMetaTag(new SlookSmartClipMetaTag(SlookSmartClipMetaTag.TAG_TYPE_PLAIN_TEXT, "This is android textview.")); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.slook_comment, menu);
		return true;
	}

	private TextView createTextView(String str) {
        TextView tv = new TextView(this);
        tv.setText(str);
        return tv;
    }
}
