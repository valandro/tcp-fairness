import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            final int port = Integer.valueOf(args[0]);
            final int rcvBufferSize = 1024 * 1024 * 1024; // 1GB
            final int fixedNumberOfThreads = 3;
            final long globalTime = System.currentTimeMillis();

            ServerSocket tcpSocket = new ServerSocket(port);
            tcpSocket.setReceiveBufferSize(rcvBufferSize);
            
	        System.out.println("-------- SERVER LISTENING ON PORT -------");
            System.out.println("                " + port);
            System.out.println("-----------------------------------------");

            int threadsRunning = 0;
            boolean hasData = true;

            while (hasData) {

                Socket socket = tcpSocket.accept();
                socket.setReceiveBufferSize(rcvBufferSize);
                System.out.println("-------- SERVER LISTENING ON PORT -----");
                System.out.println("                " + socket.getPort());
                System.out.println("---------------------------------------");
                new ReceiveDataThread(socket, globalTime).start();
                // @TODO: Count the number of running threads
                threadsRunning++;
                if (threadsRunning == fixedNumberOfThreads) {
                    hasData = false;
                }
            }
            System.out.println("-------- SERVER CLOSING TCP CONN ------");
            tcpSocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
