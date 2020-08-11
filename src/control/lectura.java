/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author aless
 */
public class lectura {

    String tipo;
    String origen;
    String destino;
    String info_1;
    String info_2;
    
    String letra = "";
    int numero = 0;
    

    public lectura(String origen, String destino, String info_1, String info_2) {
        this.origen = origen;
        this.destino = destino;
        this.info_1 = info_1;
        this.info_2 = info_2;
        
        if (this.info_1.substring(2).equals("00000")){
//        Esto es un canto Ej: B12
            switch(this.info_1.substring(0,2)){
                case "000":
                    this.letra = "B";
                    break;
                case "001":
                    this.letra = "I";
                    break;
                case "010":
                    this.letra = "N";
                    break;
                case "011":
                    this.letra = "G";
                    break;
                case "100":
                    this.letra = "O";
                    break;
            }
            
            this.numero = Integer.parseInt(this.info_2.substring(1), 2);
            
        }
        
    }
    
        
}
