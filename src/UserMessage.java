import java.io.Serializable;

public class UserMessage implements Serializable {
    private String usuario;
    private String mensaje;

    public UserMessage(String usuario, String mensaje) {
        this.usuario = usuario;
        this.mensaje = mensaje;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return usuario + ": " + mensaje;
    }
}
