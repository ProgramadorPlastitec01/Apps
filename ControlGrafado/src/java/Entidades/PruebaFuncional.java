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
@Table(name = "prueba_funcional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PruebaFuncional.findAll", query = "SELECT p FROM PruebaFuncional p"),
    @NamedQuery(name = "PruebaFuncional.findByIdPruebaFuncional", query = "SELECT p FROM PruebaFuncional p WHERE p.idPruebaFuncional = :idPruebaFuncional"),
    @NamedQuery(name = "PruebaFuncional.findByIdOrden", query = "SELECT p FROM PruebaFuncional p WHERE p.idOrden = :idOrden"),
    @NamedQuery(name = "PruebaFuncional.findByLote", query = "SELECT p FROM PruebaFuncional p WHERE p.lote = :lote"),
    @NamedQuery(name = "PruebaFuncional.findByFchInicio", query = "SELECT p FROM PruebaFuncional p WHERE p.fchInicio = :fchInicio"),
    @NamedQuery(name = "PruebaFuncional.findByUsuInicio", query = "SELECT p FROM PruebaFuncional p WHERE p.usuInicio = :usuInicio"),
    @NamedQuery(name = "PruebaFuncional.findByResultado", query = "SELECT p FROM PruebaFuncional p WHERE p.resultado = :resultado"),
    @NamedQuery(name = "PruebaFuncional.findByFchResultado", query = "SELECT p FROM PruebaFuncional p WHERE p.fchResultado = :fchResultado"),
    @NamedQuery(name = "PruebaFuncional.findByUsuResultado", query = "SELECT p FROM PruebaFuncional p WHERE p.usuResultado = :usuResultado"),
    @NamedQuery(name = "PruebaFuncional.findByFchRegistro", query = "SELECT p FROM PruebaFuncional p WHERE p.fchRegistro = :fchRegistro"),
    @NamedQuery(name = "PruebaFuncional.findByUsuRegistro", query = "SELECT p FROM PruebaFuncional p WHERE p.usuRegistro = :usuRegistro")})
public class PruebaFuncional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prueba_funcional")
    private Integer idPruebaFuncional;
    @Basic(optional = false)
    @Column(name = "id_orden")
    private int idOrden;
    @Column(name = "lote")
    private String lote;
    @Basic(optional = false)
    @Column(name = "fch_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchInicio;
    @Column(name = "usu_inicio")
    private String usuInicio;
    @Basic(optional = false)
    @Column(name = "resultado")
    private int resultado;
    @Basic(optional = false)
    @Column(name = "fch_resultado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchResultado;
    @Basic(optional = false)
    @Column(name = "usu_resultado")
    private String usuResultado;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Lob
    @Column(name = "old_resultado")
    private String oldResultado;
    @Lob
    @Column(name = "old_usu_resultado")
    private String oldUsuResultado;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Basic(optional = false)
    @Column(name = "usu_registro")
    private String usuRegistro;

    public PruebaFuncional() {
    }

    public PruebaFuncional(Integer idPruebaFuncional) {
        this.idPruebaFuncional = idPruebaFuncional;
    }

    public PruebaFuncional(Integer idPruebaFuncional, int idOrden, Date fchInicio, int resultado, Date fchResultado, String usuResultado, Date fchRegistro, String usuRegistro) {
        this.idPruebaFuncional = idPruebaFuncional;
        this.idOrden = idOrden;
        this.fchInicio = fchInicio;
        this.resultado = resultado;
        this.fchResultado = fchResultado;
        this.usuResultado = usuResultado;
        this.fchRegistro = fchRegistro;
        this.usuRegistro = usuRegistro;
    }

    public Integer getIdPruebaFuncional() {
        return idPruebaFuncional;
    }

    public void setIdPruebaFuncional(Integer idPruebaFuncional) {
        this.idPruebaFuncional = idPruebaFuncional;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFchInicio() {
        return fchInicio;
    }

    public void setFchInicio(Date fchInicio) {
        this.fchInicio = fchInicio;
    }

    public String getUsuInicio() {
        return usuInicio;
    }

    public void setUsuInicio(String usuInicio) {
        this.usuInicio = usuInicio;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public Date getFchResultado() {
        return fchResultado;
    }

    public void setFchResultado(Date fchResultado) {
        this.fchResultado = fchResultado;
    }

    public String getUsuResultado() {
        return usuResultado;
    }

    public void setUsuResultado(String usuResultado) {
        this.usuResultado = usuResultado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOldResultado() {
        return oldResultado;
    }

    public void setOldResultado(String oldResultado) {
        this.oldResultado = oldResultado;
    }

    public String getOldUsuResultado() {
        return oldUsuResultado;
    }

    public void setOldUsuResultado(String oldUsuResultado) {
        this.oldUsuResultado = oldUsuResultado;
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
        hash += (idPruebaFuncional != null ? idPruebaFuncional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PruebaFuncional)) {
            return false;
        }
        PruebaFuncional other = (PruebaFuncional) object;
        if ((this.idPruebaFuncional == null && other.idPruebaFuncional != null) || (this.idPruebaFuncional != null && !this.idPruebaFuncional.equals(other.idPruebaFuncional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.PruebaFuncional[ idPruebaFuncional=" + idPruebaFuncional + " ]";
    }
    
}
