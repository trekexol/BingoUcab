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
 * @author Boris
 */
public final class conn {
    SerialPort puertoE;
    SerialPort puertoS;
    String jugador;
    
    public String getJugador() {
        return jugador;
    }
    public void setJugador(String jugador) {
        this.jugador = jugador;
    }
    
    public conn(){}
    
    public conn(String jugador){
        setJugador(jugador);
        switch (jugador)
        {
            case "1":
                puertoE = SerialPort.getCommPorts()[1];
                puertoE.setComPortParameters(2400, 8, 0, 1);
                puertoS = SerialPort.getCommPorts()[2];
                puertoS.setComPortParameters(2400, 8, 0, 1);
                puertoE.openPort();
                puertoS.openPort();
                break;
            case "2":
                puertoE = SerialPort.getCommPorts()[3];
                puertoE.setComPortParameters(2400, 8, 0, 1);
                puertoS = SerialPort.getCommPorts()[4];
                puertoS.setComPortParameters(2400, 8, 0, 1);
                puertoE.openPort();
                puertoS.openPort();
                break;
            case "3":
                puertoE = SerialPort.getCommPorts()[5];
                puertoE.setComPortParameters(2400, 8, 0, 1);
                puertoS = SerialPort.getCommPorts()[6];
                puertoS.setComPortParameters(2400, 8, 0, 1);
                puertoE.openPort();
                puertoS.openPort();
                break;
            case "4":
                puertoE = SerialPort.getCommPorts()[7];
                puertoE.setComPortParameters(2400, 8, 0, 1);
                puertoS = SerialPort.getCommPorts()[8];
                puertoS.setComPortParameters(2400, 8, 0, 1);
                puertoE.openPort();
                puertoS.openPort();
                break;
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
}
