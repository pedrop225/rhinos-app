package connectors;

import java.util.ArrayList;

import com.android.rhinos.gest.Campaign;

public interface Connector {

	public void clearCampaigns();
	public boolean insertCampaign(Campaign camp);
	public ArrayList<Campaign> getCampaigns();
}
