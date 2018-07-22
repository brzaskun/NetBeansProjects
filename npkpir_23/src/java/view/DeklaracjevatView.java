/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.ObslugaPodpisuBean;
import beansVAT.VATDeklaracja;
import comparator.Vatcomparator;
import dao.DeklaracjaVatSchemaDAO;
import dao.DeklaracjevatDAO;
import dao.PodatnikDAO;
import dao.SMTPSettingsDAO;
import dao.SchemaEwidencjaDAO;
import dao.WpisDAO;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.Deklaracjevat;
import entity.Wpis;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import mail.MailOther;
import msg.Msg;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import pdf.PdfVAT7;
import pdf.PdfVAT7new;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DeklaracjevatView implements Serializable {
    private Deklaracjevat selected;
    @Inject private DeklaracjevatDAO deklaracjevatDAO;
    private List<Deklaracjevat> wyslane;
    private List<Deklaracjevat> wyslanenormalne;
    private List<Deklaracjevat> wyslaneniepotwierdzone;
    private List<Deklaracjevat> wyslanetestowe;
    private List<Deklaracjevat> wyslanezbledem;
    private List<Deklaracjevat> oczekujace;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private DeklaracjaVatSchemaDAO deklaracjaVatSchemaDAO;
    @Inject
    private SchemaEwidencjaDAO schemaEwidencjaDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private boolean pokazZT;
    private boolean pokazZZ;
    private boolean pokazprzyciskpodpis;

    public DeklaracjevatView() {
        wyslane = new ArrayList<>();
        oczekujace = new ArrayList<>();
        wyslanenormalne = new ArrayList<>();
        wyslanetestowe = new ArrayList<>();
        wyslanezbledem = new ArrayList<>();
        wyslaneniepotwierdzone = new ArrayList<>();
    }
    
    
    @PostConstruct
    public void init() {
        pokazZT = false;
        pokazZZ = false;
        wyslane = new ArrayList<>();
        oczekujace = new ArrayList<>();
        wyslanenormalne = new ArrayList<>();
        wyslanetestowe = new ArrayList<>();
        wyslanezbledem = new ArrayList<>();
        wyslaneniepotwierdzone = new ArrayList<>();
        try {
            oczekujace = deklaracjevatDAO.findDeklaracjeDowyslaniaList(wpisView.getPodatnikWpisu());
            if (oczekujace != null && oczekujace.size() == 1) {
                Deklaracjevat p = oczekujace.get(0);
                DeklaracjaVatSchemaWierszSum narachunek25dni = VATDeklaracja.pobierzschemawiersz(p.getSchemawierszsumarycznylista(),"do zwrotu w terminie 25 dni");
                int kwota25 = narachunek25dni.getDeklaracjaVatWierszSumaryczny().getSumavat();
                if (kwota25 > 0 && p.getVatzt() == null) {
                    pokazZT = true;
                } 
                if (kwota25 > 0 && p.getVatzz() == null) {
                    pokazZZ = true;
                }
                DeklaracjaVatSchemaWierszSum narachunek60dni = VATDeklaracja.pobierzschemawiersz(p.getSchemawierszsumarycznylista(),"do zwrotu w terminie 60 dni");
                DeklaracjaVatSchemaWierszSum narachunek180dni = VATDeklaracja.pobierzschemawiersz(p.getSchemawierszsumarycznylista(),"do zwrotu w terminie 180 dni");
                int kwota60 = narachunek60dni.getDeklaracjaVatWierszSumaryczny().getSumavat();
                int kwota180 = narachunek180dni.getDeklaracjaVatWierszSumaryczny().getSumavat();
                DeklaracjaVatSchemaWierszSum sumasprzedazy = VATDeklaracja.pobierzschemawiersz(p.getSchemawierszsumarycznylista(),"Razem (suma przychodów)");
                int vatkwota = sumasprzedazy.getDeklaracjaVatWierszSumaryczny().getSumavat();
//                if ((kwota60 > 0 || kwota180 > 0) && vatkwota == 0) {
                if (kwota60 > 0 || kwota180 > 0) {
                    pokazZZ = true;
                }
                if (kwota25 > 0 && p.getVatzt() == null) {
                    pokazZT = true;
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        try {
            wyslane = deklaracjevatDAO.findDeklaracjeWyslane(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString());
            for (Deklaracjevat p : wyslane) {
                try {
                    if (p.isTestowa()) {
                        wyslanetestowe.add(p);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
            for (Deklaracjevat p : wyslane) {
                if (!wyslanetestowe.contains(p)) {
                    if (p.getStatus().startsWith("4")) {
                        wyslanezbledem.add(p);
                    } else if (p.getStatus().startsWith("3") || p.getStatus().startsWith("1")) {
                        wyslaneniepotwierdzone.add(p);
                    } else {
                        wyslanenormalne.add(p);
                    }
                }

            }
        } catch (Exception e) {
            E.e(e);
        }
        pokazprzyciskpodpisfunkcja();
        Collections.sort(wyslanenormalne, new Vatcomparator());
        RequestContext.getCurrentInstance().update("zalacznikZZ");
        RequestContext.getCurrentInstance().update("zalacznikZT");
    }
    
    public void podpiszdeklaracje(List<Deklaracjevat> oczekujace) {
        if (!oczekujace.isEmpty()) {
            try {
                Deklaracjevat biezaca = oczekujace.get(0);
                FacesContext context = FacesContext.getCurrentInstance();
                PodpisView podpisView = (PodpisView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"podpisView");
                Object[] deklaracje = podpisView.podpiszDeklaracje(biezaca.getDeklaracja());
                biezaca.setDeklaracjapodpisana((byte[]) deklaracje[0]);
                biezaca.setDeklaracja((String) deklaracje[1]);
                deklaracjevatDAO.edit(biezaca);
                pokazprzyciskpodpisfunkcja();
                Msg.msg("Udało się podpisać deklarację podpisem certyfikowanym");
            } catch (Exception e) {
                Msg.msg("e", "Nie udało się podpisać deklaracji");
            }
        }
    }

     public void edit(RowEditEvent ex) {
        try {
            //sformatuj();
            deklaracjevatDAO.edit(selected);
            FacesMessage msg = new FacesMessage("Nowy dokytkownik edytowany " + ex.getObject().toString(), selected.getPodatnik());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage("Dokytkownik nie zedytowany", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
     
   public void destroy(Deklaracjevat selDok) {
        selected = selDok;
        try {
               oczekujace.remove(selected);
               deklaracjevatDAO.destroy(selected);
                Msg.msg("i","Deklaracja usunięta");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e","Deklaracja nie usunięta");
            }
    }
   
    public void destroy2() {
         try {
               oczekujace.remove(selected);
               deklaracjevatDAO.destroy(selected);
                Msg.msg("i","Deklaracja usunięta");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e","Deklaracja nie usunięta");
            }
           
        }
    
    public void destroy2a() {
         try {
               wyslanezbledem.remove(selected);
               wyslanetestowe.remove(selected);
               deklaracjevatDAO.destroy(selected);
                Msg.msg("i","Deklaracja usunięta");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e","Deklaracja nie usunięta");
            }
           
        }

    public void mailvat7(int row) {
        try {
            MailOther.vat7(row, wpisView, 0, sMTPSettingsDAO.findSprawaByDef());
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    public void mailvat7N(int row) {
        try {
            MailOther.vat7(row, wpisView, 1, sMTPSettingsDAO.findSprawaByDef());
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    public boolean pokazprzyciskpodpisfunkcja() {
        boolean zwrot = false;
        pokazprzyciskpodpis = false;
        if (oczekujace != null && wpisView.getPodatnikObiekt().isPodpiscertyfikowany()) {
            for (Deklaracjevat d : oczekujace) {
                if (d.getDeklaracjapodpisana()==null) {
                    pokazprzyciskpodpis = ObslugaPodpisuBean.moznaPodpisac();
                }
            }
        }
        zwrot = pokazprzyciskpodpis;
        return zwrot;
    }
    
    public void drukujprzygotowanedowysylki(Deklaracjevat dkl) {
        try {
            DeklaracjaVatSchema pasujacaSchema = null;
            List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
            for (DeklaracjaVatSchema p : schemyLista) {
                if (p.getNazwaschemy().equals(dkl.getWzorschemy())) {
                    pasujacaSchema = p;
                    break;
                }
            }
            Integer rok = Integer.parseInt(pasujacaSchema.getRokOd());
            Integer mc = Integer.parseInt(pasujacaSchema.getMcOd());
            if (rok <= 2015 && mc <= 7) {
                PdfVAT7.drukujwys(podatnikDAO, dkl);
                String f = "wydrukvat7wysylka('"+dkl.getPodatnik()+"');";
                RequestContext.getCurrentInstance().execute(f);
            } else {
                PdfVAT7new.drukujNowaVAT7(podatnikDAO, dkl, pasujacaSchema, schemaEwidencjaDAO, wpisView);
                String f = "wydrukvat7wysylkaN('"+dkl.getPodatnik()+"');";
                RequestContext.getCurrentInstance().execute(f);
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    public void drukujdeklaracje(Deklaracjevat dkl, int wiersz) {
        try {
            DeklaracjaVatSchema pasujacaSchema = null;
            List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
            for (DeklaracjaVatSchema p : schemyLista) {
                if (p.getNazwaschemy().equals(dkl.getWzorschemy())) {
                    pasujacaSchema = p;
                    break;
                }
            }
            Integer rok = Integer.parseInt(pasujacaSchema.getRokOd());
            Integer mc = Integer.parseInt(pasujacaSchema.getMcOd());
            if (rok <= 2015 && mc <= 7) {
                PdfVAT7.drukuj(dkl, wiersz, podatnikDAO);
                String f = "document.getElementById('formwyslane:akordeon:dataList:"+wiersz+":mailbutton').style.display='inline';";
                RequestContext.getCurrentInstance().execute(f);
            } else {
                PdfVAT7new.drukujNowaVAT7(podatnikDAO, dkl, pasujacaSchema, schemaEwidencjaDAO, wpisView);
                String f = "document.getElementById('formwyslane:akordeon:dataList:"+wiersz+":mailbuttonN').style.display='inline';";
                RequestContext.getCurrentInstance().execute(f);
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    public void drukujdeklaracje(Deklaracjevat dkl) {
        try {
            DeklaracjaVatSchema pasujacaSchema = null;
            List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
            for (DeklaracjaVatSchema p : schemyLista) {
                if (p.getNazwaschemy().equals(dkl.getWzorschemy())) {
                    pasujacaSchema = p;
                    break;
                }
            }
            Integer rok = Integer.parseInt(pasujacaSchema.getRokOd());
            Integer mc = Integer.parseInt(pasujacaSchema.getMcOd());
            PdfVAT7new.drukujNowaVAT7(podatnikDAO, dkl, pasujacaSchema, schemaEwidencjaDAO, wpisView);
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    private void aktualizujGuest(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpisDAO.edit(wpistmp);
    }
     private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.naniesDaneDoWpis();
    }
    
     public void aktualizujGuest(String strona) throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }
    
    public void handleSave(AjaxBehaviorEvent e) {
        deklaracjevatDAO.edit(oczekujace.get(0));
        Msg.msg("Zmieniono treść deklaracji");
    }
    
    
    public List<Deklaracjevat> getWyslane() {
        return wyslane;
    }

    public void setWyslane(List<Deklaracjevat> wyslane) {
        this.wyslane = wyslane;
    }

    public List<Deklaracjevat> getOczekujace() {
        return oczekujace;
    }

    public void setOczekujace(List<Deklaracjevat> oczekujace) {
        this.oczekujace = oczekujace;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Deklaracjevat getSelected() {
        return selected;
    }

    public void setSelected(Deklaracjevat selected) {
        this.selected = selected;
    }

    public boolean isPokazZZ() {
        return pokazZZ;
    }

    public void setPokazZZ(boolean pokazZZ) {
        this.pokazZZ = pokazZZ;
    }

    public boolean isPokazprzyciskpodpis() {
        return pokazprzyciskpodpis;
    }

    public void setPokazprzyciskpodpis(boolean pokazprzyciskpodpis) {
        this.pokazprzyciskpodpis = pokazprzyciskpodpis;
    }

   

    public List<Deklaracjevat> getWyslanenormalne() {
        return wyslanenormalne;
    }

    public void setWyslanenormalne(List<Deklaracjevat> wyslanenormalne) {
        this.wyslanenormalne = wyslanenormalne;
    }

    public List<Deklaracjevat> getWyslanetestowe() {
        return wyslanetestowe;
    }

    public void setWyslanetestowe(List<Deklaracjevat> wyslanetestowe) {
        this.wyslanetestowe = wyslanetestowe;
    }

    public List<Deklaracjevat> getWyslanezbledem() {
        return wyslanezbledem;
    }

    public void setWyslanezbledem(List<Deklaracjevat> wyslanezbledem) {
        this.wyslanezbledem = wyslanezbledem;
    }

    public List<Deklaracjevat> getWyslaneniepotwierdzone() {
        return wyslaneniepotwierdzone;
    }

    public void setWyslaneniepotwierdzone(List<Deklaracjevat> wyslaneniepotwierdzone) {
        this.wyslaneniepotwierdzone = wyslaneniepotwierdzone;
    }

    public boolean isPokazZT() {
        return pokazZT;
    }

    public void setPokazZT(boolean pokazZT) {
        this.pokazZT = pokazZT;
    }
    
    
    
}
