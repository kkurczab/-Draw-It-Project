public class Gra extends Thread{

    private int numerTury;
    private int maksymalnaLiczbaTur;
    private int liczbaGraczy;
    private int[] wygrywaGracz;


    private Gracz[] gracze;

    public Gra(int liczbaGraczy){
        this.numerTury = 0;
        this.maksymalnaLiczbaTur = 10;
        this.liczbaGraczy = liczbaGraczy;
    }


}
