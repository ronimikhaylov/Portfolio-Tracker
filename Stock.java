package proj3sp22;
/**
 * Title: Stock Class
 * Description: organizes the data of the stock object and retrieves the tickerSym, 
 * shares owned and the purchase price of the stock. 
 * @author Roni Mikhaylov
 *
 */
public class Stock {
	private String tickerSym;
	private int sharesOwned;
	private double purchasePrice;
/**
 * Parameterized constructor - initializes all instance variables to whatever the values passed 
 * through the parameters are
 * @param numShares
 * @param buyPrice
 * @param ticker
 */
	public Stock (int numShares, double buyPrice, String ticker)
	{
		sharesOwned = numShares;
		purchasePrice = buyPrice;
		tickerSym = ticker;
	}
	/**
	 * getTickerSymbol - accessor method that returns the ticker symbol of the company
	 * @return tickerSym
	 */
	public String getTickerSymbol() {
		return tickerSym;
	}

	/**
	 * getSharesOwed - accessor method that returns the the number of shares owned of a certai company
	 * @return sharesOwned
	 */
	public int getSharesOwned() {
		return sharesOwned;
	}

	public double getPurchasePrice(){
		return purchasePrice;
	}
	/**
	 * setSharesOwned - mutator method which sets the number of shares owned 
	 * to whatever is passed through the parameter
	 */
	public void setSharesOwned(int setShares) {
		sharesOwned = setShares;
	}
	/**
	 * toString method - returns the state of the stock object
	 * @return str
	 */
	public String toString() {
		String str = new String();
		str = sharesOwned + " shares owned of " +  tickerSym + " bought at " + purchasePrice;
		return str;
	}

}
