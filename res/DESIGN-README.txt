A How-to-invest-for-dummies application - DESIGN-README

The following application has been written from scratch with the MVC(model-view-controller) architecture
in mind whereby there is a separate distinction between the model of the program, controller and view of 
the program.

Controller
----------
- The controller consists of an interface class IHowToInvestController which would have a single operation
to open a portfolio manager which would start receiving inputs and perform operations based on these inputs.
The openPortfolioManager method in the interface has no return type. The controller would delegate and call
the model inorder to perform certain operations based on the user input.

- The controller would take two models and a view. The view would be an implementatino of the IHowToInvestView
interface and the models are of type IManager - one for a strategies and one for portfolio related operations.

- The following HowToInvestController class is an implementation of the IHowToInvestController interface and 
provides an operation to open up a portfolio manager wherein a user would be able to make choices provided by 
specific implementation of IPortfolio manager type class. A portfolio manager would offer operations such as 
creation of portfolios, examining the composition of portfolios which would ideally be made up of stocks and 
shares for a particular company or organization, adding shares/stocks to a portfolio for a particular date 
and retrieving the cost basis and value of stocks or of a portfolio for a given date. This would aid the 
user in learning about how the investment cycle would work and how the value would dip and spike depending on 
he date at which the investments were made or the date at which the value is being retrieved for. The user
would be able to seamlessly choose between selection of equal weight investment for stocks in the portfolio
or could enter weights of the user's choice. Another feature offered would be the higher investment strategies
offered by the program. This would allow for a user to apply a strategy and modify the strategies preferences
namely the stock(s) in the strategy, the weights for investment, the frequency of investing, the start and end
date for investment and the amount to be invested. Note that each transaction (adding/buying of a stock to a
portfolio) would include a commission charge which would be included in the cost basis/value.

- The controller would provide a method for opening up the home screen wherein the user can choose to perform
operations depending on the specific input. The home screen would provide operations for a user to create a 
portfolio, retrieve a list of portfolios and enter a particular portfolio.

- If a user enters a particular portfolio then further operations are made open to the user where a user
can observe the composition of a particular portfolio at the time, add a share/stock at a particular date
that is input by the user. The valid sequence of inputs for adding a share would be the unique ticker symbol
representing a company in string format, the amount for which a user wants to buy shares of that company
and the date for which the user is planning to add or buy that share for. The next two operations in the stock
menu would be to get or retrieve the cost basis or value of a portfolio at a particular date which is input
by the user again. The other operations would be to invest in stocks with specified weights and applying
a particular strategy to the portfolio.

- If the user chooses to invest in a fixed amount at a particular time, then there are two options for
the user - the first one is to either invest using a fixed amount and equal weights for each stock in
the portfolio and the second one would be to use weights which are specified and input by the user.Note
that on calling both methods, the user is prompted to enter a commission value.

- If the user chooses to invest in applying a strategy, list of strategies are displayed for the user
to choose from. On the choosing a strategy, the strategy model would allow the user to either apply
the strategy to the portfolio or modify the settings of that strategy.

- If the user chooses to apply the strategy to the portfolio, the user is asked for the commmision to
be used to apply the strategy.

- If the user chooses to modify the strategy settings, the user is displayed a list of modifications
that can be made to the strategy. This would include addition of stock(s) to the portfolio, modifying
the frequency of investment, amount of investment, time range of investment and modifying the weights
to be used for the strategy.

- The controller mandates that each of the input sequences would end with the user closing the application 
(which would be an input of 'q' in the controller implementation).

- The controller virtually divides the portfolio manager and portfolio options which are given by providing the
 user with 'menu' relevant to the level of operation. If the level of operation is of portfolio manager, then
the user input would be taken into account and appropriate portfolio manager level operations would be 
performed (Creation of portfolios, retrieval of portfolios and entering a portfolio). 

- If the level of operation is at portfolio level, the user input would be able to perform operations at 
porftolio level (Examining composition of a portfolio, adding shares of a stock to a portfolio and getting
the cost basis/value for a portfolio).

- This virtual division was chosen in order to reduce extra irrelevant inputs by the user which would otherwise
make it hard for the user to keep track of what was input previously. Having this division ensures that user
only needs to input data that are relevant to a level of operation and can go up a level or down a level of
operation by giving a simple input.

- Additionally, the strategy model handles all strategy related operations and would allow the controller
to choose operations pertaining to strategies.

Model
------
The model in a bottom-up fashion would start off with

- IShare interface would be a representation of the real-world shares of investments for a company and provides
operations to get the share's cost basis, the number of shares and a meaningful representation of the data. Share
class implements this interface which would be used by the Stock class.

- IStock interface would be the representation of the real-world stock of a company and provides operations
to get a stock's value and cost basis depending on a particular date, adding of a share to the stock depending
on a particular date and a meaningful representation of the data. Stock implements this interface and maintains
a list of Share objects where the key is the date on which a particular number of shares were added. This would allow
us to filter shares by date (Retrieval of cost basis, number of shares and stock value as of a particular date).
The Stock class would retrieve the stock data depending on a property value in an app.config file. This class 
would be used by StockPortfolio class.

- IPortfolio is an interface that represents a portfolio in the real world and is a collection of investments.
The interface provides operations like getting an overall portfolio cost basis/value as of a particular date,
adding an investment worth a particular amount for a specified date, investing with a specific set of weights
for the stocks in the portfolio and a meaningful representation of data. All investments take the commmision
values into account. StockPortfolio is a class implementing this interface and provides all the above
operations. This class contains a HashMap where the key is the identifier for the Stock and value would
be the Stock object. This class would be used by StockPortfolioManager class.

- IInvestmentStrategy is an interface that represents a strategy in the real world.
The interface provides operations like adding stocks to the portfolio, setting weights for the stocks,
setting the amount to be invested, setting the time range and frequency for the investments,
applying the strategy on a portfolio taking into account the commission fees.
DollarCostAveraging is a class implementing this interface and provides all the above
operations. This class would be used by DollarCostAveragingStrategyManager class.

- IManager<K> interface is parametrized over the type of items it manages which is
created to manage items and provides operations for listing all the items, creation of
items and retrieving an item. The StockPortfolioManager and DollarCostAveragingStrategyManager are classes
that implementing this interface and parametrized over StockPortfolio and DollarCostAveraging respectively.

- Additionally, an interface IStockDataRetrieval was created to allow flexibility in retrieving stock data 
and provides operations to check the validity of the ticker name and retrieve the share price for the ticker
symbol at a specified date. The two classes that implement this interface are AlphaVantage and FileStockDataReader.
AlphaVantage would check the validity of strings and retrieves stock data from a URL query. FileStockDataReader
would do the same from a .csv file where the name of the .csv file is the ticker symbol. The property READ_FROM_API
in app.config governs which source Stock class would use to retrieve source data.
Alphavantage will automatically generate csv files for the stock data if the csv file does not exist or if
they are not upto date. These csv files are used by the API to query multiple times.


View
-----

The view would handle everything related to the formatting of outputs, displaying the appropriate menus
and retrieving output from the user to be passed to the controller.

- The view would be responsible for displaying the menus which would be the following:
    1. Home Screen Menu
    2. Portfolio Operations Menu
    3. Investment Option Menu (Equal or custom weights).
    4. Strategy Menu (Applying or Modifying the menu).
    5. Strategy Modification Menu.

- Apart from displaying menus, the view is responsible for what is to be displayed when the application
quits and for prompting appropriate messages.

- The view would also retrieve inputs to be passed to the controller (getInput and getShareBuyDetails).



Design Changes (from prev. assignment)
---------------
- The model observers used to return strings, which makes it difficult for a client to get the data.
For this very reason, the changes were made to return 'double' for cost basis and stock values instead
of formatted strings.
For eg: getStockCostBasis, getStockValue used to return strings and now we return the data without
formatting as this would be handled by the view.
