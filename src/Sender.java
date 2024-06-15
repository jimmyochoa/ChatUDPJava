import java.io.*;
import java.net.*;
import java.util.*;

public class Sender {
    private DatagramSocket socket;
    private List<String> connectedNodes;

    public Sender() {
        try {
            this.socket = new DatagramSocket();
            this.connectedNodes = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void startChat() throws IOException {
        try (Scanner console = new Scanner(System.in)) {
            System.out.print("Ingrese su nombre de usuario: ");
            String usuario = console.nextLine();
            System.out.print("Ingrese su clave: ");
            String clave = console.nextLine();

            if (!autenticar(usuario, clave)) {
                System.out.println("Autenticación fallida. Saliendo...");
                return;
            }

            connectedNodes.add(usuario);

            while (true) {
                System.out.print("Ingresar mensaje: ");
                String message = console.nextLine();
                UserMessage userMessage = new UserMessage(usuario, message, new ArrayList<>(connectedNodes));

                sendMessage(userMessage);

                if (message.equalsIgnoreCase("salir")) {
                    connectedNodes.remove(usuario);
                    break;
                }
            }
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    private boolean autenticar(String usuario, String clave) {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(usuario) && parts[1].equals(clave)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void sendMessage(UserMessage userMessage) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(userMessage);
            objectStream.flush();

            byte[] buffer = byteStream.toByteArray();
            InetAddress group = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, Receiver.PORT);
            socket.send(packet);
            System.out.println("Mensaje enviado: " + userMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Sender sender = new Sender();
        sender.startChat();
    }
}
