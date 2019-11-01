import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.valueOf(args[0]));
            System.out.println("Server listening on port " + args[0]);
            Socket socket = serverSocket.accept();
            System.out.println("Server connected!");
            socket.close();
            System.out.println("Server disconnected!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
