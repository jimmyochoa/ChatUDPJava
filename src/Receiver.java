import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Receiver {

    public static final int PORT = 2020;
    private DatagramSocket socket;

    public Receiver() {
        try {
            this.socket = new DatagramSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startChat() {
        try {
            System.out.println("Receiver is working...");

            while (true) {
                // Receive message
                byte[] buffer = new byte[1500];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String receivedMessage = new String(buffer, 0, packet.getLength());
                System.out.println("Mensaje recibido: " + receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        receiver.startChat();
    }
}
