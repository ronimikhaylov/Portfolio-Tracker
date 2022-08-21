package proj3sp22;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Title: SymbolTable Class
 * Description: Organizes the symbolPairs and the number of symbols within the text files and given data overall.
 * @author Roni Mikhaylov
 *
 */
public class SymbolTable {
	private SymbolPair[] symbolPairs;
	private int numSymbols;
	
	/**
	 * SymbolTable parameterized constructor - intilializes the array of
	 * symbolPairs to 45 (oversizing because we dont know how many are in the file)
	 * @param file
	 */
	public SymbolTable(File file) {
		// sizing it before reading the file (sizing it bigger than what we need)
		symbolPairs = new SymbolPair[45];
		numSymbols  = 0; // is zero because we don't have any symbols yet

		try (Scanner input = new Scanner(file)) {
			// reading the file:
			while (input.hasNextLine()) {
				String symbol = input.next();
				String companyName = input.nextLine();
				// creating a new symbol pair based on the data read from the file
				symbolPairs [numSymbols] = new SymbolPair(symbol.trim(), companyName.trim());
				numSymbols ++;
			}


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * findCompany - like a search method but for companies.
	 * iterates through the array to check if the value passed through the parameter is 
	 * inside the array. 
	 * @param tickerSym
	 * @return the name of the company based on the tickerSymbol found
	 * @return null if the company was not found
	 */
	public String findCompany(String tickerSym) {
		for (int i = 0; i<numSymbols; i++) {
			if (symbolPairs[i].getTickerSymbol().equals(tickerSym)) {
				return symbolPairs[i].getCompanyName();
			}
		}
		return null;

	}

	/**
	 * accessor method to get and return the number of symbols
	 * @return numSymbols
	 */
	public int getNumSymbols() {
		return numSymbols;
	}

	/**
	 * toString method - increments through the symbol pair array and displays the ticker symbol and 
	 * the name of the company
	 * @return str
	 */
	public String toString() {
		String str = new String();
		for (int i = 0; i < numSymbols; i++) {
			str += symbolPairs [i].toString() + "\n";
		}
		return str;
	}
}
