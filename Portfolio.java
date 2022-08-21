package proj3sp22;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import queues.LinkedQueue;
/**
 * Title: Portfolio class
 * Description:Description: This class will calculate the profit and loss of the of the stocks purchased and bought and 
 * will also dequeue it from the companyQueues when sold. The portfolio will state the stock sold, or purchased, the number
 * of shares owned of a company at an average price and the total profit or loss
 * @author Roni Mikhaylov
 */

import exceptionclasses.EmptyQueueException;
public class Portfolio {
	private LinkedQueue <Stock>[] companyQueues;
	private int numCompanies;
	private SymbolTable symbols;
	private double gainLoss;


	/**
	 * parameterized constructor - assigns symbols to parameter 'syms' and initializes the companyQueue to  a new array
	 * @param syms
	 */
	public Portfolio (SymbolTable syms) {
		symbols = syms;
		companyQueues = new LinkedQueue[symbols.getNumSymbols()];

	}
	/**
	 * processTransaction method - takes in parameters of a transaction and calculates profit and loss 
	 * and determines whether sales are allowed to be made. 
	 * @param bSell
	 * @param numShare
	 * @param pPrice
	 * @param tickSym
	 */
	public void processTransaction (char bSell, int numShare, double pPrice, String tickSym) {
		String companyName = symbols.findCompany(tickSym);
		// creating Number format variable to print currency
		NumberFormat df = DecimalFormat.getCurrencyInstance();
		// declaring primitive variable named index and assigning it to the position of the company represented by tickSym
		int index = 0;
		try {
			index = findCompany(tickSym);
		} catch (EmptyQueueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if the company doesn't have a queue, create a new one
		if (index == -1) {
			// if this is a buy transaction, a new queue is created
			if (bSell == 'b') {
				companyQueues [numCompanies] = new LinkedQueue<>();
				index = numCompanies;
				numCompanies++;
			}
			// if its a sell transaction, throw the exception. (You cant sell when you do not have the queue)
			else {
				throw new InvalidSaleException("Unable to sell " + companyName + ". You do not own it.");
			}
		}
		//getting a reference to this company's queue of stock objects
		LinkedQueue<Stock> stockQueue = companyQueues[index];
		// buying stocks and enqueuing stock objects
		if (bSell == 'b') {
			Stock stock = new Stock(numShare, pPrice, tickSym);
			// buying and enqueuing a stock onto the queue
			stockQueue.enqueue(stock);
			System.out.println("Buy " +numShare + " shares of " + symbols.findCompany(tickSym) + " at " + df.format(pPrice));
		}

		//if the text file says its a sell transaction
		else if(bSell == 's') {
			System.out.println("Below, will be selling: " + symbols.findCompany(tickSym) + " " + numShare + " at " + df.format(pPrice));

			// intiializing totalShares to zero to accumalate sales by lot
			int totalShares = 0;
			// dequeuing and enqueuing to examine the items 
			LinkedQueue <Stock> temporaryQueue = new LinkedQueue<Stock>();
			while (!stockQueue.isEmpty()) {
				try {

					Stock stock  = stockQueue.dequeue();

					totalShares += stock.getSharesOwned();
					//re-enqueueing the stock into the queue because it was dequeued it to analyze it 
					temporaryQueue.enqueue(stock);

				}
				catch (EmptyQueueException ex) {
					break;
				}
			}
			//the StockQueue is now empty. replacing it with the temporary Queue; it was the only way to examine the amount of shares owned
			companyQueues[index] = temporaryQueue;
			// checking to see if we're selling the amount of shares we actually own; not more than what we own
			if (numShare>totalShares) {				
				throw new InvalidSaleException ("Trying to sell too many shares of "  + symbols.findCompany(tickSym) + " " +numShare + "shares\n"
						+ "You only have " + totalShares + " shares");
			}
			// trying to sell this many shares
			// assigning the number of shares remaining after each sale to variable "sharesSold"
			int sharesSold =numShare;
			stockQueue = temporaryQueue;
			// while loop is to confirm you are not selling more than you own
			while (!stockQueue.isEmpty() && sharesSold > 0) {
				try {
					// getting an existing stock object and assigning it to variable "stock"
					Stock stock  = stockQueue.dequeue();
					int lotSharesSold = 0;
					// if the amount of shares sold is greater or equal to the number of shares purchased in that lot 
					if (sharesSold >= stock.getSharesOwned()) {
						sharesSold -= stock.getSharesOwned();
						lotSharesSold = stock.getSharesOwned();
						stock.setSharesOwned(0);

					}
					else {
						// splitting up the lot. one lot is being sold partially. (ie, if buying 100 shares of MSFT, selling 20, 80 is remaining)
						stock.setSharesOwned(stock.getSharesOwned() - sharesSold);
						lotSharesSold =  sharesSold;
						sharesSold = 0;


					}
					//calculating profit and loss per Share but then multiplying it by lotShares sold making it the total profit and loss
					double pAndL = (pPrice - stock.getPurchasePrice()) * lotSharesSold;
					System.out.println("Sold Lot of " + symbols.findCompany(tickSym) + ". " + lotSharesSold + " Shares sold at " 
					+ df.format(pPrice) + " per share.");

					
					gainLoss += pAndL;
					// enquing the dequeued stock if there is a partial amount remaining, otherwise it wont be enqueued
					if(stock.getSharesOwned()>0) {
						stockQueue.enqueue(stock);

					}
					if (stockQueue.isEmpty()) {
						for (int i =  index; i<companyQueues.length - 1; i++) {
							//shifting/moving everything up from the index and beyond. 
							companyQueues[i] = companyQueues[i+1];
						}
						companyQueues[companyQueues.length - 1] = null;
						numCompanies--;
					}
				}
				catch (EmptyQueueException ex) {
					// if the queue is empty... get out of the loop
					break;
				}
			}

		}
	}
	/**
	 * findCompany - returns the position of a queue of a company in the companyQueues array
	 * @param tickSymbol
	 * @return index position of the company's queue in the array or -1, if there is now queue.
	 * @throws EmptyQueueException 
	 */
	private int findCompany (String tickSymbol) throws EmptyQueueException {
		// for loop to iterate through each company Queue and find the position of the companysQueue in the array of the array
		for (int i = 0; i<numCompanies; i++) {
			LinkedQueue<Stock> queue = companyQueues[i];

			if(queue.front().getTickerSymbol().equals(tickSymbol)){
				return i;
			}

		}  
		return -1;
	}
	/**
	 * toString method - returns the profit and loss and the state of the portfolio object.
	 * @return str 
	 */
	public String toString() {
		String str = new String();
		str = "Your portfolio currently contains: ";
		NumberFormat df = DecimalFormat.getCurrencyInstance();
		// for loop to iterate through the companyQueues and, if they're not empty, it accumalates the totalShares, totalPrice to become a part
		// of the return statement 
		for(int i = 0; i < numCompanies; i++) {
			LinkedQueue<Stock> temporaryQueue = new LinkedQueue<Stock>();
			int totalShares = 0;
			double totalCosts = 0.0;
			String companyName = null;

			while(!companyQueues[i].isEmpty()) {
				try {
					Stock stock = companyQueues[i].dequeue();
					totalShares+= stock.getSharesOwned();
					totalCosts += stock.getSharesOwned() * stock.getPurchasePrice();
					companyName = symbols.findCompany(stock.getTickerSymbol());
					temporaryQueue.enqueue(stock); 
				} catch (EmptyQueueException e) {

				}
			}
			companyQueues[i] = temporaryQueue;
			str+= "\n" + totalShares + " shares of " + companyName + " at an average price of " + df.format(totalCosts/totalShares);

		}
		str+= "\nYour total profit and loss is " + df.format(gainLoss) + "\n";
		return str;
	}
}
