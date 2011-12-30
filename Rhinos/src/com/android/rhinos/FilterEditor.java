package com.android.rhinos;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;

public class FilterEditor extends Activity {

	private CheckBox dateCheck;
	private DatePicker datePickerIn;
	private DatePicker datePickerOut;
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filter_dialog);
		
		dateCheck = (CheckBox) findViewById(R.id.filter_dialog_dateCheck);
		datePickerIn = (DatePicker) findViewById(R.id.filter_dialog_initDate);
		datePickerOut = (DatePicker) findViewById(R.id.filter_dialog_endDate);
		button = (Button) findViewById(R.id.filter_dialog_accept);
		
		datePickerIn.setEnabled(false);
		datePickerOut.setEnabled(false);
		
		dateCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				datePickerIn.setEnabled(dateCheck.isChecked());
				datePickerOut.setEnabled(dateCheck.isChecked());
			}
		});
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if (dateCheck.isChecked()) { 
					FilteredContracts.dateIn = new Date(datePickerIn.getYear(), 
														datePickerIn.getMonth(),
														datePickerIn.getDayOfMonth());
				
					FilteredContracts.dateOut = new Date(datePickerOut.getYear(), 
														datePickerOut.getMonth(),
														datePickerOut.getDayOfMonth());
				}
				else { 	
					FilteredContracts.dateIn = null;
					FilteredContracts.dateOut = null;
				}
				
				finish();
			}
		});
	}
}
