/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;



import ConexionSerial.ConexionSerialJrIng;
import conn.conexion;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Host extends javax.swing.JFrame {

    
    
    ArrayList<String[]> letras = new ArrayList();
    ConexionSerialJrIng Serial;
    
    conexion conn;
    
    int letra = -1;
    int numero = -1;
    
    String s_letra = "";
    
    public Host(conexion conn){
        initComponents();
        this.conn = conn;
        System.out.println("Jugador: "+conn.getJugador());
        initList();
        showTable();
        generateCanto();
        
        
    }
    
    private void initList(){
        for (int i = 0; i < 5;i++){
            String[] numeros = {"-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1"};
            this.letras.add(numeros);
        }
    }
    
    public void generateCanto (){
        if (checkHueco()){
            boolean repeat = true;
            System.out.println("Entrando while");
            while(repeat){
                repeat = false;
                Random ran = new Random();
                this.letra = ran.nextInt(5);
                switch (this.letra){
                    case 0:
                        System.out.println("B");
                        this.numero = ran.nextInt(15)+1;
                        break;
                    case 1:
                        System.out.println("I");
                        this.numero = ran.nextInt(15)+16;
                        break;
                    case 2:
                        System.out.println("N");
                        this.numero = ran.nextInt(15)+31;
                        break;
                    case 3:
                        System.out.println("G");
                        this.numero = ran.nextInt(15)+46;
                        break;
                    case 4:
                        System.out.println("O");
                        this.numero = ran.nextInt(15)+61;
                        break;
                }


                for (int i = 0; i < letras.size();i++){
                    if (i == this.letra){
                        System.out.println("Letra: "+i);
                        System.out.println("Num: "+this.numero);
                        String[] numeros = letras.get(i);
                        for (int j = 0; j < numeros.length; j++){
                            if (this.numero == Integer.parseInt(numeros[j])){
                                repeat = true;
                                System.out.println("Repetido, again.");
                            }
                        }
                        break;
                    }
                }
            }

            switch (this.letra){
                case 0:
                    this.s_letra = "B";
                    break;
                case 1:
                    this.s_letra = "I";
                    break;
                case 2:
                    this.s_letra = "N";
                    break;
                case 3:
                    this.s_letra = "G";
                    break;
                case 4:
                    this.s_letra = "O";
                    break;
            }

            lbCanto.setText(this.s_letra + "-" + Integer.toString(this.numero));
            
        }
        else{
            lbCanto.setText("No hay mas que generar");
        }
    }
 
    public boolean checkHueco(){
        boolean hayEspacio = false;
        
        for (int letra = 0; letra < letras.size(); letra ++){
            String[] numeros = letras.get(letra);
            for (int j = 0; j < numeros.length; j++){
                if (numeros[j].equals("-1")){
                    hayEspacio = true;
                    break;
                }
            }
            
            if(hayEspacio){
                break;
            }
           
        }
        
        return hayEspacio;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbCanto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInfo = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        mostrar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbCanto.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbCanto.setForeground(new java.awt.Color(255, 0, 0));
        getContentPane().add(lbCanto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 380, 200, 80));

        tblInfo.setBackground(new java.awt.Color(255, 255, 153));
        tblInfo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblInfo.setForeground(new java.awt.Color(102, 0, 255));
        tblInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "B", "I", "N", "G", "O"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInfo.setFocusable(false);
        tblInfo.setGridColor(new java.awt.Color(153, 0, 255));
        tblInfo.setRowHeight(20);
        tblInfo.setSelectionBackground(new java.awt.Color(255, 102, 255));
        jScrollPane1.setViewportView(tblInfo);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 320, 620, 330));

        jButton2.setBackground(new java.awt.Color(0, 204, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jButton2.setText("Enviar canto");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 260, 230, 80));

        mostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tablero5.jpg"))); // NOI18N
        getContentPane().add(mostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, 1370, 740));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
//        conn.enviar(conn.makeInfo1(s_letra),
//                conn.makeInfo2(this.numero));
        fillList();
        showTable();
        
        
        this.letra = -2;
        this.numero = -2;

        this.s_letra = "";
        
        System.out.println("Esperando que la vaina regrese.");
//        conn.lectura()

        generateCanto();
        
    }//GEN-LAST:event_jButton2ActionPerformed

   private void showTable(){
    tblInfo.removeAll();
    try{

        String[] columna = { "B","I","N","G","O"};
        DefaultTableModel dtm = new DefaultTableModel(null,columna);       
        
        System.out.println("Seteo letras");
        
        for (int filita = 0; filita < 15; filita ++){
        
            String[] fila = {"-1","-1","-1","-1","-1"};
            
            for (int letra = 0; letra < letras.size(); letra++){
                String[] letra_columna = letras.get(letra);
//                fila[letra] = (letra_columna[filita].equals("-1") ? ""+letra+"/"+filita : letra_columna[filita]);
                fila[letra] = (letra_columna[filita].equals("-1") ? "" : letra_columna[filita]);
            }
            
            dtm.addRow(fila);
                
        }

        System.out.println("Antes set tabla");
        tblInfo.setModel(dtm);
        System.out.println("Despues set tabla");
//
//        dtm.fireTableDataChanged();

    }catch(Exception e){
        e.printStackTrace();
    }
    
    

    }  

   private void fillList(){

       System.out.println("Agregando valor");
        System.out.println("Letra: " + this.letra);
        System.out.println("Numero: " + this.numero);
        
        ArrayList<String[]> temporal = new ArrayList();
        
        for (int letra = 0; letra < letras.size(); letra ++){
            String[] numeros = letras.get(letra);
            if (this.letra == letra){
                System.out.println("Encontre la letra.");
                for (int j = 0; j < numeros.length; j++){
                    if (numeros[j].equals("-1")){
                        System.out.println("Reemplazando: "+j);
                        numeros[j] = String.valueOf(this.numero);
                        System.out.println("Ahora vale: "+numeros[j]);
                        break;
                    }
                }
            }
            temporal.add(numeros);
        }
        this.letras = temporal;


    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbCanto;
    private javax.swing.JLabel mostrar;
    private javax.swing.JTable tblInfo;
    // End of variables declaration//GEN-END:variables
}
