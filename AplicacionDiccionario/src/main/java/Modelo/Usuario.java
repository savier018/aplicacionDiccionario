package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Usuario {
    private String username;
    private String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static ArrayList<Usuario> cargarUsuarios(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/text/usuarios.txt"));){
            br.readLine();
            String linea;
            while ((linea = br.readLine())!=null){
                String[] datos = linea.split(","); 
                usuarios.add(new Usuario(datos[0], datos[1]));
            }
        } catch (IOException e){
            System.out.println(e);
        }
        return usuarios;
    }
    
    public static void sobreescribirFichero(ArrayList<Usuario> usuarios){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/text/usuarios.txt"));){
            bw.write("Usuario,Contrasena");
            for(int i = 0;i<usuarios.size();i++){
                Usuario u= usuarios.get(i);
                bw.newLine();
                bw.write(u.getUsername() + "," + u.getPassword());
            }
        } catch (IOException e){
            System.out.println("error");
        }
    }
    
}
