package socket;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class socket1 extends javax.swing.JFrame {

    private Socket conexion;
    private DataOutputStream flujoSalida;
    private DataInputStream flujoEntrada;

    public socket1() {
        initComponents();
        this.setVisible(true);
        iniciarConexion();
    }

    private void iniciarConexion() {
        try {
            conexion = new Socket("127.0.0.1", 9999);
            flujoSalida = new DataOutputStream(conexion.getOutputStream());
            flujoEntrada = new DataInputStream(conexion.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(socket1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recibirMensajes() {
        try {
            while (true) {
                if (flujoEntrada.available() > 0) { // Verifica si hay datos disponibles
                    String mensaje = flujoEntrada.readUTF();
                    jTextArea1.append("Servidor: " + mensaje + "\n");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(socket1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void botonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            flujoSalida.writeUTF(texto.getText());
            jTextArea1.append("Yo: " + texto.getText() + "\n");
            texto.setText("");
            recibirMensajes(); // Llama a la función para recibir mensajes después de enviar
        } catch (IOException ex) {
            Logger.getLogger(socket1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Resto del código permanece igual...

    // Variables declaration - do not modify
    private javax.swing.JButton boton;
    private javax.swing.JTextField texto;
    private javax.swing.JTextArea jTextArea1; // Añadir un JTextArea para mostrar mensajes
    // End of variables declaration
}

package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class socket2 extends javax.swing.JFrame {

    private ServerSocket server;
    private Socket socket;
    private DataInputStream flujoEntrada;
    private DataOutputStream flujoSalida;

    public socket2() {
        initComponents();
        this.setVisible(true);
        iniciarServidor();
    }

    private void iniciarServidor() {
        try {
            server = new ServerSocket(9999);
            socket = server.accept();
            flujoEntrada = new DataInputStream(socket.getInputStream());
            flujoSalida = new DataOutputStream(socket.getOutputStream());
            jTextArea1.setText("Cliente conectado.\n");

            while (true) {
                if (flujoEntrada.available() > 0) { // Verifica si hay datos disponibles
                    String mensaje = flujoEntrada.readUTF();
                    jTextArea1.append("Cliente: " + mensaje + "\n");
                    flujoSalida.writeUTF("Mensaje recibido: " + mensaje);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(socket2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Resto del código permanece igual...

    // Variables declaration - do not modify
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea jTextArea1;
    // End of variables declaration
}# Sockets-quiz

