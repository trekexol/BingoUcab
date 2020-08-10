/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import conn.conexion;
import control.control;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
//import gnu.io.*; 
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cefre
 */
public class menu extends javax.swing.JFrame {

   
control controlelr = new control();


    public menu() {
        initComponents();
        jComboBox1.removeAllItems();
        jComboBox2.removeAllItems();
        for(String puerto: conexion.listaConexion()){
            jComboBox1.addItem(puerto);
            jComboBox2.addItem(puerto);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setText("En Linea");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, 270, 40));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Proyecto Bingo");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 362, 76));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Selecciona tus puertos");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 140, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 140, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Selecciona a tu jugador");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, -1, -1));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Host", "Jugador 1", "Jugador 2", "Jugador 3", "Jugador 4" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, -1, -1));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bingofondo.png"))); // NOI18N
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String jugador = "";

        System.out.println ("Conexiones de pruebas");

        System.out.println ("Indique el numero de jugador(del 1 al 4):");

        String str = jComboBox3.getItemAt(jComboBox3.getSelectedIndex());
        
        conexion conn = new conexion(jComboBox1.getSelectedIndex(),
                            jComboBox2.getSelectedIndex()); //Entrada, Salida
        tablero ventana_jugador;
        
        switch(str){
            
            case "Host":
                conn.setJugador("000");

                Host ventana_host = new Host(conn);
                controlelr.activaVentana(ventana_host, this);
               
                break;
            case "Jugador 1":
                ventana_jugador = new  tablero(conn);
                conn.setJugador("001");

            //                3 primeros bits de la info 1 son la letra:
            //                (sobran 5 y no alcanza para los 70 numeros del bingo
            //                000 - B
            //                001 - I
            //                010 - N
            //                011 - G
            //                100 - O

            //                Los siguientes 5 los puedes usar para indicar cualquier cosa:
            //                Ejemplo, anunciar el bingo de un jugador
            //                010 - Jugador 2
            //                01 - BINGO

            //                De los 8 bits de info 2, tomamos los 7 de la derecha
            //                Ejemplo, enviar el numero 65
            //                01000001

            //                EJEMPLO, ENVIAR O-65
            //                INFO 1: 10000000
            //                INFO 2: 01000001

            //                EJEMPLO DE RECIBIR BINGO (PROPUESTA) CON LA LETRA Y EL NUMERO:
            //                ENVIADO:
            //                INFO 1: 10001101
            //                INFO 2: 01000001

//            con.enviar("00000000", "00000010");

                System.out.println("Entrando a leer");
                conn.lectura();
                controlelr.activaVentana(ventana_jugador, this);
                break;

            case "Jugador 2":
                ventana_jugador = new  tablero(conn);
//            conexion con2 = new conexion(jComboBox1.getSelectedIndex(),
//                jComboBox2.getSelectedIndex());
                conn.setJugador("010");


                conn.lectura();
                controlelr.activaVentana(ventana_jugador, this);
                break;

            case "Jugador 3":
                ventana_jugador = new  tablero(conn);
//            conexion con3 = new conexion(jComboBox1.getSelectedIndex(),
//                jComboBox2.getSelectedIndex());
                conn.setJugador("011");

                conn.lectura();
                controlelr.activaVentana(ventana_jugador, this);
                break;

            case "Jugador 4":
                ventana_jugador = new  tablero(conn);
//            conexion con4 = new conexion(jComboBox1.getSelectedIndex(),
//                jComboBox2.getSelectedIndex());
                conn.setJugador("100");


                conn.lectura();

                controlelr.activaVentana(ventana_jugador, this);
                break;
            default:
                System.exit(0);
            break;
        }
        
           
           
          
                  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fondo;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
