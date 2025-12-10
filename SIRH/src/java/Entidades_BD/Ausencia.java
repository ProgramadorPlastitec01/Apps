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
@Table(name = "ausencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ausencia.findAll", query = "SELECT a FROM Ausencia a"),
    @NamedQuery(name = "Ausencia.findByIdAusencia", query = "SELECT a FROM Ausencia a WHERE a.idAusencia = :idAusencia"),
    @NamedQuery(name = "Ausencia.findByDocumento", query = "SELECT a FROM Ausencia a WHERE a.documento = :documento"),
    @NamedQuery(name = "Ausencia.findByFecha", query = "SELECT a FROM Ausencia a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "Ausencia.findByTipo", query = "SELECT a FROM Ausencia a WHERE a.tipo = :tipo"),
    @NamedQuery(name = "Ausencia.findByHoras", query = "SELECT a FROM Ausencia a WHERE a.horas = :horas"),
    @NamedQuery(name = "Ausencia.findBySalarioHora", query = "SELECT a FROM Ausencia a WHERE a.salarioHora = :salarioHora"),
    @NamedQuery(name = "Ausencia.findByEstado", query = "SELECT a FROM Ausencia a WHERE a.estado = :estado"),
    @NamedQuery(name = "Ausencia.findByFechaRegistro", query = "SELECT a FROM Ausencia a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Ausencia.findByUsuarioRegistro", query = "SELECT a FROM Ausencia a WHERE a.usuarioRegistro = :usuarioRegistro")})
public class Ausencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ausencia")
    private Integer idAusencia;
    @Column(name = "documento")
    private BigInteger documento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "tipo")
    private String tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "horas")
    private Double horas;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "salario_hora")
    private String salarioHora;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public Ausencia() {
    }

    public Ausencia(Integer idAusencia) {
        this.idAusencia = idAusencia;
    }

    public Integer getIdAusencia() {
        return idAusencia;
    }

    public void setIdAusencia(Integer idAusencia) {
        this.idAusencia = idAusencia;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getHoras() {
        return horas;
    }

    public void setHoras(Double horas) {
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
        hash += (idAusencia != null ? idAusencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ausencia)) {
            return false;
        }
        Ausencia other = (Ausencia) object;
        if ((this.idAusencia == null && other.idAusencia != null) || (this.idAusencia != null && !this.idAusencia.equals(other.idAusencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.Ausencia[ idAusencia=" + idAusencia + " ]";
    }
    
}
