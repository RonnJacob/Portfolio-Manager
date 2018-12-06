README
------

Setup
-----
- Move the Assignment8 jar file to any desired location. 
- Ensure app.config file is present in the same folder and all the supporting 
  files like the csv files for the stock data, the folders Stock Portfolios and Strategies(save and load)
  exist in the same folder as the jar.


Running the program.
--------------------
- Open terminal/cmd and navigate to the folder where the Assignment8 jar exists.
- Set the READ_FROM_API to 0 in app.config if you want to read the stock data from .csv files
  else 1 if you want to get the stock data from Alpha Vantage api.
- Now type java -jar Assignment8.jar and press Enter.

How To Use
-----------
The jar provides two application, a console based and a gui based.
Opening by default will open the GUI application.

Open the jar with -view "console" in the command line to open the console application.
Console-
- On opening the jar, the portfolio manager is opened with options numbered 
from 1-4 and an option to quit the program by inputting 'q'.
- The inputs required by the user are prompted by the application and the format
is specified as well.
- There is a default strategy with MSFT stock which can be used.

Open the jar with -view "gui" in the command line to open the gui application.
GUI-
- A window each for Portfolio manager and Strategy manager are opened with the Portfolio manager
  in focus. The user can manually switch between these windows.
- If the user exits any one of the windows the application will be terminated.
- The inputs required by the user are prompted by the application and the format
  is specified as well.

The stocks and portfolios can be saved to json files in the system and these json files can be loaded
from the system. For more documentation on the format of these files refer FILE-FORMAT-README.txt.

To open these portfolios and strategies if the file name is portfolio1.json just enter portfolio1 in the application
when prompted. Same applies while saving.

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

Third party libraries
----------------------
Third party libraries dependencies are in the lib folder in src. These dependencies can be manually
added in the IntelliJ by adding the lib as a dependency library. ALl the dependencies are packaged
with the jar.

Swing license-
https://download.oracle.com/otndocs/jcp/java_se-8-mrel-spec/license.html

GSON license-
https://github.com/google/gson/blob/master/LICENSE

