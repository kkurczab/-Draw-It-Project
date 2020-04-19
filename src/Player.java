
import java.io.*;
import java.net.*;

public class Player {//haslo to ostatni oktet adresu ip
    private byte[] oktetyIP;
    private PolaczenieOdKlienta polaczenieOdKlienta;
    private short playerID = -5;
    private short nrGraczaUWladzy = -1;
    private byte kolor = 3;
    private short currentX;
    private short currentY;
    private byte flaga;
    private String slowo="";
    private String imiona[];
    private short otrzymanyNR;
    private int seed;

    /////////////Metody
    public void setOktet4(int oktet4) throws IllegalArgumentException {
        if (oktet4 < 256 && oktet4 > 0)
            this.oktetyIP[3] = (byte) oktet4;
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

    public synchronized String getSlowo() {
        return slowo;
    }

    public void setSlowo(String slowo) {
        this.slowo = slowo;
    }
    public void setImie(String imie,int playerID) {
        this.imiona[playerID] = imie;
    }
    public String[] getImiona() {
        return imiona;
    }

    public int getSeed() {
        return seed;
    }

    public short getOtrzymanyNR() {
        return otrzymanyNR;
    }

    //////////////Kontruktor
    public Player() {
        imiona = new String[]{"",""};
        try {
            oktetyIP = InetAddress.getLocalHost().getAddress();
        } catch (UnknownHostException e) {
            System.out.println("wyjakte IP");
        }

    }

    public void polaczZSerwerem() {
        polaczenieOdKlienta = new PolaczenieOdKlienta();
    }

    public int getPlayerID() {
        return playerID;
    }

    public void wyslijKolor() {
        try {
            flaga = 1;
            polaczenieOdKlienta.daneOUT.writeByte(flaga);
            polaczenieOdKlienta.daneOUT.writeByte(kolor);
            polaczenieOdKlienta.daneOUT.flush();
        } catch (IOException e) {

        }
    }

    public void wyslijSlowo() {
        try {
            flaga = 2;
            polaczenieOdKlienta.daneOUT.writeByte(flaga);
            polaczenieOdKlienta.daneOUT.writeShort(playerID);
            polaczenieOdKlienta.daneOUT.writeUTF(slowo);
            polaczenieOdKlienta.daneOUT.flush();
        } catch (IOException e) {
            System.out.println("zle sie dzieje");
        }
    }
    public void wyslijImie() {
        try {
            flaga = 3;
            polaczenieOdKlienta.daneOUT.writeByte(flaga);
            polaczenieOdKlienta.daneOUT.writeShort(playerID);
            polaczenieOdKlienta.daneOUT.writeUTF(imiona[playerID]);
            polaczenieOdKlienta.daneOUT.flush();
        } catch (IOException e) {
            System.out.println("zle sie dzieje");
        }
    }


    public void wyslijCurrentX() {
        try {
            flaga = 5;
            polaczenieOdKlienta.daneOUT.writeByte(flaga);
            polaczenieOdKlienta.daneOUT.writeShort(currentX);
            polaczenieOdKlienta.daneOUT.flush();
        } catch (IOException e) {
        }
    }

    public void wyslijCurrentY() {
        try {
            flaga = 6;
            polaczenieOdKlienta.daneOUT.writeByte(flaga);
            polaczenieOdKlienta.daneOUT.writeShort(currentY);
            polaczenieOdKlienta.daneOUT.flush();
        } catch (IOException e) {
        }
    }

    ///////////////////Klasy
    private class PolaczenieOdKlienta extends Thread {
        private Socket socket;
        private DataInputStream daneIN;
        private DataOutputStream daneOUT;

        public void run() {

            while (true) {
                odbiorDanych();
            }
        }

        public void odbiorDanych() {
            try {
                flaga = daneIN.readByte();
                if (flaga == 2) {
                    otrzymanyNR = daneIN.readShort();
                    slowo = daneIN.readUTF();
                }
                if (flaga == 3) {

                    imiona[0] = daneIN.readUTF();
                    imiona[1] = daneIN.readUTF();
                }
                if (flaga == 0)
                    nrGraczaUWladzy = daneIN.readShort();
                if (nrGraczaUWladzy != playerID) {
                    if (flaga == 1)
                        kolor = daneIN.readByte();
                    if (flaga == 5)
                        currentX = daneIN.readShort();
                    if (flaga == 6)
                        currentY = daneIN.readShort();
                }
            } catch (IOException e) {
            }
        }

        public PolaczenieOdKlienta() {

            System.out.println("Klient!!!!");
            try {
                socket = new Socket(InetAddress.getByAddress(new byte[]{(byte) oktetyIP[0], (byte) oktetyIP[1], (byte) oktetyIP[2], (byte) oktetyIP[3]}), 51724);
                daneIN = new DataInputStream(socket.getInputStream());
                daneOUT = new DataOutputStream(socket.getOutputStream());
                playerID = daneIN.readShort();
                seed = daneIN.readInt();
                System.out.println("Moj numer gracza to: " + playerID);
                this.start();

            } catch (IOException e) {
                System.out.println("Socket w kontruktorze pol klienta");
            }
        }
    }
}




