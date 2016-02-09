package com.masdar.slook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.samsung.android.sdk.look.smartclip.SlookSmartClip;
import com.samsung.android.sdk.look.smartclip.SlookSmartClipCroppedArea;
import com.samsung.android.sdk.look.smartclip.SlookSmartClipDataElement;
import com.samsung.android.sdk.look.smartclip.SlookSmartClipMetaTag;

public class LookCustomTextView extends TextView {

    private SlookSmartClip mSmartClip;

    public LookCustomTextView(Context context) {
        super(context);
        init();
    }

    public LookCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LookCustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        mSmartClip = new SlookSmartClip(this);
        
        mSmartClip.setDataExtractionListener(new SlookSmartClip.DataExtractionListener() {

            public int onExtractSmartClipData(View view, SlookSmartClipDataElement resultElement, SlookSmartClipCroppedArea arg2) {
                
                resultElement.addTag(new SlookSmartClipMetaTag(SlookSmartClipMetaTag.TAG_TYPE_PLAIN_TEXT, "This is custom textview."));
                resultElement.addTag(new SlookSmartClipMetaTag(SlookSmartClipMetaTag.TAG_TYPE_APP_DEEP_LINK, "slookdemos://SmartClip"));
                return -1;
            }            
        });
    }

}