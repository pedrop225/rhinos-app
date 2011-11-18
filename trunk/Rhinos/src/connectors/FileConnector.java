package connectors;

import java.util.ArrayList;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Service;

public class FileConnector implements Connector {

	@Override
	public void clearCampaigns() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean addCampaign(Campaign camp) {
		return false;
	}
	
	@Override
	public ArrayList<Campaign> getCampaigns() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean addClient(Client c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ArrayList<Client> getClients() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Client> getCampaignClients(Campaign c) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Client clientExists(String _id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean addService(Service s, Client c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ArrayList<Service> getServices(String _id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getSumCommissions(Client c) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void deleteService(Service service) {
		// TODO Auto-generated method stub
		
	}
}
