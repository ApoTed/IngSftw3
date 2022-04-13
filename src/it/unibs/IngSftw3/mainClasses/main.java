package it.unibs.IngSftw3.mainClasses;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import it.unibs.IngSftw3.xmlUtilities.*;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.util.ArrayList;

public class main {

    public static void main(String[] args) throws XMLStreamException, ParserConfigurationException {
        // TODO Auto-generated method stubsss
        ArrayList <Utente> l=new ArrayList<Utente>();
        DatiUtenti x=new DatiUtenti(l);
        File fileUtenti = new File("listaUtenti.xml");
        if(fileUtenti.exists() && !fileUtenti.isDirectory()) {
            x=XmlReader.leggiUtenti("listaUtenti.xml");
        }
        Utente acceduto=x.menuAccesso();
        ArrayList <Gerarchia> gs=new ArrayList<>();
        Sistema sistema=new Sistema(gs);
        ParametriScambi param=null;
        File fileParametri=new File("parametriSalvati.xml");
        boolean parametriFatti=true;
        if(fileParametri.exists() && !fileParametri.isDirectory()){
            param=XmlReader.leggiParametri("parametriSalvati.xml");
        }
        else{
            parametriFatti=false;
        }
        File fileSistema = new File("sistema.xml");
        if(fileSistema.exists() && !fileSistema.isDirectory()) {
            sistema= XmlReader.readSis("sistema.xml");
        }
        if(!parametriFatti){
            param=ParametriScambi.inserimentoParametri();
        }
        Configurazione conf=new Configurazione(sistema,param);

        if(acceduto instanceof Configuratore){
            String titolo="Benvenuto nel sistema di gestione baratti";
            String[] voci=new String[]{};
            Menu m=new Menu(titolo,voci);
            m.MenuConfiguratore(conf);
        }
        if(acceduto instanceof  Fruitore){
            String titolo="Benvenuto nel sistema di gestione baratti";
            String[] voci=new String[]{};
            Menu m=new Menu(titolo,voci);
            m.MenuFruitore(conf);
        }

       /* ArrayList<String> luoghi=new ArrayList<String>();
        luoghi.add("a");
        luoghi.add("s");
        luoghi.add("d");
        ArrayList<Giorno> giorni=new ArrayList<>();
        giorni.add(Giorno.MARTEDI);
        giorni.add(Giorno.GIOVEDI);
        Orario[] ore=new Orario[]{new Orario(10,00),new Orario(15,00)};
        ParametriScambi p=new ParametriScambi("Brescia",luoghi,giorni,ore,15);
        System.out.println(p.toStringParametri());
        */
        System.out.println("\nFINE PROGRAMMA");
        XmlWriter.salvaParametri(conf.getParametri(),"parametriSalvati.xml");
        if(conf.getSis().getListaGerarchie().size()!=0){
            XmlWriter.salvaSistema(conf.getSis(), "sistema.xml");
        }

        XmlWriter.utentiWrite(x, "listaUtenti.xml");

    }

}
