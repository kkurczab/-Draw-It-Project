import java.util.Random;

public class Gra extends Thread{

    private int numerTury;
    private int maksymalnaLiczbaTur;
    private int liczbaGraczy;
    public int wygrywaGracz;


    private Gracz[] gracze;

    public Gra(int liczbaGraczy){
        this.numerTury = 0;
        this.maksymalnaLiczbaTur = 10;
        this.liczbaGraczy = liczbaGraczy;
        this.gracze = new Gracz[liczbaGraczy];

        for(int i = 0; i<liczbaGraczy;i++){
            gracze[i] = new Gracz(false);
        }


        this.start();

    }

    public void dodajPunkt(){
        int liczbaP = gracze[getNrGracza()].getLiczbaPunktow();
        liczbaP++;
    }

    public int getNrGracza() {
        return numerTury%liczbaGraczy;
    }

    public int losujNrGracza(){
            return new Random().nextInt(liczbaGraczy);
    }

    public Gracz[] getGracze() {
        return gracze;
    }


}
