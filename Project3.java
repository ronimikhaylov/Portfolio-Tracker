package proj3sp22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Title: Stock Project Application class
 * Description: This class takes in input from the given stock text files and
 *  uses methods from the Portfolio class, and Symboltable class to display the each transaction listed in the text file 
 *  as well the stocks in the portfolio and the profit/loss.
 * @author Roni Mikhaylov
 *
 */
public class Project3 {

	public static void main(String[] args) throws FileNotFoundException {
		//testing SymbolTable class
		SymbolTable st = new SymbolTable(new File("symboldata.txt"));
		Portfolio port = new Portfolio(st);
		try (Scanner input = new Scanner(new File("stockdata.txt")))
		{
			// loop to retrieve data from the files until there is not next line 
			while (input.hasNextLine()) {
				// gets input of whether its a buy or sell (b or s)
				char bOrS= input.next().charAt(0);
				// retrieves the numshares w/nextInt method
				int numShares = input.nextInt();
				// gets the price
				double price = input.nextDouble();
				// gets the ticker
				String ticker = input.nextLine().trim();
				try {
					// tries the process transaction method with all 
					port.processTransaction(bOrS, numShares, price, ticker);
					System.out.println(port.toString());
				}
				catch (InvalidSaleException s) {
					System.out.println(s.getMessage());
				}


			}
		}
	}
}
