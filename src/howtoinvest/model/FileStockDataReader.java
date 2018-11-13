package howtoinvest.model;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class FileStockDataReader implements IStockDataRetrieval {
  private final String extension = ".csv";
  private final String datePattern = "yyyy-MM-dd";
  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
  private StringBuilder output = new StringBuilder();

  @Override
  public boolean checkValidityOfTickerName(String tickerName) {
    File f = new File(tickerName + extension);
    if (f.exists() && !f.isDirectory()) {
      return true;
    }
    return false;
  }

  @Override
  public double retrieveSharePrice(String date, String tickerName) throws ParseException {
    /**
     * Initializing a scanner object for reading the .csv file.
     */
    Scanner scanner;
    try {
      scanner = new Scanner(new File(tickerName + extension));
    } catch (IOException ex) {
      throw new IllegalArgumentException("Cannot read from file.");
    }

    /**
     * Storing the output of the csv file to an appendable object i.e StringBuilder in this case.
     */
    scanner.useDelimiter(",");
    while (scanner.hasNext()) {
      this.output.append(scanner.next() + ",");
    }

    /**
     * Converting the date to retrieve data to date format.
     */
    String[] dailySharePrices = output.toString().split("\n");

    if (dailySharePrices.length == 0) {
      throw new IllegalArgumentException("No share prices provided.");
    }

    Date dateToFind = simpleDateFormat.parse(date);
    Date currentDate;
    double closestShareValue = Double.parseDouble(dailySharePrices[0].split(",")[1]);


    /**
     * If the date to find is today, then return today or the latest share value as the
     * share value.
     */
    if (dateToFind.equals(simpleDateFormat.parse(simpleDateFormat.format(new Date())))) {
      return closestShareValue;
    }
    /**
     * Cannot fetch share value for a future date that is input.
     */
    else if (dateToFind.after(simpleDateFormat.parse(simpleDateFormat.format(new Date())))) {
      throw new IllegalArgumentException("Cannot fetch share value for a future date.");
    }


    /**
     * Cannot fetch further history.
     */
    if (dateToFind.before(simpleDateFormat.parse(dailySharePrices[dailySharePrices.length - 1].
            split(",")[0]))) {
      throw new IllegalArgumentException("Share prices do not exist for given date.");
    }

    for (int day = dailySharePrices.length - 1; day >= 0; day--) {
      currentDate = simpleDateFormat.parse(dailySharePrices[day].split(",")[0]);
      if (dateToFind.equals(currentDate)) {
        return Double.parseDouble(dailySharePrices[day].split(",")[1]);
      }
      /**
       * If the date to be found is after the current day's share value then we update
       * the closest share value.
       */
      else if (dateToFind.after(currentDate)) {
        closestShareValue = Double.parseDouble(dailySharePrices[day].split(",")[1]);
      }
    }
    return closestShareValue;
  }
}
