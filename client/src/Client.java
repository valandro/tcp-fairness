import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private static Runnable clientConn(final String host, final int port) {
        return () -> {
            final long times = 10000000;
            final int oneByte = 1024;
            final int bufferSize = 1024 * 1024 * 1024;
            try {
                Socket socket = new Socket(host, port);
                socket.setSendBufferSize(bufferSize);
                final int localPort = socket.getLocalPort();
                System.out.println(String.format("Client on port %s connected on server %s on port %s", localPort, host, port));
                for (int i = 0; i < times; i++) {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		            out.writeInt(oneByte);
                    out.write(new byte[oneByte]);
                }
                System.out.println(String.format("Client %s closed connection!", socket.getLocalPort()));
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
        System.out.println("Client 1 starting...!");
        new Thread(clientConn(host, port)).start();
        Thread.sleep(5000);
        System.out.println("Client 2 starting...!");
        new Thread(clientConn(host, port)).start();
        Thread.sleep(7000);
        System.out.println("Client 3 starting...!");
        new Thread(clientConn(host, port)).start();
    }
}
