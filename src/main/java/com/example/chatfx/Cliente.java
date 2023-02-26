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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Cliente implements Initializable {

    public TextArea textArea;
    public TextField textField;
    public TextField nameTF;
    public TextField ipTF;
    public TextField portTF;

    public static Socket socket;

    public static int puerto;
    public static String host;
    public static String usuario;

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
        ConexionServidor cs = new ConexionServidor(socket, textField, usuario);
        cs.enviarMsg();
    }

    public void loginBT(ActionEvent event) {
        Cliente.host = ipTF.getText();
        Cliente.puerto = Integer.parseInt(portTF.getText());
        Cliente.usuario = nameTF.getText();
        textArea.clear();
        textArea.setText("Conectado al chat!" + System.lineSeparator());
        System.out.println("Hasta aqui llego?");

        try {
            Cliente.socket=new Socket(ipTF.getText(),Integer.parseInt(portTF.getText()));
            Executor executor = Executors.newFixedThreadPool(2);
            executor.execute(this::recibirMensajesServidor);
            System.out.println("Conectado Correctamente");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
