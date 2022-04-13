package it.unibs.IngSftw3.mainClasses;

import java.util.ArrayList;
import java.util.HashMap;

public class Offerte {
    private HashMap<Fruitore, ArrayList<Offerta>> offerteAccoppiate=new HashMap<>();

    public Offerte(HashMap<Fruitore, ArrayList<Offerta>> _offerteAccoppiate) {
        this.offerteAccoppiate=_offerteAccoppiate;
    }
    public void addOffertaAunFruitore(Fruitore f, Offerta o){
        ArrayList <Offerta> temp=new ArrayList<>();
        if(this.offerteAccoppiate.containsKey(f)){
            temp.addAll(this.offerteAccoppiate.get(f));
            temp.add(o);
            this.offerteAccoppiate.replace(f, temp);
        }
        else{
            temp.add(o);
            this.offerteAccoppiate.put(f,temp);
        }

    }
}

