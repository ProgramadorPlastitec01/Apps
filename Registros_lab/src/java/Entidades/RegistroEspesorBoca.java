/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author asistemas2
 */
@Entity
@Table(name = "registro_espesor_boca")
@NamedQueries({
    @NamedQuery(name = "RegistroEspesorBoca.findAll", query = "SELECT r FROM RegistroEspesorBoca r"),
    @NamedQuery(name = "RegistroEspesorBoca.findByIdRegistroEspesorBoca", query = "SELECT r FROM RegistroEspesorBoca r WHERE r.idRegistroEspesorBoca = :idRegistroEspesorBoca"),
    @NamedQuery(name = "RegistroEspesorBoca.findByFrecuencia", query = "SELECT r FROM RegistroEspesorBoca r WHERE r.frecuencia = :frecuencia"),
    @NamedQuery(name = "RegistroEspesorBoca.findBySubFrecuencia", query = "SELECT r FROM RegistroEspesorBoca r WHERE r.subFrecuencia = :subFrecuencia"),
    @NamedQuery(name = "RegistroEspesorBoca.findByToma1", query = "SELECT r FROM RegistroEspesorBoca r WHERE r.toma1 = :toma1"),
    @NamedQuery(name = "RegistroEspesorBoca.findByToma2", query = "SELECT r FROM RegistroEspesorBoca r WHERE r.toma2 = :toma2"),
    @NamedQuery(name = "RegistroEspesorBoca.findByUsuarioRegistro", query = "SELECT r FROM RegistroEspesorBoca r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "RegistroEspesorBoca.findByFechaRegistro", query = "SELECT r FROM RegistroEspesorBoca r WHERE r.fechaRegistro = :fechaRegistro")})
public class RegistroEspesorBoca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_espesor_boca")
    private Integer idRegistroEspesorBoca;
    @Basic(optional = false)
    @Column(name = "frecuencia")
    private int frecuencia;
    @Basic(optional = false)
    @Column(name = "sub_frecuencia")
    private int subFrecuencia;
    @Basic(optional = false)
    @Column(name = "toma1")
    private double toma1;
    @Basic(optional = false)
    @Column(name = "toma2")
    private double toma2;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne(optional = false)
    private Registro registro;

    public RegistroEspesorBoca() {
    }

    public RegistroEspesorBoca(Integer idRegistroEspesorBoca) {
        this.idRegistroEspesorBoca = idRegistroEspesorBoca;
    }

    public RegistroEspesorBoca(Integer idRegistroEspesorBoca, int frecuencia, int subFrecuencia, double toma1, double toma2, String usuarioRegistro, Date fechaRegistro) {
        this.idRegistroEspesorBoca = idRegistroEspesorBoca;
        this.frecuencia = frecuencia;
        this.subFrecuencia = subFrecuencia;
        this.toma1 = toma1;
        this.toma2 = toma2;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdRegistroEspesorBoca() {
        return idRegistroEspesorBoca;
    }

    public void setIdRegistroEspesorBoca(Integer idRegistroEspesorBoca) {
        this.idRegistroEspesorBoca = idRegistroEspesorBoca;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getSubFrecuencia() {
        return subFrecuencia;
    }

    public void setSubFrecuencia(int subFrecuencia) {
        this.subFrecuencia = subFrecuencia;
    }

    public double getToma1() {
        return toma1;
    }

    public void setToma1(double toma1) {
        this.toma1 = toma1;
    }

    public double getToma2() {
        return toma2;
    }

    public void setToma2(double toma2) {
        this.toma2 = toma2;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroEspesorBoca != null ? idRegistroEspesorBoca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroEspesorBoca)) {
            return false;
        }
        RegistroEspesorBoca other = (RegistroEspesorBoca) object;
        if ((this.idRegistroEspesorBoca == null && other.idRegistroEspesorBoca != null) || (this.idRegistroEspesorBoca != null && !this.idRegistroEspesorBoca.equals(other.idRegistroEspesorBoca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroEspesorBoca[idRegistroEspesorBoca=" + idRegistroEspesorBoca + "]";
    }

}
