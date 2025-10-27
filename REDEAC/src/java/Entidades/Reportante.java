/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "reportante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reportante.findAll", query = "SELECT r FROM Reportante r")
    , @NamedQuery(name = "Reportante.findByIdReportante", query = "SELECT r FROM Reportante r WHERE r.idReportante = :idReportante")
    , @NamedQuery(name = "Reportante.findByNombres", query = "SELECT r FROM Reportante r WHERE r.nombres = :nombres")
    , @NamedQuery(name = "Reportante.findByApellidos", query = "SELECT r FROM Reportante r WHERE r.apellidos = :apellidos")
    , @NamedQuery(name = "Reportante.findByCorreo", query = "SELECT r FROM Reportante r WHERE r.correo = :correo")
    , @NamedQuery(name = "Reportante.findByFechaRegistro", query = "SELECT r FROM Reportante r WHERE r.fechaRegistro = :fechaRegistro")})
public class Reportante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reportante")
    private Integer idReportante;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "correo")
    private String correo;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idReportante")
    private Collection<Caso> casoCollection;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne
    private Area idArea;

    public Reportante() {
    }

    public Reportante(Integer idReportante) {
        this.idReportante = idReportante;
    }

    public Reportante(Integer idReportante, String nombres, String apellidos) {
        this.idReportante = idReportante;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Integer getIdReportante() {
        return idReportante;
    }

    public void setIdReportante(Integer idReportante) {
        this.idReportante = idReportante;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @XmlTransient
    public Collection<Caso> getCasoCollection() {
        return casoCollection;
    }

    public void setCasoCollection(Collection<Caso> casoCollection) {
        this.casoCollection = casoCollection;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReportante != null ? idReportante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reportante)) {
            return false;
        }
        Reportante other = (Reportante) object;
        if ((this.idReportante == null && other.idReportante != null) || (this.idReportante != null && !this.idReportante.equals(other.idReportante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Reportante[ idReportante=" + idReportante + " ]";
    }
    
}
