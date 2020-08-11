/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;
import com.fazecast.jSerialComm.*;
import java.util.ArrayList;

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
        //buffer para almacenar la trama
        byte[] readbuffer = new byte[5];
        
        System.out.println("Jugador Actual: "+this.jugador+"\n"); 
        try{
            System.out.println(" En espera del mensaje "+puertoE.bytesAvailable());
            //leer la trama
            puertoE.readBytes(readbuffer, 5);
        
            System.out.println("Mensaje: \n"
                + " flag: " +  readbuffer[0]
                + "\n info1: " + readbuffer[1] 
                + "\n info2: " +  readbuffer[2]
                + "\n flag: " +  readbuffer[3]);
            
            String info1 = ConversionString(readbuffer[1]);
            String info2 = ConversionString(readbuffer[2]);
            String letra = info1.substring(0, 3);
            String jugador_bingo = info1.substring(3, 6);
            String bingo = info1.substring(6, 8);
            
            switch(this.jugador){

                case "000":// lectura y envio de datos                 
                    enviar(letra, info2, getJugador(), bingo);         
                    break;

                case "001":             
                    enviar(letra, info2, getJugador(), bingo);
                    break;

                case "010":
                    enviar(letra, info2, getJugador(), bingo);
                    break;
                    
                case "011":
                    enviar(letra, info2, getJugador(), bingo);
                    break;
                    
                case "100":
                    enviar(letra, info2, getJugador(), bingo);
                    break;
            }         
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String makeInfo1(String letra){
    
        String mInfo1 = "00000000";
        
        switch(letra){
            case "B":
                mInfo1 = "00000000";
                break;
            case "I":
                mInfo1 = "00100000";
                break;
            case "N":
                mInfo1 = "01000000";
                break;
            case "G":
                mInfo1 = "01100000";
                break;
            case "O":
                mInfo1 = "10000000";
                break;
        }
        
        return mInfo1;
    }
    public String makeInfo2(int numero){
        String numero_bin = String.format("%08d", Long.parseLong(Integer.toBinaryString(numero)));
        return numero_bin;
    }
    
   public String ConversionString(byte p){
    String converter = Integer.toBinaryString(p & 0xFF);
    while(converter.length()<8) converter= "0" + converter;
    return converter;
    }
   
}