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

    public String tipo;
    public String origen;
    public String destino;
    public String info_1;
    public String info_2;
    public String letra = "";
    public String b_letra = "";
    public int n_letra = 0;
    public int numero = 0;
    public String jugador_bingo = "";
    public boolean bingo = false;
    
    public lectura(String letra, String numero, String jugador_bingo, String bingo){
    
        this.b_letra = letra;
        
        switch(letra){
            case "000":
                System.out.println("TRAMA DE BINGO");
                if (bingo.equals("01")){
                    this.bingo = true;
                } 
                
                switch (jugador_bingo){
                    case "000":
                        this.jugador_bingo = "Host";
                        break;
                    case "001":
                        this.jugador_bingo = "Jugador 1";
                        break;
                    case "010":
                        this.jugador_bingo = "Jugador 2";
                        break;
                    case "011":
                        this.jugador_bingo = "Jugador 3";
                        break;
                    case "100":
                        this.jugador_bingo = "Jugador 4";
                        break;
                }
                
                break;
            case "001":
                this.letra = "B";
                this.n_letra = 0;
                this.numero = Integer.parseInt(numero, 2);
                break;
            case "010":
                this.letra = "I";
                this.n_letra = 1;
                this.numero = Integer.parseInt(numero, 2);
                break;
            case "011":
                this.letra = "N";
                this.n_letra = 2;
                this.numero = Integer.parseInt(numero, 2);
                break;
            case "100":
                this.letra = "G";
                this.n_letra = 3;
                this.numero = Integer.parseInt(numero, 2);
                break;
            case "101":
                this.letra = "O";
                this.n_letra = 4;
                this.numero = Integer.parseInt(numero, 2);
                break;
        }

          
    }
    
    
        
}
