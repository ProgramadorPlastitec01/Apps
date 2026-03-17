/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Programador.TI2
 */
@Entity
@Table(name = "tipo_instrumento")
@NamedQueries({
    @NamedQuery(name = "TipoInstrumento.findAll", query = "SELECT t FROM TipoInstrumento t")})
public class TipoInstrumento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_instrumento")
    private Integer idTipoInstrumento;
    @Column(name = "id_plantilla")
    private Integer idPlantilla;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "frecuencia_interna")
    private Integer frecuenciaInterna;
    @Column(name = "tolerancia_interna")
    private Integer toleranciaInterna;
    @Column(name = "frecuencia_externa")
    private Integer frecuenciaExterna;
    @Column(name = "tolerancia_externa")
    private Integer toleranciaExterna;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "tipo_frecuencia")
    private Integer tipoFrecuencia;
    @Column(name = "grafica")
    private Integer grafica;
    @OneToMany(mappedBy = "tipoInstrumento")
    private Collection<InstrumentoMedicion> instrumentoMedicionCollection;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne
    private Area area;

    public TipoInstrumento() {
    }

    public TipoInstrumento(Integer idTipoInstrumento) {
        this.idTipoInstrumento = idTipoInstrumento;
    }

    public Integer getIdTipoInstrumento() {
        return idTipoInstrumento;
    }

    public void setIdTipoInstrumento(Integer idTipoInstrumento) {
        this.idTipoInstrumento = idTipoInstrumento;
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getFrecuenciaInterna() {
        return frecuenciaInterna;
    }

    public void setFrecuenciaInterna(Integer frecuenciaInterna) {
        this.frecuenciaInterna = frecuenciaInterna;
    }

    public Integer getToleranciaInterna() {
        return toleranciaInterna;
    }

    public void setToleranciaInterna(Integer toleranciaInterna) {
        this.toleranciaInterna = toleranciaInterna;
    }

    public Integer getFrecuenciaExterna() {
        return frecuenciaExterna;
    }

    public void setFrecuenciaExterna(Integer frecuenciaExterna) {
        this.frecuenciaExterna = frecuenciaExterna;
    }

    public Integer getToleranciaExterna() {
        return toleranciaExterna;
    }

    public void setToleranciaExterna(Integer toleranciaExterna) {
        this.toleranciaExterna = toleranciaExterna;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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

    public Integer getTipoFrecuencia() {
        return tipoFrecuencia;
    }

    public void setTipoFrecuencia(Integer tipoFrecuencia) {
        this.tipoFrecuencia = tipoFrecuencia;
    }

    public Integer getGrafica() {
        return grafica;
    }

    public void setGrafica(Integer grafica) {
        this.grafica = grafica;
    }

    public Collection<InstrumentoMedicion> getInstrumentoMedicionCollection() {
        return instrumentoMedicionCollection;
    }

    public void setInstrumentoMedicionCollection(Collection<InstrumentoMedicion> instrumentoMedicionCollection) {
        this.instrumentoMedicionCollection = instrumentoMedicionCollection;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoInstrumento != null ? idTipoInstrumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoInstrumento)) {
            return false;
        }
        TipoInstrumento other = (TipoInstrumento) object;
        if ((this.idTipoInstrumento == null && other.idTipoInstrumento != null) || (this.idTipoInstrumento != null && !this.idTipoInstrumento.equals(other.idTipoInstrumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TipoInstrumento[ idTipoInstrumento=" + idTipoInstrumento + " ]";
    }
    
}
