/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class logica {

    @PersistenceContext(name = "horariosPU")
    EntityManager em;
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat fds=new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat hora=new SimpleDateFormat("HH:mm");
    SimpleDateFormat diaHora=new SimpleDateFormat("dd-MM-yyyy HH:mm");
    SimpleDateFormat diaHoraFinal=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    public List<Persona> listaUsuarios(){
        
        Query q=em.createNamedQuery("Persona.findAll");
        List<Persona>l=q.getResultList();
        return l;
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    public boolean insertaTodasFechas(){
        
        
        Calendar cl2=Calendar.getInstance();
        Date d=new Date();
                SimpleDateFormat sdf2=new SimpleDateFormat("yyyy M d");
                String str=sdf2.format(d);
                try{
                    cl2.setTime(sdf2.parse(str)); 
                }catch (Exception ex){
                    ex.printStackTrace();
                    return false;
                }
               
        for(int i=0;i<30;i++){
                
                
                
                
                
                cl2.add(Calendar.DAY_OF_YEAR, 1);//empieza a introducir desde el dia siguiente
                
                
                for(int j=0;j<24;j++){
                    
                    if((j>=9&&j<=13)||(j>=16&&j<22)){
                        
                        cl2.set(Calendar.HOUR_OF_DAY, j);
                        
                        Fecha f=new Fecha(cl2.getTime());
                        f.setDisponible(Boolean.TRUE);
                        try{
                            
                        em.persist(f);
                        }catch(Exception ex){
                            ex.printStackTrace();
                            System.out.println("error en el persist");
                            return false;
                        }
                        
                        
                        
                        
                        System.out.println("hemos llegado hasta"+ cl2.getTime());
                        
                           
                    }
                    
                }
                
                
        }
       
        return true;
        
    }
    
    
    
    
    public int devuelveId(){
        
        Query q=em.createNamedQuery("Persona.findById");
        q.setParameter("id", 1);
        Persona p=(Persona)q.getSingleResult();
        return p.getId();
        
        
    }
    
    public int compruebaAdmin(String user,String pass){
        
        
        Query q=em.createQuery("select p from Persona p where p.login=:user and p.password=:pass");
        q.setParameter("user", user);
        q.setParameter("pass", pass);
        
        try{
            
            Persona p=(Persona)q.getSingleResult();
            if(p.getAdmin()==true)
                
            
            return 1;
            
            return 2;
            
        }catch(Exception ex){
            
            return 0;
        }
        
        
        
        
        
    }
    
    
    public Persona recuperaPersona(String user){
        
        
        Query q=em.createNamedQuery("Persona.findByLogin");
        q.setParameter("login", user);
        Persona p=(Persona)q.getSingleResult();
        return p;
        
        
        
       
        
    }
    
    
    
    public void eliminaFechasAntiguas(String user){
        
        Persona p=recuperaPersona(user);
        ArrayList<Fecha> aka=new ArrayList<Fecha>();
        aka.addAll(p.getFechaCollection());
        Date actual=new Date();
        Date fechap=null;
        String format=sdf.format(actual);
        try{
        actual=sdf.parse(format);
                System.out.println( "actual "+actual);

        }catch(ParseException ex){
            
        }
        
        
        for(int i=0;i<aka.size();i++){
            
            
            Fecha f=aka.get(i);
            Date fechaPersona=f.getFecha();
            format=sdf.format(fechaPersona);
            try{
                fechap=sdf.parse(format);
                System.out.println(fechap+ " fechap");
            }catch(ParseException ex){
                
                
                
            }
             
            if(actual.after(fechap)==true){
                
                f.setPid(null);
                p.getFechaCollection().remove(f);
                em.remove(f);
                
                
            }
            
            
        }
        
        
    }
    
    
    
    public ArrayList<String> recuperaFechas(String user){
        
        Persona p=recuperaPersona(user);
        
        
        
        
        
        ArrayList<String> listaFechasString=new ArrayList<String>();
        ArrayList<Fecha> listaFechas=new ArrayList<Fecha>();
        
        Query q=em.createQuery("select f from Fecha f where f.pid.id="+p.getId()+" and f.fecha>CURRENT_TIMESTAMP");
        List<Fecha>l=q.getResultList();
        
        
        for(int i=0;i<l.size();i++){
            
            listaFechasString.add(diaHora.format(l.get(i).getFecha()));
            
            
        }
       
        
        return listaFechasString;
    }
    
    
    
    
    
    
    
    
    
    
    
    public ArrayList<Fecha> listaTodasFechas(){
        
        Query q=em.createNamedQuery("Fecha.findAll");
        List<Fecha> l=q.getResultList();
        ArrayList<Fecha> listafechas=new ArrayList<Fecha>();
        listafechas.addAll(l);
        return listafechas;
        
        
    }
    
    public String minDate(){
        
        ArrayList<Fecha> listaFechas=listaTodasFechas();
        
        String minDate=sdf.format(listaFechas.get(0).getFecha());
        return minDate;
        
        
        
    }
    
    public String maxDate(){
        
        ArrayList<Fecha> listaFechas=listaTodasFechas();
        
        String maxDate=sdf.format(listaFechas.get(listaFechas.size()-1).getFecha());
        
        return maxDate;
        
        
        
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }
    
    
    
    
    
    
    public ArrayList<String> seleccionaFechaPorDia(Date d){
        
        ArrayList<String> listaHoras=new ArrayList<String>();
        String sentencia="'"+fds.format(d)+"%'";
        ArrayList<Fecha> listaFechas=new ArrayList<Fecha>();
        Query q=em.createQuery("select f from Fecha f where f.fecha like "+sentencia+" and f.disponible=1 and f.fecha>CURRENT_TIMESTAMP");
        List<Fecha>l=q.getResultList();
        listaFechas.addAll(l);
        for(int i=0;i<listaFechas.size();i++){
            listaHoras.add(hora.format(listaFechas.get(i).getFecha()));
            
        }
        
        return listaHoras;
        
        
        
    }
    
    public int numeroFechasPersona(Persona p){
        
        
      Query q=em.createQuery("select count(f) from Fecha f where f.pid="+p.getId());
       List<Fecha> l=q.getResultList();
       
       return l.size();
    }
    
    
    
    
    
   public int enviaFecha(String hora,Date d,String param){
      
       
       Persona p=recuperaPersona(param);
        String s=fds.format(d);
       
        
        
                
       
       
        List<Fecha> l=(List<Fecha>)p.getFechaCollection();
        ArrayList<Fecha> listaFechas=new ArrayList<Fecha>();
        listaFechas.addAll(l);
        String dh;
        for(int i=0;i<listaFechas.size();i++){
            dh=fds.format(listaFechas.get(i).getFecha());
            
            if(s.equals(dh))
                
                return 0;
       
        }
       
       
       
       
      
       
       
       
       
       
       
       
       
       String fechaHora="'"+s+" "+hora+"%'";
       
       
      Query q=em.createQuery("select f from Fecha f where f.fecha like "+fechaHora);
       Fecha f=(Fecha)q.getSingleResult();
       f.setPid(p);
       f.setDisponible(Boolean.FALSE);
       p.getFechaCollection().add(f);
       
      
        
       
       return 2;
        
        
        
    }
   
   
   
   
   public void eliminaFecha(String[] fechas,String user){
       
       Persona p=recuperaPersona(user);
       
       Query q;
       Fecha f=null;
       String fechaFinal="";
       for(int i=0;i<fechas.length;i++){
           try{
           fechaFinal=diaHoraFinal.format(diaHora.parse(fechas[i]));
                   }catch(Exception ex){
                       ex.printStackTrace();
                   }
           System.out.println(fechaFinal);
           
           q=em.createQuery("select f from Fecha f where f.fecha like '"+fechaFinal+"%' and f.fecha>CURRENT_TIMESTAMP");
           try{
          f=(Fecha)q.getSingleResult();
           }catch(Exception ex){
               ex.printStackTrace();
               
               
               
               
           }
          
          if (f!=null){
            //Persona p=f.getPid();tambien se podria coger este
           f.setDisponible(Boolean.TRUE);
           f.setPid(null);
           p.getFechaCollection().remove(f);
          }
           
           
           
           
           
           
           
       }
       
       
       
       
       
       
       
       
       
       
   }
   
   
   
   public void eliminaUsuario(String[] user){
       
       Fecha f;
       
       
       System.out.println("entra en eliminausuario");
       
       
       for(int j=0;j<user.length;j++){
       Persona p=recuperaPersona(user[j]);
       ArrayList<Fecha> listaFechas=new ArrayList<Fecha>();
       
       listaFechas.addAll(p.getFechaCollection());
       for(int i=0;i<listaFechas.size();i++){
           f=listaFechas.get(i);
           f.setDisponible(Boolean.TRUE);
           f.setPid(null);
       }
       em.remove(p);
       }
       
       
       
       
   }
    
    
    
    
}
