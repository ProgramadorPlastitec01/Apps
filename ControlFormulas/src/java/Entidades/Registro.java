/*
 * To change this template, choose Tools | Templates
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

/**
 *
 * @author asistemas2
 */
@Entity
@Table(name = "registro")
@NamedQueries({
    @NamedQuery(name = "Registro.findAll", query = "SELECT r FROM Registro r"),
    @NamedQuery(name = "Registro.findByIdRegistro", query = "SELECT r FROM Registro r WHERE r.idRegistro = :idRegistro"),
    @NamedQuery(name = "Registro.findByFechaGeneracion", query = "SELECT r FROM Registro r WHERE r.fechaGeneracion = :fechaGeneracion"),
    @NamedQuery(name = "Registro.findByLoteGeneracion", query = "SELECT r FROM Registro r WHERE r.loteGeneracion = :loteGeneracion"),
    @NamedQuery(name = "Registro.findByCodigoCompuesto", query = "SELECT r FROM Registro r WHERE r.codigoCompuesto = :codigoCompuesto"),
    @NamedQuery(name = "Registro.findByResponsableProduccion", query = "SELECT r FROM Registro r WHERE r.responsableProduccion = :responsableProduccion"),
    @NamedQuery(name = "Registro.findByResponsableCalidad", query = "SELECT r FROM Registro r WHERE r.responsableCalidad = :responsableCalidad"),
    @NamedQuery(name = "Registro.findByUsuarioRegistro", query = "SELECT r FROM Registro r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Registro.findByFechaRegistro", query = "SELECT r FROM Registro r WHERE r.fechaRegistro = :fechaRegistro")})
public class Registro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro")
    private Integer idRegistro;
    @Column(name = "fecha_generacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGeneracion;
    @Column(name = "lote_generacion")
    private String loteGeneracion;
    @Column(name = "codigo_compuesto")
    private Integer codigoCompuesto;
    @Column(name = "responsable_produccion")
    private String responsableProduccion;
    @Column(name = "responsable_calidad")
    private String responsableCalidad;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_formula", referencedColumnName = "id_formula")
    @ManyToOne
    private Formula formula;
    @OneToMany(mappedBy = "registro")
    private Collection<RegistroDetalle> registroDetalleCollection;

    public Registro() {
    }

    public Registro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getLoteGeneracion() {
        return loteGeneracion;
    }

    public void setLoteGeneracion(String loteGeneracion) {
        this.loteGeneracion = loteGeneracion;
    }

    public Integer getCodigoCompuesto() {
        return codigoCompuesto;
    }

    public void setCodigoCompuesto(Integer codigoCompuesto) {
        this.codigoCompuesto = codigoCompuesto;
    }

    public String getResponsableProduccion() {
        return responsableProduccion;
    }

    public void setResponsableProduccion(String responsableProduccion) {
        this.responsableProduccion = responsableProduccion;
    }

    public String getResponsableCalidad() {
        return responsableCalidad;
    }

    public void setResponsableCalidad(String responsableCalidad) {
        this.responsableCalidad = responsableCalidad;
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

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public Collection<RegistroDetalle> getRegistroDetalleCollection() {
        return registroDetalleCollection;
    }

    public void setRegistroDetalleCollection(Collection<RegistroDetalle> registroDetalleCollection) {
        this.registroDetalleCollection = registroDetalleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistro != null ? idRegistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registro)) {
            return false;
        }
        Registro other = (Registro) object;
        if ((this.idRegistro == null && other.idRegistro != null) || (this.idRegistro != null && !this.idRegistro.equals(other.idRegistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Registro[idRegistro=" + idRegistro + "]";
    }

}
