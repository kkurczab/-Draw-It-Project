import java.io.*;
import java.net.*;

public class Player {//szukanie adresu ip to bedzie iteracja po wszystkich adresach w podsieci XD, bo serwerem bedzie host ktory pierwszy odpali gre
    private String ipSerwera = "localhost";//tymczasowo do pierwszych testow///isReachable useful!!!
    private PolaczenieOdKlienta polaczenieOdKlienta;
    private int playerID;
    private int[] pozostaliGracze;
    /////////////Metody




    //////////////Kontruktor
    public Player(){

    }
    public void polaczZSerwerem(){
        polaczenieOdKlienta = new PolaczenieOdKlienta();
    }
    ///////////////////Klasy
    private class PolaczenieOdKlienta{
        private Socket socket;
        private DataInputStream daneIN;
        private DataOutputStream daneOUT;

        public PolaczenieOdKlienta(){
            System.out.println("Klient!!!!");
            try{
                socket = new Socket(ipSerwera,51724);///InetAddress.getByAddress(new byte[] {(byte)192, (byte)168, (byte)0, (byte)102}),
                daneIN = new DataInputStream(socket.getInputStream());
                daneOUT = new DataOutputStream(socket.getOutputStream());
                playerID = daneIN.readInt();
                System.out.println("Moj numer gracza to: " + playerID);
            }catch (IOException e)
            {
                System.out.println("Skocket w kontruktorze pol klienta");
            }
        }
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.polaczZSerwerem();
    }
}
