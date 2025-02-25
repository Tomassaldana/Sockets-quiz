package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class socket1 extends javax.swing.JFrame {

    private Socket conexion;
    private DataOutputStream flujo;

    public socket1() {
        initComponents();
        this.setVisible(true);
        connectToServer();
        startListening();
    }

    private void connectToServer() {
        try {
            conexion = new Socket("127.0.0.1", 9999);
            flujo = new DataOutputStream(conexion.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(socket1.class.getName()).log(Level.SEVERE, null, ex);
            jTextArea1.append("Error al conectar al servidor: " + ex.getMessage() + "\n");
        }
    }

    private void sendMessage(String message) {
        try {
            flujo.writeUTF(message);
            flujo.flush();
        } catch (IOException ex) {
            Logger.getLogger(socket1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startListening() {
        new Thread(() -> {
            try {
                while (true) {
                    DataInputStream input = new DataInputStream(conexion.getInputStream());
                    String message = input.readUTF();
                    jTextArea1.append("Servidor: " + message + "\n");
                }
            } catch (IOException ex) {
                Logger.getLogger(socket1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    private void botonActionPerformed(java.awt.event.ActionEvent evt) {
        String message = texto.getText();
        sendMessage(message);
        jTextArea1.append("Cliente: " + message + "\n");
        texto.setText(""); // Limpiar el campo de texto después de enviar
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new socket1().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton boton;
    private javax.swing.JTextField texto;
    private javax.swing.JTextArea jTextArea1; // Añadido para mostrar mensajes
    // End of variables declaration                   

    private void initComponents() {
        // Inicialización de componentes
        texto = new javax.swing.JTextField();
        boton = new javax.swing.JButton();
        jTextArea1 = new javax.swing.JTextArea();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cliente Chat");

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
}