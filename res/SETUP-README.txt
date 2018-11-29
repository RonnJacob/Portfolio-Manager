README
------

Setup
-----
- Move the Assignment8 jar file to any desired location. 
- Ensure app.config file is present in the same folder and all the supporting 
files like the csv files for the stock data exist in the same folder as the jar.


Running the program.
--------------------
- Open terminal/cmd and navigate to the folder where the Assignment8 jar exists.
- Set the READ_FROM_API to 0 in app.config if you want to read the stock data from .csv files
  else 1 if you want to get the stock data from Alpha Vantage api.
- Now type java -jar Assignment8.jar and press Enter.

How To Use
-----------
- On opening the jar, the portfolio manager is opened with options numbered 
from 1-3 and an option to quit the program by inputting 'q'.
- The inputs required by the user are prompted by the application and the format
is specified as well.
- There is a default strategy with MSFT stock which can be used.

Stock Data File Instructions
-----------------------------
- Create a .csv file with the name of the ticker symbol. eg: MSFT.csv 
(ticker symbol for Microsoft).
- Each line should represent the stock data for the current date which would be
in the format - date,share price.
eg: 2018-11-11,150
- Ensure that the date is in the format yyyy-mm-dd. Example files are given
in the res folder for reference.

Alpha Vantage API
--------------------
- The alpha vantage API would generate a .csv file with the ticker symbol appended
by '_AlphaVantage' and would contain open, low, high, close prices and volume of shares for
a particular date in CSV format.
- The .csv file would be populated by the API.

Commission File Instructions
-----------------------------
- Three presets can be set in the commission.csv file that is present in the res folder in the
format l,m,h which are comma separated. These three values would correspond to low commision value
, medium commission value and high commission value.