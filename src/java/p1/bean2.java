
package p1;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Named(value = "bean2")
@SessionScoped
public class bean2 implements Serializable {

    @EJB
    logica l;
    
   Date d1;
    int id;
    long numeroFechas;
    String minDate,maxDate;
    ArrayList<String>listaHoras=new ArrayList<String>();
    String horaSeleccionada;
    private String[] usuarioElegido;
    boolean muestraMenu;
    private Persona p;
    
    private String param;
    
    
    private  ArrayList <String> listaFechasPersona;
    private String[] listaFechasEliminar;
    
    private List<Persona>listaPersonas;
    private boolean muestraListaPersonas;
    
    private Persona[] personasel;
    
    
    
    
    public bean2() {
        
        
        
        
        
        
        
    }

    

    public Persona[] getPersonasel() {
        return personasel;
    }

    public void setPersonasel(Persona[] personasel) {
        this.personasel = personasel;
    }

    
    
    
    
    
    

    public boolean isMuestraListaPersonas() {
        return muestraListaPersonas;
    }

    

    public List<Persona> getListaPersonas() {
        
        
        listaPersonas=l.listaUsuarios();
        return listaPersonas;
    }

    

    
    
    
    
    
    
    public String getParam() {
        
        FacesContext context =FacesContext.getCurrentInstance();
        HttpSession s=(HttpSession)context.getExternalContext().getSession(false);
        param=s.getAttribute("user").toString();
       
        //ServletRequest req=(ServletRequest)context.getExternalContext().getRequest();
        //HttpServletRequest request = (HttpServletRequest) req;
        //HttpServletResponse response = (HttpServletResponse) res;
        
        //ServletResponse res =(ServletResponse)context.getExternalContext().getResponse();
        //HttpServletResponse response=(HttpServletResponse)res;
        
        
        
        //param=request.getContextPath();
        //param=request.getRequestURI();
        
        //param=response.getContentType();
        
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
    
    
    @PostConstruct
    public void init(){
        minDate=l.minDate();
        maxDate=l.maxDate();
    }

    public Persona getP() {
        p=l.recuperaPersona(getParam());
        return p;
    }

    public void setP(Persona p) {
        this.p = p;
    }

    public String[] getListaFechasEliminar() {
        return listaFechasEliminar;
    }

    public void setListaFechasEliminar(String[] listaFechasEliminar) {
        this.listaFechasEliminar = listaFechasEliminar;
    }

    
    

    
    
    
    

    public ArrayList<String> getListaFechasPersona() {
        listaFechasPersona=l.recuperaFechas(param);
        if(listaFechasPersona.isEmpty()){
            return null;
        }
        return listaFechasPersona;
    }

    public void setListaFechasPersona(ArrayList<String> listaFechasPersona) {
        this.listaFechasPersona = listaFechasPersona;
    }
    
    
    
    
    
    
    public ArrayList<String> getListaHoras() {
        
        return listaHoras;
    }

    public void setListaHoras(ArrayList<String> listaHoras) {
        this.listaHoras = listaHoras;
    }

    public String getHoraSeleccionada() {
        return horaSeleccionada;
    }

    public void setHoraSeleccionada(String horaSeleccionada) {
        this.horaSeleccionada = horaSeleccionada;
    }

    public boolean isMuestraMenu() {
        return muestraMenu;
    }

    public void setMuestraMenu(boolean muestraMenu) {
        this.muestraMenu = muestraMenu;
    }
    
    
    
    

    public long getNumeroFechas() {
        return numeroFechas;
    }

    public void setNumeroFechas(long numeroFechas) {
        this.numeroFechas = numeroFechas;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    
    
    
    
    
    public Date getD1() {
        
        return d1;
    }

    public void setD1(Date d1) {
        
        
        this.d1 = d1;
        listaHoras=l.seleccionaFechaPorDia(d1);
        muestraMenu=true;
        
        
    }

    public int getId() {
        id=l.devuelveId();
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
    
    
    
    
    
    
    public void enviaFecha(){
        FacesContext context =FacesContext.getCurrentInstance();
        
        
        
        
        
        
        if(d1==null||horaSeleccionada==null){
            
            
        FacesMessage message=new FacesMessage("hay que elegir fecha y hora");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage("mensajes", message);
            
            
            
            
        
        
        
        
        
    } else{
            
            int i=l.enviaFecha(horaSeleccionada, d1, param);
            
        if(i==0){
        FacesMessage message=new FacesMessage("solo se puede escoger una hora al día");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage("mensajes", message);
        
    }else if(i==1){
        
        FacesMessage message=new FacesMessage("ha ocurrido un error solicitando fecha");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage("mensajes", message);
        
        
    }
        
    else{
        
        muestraMenu=false;
       horaSeleccionada=null;
        FacesMessage message=new FacesMessage("fecha reservada");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        context.addMessage("mensajes", message);
    }
        
        
        
        
        
        
    }
        
    }
    
    
    
   public void insertaTodasFechas(){
       
       
       
       
       
      boolean b=l.insertaTodasFechas();
      if(b==true){
          
          FacesContext context =FacesContext.getCurrentInstance();
          FacesMessage message=new FacesMessage("fechas insertadas");
          message.setSeverity(FacesMessage.SEVERITY_INFO);
          context.addMessage("mensaje_insertar", message);
          
          
          
          
      }else{
          
          FacesContext context =FacesContext.getCurrentInstance();
          FacesMessage message=new FacesMessage("no se pudo insertar las fechas");
          message.setSeverity(FacesMessage.SEVERITY_ERROR);
                 
          context.addMessage("mensaje_insertar", message);
      } 
      
      
      
       
       
   }
    
    public void eliminaFecha(){
        l.eliminaFechasAntiguas(param);
        if(listaFechasEliminar.length>0){
            
            
            l.eliminaFecha(listaFechasEliminar, param);
            FacesContext context=FacesContext.getCurrentInstance();
            FacesMessage message=new FacesMessage("fechas eliminadas");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage("elimina_mensaje", message);
            
            
        }
    }
    
    
    public void muestraPersonas(){
        
        if(muestraListaPersonas==false)
            muestraListaPersonas=true;
        else{
            muestraListaPersonas=false;
            
        }
        
    }
    
    
    
    public void eliminaUsuario(){
        
        if(personasel!=null&&personasel.length>0){
        
            String[] arraysel=new String[personasel.length];
            
            for(int i=0;i<personasel.length;i++){
                arraysel[i]=personasel[i].getLogin();
                
            }
           
            
        l.eliminaUsuario(arraysel);
        }
        
    }
   
    public String reserva(){
    
    return "reserva.xhtml?faces-redirect=true";
    
    //se puede poner tb como "/restricted2/reserva.xhtml?faces-redirect=true";
    
}
    
    
    
    
    
}



