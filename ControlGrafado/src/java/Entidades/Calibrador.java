/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author prog.sistemas2
 */
@Entity
@Table(name = "calibrador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calibrador.findAll", query = "SELECT c FROM Calibrador c"),
    @NamedQuery(name = "Calibrador.findByIdCalibrador", query = "SELECT c FROM Calibrador c WHERE c.idCalibrador = :idCalibrador"),
    @NamedQuery(name = "Calibrador.findByFecha", query = "SELECT c FROM Calibrador c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Calibrador.findBySerial", query = "SELECT c FROM Calibrador c WHERE c.serial = :serial"),
    @NamedQuery(name = "Calibrador.findByEncargado", query = "SELECT c FROM Calibrador c WHERE c.encargado = :encargado"),
    @NamedQuery(name = "Calibrador.findByFchVC", query = "SELECT c FROM Calibrador c WHERE c.fchVC = :fchVC"),
    @NamedQuery(name = "Calibrador.findByFchP", query = "SELECT c FROM Calibrador c WHERE c.fchP = :fchP"),
    @NamedQuery(name = "Calibrador.findByEstado", query = "SELECT c FROM Calibrador c WHERE c.estado = :estado"),
    @NamedQuery(name = "Calibrador.findByTImplemento", query = "SELECT c FROM Calibrador c WHERE c.tImplemento = :tImplemento")})
public class Calibrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_calibrador")
    private Integer idCalibrador;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "serial")
    private String serial;
    @Column(name = "encargado")
    private String encargado;
    @Column(name = "fch_v_c")
    @Temporal(TemporalType.DATE)
    private Date fchVC;
    @Column(name = "fch_p")
    @Temporal(TemporalType.DATE)
    private Date fchP;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "t_implemento")
    private String tImplemento;

    public Calibrador() {
    }

    public Calibrador(Integer idCalibrador) {
        this.idCalibrador = idCalibrador;
    }

    public Integer getIdCalibrador() {
        return idCalibrador;
    }

    public void setIdCalibrador(Integer idCalibrador) {
        this.idCalibrador = idCalibrador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Date getFchVC() {
        return fchVC;
    }

    public void setFchVC(Date fchVC) {
        this.fchVC = fchVC;
    }

    public Date getFchP() {
        return fchP;
    }

    public void setFchP(Date fchP) {
        this.fchP = fchP;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getTImplemento() {
        return tImplemento;
    }

    public void setTImplemento(String tImplemento) {
        this.tImplemento = tImplemento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCalibrador != null ? idCalibrador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calibrador)) {
            return false;
        }
        Calibrador other = (Calibrador) object;
        if ((this.idCalibrador == null && other.idCalibrador != null) || (this.idCalibrador != null && !this.idCalibrador.equals(other.idCalibrador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Calibrador[ idCalibrador=" + idCalibrador + " ]";
    }
    
}
