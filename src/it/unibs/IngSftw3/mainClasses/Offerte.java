package it.unibs.IngSftw3.mainClasses;

import java.util.ArrayList;

public class Offerte {
    private ArrayList<Offerta> listaOfferte= new ArrayList<Offerta>();

    public Offerte( ArrayList<Offerta> _offerteAccoppiate) {
        this.listaOfferte=_offerteAccoppiate;
    }
    public void addOffertaAunFruitore( Offerta o){
        this.listaOfferte.add(o);
    }

    public ArrayList<Offerta> getListaOfferte() {
        return listaOfferte;
    }
    public ArrayList<Offerta> getOfferteFromFruitore(String nomeFruiotore){
        ArrayList <Offerta> toReturn=new ArrayList<>();
        for(Offerta o: this.listaOfferte){
            if(o.getNomeFruitore().equals(nomeFruiotore)){
                toReturn.add(o);
            }
        }
        return toReturn;
    }
    public  String toStringOfferte(){
        StringBuffer s=new StringBuffer();
        int count=0;
        for(Offerta o:this.listaOfferte){
            s.append(count +") " );
            s.append(o.toStringOfferta());
            count++;
        }
        return s.toString();
    }

    public Offerta scegliOfferta(){
        System.out.println("Queste sono le tue offerte : ");
        System.out.println(this.toStringOfferte());
        int scelta=Utilita.leggiIntero("scegli il numero relativo all'offerta scelta: ",0, this.listaOfferte.size());
        return this.listaOfferte.get(scelta);
    }

    public void modificaOffertaEsistente(Offerta toChange){
        int indice=this.listaOfferte.indexOf(toChange);
        Offerta temp=this.listaOfferte.get(indice);
        temp.cambiaStato();
        this.listaOfferte.remove(indice);
        this.listaOfferte.add(temp);
    }

    public void togliRitirate(){
        ArrayList <Offerta> temp=new ArrayList<>();
        for(Offerta o: this.listaOfferte){
            if(o.getStatoAttuale()==StatoOfferta.APERTA.APERTA){
                temp.add(o);
            }
        }
        this.listaOfferte.clear();
        this.listaOfferte.addAll(temp);
    }

    public Offerte offerteFoglia(String nomeFoglia){
        ArrayList <Offerta> toRet=new ArrayList<>();
        for(Offerta o:this.listaOfferte){
            if(o.getNomeCategoria().equals(nomeFoglia)){
                toRet.add(o);
            }
        }
        Offerte ret=new Offerte(toRet);
        ret.togliRitirate();
        return ret;
    }
}

