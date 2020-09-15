import java.util.Currency;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import CurrConvertApp.*;

public class MainClient {
	private static Scanner c;
	private static int choice;
	private static CurrConvert converterObj;
	private static String baseCurrency, targetCurrency;
	private static double baseAmount;
	public static void main(String[] args) {
		try {
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef =   orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			converterObj = (CurrConvert) CurrConvertHelper.narrow(ncRef.resolve_str("ABC"));
			c = new Scanner(System.in);
			//Display Menu
			displayMainMenu();
		}
		catch (Exception e) {
			System.out.println("Hello Client exception: " + e);
			e.printStackTrace();
		}
	}
	
	private static void displayAppName() {
		System.out.println("**********************************************");
		System.out.println("* Welcome to A Wise Man's CURRENCY CONVERTER *");
		System.out.println("**********************************************");
	}
	private static void displayMainMenu() {
		displayAppName();
		System.out.println("What do you want to do today?");
		System.out.println("1 ==> Convert One Currency to All Currencies");
		System.out.println("2 ==> Convert One Currency to Another Currency");
		System.out.println("3 ==> Convert One Currency to One or More Currencies");
		System.out.println("4 ==> Display All Currency Symbols and Meaning");
		System.out.println("5 ==> Close Client & Server");
		choice = getIntInput(5);
		switch (choice) {
		case 1: //Convert One Currency to All Currencies
			convertOneToAll();
			break;
		case 2: //Convert One Currency to Another Currency
			convertOneToAnother();
			break;
		case 3: //Convert One Currency to One or More Currencies
			convertOneToSome();
			break;
		case 4: //Display All Currency Symbols and Meaning
			displaySymbolsAndMeaning();
			break;
		case 5: //Close Client
			converterObj.shutdown();
			break;
		default:
			break;
		}
	}
	private static void convertOneToAll() {
		System.out.println("Here's the list of available currencies:");
		System.out.println(converterObj.getSymbols());
		System.out.println("What's the base currency?");
		baseCurrency = getCurrencyInput();
		System.out.println("Want to provide base amount? (Default: 1)");
		System.out.println("1 - Yes");
		System.out.println("2 - No");
		switch (getIntInput(2)) {
		case 1:
			baseAmount = getAmountInput();
			System.out.println(converterObj.convertAllWithAmount(baseAmount, baseCurrency));
			break;
		case 2:
			System.out.println(converterObj.convertAll(baseCurrency));
		default:
			break;
		}
		displayMainMenu();
	}
	private static void convertOneToAnother() {
		System.out.println("Here's the list of available currencies:");
		System.out.println(converterObj.getSymbols());
		System.out.println("What's the base currency?");
		baseCurrency = getCurrencyInput();
		System.out.println("Want to provide base amount? (Default Amount: 1.00)");
		System.out.println("1 - Yes");
		System.out.println("2 - No");
		if (getIntInput(2) == 1) baseAmount = getAmountInput();
		else baseAmount = 1;
		System.out.println("What's the target currency?");
		targetCurrency = getCurrencyInput();
		System.out.println(converterObj.convertSomeWithAmount(baseAmount, baseCurrency, targetCurrency));
		displayMainMenu();
	}
	private static void convertOneToSome() {
		System.out.println("Here's the list of available currencies:");
		System.out.println(converterObj.getSymbols());
		System.out.println("What's the base currency?");
		baseCurrency = getCurrencyInput();
		System.out.println("Want to provide base amount? (Default Amount: 1.00)");
		System.out.println("1 - Yes");
		System.out.println("2 - No");
		if (getIntInput(2) == 1) baseAmount = getAmountInput();
		else baseAmount = 1;
		System.out.println("What are the target currencies? (Use comma to separate)");
		targetCurrency = getMoreCurrencyInput();
		System.out.println(converterObj.convertSomeWithAmount(baseAmount, baseCurrency, targetCurrency));
		displayMainMenu();
	}
	private static void displaySymbolsAndMeaning() {
		System.out.println(converterObj.displaySymbols());
		displayMainMenu();
	}
	
	//Useful Methods
	private static int getIntInput(int max) {
		int num;
		for(;;) {
			try {
				System.out.print(">>> Enter choice: ");
				num = Integer.parseInt(c.nextLine().trim());
				if(num < 1 || num > max) {
					throw new Exception();
				}else {
					return num;
				}
			} catch (Exception e) {
				System.err.println("Error: Please choose a number from 1 to "+max+".");
			}
		}
	}
	
	private static double getAmountInput() {
		double amount;
		for(;;) {
			try {
				System.out.print(">>> Enter amount: ");
				amount = Integer.parseInt(c.nextLine().trim());
				if(amount < 0) throw new Exception();
				else return amount;
			} catch (Exception e) {
				System.err.println("Error: Please provide a valid amount.");
			}
		}
	}
	
	private static String getCurrencyInput() {
		String currency;
		for(;;) {
			try {
				System.out.print(">>> Enter currency: ");
				currency = c.nextLine().trim().toUpperCase();
				if(!converterObj.symbolExist(currency)) throw new Exception();
				return currency;
			} catch (Exception e) {
				System.err.println("Error: Please choose one from available currencies: "+converterObj.getSymbols());
			}
		}
	}
	
	private static String getMoreCurrencyInput() {
		String currency;
		for(;;) {
			try {
				System.out.print(">>> Enter currencies: ");
				currency = c.nextLine().trim().toUpperCase();
				for (String str : currency.split(",")) {
					str = str.trim();
					if(!converterObj.symbolExist(str)) throw new Exception();
				}
				return currency;
			} catch (Exception e) {
				System.err.println("Error: One or more currencies are not available.");
				System.err.println("Error: Please choose from available currencies: "+converterObj.getSymbols());
			}
		}
	}
}
