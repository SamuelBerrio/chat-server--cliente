package com.example.chatfx.persistence;

import com.example.chatfx.model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Persistence {

    public static final String RUTA_ARCHIVO_CLIENTES = "src/main/java/com/example/chatfx/persistence/resources/users.txt";

    public static void saveUsers(ArrayList<User> usersList) throws IOException {

        String content = "";

        for(User user: usersList) {

            content+= user.getUsername()+"~"+user.getIp()+"~"+user.getPort()+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_CLIENTES, content, false);
    }

    public static ArrayList<User> loadUsers() throws FileNotFoundException, IOException {

        ArrayList<User> userList = new ArrayList<>();

        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_CLIENTES);

        for (String s : contenido) {
            User myUser = new User();
            myUser.setUsername(s.split("~")[0]);
            myUser.setIp(s.split("~")[1]);
            myUser.setPort(Integer.parseInt(s.split("~")[2]));
            userList.add(myUser);
        }

        return userList;
    }
}
