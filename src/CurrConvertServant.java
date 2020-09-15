
import org.omg.CORBA.ORB;

import CurrConvertApp.*;

public class CurrConvertServant extends CurrConvertPOA{

	private ORB orb;
	private CurrencyConverterAPI api;
	
	public CurrConvertServant() {
		api = new CurrencyConverterAPI();
	}
	
	public void setOrb(ORB orb) {
		this.orb = orb;
	}
	
	@Override
	public String getSymbols() {
		return api.getSymbols();
	}
	
	public String convertAll(String base) {
		return api.convert(base, null, false);
	}
	
	@Override
	public String convertAllWithAmount(double amount, String base) {
		return api.convert(amount, base, null, false);
	}
	
	@Override
	public String convertSome(String base, String target) {
		return api.convert(base, target, false);
	}
	
	@Override
	public String convertSomeWithAmount(double amount, String base, String target) {
		return api.convert(amount, base, target, false);
	}
	
	@Override
	public String displaySymbols() {
		return api.displaySymbols();
	}

	@Override
	public void shutdown() {
		orb.shutdown(false);
	}

	@Override
	public boolean symbolExist(String symbol) {
		return api.hasSymbol(symbol);
	}
}
