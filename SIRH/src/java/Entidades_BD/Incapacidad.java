/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "incapacidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Incapacidad.findAll", query = "SELECT i FROM Incapacidad i"),
    @NamedQuery(name = "Incapacidad.findByIdIncapacidad", query = "SELECT i FROM Incapacidad i WHERE i.idIncapacidad = :idIncapacidad"),
    @NamedQuery(name = "Incapacidad.findByDocumento", query = "SELECT i FROM Incapacidad i WHERE i.documento = :documento"),
    @NamedQuery(name = "Incapacidad.findByFecha", query = "SELECT i FROM Incapacidad i WHERE i.fecha = :fecha"),
    @NamedQuery(name = "Incapacidad.findByClasificacion", query = "SELECT i FROM Incapacidad i WHERE i.clasificacion = :clasificacion"),
    @NamedQuery(name = "Incapacidad.findByTipo", query = "SELECT i FROM Incapacidad i WHERE i.tipo = :tipo"),
    @NamedQuery(name = "Incapacidad.findByHoras", query = "SELECT i FROM Incapacidad i WHERE i.horas = :horas"),
    @NamedQuery(name = "Incapacidad.findBySalarioHora", query = "SELECT i FROM Incapacidad i WHERE i.salarioHora = :salarioHora"),
    @NamedQuery(name = "Incapacidad.findByEstado", query = "SELECT i FROM Incapacidad i WHERE i.estado = :estado"),
    @NamedQuery(name = "Incapacidad.findByFechaRegistro", query = "SELECT i FROM Incapacidad i WHERE i.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Incapacidad.findByUsuarioRegistro", query = "SELECT i FROM Incapacidad i WHERE i.usuarioRegistro = :usuarioRegistro")})
public class Incapacidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_incapacidad")
    private Integer idIncapacidad;
    @Column(name = "documento")
    private BigInteger documento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "clasificacion")
    private String clasificacion;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "horas")
    private Integer horas;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "salario_hora")
    private String salarioHora;
    @Column(name = "estado")
    private Integer estado;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public Incapacidad() {
    }

    public Incapacidad(Integer idIncapacidad) {
        this.idIncapacidad = idIncapacidad;
    }

    public Incapacidad(Integer idIncapacidad, Date fechaRegistro) {
        this.idIncapacidad = idIncapacidad;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdIncapacidad() {
        return idIncapacidad;
    }

    public void setIdIncapacidad(Integer idIncapacidad) {
        this.idIncapacidad = idIncapacidad;
    }

    public BigInteger getDocumento() {
        return documento;
    }

    public void setDocumento(BigInteger documento) {
        this.documento = documento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getSalarioHora() {
        return salarioHora;
    }

    public void setSalarioHora(String salarioHora) {
        this.salarioHora = salarioHora;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIncapacidad != null ? idIncapacidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incapacidad)) {
            return false;
        }
        Incapacidad other = (Incapacidad) object;
        if ((this.idIncapacidad == null && other.idIncapacidad != null) || (this.idIncapacidad != null && !this.idIncapacidad.equals(other.idIncapacidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.Incapacidad[ idIncapacidad=" + idIncapacidad + " ]";
    }
    
}
