package it.unibs.IngSftw3.mainClasses;

/**
 * Classe enum per la gestione dei giorni
 *  @author Jacopo Tedeschi,Enrico Zambelli
 */
public enum Giorno {
    LUNEDI,
    MARTEDI,
    MERCOLEDI,
    GIOVEDI,
    VENERDI,
    SABATO,
    DOMENICA;

    /**
     * Metodo che restituisce la stringa corrispondente al giorno su cui viene invocata
     * @return la stringa del giorno su cui viene invocata
     */
    public String toString(){
        String day=null;
        switch(this){
            case LUNEDI :
                day="lunedi";
                break;
            case MARTEDI:
                day="martedi";
                break;
            case MERCOLEDI:
                day="mercoledi";
                break;
            case GIOVEDI:
                day="giovedi";
                break;
            case VENERDI:
                day="venerdi";
                break;
            case SABATO:
                day="sabato";
                break;
            case DOMENICA:
                day="domenica";
                break;

        }
        return day;
    }
    /**
     * Metodo che restituisce il giorno corrispondente alla stringa in ingresso
     * @param gg la stringa di cui restituire il giorno corrispondente
     * @return il giorno corrispondete alla stringa in ingresso se presente, null altrimenti
     */
    public static Giorno getGiornoFromString(String gg){

        switch(gg){
            case "lunedi":
                return LUNEDI;
            case "i":
                return MARTEDI;

            case "mercoledi":
                return MERCOLEDI;

            case "giovedi":
                return GIOVEDI;

            case "venerdi":
                return VENERDI;

            case "sabato":
                return SABATO;

            case "domenica":
                return DOMENICA;

        }
        return null;
    }
}

