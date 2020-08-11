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
public class conexion {
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
   
    public void enviar(String ste, String ste2){
        System.out.println("enviar.Jugador_Actual: "+this.jugador);
        try{    
            byte[] enviar = new byte[5];
            enviar[0] = (byte)Short.parseShort(bandera_I, 2);
            enviar[1] = (byte)Short.parseShort(jugador+jugadorSiguiente(this.jugador)+"00",2);
            enviar[2] = (byte)Short.parseShort(ste,2);
            enviar[3] = (byte)Short.parseShort(ste2, 2);
            enviar[4] = (byte)Short.parseShort(bandera_F, 2);

        //El mensaje que se enviara
            System.out.println("Inicio de envio de mensaje: " 
                 +  enviar[0]+ ": flag, "
                 +  enviar[1]+ ": informacion jugador, "
                 +  enviar[2]+ ": informacion, "
                 +  enviar[3]+ ": informacion 2, "
                 +  enviar[4]+ ": flag \n");
            puertoS.writeBytes( enviar, enviar.length);     

            }
        catch(Exception e1){
            e1.printStackTrace();
        }
    }
   
    public void lectura(){
 
        byte[] readbuffer = new byte[5];
        
        System.out.println("Jugador Actual: "+this.jugador+"\n"); 
        try{
            System.out.println(" En espera del mensaje");      
            while(puertoE.bytesAvailable()<5){}
            
        int numero = puertoE.readBytes(readbuffer, 5);
            
        for(int j=0;j<numero;j++){
            System.out.println(" "+ ConversionString(readbuffer[j]));
        }
        
            System.out.println("Mensaje: "
            +readbuffer[0]+" bandera, "
            +readbuffer[1]+" informacion 1, "
            +readbuffer[2]+" informacion 2, "
            +readbuffer[3]+" informacion 3, "
            +readbuffer[4]+" bandera ");
            
        String info = ConversionString(readbuffer[1]);
        String informacion1 = ConversionString(readbuffer[2]);
        String infor2 = ConversionString(readbuffer[3]);
        String origen = info.substring(0, 2);
        String destino = info.substring(2, 5);
//        String extra = info.substring(5,8);
     //   String instruc = info.substring(5); //instruccion de accion de la partida 
            
        switch(destino){
               
            case "000":// lectura y envio de datos
                    System.out.println("Jugador 1 \n");
                    System.out.println("Origen: "+origen);
                    System.out.println("Destino: "+destino);
                    System.out.println("Info: "+informacion1);
                    System.out.println("Info: "+infor2);
                    
                    enviar(informacion1,infor2);           
                break;
                    
                case "001": 
                    System.out.println("Jugador 2 \n");
                    System.out.println("Origen: "+origen);
                    System.out.println("Destino: "+destino);
                    System.out.println("Info: "+informacion1);
                    System.out.println("Info: "+infor2);
                    
                    enviar(informacion1,infor2);
                  break;
                    
                case "010":
                    System.out.println("Jugador 3 \n");
                    System.out.println("Origen: "+origen);
                    System.out.println("Destino: "+destino);
                    System.out.println("Info: "+informacion1);
                    System.out.println("Info: "+infor2);
                    
                    enviar(informacion1,infor2);
                    break;
                case "011":
                    System.out.println("Jugador 4 \n");
                    System.out.println("Origen: "+origen);
                    System.out.println("Destino: "+destino);
                    System.out.println("Info: "+informacion1);
                    System.out.println("Info: "+infor2);
                    
                    enviar(informacion1,infor2);
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