package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class        SistemaMedicos {

    private static ArrayList<Medico> listaMedicos = new ArrayList<>();

    private static String NOMBRE_ARCHIVO = "medicos.txt";

    public static void main(){

        cargarDatos();
        mostrarMenu();

    }

    public static void mostrarMenu() {
        boolean salir = false;
        while(!salir) {

            String opcion = String.valueOf(JOptionPane.showInputDialog((Component)null, "-------- Gestion de medicos --------\n1 - Crear medico\n2 - Editar medico\n3 - Buscar medico\n4 - Eliminar medico\n5 - Salir\nDigite una opción para continuar: "));

            if (opcion == null){
                opcion = "5";
            }

            switch (opcion) {
                case "1":
                    crearMedico();
                    break;
                case "2":
                    editarMedico();
                    break;
                case "3":
                    buscarMedico();
                    break;
                case "4":
                    eliminarMedico();
                    break;
                case "5":
                    guardarDatos();
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

    private static void editarMedico(){
        String codigo = JOptionPane.showInputDialog("Digite el codigo del medico a editar: ");

        if (codigo == null){
            return;
        }

        Medico medico = buscarMedicoPorCodigo(codigo);

        if (medico == null){
            JOptionPane.showMessageDialog(null, "Error: Medico no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = JOptionPane.showInputDialog("Digite el nombre nuevo:", medico.getNombre());
        if (nombre != null) {
            medico.setNombre(nombre);
        }

        String telefono = JOptionPane.showInputDialog("Digite el teléfono nuevo: ", medico.getTelefono());
        if (telefono != null) {
            medico.setTelefono(telefono);
        }

        String email = JOptionPane.showInputDialog("Digite el email nuevo:", medico.getEmail());
        if (email != null) {
            medico.setEmail(email);
        }

        while (true) {
            String especialidadTexto = JOptionPane.showInputDialog("Digite la especialidad nueva:", medico.getEspecialidad().name());

            if (especialidadTexto == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada. No se modificó la especialidad.");
                break;
            }

            try {
                Especialidad especialidad = Especialidad.valueOf(especialidadTexto.toUpperCase());
                medico.setEspecialidad(especialidad);
                break;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Especialidad inválida. Intente nuevamente.");
            }
        }

        JOptionPane.showMessageDialog(null, "Datos del médico actualizados correctamente.");
        JOptionPane.showMessageDialog(null, medico.toString());

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

    private static void cargarDatos(){

        File archivo = new File(NOMBRE_ARCHIVO);

        if(!archivo.exists()){
            return;
        }

        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(NOMBRE_ARCHIVO));

            while (dataInputStream.available() > 0 ) {

                String codigo = dataInputStream.readUTF();
                String nombre = dataInputStream.readUTF();
                Especialidad esp = Especialidad.valueOf(dataInputStream.readUTF());
                String telefono = dataInputStream.readUTF();
                String email = dataInputStream.readUTF();

                listaMedicos.add(new Medico(codigo,nombre,esp,telefono,email));
            }

            dataInputStream.close();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: No se encontró el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de lectura/escritura: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error general al cargar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    private static void guardarDatos(){

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(NOMBRE_ARCHIVO));

            for (Medico medico : listaMedicos){

                dataOutputStream.writeUTF(medico.getCodigo());
                dataOutputStream.writeUTF(medico.getNombre());
                dataOutputStream.writeUTF(medico.getEspecialidad().name());
                dataOutputStream.writeUTF(medico.getTelefono());
                dataOutputStream.writeUTF(medico.getEmail());
            }

            dataOutputStream.close();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: No se encontró el archivo para guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de lectura/escritura al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error general al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

