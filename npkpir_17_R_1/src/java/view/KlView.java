
package view;

import dao.KlienciDAO;
import embeddable.PanstwaSymb1;
import entity.Klienci;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@ManagedBean(name="KlView")
@ViewScoped
public class KlView implements Serializable{
    final static String FILE_NAME = "C:\\Temp\\dane.txt";
    final static String OUTPUT_FILE_NAME = "C:\\Temp\\outputdane.txt";
    final static Charset ENCODING = StandardCharsets.UTF_8;
    
    @Inject
    private KlienciDAO klDAO;
    @Inject
    private Klienci selected;
    @Inject PanstwaSymb1 ps1;
    private static ArrayList<Klienci> kl;
    private ArrayList<Klienci> kl1;
    
    private static Klienci doUsuniecia;
    
    private Integer ilesrodkow;

    
    public Klienci getSelected() {
        return selected;
    }

    public void setSelected(Klienci selected) {
        this.selected = selected;
    }
    
    public Integer getIlesrodkow() {
        return ilesrodkow;
    }

    public void setIlesrodkow(Integer ilesrodkow) {
        this.ilesrodkow = ilesrodkow;
    }

    public KlienciDAO getKlDAO() {
        return klDAO;
    }

    public void setKlDAO(KlienciDAO klDAO) {
        this.klDAO = klDAO;
    }

    public KlView() {
        kl = new ArrayList<>();
    }

    public static ArrayList<Klienci> getKl() {
        return kl;
    }
    
    public ArrayList<Klienci> getKl1() {
        return kl;
    }

    public Klienci getDoUsuniecia() {
        return doUsuniecia;
    }

    public void setDoUsuniecia(Klienci doUsuniecia) {
        this.doUsuniecia = doUsuniecia;
    }

    
    
    
    @PostConstruct
    private void init(){
        kl.addAll(klDAO.getdownloadedKlienci());
        kl1 = kl;
        
    }
  
    public void dodajKlienta(){
      try {
        String formatka = selected.getNpelna().substring(0, 1).toUpperCase();
        formatka = formatka.concat(selected.getNpelna().substring(2).toLowerCase());
        selected.setNpelna(formatka);
        formatka = selected.getNskrocona().toUpperCase();
        selected.setNskrocona(formatka);
        formatka = selected.getUlica().substring(0, 1).toUpperCase();
        formatka = formatka.concat(selected.getUlica().substring(2).toLowerCase());
        selected.setUlica(formatka);
        String kraj = selected.getKrajnazwa();
        String symbol = ps1.getWykazPanstwSX().get(kraj);
        selected.setKrajkod(symbol);
        klDAO.dodajNowyWpis(selected);
        RequestContext ctx = null;
        ctx.getCurrentInstance().update("formX:");
        } catch (Exception e) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nowy klient nie zachowany", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
         
   }
    
     public void pobierzklientazPliku() throws IOException{
          readLargerTextFile(FILE_NAME);
    
   }
    
    public void readLargerTextFile(String aFileName) throws IOException {
    Path path = Paths.get(aFileName);
    try (Scanner scanner =  new Scanner(path, "Windows-1250")){
      int i = 0;
      while (scanner.hasNextLine()){
        String tmp = String.valueOf(scanner.nextLine());
        while(i<28){
        if(tmp.contains("bnazwa")){
            i++;
            break;
        } else if(tmp.contains("nazwa")){
            i++;
            tmp = tmp.replace("\"","");
            tmp = tmp.replace("'","");
            selected.setNpelna(tmp.substring(8));
            break;
        } else if (tmp.contains("miejscowosc")) {
            i++;
            selected.setMiejscowosc(tmp.substring(14));
            break;
        } else if (tmp.contains("ulica")) {
            i++;
            selected.setUlica(tmp.substring(8));
            break;
        } else if (tmp.contains("dom")) {
            i++;
            selected.setDom(tmp.substring(6));
            break;
        }else if (tmp.contains("lokal")) {
            i++;
            selected.setLokal(tmp.substring(8));
            break;
        }else if (tmp.contains("kodpocz")) {
            i++;
            selected.setKodpocztowy(tmp.substring(10));
            break;
        }else if (tmp.contains("nip")) {    
            i++;
            tmp = tmp.replace("-","");
            selected.setNip(tmp.substring(6));
            break;
        }else if (tmp.contains("pesel")) {
            i++;
            selected.setPesel(tmp.substring(8));
            break;
        }else if (tmp.contains("krajKod")) {
            i++;
            selected.setKrajkod(tmp.substring(10));
            break;
        }else if (tmp.contains("krajNazwa")) {
            i++;
            selected.setKrajnazwa(tmp.substring(12));
            break;
        }else {
            i++;
            break;
        }
        }
        if(i==27){
           klDAO.dodajNowyWpis(selected);
           i=0;
        }
      }      
    }
  }
  
   public List<Klienci> complete(String query) {  
        List<Klienci> results = new ArrayList<>();  
        Klienci kl = new Klienci();
        try{
            String q = query.substring(0,1);
            int i = Integer.parseInt(q);
            for(Klienci p : klDAO.getdownloadedKlienci()) {  
             if(p.getNip().startsWith(query)) {
                 results.add(p);
             }
            }
        } catch (NumberFormatException e){
            for(Klienci p : klDAO.getdownloadedKlienci()) {
            if(p.getNpelna().startsWith(query)) {
                 results.add(p);
             }
            }
        }  

        return results;  
    }  
 
     public void edit(RowEditEvent ex) {
        try {
            //sformatuj();
            klDAO.edit(selected);
            //refresh();
            FacesMessage msg = new FacesMessage("Klient zedytowany DAO" + ex.getObject().toString(), selected.getNpelna().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Klient nie zedytowany DAO", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

      public void destroy(Klienci selDok) {
          doUsuniecia = new Klienci();
          doUsuniecia = selDok;
          
    }
      
         public void destroy2() {
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //       Principal principal = request.getUserPrincipal();
//        if(request.isUserInRole("Administrator")){
        try {
            kl.remove(doUsuniecia);
            kl1.remove(doUsuniecia);
            klDAO.destroy(doUsuniecia);
        } catch (Exception e) {
            System.out.println("Nie usnieto klienta View" + doUsuniecia.getNpelna() + " " + e.toString());
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Klient usunięty View", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
//    } else {
//            FacesMessage msg = new FacesMessage("Nie masz uprawnien do usuniecia dokumentu", selDokument.getIdDok().toString());
//          FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//     }
    }
   
   
}
