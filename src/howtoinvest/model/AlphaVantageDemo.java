package howtoinvest.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlphaVantageDemo implements IStockDataRetrieval {

  private final String apiKey = "W0M1JOKC82EZEQA8";
  private final String updateInterval = "1min";
  private final String datePattern = "yyyy-MM-dd";
  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
  private URL url;


  private String getCurrentSharePrice(String tickerName) {
    setURL(tickerName, true);
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {

      addToOutput(in, output, url);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("No price data found for " + tickerName);
    }
    return output.toString().split("\n")[1].split(",")[1];
  }



  @Override
  public boolean checkValidityOfTickerName(String tickerName) {
    setURL(tickerName, true);
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {

      addToOutput(in, output, url);
    } catch (IllegalArgumentException e) {
      return false;
    }
    return !output.toString().toLowerCase().contains("Error Message".toLowerCase());
  }



  @Override
  public double retrieveSharePrice(String date, String tickerName) throws ParseException{

    setURL(tickerName, false);

    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {

      addToOutput(in, output, url);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("No price data found for " + tickerName);
    }


    /**
     * Converting the date to retrieve data to date format.
     */
    String[] dailySharePrices = output.toString().split("\n");

    if(dailySharePrices.length==0 || dailySharePrices.length==1){
      throw new IllegalArgumentException("No share prices provided.");
    }

    Date dateToFind = simpleDateFormat.parse(date);
    Date currentDate;
    double closestShareValue = Double.parseDouble(dailySharePrices[1].split(",")[1]);

    /**
     * If the date to find is today, then return today or the latest share value as the
     * share value.
     */
    if(dateToFind.equals(simpleDateFormat.parse(simpleDateFormat.format(new Date())))){
      return Double.parseDouble(this.getCurrentSharePrice(tickerName));
    }
    /**
     * Cannot fetch share value for a future date that is input.
     */
    else if(dateToFind.after(simpleDateFormat.parse(simpleDateFormat.format(new Date())))){
      throw new IllegalArgumentException("Cannot fetch share value for a future date");
    }


    /**
     * Cannot fetch further history.
     */
    if(dateToFind.before(simpleDateFormat.parse(dailySharePrices[dailySharePrices.length-1].
            split(",")[0]))){
      throw new IllegalArgumentException("Share prices do not exist for given date.");
    }


    for(int day = dailySharePrices.length-1; day>=1; day--){
      currentDate = simpleDateFormat.parse(dailySharePrices[day].split(",")[0]);
      if(dateToFind.equals(currentDate)){
        return Double.parseDouble(dailySharePrices[day].split(",")[4]);
      }
      /**
       * If the date to be found is after the current day's share value then we update
       * the closest share value.
       */
      else if(dateToFind.after(currentDate)){
        closestShareValue = Double.parseDouble(dailySharePrices[day].split(",")[4]);
      }
    }
    return closestShareValue;
  }



  private void addToOutput(InputStream in, Appendable out, URL url) {
    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        out.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data.");
    }
  }



  private void setURL(String tickerName, boolean intraday){
    String urlSuffix = "";
    String function = "TIME_SERIES_DAILY";
    if(intraday){
      urlSuffix = "&interval=" + updateInterval;
      function = "TIME_SERIES_INTRADAY";
    }
    try {

      url = new URL("https://www.alphavantage"
              + ".co/query?function=" + function
              + "&outputsize=full"
              + "&symbol"
              + "=" + tickerName + urlSuffix + "&apikey=" + apiKey
              + "&datatype=csv");


    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
  }


}
