package com.masdar.drawing;

import com.masdar.R;
import com.masdar.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IdeaTypeSelector extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.idea_type_fragment,container, false);
		return view;
	}
	
}
