package com.android.rhinos;

import com.android.rhinos.gest.Service;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class ServiceEditor extends Dialog {
	
	public ServiceEditor(Context context, Service s) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_editor);
	}
}
