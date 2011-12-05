package com.android.rhinos;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.User;

public class UserProfile extends Activity {
	
	private TextView up_head;
	private TextView up_id;
	private TextView up_user;
	private TextView up_name;
	private TextView up_mail;
	private TableLayout up_campaigns;
	
	public Bundle _bundle;
	
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);		
		_bundle = savedInstanceState;

		up_head = (TextView) findViewById(R.id.user_profile_head);
		up_id = (TextView) findViewById(R.id.user_profile_id);
		up_user = (TextView) findViewById(R.id.user_profile_user);
		up_name = (TextView) findViewById(R.id.user_profile_name);
		up_mail = (TextView) findViewById(R.id.user_profile_mail);
		up_campaigns = (TableLayout) findViewById(R.id.user_profile_campaigns);
		
		user = (User) getIntent().getSerializableExtra("user");
		
		up_head.setText(user.getName());
		up_id.setText(user.getExtId()+"");
		up_user.setText(user.getUser());
		up_name.setText(user.getName());
		up_mail.setText(user.getMail());
		
		ArrayList<Campaign> campaigns = App.src.getCampaigns(App.user);
		ArrayList<String> p = App.src.getAuthorizedCampaigns(user);

		for (Campaign c : campaigns) {
			up_campaigns.addView(new CampaignPermissionItem(UserProfile.this, user, c, p.contains(c.getName())));
		}
	}
}

class CampaignPermissionItem extends TableRow {
	
	private CheckBox checkbox;
	
	public CampaignPermissionItem(Context context, final User user, final Campaign campaign, boolean p) {
		super(context);
		setOrientation(TableRow.VERTICAL);
		
		checkbox = new CheckBox(context);
		checkbox.setText(campaign.getName());
		checkbox.setChecked(p);
		
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (checkbox.isChecked()) {
					App.src.grantCampaignPermission(user, campaign);
				}
				else {
					App.src.removeCampaingPermission(user, campaign);
				}
			}
		});
		
		addView(checkbox);
	}
}
