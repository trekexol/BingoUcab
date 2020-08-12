/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;
import com.fazecast.jSerialComm.*;
import control.lectura;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aless
 */
public class conexion implements Runnable{
    SerialPort puertoE;
    SerialPort puertoS;
    String jugador;
    String bandera_I = "00000001"; //1
    String bandera_F = "01001011"; //75
    JLabel label;
    JTable tabla;
    ArrayList<String[]> letras;
    JButton boton;
    JFrame ventana;
    
    public boolean leyo = false;
    public lectura leido = null;
    
    public String getJugador() {
        return jugador;
    }
    public void setJugador(String jugador) {
        this.jugador = jugador;
    }
    
   public conexion(int entrada, int salida){
        puertoE = SerialPort.getCommPorts()[entrada];
        puertoE.setComPortParameters(2400, 8, 0, 1);
        //puertoEntrada.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 1, 1);
        puertoE.openPort();
        if(entrada!=salida){
            // Los puertos de salida y entrada son diferentes(para realizar pruebas en una sola maquina)
            puertoS = SerialPort.getCommPorts()[salida];
            puertoS.setComPortParameters(2400, 8, 0, 1);
            //puertoSalida.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 1, 1);
            puertoS.openPort(); 
        }

    }

    public JFrame getVentana() {
        return ventana;
    }

    public void setVentana(JFrame ventana) {
        this.ventana = ventana;
    }
    
    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JTable getTabla() {
        return tabla;
    }

    public ArrayList<String[]> getLetras() {
        return letras;
    }

    public void setLetras(ArrayList<String[]> letras) {
        this.letras = letras;
    }
    

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    public JButton getBoton() {
        return boton;
    }

    public void setBoton(JButton boton) {
        this.boton = boton;
    }
    
    
   
    
    
   public static ArrayList<String> listaConexion(){
       ArrayList<String> puertos = new ArrayList<String>();
       SerialPort[] comPort = SerialPort.getCommPorts();
       
       for(int i=0;i<comPort.length;i++){
           puertos.add(comPort[i].getSystemPortName());
       }
       
       return puertos;
   } 
   
   
   public String jugadorSiguiente(String jugador){ //retorna el valor del siguiente jugador
       if(jugador.equals("000")) return "001";
       
       if(jugador.equals("001")) return "010";
       
       if(jugador.equals("010")) return "011";
       
       if(jugador.equals("011")) return "100";
       
       return "000";
   }
   
    public void enviar(String letra, String numero, String jugador_bingo, String bingo){
        System.out.println("enviar.Jugador_Actual: "+this.jugador);
        try{    
            byte[] enviar = new byte[5];
            //Banera 00000001
            enviar[0] = (byte)Short.parseShort(bandera_I, 2);
            //letra: 3 bits, jugador_bingo: 3 bits, bingo: 2 bits
            enviar[1] = (byte)Short.parseShort(letra+jugador_bingo+bingo,2);
            //el numero completo 7 bits más significativos
            enviar[2] = (byte)Short.parseShort(numero, 2);
            //Bandera 75
            enviar[3] = (byte)Short.parseShort(bandera_F, 2);

            //El mensaje que se enviara
            System.out.println("Inicio de envio de mensaje: \n" 
                 + " flag: " +  enviar[0]
                 + "\n info1: " + enviar[1] 
                 + "\n info2: " +  enviar[2]
                 + "\n flag: " +  enviar[3]);
            puertoS.writeBytes( enviar, enviar.length);
            }
        catch(Exception e1){
            e1.printStackTrace();
        }
    }
   
    //Hilo para recibir la trama
    public void RecibirInfo(){
        conexion proceso1 = this;
        new Thread(proceso1).start();
    }
    
    @Override
    public void run(){
        while (true){
            //buffer para almacenar la trama
            byte[] readbuffer = new byte[5];

            System.out.println("Jugador Actual: "+this.jugador+"\n"); 
            try{
                System.out.println(" En espera del mensaje "+puertoE.bytesAvailable());
                while(puertoE.bytesAvailable()<4){}
                //leer la trama
                puertoE.readBytes(readbuffer, 5);
                
                System.out.println("Mensaje: \n"
                    + " flag: " +  readbuffer[0]
                    + "\n info1: " + readbuffer[1] 
                    + "\n info2: " +  readbuffer[2]
                    + "\n flag: " +  readbuffer[3]);

                String info1 = ConversionString(readbuffer[1]);
                String info2 = ConversionString(readbuffer[2]);

//                String info1 = String.valueOf(readbuffer[1]);
//                String info2 = String.valueOf(readbuffer[2]);

                System.out.println("Infor1: "+info1);
                System.out.println("Infor2: "+info2);

                String letra = info1.substring(0, 3);
                String jugador_bingo = info1.substring(3, 6);
                String bingo = info1.substring(6, 8);


                switch(this.jugador){
    //                SI EL QUE LEE ES EL HOST, PROCESAMOS SI HAY BINGO
    //                SI NO HAY BINGO, TERMINAMOS LA LECTURA Y ESPERAMOS QUE DESDE EL FRONT SE ENVIE EL CANTO NUEVO
                    case "000":// lectura y envio de datos                 

                        System.out.println("RECIBIENDO EN HOST");

                        if (!jugador_bingo.equals("000")){
                            System.out.println("SOMEONE SAYS BINGO!");

                            System.out.println("ENVIAR TRAMA NOTIFICACION FIN DE PARTIDA");
    //                        UNA TRAMA NORMAL PERO SIN LETRA NI NUMERO JIJI

                            enviar("000", "00000000", jugador_bingo, bingo);

                            this.leido = new lectura("000", "00000000",jugador_bingo,bingo);
                            this.leyo = true;
                            this.label.setText("BINGO: "+this.leido.jugador_bingo);
                                                        
                            this.boton.setText("FIN");
                            this.boton.setBackground(Color.red);
                            this.boton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent ae) {
                                    ventana.dispose();
                                    System.exit(0);
                                }
                            });
                        }
                        else{
    //                        PROCESAR SIGUIENTE CANTO
                            this.leido = new lectura(letra, info2,"000","00");
                            this.leyo = true;
                        }
                        break;
                    case "001":  
                        if (letra.equals("000")){
                            
                            System.out.println("SOMEONE SAYS BINGO!");

                            System.out.println("ENVIAR TRAMA NOTIFICACION FIN DE PARTIDA");
    //                        UNA TRAMA NORMAL PERO SIN LETRA NI NUMERO JIJI

                            this.leido = new lectura("000", "00000000",jugador_bingo,bingo);
                            this.leyo = true;
                            this.label.setText("BINGO: "+this.leido.jugador_bingo);
                                                        
                            this.boton.setText("FIN");
                            this.boton.setBackground(Color.red);
                            this.boton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent ae) {
                                    ventana.dispose();
                                    System.exit(0);
                                }
                            });
                            if (jugador_bingo.equals(this.jugador)){
                                break;
                            }
    //                        return leido;
                        }
                        else{
                            
    //                        PROCESAR UN CANTO NORMAL
                            this.leido = new lectura(letra, info2,"000","00");
                            this.leyo = true;
                            
                            this.label.setText(this.leido.letra +"-"+this.leido.numero);
                            this.searchValue(this.leido.n_letra,this.leido.numero);
                            this.showTable();
    //                        return leido;

                        }

                        enviar(letra, info2, jugador_bingo, bingo);
                        break;


                    case "010":             
                        if (letra.equals("000")){
                            System.out.println("SOMEONE SAYS BINGO!");

                            System.out.println("ENVIAR TRAMA NOTIFICACION FIN DE PARTIDA");
    //                        UNA TRAMA NORMAL PERO SIN LETRA NI NUMERO JIJI
                            this.leido = new lectura("000", "00000000",jugador_bingo,bingo);
                            this.leyo = true;
                            this.label.setText("BINGO: "+this.leido.jugador_bingo);
                                                        
                            this.boton.setText("FIN");
                            this.boton.setBackground(Color.red);
                            this.boton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent ae) {
                                    ventana.dispose();
                                    System.exit(0);
                                }
                            });
                            
                            if (jugador_bingo.equals(this.jugador)){
                                break;
                            }
                        }
                        else{
    //                        PROCESAR UN CANTO NORMAL
                            this.leido = new lectura(letra, info2,"000","00");
                            this.leyo = true;
                            this.label.setText(this.leido.letra +"-"+this.leido.numero);
                            this.searchValue(this.leido.n_letra,this.leido.numero);
                            this.showTable();

                        }

                        enviar(letra, info2, jugador_bingo, bingo);
                        break;

                    case "011":           
                        if (letra.equals("000")){
                            System.out.println("SOMEONE SAYS BINGO!");

                            System.out.println("ENVIAR TRAMA NOTIFICACION FIN DE PARTIDA");
    //                        UNA TRAMA NORMAL PERO SIN LETRA NI NUMERO JIJI
                            this.leido = new lectura("000", "00000000",jugador_bingo,bingo);
                            this.leyo = true;
                            this.label.setText("BINGO: "+this.leido.jugador_bingo);
                                                        
                            this.boton.setText("FIN");
                            this.boton.setBackground(Color.red);
                            this.boton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent ae) {
                                    ventana.dispose();
                                    System.exit(0);
                                }
                            });
                            if (jugador_bingo.equals(this.jugador)){
                                break;
                            }
                        }
                        else{
    //                        PROCESAR UN CANTO NORMAL
                            this.leido = new lectura(letra, info2,"000","00");
                            this.leyo = true;
                            this.label.setText(this.leido.letra +"-"+this.leido.numero);
                            this.searchValue(this.leido.n_letra,this.leido.numero);
                            this.showTable();

                        }

                        enviar(letra, info2, jugador_bingo, bingo);
                        break;

                    case "100":           
                        if (letra.equals("000")){
                            System.out.println("SOMEONE SAYS BINGO!");

                            System.out.println("ENVIAR TRAMA NOTIFICACION FIN DE PARTIDA");
    //                        UNA TRAMA NORMAL PERO SIN LETRA NI NUMERO JIJI
                            this.leido = new lectura("000", "00000000",jugador_bingo,bingo);
                            this.leyo = true;
                            this.label.setText("BINGO: "+this.leido.jugador_bingo);
                                                        
                            this.boton.setText("FIN");
                            this.boton.setBackground(Color.red);
                            this.boton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent ae) {
                                    ventana.dispose();
                                    System.exit(0);
                                }
                            });
                            if (jugador_bingo.equals(this.jugador)){
                                break;
                            }
                        }
                        else{
    //                        PROCESAR UN CANTO NORMAL
                            this.leido = new lectura(letra, info2,"000","00");
                            this.leyo = true;
                            this.label.setText(this.leido.letra +"-"+this.leido.numero);
                            this.searchValue(this.leido.n_letra,this.leido.numero);
                            this.showTable();

                        }

                        enviar(letra, info2, jugador_bingo, bingo);
                        break;
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public lectura leer(){
        
        //buffer para almacenar la trama
        byte[] readbuffer = new byte[5];
        
        System.out.println("Jugador Actual: "+this.jugador+"\n"); 
        try{
            System.out.println(" En espera del mensaje "+puertoE.bytesAvailable());
            while(puertoE.bytesAvailable()<4){}
            //leer la trama
            puertoE.readBytes(readbuffer, 5);
        
            System.out.println("Mensaje: \n"
                + " flag: " +  readbuffer[0]
                + "\n info1: " + readbuffer[1] 
                + "\n info2: " +  readbuffer[2]
                + "\n flag: " +  readbuffer[3]);
            
            String info1 = ConversionString(readbuffer[1]);
            String info2 = ConversionString(readbuffer[2]);
            
            System.out.println("Infor1: "+info1);
            System.out.println("Infor2: "+info2);
            
            String letra = info1.substring(0, 3);
            String jugador_bingo = info1.substring(3, 6);
            String bingo = info1.substring(6, 8);
            
            lectura leido = null;
            
            switch(this.jugador){
//                SI EL QUE LEE ES EL HOST, PROCESAMOS SI HAY BINGO
//                SI NO HAY BINGO, TERMINAMOS LA LECTURA Y ESPERAMOS QUE DESDE EL FRONT SE ENVIE EL CANTO NUEVO
                case "000":// lectura y envio de datos                 
                    
                    System.out.println("RECIBIENDO EN HOST");
                    
                    if (!jugador_bingo.equals("000")){
                        System.out.println("SOMEONE SAYS BINGO!");
                    
                        System.out.println("ENVIAR TRAMA NOTIFICACION FIN DE PARTIDA");
//                        UNA TRAMA NORMAL PERO SIN LETRA NI NUMERO JIJI
                        
                        enviar("000", "00000000", jugador_bingo, bingo);
                        
                        leido = new lectura("000", "00000000",jugador_bingo,bingo);
                    }
                    else{
//                        PROCESAR SIGUIENTE CANTO
                        leido = new lectura(letra, info2,"000","00");
                    }
                    break;
                case "001":  
                    if (letra.equals("000")){
                        System.out.println("SOMEONE SAYS BINGO!");
                    
                        System.out.println("ENVIAR TRAMA NOTIFICACION FIN DE PARTIDA");
//                        UNA TRAMA NORMAL PERO SIN LETRA NI NUMERO JIJI

                        leido = new lectura("000", "00000000",jugador_bingo,bingo);
//                        return leido;
                    }
                    else{
//                        PROCESAR UN CANTO NORMAL
                        leido = new lectura(letra, info2,"000","00");
//                        return leido;
                        
                    }
                    
                    enviar(letra, info2, jugador_bingo, bingo);
                    break;
                    

                case "010":             
                    if (letra.equals("000")){
                        System.out.println("SOMEONE SAYS BINGO!");
                    
                        System.out.println("ENVIAR TRAMA NOTIFICACION FIN DE PARTIDA");
//                        UNA TRAMA NORMAL PERO SIN LETRA NI NUMERO JIJI
                        leido = new lectura("000", "00000000",jugador_bingo,bingo);
                    }
                    else{
//                        PROCESAR UN CANTO NORMAL
                        leido = new lectura(letra, info2,"000","00");
                        
                    }
                    
                    enviar(letra, info2, jugador_bingo, bingo);
                    break;
                    
                case "011":           
                    if (letra.equals("000")){
                        System.out.println("SOMEONE SAYS BINGO!");
                    
                        System.out.println("ENVIAR TRAMA NOTIFICACION FIN DE PARTIDA");
//                        UNA TRAMA NORMAL PERO SIN LETRA NI NUMERO JIJI
                        leido = new lectura("000", "00000000",jugador_bingo,bingo);
                    }
                    else{
//                        PROCESAR UN CANTO NORMAL
                        leido = new lectura(letra, info2,"000","00");
                        
                    }
                    
                    enviar(letra, info2, jugador_bingo, bingo);
                    break;
                    
                case "100":           
                    if (letra.equals("000")){
                        System.out.println("SOMEONE SAYS BINGO!");
                    
                        System.out.println("ENVIAR TRAMA NOTIFICACION FIN DE PARTIDA");
//                        UNA TRAMA NORMAL PERO SIN LETRA NI NUMERO JIJI
                        leido = new lectura("000", "00000000",jugador_bingo,bingo);
                    }
                    else{
//                        PROCESAR UN CANTO NORMAL
                        leido = new lectura(letra, info2,"000","00");
                        
                    }
                    
                    enviar(letra, info2, jugador_bingo, bingo);
                    break;
            }
            
            return leido;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    public String makeInfo1(String letra){
    
        String mInfo1 = "00000000";
        
        switch(letra){
            case "B":
                mInfo1 = "00100000";
                break;
            case "I":
                mInfo1 = "01000000";
                break;
            case "N":
                mInfo1 = "01100000";
                break;
            case "G":
                mInfo1 = "10000000";
                break;
            case "O":
                mInfo1 = "10100000";
                break;
        }
        
        return mInfo1;
    }
    
    public String makeInfo2(int numero){
        String numero_bin = String.format("%08d", Long.parseLong(Integer.toBinaryString(numero)));
        return numero_bin;
    }
    
    public void searchValue(int n_letra, int numero){
        ArrayList<String[]> temporal = new ArrayList();
        
        for (int letra = 0; letra < letras.size(); letra ++){
            String[] numeros = letras.get(letra);
            if (n_letra == letra){
                System.out.println("Encontre la letra.");
                for (int j = 0; j < numeros.length; j++){
                    if (numeros[j].equals(String.valueOf(numero))){
                        System.out.println("Reemplazando: "+j);
                        numeros[j] = "X - "+numeros[j];
                        System.out.println("Ahora vale: "+numeros[j]);
                        break;
                    }
                }
            }
            temporal.add(numeros);
        }
        this.letras = temporal;
    }
    
    private void showTable(){
    tabla.removeAll();
    try{

        String[] columna = { "B","I","N","G","O"};
        DefaultTableModel dtm = new DefaultTableModel(null,columna);       
        
        System.out.println("Seteo letras");
        
        for (int filita = 0; filita < 5; filita ++){
        
            String[] fila = {"-1","-1","-1","-1","-1"};
            
            for (int letra = 0; letra < letras.size(); letra++){
                String[] letra_columna = letras.get(letra);
//                fila[letra] = (letra_columna[filita].equals("-1") ? ""+letra+"/"+filita : letra_columna[filita]);
                fila[letra] = (letra_columna[filita].equals("-1") ? "" : letra_columna[filita]);
            }
            
            dtm.addRow(fila);
                
        }

        tabla.setModel(dtm);
//
//        dtm.fireTableDataChanged();

    }catch(Exception e){
        e.printStackTrace();
    }
    
    

    }
    
   public String ConversionString(byte p){
//    String converter = Integer.toBinaryString(p & 0xFF);
//    while(converter.length()<8) converter= "0" + converter;
    return String.format("%8s", Integer.toBinaryString(p & 0xFF)).replace(' ', '0');
    }
   
}