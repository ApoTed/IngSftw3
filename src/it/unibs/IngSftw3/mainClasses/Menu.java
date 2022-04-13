package it.unibs.IngSftw3.mainClasses;


/**
 * Classe per la gestione dei menu
 *  @author Jacopo Tedeschi, Enrico Zambelli
 */
public class Menu {


    final private static String CORNICE = "--------------------------------";
    final private static String VOCE_USCITA = "0\tEsci";
    final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata : ";
    final private static String[] VOCI_Configuratore = new String[]{"Inserimento nuova gerarchia","Visualizzazione delle gerarchie","Modifica dei parametri"};
    public static final String[] VOCI_Fruitore = new String[]{"Visualizza le radici e i parametri di sistema"};
    public static final int ZERO = 0;
    public static final int UNO = 1;

    private String titolo;
    private String[] voci;


    /**
     * Costruttore della classe menu
     * @param titolo titolo del menu
     * @param voci le voci del menu
     */
    public Menu(String titolo, String[] voci) {
        this.titolo = titolo;
        this.voci = voci;
    }

    /**
     * Metodo per la scelta di un'opzione del menu
     * @return il numero della scelta
     */
    public int scegli() {
        stampaMenu();
        return Utilita.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
    }

    /**
     * Metodo per la stampa a video di un menu
     */
    public void stampaMenu() {
        System.out.println(CORNICE);
        System.out.println(titolo);
        System.out.println(CORNICE);
        for (int i = 0; i < voci.length; i++) {
            System.out.println((i + 1) + "\t" + voci[i]);
        }
        System.out.println();
        System.out.println(VOCE_USCITA);
        System.out.println();
    }

    /**
     * Metodo per la gestione del menu del configuratore
     * @param conf la configurazione su cui opera il configuratore
     */
    public void MenuConfiguratore(Configurazione conf){
        int risposta=1;
        this.setVoci(VOCI_Configuratore);
        do {
            if(conf.getSis().getListaGerarchie().size()==0){
                risposta=1;
                System.out.println("Il sistema non presenta alcuna gerarchia, inserisci una gerarchia, in futuro potrai aggiungerne altre");
            }
            else
                risposta = this.scegli();
            switch (risposta){
                case 1 :
                    String nomeRadice;
                    boolean nomeRadiceNuovo=false;
                    do{
                        nomeRadice=Utilita.leggiStringaNonVuota("Inserisci il nome della radice della gerarchia:");
                        if(conf.getSis().checkNomeNuovoRadice(nomeRadice)){
                            nomeRadiceNuovo=true;
                        }
                        else
                            System.out.println("Questo nome è già presente");
                    }while(!nomeRadiceNuovo);
                    Gerarchia creata=Gerarchia.creaRamo(nomeRadice);
                    //conf.getSis().addGerarchia(creata);
                    conf.getSis().getListaGerarchie().add(creata);
                    break;
                case 2 :
                    System.out.println(conf.getSis().toStringSistema());
                    if(conf.getSis().getListaGerarchie().size()==0){
                        break;
                    }
                    int sceltaCat=0;
                    do{
                        sceltaCat=Utilita.leggiIntero("Inserisci 1 se vuoi visualizzare in dettaglio una categoria, 0 altrimenti:", ZERO, UNO);
                        if(sceltaCat==1){
                            Categoria toSee=Utilita.leggiCategoria(conf.getSis());
                            if(toSee!=null){
                                System.out.println(toSee.toStringCategoria());
                            }
                        }


                    }while(sceltaCat==1);
                    break;
                case 3:
                    conf.getParametri().modificaParametri();
                    break;
                default:
                    break;

            }
        }while(risposta!=0);

    }

    /**
     * Metodo per la gestione del menu del fruitore
     * @param conf la configurazione su cui opera il fruitore
     */
    public void MenuFruitore(Configurazione conf){
        int rispostaFruitore;
        this.setVoci(VOCI_Fruitore);
        do {
            rispostaFruitore = this.scegli();
            switch (rispostaFruitore){
                case 1 :
                    System.out.println(conf.getSis().visualizzaRadici());
                    if(conf.getParametri()==null){
                        System.out.println("I parametri di configurazione non sono ancora stati settati");
                    }
                    else{
                        System.out.println(conf.getParametri().toStringParametri());
                    }
                    break;
                default:
                    break;

            }
        }while(rispostaFruitore !=0);
    }


    /**
     * Metodo get per le voci del menu
     * @return le voci del menu
     */
    public String[] getVoci() {
        return voci;
    }

    /**
     * Metodo set per le voci del menu
     * @param voci le voci da settare
     */
    public void setVoci(String[] voci) {
        this.voci = voci;
    }

}
