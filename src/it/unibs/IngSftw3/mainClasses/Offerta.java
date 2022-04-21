package it.unibs.IngSftw3.mainClasses;

import java.util.ArrayList;
import java.util.HashMap;

public class Offerta {

    private String nomeCategoria;
    private HashMap <CampoNativo, String> compliazioni=new HashMap<>();
    private StatoOfferta statoAttuale;
    private ArrayList <StatoOfferta> statiPassati=new ArrayList<>();
    private String nomeFruitore;

    public Offerta( String _nomeCategoria, HashMap <CampoNativo, String> _compilazioni,StatoOfferta _statoAttuale,String _nomeFruitore,ArrayList<StatoOfferta> _statiPassati ){
        this.nomeCategoria=_nomeCategoria;
        this.compliazioni=_compilazioni;
        this.statoAttuale=_statoAttuale;
        this.nomeFruitore=_nomeFruitore;
        this.statiPassati=_statiPassati;
    }

    public Offerta() {
    }

    public boolean creaOfferta(Sistema s,String nome){
        boolean successo=false;
        System.out.println(s.toStringSistema());
        boolean continua=true;
        do{
            int ger=Utilita.leggiIntero("inserisci il numero della gerarchia dove si trova la categoria scelta",1,s.getListaGerarchie().size());
            String n=Utilita.leggiStringaNonVuota("Inserisci il nome della categoria doce vuoi pubblicare il tuo articolo: ");
            Categoria c=s.findCategoria(n,ger);
            if(c!=null){
                this.nomeCategoria=c.getNome();
                this.compilaCampi(c);
                this.statoAttuale=StatoOfferta.APERTA;
                this.nomeFruitore=nome;
                this.statiPassati=new ArrayList<StatoOfferta>();
                continua=false;
            }
            else{
                int temp=Utilita.leggiIntero("La categoria inserita non esiste, se vuoi riprovare premi 1 altrimenti 0",0,1);
                if(temp==0)
                    continua=false;
            }
        }while(continua);

        return successo;
    }

    public void compilaCampi(Categoria c){
        for(CampoNativo camp:c.getCampiNativi()){
            if(camp.isObbligatoria()){
                String descr=Utilita.leggiStringaNonVuota("Inserisci la descrizione realtiva al campo "+camp.getNomeCampo()+" : ");
                this.compliazioni.put(camp, descr);
            }
            else{
                int sce=Utilita.leggiIntero("Se vuoi inserire una descrizione al campo "+camp.getNomeCampo()+" inserisci 1 altrimenti 0",0,1);
                String descr="";
                if(sce==1){
                    descr=Utilita.leggiStringaNonVuota("Inserisci la descrizione realtiva al campo "+camp.getNomeCampo()+" : ");
                }
                this.compliazioni.put(camp, descr);
            }
        }
        System.out.println("Fine compilazione campi");
    }

    public ArrayList<StatoOfferta> getStatiPassati() {
        return statiPassati;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public HashMap<CampoNativo, String> getCompliazioni() {
        return compliazioni;
    }

    public StatoOfferta getStatoAttuale() {
        return statoAttuale;
    }

    public String getNomeFruitore() {
        return nomeFruitore;
    }
}
