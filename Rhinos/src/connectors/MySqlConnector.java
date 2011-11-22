package connectors;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

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

import android.content.Context;
import android.database.SQLException;

import com.android.rhinos.App;
import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
import com.android.rhinos.gest.Dni;
import com.android.rhinos.gest.Id;
import com.android.rhinos.gest.Nie;
import com.android.rhinos.gest.Service;

public class MySqlConnector implements Connector {

	public MySqlConnector(Context context) {}
	
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
            
            return (result.trim().length() > 0) ? new JSONArray(result) : new JSONArray();
	    }
	    catch (Exception e) {e.printStackTrace();}
	    
		return null;
	}
	
	public boolean login(String user, String password) {

	    //the mail data to send
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("user", user));
	    
	    try {
	        JSONArray jsonArray = getDataFromDB(App.external_path+"/db_login.php", nameValuePairs);
	        JSONObject jsonObj = jsonArray.getJSONObject(0);
	            
	        if (jsonObj.getString("password").equals(password)) {
	        	App.user.setExtId(jsonObj.getInt("id"));
	        	App.user.setType(jsonObj.getInt("type"));
	        	App.user.setUser(user);
	        	App.user.setMail(jsonObj.getString("user"));
	        	return true;
	        }
	    }
	    catch (Exception e) {e.printStackTrace();}
        
        return false;
	}
	
	@Override
	public void clearCampaigns() {
		getDataFromDB(App.external_path+"/db_login.php", null);
	}

	@Override
	public boolean addCampaign(Campaign camp) {

	    //the mail data to send
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", camp.getName()));
	    
	    try {
	    	//inserting into Campaings
	        getDataFromDB(App.external_path+"/db_add_campaign.php", nameValuePairs);
	       
	        //looking up campaign id
	        JSONArray JsonArray = getDataFromDB(App.external_path+"/db_get_id_campaign.php", nameValuePairs);
	        int c_id = JsonArray.getJSONObject(0).getInt("id");
	        
	        //inserting into campinfo
	        for (Service s : camp.getServices().values()) {
				nameValuePairs.clear();
				nameValuePairs.add(new BasicNameValuePair("id", ""+c_id));
				nameValuePairs.add(new BasicNameValuePair("service", s.getService()));
				nameValuePairs.add(new BasicNameValuePair("commission", ""+s.getCommission()));
				
				getDataFromDB(App.external_path+"/db_insert_into_campinfo.php", nameValuePairs);
			}
	        
	        return true;
	    }
	    catch (Exception e) {e.printStackTrace();}
        
        return false;
	}

	@Override
	public ArrayList<Campaign> getCampaigns() {
		
		ArrayList<Campaign> r = new ArrayList<Campaign>();
	    
	    try {
	        JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_campaigns.php", new ArrayList<NameValuePair>());
	        
	        if (jsonArray.length() > 0) {
				
	        	JSONObject jsonObj = jsonArray.getJSONObject(0);
	        	String name = jsonObj.getString("name");
				String old = name;
				Campaign camp = new Campaign(name);

				for (int i = 0; i < jsonArray.length(); i++) {

					jsonObj = jsonArray.getJSONObject(i);
					
					if (!(name = jsonObj.getString("name")).equals(old)) {
						r.add(camp);
						old = name;
						camp = new Campaign(name);
					}
					
					camp.addService(jsonObj.getString("service"), new Service(jsonObj.getString("service"), jsonObj.getInt("commission")));
				}
				r.add(camp);
	        }
	    }
	    catch (Exception e) {e.printStackTrace();}
        
        return r;
	}

	@Override
	public boolean addClient(Client c) {

	    //the mail data to send
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", App.cipher.encode(c.getName())));
	    nameValuePairs.add(new BasicNameValuePair("idType", c.getId().getType()+""));
	    nameValuePairs.add(new BasicNameValuePair("name", App.cipher.encode(c.getName())));
	    nameValuePairs.add(new BasicNameValuePair("tlf_1", App.cipher.encode(c.getTlf_1())));
	    nameValuePairs.add(new BasicNameValuePair("tlf_2", App.cipher.encode(c.getTlf_2())));
	    nameValuePairs.add(new BasicNameValuePair("mail", App.cipher.encode(c.getMail())));
	    nameValuePairs.add(new BasicNameValuePair("address", App.cipher.encode(c.getAddress())));
	    
	    try {
	        getDataFromDB(App.external_path+"/db_add_client.php", nameValuePairs);
	        return true;
	    }
	    catch (Exception e) {e.printStackTrace();}
	    
		return false;
	}

	@Override
	public ArrayList<Client> getClients() {
		ArrayList<Client> r = new ArrayList<Client>();
	    
	    try {
	        JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_clients.php", new ArrayList<NameValuePair>());
				
			for (int i = 0; i < jsonArray.length(); i++) {
				Client cl = new Client();
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				
				switch (jsonObj.getInt("idType")) {
					case Id.DNI: cl.setId(new Dni(App.cipher.decode(jsonObj.getString("id")))); break;
					case Id.NIE: cl.setId(new Nie(App.cipher.decode(jsonObj.getString("id")))); break;
					case Id.CIF: cl.setId(new Dni(App.cipher.decode(jsonObj.getString("id")))); break;
				}
				
				cl.setName(App.cipher.decode(jsonObj.getString("name")));
				cl.setTlf_1(App.cipher.decode(jsonObj.getString("tlf_1")));
				cl.setTlf_2(App.cipher.decode(jsonObj.getString("tlf_2")));
				cl.setMail(App.cipher.decode(jsonObj.getString("mail")));
				cl.setAddress(App.cipher.decode(jsonObj.getString("address")));
				
				r.add(cl);
			}
	    }
	    catch (Exception e) {e.printStackTrace();}
		
		return r;
	}

	@Override
	public ArrayList<Client> getCampaignClients(Campaign c) {
		ArrayList<Client> tr = new ArrayList<Client>();
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("campaign", c.getName()));
	    
	    JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_campaign_clients.php", nameValuePairs);
		
	    try {
			for (int i = 0; i < jsonArray.length(); i++) {
				Client cl = new Client();
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				
				switch (jsonObj.getInt("idType")) {
					case Id.DNI: cl.setId(new Dni(jsonObj.getString("id"))); break;
					case Id.NIE: cl.setId(new Nie(jsonObj.getString("id"))); break;
					case Id.CIF: cl.setId(new Dni(jsonObj.getString("id"))); break;
				}
				cl.setName(jsonObj.getString("name"));
				cl.setTlf_1(jsonObj.getString("tlf_1"));
				cl.setTlf_2(jsonObj.getString("tlf_2"));
				cl.setMail(jsonObj.getString("mail"));
				cl.setAddress(jsonObj.getString("address"));
				
				tr.add(cl);
			}
	    }
	    catch (Exception e) {e.printStackTrace();}
		
		return tr;
	}

	@Override
	public Client clientExists(String id) {
		Client client = null;
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", App.cipher.decode(id)));
		
	    JSONArray jsonArray = getDataFromDB(App.external_path+"/db_client_exists.php", nameValuePairs);
		
	    try {
		    if (jsonArray.length() > 0) {
				client = new Client();
				JSONObject jsonObj = jsonArray.getJSONObject(0);
				
				client.setName(App.cipher.decode(jsonObj.getString("name")));
				client.setTlf_1(App.cipher.decode(jsonObj.getString("tlf_1")));
				client.setTlf_2(App.cipher.decode(jsonObj.getString("tlf_2")));
				client.setMail(App.cipher.decode(jsonObj.getString("mail")));
				client.setAddress(App.cipher.decode(jsonObj.getString("address")));
			}
	    }
	    catch (Exception e) {e.printStackTrace();}

		return client;
	}

	@Override
	public boolean addService(Service s, Client c) {
		boolean tr = true;
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("idClient", c.getId().toString()));
	    nameValuePairs.add(new BasicNameValuePair("service", s.getService()));
	    nameValuePairs.add(new BasicNameValuePair("campaign", s.getCampaign()));
	    nameValuePairs.add(new BasicNameValuePair("tlf_1", s.getTlf_1()));
	    nameValuePairs.add(new BasicNameValuePair("tlf_2", s.getTlf_2()));
	    nameValuePairs.add(new BasicNameValuePair("commission", s.getCommission()+""));
	    nameValuePairs.add(new BasicNameValuePair("date", s.getDate().toGMTString()));
		
		try {
			getDataFromDB(App.external_path+"/db_add_service.php", nameValuePairs);
		}
		catch (SQLException e) { e.printStackTrace(); tr = false;}
		
		return tr;
	}

	@Override
	public ArrayList<Service> getServices(String id) {
		ArrayList<Service> tr = new ArrayList<Service>();
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("idClient", id));
	    
	    JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_services.php", nameValuePairs);
	    
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				Service s = new Service(jsonObj.getString("service"), jsonObj.getInt("commission"));
				
				s.setExtId(jsonObj.getInt("id"));
				s.setCampaign(jsonObj.getString("campaign"));
				s.setDate(new Date(jsonObj.getString("date")));
				s.setTlf_1(jsonObj.getString("tlf_1"));
				s.setTlf_2(jsonObj.getString("tlf_2"));
				
				tr.add(s);
			}
		}
		catch (Exception e) {e.printStackTrace();}
		
		return tr;	
	}

	@Override
	public int getSumCommissions(Client c) {		

	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("idUser", App.user.getExtId()+""));
	    nameValuePairs.add(new BasicNameValuePair("idClient", c.getId().toString()));
	    
		int res = 0;
	    JSONArray jsonArray = getDataFromDB(App.external_path+"/db_get_sum_commissions.php", nameValuePairs);
		 
	    try {
			if (jsonArray.length() > 0) {
				res = jsonArray.getJSONObject(0).getInt("SUM(commission)");
			}
	    } 
	    catch (Exception e) {e.printStackTrace();}
		
		return res;
	}

	@Override
	public void deleteService(Service service) {
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("id", service.getExtId()+""));
	 
	    getDataFromDB(App.external_path+"/db_delete_service.php", nameValuePairs);
	}
	
	@Override
	public void changePassword(String user, String newpass) {
		
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("user", user));
	    nameValuePairs.add(new BasicNameValuePair("newpass", newpass));
	 
	    getDataFromDB(App.external_path+"/db_change_password.php", nameValuePairs);
	}
}
