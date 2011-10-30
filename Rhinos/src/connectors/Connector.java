package connectors;

import java.util.ArrayList;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Service;

public interface Connector {

	public void clearCampaigns();
	
	public boolean addCampaign(Campaign camp);
	public ArrayList<Campaign> getCampaigns();
		
	public boolean addClient(Client c);
	public ArrayList<Client> getClients();
	
	public Client clientExists(String _id);
	
	public boolean addService(Service s, Client c);
}
