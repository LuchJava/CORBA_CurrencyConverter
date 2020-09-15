public class Test {
	public static void main(String[] args) {
		CurrencyConverterAPI obj = new CurrencyConverterAPI();
		if(obj.getRates() != null ) {
//        	System.out.println("Date: "+obj.getFetchDate());
//        	System.out.println("Base: "+obj.getBase());
//        	System.out.println("Has connection: "+obj.hasConnection());
//        	System.out.println("Currencies: "+obj.getSymbols());
//        	System.out.println("Sample:\n"+obj.convert(1, "USD", "PHP, JPY, EUR", false));
//        	System.out.println("Sample:\n"+obj.convert("USD"));
        	System.out.println("Sample:\n"+obj.convert(200, "USD"));
        }
	}
}
