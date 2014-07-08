/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "fecha")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fecha.findAll", query = "SELECT f FROM Fecha f"),
    @NamedQuery(name = "Fecha.findByFecha", query = "SELECT f FROM Fecha f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Fecha.findByDiponible", query = "SELECT f FROM Fecha f WHERE f.disponible = :disponible")})
public class Fecha implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "disponible")
    private Boolean disponible;
    @JoinColumn(name = "pid", referencedColumnName = "id")
    @ManyToOne
    private Persona pid;

    public Fecha() {
    }

    public Fecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean diponible) {
        this.disponible = diponible;
    }

    public Persona getPid() {
        return pid;
    }

    public void setPid(Persona pid) {
        this.pid = pid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fecha)) {
            return false;
        }
        Fecha other = (Fecha) object;
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "p1.Fecha[ fecha=" + fecha + " ]";
    }
    
}
