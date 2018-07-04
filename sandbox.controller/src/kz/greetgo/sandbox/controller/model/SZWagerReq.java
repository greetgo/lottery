package kz.greetgo.sandbox.controller.model;

public class SZWagerReq {
  public int TVN;
  public int operatorIndex;
  public WagerBetData wagerBetData;

  public boolean filled() {
    return TVN > 0;
  }

  @Override
  public String toString() {
    return "SZWagerReq{" +
      "TVN=" + TVN +
      ", operatorIndex=" + operatorIndex +
      ", wagerBetData=" + wagerBetData +
      '}';
  }
}
