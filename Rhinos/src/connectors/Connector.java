package connectors;

import java.util.ArrayList;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Service;
import com.android.rhinos.gest.User;

public interface Connector {

	public boolean login(String user, String password);
	
	public void clearCampaigns();
	
	public boolean addCampaign(Campaign camp);
	public ArrayList<Campaign> getCampaigns();
		
	public boolean addClient(Client c);
	
	public ArrayList<Client> getClients(User u);
	public ArrayList<Client> getCampaignClients(Campaign c, User u);
	
	public Client clientExists(String _id);
	
	public boolean addService(Service s, Client c);
	public ArrayList<Service> getServices(String _id);
	
	public int getSumCommissions(Client c);

	public void deleteService(Service service);
	
	public void deleteClient(String id);

	public void changePassword(String user, String newpass);

	public boolean createAccount(User u, String pass);

	public String getCurrentVersion();

	public ArrayList<User> getUsers();
}
