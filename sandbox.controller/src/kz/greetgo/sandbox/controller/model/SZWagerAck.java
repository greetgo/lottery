package kz.greetgo.sandbox.controller.model;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class SZWagerAck {
  public Date transactionDateBetNet;
  public boolean isFreeTicket;
  public long ticketNo;

  public byte[] serialize() {
    try {
      ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(bOut);

      if (transactionDateBetNet == null) {
        out.writeLong(0);
      } else {
        out.writeLong(transactionDateBetNet.getTime());
      }

      out.writeBoolean(isFreeTicket);
      out.writeLong(ticketNo);

      return bOut.toByteArray();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static SZWagerAck deserialize(byte[] arr) {
    try {
      SZWagerAck ret = new SZWagerAck();
      LittleEndianDataInputStream in = new LittleEndianDataInputStream(new ByteArrayInputStream(arr));
      ret.transactionDateBetNet = toDate(in.readLong());
      ret.isFreeTicket = in.readBoolean();
      ret.ticketNo = in.readLong();
      return ret;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Date toDate(long time) {
    if (time == 0) return null;
    return new Date(time);
  }
}
