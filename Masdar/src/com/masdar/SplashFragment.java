package com.masdar;

import java.util.Arrays;

import com.facebook.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SplashFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.splash,container, false);
		LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
		loginButton.setReadPermissions(Arrays.asList("email"));
		return view;
	}
	
}
