package control;


import java.text.SimpleDateFormat;
import javax.swing.*;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import java.util.*;
import javax.swing.table.DefaultTableModel;



public class control {

    JFrame ventana;
    
    private int mes;
    private int dia;
    
   
    public control() {
    }
    
    
     
      public control(JFrame ventana){
       this.ventana = ventana;
    }
    
      public void iniciaVentana(JFrame ventana){
       ventana.setLocationRelativeTo(null); //permite centrar la ventana
      
// ventana.setIconImage(new ImageIcon(ruta).getImage()); 
      //ImageIcon permite agregar un icono en el frame superior (hacia la
      //izquierda) de la ventana para evitar que salga la taza de java por 
      //defecto, debe importarse la librería javax.swing.ImageIcon
     
      ventana.setResizable(false); // no permite que el usuario cambie el tamaño 
      // de la ventana, de lo contrario los componentes pueden verse afectados
      
      ventana.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
         //para evitar cerrar con la "X", solo se sale de la ventana a través
        // del botón Salir del Sistema.
       // btnGuardar.setVisible(false); //DESAPARECER EL BOTON
    }
     
        public void activaVentana(JFrame ventana,JFrame ventana2) {
        
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana2.dispose(); //elimina la ventana de la memoria, incluyendo datos
        //el dispose() garantiza que no dejes ventanas ejecutándose
        //en esta etapa no nos precupamos por guardar los datos, lo haremos
        //cuando veamos xml
     }
        
     
    
}