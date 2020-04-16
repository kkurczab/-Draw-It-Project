import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Player {//haslo to ostatni oktet adresu ip
    private byte[] oktetyIP;
    private PolaczenieOdKlienta polaczenieOdKlienta;
    private int playerID;
    private int[] pozostaliGracze;
    private int nrGraczaUWladzy=-1;
    private int kolor;
    private int oldX;
    private int oldY;
    private int currentX;
    private int currentY;
    private int flaga;

    /////////////Metody
    public void setOktet4(int oktet4) {
        if(oktet4 < 256 && oktet4 > 0)
            this.oktetyIP[3] = (byte)oktet4;
        else
            throw new IllegalArgumentException();
    }

    public int getNrGraczaUWladzy() {
        return nrGraczaUWladzy;
    }

    public int getKolor() {
        return kolor;
    }

    public void setKolor(int kolor) {
        this.kolor = kolor;
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }



    //////////////Kontruktor
    public Player() {
        try {
            oktetyIP = InetAddress.getLocalHost().getAddress();
        }catch (UnknownHostException e){}

        playerID = -1;//serwer nie wstal

    }
    public void polaczZSerwerem(){
        polaczenieOdKlienta = new PolaczenieOdKlienta();
    }
    public void wyslijKolor() {
        try {
            flaga = 1;
            polaczenieOdKlienta.daneOUT.writeInt(flaga);
            polaczenieOdKlienta.daneOUT.writeInt(kolor);
            polaczenieOdKlienta.daneOUT.flush();
        }catch (IOException e){
            System.out.println("");
        }
    }
    public void wyslijOldX() {
        try{
        flaga = 3;
        polaczenieOdKlienta.daneOUT.writeInt(flaga);
        polaczenieOdKlienta. daneOUT.writeInt(oldX);
        polaczenieOdKlienta.daneOUT.flush();
        }catch (IOException e){}
    }
    public void wyslijOldY() {
        try{
        flaga = 4;
        polaczenieOdKlienta.daneOUT.writeInt(flaga);
        polaczenieOdKlienta.daneOUT.writeInt(oldY);
        polaczenieOdKlienta.daneOUT.flush();
        }catch (IOException e){}
    }
    public void wyslijCurrentX() {
        try{
        flaga = 5;
        polaczenieOdKlienta.daneOUT.writeInt(flaga);
        polaczenieOdKlienta.daneOUT.writeInt(currentX);
        polaczenieOdKlienta.daneOUT.flush();
        }catch (IOException e){}
    }
    public void wyslijCurrentY() {
        try{
        flaga = 6;
        polaczenieOdKlienta.daneOUT.writeInt(flaga);
        polaczenieOdKlienta.daneOUT.writeInt(currentY);
        polaczenieOdKlienta.daneOUT.flush();
        }catch (IOException e){}
    }
    ///////////////////Klasy
    private class PolaczenieOdKlienta extends Thread{
        private Socket socket;
        private DataInputStream daneIN;
        private DataOutputStream daneOUT;

        public void run(){


                System.out.println(nrGraczaUWladzy + "wladza");

            while (true){
                synchroDanych();
            }
        }

        public void synchroDanych(){
                try {

                    flaga = daneIN.readInt();
                    if(flaga == 0) {
                        nrGraczaUWladzy = daneIN.readInt();

                        System.out.println("wladza ha" + nrGraczaUWladzy);
                    }
                    if (nrGraczaUWladzy != playerID) {
                        System.out.println("Przegryw" + playerID);
                        if(flaga == 1) {
                            System.out.println("przed kolorem" + kolor);
                            kolor = daneIN.readInt();
                            System.out.println("po kolorze" + kolor);
                        }
                        if(flaga == 3)
                            oldX = daneIN.readInt();
                        if(flaga == 4)
                            oldY = daneIN.readInt();
                        if(flaga == 5)
                            currentX = daneIN.readInt();
                        if(flaga == 6)
                            currentY = daneIN.readInt();
                    }
                } catch (IOException e) { }
        }

        public PolaczenieOdKlienta() {

            System.out.println("Klient!!!!");
            try{
                socket = new Socket(InetAddress.getByAddress(new byte[] {(byte)oktetyIP[0], (byte)oktetyIP[1], (byte)oktetyIP[2], (byte)oktetyIP[3]}),51724);
                daneIN = new DataInputStream(socket.getInputStream());
                daneOUT = new DataOutputStream(socket.getOutputStream());
                playerID = daneIN.readInt();
                System.out.println("Moj numer gracza to: " + playerID);
                this.start();

            }catch (IOException e)
            {
                System.out.println("Socket w kontruktorze pol klienta");
            }
        }
    }


    public static void main(String[] args) {
        Player player = new Player();
        player.polaczZSerwerem();
        player.polaczenieOdKlienta.synchroDanych();
        System.out.println(player.kolor);

    }
    public int getPlayerID(){
        return playerID;
    }

}
