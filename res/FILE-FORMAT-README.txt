Portfolio-
- Files for portfolio will be saved and loaded from the Stock Portfolios folder only.

- Example Format for Portfolio Json
  {
    "portfolio": {
      "MSFT": {
        "tickerSymbol": "MSFT",
        "shareList": {
          "Wed Oct 10 00:00:00 EDT 2018": {
            "noOfShares": 3.2362459546925564,
            "shareCostBasis": 333.3333333333333
          },
          "Sun Nov 11 00:00:00 EST 2018": {
            "noOfShares": 11.423871321513435,
            "shareCostBasis": 1250.0
          }
        }
      },
      "GOOGL": {
        "tickerSymbol": "GOOGL",
        "shareList": {
          "Mon Oct 10 00:00:00 EDT 2016": {
            "noOfShares": 145.83333333333331,
            "shareCostBasis": 583.3333333333333
          },
          "Wed Oct 10 00:00:00 EDT 2018": {
            "noOfShares": 33.33333333333333,
            "shareCostBasis": 333.33333333333326
          },
          "Sun Nov 11 00:00:00 EST 2018": {
            "noOfShares": 23.99232245681382,
            "shareCostBasis": 250.0
          }
        }
      },
      "FB": {
        "tickerSymbol": "FB",
        "shareList": {
          "Mon Oct 10 00:00:00 EDT 2016": {
            "noOfShares": 5.833333333333333,
            "shareCostBasis": 583.3333333333333
          },
          "Wed Oct 10 00:00:00 EDT 2018": {
            "noOfShares": 3.1746031746031744,
            "shareCostBasis": 333.3333333333333
          },
          "Sun Nov 11 00:00:00 EST 2018": {
            "noOfShares": 4.569548528605374,
            "shareCostBasis": 500.0
          }
        }
      }
    }
  }

- Files for strategy will be saved and loaded from the Strategies folder only.

- Example format for strategy:
    {
      "stockWeights": {
        "GOOGL": 20.0,
        "Invalid": 0.0,
        "MSFT": 80.0
      },
      "amount": 100.0,
      "frequency": 10,
      "startDate": "Oct 25, 2016 12:00:00 AM",
      "endDate": "Nov 15, 2016 12:00:00 AM"
    }


