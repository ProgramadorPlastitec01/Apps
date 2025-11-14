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
@Table(name = "accidente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accidente.findAll", query = "SELECT a FROM Accidente a"),
    @NamedQuery(name = "Accidente.findByIdAccidente", query = "SELECT a FROM Accidente a WHERE a.idAccidente = :idAccidente"),
    @NamedQuery(name = "Accidente.findByDocumento", query = "SELECT a FROM Accidente a WHERE a.documento = :documento"),
    @NamedQuery(name = "Accidente.findByFecha", query = "SELECT a FROM Accidente a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "Accidente.findByTipo", query = "SELECT a FROM Accidente a WHERE a.tipo = :tipo"),
    @NamedQuery(name = "Accidente.findByDias", query = "SELECT a FROM Accidente a WHERE a.dias = :dias"),
    @NamedQuery(name = "Accidente.findByParte", query = "SELECT a FROM Accidente a WHERE a.parte = :parte"),
    @NamedQuery(name = "Accidente.findByAgente", query = "SELECT a FROM Accidente a WHERE a.agente = :agente"),
    @NamedQuery(name = "Accidente.findBySalarioHora", query = "SELECT a FROM Accidente a WHERE a.salarioHora = :salarioHora"),
    @NamedQuery(name = "Accidente.findByEstado", query = "SELECT a FROM Accidente a WHERE a.estado = :estado"),
    @NamedQuery(name = "Accidente.findByFechaRegistro", query = "SELECT a FROM Accidente a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Accidente.findByUsuarioRegistro", query = "SELECT a FROM Accidente a WHERE a.usuarioRegistro = :usuarioRegistro")})
public class Accidente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_accidente")
    private Integer idAccidente;
    @Column(name = "documento")
    private BigInteger documento;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "dias")
    private Integer dias;
    @Column(name = "parte")
    private String parte;
    @Column(name = "agente")
    private String agente;
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

    public Accidente() {
    }

    public Accidente(Integer idAccidente) {
        this.idAccidente = idAccidente;
    }

    public Integer getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(Integer idAccidente) {
        this.idAccidente = idAccidente;
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

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getParte() {
        return parte;
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
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
        hash += (idAccidente != null ? idAccidente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accidente)) {
            return false;
        }
        Accidente other = (Accidente) object;
        if ((this.idAccidente == null && other.idAccidente != null) || (this.idAccidente != null && !this.idAccidente.equals(other.idAccidente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.Accidente[ idAccidente=" + idAccidente + " ]";
    }
    
}
