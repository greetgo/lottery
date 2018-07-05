package kz.greetgo.sandbox.emulator;

import kz.greetgo.sandbox.controller.model.SZWagerAck;
import kz.greetgo.sandbox.controller.model.SZWagerReq;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Caller {
  public static void main(String[] args) throws Exception {
    new Caller().exec();
  }

  private final int SZWagerAck_LEN = new SZWagerAck().serialize().length;

  private void exec() throws Exception {
    Socket socket = new Socket("localhost", 5000);

    OutputStream outputStream = socket.getOutputStream();

    SZWagerReq req = new SZWagerReq();
    req.TVN = 36000089;
    req.operatorIndex = 345;
    req.wagerBetData.onlineGameNo = 10;
    req.wagerBetData.onlineBetModeNo = 13;

    System.out.println("... outputStream got");
    outputStream.write(req.serialize());
    outputStream.flush();

    InputStream inputStream = socket.getInputStream();
    System.out.println("... inputStream got");
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] arr = new byte[4 * 1024];
    while (true) {
      int count = inputStream.read(arr);
      System.out.println("... inputStream read bytes " + count);
      if (count < 0) break;
      out.write(arr, 0, count);

      if (out.size() >= SZWagerAck_LEN) {
        final byte[] bytes = out.toByteArray();
        out.reset();
        byte[] respBytes;
        if (bytes.length == SZWagerAck_LEN) {
          respBytes = bytes;
        } else {
          respBytes = new byte[SZWagerAck_LEN];
          System.arraycopy(bytes, 0, respBytes, 0, SZWagerAck_LEN);
          out.write(bytes, SZWagerAck_LEN, bytes.length - SZWagerAck_LEN);
        }

        final SZWagerAck act = SZWagerAck.deserialize(respBytes);
        System.out.println("act = " + act);
        break;
      }

    }

    System.out.println("... out from reading");

    System.out.println("Answer " + out.toString("UTF-8"));
  }
}
