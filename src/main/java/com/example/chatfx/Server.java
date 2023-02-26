package com.example.chatfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {
    public static void main(String[] args) throws IOException {
        Logger log = Logger.getLogger(String.valueOf(Server.class));

        int puerto = 5050;
        int maximoConexiones = 10; // Maximo de conexiones simultaneas
        ServerSocket servidor = null;
        Socket socket = null;
        MensajesChat mensajes = new MensajesChat();

        try {
            servidor = new ServerSocket(puerto, maximoConexiones);
            while (true) {
                System.out.println("Servidor a la espera de conexiones.");
                socket = servidor.accept();
                System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");
                ConexionCliente cc = new ConexionCliente(socket, mensajes);
                cc.start();
                System.out.println("Cliente "+ socket.getInetAddress().getHostName()+" funcionando");
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally{
            try {
                socket.close();
                servidor.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar el servidor: " + ex.getMessage());
            }
        }
    }
    }
