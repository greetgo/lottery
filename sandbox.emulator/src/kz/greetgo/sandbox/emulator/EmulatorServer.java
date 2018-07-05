package kz.greetgo.sandbox.emulator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class EmulatorServer {
  public static void main(String[] args) throws Exception {
    new EmulatorServer().exec();
  }

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

          byteBuffer.clear();
        }

        String readStr = bOut.toString("UTF-8");
        System.out.println("thread = " + Thread.currentThread().getName() + ", readStr = " + readStr);

        System.out.println("... Writing answer");
        ch.write(ByteBuffer.wrap(("OK - " + readStr).getBytes("UTF-8")));

        if (readStr.contains("exit")) working.set(false);

      } catch (InterruptedException | ExecutionException | TimeoutException | UnsupportedEncodingException e) {
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
}
