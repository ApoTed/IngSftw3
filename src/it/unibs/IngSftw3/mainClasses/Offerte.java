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
}

