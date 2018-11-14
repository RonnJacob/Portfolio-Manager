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
- Now type java -jar Assignment8.jar and press Enter.

How To Use
-----------
- On opening the jar, the portfolio manager is opened with options numbered 
from 1-3 and an option to quit the program by inputting 'q'.
- The inputs required by the user are prompted by the application and the format
is specified as well.

Stock Data File Instructions
-----------------------------
- Create a .csv file with the name of the ticker symbol. eg: MSFT.csv 
(ticker symbol for Microsoft).
- Each line should represent the stock data for the current date which would be
in the format - date,share price.
eg: 2018-11-11,150
- Ensure that the date is in the format yyyy-mm-dd. Example files are given
in the res folder for reference.