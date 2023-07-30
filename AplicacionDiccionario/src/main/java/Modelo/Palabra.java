
package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Palabra {
    private String termino;
    private String definicion;

    public Palabra(String termino, String definicion) {
        this.termino = termino;
        this.definicion = definicion;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }
    
    public static ArrayList<Palabra> cargarPalabras(){
        ArrayList<Palabra> listaPalabras = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/text/palabras.txt"));){
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null){
                String[] datos = linea.split("-"); 
                listaPalabras.add(new Palabra(datos[0], datos[1]));
            }
        } catch (IOException e){
            System.out.println(e);
        }
        return listaPalabras;
    }
    
    public static Map<Character, ArrayList<Palabra>> sublistasPorLetra(ArrayList<Palabra> lista){
        Map<Character, ArrayList<Palabra>> mapaPalabra = new HashMap<>();
        for (Palabra p : lista){
            char letraInicial = Character.toUpperCase(p.getTermino().charAt(0));
            mapaPalabra.computeIfAbsent(letraInicial, c -> new ArrayList<>()).add(p);
        }      
        return mapaPalabra;
    }
    
    @Override
    public String toString(){
        return termino;
    }
    
}
