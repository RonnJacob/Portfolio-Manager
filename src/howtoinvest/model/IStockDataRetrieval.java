package howtoinvest.model;

import java.text.ParseException;

public interface IStockDataRetrieval {

  boolean checkValidityOfTickerName(String tickerName);

  double retrieveSharePrice(String date, String tickerName) throws ParseException;
}
