package it.unibs.IngSftw3.mainClasses;

public enum StatoOfferta {
    RITIRATA,
    APERTA;

    public String toStringStato(){
        String s=null;
        if(this== RITIRATA){
            s="ritirata";
        }
        else{
            s="aperta";
        }
        return s;
    }
    public static StatoOfferta getStatoFromString(String stato){
        if(stato.equals("ritirata")){
            return RITIRATA;
        }
        else
            return APERTA;
    }
}
