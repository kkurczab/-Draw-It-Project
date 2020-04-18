import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Player {//haslo to ostatni oktet adresu ip
    private byte[] oktetyIP;
    private PolaczenieOdKlienta polaczenieOdKlienta;
    private short playerID = -5;
    private int[] pozostaliGracze;
    private short nrGraczaUWladzy=-1;
    private byte kolor = 3;
    private short oldX;
    private short oldY;
    private short currentX;
    private short currentY;
    private byte flaga;
    String slowo;
    private short otrzymanyNR;

    /////////////Metody
    public void setOktet4(int oktet4) throws IllegalArgumentException{
        if(oktet4 < 256 && oktet4 > 0)
            this.oktetyIP[3] = (byte)oktet4;
        else
            throw new IllegalArgumentException();
    }

    public short getNrGraczaUWladzy() {
        return nrGraczaUWladzy;
    }

    public byte getKolor() {
        return kolor;
    }

    public void setKolor(byte kolor) {
        this.kolor = kolor;
    }

    public short getOldX() {
        return oldX;
    }

    public void setOldX(short oldX) {
        this.oldX = oldX;
    }

    public short getOldY() {
        return oldY;
    }

    public void setOldY(short oldY) {
        this.oldY = oldY;
    }

    public short getCurrentX() {
        return currentX;
    }

    public void setCurrentX(short currentX) {
        this.currentX = currentX;
    }

    public short getCurrentY() {
        return currentY;
    }

    public void setCurrentY(short currentY) {
        this.currentY = currentY;
    }

    public String getSlowo() {
        return slowo;
    }

    public void setSlowo(String slowo) {
        this.slowo = slowo;
    }

    public short getOtrzymanyNR() {
        return otrzymanyNR;
    }

    //////////////Kontruktor
    public Player() {
        try {
            oktetyIP = InetAddress.getLocalHost().getAddress();
        }catch (UnknownHostException e){
            System.out.println("wyjakte IP");
        }

        //playerID = -1;//serwer nie wstal

    }
    public void polaczZSerwerem(){
        polaczenieOdKlienta = new PolaczenieOdKlienta();
    }
    public void wyslijKolor() {
        try {
            flaga = 1;
            polaczenieOdKlienta.daneOUT.writeByte(flaga);
            polaczenieOdKlienta.daneOUT.writeByte(kolor);
            polaczenieOdKlienta.daneOUT.flush();
        }catch (IOException e){

        }
    }

    public void wyslijSlowo() {
        try{
            flaga = 2;
            polaczenieOdKlienta.daneOUT.writeByte(flaga);
            polaczenieOdKlienta.daneOUT.writeShort(playerID);
            polaczenieOdKlienta. daneOUT.writeUTF(slowo);
            polaczenieOdKlienta.daneOUT.flush();
            //slowo = "";
        }catch (IOException e){
            System.out.println("zle sie dzieje");
        }
    }

    public void wyslijOldX() {
        try{
        flaga = 3;
        polaczenieOdKlienta.daneOUT.writeByte(flaga);
        polaczenieOdKlienta. daneOUT.writeShort(oldX);
        polaczenieOdKlienta.daneOUT.flush();
        }catch (IOException e){}
    }
    public void wyslijOldY() {
        try{
        flaga = 4;
        polaczenieOdKlienta.daneOUT.writeByte(flaga);
        polaczenieOdKlienta.daneOUT.writeShort(oldY);
        polaczenieOdKlienta.daneOUT.flush();
        }catch (IOException e){}
    }
    public void wyslijCurrentX() {
        try{
        flaga = 5;
        polaczenieOdKlienta.daneOUT.writeByte(flaga);
        polaczenieOdKlienta.daneOUT.writeShort(currentX);
        polaczenieOdKlienta.daneOUT.flush();
        }catch (IOException e){}
    }
    public void wyslijCurrentY() {
        try{
        flaga = 6;
        polaczenieOdKlienta.daneOUT.writeByte(flaga);
        polaczenieOdKlienta.daneOUT.writeShort(currentY);
        polaczenieOdKlienta.daneOUT.flush();
        }catch (IOException e){}
    }
    ///////////////////Klasy
    private class PolaczenieOdKlienta extends Thread{
        private Socket socket;
        private DataInputStream daneIN;
        private DataOutputStream daneOUT;

        public void run(){

            while (true){
                //Thread.yield();
                synchroDanych();
            }
        }

        public void synchroDanych(){
                try {
                    flaga = daneIN.readByte();
                    if(flaga == 2) {
                        otrzymanyNR = daneIN.readShort();
                        slowo = daneIN.readUTF();
                    }
                    if(flaga == 0)
                        nrGraczaUWladzy = daneIN.readShort();
                    if (nrGraczaUWladzy != playerID) {
                       // System.out.println("Przegryw" + playerID);
                        if(flaga == 1)
                            kolor = daneIN.readByte();
                        if(flaga == 3)
                            oldX = daneIN.readShort();
                        if(flaga == 4)
                            oldY = daneIN.readShort();
                        if(flaga == 5)
                            currentX = daneIN.readShort();
                        if(flaga == 6)
                            currentY = daneIN.readShort();
                    }
                } catch (IOException e) { }
        }

        public PolaczenieOdKlienta() {

            System.out.println("Klient!!!!");
            try{
                socket = new Socket(InetAddress.getByAddress(new byte[] {(byte)oktetyIP[0], (byte)oktetyIP[1], (byte)oktetyIP[2], (byte)oktetyIP[3]}),51724);
                daneIN = new DataInputStream(socket.getInputStream());
                daneOUT = new DataOutputStream(socket.getOutputStream());
                playerID = daneIN.readShort();
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
