import okhttp3.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;

import org.json.JSONObject;
public class CurrencyConverterAPI {

	/**
	 * Link/s:
	 * https://www.baeldung.com/java-org-json
	 */	
	private JSONObject rates = null;
	private final OkHttpClient httpClient = new OkHttpClient();
	private boolean isConnected = false;
	private ArrayList<String> symbols = null;
	
	//Constructor
	public CurrencyConverterAPI() {
		symbols = new ArrayList<>();
		getRates();
	}
	
	//Getters
	public JSONObject getRates(String base, boolean forceRefetch){
		try {
			fetchRates(base, forceRefetch);
			return rates.getJSONObject("rates");
		} catch (Exception e) {
			System.err.println("Error: Cannot fetch currency rates. Possible internet connection error.");
			return null;
		}
	}
	
	public JSONObject getRates(){
		return getRates(null, false);
	}
	
	public String getFetchDate() {
		try {
			return rates.getString("date");
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getBase() {
		try {
			return rates.getString("base");
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getSymbols() {
		return Arrays.toString(symbols.toArray());
	}
	
	public static String getFullCurrency(String key) {
		return Currency.getInstance(key).getDisplayName();
	}
	
	public boolean hasConnection() {
		try {
			fetchRates(null, true);
			return isConnected;
		} catch (Exception e) {
			return false;
		}
		
	}
	public boolean hasSymbol(String symbol) {
		return symbols.contains(symbol);
	}
	
	//Mutators
	private JSONObject sendGet(String url) throws Exception {
		Request request = new Request.Builder().url(url).build();
        try (Response response = httpClient.newCall(request).execute()) {
        	if (!response.isSuccessful()) {
        		isConnected = false;
        		throw new IOException("Unexpected code " + response);
        	}
        	isConnected = true;
        	return new JSONObject(response.body().string());           
        }
    }
	private void fetchRates(String base, boolean forceRefetch) throws Exception {
		base = base == null || base.length() == 0 ? (getBase() == null ? Currency.getInstance(Locale.getDefault()).getCurrencyCode() : getBase()) : base.trim().toUpperCase();
		if(rates == null || !getBase().equalsIgnoreCase(base) || forceRefetch) {
			rates = sendGet("https://api.exchangeratesapi.io/latest?base="+base);
			//Refresh List of Currency Symbols for Optimization
			Iterator<String> keys = rates.getJSONObject("rates").keys();
			if(keys.hasNext()) symbols.clear();
			while(keys.hasNext()) {
				symbols.add(keys.next());
			}
			Collections.sort(symbols);
			
		}
	}
	
	//Displays
	public String convert(String base) {
		return convert(1, base, null, false);
	}
	public String convert(double amount, String base) {
		return convert(amount, base, null, false);
	}
	public String convert(String base, String target) {
		return convert(1, base, target, false);
	}
	public String convert(String base, String target, boolean forceRefetch) {
		return convert(1, base, target, forceRefetch);
	}
	
	public String convert(double amount, String base, String target) {
		return convert(amount, base, target, false);
	}
	
	public String convert(double amount, String base, String target, boolean forceRefetch) {
		String message = "", targets[] = null;
		Iterator<String> keys = getRates(base, forceRefetch).keys();
		targets = target != null ? target.split(",") : null;
		if(targets == null) {
			while(keys.hasNext()) {
	    		String key = keys.next();
	    		double exchange = amount * getRates().getDouble(key);
	    		message+=String.format("%,3.2f %s = %,3.2f %s (%s)%n", amount, getBase(), exchange, key, CurrencyConverterAPI.getFullCurrency(key));
	    	}
		}
		else {
			for (String key : targets) {
				key = key.trim().toUpperCase(); //clean first, user might have inserted spaces
				if(getRates().has(key)) {
					double exchange = amount * getRates().getDouble(key);
					message+=String.format("%,3.2f %s = %,3.2f %s (%s)%n", amount, getBase(), exchange, key, CurrencyConverterAPI.getFullCurrency(key));
				}
			}
		}
		if(message.length() != 0) {
			message = String.format("Exchange Rate Fetch Date: %s%nBase Currency: %s (%s)%n%n%s", getFetchDate(), getBase(), getFullCurrency(getBase()), message);
		}
    	return message;
	}
	
	public String displaySymbols() {
		String message = "";
		Iterator<String> keys = getRates().keys();
		while(keys.hasNext()) {
			String key = keys.next();
			message+=String.format("%s (%s)%n", key, CurrencyConverterAPI.getFullCurrency(key));
		}
		if(!message.isEmpty()) {
			message = "Here are the available currencies:\n" + message;
		}
		return message;
	}
}