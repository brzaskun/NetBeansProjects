/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.PodatnikDAO;
import dao.ZUSDAO;
import embeddable.Mce;
import embeddable.Parametr;
import embeddable.Pod;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.Zusstawki;
import entity.ZusstawkiPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.event.ValueChangeListener;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PodatnikView implements Serializable{
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private Podatnik selected;
    @Inject private Pod selectedPod;
    @Inject private Rodzajedok selectedDokKsi;
    @ManagedProperty(value = "#{rodzajedokView}")
    private RodzajedokView rodzajedokView;
    private static ArrayList<Podatnik> listapodatnikow;
    //
    private String nazwaWybranegoPodatnika;
    private List<String> pojList;
    private PanelGrid grid;
    private String[] listka;
    private List<String> listkakopia;
    private List<String> miesiacepoweryfikacji;

    @Inject private Zusstawki zusstawki;
    
    @Inject
    private Parametr parametr;
    @Inject
    private Parametr ostatniparametr;
   //tak sie sklada ze to jest glowna lista z podatnikami :)
    private static List<Podatnik> li;
    @Inject
    private DokDAO dokDAO;
    @Inject 
    private ZUSDAO zusDAO;
    @ManagedProperty(value="#{wpisView}")
    private WpisView wpisView;
    
    public  List<Podatnik> getLi() {
        return li;
    }

    public void setLi(List<Podatnik> li) {
        PodatnikView.li = li;
    }
    
    
    public PodatnikView() {
        miesiacepoweryfikacji = new ArrayList<>();
        li = new ArrayList<>();
        listapodatnikow = new ArrayList<>();
        listka = new String[3] ;
        listka[0]="zero";
        listka[1]="jeden";
        listka[2]="dwa";
       
    }
    
    @PostConstruct
    public void init(){
        Collection c;
        c = podatnikDAO.getDownloaded();
        li.addAll(c);
        wpisView = new WpisView();
        nazwaWybranegoPodatnika = wpisView.getPodatnikWpisu();
        try{
        selected=podatnikDAO.find(nazwaWybranegoPodatnika);
         pobierzogolneDokKsi();
        } catch (Exception e){}
       
    }

   
    
     public void dodaj(){
         System.out.println("Wpis do bazy zaczynam");
         sformatuj();
             try {
                 podatnikDAO.dodaj(selected);
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno nowego podatnika-klienta.", selected.getNazwapelna());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
                 
             } catch (Exception e) {
                 System.out.println(e.getStackTrace().toString());
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uzytkownik o takim NIP już istnieje.Probuje edycji", e.getStackTrace().toString());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
                 podatnikDAO.edit(selected);
             }
    }
   
    public void sformatuj(){
        String formatka=null;
        selected.setNazwapelna(selected.getNazwapelna().toUpperCase());
        selected.setWojewodztwo(selected.getWojewodztwo().substring(0,1).toUpperCase()+selected.getWojewodztwo().substring(1).toLowerCase());
        selected.setGmina(selected.getGmina().substring(0,1).toUpperCase()+selected.getGmina().substring(1).toLowerCase());
        selected.setUlica(selected.getUlica().substring(0,1).toUpperCase()+selected.getUlica().substring(1).toLowerCase());
        selected.setPowiat(selected.getPowiat().substring(0,1).toUpperCase()+selected.getPowiat().substring(1).toLowerCase());
        selected.setMiejscowosc(selected.getMiejscowosc().substring(0,1).toUpperCase()+selected.getMiejscowosc().substring(1).toLowerCase());
        selected.setPoczta(selected.getPoczta().substring(0,1).toUpperCase()+selected.getPoczta().substring(1).toLowerCase());
        selected.setImie(selected.getImie().substring(0,1).toUpperCase()+selected.getImie().substring(1).toLowerCase());
        selected.setNazwisko(selected.getNazwisko().substring(0,1).toUpperCase()+selected.getNazwisko().substring(1).toLowerCase());
        
    }
    
    public void dodajrzadwzor(ActionEvent e){
        UIComponent wywolaneprzez = getGrid();
        
        //wywolaneprzez.setRendered(false);
        System.out.println("Form: "
                + wywolaneprzez.getNamingContainer().getClientId());
        System.out.println("Rodzic: "
                +(wywolaneprzez = wywolaneprzez.getParent()));
        System.out.println("Klientid: "+wywolaneprzez.getClientId());
        RequestContext.getCurrentInstance().update(wywolaneprzez.getClientId());
        UIComponent output = new HtmlOutputText();
        UIComponent nowyinput = new HtmlInputText();
        UIComponent nowyinput1 = new HtmlInputText();
        HtmlCommandButton button = new HtmlCommandButton();
        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ExpressionFactory ef = ExpressionFactory.newInstance();
        int rozmiar = 0;
        for(int i=0;i<listka.length;i++){
            if(listka[i]!=null){
                rozmiar++;
            }
        }
        int rozmiarS = rozmiar+1;
        final String bindingO = "parametr w okresie";
        final String binding = "#{podatnikView.listka["+rozmiar+"]}";
        final String bindingS = "#{podatnikView.listka["+rozmiarS+"]}";
        ValueExpression veO = ef.createValueExpression(elContext, bindingO, String.class);
        ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
        ValueExpression ve1 = ef.createValueExpression(elContext, bindingS, String.class);
        button.setValue("dodaj");
        FacesContext context = FacesContext.getCurrentInstance();
        MethodExpression actionListener = context.getApplication().getExpressionFactory()
    .createMethodExpression(context.getELContext(), "#{podatnikView.dodajrzad}", null, new Class[] {ActionEvent.class});
        button.addActionListener(new MethodExpressionActionListener(actionListener));
        
        final String bindingB3 = "@form";
        button.getAttributes().put("update", bindingB3);
        output.setValueExpression("value", veO);
        nowyinput.setValueExpression("value", ve);
        nowyinput1.setValueExpression("value", ve1);
        grid = getGrid();
        grid.getChildren().add(output);
        grid.getChildren().add(nowyinput);
        grid.getChildren().add(nowyinput1);
        grid.getChildren().add(button);
        
        RequestContext.getCurrentInstance().update(wywolaneprzez.getClientId());
        listkakopia = Arrays.asList(listka);
        List<String> nowalista = new ArrayList();
        for (String c : listkakopia)  {
            if(c!=null){
                nowalista.add(c);
            }
        }
        System.out.println("To jest listka: "+listkakopia.toString());
        System.out.println("To jest listka: "+nowalista.toString());
    }
    
      public void dodajrzad(ActionEvent e){
        UIComponent wywolaneprzez = getGrid();
        
        //wywolaneprzez.setRendered(false);
        RequestContext.getCurrentInstance().update(wywolaneprzez.getClientId());
        HtmlOutputText output = new HtmlOutputText();
        UIComponent nowyinput = new HtmlInputText();
        UIComponent nowyinput1 = new HtmlInputText();
        HtmlCommandButton button = new HtmlCommandButton();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ExpressionFactory ef = ExpressionFactory.newInstance();
        int rozmiar = 0;
        for(int i=0;i<listka.length;i++){
            if(listka[i]!=null){
                rozmiar++;
            }
        }
        int rozmiarS = rozmiar+1;
        final String binding = "#{podatnikView.listka["+rozmiar+"]}";
        final String bindingS = "#{podatnikView.listka["+rozmiarS+"]}";
        ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
        ValueExpression ve1 = ef.createValueExpression(elContext, bindingS, String.class);
        button.setValue("dodaj");
        FacesContext context = FacesContext.getCurrentInstance();
        MethodExpression actionListener = context.getApplication().getExpressionFactory()
    .createMethodExpression(context.getELContext(), "#{podatnikView.dodajrzad}", null, new Class[] {ActionEvent.class});
        button.addActionListener(new MethodExpressionActionListener(actionListener));
        
        final String bindingB3 = "@form";
        button.getAttributes().put("update", bindingB3);
        output.setValue("parametr w okresie");
        nowyinput.setValueExpression("value", ve);
        nowyinput1.setValueExpression("value", ve1);
        grid = getGrid();
        grid.getChildren().add(output);
        grid.getChildren().add(nowyinput);
        grid.getChildren().add(nowyinput1);
        grid.getChildren().add(button);
        
        RequestContext.getCurrentInstance().update(wywolaneprzez.getClientId());
        listkakopia = Arrays.asList(listka);
        List<String> nowalista = new ArrayList();
        for (String c : listkakopia)  {
            if(c!=null){
                nowalista.add(c);
            }
        }
        
    }
     public void dodajdoch(){
         selected=podatnikDAO.find(nazwaWybranegoPodatnika);
         List<Parametr> lista = new ArrayList<>();
         try{
             lista.addAll(selected.getPodatekdochodowy());
         } catch (Exception e){}
         if(sprawdzrok(parametr, lista)==0){
         lista.add(parametr);
         selected.setPodatekdochodowy(lista);
         podatnikDAO.edit(selected);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr pod.dochodowy do podatnika.", selected.getNazwapelna());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         } else {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru pod.doch. Niedopasowane okresy.", selected.getNazwapelna());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         }
     }
     
     private int sprawdzrok(Parametr nowe, List<Parametr> stare){
         if(stare.size()==0){
            Integer new_rokOd = Integer.parseInt(nowe.getRokOd());
            parametr.setMcOd("01");
            parametr.setMcDo("12");
            parametr.setRokDo(new_rokOd.toString());
             return 0;
         } else {
         Parametr ostatniparametr = stare.get(stare.size()-1);
         Integer old_rokDo = Integer.parseInt(ostatniparametr.getRokDo());
         Integer new_rokOd = Integer.parseInt(nowe.getRokOd());
         if(old_rokDo==new_rokOd-1){
            parametr.setMcOd("01");
            parametr.setMcDo("12");
            parametr.setRokDo(new_rokOd.toString());
            return 0;
         } else {
            return 1;
         }
         }
     }
     
     public void usundoch(){
         List<Parametr> tmp = new ArrayList<>();
         tmp.addAll(selected.getPodatekdochodowy());
         tmp.remove(tmp.size()-1);
         selected.setPodatekdochodowy(tmp);
         podatnikDAO.edit(selected);
     }
     
     public void dodajvat(){
         selected=podatnikDAO.find(nazwaWybranegoPodatnika);
         List<Parametr> lista = new ArrayList<>();
         try{
             lista.addAll(selected.getVatokres());
         } catch (Exception e){}
         if(sprawdzvat(parametr, lista)==0){
         lista.add(parametr);
         selected.setVatokres(lista);
         podatnikDAO.edit(selected);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr VAT metoda do podatnika.", selected.getNazwapelna());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         } else {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru VAT metoda. Niedopasowane okresy.", selected.getNazwapelna());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         }
     }
     
      private int sprawdzvat(Parametr nowe, List<Parametr> stare) {
        if (stare.size() == 0) {
            parametr.setMcDo("");
            parametr.setRokDo("");
            return 0;
        } else {
            ostatniparametr = stare.get(stare.size() - 1);
            Integer old_rokDo = Integer.parseInt(nowe.getRokOd());
            Integer old_mcOd = Integer.parseInt(nowe.getMcOd());
            Integer sumOld = Integer.parseInt(ostatniparametr.getMcOd())+Integer.parseInt(ostatniparametr.getRokOd());
            Integer sumNew = Integer.parseInt(nowe.getMcOd())+Integer.parseInt(nowe.getRokOd());
            if(sumOld>=sumNew){
                return 1;
            }
            if (old_mcOd == 1) {
                old_mcOd = 12;
                old_rokDo--;
            } else {
                old_mcOd--;
            }
            ostatniparametr.setRokDo(old_rokDo.toString());
            ostatniparametr.setMcDo(Mce.getMapamcy().get(old_mcOd));
            return 0;
        } 
}
     
      public void usunvat(){
         List<Parametr> tmp = new ArrayList<>();
         tmp.addAll(selected.getVatokres());
         tmp.remove(tmp.size()-1);
         selected.setVatokres(tmp);
         podatnikDAO.edit(selected);
     }
      
       public void dodajzus(){
         try{
         selected=podatnikDAO.find(nazwaWybranegoPodatnika);
         List<Zusstawki> tmp = new ArrayList<>();
         try{
         tmp.addAll(selected.getZusparametr());
         } catch (Exception e){}
         sprawdzzus(tmp);
         tmp.add(zusstawki);
         selected.setZusparametr(tmp);
         podatnikDAO.edit(selected);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr ZUS do podatnika.", selected.getNazwapelna());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         } catch (Exception e) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru ZUS. Niedopasowane okresy.", selected.getNazwapelna());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         }
     }
     
      private void sprawdzzus(List tmp) throws Exception{
          Iterator it;
          it = tmp.iterator();
          while(it.hasNext()){
              Zusstawki tmpx = (Zusstawki) it.next();
              if(tmpx.getZusstawkiPK().equals(zusstawki.getZusstawkiPK())){
                  throw new Exception("Blad");
              }
          }
      }
     
      public void usunzus(){
         selected=podatnikDAO.find(nazwaWybranegoPodatnika);
         List<Zusstawki> tmp = new ArrayList<>();
         tmp.addAll(selected.getZusparametr());
         tmp.remove(tmp.size()-1);
         selected.setZusparametr(tmp);
         podatnikDAO.edit(selected);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usunięto parametr ZUS do podatnika.", selected.getNazwapelna());
         FacesContext.getCurrentInstance().addMessage(null, msg);
     }
      
      public void pobierzzus(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String rokzus = params.get("akordeon:form3:rokzus");
        String mczus = params.get("akordeon:form3:miesiaczus");
        List<Zusstawki> tmp;
        tmp = new ArrayList<>();
        Collection c;
        c = zusDAO.getDownloaded();
        tmp.addAll(c);
        ZusstawkiPK key = new ZusstawkiPK();
            key.setRok(rokzus);
            key.setMiesiac(mczus);
        Iterator it;
        it = tmp.iterator();
        while(it.hasNext()){
            Zusstawki tmpX = (Zusstawki) it.next();
            if(tmpX.getZusstawkiPK().equals(key)){
                zusstawki = tmpX;
                break;
            }
        }
    }
      
     public String przejdzdoStrony(){
           selected=podatnikDAO.find(nazwaWybranegoPodatnika);
           //sprawdazic
           RequestContext.getCurrentInstance().execute("openwindow()");
           return "/manager/managerPodUstaw.xhtml?faces-redirect=true";
     }
     
      public void dodajremanent(){
         selected=podatnikDAO.find(nazwaWybranegoPodatnika);
         List<Parametr> lista = new ArrayList<>();
         try{
             lista.addAll(selected.getRemanent());
         } catch (Exception e){}
         if(sprawdzrok(parametr, lista)==0){
         lista.add(parametr);
         selected.setRemanent(lista);
         podatnikDAO.edit(selected);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr remanent do podatnika.", selected.getNazwapelna());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         } else {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru remanent. Niedopasowane okresy.", selected.getNazwapelna());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         }
     }
        public void usunremanent(){
         List<Parametr> tmp = new ArrayList<>();
         tmp.addAll(selected.getRemanent());
         tmp.remove(tmp.size()-1);
         selected.setRemanent(tmp);
         podatnikDAO.edit(selected);
     }
        
         public void dodajkwoteautoryzujaca(){
         selected=podatnikDAO.find(nazwaWybranegoPodatnika);
         List<Parametr> lista = new ArrayList<>();
         try{
             lista.addAll(selected.getKwotaautoryzujaca());
         } catch (Exception e){}
         if(sprawdzrok(parametr, lista)==0){
         lista.add(parametr);
         selected.setKwotaautoryzujaca(lista);
         podatnikDAO.edit(selected);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr kwota autoryzująca do podatnika.", selected.getNazwapelna());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         } else {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru kwota autoryzujaca. Niedopasowane okresy.", selected.getNazwapelna());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         }
     }
        public void usunkwoteautoryzujaca(){
         List<Parametr> tmp = new ArrayList<>();
         tmp.addAll(selected.getKwotaautoryzujaca());
         tmp.remove(tmp.size()-1);
         selected.setKwotaautoryzujaca(tmp);
         podatnikDAO.edit(selected);
     }
        
      public void dodajDokKsi(){
         selected=podatnikDAO.find(nazwaWybranegoPodatnika);
         List<Rodzajedok> lista = new ArrayList<>();
         try{
             lista.addAll(selected.getDokumentyksiegowe());
         } catch (Exception e){}
         try{
         lista.add(selectedDokKsi);
         selected.setDokumentyksiegowe(lista);
         podatnikDAO.edit(selected);
         Msg.msg("i","Dodano nowy wzor dokumentu","akordeon:form6:pDokKsi");
         } catch (Exception ex) {
         Msg.msg("e","Niedodano nowego wzoru dokumentu, wystąpił błąd","akordeon:form6:pDokKsi");
         } 
        
     }

     
      public void usunDokKsi(Rodzajedok rodzajDokKsi){
        selected=podatnikDAO.find(nazwaWybranegoPodatnika);
         List<Rodzajedok> tmp = new ArrayList<>();
         tmp.addAll(selected.getDokumentyksiegowe());
         tmp.remove(rodzajDokKsi);
         selected.setDokumentyksiegowe(tmp);
         podatnikDAO.edit(selected);
         Msg.msg("i","Usunięto wzor dokumentu","akordeon:form6:pDokKsi");
     }
     
      
      public void pobierzogolneDokKsi(){
         selected=podatnikDAO.find(nazwaWybranegoPodatnika);
         List<Rodzajedok> lista = new ArrayList<>();
         try{
             lista.addAll(selected.getDokumentyksiegowe());
         } catch (Exception e){}
         List<Rodzajedok> ogolna = new ArrayList<>();
         try{
             ogolna.addAll(rodzajedokView.getLista());
         } catch (Exception e){}
         Iterator it;
         it = ogolna.iterator();
         while(it.hasNext()){
             Rodzajedok tmp = (Rodzajedok) it.next();
             if(!lista.contains(tmp)){
                 lista.add(tmp);
             }
         }
         try{
         selected.setDokumentyksiegowe(lista);
         podatnikDAO.edit(selected);
         } catch (Exception ex) {
         } 
        
     }
 
     public void peseldataurodzenia(){
         Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
         String skrot = params.get("form:pesel");
         String tmp = "19"+skrot.substring(0,2)+"-"+skrot.substring(2,4)+"-"+skrot.substring(4,6);
         selected.setDataurodzenia(tmp);
     } 
     
     public void wypelnijfax(){
         selected.setFax("000000000");
     }
     
     
     public void updateDokKsi(ValueChangeListener ex){
         RequestContext.getCurrentInstance().update("akordeon:form6:parametryDokKsi");
     }
        
      public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
    
    public ZUSDAO getZusDAO() {
        return zusDAO;
    }

    public void setZusDAO(ZUSDAO zusDAO) {
        this.zusDAO = zusDAO;
    }

    
    public Zusstawki getZusstawki() {
        return zusstawki;
    }

    public void setZusstawki(Zusstawki zusstawki) {
        this.zusstawki = zusstawki;
    }
    

    
    public ArrayList<Podatnik> getListapodatnikow() {
        return listapodatnikow;
    }

    public void setListapodatnikow(ArrayList<Podatnik> listapodatnikow) {
        PodatnikView.listapodatnikow = listapodatnikow;
    }

    public Pod getSelectedPod() {
        return selectedPod;
    }

    public void setSelectedPod(Pod selectedPod) {
        this.selectedPod = selectedPod;
    }

    
    public Parametr getParametr() {
        return parametr;
    }

    public void setParametr(Parametr parametr) {
        this.parametr = parametr;
    }
    
    
    public List<String> getListkakopia() {
        return listkakopia;
    }

    public void setListkakopia(List<String> listkakopia) {
        this.listkakopia = listkakopia;
    }

    
    
    public String[] getListka() {
        return listka;
    }

    public void setListka(String[] listka) {
        this.listka = listka;
    }

   

   
    public List<String> getPojList() {
        return pojList;
    }

    public void setPojList(List<String> pojList) {
        this.pojList = pojList;
    }
    
    
    public PanelGrid getGrid() {
        return grid;
    }

    public void setGrid(PanelGrid grid) {
        this.grid = grid;
    }

    

    public String getNazwaWybranegoPodatnika() {
        return nazwaWybranegoPodatnika;
    }

    public void setNazwaWybranegoPodatnika(String nazwaWybranegoPodatnika) {
        this.nazwaWybranegoPodatnika = nazwaWybranegoPodatnika;
    }

    public Podatnik getSelected() {
        return selected;
    }

    public void setSelected(Podatnik selected) {
        this.selected = selected;
    }

    public Rodzajedok getSelectedDokKsi() {
        return selectedDokKsi;
    }

    public void setSelectedDokKsi(Rodzajedok selectedDokKsi) {
        this.selectedDokKsi = selectedDokKsi;
    }

    public RodzajedokView getRodzajedokView() {
        return rodzajedokView;
    }

    public void setRodzajedokView(RodzajedokView rodzajedokView) {
        this.rodzajedokView = rodzajedokView;
    }
    
    
}

