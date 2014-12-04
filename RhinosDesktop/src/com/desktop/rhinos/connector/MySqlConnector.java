package com.desktop.rhinos.connector;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import javax.crypto.SecretKey;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.android.rhinos.RCipher;
import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Consultancy;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Service;
import com.android.rhinos.gest.User;

public class MySqlConnector implements Connector {

	public static class App {
		private static final String external_path = "http://www.pedroapv.com/desktopApp";
		public static final User user = new User();
		
		public static final Font DEFAULT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 11);
		public static final Color LIGHT_GREEN = new Color(57, 134, 90, 40);
		public static final Color APP_GREEN = new Color(57, 134, 90);
	}
	
	private RCipher cipher;
	private SimpleDateFormat formatter;
	private static MySqlConnector INSTANCE = new MySqlConnector();
	
	private MySqlConnector() {
		
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			SecretKey key = RCipher.importKeyFromFile("security/security.keys");
			cipher = new RCipher(key);
		}
		catch (Exception e) {e.getStackTrace();}
	}
	
	public synchronized static MySqlConnector getInstance() {
		return INSTANCE;
	}
	
	public boolean login(String user, String password) {

	    //the mail data to send
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("user", cipher.encode(user)));
	    
	    try {
	        JSONArray jsonArray = getDataFromDB(App.external_path+"/db_login.php", nameValuePairs);
	        JSONObject jsonObj = jsonArray.getJSONObject(0);
	        if (cipher.decode(jsonObj.getString("password")).equals(password)) {
	        	App.user.setExtId(jsonObj.getInt("id"));
	        	App.user.setType(jsonObj.getInt("type"));
	        	App.user.setUser(user);
	        	App.user.setName(cipher.decode(jsonObj.getString("name")));
	        	App.user.setMail(cipher.decode(jsonObj.getString("mail")));
	        	return true;
	        }
	    }
	    catch (Exception e) {return false;}
        
        return false;
	}
	
	@Override
	public void clearCampaigns() {
		getDataFromDB(App.external_path+"/db_clear_campaigns.php", new ArrayList<NameValuePair>());
	}

	@Override
	public boolean addCampaign(Campaign camp) {

	    //the mail data to send
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(camp.getName())));
	    
	    try {
	    	//inserting into Campaings
	        getDataFromDB(App.external_path+"/db_add_campaign.php", nameValuePairs);
	       
	        //looking up campaign id
	        JSONArray JsonArray = getDataFromDB(App.external_path+"/db_get_campaign_id.php", nameValuePairs);
	        int c_id = JsonArray.getJSONObject(0).getInt("id");
	        
	        //inserting into campinfo
	        for (Service s : camp.getServices().values()) {
				nameValuePairs.clear();
				nameValuePairs.add(new BasicNameValuePair("id", c_id+""));
				nameValuePairs.add(new BasicNameValuePair("service", cipher.encode(s.getService())));
				nameValuePairs.add(new BasicNameValuePair("commission", s.getCommission()+""));
				
				getDataFromDB(App.external_path+"/db_insert_into_campinfo.php", nameValuePairs);
			}
	        
	        return true;
	    }
	    catch (Exception e) {}
        
        return false;
	}

	@Override
	public ArrayList<Campaign> getCampaigns(User u) {
		
		ArrayList<Campaign> r = new ArrayList<Campaign>();
	    
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("idUser", u.getExtId()+""));
	    
	    try {
	        JSONArray jsonArray = getDataFromDB(App.external_path+ ((u.isRoot()) ? "/db_get_all_campaigns.php" : "/db_get_campaigns.php"), nameValuePairs);
	        
	        if (jsonArray.length() > 0) {
				
	        	JSONObject jsonObj = jsonArray.getJSONObject(0);
	        	String name = cipher.decode(jsonObj.getString("name"));
				String old = name;
				Campaign camp = new Campaign(name);

				for (int i = 0; i < jsonArray.length(); i++) {

					jsonObj = jsonArray.getJSONObject(i);
					
					if (!(name = cipher.decode(jsonObj.getString("name"))).equals(old)) {
						r.add(camp);
						old = name;
						camp = new Campaign(name);
					}
					
					String service = cipher.decode(jsonObj.getString("service"));
					camp.addService(service , new Service(service, jsonObj.getInt("commission")));
				}
				r.add(camp);
	        }
	    }
	    catch (Exception e) {}
        
        return r;
	}

	@Override
	public boolean addClient(Client c) {

	    //the mail data to send
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", cipher.encode(c.getId().toString())));
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(c.getName())));
	    nameValuePairs.add(new BasicNameValuePair("tlf_1", cipher.encode(c.getTlf_1())));
	    nameValuePairs.add(new BasicNameValuePair("tlf_2", cipher.encode(c.getTlf_2())));
	    nameValuePairs.add(new BasicNameValuePair("mail", cipher.encode(c.getMail())));
	    nameValuePairs.add(new BasicNameValuePair("consultancy", c.getConsultancy()+""));
	    
	    //address
	    nameValuePairs.add(new BasicNameValuePair("tipo_via", cipher.encode(c.getDirTipoVia())));
	    nameValuePairs.add(new BasicNameValuePair("nombre_via", cipher.encode(c.getDirNombreVia())));
	    nameValuePairs.add(new BasicNameValuePair("numero", cipher.encode(c.getDirNumero())));
	    nameValuePairs.add(new BasicNameValuePair("portal", cipher.encode(c.getDirPortal())));
	    nameValuePairs.add(new BasicNameValuePair("escalera", cipher.encode(c.getDirEscalera())));
	    nameValuePairs.add(new BasicNameValuePair("piso", cipher.encode(c.getDirPiso())));
	    nameValuePairs.add(new BasicNameValuePair("puerta", cipher.encode(c.getDirPuerta())));
	    nameValuePairs.add(new BasicNameValuePair("poblacion", cipher.encode(c.getDirPoblacion())));
	    nameValuePairs.add(new BasicNameValuePair("municipio", cipher.encode(c.getDirMunicipio())));
	    nameValuePairs.add(new BasicNameValuePair("cp", cipher.encode(c.getDirCp())));
	    
	    try {
	        getDataFromDB(App.external_path+"/db_add_client.php", nameValuePairs);
	        return true;
	    }
	    catch (Exception e) {}
	    
		return false;
	}
	
	@Override
	public boolean editClient(Client c) {

	    //the mail data to send
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", cipher.encode(c.getId().toString())));
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(c.getName())));
	    nameValuePairs.add(new BasicNameValuePair("tlf_1", cipher.encode(c.getTlf_1())));
	    nameValuePairs.add(new BasicNameValuePair("tlf_2", cipher.encode(c.getTlf_2())));
	    nameValuePairs.add(new BasicNameValuePair("mail", cipher.encode(c.getMail())));
	    nameValuePairs.add(new BasicNameValuePair("consultancy", c.getConsultancy()+""));
	    
	    //address
	    nameValuePairs.add(new BasicNameValuePair("tipo_via", cipher.encode(c.getDirTipoVia())));
	    nameValuePairs.add(new BasicNameValuePair("nombre_via", cipher.encode(c.getDirNombreVia())));
	    nameValuePairs.add(new BasicNameValuePair("numero", cipher.encode(c.getDirNumero())));
	    nameValuePairs.add(new BasicNameValuePair("portal", cipher.encode(c.getDirPortal())));
	    nameValuePairs.add(new BasicNameValuePair("escalera", cipher.encode(c.getDirEscalera())));
	    nameValuePairs.add(new BasicNameValuePair("piso", cipher.encode(c.getDirPiso())));
	    nameValuePairs.add(new BasicNameValuePair("puerta", cipher.encode(c.getDirPuerta())));
	    nameValuePairs.add(new BasicNameValuePair("poblacion", cipher.encode(c.getDirPoblacion())));
	    nameValuePairs.add(new BasicNameValuePair("municipio", cipher.encode(c.getDirMunicipio())));
	    nameValuePairs.add(new BasicNameValuePair("cp", cipher.encode(c.getDirCp())));
	    
	    try {
	        getDataFromDB(App.external_path+"/db_edit_client.php", nameValuePairs);
	        return true;
	    }
	    catch (Exception e) {}
	    
		return false;
	}

	@Override
	public ArrayList<Client> getClients(User u) {
		ArrayList<Client> r = new ArrayList<Client>();
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
	    try {
	    	String _path = null;
	    	if (!u.isRoot()) {
	    		nameValuePairs = new ArrayList<NameValuePair>();
	    		nameValuePairs.add(new BasicNameValuePair("idUser", u.getExtId()+""));
	    		_path = "/db_get_user_clients.php";
	    	}
	    	else
	    		_path = "/db_get_clients.php";
	    		
	        JSONArray jsonArray = getDataFromDB(App.external_path+ _path, nameValuePairs);
			
	        if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					Client cl = new Client();
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					
					cl.setId(new Dni(cipher.decode(jsonObj.getString("id"))));
					cl.setName(cipher.decode(jsonObj.getString("name")));
					cl.setTlf_1(cipher.decode(jsonObj.getString("tlf_1")));
					cl.setTlf_2(cipher.decode(jsonObj.getString("tlf_2")));
					cl.setMail(cipher.decode(jsonObj.getString("mail")));
					
					cl.setDirTipoVia(cipher.decode(jsonObj.getString("tipo_via")));
					cl.setDirNombreVia(cipher.decode(jsonObj.getString("nombre_via")));
					cl.setDirNumero(cipher.decode(jsonObj.getString("numero")));
					cl.setDirPortal(cipher.decode(jsonObj.getString("portal")));
					cl.setDirEscalera(cipher.decode(jsonObj.getString("escalera")));
					cl.setDirPiso(cipher.decode(jsonObj.getString("piso")));
					cl.setDirPuerta(cipher.decode(jsonObj.getString("puerta")));
					cl.setDirPoblacion(cipher.decode(jsonObj.getString("poblacion")));
					cl.setDirMunicipio(cipher.decode(jsonObj.getString("municipio")));
					cl.setDirCp(cipher.decode(jsonObj.getString("cp")));
					
					cl.setConsultancy(jsonObj.getInt("consultancy"));
					
					r.add(cl);
				}
	        }
	    }
	    catch (Exception e) {e.printStackTrace();}
		
		return r;
	}

	@Override
	public ArrayList<Client> getCampaignClients(Campaign c, User u) {
		ArrayList<Client> tr = new ArrayList<Client>();
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("campaign", cipher.encode(c.getName())));
	    
	    String _path = null;
	    if (!u.isRoot()) {
	    	nameValuePairs.add(new BasicNameValuePair("idUser", u.getExtId()+""));
	    	_path = "/db_get_user_campaign_clients.php";
	    }
	    else
	    	_path = "/db_get_campaign_clients.php";
	    
	    JSONArray jsonArray = getDataFromDB(App.external_path+ _path, nameValuePairs);
		
	    try {
			for (int i = 0; i < jsonArray.length(); i++) {
				Client cl = new Client();
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				
				cl.setId(new Dni(cipher.decode(jsonObj.getString("id"))));
				cl.setName(cipher.decode(jsonObj.getString("name")));
				cl.setTlf_1(cipher.decode(jsonObj.getString("tlf_1")));
				cl.setTlf_2(cipher.decode(jsonObj.getString("tlf_2")));
				cl.setMail(cipher.decode(jsonObj.getString("mail")));
				cl.setDirNombreVia(cipher.decode(jsonObj.getString("address")));
				cl.setConsultancy(jsonObj.getInt("consultancy"));
				
				tr.add(cl);
			}
	    }
	    catch (Exception e) {}
		
		return tr;
	}

	@Override
	public Client clientExists(String id) {
		Client client = null;
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", cipher.encode(id)));
		
	    JSONArray jsonArray = getDataFromDB(App.external_path+"/db_client_exists.php", nameValuePairs);
		
	    try {
		    if (jsonArray.length() > 0) {
				client = new Client();
				JSONObject jsonObj = jsonArray.getJSONObject(0);
				
				client.setId(new Dni(id));
				client.setName(cipher.decode(jsonObj.getString("name")));
				client.setTlf_1(cipher.decode(jsonObj.getString("tlf_1")));
				client.setTlf_2(cipher.decode(jsonObj.getString("tlf_2")));
				client.setMail(cipher.decode(jsonObj.getString("mail")));
				client.setConsultancy(jsonObj.getInt("consultancy"));
				
				client.setDirTipoVia(cipher.decode(jsonObj.getString("tipo_via")));
				client.setDirNombreVia(cipher.decode(jsonObj.getString("nombre_via")));
				client.setDirNumero(cipher.decode(jsonObj.getString("numero")));
				client.setDirPortal(cipher.decode(jsonObj.getString("portal")));
				client.setDirEscalera(cipher.decode(jsonObj.getString("escalera")));
				client.setDirPiso(cipher.decode(jsonObj.getString("piso")));
				client.setDirPuerta(cipher.decode(jsonObj.getString("puerta")));
				client.setDirPoblacion(cipher.decode(jsonObj.getString("poblacion")));
				client.setDirMunicipio(cipher.decode(jsonObj.getString("municipio")));
				client.setDirCp(cipher.decode(jsonObj.getString("cp")));

			}
	    }
	    catch (Exception e) {}

		return client;
	}

	@Override
	public boolean addService(int userId, Service s, Client c) {
		boolean tr = true;
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("idUser", userId+""));
	    nameValuePairs.add(new BasicNameValuePair("idClient", cipher.encode(c.getId().toString())));
	    nameValuePairs.add(new BasicNameValuePair("service", cipher.encode(s.getService())));
	    nameValuePairs.add(new BasicNameValuePair("campaign", cipher.encode(s.getCampaign())));
	    nameValuePairs.add(new BasicNameValuePair("commission", s.getCommission()+""));
	    nameValuePairs.add(new BasicNameValuePair("date", formatter.format(s.getDate())));
	    nameValuePairs.add(new BasicNameValuePair("expiry", formatter.format(s.getExpiryDate())));
	    nameValuePairs.add(new BasicNameValuePair("state", s.getState()+""));
	    nameValuePairs.add(new BasicNameValuePair("notes", cipher.encode(s.getNotes())));
		
		try {
			getDataFromDB(App.external_path+"/db_add_service.php", nameValuePairs);
		}
		catch (Exception e) {tr = false;}
		
		return tr;
	}

	@Override
	public void editService(int serviceId, int state, String notes) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", serviceId+""));
	    nameValuePairs.add(new BasicNameValuePair("state", state+""));
	    nameValuePairs.add(new BasicNameValuePair("notes", cipher.encode(notes)));
		
		try {
			getDataFromDB(App.external_path+"/db_edit_service.php", nameValuePairs);
		}
		catch (Exception e) {}
	}
	
	@Override
	public void editServiceCommission(int serviceId, double commission) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", serviceId+""));
	    nameValuePairs.add(new BasicNameValuePair("commission", commission+""));
		
		try {
			getDataFromDB(App.external_path+"/db_edit_service_commission.php", nameValuePairs);
		}
		catch (Exception e) {}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public ArrayList<Service> getServices(String id) {
		ArrayList<Service> tr = new ArrayList<Service>();
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("idClient", cipher.encode(id)));
	    
	    JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_services.php", nameValuePairs);
	    
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				Service s = new Service(cipher.decode(jsonObj.getString("service")), jsonObj.getDouble("commission"));
				
				s.setExtId(jsonObj.getInt("id"));
				s.setCampaign(cipher.decode(jsonObj.getString("campaign")));
				s.setDate(new Date(jsonObj.getString("date").replace("-", "/")));
				s.setExpiryDate(new Date(jsonObj.getString("expiry").replace("-", "/")));
				s.setState(jsonObj.getInt("state"));
				s.setNotes(cipher.decode(jsonObj.getString("notes")));
				
				tr.add(s);
			}
		}
		catch (Exception e) {e.printStackTrace();}
		
		return tr;	
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public ArrayList<Service> getUserServices(User u) {
		ArrayList<Service> tr = new ArrayList<Service>();
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("idUser", u.getExtId()+""));
	    	    
	    JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_user_services.php", nameValuePairs);
	    
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				Service s = new Service(cipher.decode(jsonObj.getString("service")), jsonObj.getDouble("commission"));
								
				s.setExtId(jsonObj.getInt("id"));
				s.setCampaign(cipher.decode(jsonObj.getString("campaign")));
				s.setDate(new Date(jsonObj.getString("date").replace("-", "/")));
				s.setExpiryDate(new Date(jsonObj.getString("expiry").replace("-", "/")));
				s.setState(jsonObj.getInt("state"));
				s.setNotes(cipher.decode(jsonObj.getString("notes")));
				
				s.setId(new Dni(cipher.decode(jsonObj.getString("idClient"))));
				s.setTitular(cipher.decode(jsonObj.getString("name")));
				
				tr.add(s);
			}
		}
		catch (Exception e) {}
		
		return tr;	
	}

	@SuppressWarnings("deprecation")
	public ArrayList<Service> getUserServicesByDate(User u, Date date_in, Date date_out, int date_type) {
		ArrayList<Service> tr = new ArrayList<Service>();
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("idUser", u.getExtId()+""));
	    nameValuePairs.add(new BasicNameValuePair("date_in", formatter.format(date_in)));
	    nameValuePairs.add(new BasicNameValuePair("date_out", formatter.format(date_out)));
	    nameValuePairs.add(new BasicNameValuePair("date_type", date_type+""));
	    	    
	    JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_user_services_by_date.php", nameValuePairs);
	    
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				Service s = new Service(cipher.decode(jsonObj.getString("service")), jsonObj.getDouble("commission"));
								
				s.setExtId(jsonObj.getInt("id"));
				s.setCampaign(cipher.decode(jsonObj.getString("campaign")));
				s.setDate(new Date(jsonObj.getString("date").replace("-", "/")));
				s.setExpiryDate(new Date(jsonObj.getString("expiry").replace("-", "/")));
				s.setState(jsonObj.getInt("state"));
				s.setNotes(cipher.decode(jsonObj.getString("notes")));
				
				s.setId(new Dni(cipher.decode(jsonObj.getString("idClient"))));
				s.setTitular(cipher.decode(jsonObj.getString("name")));
				
				tr.add(s);
			}
		}
		catch (Exception e) {}
		
		return tr;	
	}

	@Override
	public void deleteService(Service service) {
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", service.getExtId()+""));
	 
	    getDataFromDB(App.external_path+"/db_delete_service.php", nameValuePairs);
	}
	
	@Override
	public void deleteClient(String id) {
	    
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", cipher.encode(id)));
	    
	    getDataFromDB(App.external_path+"/db_delete_client.php", nameValuePairs);
	}
	
	@Override
	public void changePassword(String user, String newpass) {
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("user", cipher.encode(user)));
	    nameValuePairs.add(new BasicNameValuePair("newpass", cipher.encode(newpass)));
	 
	    getDataFromDB(App.external_path+"/db_change_password.php", nameValuePairs);
	}
	
	@Override
	public boolean createAccount(User u, String password, boolean sendMail) {
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("user", cipher.encode(u.getUser())));
	    nameValuePairs.add(new BasicNameValuePair("mail", cipher.encode(u.getMail())));
	    
	    JSONArray jsonArray = getDataFromDB(App.external_path+"/db_user_or_mail_exists.php", nameValuePairs);
	    
	    if (jsonArray == null) {
		    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(u.getName())));
		    nameValuePairs.add(new BasicNameValuePair("password", cipher.encode(password)));
	    	
		    getDataFromDB(App.external_path+"/db_create_account.php", nameValuePairs);
		    
		    if (sendMail) {
			    nameValuePairs.clear();
			    nameValuePairs.add(new BasicNameValuePair("name", u.getName()));
			    nameValuePairs.add(new BasicNameValuePair("user", u.getUser()));
			    nameValuePairs.add(new BasicNameValuePair("mail", u.getMail()));
			    nameValuePairs.add(new BasicNameValuePair("password", password));
			    nameValuePairs.add(new BasicNameValuePair("c_version", getCurrentVersion()));
			    getDataFromDB(App.external_path+"/db_send_mail.php", nameValuePairs);
		    }

	    	return true;
	    }
	    else
	    	return false;
	}
	
	@Override
	public String getCurrentVersion() {
		try {
			JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_current_version.php", new ArrayList<NameValuePair>()); 
			return jsonArray.getJSONObject(0).getString("c_version");
		}
		catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	@Override
	public ArrayList<User> getUsers() {
		ArrayList<User> result = new ArrayList<User>();
		
		try {
			JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_users.php", new ArrayList<NameValuePair>());
			
			for (int i = 0; i < jsonArray.length(); i++) {
				User u = new User();
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				
				u.setExtId(jsonObj.getInt("id"));
				u.setType(jsonObj.getInt("type"));
				u.setUser(cipher.decode(jsonObj.getString("user")));
				u.setName(cipher.decode(jsonObj.getString("name")));
				u.setMail(cipher.decode(jsonObj.getString("mail")));
				
				result.add(u);
			}
		}
		catch (Exception e) {e.printStackTrace();}
		
		return result;
	}
	
	@Override
	public User getUserById(int id) {
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", id+""));
	    
		User result = new User();
		
		try {
			JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_user_by_id.php", nameValuePairs);
			
			User u = new User();
			JSONObject jsonObj = jsonArray.getJSONObject(0);
			
			u.setExtId(id);
			u.setUser(cipher.decode(jsonObj.getString("user")));
			u.setName(cipher.decode(jsonObj.getString("name")));
			u.setMail(cipher.decode(jsonObj.getString("mail")));
        	u.setParentProfit(jsonObj.getInt("p_profit"));
		}
		catch (Exception e) {e.printStackTrace();}
		
		return result;
	}
	
	@Override
	public ArrayList<String> getAuthorizedCampaigns(User user) {
		ArrayList<String> r = new ArrayList<String>();
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("idUser", user.getExtId()+""));
	    
	    JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_authorized_campaigns.php", nameValuePairs);
	    
	    try {
	    	for (int i = 0; i < jsonArray.length(); i++) {
	    		r.add(cipher.decode(jsonArray.getJSONObject(i).getString("campaign")));
	    	}
	    }
	    catch (Exception e) {e.printStackTrace();}
	    	
		return r;
	}
	
	@Override
	public void grantCampaignPermission(User user, Campaign campaign) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("idUser", user.getExtId()+""));
	    nameValuePairs.add(new BasicNameValuePair("campaign", cipher.encode(campaign.getName())));
	    
	    getDataFromDB(App.external_path+"/db_grant_campaign_permission.php", nameValuePairs);
	}
	
	@Override
	public void removeCampaingPermission(User user, Campaign campaign) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("idUser", user.getExtId()+""));
	    nameValuePairs.add(new BasicNameValuePair("campaign", cipher.encode(campaign.getName())));
	    
	    getDataFromDB(App.external_path+"/db_remove_campaign_permission.php", nameValuePairs);
	}
	
	@Override
	public ArrayList<Consultancy> getConsultancy() {
		ArrayList<Consultancy> result = new ArrayList<Consultancy>();
		
		try {
			JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_consultancy.php", new ArrayList<NameValuePair>());
			
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					Consultancy u = new Consultancy();
					JSONObject jsonObj = jsonArray.getJSONObject(i);
	
					u.setExtId(jsonObj.getInt("id"));
					u.setName(cipher.decode(jsonObj.getString("name")));
					u.setConsultant(cipher.decode(jsonObj.getString("consultant")));
					u.setTlf_1(cipher.decode(jsonObj.getString("tlf_1")));
					u.setTlf_2(cipher.decode(jsonObj.getString("tlf_2")));
					u.setMail(cipher.decode(jsonObj.getString("mail")));
					
					result.add(u);
				}
			}
		}
		catch (Exception e) {e.printStackTrace();}
		
		return result;
	}
	
	@Override
	public Consultancy getConsultancy(int id) {
		Consultancy u = null;
		try {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		    nameValuePairs.add(new BasicNameValuePair("id", id+""));
			
			JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_consultancy_by_id.php", nameValuePairs);

			if (jsonArray != null) {
				u = new Consultancy();
				JSONObject jsonObj = jsonArray.getJSONObject(0);

				u.setExtId(jsonObj.getInt("id"));
				u.setName(cipher.decode(jsonObj.getString("name")));
				u.setConsultant(cipher.decode(jsonObj.getString("consultant")));
				u.setTlf_1(cipher.decode(jsonObj.getString("tlf_1")));
				u.setTlf_2(cipher.decode(jsonObj.getString("tlf_2")));
				u.setMail(cipher.decode(jsonObj.getString("mail")));
			}
		}
		catch (Exception e) {}
		
		return u;
	}
	
	public boolean addConsultancy(Consultancy c) {

	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(c.getName())));
	    nameValuePairs.add(new BasicNameValuePair("consultant", cipher.encode(c.getConsultant())));
	    nameValuePairs.add(new BasicNameValuePair("tlf_1", cipher.encode(c.getTlf_1())));
	    nameValuePairs.add(new BasicNameValuePair("tlf_2", cipher.encode(c.getTlf_2())));
	    nameValuePairs.add(new BasicNameValuePair("mail", cipher.encode(c.getMail())));
	    
	    try {
	        getDataFromDB(App.external_path+"/db_add_consultancy.php", nameValuePairs);
	        return true;
	    }
	    catch (Exception e) {}
	    
		return false;
	}
	
	public boolean editConsultancy(Consultancy c) {
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", c.getExtId()+""));
	    nameValuePairs.add(new BasicNameValuePair("name", cipher.encode(c.getName())));
	    nameValuePairs.add(new BasicNameValuePair("consultant", cipher.encode(c.getConsultant())));
	    nameValuePairs.add(new BasicNameValuePair("tlf_1", cipher.encode(c.getTlf_1())));
	    nameValuePairs.add(new BasicNameValuePair("tlf_2", cipher.encode(c.getTlf_2())));
	    nameValuePairs.add(new BasicNameValuePair("mail", cipher.encode(c.getMail())));
	    
	    try {
	        getDataFromDB(App.external_path+"/db_edit_consultancy.php", nameValuePairs);
	        return true;
	    }
	    catch (Exception e) {}
	    
		return false;
	}
	
	@Override
	public void deleteConsultancy(int id) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", id+""));
	    
	    getDataFromDB(App.external_path+"/db_delete_consultancy.php", nameValuePairs);
	}
	
	@Override
	public void deleteAccount(int id) {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", id+""));
	    
	    getDataFromDB(App.external_path+"/db_delete_account.php", nameValuePairs);
	}
	
	@Override
	public ArrayList<ArrayList<User>> getUserStructure(User user) {
		
		ArrayList<ArrayList<User>> st = new ArrayList<ArrayList<User>>();
		HashSet<User> set = new HashSet<User>();
	
		set.add(user);
		while (!set.isEmpty()) {
			Iterator<User> it = set.iterator();
			User rem = it.next();
			ArrayList<User> list = getUserChildren(rem);
			
			if (list != null) {
				st.add(list);
			
				for (User u : st.get(st.size() -1)){
					set.add(u);
				}
			}
			set.remove(rem);
		}
		return st;
	}
	
	public ArrayList<User> getUserChildren(User user) {
		ArrayList<User> t = new ArrayList<User>();
		t.add(user);
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("parent", user.getExtId()+""));
	    
	    try {
	        JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_user_children.php", nameValuePairs);
	        
	        for (int i = 0; i < jsonArray.length(); i++) {
	        	JSONObject jsonObj = jsonArray.getJSONObject(i);
	        	
	        	User u = new User();
	        	u.setExtId(jsonObj.getInt("id"));
	        	u.setType(jsonObj.getInt("type"));
	        	u.setUser(cipher.decode(jsonObj.getString("user")));
	        	u.setName(cipher.decode(jsonObj.getString("name")));
	        	u.setMail(cipher.decode(jsonObj.getString("mail")));
	        	u.setParentProfit(jsonObj.getDouble("p_profit"));
	        	
	        	t.add(u);
	        }

	        return t;
	    }
	    catch (Exception e) {return null;}
	}
	
	public User getUserParent(int extId) {
		User u = null;
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("child", extId+""));
	    
	    try {
	        JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_user_parent.php", nameValuePairs);
	        JSONObject jsonObj = jsonArray.getJSONObject(0);
	        	
        	u = new User();
        	u.setExtId(jsonObj.getInt("parent"));
        	u.setName(cipher.decode(jsonObj.getString("name")));
        	u.setMail(cipher.decode(jsonObj.getString("mail")));
        	u.setParentProfit(jsonObj.getDouble("p_profit"));

	        return u;
	    }
	    catch (Exception e) {return null;}
	}
	
	public void editUserParent(int extId, int idParent, double p_profit) {
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("child", extId+""));
	    nameValuePairs.add(new BasicNameValuePair("parent", idParent+""));
	    nameValuePairs.add(new BasicNameValuePair("p_profit", p_profit+""));
	    
	    try {
	        getDataFromDB(App.external_path+"/db_edit_user_parent.php", nameValuePairs);
	    }
	    catch (Exception e) {e.printStackTrace();}
	}
	
	private JSONArray getDataFromDB(String url, ArrayList<NameValuePair> nameValuePairs) {
	    
		String result = "";
	    InputStream is = null;
	    
	    try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            sb.append(reader.readLine());
	        
            is.close();
            result = sb.toString();
            return ((result.trim().length() > 0) ? new JSONArray(result) : new JSONArray());
	    }
	    catch (Exception e) {}
	    
		return null;
	}
}
