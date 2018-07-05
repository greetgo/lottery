package kz.greetgo.sandbox.db.register_impl.service;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.SZWagerAck;
import kz.greetgo.sandbox.controller.model.SZWagerReq;
import kz.greetgo.sandbox.db.in_service.BinService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Bean
public class BinServiceImpl implements BinService {
  private final int SZWagerAck_LEN = new SZWagerAck().serialize().length;

  @Override
  public SZWagerAck call(SZWagerReq req) {
    try {

      try (Socket socket = new Socket("localhost", 5000)) {

        OutputStream outputStream = socket.getOutputStream();

        outputStream.write(req.serialize());
        outputStream.flush();

        InputStream inputStream = socket.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] arr = new byte[4 * 1024];
        while (true) {
          int count = inputStream.read(arr);
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

            return SZWagerAck.deserialize(respBytes);
          }

        }


        return null;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
