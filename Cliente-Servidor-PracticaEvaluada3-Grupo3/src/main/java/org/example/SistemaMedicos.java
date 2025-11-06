package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class SistemaMedicos {

    private static ArrayList<Medico> listaMedicos = new ArrayList<>();

    private String NOMBRE_ARCHIVO = "medicos.txt";

    public static void main(){

        //cargarDatos();
        mostrarMenu();


    }

    public static void mostrarMenu() {
        boolean salir = false;
        while(!salir) {

            String opcion = String.valueOf(JOptionPane.showInputDialog((Component)null, "-------- Gestion de medicos --------\n1 - Crear medico\n2 - Editar medico\n3 - Buscar medico\n4 - Eliminar medico\n5 - Salir\nDigite una opción para continuar: "));


            switch (opcion) {
                case "1":
                    crearMedico();
                    break;
                case "2":
                    //editarMedico();
                    break;
                case "3":
                    buscarMedico();
                    break;
                case "4":
                    eliminarMedico();
                    break;
                case "5":
                    //guardarDatos();
                    salir = true;
                    break;
                default:
                    JOptionPane.showMessageDialog((Component)null, "Opción inválida", "Error", 0);
            }
        }

    }

    private static Medico buscarMedicoPorCodigo(String codigo){
        for(Medico medico : listaMedicos) {
            if(medico.getCodigo().equals(codigo)){
                return medico;
            }
        }
        return null;
    }

    private static void crearMedico(){

        String codigo = JOptionPane.showInputDialog("Digite el codigo unico");
        if (codigo == null || codigo.isEmpty()) {
            return;
        }

        if (buscarMedicoPorCodigo(codigo) != null){
            JOptionPane.showMessageDialog(null, "Error El codigo "+ codigo + " ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = JOptionPane.showInputDialog("Digite el nombre: ");
        String tel = JOptionPane.showInputDialog("Digite el telefono: ");
        String email = JOptionPane.showInputDialog("Digite el email: ");

        Especialidad especialidad = null;

        while (especialidad == null){
            String espInput = JOptionPane.showInputDialog("Digite la especialidad: ");
            if (espInput == null){
                return;
            }
            try{
                especialidad = Especialidad.valueOf(espInput.toUpperCase());
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"Especialidad no valida. Intente de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        Medico medicoNuevo = new Medico(codigo,nombre,especialidad,tel,email);
        listaMedicos.add(medicoNuevo);
        JOptionPane.showMessageDialog(null, "Medico agregado exitosamente.");

    }

    private static void buscarMedico(){
        String codigo = JOptionPane.showInputDialog("Digite el codigo del medico a buscar:");

        if (codigo == null){
            return;
        }

        Medico medico = buscarMedicoPorCodigo(codigo);

        if (medico != null){
            JOptionPane.showMessageDialog(null, medico.toString(), "Medico encontrado", JOptionPane.INFORMATION_MESSAGE);
        } else{
            JOptionPane.showMessageDialog(null, "Error: Medico no encontrado. ", "Error" ,JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void eliminarMedico(){
        String codigo = JOptionPane.showInputDialog("Digite el codigo del medico a eliminar");

        if (codigo == null){
            return;
        }

        Medico medico = buscarMedicoPorCodigo(codigo);

        if (medico == null){
            JOptionPane.showMessageDialog(null, "Error: Medico no encontrado. ", "Error" ,JOptionPane.ERROR_MESSAGE);
            return;
        }

        listaMedicos.remove(medico);
        JOptionPane.showMessageDialog(null, "Medico eliminado exitosamente.");
    }
}

