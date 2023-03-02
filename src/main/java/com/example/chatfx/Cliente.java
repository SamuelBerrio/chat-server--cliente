package com.example.chatfx;

import com.example.chatfx.model.User;
import com.example.chatfx.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Cliente implements Initializable {

    ArrayList<User> userArrayList;

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void initializeUsersList(){
        try{
            this.userArrayList = Persistence.loadUsers();

        }catch (IOException err){
            System.out.println(err.getMessage());
        }

    }

    public void addUser(User user) {

        userArrayList.add(user);
        try{
            Persistence.saveUsers(userArrayList);
            System.out.println("Usuario " + user.getUsername() + " guardado con exito");
        }catch (IOException err){
            System.out.println(err.getMessage());
        }

    }

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
        initializeUsersList();
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
                assert entradaDatos != null;
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
        if (validarUsuario()){
            System.out.println("Usuario ya usado, ingrese otro");
        }else {
            textArea.clear();
            textArea.setText("Conectado al chat!" + System.lineSeparator());
            System.out.println("Hasta aqui llego?");

            try {
                Cliente.socket = new Socket(ipTF.getText(), Integer.parseInt(portTF.getText()));
                Executor executor = Executors.newFixedThreadPool(2);
                executor.execute(this::recibirMensajesServidor);
                addUser(new User(nameTF.getText(), ipTF.getText(), Integer.parseInt(portTF.getText())));
                System.out.println("Conectado y guardado correctamente");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean validarUsuario(){
       for(User user: userArrayList){
           if (user.getUsername().equals(usuario)){
               return true;
           }
       }
        return false;
    }
}
