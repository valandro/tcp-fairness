import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", Integer.valueOf(args[0]));
            System.out.println("Socket connected!");
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
