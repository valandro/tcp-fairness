import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Socket;


public class ReceiveDataThread extends Thread {
    private Socket socket;
    private long globalTime;

    ReceiveDataThread(Socket socket, long globalTime) {
        this.globalTime = globalTime;
        this.socket = socket;
    }

    public void run() {
        final int megaBits = 1000 * 1000;
        int totalRecv = 0;
        long start = System.currentTimeMillis();

        int dataLength = 0;
        boolean hasData = true;

        while(hasData) {

            try {
                DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                byte[] bb = new byte[8192];
                dis.readFully(bb);
                dataLength = bb.length;
            } catch (Exception eof) {
                hasData = false;
                try {
                    System.out.println("------ SERVER CLOSING CONN ON PORT ----");
                    System.out.println("                " + socket.getPort());
                    System.out.println("---------------------------------------");
                    socket.close();
                } catch (IOException ex) {
                    System.out.println("Error closing socket with client " + socket.getPort());
                    System.exit(0);
                }
            }
            // Has data on recv buffer
            if (dataLength > 0) {
                totalRecv += dataLength;

                long stop = System.currentTimeMillis();

                long time = stop - start;
                long glTime = (stop - globalTime) / 1000;

                // Wait 1s to measure bandwith
                if (time >= 1000) {
                    final double bandWith = (totalRecv * 8) / ((time / 1000.0) * megaBits);
                    final BigDecimal bandWithPrecision =
                            BigDecimal.valueOf(bandWith).setScale(2, RoundingMode.HALF_EVEN);
                    try {
                        FileWriter fstream = new FileWriter("../../metrics.csv", true);
                        BufferedWriter out = new BufferedWriter(fstream);
                        out.write(glTime + "," + socket.getPort() + "," + bandWithPrecision + "\n");
                        out.close();
                        fstream.close();
                    } catch (IOException ex) {
                        System.err.println("Error opening file.");
                    }
                    System.out.println("---- C "+ socket.getPort() + " BANDWIDTH " + bandWithPrecision +" MBITS/S ----");
                    // Reset total receive and time chronometer
                    totalRecv = 0;
                    start = System.currentTimeMillis();
                }
            }
        }
    }
}
