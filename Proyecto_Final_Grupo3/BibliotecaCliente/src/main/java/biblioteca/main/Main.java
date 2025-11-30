package biblioteca.main;

import biblioteca.controller.LoginController;
import biblioteca.view.LoginView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception ignored) {}

        System.out.println("Iniciando Cliente Biblioteca...");

        // 2. Instanciar la Vista
        LoginView vista = new LoginView();

        // 3. Instanciar el Controlador (El puente)
        LoginController controller = new LoginController(vista);

        // 4. Iniciar la aplicación
        controller.iniciar();

}
}