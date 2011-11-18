package com.android.rhinos.gest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

import android.util.Log;

public class Campaign implements Serializable, Comparable<Campaign> {

	private String name;
	private HashMap<String, Service> services;
	
	private static final long serialVersionUID = 1L;
	
	public Campaign() {
		name = "";
		services = new HashMap<String, Service>();
	}
	
	public Campaign (String name) {
		this();
		this.name = name;
	}
	
	public Service getCommissions(String str) {
		return services.get(str);
	}
	
	public String getDetails() {
		String r =	""+name+":\n\n";
		
		ArrayList<Service> s = new ArrayList<Service>(services.values());
		Collections.sort(s);
		
		for (Service d : s) {
			r += d + "\n";
		}
		return r;
	}
	
	public String getName() {
		return name;
	}
	
	public HashMap<String, Service> getServices() {
		return services;
	}
	
	public void addService(String k, Service v) {
		services.put(k, v);
	}
	
	public boolean load(URL url) {
		boolean status = true;
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			StringTokenizer st;
					
			if ((in != null) && (in.ready())) {
				name = in.readLine();
				
				//Loading campaign
				String line;
				while ((line = in.readLine()) != null) {
					st = new StringTokenizer(line, "|");
					Service c = new Service(st.nextToken(), st.nextToken());
					services.put(c.getService(), c);
				}
			}
		}
		catch (Exception e) {
			Log.e("", ""+e.toString());
			status = false;
		}
		finally {
			try {
				if (in != null)
					in.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return status;
	}
	
	public static ArrayList<Campaign> containerLoader(URL url) {	
		BufferedReader _in = null;
		ArrayList<Campaign> result = new ArrayList<Campaign>();
		try {
			_in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			//Loading campaigns
			String line;
			while ((line = _in.readLine()) != null) {
				Campaign i = new Campaign();
				i.load(new URL(line));
				result.add(i);
			}
		}
		catch (Exception e) {
			return null;
		}
		finally {
			try {
				if (_in != null)
					_in.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Campaign another) {
		return getName().compareTo(another.getName());
	}
}