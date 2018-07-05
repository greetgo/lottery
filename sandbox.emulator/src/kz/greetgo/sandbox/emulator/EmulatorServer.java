package kz.greetgo.sandbox.emulator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class EmulatorServer {
  public static void main(String[] args) throws Exception {
    new EmulatorServer().exec();
  }

  final Handler handler = new Handler();

  AsynchronousServerSocketChannel listener;

  private void exec() throws Exception {
    listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(5000));
    listener.accept(null, handler);

    Thread.sleep(4 * 1000);
  }

  class Handler implements CompletionHandler<AsynchronousSocketChannel, Void> {

    @Override
    public void failed(Throwable exc, Void attachment) {

    }

    @Override
    public void completed(AsynchronousSocketChannel ch, Void attachment) {
      listener.accept(null, this);

      ByteBuffer byteBuffer = ByteBuffer.allocate(4 * 1024);

      ch.read(byteBuffer, null, new CompletionHandler<Integer, Object>() {
        @Override
        public void completed(Integer result, Object attachment) {
          
        }

        @Override
        public void failed(Throwable exc, Object attachment) {

        }
      });

      if (ch.isOpen()) {
        try {
          ch.close();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }

  }
}
