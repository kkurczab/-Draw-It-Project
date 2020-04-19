import java.io.*;
import java.net.*;

public class Server {
    private Gra gra;
    private ServerSocket socketSerwer;
    private short nrGracza;
    private PolaczenieDoKlienta[] gracze;
    private int maxLiczbaGraczy;
    private short nrGraczaUWladzy;
    private byte kolor;
    private short currentX;
    private short currentY;
    private byte kolorBuff;
    private short currentXBuff;
    private short currentYBuff;
    private byte flaga;//1=kolor, 2 = slowo, 5 = currx, 6 = curry
    private String slowo = "";
    private String slowoBuff = "";
    private short otrzymanyNR;
    private String[] imiona;


   ///////////////////Metody
    public void przyjeciePolaczenia(){
        System.out.println("Oczekiwanie na graczy");
        try {
            while (nrGracza < maxLiczbaGraczy) {
                Socket socket = socketSerwer.accept();//accept zwraca socket,(kliencka wersja serwer socketa)
                System.out.println("Gracz nr " + nrGracza + " polaczyl sie");
                PolaczenieDoKlienta polaczenieDoKlienta = new PolaczenieDoKlienta(socket,nrGracza);
                gracze[nrGracza] = polaczenieDoKlienta;
                Thread t = new Thread(polaczenieDoKlienta);
                t.start();
                nrGracza++;
            }
            System.out.println("Maksymalna liczb garczy osiganieta!!!");
        }catch(IOException e){
            System.out.println("przechwycono wyjątek z podlaczania klienta ");
        }
    }

    ///////////////////Konstruktor
    public Server(){
        this.maxLiczbaGraczy = 2;
        gra = new Gra(maxLiczbaGraczy);
        nrGraczaUWladzy =(short)gra.losujNrGracza();//trzeba przeniesc do zmiany tury czy czegos
        gracze = new PolaczenieDoKlienta[maxLiczbaGraczy];
        System.out.print("Serwer ruszyl!!!");
        nrGracza = 0;
        try{
            socketSerwer = new ServerSocket(51724);

        }catch(IOException e){
            System.out.print("przechwycono wyjątek z kontrukcji socketa");
        }
    }

    ////Klasy

    private class  PolaczenieDoKlienta implements Runnable{
        private Socket socket;
        private DataOutputStream daneOUT;
        private DataInputStream daneIN;
        private short playerID;

        public  PolaczenieDoKlienta(Socket s, short id) {
            socket = s;
            playerID = id;
            try {
                daneIN = new DataInputStream(socket.getInputStream());
                daneOUT = new DataOutputStream(socket.getOutputStream());

            } catch (IOException e) {
                System.out.println("Wyjatek z polaczena strona serwera");
            }

        }
        public void run(){

            new Thread(() -> {
                while (true) {
                    Thread.yield();
                    try {
                        if (!slowo.equals(slowoBuff)) {
                            flaga = 2;
                            daneOUT.writeByte(flaga);
                            daneOUT.writeShort(otrzymanyNR);
                            daneOUT.writeUTF(slowo);
                            daneOUT.flush();
                            slowo = slowoBuff;
                        }
                        if (nrGraczaUWladzy != playerID) {
                            if (kolor != kolorBuff) {
                                kolor = kolorBuff;
                                flaga = 1;
                                daneOUT.writeByte(flaga);
                                daneOUT.writeByte(kolor);
                                daneOUT.flush();
                            }
                            if (currentX != currentXBuff) {
                                currentX = currentXBuff;
                                flaga = 5;
                                daneOUT.writeByte(flaga);
                                daneOUT.writeShort(currentX);
                                daneOUT.flush();
                            }
                            if (currentY != currentYBuff) {
                                currentY = currentYBuff;
                                flaga = 6;
                                daneOUT.writeByte(flaga);
                                daneOUT.writeShort(currentY);
                                daneOUT.flush();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


            try{
                daneOUT.writeShort(playerID);
                flaga = 0;
                    daneOUT.writeByte(flaga);
                    daneOUT.writeShort(nrGraczaUWladzy);
                    daneOUT.flush();
                    flaga = -1;

                while(true){
                    Thread.yield();
                    flaga = daneIN.readByte();
                    if(nrGraczaUWladzy == playerID){
                        if(flaga == 1)
                            kolorBuff = daneIN.readByte();
                        if(flaga == 5)
                            currentXBuff = daneIN.readShort();
                        if(flaga == 6)
                            currentYBuff = daneIN.readShort();
                    }
                    if(flaga == 2) {
                        otrzymanyNR = daneIN.readShort();
                        slowo = daneIN.readUTF();
                    }

                }

            }catch (IOException e){

            }
        }
    }


}
