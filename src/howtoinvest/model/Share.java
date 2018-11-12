package howtoinvest.model;

public class Share implements IShare {


  private long noOfShares;
  private double shareCostBasis;


  public Share(double shareCostBasis, long noOfShares) {

    if (noOfShares < 0 || shareCostBasis < 0) {
      throw new IllegalArgumentException("Invalid input for stock.");
    }
    this.shareCostBasis = shareCostBasis;
    this.noOfShares = noOfShares;
  }


  @Override
  public double getShareCostBasis() {
    return this.shareCostBasis;
  }


  @Override
  public long getNumberOfShares() {
    return this.noOfShares;
  }

  @Override
  public String getShareData() {
    return String.format("Number of Share: %d\nShare Cost Basis: %.2f\n", this.noOfShares,
            this.shareCostBasis);
  }

}


