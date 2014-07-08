/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@Named(value = "bean1")
@SessionScoped
public class bean1 implements Serializable {

    
    public bean1() {
    }
    @EJB
    logica l;
    
    
    private String user,pass;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
    public String accede(){
     int opcion=l.compruebaAdmin(user, pass);
        if(opcion==0){
        FacesContext context=FacesContext.getCurrentInstance();
        FacesMessage message=new FacesMessage("contraseña incorrecta");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage("mensajes1", message);
        return "";
        }else if(opcion==1){
            
            HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("user", user);
            return "/restricted/admin2.xhtml?faces-redirect=true";
            
        }else{
            
            HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("user", user);
            
            return "/restricted2/user.xhtml?faces-redirect=true";
            
        }
    }
        
        
        public String desconecta(){
            
            HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            return "/admin1.xhtml?faces-redirect=true";
            
            
            
        }
       
        
        
 }
    

   
    
    
    

