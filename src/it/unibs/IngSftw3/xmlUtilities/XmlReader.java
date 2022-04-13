package it.unibs.IngSftw3.xmlUtilities;


import it.unibs.IngSftw3.mainClasses.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe per la gestione della lettura di un file XML
 * @author Jacopo Tedeschi,Enrico Zambelli
 */
public class XmlReader {
    /**
     * metodo per leggere i dati del sistema salvato su file xml
     * @param filename nome del file di cui si vogliono leggere i dati
     * @return sis ovvero il sistema che era salvato su file xml
     * @throws XMLStreamException
     */
    public static Sistema readSis(String filename) throws XMLStreamException {
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;
        //inizialiazzo il reader
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }
        ArrayList<Gerarchia> listaG= new ArrayList<Gerarchia>();

        while(xmlr.hasNext()){
            if(xmlr.isStartElement() && xmlr.getLocalName().equals("insiemeGerarchie")){
                boolean fineSis=false;
                while(!fineSis){
                    if(xmlr.isStartElement() && xmlr.getLocalName().equals("gerarchia")){
                        //xmlr.next();
                        boolean fineGerarchia=false;
                        HashMap <Categoria,Categoria> linkPadri=new HashMap<>();
                        HashMap<Categoria,String> linkTemp=new HashMap<>();
                        ArrayList <Categoria> allCat= new ArrayList<>();
                        Categoria radice=null;
                        while(!fineGerarchia){

                            String nomeCat="";
                            String descrCat="";
                            ArrayList <CampoNativo> campiCat=new ArrayList<>();
                            String nomePadre = null;
                            //inizio a leggere i dati di una categoria
                            if(xmlr.isStartElement() && xmlr.getLocalName().equals("categoria")){
                                boolean fineCategoria=false;
                                boolean rad=false;
                                while(!fineCategoria){
                                    //xmlr.next();
                                    if(xmlr.isStartElement()){
                                        boolean fineCheckCharacterCat=false;
                                        switch(xmlr.getLocalName()){
                                            case "nomeCategoria":
                                                //salvo il nome della categoria
                                                do{
                                                    if(xmlr.isCharacters()){
                                                        nomeCat=xmlr.getText();
                                                        fineCheckCharacterCat=true;
                                                    }
                                                    xmlr.next();
                                                }while(!fineCheckCharacterCat);
                                                break;
                                            case "descrizione":
                                                //salvo la descrizione della categoria
                                                do{
                                                    if(xmlr.isCharacters()){
                                                        descrCat=xmlr.getText();
                                                        fineCheckCharacterCat=true;
                                                    }
                                                    xmlr.next();
                                                }while(!fineCheckCharacterCat);

                                                break;
                                            case "campiNativi":
                                                boolean fineCampi=false;
                                                while(!fineCampi){

                                                    if(xmlr.isStartElement() && xmlr.getLocalName().equals("campoNativo")){
                                                        //xmlr.next();
                                                        String nomeCampo="";
                                                        boolean obbligoCampo = false;
                                                        boolean fineCampo=false;
                                                        while (!fineCampo){

                                                            if(xmlr.isStartElement()){
                                                                boolean fineCicloControlloCampo=false;
                                                                switch (xmlr.getLocalName()){
                                                                    case  "nomeCampo":
                                                                        //salvo il nome del campo
                                                                        do{
                                                                            if(xmlr.isCharacters()){
                                                                                nomeCampo=xmlr.getText();
                                                                                fineCicloControlloCampo=true;
                                                                            }
                                                                            xmlr.next();
                                                                        }while(!fineCicloControlloCampo);
                                                                        break;
                                                                    case "obbligoCampo":
                                                                        //vedo se è true o false l'obbligo del campo
                                                                        do{
                                                                            if(xmlr.isCharacters()){
                                                                                if(xmlr.getText().equals("true")){
                                                                                    obbligoCampo=true;
                                                                                }
                                                                                else{
                                                                                    obbligoCampo=false;
                                                                                }
                                                                                fineCicloControlloCampo=true;
                                                                            }
                                                                            xmlr.next();
                                                                        }while(!fineCicloControlloCampo);

                                                                        break;
                                                                }
                                                            }
                                                            if(xmlr.isEndElement() && xmlr.getLocalName().equals("campoNativo"))
                                                                fineCampo=true;
                                                            if(!fineCampo)
                                                                xmlr.next();
                                                        }
                                                        CampoNativo c=new CampoNativo(nomeCampo, obbligoCampo);
                                                        campiCat.add(c);
                                                    }
                                                    if(xmlr.isEndElement() && xmlr.getLocalName().equals("campiNativi"))
                                                        fineCampi=true;
                                                    if(!fineCampi)
                                                        xmlr.next();
                                                }
                                                break;
                                            case "categoriaPadre":

                                                //salvo il nome del padre della cat
                                                do{
                                                    if(xmlr.isCharacters()){
                                                        nomePadre=xmlr.getText();
                                                        if(nomePadre.equals("inesistente")){
                                                            rad=true;
                                                        }
                                                        fineCheckCharacterCat=true;
                                                    }
                                                    xmlr.next();
                                                }while(!fineCheckCharacterCat);

                                                break;

                                        }

                                    }
                                    if(xmlr.isEndElement() && xmlr.getLocalName().equals("categoria"))
                                        fineCategoria=true;
                                    if(!fineCategoria)
                                        xmlr.next();
                                }
                                if(!rad){
                                    Categoria cat=new Categoria(nomeCat,descrCat, campiCat);
                                    allCat.add(cat);
                                    linkTemp.put(cat,nomePadre);
                                }
                                else{
                                    radice=new Categoria(nomeCat,descrCat, campiCat);
                                    allCat.add(radice);
                                    linkTemp.put(radice,"inesistente");
                                }

                            }
                            else if(xmlr.isEndElement() && xmlr.getLocalName().equals("gerarchia")){
                                fineGerarchia=true;
                            }

                            if(!fineGerarchia)
                                xmlr.next();
                        }
                        //metodo per trovare il padre e fare i giusti put a linkPadri
                        for(Categoria x: linkTemp.keySet()){
                            if(linkTemp.get(x).equals("inesistente")){

                                CampoNativo uno=new CampoNativo("stato di conservazione",true);
                                CampoNativo due=new CampoNativo("descrizione libera",false);
                                ArrayList <CampoNativo> campiIniziali=new ArrayList<>();
                                campiIniziali.add(uno);
                                campiIniziali.add(due);
                                Categoria fantoccio=new Categoria("inesistente","", campiIniziali);
                                linkPadri.put(x, fantoccio);
                            }
                            else{
                                for(Categoria y:allCat){

                                    if(y.getNome().equals(linkTemp.get(x))){
                                        linkPadri.put(x,y);
                                    }
                                }
                            }

                        }
                        Gerarchia ger=new Gerarchia(linkPadri, radice);
                        listaG.add(ger);
                    }
                    if(xmlr.isEndElement() && xmlr.getLocalName().equals("insiemeGerarchie")){
                        fineSis=true;
                    }
                    else
                        xmlr.next();
                }
            }
            if(xmlr.hasNext())
                xmlr.next();
        }
        Sistema sis=new Sistema(listaG);
        return sis;
    }

    /**
     * metodo per la lettura dei parametri salvati in xml
     * @param filename nome file xml da cui leggere i dati
     * @return
     * @throws XMLStreamException
     */
    public static DatiUtenti leggiUtenti(String filename) throws XMLStreamException {
        ArrayList <Utente> listaUtenti = new ArrayList<>();
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;
        //inizialiazzo il reader
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }
        while(xmlr.hasNext()){
            if(xmlr.isStartElement() && xmlr.getLocalName().equals("datiUtenti")){
                boolean fineUtenti=false;
                while(!fineUtenti){
                    if(xmlr.isStartElement() && xmlr.getLocalName().equals("utente")){
                        boolean fineUtente=false;
                        String username=null;
                        String password=null;
                        String tipo=null;
                        while(!fineUtente){
                            if(xmlr.isStartElement() && xmlr.getLocalName().equals("username")){
                                boolean fineUser=false;
                                while(!fineUser){
                                    if(xmlr.isCharacters()){
                                        username=xmlr.getText();
                                        fineUser=true;
                                    }
                                    xmlr.next();
                                }
                            }
                            if(xmlr.isStartElement() && xmlr.getLocalName().equals("password")){
                                boolean finePass=false;
                                while(!finePass){
                                    if(xmlr.isCharacters()){
                                        password=xmlr.getText();
                                        finePass=true;
                                    }
                                    xmlr.next();
                                }
                            }
                            if(xmlr.isStartElement() && xmlr.getLocalName().equals("tipoUtente")){
                                boolean fineTipo=false;
                                while(!fineTipo){
                                    if(xmlr.isCharacters()){
                                        tipo=xmlr.getText();
                                        fineTipo=true;
                                    }
                                    if(!fineTipo){
                                        xmlr.next();
                                    }
                                }
                            }
                            if(xmlr.isEndElement() && xmlr.getLocalName().equals("utente")){
                                fineUtente=true;
                            }
                            if(!fineUtente){
                                xmlr.next();
                            }

                        }
                        if(tipo.equals("configuratore")){
                            Configuratore c=new Configuratore(username,password);
                            listaUtenti.add(c);
                        }
                        else{
                            Fruitore f=new Fruitore(username,password);
                            listaUtenti.add(f);
                        }

                    }
                    if(xmlr.isEndElement() && xmlr.getLocalName().equals("datiUtenti"))
                        fineUtenti=true;

                    if(!fineUtenti){
                        xmlr.next();
                    }
                }

            }
            xmlr.next();
        }
        DatiUtenti dati=new DatiUtenti(listaUtenti);
        return dati;
    }

    public static ParametriScambi leggiParametri(String filename) throws XMLStreamException {
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;
        //inizialiazzo il reader
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }String piazza = null;
        ArrayList <String> luoghi=new ArrayList<>();
        ArrayList <Giorno> giorni=new ArrayList<>();

        ArrayList <Intervallo> valli=new ArrayList<>();
        int scadenza = 0;
        while(xmlr.hasNext()){
            boolean fineParametri=false;

            if(xmlr.isStartElement() && xmlr.getLocalName().equals("ParametriScambi")){
                while(!fineParametri){
                    if(xmlr.isStartElement()){
                        switch(xmlr.getLocalName()){
                            case "piazza":
                                boolean finePiazza=false;
                                while(!finePiazza){
                                    if(xmlr.isCharacters()){
                                        piazza=xmlr.getText();
                                    }
                                    if(xmlr.isEndElement() && xmlr.getLocalName().equals("piazza"))
                                        finePiazza=true;
                                    if(!finePiazza)
                                        xmlr.next();
                                }
                                break;
                            case "luoghi":
                                boolean fineLuoghi=false;
                                while(!fineLuoghi){
                                    if(xmlr.isStartElement() && xmlr.getLocalName().equals("luogo")){
                                        boolean fineLuogo=false;
                                        String l=null;
                                        while(!fineLuogo){
                                            if(xmlr.isCharacters()){
                                                l=xmlr.getText();
                                            }
                                            if(xmlr.isEndElement() && xmlr.getLocalName().equals("luogo"))
                                                fineLuogo=true;
                                            if(!fineLuogo)
                                                xmlr.next();
                                        }
                                        luoghi.add(l);
                                    }
                                    if(xmlr.isEndElement() && xmlr.getLocalName().equals("luoghi"))
                                        fineLuoghi=true;
                                    if(!fineLuoghi)
                                        xmlr.next();
                                }
                                break;
                            case "giorni":
                                boolean fineGiorni=false;
                                while(!fineGiorni){
                                    if(xmlr.isStartElement() && xmlr.getLocalName().equals("giorno")){
                                        boolean fineGiorno=false;
                                        Giorno g=null;
                                        while(!fineGiorno){
                                            if(xmlr.isCharacters()){
                                                g=Giorno.getGiornoFromString(xmlr.getText());
                                            }
                                            if(xmlr.isEndElement() && xmlr.getLocalName().equals("giorno"))
                                                fineGiorno=true;
                                            if(!fineGiorno)
                                                xmlr.next();
                                        }
                                        giorni.add(g);
                                    }
                                    if(xmlr.isEndElement() && xmlr.getLocalName().equals("giorni"))
                                        fineGiorni=true;
                                    if(!fineGiorni)
                                        xmlr.next();
                                }
                                break;
                            case "intervalli":
                                boolean fineIntervalli=false;
                                while(!fineIntervalli){
                                    if(xmlr.isStartElement() && xmlr.getLocalName().equals("intervallo")){
                                        boolean fineIntervallo=false;
                                        Orario [] ore=new Orario[2];
                                        while(!fineIntervallo){
                                            if(xmlr.isStartElement() && xmlr.getLocalName().equals("orarioIniziale")){
                                                boolean fineInizio=false;
                                                Orario or=null;
                                                while(!fineInizio){
                                                    if(xmlr.isCharacters()){
                                                        or=Orario.getOrarioFromString(xmlr.getText());
                                                    }
                                                    if(xmlr.isEndElement() && xmlr.getLocalName().equals("orarioIniziale")){
                                                        ore[0]=or;
                                                        fineInizio=true;
                                                    }

                                                    if(!fineInizio)
                                                        xmlr.next();
                                                }
                                            }
                                            if(xmlr.isStartElement() && xmlr.getLocalName().equals("orarioFinale")){
                                                boolean fineFinale=false;
                                                Orario oFin=null;
                                                while(!fineFinale){
                                                    if(xmlr.isCharacters()){
                                                        oFin=Orario.getOrarioFromString(xmlr.getText());
                                                    }
                                                    if(xmlr.isEndElement() && xmlr.getLocalName().equals("orarioFinale")){
                                                        fineFinale=true;
                                                        ore[1]=oFin;
                                                    }
                                                    if(!fineFinale)
                                                        xmlr.next();
                                                }
                                            }
                                            if(xmlr.isEndElement() && xmlr.getLocalName().equals("intervallo")){
                                                fineIntervallo=true;
                                            }
                                            if(!fineIntervallo)
                                                xmlr.next();
                                        }
                                        Intervallo i=new Intervallo(ore);
                                        valli.add(i);
                                    }
                                    if(xmlr.isEndElement() & xmlr.getLocalName().equals("intervalli"))
                                        fineIntervalli=true;
                                    if(!fineIntervalli){
                                        xmlr.next();
                                    }
                                }

                                break;
                            case "scadenza":
                                boolean fineScad=false;
                                while(!fineScad){
                                    if(xmlr.isCharacters()){
                                        scadenza=Integer.valueOf(xmlr.getText());
                                    }
                                    if(xmlr.isEndElement() && xmlr.getLocalName().equals("scadenza"))
                                        fineScad=true;
                                    if(!fineScad){
                                        xmlr.next();
                                    }
                                }
                        }
                    }
                    if(xmlr.isEndElement() && xmlr.getLocalName().equals("ParametriScambi" ))
                        fineParametri=true;
                    if(!fineParametri)
                        xmlr.next();
                }
            }
            xmlr.next();
        }
        ParametriScambi par=new ParametriScambi(piazza,luoghi, giorni,valli,scadenza);
        return par;
    }
}

