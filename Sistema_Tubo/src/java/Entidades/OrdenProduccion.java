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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Programador.TI1
 */
@Entity
@Table(name = "orden_produccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenProduccion.findAll", query = "SELECT o FROM OrdenProduccion o")
    , @NamedQuery(name = "OrdenProduccion.findByIdOrden", query = "SELECT o FROM OrdenProduccion o WHERE o.idOrden = :idOrden")
    , @NamedQuery(name = "OrdenProduccion.findByNumero", query = "SELECT o FROM OrdenProduccion o WHERE o.numero = :numero")
    , @NamedQuery(name = "OrdenProduccion.findByCliente", query = "SELECT o FROM OrdenProduccion o WHERE o.cliente = :cliente")
    , @NamedQuery(name = "OrdenProduccion.findByEstado", query = "SELECT o FROM OrdenProduccion o WHERE o.estado = :estado")
    , @NamedQuery(name = "OrdenProduccion.findByUsuarioRegistro", query = "SELECT o FROM OrdenProduccion o WHERE o.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "OrdenProduccion.findByFechaRegistro", query = "SELECT o FROM OrdenProduccion o WHERE o.fechaRegistro = :fechaRegistro")})
public class OrdenProduccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_orden")
    private Integer idOrden;
    @Column(name = "numero")
    private String numero;
    @Column(name = "cliente")
    private String cliente;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_ficha_tecnica", referencedColumnName = "id_ficha_tecnica")
    @ManyToOne
    private FichaTecnica idFichaTecnica;
    @JoinColumn(name = "id_linea", referencedColumnName = "id_linea")
    @ManyToOne
    private Linea idLinea;

    public OrdenProduccion() {
    }

    public OrdenProduccion(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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

    public FichaTecnica getIdFichaTecnica() {
        return idFichaTecnica;
    }

    public void setIdFichaTecnica(FichaTecnica idFichaTecnica) {
        this.idFichaTecnica = idFichaTecnica;
    }

    public Linea getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Linea idLinea) {
        this.idLinea = idLinea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrden != null ? idOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenProduccion)) {
            return false;
        }
        OrdenProduccion other = (OrdenProduccion) object;
        if ((this.idOrden == null && other.idOrden != null) || (this.idOrden != null && !this.idOrden.equals(other.idOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.OrdenProduccion[ idOrden=" + idOrden + " ]";
    }
    
}
