import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Sender {
    private DatagramSocket socket;

    public Sender() {
        try {
            this.socket = new DatagramSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
    public void startChat() throws IOException {
        Scanner console = new Scanner(System.in);

        System.out.print("Ingrese su nombre de usuario: ");
        String usuario = console.nextLine();
        System.out.print("Ingrese su clave: ");
        String clave = console.nextLine();

        // Simulación de autenticación (comparación con datos en archivo)
        if (!autenticar(usuario, clave)) {
            System.out.println("Autenticación fallida. Saliendo...");
            return;
        }

        while (true) {
            System.out.print("Ingresar mensaje: ");
            String message = console.nextLine();

            // Send message to the chat
            sendMessage(usuario, message);

            // Exit condition if "salir" is typed
            if (message.equalsIgnoreCase("salir")) {
                break;
            }
        }

        console.close();
        socket.close();
    }

    private boolean autenticar(String usuario, String clave) {
        // Aquí deberías implementar la lógica de autenticación.
        // En este ejemplo, se acepta cualquier usuario y clave.
        return true;
    }

    private void sendMessage(String usuario, String message) {
        try {
            String fullMessage = usuario + ": " + message;
            byte[] buffer = fullMessage.getBytes();
            InetAddress group = InetAddress.getByName("localhost"); // Multicast group address
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, Receiver.PORT);
            socket.send(packet);
            System.out.println("Mensaje enviado: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Sender sender = new Sender();
        sender.startChat();
    }
}
