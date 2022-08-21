package proj3sp22;

public class InvalidSaleException extends RuntimeException {
	/**
	 * <p>Title:The Invalid Sale Exception Class</p>
	 *
	 * <p>Description: This class  consists of a constructor which is used when an InvalidSaleExtension 
	 * is thrown. It extends runtimeException and therefore is unchecked.  </p>
	 *
	 * <p>@author  Roni Mikhaylov</p>
	 */
	public InvalidSaleException(String m)
	{
		super(m);
	}
}

