package kz.greetgo.sandbox.emulator;

import kz.greetgo.sandbox.controller.model.SZWagerAck;
import kz.greetgo.sandbox.controller.model.SZWagerReq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class EmulatorServer {
  public static void main(String[] args) throws Exception {
    new EmulatorServer().exec();
  }

  private static final int SZWagerReq_LEN = new SZWagerReq().serialize().length;

  final Handler handler = new Handler();

  AsynchronousServerSocketChannel listener;

  AtomicBoolean working = new AtomicBoolean(true);

  private void exec() throws Exception {

    listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(5000));
    System.out.println("... accept listener");
    listener.accept(null, handler);

    while (working.get()) Thread.sleep(1000);
  }

  class Handler implements CompletionHandler<AsynchronousSocketChannel, Void> {

    @Override
    public void failed(Throwable exc, Void attachment) {
      if (exc instanceof RuntimeException) throw (RuntimeException) exc;
      throw new RuntimeException(exc);
    }

    @Override
    public void completed(AsynchronousSocketChannel ch, Void attachment) {
      if (!working.get()) return;

      System.out.println("... complete listener");

      System.out.println("... accept new listener");
      listener.accept(null, this);

      try {

        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * 1024);
        byte[] array = new byte[4 * 1024];
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();

        while (true) {

          System.out.println("... start to read");
          int bytesRead = ch.read(byteBuffer).get(20000, TimeUnit.SECONDS);
          System.out.println("... read bytes " + bytesRead);
          if (bytesRead < 0) {
            System.out.println("... breaking from reading");
            break;
          }

          byteBuffer.flip();

          byteBuffer.get(array, 0, bytesRead);
          bOut.write(array, 0, bytesRead);

          if (bOut.size() >= SZWagerReq_LEN) {
            byte[] bytes = bOut.toByteArray();
            bOut.reset();
            byte[] reqBytes;
            if (bytes.length == SZWagerReq_LEN) {
              reqBytes = bytes;
            } else {
              reqBytes = new byte[SZWagerReq_LEN];
              System.arraycopy(bytes, 0, reqBytes, 0, SZWagerReq_LEN);
              bOut.write(bytes, SZWagerReq_LEN, bytes.length - SZWagerReq_LEN);
            }
            ForkJoinPool.commonPool().execute(() -> performSZWager(reqBytes, ch));
          }

          byteBuffer.clear();
        }

      } catch (InterruptedException | ExecutionException | TimeoutException e) {
        throw new RuntimeException(e);
      } finally {
        if (ch.isOpen()) {
          try {
            ch.close();
          } catch (IOException e) {
            //noinspection ThrowFromFinallyBlock
            throw new RuntimeException(e);
          }
        }
      }
    }
  }

  private void performSZWager(byte[] reqBytes, AsynchronousSocketChannel ch) {
    SZWagerReq req = SZWagerReq.deserialize(reqBytes);
    System.out.println(req);

    SZWagerAck ack = new SZWagerAck();
    ack.ticketNo = 123;
    ack.isFreeTicket = true;
    ack.transactionDateBetNet = new Date();

    ch.write(ByteBuffer.wrap(ack.serialize()));
  }
}
