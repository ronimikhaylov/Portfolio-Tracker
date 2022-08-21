package proj3sp22;
import java.io.File;
import java.util.Scanner;

public class SymbolPair {
	/**
	 * Title: Symbol Pair Class
	 * Description: This class puts together the ticker symbol and the company name for each listed company
	 * in the text file.
	 * @author Roni Mikhaylov
	 */
	private String tickerSym;
	private String companyName;

	/**
	 * parameterizedConstructor - initializes tickerSym and companyName to what gets passed through
	 * the parameters
	 * @param ticker
	 * @param cName
	 */
	public SymbolPair(String ticker, String cName)  
	{
		tickerSym = ticker;
		companyName = cName;
	}




	/**
	 * accessor method to get and return the ticker symbol
	 * @return tickerSym
	 */
	public String getTickerSymbol() 
	{
		return tickerSym;
	}

	/**
	 * accessor method to get and return the company name
	 * @return companyName
	 */
	public String getCompanyName() 
	{
		return companyName;
	}


	/**
	 * toString - returns the full name of the company with the ticker symbol and company name side by side
	 * @return str 
	 */
	public String toString()
	{
		String str = tickerSym + " " + companyName;
		return str;
	}
}