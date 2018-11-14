package howtoinvest.model;

/**
 * <p>This interface represents shares of investments.</p>
 * <p>A stock of a company is simply a part of ownership in that company.
 * Ownership is divided into shares, where a share represents a fraction of the total ownership. For
 * example, Apple has about 5.2B shares. So if you own 100 shares, you own about 1.9𝑥10−6% of the
 * company (and it would be worth about $20800 today). This interface provides three
 * operations:</p>
 * <ul>
 * <li>Retrieve the total amount invested in buying the shares which is called cost basis.</li>
 * <li>Get the number of shares bought.</li>
 * <li>Get a meaningful representation of the data in this share.</li>
 * </ul>
 */
public interface IShare {

  /**
   * Returns the total amount invested in buying the shares which is called cost basis.
   *
   * @return the cost basis.
   */
  double getShareCostBasis();

  /**
   * Return the number of shares bought.
   *
   * @return the number of shares.
   */
  double getNumberOfShares();

  /**
   * Return the data stored in the share.
   *
   * @return the data stored in the share as a string.
   */
  String getShareData();
}
