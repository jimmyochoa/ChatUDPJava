import java.io.*;
import java.net.*;
import java.util.*;

public class Receiver {
    public static final int PORT = 2020;
    private DatagramSocket socket;

    public Receiver() {
        try {
            this.socket = new DatagramSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void startChat() {
        try {
            System.out.println("Receiver is working...");

            while (true) {
                byte[] buffer = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
                ObjectInputStream objectStream = new ObjectInputStream(byteStream);
                UserMessage receivedMessage = (UserMessage) objectStream.readObject();

                System.out.println("Mensaje recibido de " + receivedMessage.getUsuario() + ": " + receivedMessage.getMensaje());
                System.out.println("Nodos conectados: " + receivedMessage.getConnectedNodes());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        receiver.startChat();
    }
}