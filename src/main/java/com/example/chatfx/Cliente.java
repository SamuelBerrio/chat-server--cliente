package com.example.chatfx;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class Cliente implements Initializable {

    public TextArea textArea;
    public TextField textField;
    public TextField nameTF;
    public TextField ipTF;
    public TextField portTF;

    private Socket socket;

    private int puerto;
    private String host;
    private String usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameTF.setText("");
        ipTF.setText("localhost");
        portTF.setText("5050");
        textArea.appendText("Sin conexion con el Server..." + System.lineSeparator());
    }

    public void recibirMensajesServidor(){
        DataInputStream entradaDatos = null;
        String mensaje;
        try {
            entradaDatos = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error al crear el stream de entrada: " + ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println("El socket no se creo correctamente. ");
        }

        boolean conectado = true;
        while (conectado) {
            try {
                mensaje = entradaDatos.readUTF();
                textArea.appendText(mensaje + System.lineSeparator());
            } catch (IOException ex) {
                System.out.println("Error al leer del stream de entrada: " + ex.getMessage());
                conectado = false;
            } catch (NullPointerException ex) {
                System.out.println("El socket no se creo correctamente. ");
                conectado = false;
            }
        }
    }

    public void enviarBT(ActionEvent event) {
        new ConexionServidor(socket, textField, usuario);
    }

    public void loginBT(ActionEvent event) {
        this.host = ipTF.getText();
        this.puerto = Integer.parseInt(portTF.getText());
        this.usuario = nameTF.getText();
        try {
            socket = new Socket(host, puerto);
            textArea.appendText("Conectado con el Server con exito" + System.lineSeparator());
            recibirMensajesServidor();
        } catch (UnknownHostException ex) {
            System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        } catch (IOException ex) {
            System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        }
    }
}
