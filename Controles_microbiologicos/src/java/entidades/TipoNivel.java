/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "tipo_nivel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoNivel.findAll", query = "SELECT t FROM TipoNivel t"),
    @NamedQuery(name = "TipoNivel.findByIdTipoNivel", query = "SELECT t FROM TipoNivel t WHERE t.idTipoNivel = :idTipoNivel"),
    @NamedQuery(name = "TipoNivel.findByTipo", query = "SELECT t FROM TipoNivel t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "TipoNivel.findByDato", query = "SELECT t FROM TipoNivel t WHERE t.dato = :dato"),
    @NamedQuery(name = "TipoNivel.findByAlerta", query = "SELECT t FROM TipoNivel t WHERE t.alerta = :alerta"),
    @NamedQuery(name = "TipoNivel.findByAccion", query = "SELECT t FROM TipoNivel t WHERE t.accion = :accion"),
    @NamedQuery(name = "TipoNivel.findByInclumplimiento", query = "SELECT t FROM TipoNivel t WHERE t.inclumplimiento = :inclumplimiento"),
    @NamedQuery(name = "TipoNivel.findByFchRegistro", query = "SELECT t FROM TipoNivel t WHERE t.fchRegistro = :fchRegistro")})
public class TipoNivel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_nivel")
    private Integer idTipoNivel;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "dato")
    private int dato;
    @Basic(optional = false)
    @Column(name = "alerta")
    private String alerta;
    @Basic(optional = false)
    @Column(name = "accion")
    private String accion;
    @Basic(optional = false)
    @Column(name = "inclumplimiento")
    private String inclumplimiento;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    private String fchRegistro;

    public TipoNivel() {
    }

    public TipoNivel(Integer idTipoNivel) {
        this.idTipoNivel = idTipoNivel;
    }

    public TipoNivel(Integer idTipoNivel, String tipo, int dato, String alerta, String accion, String inclumplimiento, String fchRegistro) {
        this.idTipoNivel = idTipoNivel;
        this.tipo = tipo;
        this.dato = dato;
        this.alerta = alerta;
        this.accion = accion;
        this.inclumplimiento = inclumplimiento;
        this.fchRegistro = fchRegistro;
    }

    public Integer getIdTipoNivel() {
        return idTipoNivel;
    }

    public void setIdTipoNivel(Integer idTipoNivel) {
        this.idTipoNivel = idTipoNivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getInclumplimiento() {
        return inclumplimiento;
    }

    public void setInclumplimiento(String inclumplimiento) {
        this.inclumplimiento = inclumplimiento;
    }

    public String getFchRegistro() {
        return fchRegistro;
    }

    public void setFchRegistro(String fchRegistro) {
        this.fchRegistro = fchRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoNivel != null ? idTipoNivel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoNivel)) {
            return false;
        }
        TipoNivel other = (TipoNivel) object;
        if ((this.idTipoNivel == null && other.idTipoNivel != null) || (this.idTipoNivel != null && !this.idTipoNivel.equals(other.idTipoNivel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TipoNivel[ idTipoNivel=" + idTipoNivel + " ]";
    }
    
}
