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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "cronograma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cronograma.findAll", query = "SELECT c FROM Cronograma c")
    , @NamedQuery(name = "Cronograma.findByIdCronograma", query = "SELECT c FROM Cronograma c WHERE c.idCronograma = :idCronograma")
    , @NamedQuery(name = "Cronograma.findByTipo", query = "SELECT c FROM Cronograma c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "Cronograma.findByAplicativo", query = "SELECT c FROM Cronograma c WHERE c.aplicativo = :aplicativo")
    , @NamedQuery(name = "Cronograma.findByMes", query = "SELECT c FROM Cronograma c WHERE c.mes = :mes")
    , @NamedQuery(name = "Cronograma.findByFchRegistro", query = "SELECT c FROM Cronograma c WHERE c.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "Cronograma.findByUsuRegistro", query = "SELECT c FROM Cronograma c WHERE c.usuRegistro = :usuRegistro")})
public class Cronograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cronograma")
    private Integer idCronograma;
    @Basic(optional = false)
    @Column(name = "tipo")
    private int tipo;
    @Basic(optional = false)
    @Column(name = "aplicativo")
    private int aplicativo;
    @Basic(optional = false)
    @Lob
    @Column(name = "actividad")
    private String actividad;
    @Basic(optional = false)
    @Column(name = "mes")
    private int mes;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;

    public Cronograma() {
    }

    public Cronograma(Integer idCronograma) {
        this.idCronograma = idCronograma;
    }

    public Cronograma(Integer idCronograma, int tipo, int aplicativo, String actividad, int mes, Date fchRegistro) {
        this.idCronograma = idCronograma;
        this.tipo = tipo;
        this.aplicativo = aplicativo;
        this.actividad = actividad;
        this.mes = mes;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdCronograma() {
        return idCronograma;
    }

    public void setIdCronograma(Integer idCronograma) {
        this.idCronograma = idCronograma;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getAplicativo() {
        return aplicativo;
    }

    public void setAplicativo(int aplicativo) {
        this.aplicativo = aplicativo;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public Date getFchRegistro() {
        return fchRegistro;
    }

    public void setFchRegistro(Date fchRegistro) {
        this.fchRegistro = fchRegistro;
    }

    public String getUsuRegistro() {
        return usuRegistro;
    }

    public void setUsuRegistro(String usuRegistro) {
        this.usuRegistro = usuRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCronograma != null ? idCronograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cronograma)) {
            return false;
        }
        Cronograma other = (Cronograma) object;
        if ((this.idCronograma == null && other.idCronograma != null) || (this.idCronograma != null && !this.idCronograma.equals(other.idCronograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cronograma[ idCronograma=" + idCronograma + " ]";
    }
    
}
