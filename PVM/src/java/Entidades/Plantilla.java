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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Programador.TI2
 */
@Entity
@Table(name = "plantilla")
@NamedQueries({
    @NamedQuery(name = "Plantilla.findAll", query = "SELECT p FROM Plantilla p")})
public class Plantilla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_plantilla")
    private Integer idPlantilla;
    @Column(name = "codigo")
    private String codigo;
    @Lob
    @Column(name = "plantilla")
    private String plantilla;
    @Column(name = "version")
    private Integer version;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "fch_vigencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchVigencia;
    @Column(name = "estado")
    private Integer estado;
    @JoinColumn(name = "id_tipo_plantilla", referencedColumnName = "id_tipo_plantilla")
    @ManyToOne
    private TipoPlantilla tipoPlantilla;

    public Plantilla() {
    }

    public Plantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getFchRegistro() {
        return fchRegistro;
    }

    public void setFchRegistro(Date fchRegistro) {
        this.fchRegistro = fchRegistro;
    }

    public Date getFchVigencia() {
        return fchVigencia;
    }

    public void setFchVigencia(Date fchVigencia) {
        this.fchVigencia = fchVigencia;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public TipoPlantilla getTipoPlantilla() {
        return tipoPlantilla;
    }

    public void setTipoPlantilla(TipoPlantilla tipoPlantilla) {
        this.tipoPlantilla = tipoPlantilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlantilla != null ? idPlantilla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plantilla)) {
            return false;
        }
        Plantilla other = (Plantilla) object;
        if ((this.idPlantilla == null && other.idPlantilla != null) || (this.idPlantilla != null && !this.idPlantilla.equals(other.idPlantilla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Plantilla[ idPlantilla=" + idPlantilla + " ]";
    }
    
}
