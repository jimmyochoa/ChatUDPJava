import java.io.Serializable;
import java.util.List;

public class UserMessage implements Serializable {
    private String usuario;
    private String mensaje;
    private List<String> connectedNodes;

    public UserMessage(String usuario, String mensaje, List<String> connectedNodes) {
        this.usuario = usuario;
        this.mensaje = mensaje;
        this.connectedNodes = connectedNodes;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<String> getConnectedNodes() {
        return connectedNodes;
    }

    @Override
    public String toString() {
        return usuario + ": " + mensaje;
    }
}