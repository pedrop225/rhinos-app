package com.desktop.rhinos.connector;

import java.util.ArrayList;
import java.util.Date;

import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Consultancy;
import com.android.rhinos.gest.Service;
import com.android.rhinos.gest.User;

public interface Connector {

	public boolean login(String user, String password);
	
	public void clearCampaigns();
	
	public boolean addCampaign(Campaign camp);
	public ArrayList<Campaign> getCampaigns(User u);
		
	public boolean addClient(Client c);
	public boolean editClient(Client c);
	
	public ArrayList<Client> getClients(User u);
	public ArrayList<Client> getCampaignClients(Campaign c, User u);
	
	public Client clientExists(String _id);
	
	public boolean addService(int userId, Service s, Client c);
	public ArrayList<Service> getServices(String _id);

	public void deleteService(Service service);
	
	public void editService(int serviceId, int state, String notes);
	
	public void editServiceCommission(int serviceId, double commission);
	
	public ArrayList<Service> getUserServices(User u);
	
	public ArrayList<Service> getUserServicesByDate(User u, Date date_in, Date date_out);
	
	public void deleteClient(String id);

	public void changePassword(String user, String newpass);

	public boolean createAccount(User u, String pass, boolean sendMail);

	public String getCurrentVersion();

	public ArrayList<User> getUsers();
	
	public User getUserById(int id);

	public ArrayList<String> getAuthorizedCampaigns(User user);

	public void grantCampaignPermission(User user, Campaign campaign);

	public void removeCampaingPermission(User user, Campaign campaign);
	
	public ArrayList<Consultancy> getConsultancy();
	
	public Consultancy getConsultancy(int id);
	
	public boolean editConsultancy(Consultancy c);
	
	public void deleteConsultancy(int id);
	
	public void deleteAccount(int id);
	
	public ArrayList<User> getUserChildren(User user);
	
	public ArrayList<ArrayList<User>> getUserStructure(User user);
}
