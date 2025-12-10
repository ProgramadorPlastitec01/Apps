/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

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
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "numero_trabajadores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NumeroTrabajadores.findAll", query = "SELECT n FROM NumeroTrabajadores n"),
    @NamedQuery(name = "NumeroTrabajadores.findByIdNumeroTrabajadores", query = "SELECT n FROM NumeroTrabajadores n WHERE n.idNumeroTrabajadores = :idNumeroTrabajadores"),
    @NamedQuery(name = "NumeroTrabajadores.findByAnio", query = "SELECT n FROM NumeroTrabajadores n WHERE n.anio = :anio"),
    @NamedQuery(name = "NumeroTrabajadores.findByMes", query = "SELECT n FROM NumeroTrabajadores n WHERE n.mes = :mes"),
    @NamedQuery(name = "NumeroTrabajadores.findByNumero", query = "SELECT n FROM NumeroTrabajadores n WHERE n.numero = :numero"),
    @NamedQuery(name = "NumeroTrabajadores.findByFechaRegistro", query = "SELECT n FROM NumeroTrabajadores n WHERE n.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "NumeroTrabajadores.findByUsuarioRegistro", query = "SELECT n FROM NumeroTrabajadores n WHERE n.usuarioRegistro = :usuarioRegistro")})
public class NumeroTrabajadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_numero_trabajadores")
    private Integer idNumeroTrabajadores;
    @Column(name = "anio")
    private Integer anio;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public NumeroTrabajadores() {
    }

    public NumeroTrabajadores(Integer idNumeroTrabajadores) {
        this.idNumeroTrabajadores = idNumeroTrabajadores;
    }

    public Integer getIdNumeroTrabajadores() {
        return idNumeroTrabajadores;
    }

    public void setIdNumeroTrabajadores(Integer idNumeroTrabajadores) {
        this.idNumeroTrabajadores = idNumeroTrabajadores;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
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
        hash += (idNumeroTrabajadores != null ? idNumeroTrabajadores.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NumeroTrabajadores)) {
            return false;
        }
        NumeroTrabajadores other = (NumeroTrabajadores) object;
        if ((this.idNumeroTrabajadores == null && other.idNumeroTrabajadores != null) || (this.idNumeroTrabajadores != null && !this.idNumeroTrabajadores.equals(other.idNumeroTrabajadores))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.NumeroTrabajadores[ idNumeroTrabajadores=" + idNumeroTrabajadores + " ]";
    }
    
}
