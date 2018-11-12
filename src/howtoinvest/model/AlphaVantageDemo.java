package howtoinvest.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlphaVantageDemo {

  public String getCurrentSharePrice(String tickerName){
    String apiKey = "W0M1JOKC82EZEQA8";
    URL url = null;
    String updateInterval = "1min";
    try {

      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_INTRADAY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + tickerName + "&interval="+updateInterval+"&apikey="+apiKey+"&datatype=csv");


    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try{

      addToOutput(in, output,url);
    }
    catch(IllegalArgumentException e){
      throw new IllegalArgumentException("No price data found for "+tickerName);
    }
    /**
     * Returns the open value of the latest share price.
     */
    return output.toString().split("\n")[1].split(",")[1];
  }

  public boolean checkValidityOfTickerName(String tickerName){
    String apiKey = "W0M1JOKC82EZEQA8";
    URL url;
    String updateInterval = "1min";
    try {

      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_INTRADAY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + tickerName + "&interval="+updateInterval+"&apikey="+apiKey+"&datatype=csv");


    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try{

      addToOutput(in, output,url);
    }
    catch(IllegalArgumentException e){
      throw new IllegalArgumentException("No price data found for "+tickerName);
    }
    return !output.toString().toLowerCase().contains("Error Message".toLowerCase());
  }

  public double retrieveSharePrice(String date, String tickerName) throws ParseException {

    Date date1  =new SimpleDateFormat("yyyy-MM-dd").
            parse(date);

    String apiKey = "W0M1JOKC82EZEQA8";
    URL url = null;
    try {

      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + tickerName + "&apikey="+apiKey+"&datatype=csv");


    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try{

      addToOutput(in, output,url);
    }
    catch(IllegalArgumentException e){
      throw new IllegalArgumentException("No price data found for "+tickerName);
    }
    String[] eachDayStock = output.toString().split("\n");


    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    /**
     * Initialize the closest date to the earliest date that can be retrieved.
     */
    Date dateToCompare = new SimpleDateFormat("yyyy-MM-dd").
            parse(eachDayStock[eachDayStock.length-1].split(",")[0]);


    Date finalDateAvailable = new SimpleDateFormat("yyyy-MM-dd").
            parse(eachDayStock[1].split(",")[0]);

    if((date1.before(new Date()) && date1.after(finalDateAvailable)) || date1.equals(new Date())){
      System.out.println(output.toString());
      return Double.parseDouble(eachDayStock[1].split(",")[4]);
    }

    if(date1.after(finalDateAvailable)){
      throw new IllegalArgumentException("Invalid date. Cannot invest in future date.");
    }

    /**
     * Lowest value of selected date.
     */
    String closestValue = "";
    if(dateToCompare.equals(date1)){
      System.out.println(eachDayStock[eachDayStock.length-1].split(",")[3]);
    }
    else if(date1.before(dateToCompare)){
      throw new IllegalArgumentException("Cannot fetch share price details beyond "
              +formatter.format(date1));
    }
    for(int i = eachDayStock.length-1; i>=1; i--){
      dateToCompare=new SimpleDateFormat("yyyy-MM-dd").
              parse(eachDayStock[i].split(",")[0]);
      if(date1.after(dateToCompare)){
        closestValue = eachDayStock[i].split(",")[4];
      }
      /**
       * Return the low value for that day if we're retrieving share prices for an available day.
       */
      else if(date1.equals(dateToCompare)){
        closestValue = eachDayStock[i].split(",")[3];
        break;
      }
      /**
       * Retrieve the price for the day before if a day is either a holiday or the stock exchange
       * hasn't functioned that day.
       */
      else if(date1.before(dateToCompare)){
        break;
      }
    }
    return Double.parseDouble(closestValue);
  }

  private void addToOutput(InputStream in, Appendable out, URL url){
    try {
      in = url.openStream();
      int b;

      while ((b=in.read())!=-1) {
        out.append((char)b);
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data.");
    }
  }
 }
