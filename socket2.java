package chat;

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
    private DataOutputStream output;

    public socket2() {
        initComponents();
        this.setVisible(true);
        startServer();
    }

    private void startServer() {
        new Thread(() -> {
            try {
                server = new ServerSocket(9999);
                jTextArea1.setText("Esperando conexiones...\n");
                socket = server.accept(); // Espera a que un cliente se conecte
                output = new DataOutputStream(socket.getOutputStream());
                jTextArea1.append("Cliente conectado.\n");

                while (true) {
                    // Escuchar mensajes del cliente
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    String message = input.readUTF();
                    jTextArea1.append("Cliente: " + message + "\n");
                }
            } catch (IOException ex) {
                Logger.getLogger(socket2.class.getName()).log(Level.SEVERE, null, ex);
                jTextArea1.append("Error: " + ex.getMessage() + "\n");
            }
        }).start();
    }

    private void sendMessage(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(socket2.class.getName()).log(Level.SEVERE, null, ex);
            jTextArea1.append("Error al enviar mensaje: " + ex.getMessage() + "\n");
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new socket2().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField texto; // Campo de texto para enviar mensajes
    private javax.swing.JButton boton; // Botón para enviar mensajes
    // End of variables declaration                   

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        texto = new javax.swing.JTextField();
        boton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor Chat");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        boton.setText("Enviar");
        boton.addActionListener(this::botonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(texto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(texto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton))
                .addContainerGap()
        );

        pack();
    }

    private void botonActionPerformed(java.awt.event.ActionEvent evt) {
        String message = texto.getText();
        sendMessage(message);
        jTextArea1.append("Servidor: " + message + "\n");
        texto.setText(""); // Limpiar el campo de texto después de enviar
    }
}