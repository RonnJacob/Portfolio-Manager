package howtoinvest.model;

/**
 * This class represents shares of a stock and implements IShare. Each share object contains the no
 * of shares and the cost basis for it. The number of shares and cost basis cannot be negative.
 */
public class Share implements IShare {
  /**
   * Holds the number of shares.
   */
  private double noOfShares;

  /**
   * Holds the cost basis of the shares.
   */
  private double shareCostBasis;

  /**
   * Constructs a share object with the given cost basis and number of shares. Throws an exception
   * if the cost basis or the number of shares is negative.
   *
   * @param shareCostBasis cost basis of the shares.
   * @param noOfShares     number of shares.
   * @throws IllegalArgumentException for negative cost basis and number of shares.
   */
  public Share(double shareCostBasis, double noOfShares) throws IllegalArgumentException {

    if (noOfShares < 0 || shareCostBasis < 0) {
      throw new IllegalArgumentException("Invalid input for share.");
    }
    this.shareCostBasis = shareCostBasis;
    this.noOfShares = noOfShares;
  }

  @Override
  public double getShareCostBasis() {
    return this.shareCostBasis;
  }

  @Override
  public double getNumberOfShares() {
    return this.noOfShares;
  }

  /**
   * Returns the number of shares and cost basis in the format: Number of Shares: x "linebreak"
   * Share Cost Basis: y where x and y represent the number of shares and cost basis.
   *
   * @return the data representation of the share object.
   */
  @Override
  public String getShareData() {
    return String.format("Number of shares: %.2f\nShare Cost Basis: %.2f\n", this.noOfShares,
            this.shareCostBasis);
  }
}


