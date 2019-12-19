import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private static Runnable clientConn(final String host, final int port) {
        return () -> {
            final long times = 10000;
            final int bufferSize = 1024;
            try {
                Socket socket = new Socket(host, port);
                socket.setSendBufferSize(1024 * 1024 * 1024);
                System.out.println("Client on port " + socket.getLocalPort() + " connected on server " + host + " on port " + port);
                for (int i = 0; i < times; i++) {
                    OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
                    outputStream.write(new byte[bufferSize]);
                }
                System.out.println("Client " + socket.getLocalPort() + " closed connection!");
                socket.close();
            } catch (IOException ex) {
                System.out.println("Socket error!");
                ex.printStackTrace();
            }
        };
    }

    public static void main(String[] args) throws Exception {
        String host = args[0];
        int port = Integer.valueOf(args[1]);
        System.out.println("Clients starting...!");
        new Thread(clientConn(host, port)).start();
        Thread.sleep(5000);
        new Thread(clientConn(host, port)).start();
        Thread.sleep(7500);
        new Thread(clientConn(host, port)).start();
    }
}
