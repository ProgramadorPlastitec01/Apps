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
 * @author prog.sistemas2
 */
@Entity
@Table(name = "resumen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resumen.findAll", query = "SELECT r FROM Resumen r"),
    @NamedQuery(name = "Resumen.findByIdResumen", query = "SELECT r FROM Resumen r WHERE r.idResumen = :idResumen"),
    @NamedQuery(name = "Resumen.findByNumCertificado", query = "SELECT r FROM Resumen r WHERE r.numCertificado = :numCertificado"),
    @NamedQuery(name = "Resumen.findByOrden", query = "SELECT r FROM Resumen r WHERE r.orden = :orden"),
    @NamedQuery(name = "Resumen.findByLoteEnsamble", query = "SELECT r FROM Resumen r WHERE r.loteEnsamble = :loteEnsamble"),
    @NamedQuery(name = "Resumen.findByCantidadRegistro", query = "SELECT r FROM Resumen r WHERE r.cantidadRegistro = :cantidadRegistro"),
    @NamedQuery(name = "Resumen.findByFInicial", query = "SELECT r FROM Resumen r WHERE r.fInicial = :fInicial"),
    @NamedQuery(name = "Resumen.findByHInicial", query = "SELECT r FROM Resumen r WHERE r.hInicial = :hInicial"),
    @NamedQuery(name = "Resumen.findByFFinal", query = "SELECT r FROM Resumen r WHERE r.fFinal = :fFinal"),
    @NamedQuery(name = "Resumen.findByHFinal", query = "SELECT r FROM Resumen r WHERE r.hFinal = :hFinal"),
    @NamedQuery(name = "Resumen.findByFchDespacho", query = "SELECT r FROM Resumen r WHERE r.fchDespacho = :fchDespacho"),
    @NamedQuery(name = "Resumen.findByNumGrafadora", query = "SELECT r FROM Resumen r WHERE r.numGrafadora = :numGrafadora"),
    @NamedQuery(name = "Resumen.findByUsuRegistro", query = "SELECT r FROM Resumen r WHERE r.usuRegistro = :usuRegistro"),
    @NamedQuery(name = "Resumen.findByFchRegistro", query = "SELECT r FROM Resumen r WHERE r.fchRegistro = :fchRegistro"),
    @NamedQuery(name = "Resumen.findByOrdenDespacho", query = "SELECT r FROM Resumen r WHERE r.ordenDespacho = :ordenDespacho"),
    @NamedQuery(name = "Resumen.findByCliente", query = "SELECT r FROM Resumen r WHERE r.cliente = :cliente")})
public class Resumen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_resumen")
    private Integer idResumen;
    @Column(name = "num_certificado")
    private String numCertificado;
    @Column(name = "orden")
    private String orden;
    @Column(name = "lote_ensamble")
    private String loteEnsamble;
    @Column(name = "cantidad_registro")
    private Integer cantidadRegistro;
    @Column(name = "f_inicial")
    @Temporal(TemporalType.DATE)
    private Date fInicial;
    @Column(name = "h_inicial")
    @Temporal(TemporalType.TIME)
    private Date hInicial;
    @Column(name = "f_final")
    @Temporal(TemporalType.DATE)
    private Date fFinal;
    @Column(name = "h_final")
    @Temporal(TemporalType.TIME)
    private Date hFinal;
    @Column(name = "fch_despacho")
    private String fchDespacho;
    @Column(name = "num_grafadora")
    private String numGrafadora;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "orden_despacho")
    private String ordenDespacho;
    @Column(name = "cliente")
    private String cliente;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;

    public Resumen() {
    }

    public Resumen(Integer idResumen) {
        this.idResumen = idResumen;
    }

    public Integer getIdResumen() {
        return idResumen;
    }

    public void setIdResumen(Integer idResumen) {
        this.idResumen = idResumen;
    }

    public String getNumCertificado() {
        return numCertificado;
    }

    public void setNumCertificado(String numCertificado) {
        this.numCertificado = numCertificado;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getLoteEnsamble() {
        return loteEnsamble;
    }

    public void setLoteEnsamble(String loteEnsamble) {
        this.loteEnsamble = loteEnsamble;
    }

    public Integer getCantidadRegistro() {
        return cantidadRegistro;
    }

    public void setCantidadRegistro(Integer cantidadRegistro) {
        this.cantidadRegistro = cantidadRegistro;
    }

    public Date getFInicial() {
        return fInicial;
    }

    public void setFInicial(Date fInicial) {
        this.fInicial = fInicial;
    }

    public Date getHInicial() {
        return hInicial;
    }

    public void setHInicial(Date hInicial) {
        this.hInicial = hInicial;
    }

    public Date getFFinal() {
        return fFinal;
    }

    public void setFFinal(Date fFinal) {
        this.fFinal = fFinal;
    }

    public Date getHFinal() {
        return hFinal;
    }

    public void setHFinal(Date hFinal) {
        this.hFinal = hFinal;
    }

    public String getFchDespacho() {
        return fchDespacho;
    }

    public void setFchDespacho(String fchDespacho) {
        this.fchDespacho = fchDespacho;
    }

    public String getNumGrafadora() {
        return numGrafadora;
    }

    public void setNumGrafadora(String numGrafadora) {
        this.numGrafadora = numGrafadora;
    }

    public String getUsuRegistro() {
        return usuRegistro;
    }

    public void setUsuRegistro(String usuRegistro) {
        this.usuRegistro = usuRegistro;
    }

    public Date getFchRegistro() {
        return fchRegistro;
    }

    public void setFchRegistro(Date fchRegistro) {
        this.fchRegistro = fchRegistro;
    }

    public String getOrdenDespacho() {
        return ordenDespacho;
    }

    public void setOrdenDespacho(String ordenDespacho) {
        this.ordenDespacho = ordenDespacho;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResumen != null ? idResumen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resumen)) {
            return false;
        }
        Resumen other = (Resumen) object;
        if ((this.idResumen == null && other.idResumen != null) || (this.idResumen != null && !this.idResumen.equals(other.idResumen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Resumen[ idResumen=" + idResumen + " ]";
    }
    
}
