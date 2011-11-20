package connectors;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

import com.android.rhinos.App;
import com.android.rhinos.gest.Campaign;
import com.android.rhinos.gest.Client;
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
            
            return new JSONArray(result);
	    }
	    catch (Exception e) {e.printStackTrace();}
	    
		return null;
	}
	
	public boolean login(String user, String password) {

	    //the mail data to send
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("user", user));
	    
	    try {
	        JSONArray jsonArray = getDataFromDB("http://pedrop225.comuf.com/rhinos/db_login.php", nameValuePairs);
	        JSONObject jsonObj = jsonArray.getJSONObject(0);
	            
	        if (jsonObj.getString("password").equals(password)) {
	        	App.user.setExtId(jsonObj.getInt("id"));
	        	App.user.setType(jsonObj.getInt("type"));
	        	App.user.setMail(jsonObj.getString("user"));
	        	return true;
	        }
	    }
	    catch (Exception e) {e.printStackTrace();}
        
        return false;
	}
	
	@Override
	public void clearCampaigns() {
		getDataFromDB("http://pedrop225.comuf.com/rhinos/db_login.php", null);
	}

	@Override
	public boolean addCampaign(Campaign camp) {

	    //the mail data to send
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("name", camp.getName()));
	    
	    try {
	    	//inserting into Campaings
	        getDataFromDB("http://pedrop225.comuf.com/rhinos/db_add_campaign.php", nameValuePairs);
	       
	        //looking up campaign id
	        JSONArray JsonArray = getDataFromDB("http://pedrop225.comuf.com/rhinos/db_get_id_campaign.php", nameValuePairs);
	        int c_id = JsonArray.getJSONObject(0).getInt("id");
	        
	        for (Service s : camp.getServices().values()) {
				nameValuePairs.clear();
				nameValuePairs.add(new BasicNameValuePair("id", ""+c_id));
				nameValuePairs.add(new BasicNameValuePair("service", s.getService()));
				nameValuePairs.add(new BasicNameValuePair("commission", ""+s.getCommission()));
				
				getDataFromDB("http://pedrop225.comuf.com/rhinos/db_insert_into_campinfo.php", nameValuePairs);
			}
	        
	        return true;
	    }
	    catch (Exception e) {e.printStackTrace();}
        
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
