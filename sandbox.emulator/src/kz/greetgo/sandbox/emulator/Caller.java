package kz.greetgo.sandbox.emulator;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Caller {
  public static void main(String[] args) throws Exception {
    new Caller().exec();
  }

  private void exec() throws Exception {
    Socket socket = new Socket("localhost", 5000);

    OutputStream outputStream = socket.getOutputStream();
    System.out.println("... outputStream got");
    outputStream.write("hello world asd".getBytes(StandardCharsets.UTF_8));
    outputStream.flush();
    System.out.println("... outputStream flushed");
    outputStream.close();
    System.out.println("... outputStream closed");

    InputStream inputStream = socket.getInputStream();
    System.out.println("... inputStream got");
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] arr = new byte[4 * 1024];
    while (true) {
      int count = inputStream.read(arr);
      System.out.println("... inputStream read bytes " + count);
      if (count < 0) break;
      out.write(arr, 0, count);
    }

    System.out.println("... out from reading");

    System.out.println("Answer " + out.toString("UTF-8"));
  }
}
