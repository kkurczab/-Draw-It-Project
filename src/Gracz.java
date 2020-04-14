public class Gracz {

    private boolean czyMojaTura; //1- tak, 0-nie
    //private int pozycjaRanking; //ktore miejsce punktowo zajmuje wsrod graczy
    private int liczbaPunktow;
    private int numerGracza;

    private String nazwaGracza;

    public Gracz(boolean czyMojaTura){
        this.czyMojaTura = czyMojaTura;
      //  this.pozycjaRanking = pozycjaRanking;
        this.nazwaGracza = "";
    }

    public void dodajPunkt(int numerGracza, int liczbaDodanychPunktow){
        liczbaPunktow =+ liczbaDodanychPunktow;
    }

    public void setCzyMojaTura(boolean czyMojaTura) {
        this.czyMojaTura = czyMojaTura;
    }

    public boolean isCzyMojaTura() { return czyMojaTura; }

  //  public int getPozycjaRanking() { return pozycjaRanking; }

    public int getLiczbaPunktow(){ return liczbaPunktow; }

    public int getNumerGracza() { return numerGracza; }

    public String getNazwaGracza() { return nazwaGracza; }


    public void setNazwaGracza(String nazwaGracza) {
        if(nazwaGracza.equals(""))
            throw new IllegalArgumentException();
        else
            this.nazwaGracza = nazwaGracza; }

}
