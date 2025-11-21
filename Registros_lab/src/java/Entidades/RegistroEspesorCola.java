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
@Table(name = "registro_espesor_cola")
@NamedQueries({
    @NamedQuery(name = "RegistroEspesorCola.findAll", query = "SELECT r FROM RegistroEspesorCola r"),
    @NamedQuery(name = "RegistroEspesorCola.findByIdRegistroEspesorCola", query = "SELECT r FROM RegistroEspesorCola r WHERE r.idRegistroEspesorCola = :idRegistroEspesorCola"),
    @NamedQuery(name = "RegistroEspesorCola.findByFrecuencia", query = "SELECT r FROM RegistroEspesorCola r WHERE r.frecuencia = :frecuencia"),
    @NamedQuery(name = "RegistroEspesorCola.findBySubFrecuencia", query = "SELECT r FROM RegistroEspesorCola r WHERE r.subFrecuencia = :subFrecuencia"),
    @NamedQuery(name = "RegistroEspesorCola.findByToma1", query = "SELECT r FROM RegistroEspesorCola r WHERE r.toma1 = :toma1"),
    @NamedQuery(name = "RegistroEspesorCola.findByToma2", query = "SELECT r FROM RegistroEspesorCola r WHERE r.toma2 = :toma2"),
    @NamedQuery(name = "RegistroEspesorCola.findByUsuarioRegistro", query = "SELECT r FROM RegistroEspesorCola r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "RegistroEspesorCola.findByFechaRegistro", query = "SELECT r FROM RegistroEspesorCola r WHERE r.fechaRegistro = :fechaRegistro")})
public class RegistroEspesorCola implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_espesor_cola")
    private Integer idRegistroEspesorCola;
    @Column(name = "frecuencia")
    private Integer frecuencia;
    @Column(name = "sub_frecuencia")
    private Integer subFrecuencia;
    @Column(name = "toma1")
    private Double toma1;
    @Column(name = "toma2")
    private Double toma2;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro registro;

    public RegistroEspesorCola() {
    }

    public RegistroEspesorCola(Integer idRegistroEspesorCola) {
        this.idRegistroEspesorCola = idRegistroEspesorCola;
    }

    public Integer getIdRegistroEspesorCola() {
        return idRegistroEspesorCola;
    }

    public void setIdRegistroEspesorCola(Integer idRegistroEspesorCola) {
        this.idRegistroEspesorCola = idRegistroEspesorCola;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getSubFrecuencia() {
        return subFrecuencia;
    }

    public void setSubFrecuencia(Integer subFrecuencia) {
        this.subFrecuencia = subFrecuencia;
    }

    public Double getToma1() {
        return toma1;
    }

    public void setToma1(Double toma1) {
        this.toma1 = toma1;
    }

    public Double getToma2() {
        return toma2;
    }

    public void setToma2(Double toma2) {
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
        hash += (idRegistroEspesorCola != null ? idRegistroEspesorCola.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroEspesorCola)) {
            return false;
        }
        RegistroEspesorCola other = (RegistroEspesorCola) object;
        if ((this.idRegistroEspesorCola == null && other.idRegistroEspesorCola != null) || (this.idRegistroEspesorCola != null && !this.idRegistroEspesorCola.equals(other.idRegistroEspesorCola))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroEspesorCola[idRegistroEspesorCola=" + idRegistroEspesorCola + "]";
    }

}
