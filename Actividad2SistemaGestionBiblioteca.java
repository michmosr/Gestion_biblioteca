/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package actividad2sistemagestionbiblioteca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import java.util.Scanner;

public class Actividad2SistemaGestionBiblioteca {

   
    public static void main(String[] args) {
        
        ArrayList<String[]> libros = new ArrayList<>();
        LinkedList<String[]> usuarios = new LinkedList<>();
        Stack<String[]> librosPrestados = new Stack<>();
        Queue<String[]> colaEspera = new LinkedList<>();
        
        Scanner entrada = new Scanner(System.in);
    
        int opcion;
        do{
                System.out.println("====================================");
                System.out.println("   SISTEMA DE GESTIÓN DE BIBLIOTECA   ");
                System.out.println("====================================");
                System.out.println("          MENÚ PRINCIPAL           ");
                System.out.println("====================================");
                System.out.println("1. Agregar Libro");
                System.out.println("2. Registrar Usuario");
                System.out.println("3. Prestar Libro");
                System.out.println("4. Devolver Libro");
                System.out.println("5. Mostrar Libros Disponibles");
                System.out.println("6. Mostrar Usuarios Registrados");
                System.out.println("7. Salir");
                System.out.println("====================================");
                System.out.println("       Autor: Michael Mosquera      ");
                System.out.println("====================================");
                System.out.print("Seleccione una opción: ");
            
            while(!entrada.hasNextInt()){
                System.out.println("Error: Ingrese un valor valido!");
                entrada.next();
                System.out.println("Seleccione una opcion: ");
            }
            
            opcion = entrada.nextInt();
            entrada.nextLine();
            switch(opcion){
                case 1:
                        System.out.println("Ingrese el ID del libro ");
                        String idLibro = entrada.nextLine();
                        boolean idDuplicado = false;
                        for(String[] libro: libros){
                            if(libro[0].equals(idLibro)){
                                idDuplicado = true;
                                break;
                            }
                        }
                        if(idDuplicado){
                            System.out.println("Error: El ID del libreo ya existe!");
                        }else{

                              System.out.println("Ingrese el nombre del libro");
                              String nombreLibro = entrada.nextLine();
                              System.out.println("Ingrese el autor del libro: ");
                              String autorLibro = entrada.nextLine();
                              libros.add(new String[]{idLibro, nombreLibro, autorLibro});
                                System.out.println("Libro agregado exitosamente.");
                        }                    
                    break;
                    
                case 2:
                        System.out.println("Ingrese la cedula del usuario, solo numeros: ");

                        while(!entrada.hasNextInt()){
                        System.out.println("Error: Ingrese un valor valido!");
                        entrada.next();
                        System.out.println("Ingrese la cedula del usuario, solo numeros: ");
                        }
                          int cedulaUsuario = entrada.nextInt();
                          entrada.nextLine();
                          System.out.println("Ingrese el nombre del usuario: ");
                          String nombreUsuario = entrada.nextLine();
                          System.out.println("Ingrese los apellidos del usuario: ");
                          String apellidosUsuario = entrada.nextLine();

                          boolean cedulaDuplicado = false;
                          for(String[] usuario: usuarios){
                            if(usuario[0].equals(String.valueOf(cedulaUsuario))){
                                cedulaDuplicado = true;
                                break;
                            }
                        }
                        if(cedulaDuplicado){
                            System.out.println("Error: El usuario ya existe!");
                        }else{
                            usuarios.add(new String[]{String.valueOf(cedulaUsuario), nombreUsuario, apellidosUsuario});
                            System.out.println("Usuario registrado exitosamente."); 
                        }                      
                        break;
                        
                case 3:
                    System.out.println("Ingrese el id del libro que desea prestar: ");
                    String idPrestar = entrada.nextLine();

                    System.out.println("Ingrese la cédula del usuario que presta el libro: ");
                    while (!entrada.hasNextInt()) {
                        System.out.println("Error: Ingrese un valor valido!");
                        entrada.next();  // Limpiar entrada incorrecta
                        System.out.println("Ingrese la cédula del usuario, solo números: ");
                    }
                    int cedulaPrestar = entrada.nextInt();
                    entrada.nextLine();  // Limpiar el buffer de entrada

                    boolean usuarioRegistrado = false;
                    for (String[] usuario : usuarios) {
                        if (usuario[0].equals(String.valueOf(cedulaPrestar))) {
                            usuarioRegistrado = true;
                            break;
                        }
                    }

                    if (!usuarioRegistrado) {
                        System.out.println("Error: El usuario con cédula " + cedulaPrestar +
                                           " no está registrado. No se puede prestar el libro.");
                    } else {
                        boolean libroEncontrado = false;
                        for (String[] libro : libros) {
                            if (libro[0].equals(idPrestar)) {
                                librosPrestados.push(new String[]{idPrestar, String.valueOf(cedulaPrestar)});
                                libros.remove(libro);
                                libroEncontrado = true;
                                System.out.println("Libro prestado con éxito");
                                break;
                            }
                        }

                        if (!libroEncontrado) {
                            System.out.println("Libro no disponible. ¿Desea agregar a la cola de espera? (Sí/No)");
                            String respuesta = entrada.nextLine();
                            if (respuesta.equalsIgnoreCase("sí")) {
                                colaEspera.add(new String[]{idPrestar, String.valueOf(cedulaPrestar)});
                                System.out.println("Agregado a la cola de espera");
                            }
                        }
                    }
                    break;

               case 4:
                    if (!librosPrestados.isEmpty()) {
                        String[] libroDevuelto = librosPrestados.pop();
                        libros.add(new String[]{libroDevuelto[0], libroDevuelto[1], libroDevuelto[2]});
                        System.out.println("Libro devuelto exitosamente.");

                        if (!colaEspera.isEmpty()) {
                            String[] proximosEnCola = colaEspera.poll();
                            System.out.println("El usuario con cédula " + proximosEnCola[1] + 
                                               " está en cola y ahora prestará el libro con ID: " + proximosEnCola[0]);
                            librosPrestados.push(proximosEnCola);
                        }
                    } else {
                        System.out.println("No hay libros prestados.");
                    }
                    break;
                case 5:
                    if (libros.isEmpty()) {
                        System.out.println("No hay libros disponibles.");
                    } else {
                        System.out.println("Libros Disponibles:");
                        System.out.printf("%-10s %-20s %-20s%n", "ID", "Nombre", "Autor");
                        System.out.println("----------------------------------------");

                        for (String[] libro : libros) {
                            System.out.printf("%-10s %-20s %-20s%n", libro[0], libro[1], libro[2]);
                        }
                    }
                    break;
                case 6:
                    if (usuarios.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                    } else {
                        System.out.println("Usuarios Registrados:");
                        System.out.printf("%-10s %-20s %-20s%n", "Cédula", "Nombre", "Apellidos");
                        System.out.println("---------------------------------------------");

                        for (String[] usuario : usuarios) {
                            System.out.printf("%-10s %-20s %-20s%n", usuario[0], usuario[1], usuario[2]);
                        }
                    }
                    break;  
                case 7:
                    System.out.println("Gracias por visitar la biblioteca. Te quiero profe!");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo");
                    
            }  
        }while (opcion !=7);
    }
    
}

