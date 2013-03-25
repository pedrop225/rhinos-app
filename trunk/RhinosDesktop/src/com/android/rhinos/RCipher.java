package com.android.rhinos;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

public class RCipher {

	private Cipher c_enc;
	private Cipher c_dec;
	
	public RCipher(SecretKey key) {
		try {
			c_enc = Cipher.getInstance("DES");
			c_dec = Cipher.getInstance("DES");
			
			c_enc.init(Cipher.ENCRYPT_MODE, key);
			c_dec.init(Cipher.DECRYPT_MODE, key);
		} 
		catch (Exception e) {e.printStackTrace();}
	}
	
	public String encode(String word) {
		
		try {
			byte [] utf8 = word.getBytes("UTF8");
			byte [] enc = c_enc.doFinal(utf8);
			
			return Base64.encodeBase64String(enc)+'\n';
		} 
		catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
	
	public String decode(String word) {
		
		try {
			byte [] dec = Base64.decodeBase64(word);

			return (new String(c_dec.doFinal(dec), "UTF8"));
		} 
		catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
	
	public static SecretKey importKeyFromFile(String file) {
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(new File(file)));
			return ((SecretKey)ois.readObject());
		} 
		catch (Exception e) {e.printStackTrace();}
		finally {
			try {
				if (ois != null)
					ois.close();
			}
			catch (Exception e) {e.printStackTrace();}
		}
		
		return null;
	}
	
	public static SecretKey importKeyFromUrl(String url) {
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new URL(url).openStream());
			return ((SecretKey)ois.readObject());
		} 
		catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
}
