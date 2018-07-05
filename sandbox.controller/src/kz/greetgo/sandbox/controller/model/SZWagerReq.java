package kz.greetgo.sandbox.controller.model;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SZWagerReq {
  public int TVN;
  public int operatorIndex;
  public WagerBetData wagerBetData = new WagerBetData();

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


  public byte[] serialize() {
    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(bOut);
    try {

      out.writeInt(TVN);
      out.writeInt(operatorIndex);
      out.writeInt(wagerBetData == null ? 0 : wagerBetData.onlineBetModeNo);
      out.writeInt(wagerBetData == null ? 0 : wagerBetData.onlineGameNo);

      return bOut.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public static SZWagerReq deserialize(byte[] array) {
    LittleEndianDataInputStream in = new LittleEndianDataInputStream(new ByteArrayInputStream(array));
    try {
      SZWagerReq ret = new SZWagerReq();

      ret.TVN = in.readInt();
      ret.operatorIndex = in.readInt();
      ret.wagerBetData.onlineBetModeNo = in.readInt();
      ret.wagerBetData.onlineGameNo = in.readInt();

      return ret;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
