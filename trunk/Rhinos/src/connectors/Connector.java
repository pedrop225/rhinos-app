package connectors;

import java.util.ArrayList;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;

public interface Connector {

	public void clearCampaigns();
	
	public boolean addCampaign(Campaign camp);
	public ArrayList<Campaign> getCampaigns();
	
	public boolean addClient(Client c);
	public ArrayList<Client> getClients();
}
