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
@Table(name = "verificacion")
@NamedQueries({
    @NamedQuery(name = "Verificacion.findAll", query = "SELECT v FROM Verificacion v")})
public class Verificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_verificacion")
    private Integer idVerificacion;
    @Lob
    @Column(name = "justificacion")
    private String justificacion;
    @Column(name = "adjunto")
    private String adjunto;
    @Column(name = "estado")
    private Integer estado;
    @Basic(optional = false)
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Basic(optional = false)
    @Column(name = "usu_registro")
    private String usuRegistro;
    @JoinColumn(name = "id_instrumento", referencedColumnName = "id_instrumento_medicion")
    @ManyToOne
    private InstrumentoMedicion instrumentoMedicion;
    @JoinColumn(name = "id_plantilla", referencedColumnName = "id_plantilla_instrumento")
    @ManyToOne
    private PlantillaInstrumento plantillaInstrumento;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo_verificacion")
    @ManyToOne
    private TipoVerificacion tipoVerificacion;

    public Verificacion() {
    }

    public Verificacion(Integer idVerificacion) {
        this.idVerificacion = idVerificacion;
    }

    public Verificacion(Integer idVerificacion, Date fchRegistro, String usuRegistro) {
        this.idVerificacion = idVerificacion;
        this.fchRegistro = fchRegistro;
        this.usuRegistro = usuRegistro;
    }

    public Integer getIdVerificacion() {
        return idVerificacion;
    }

    public void setIdVerificacion(Integer idVerificacion) {
        this.idVerificacion = idVerificacion;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
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

    public InstrumentoMedicion getInstrumentoMedicion() {
        return instrumentoMedicion;
    }

    public void setInstrumentoMedicion(InstrumentoMedicion instrumentoMedicion) {
        this.instrumentoMedicion = instrumentoMedicion;
    }

    public PlantillaInstrumento getPlantillaInstrumento() {
        return plantillaInstrumento;
    }

    public void setPlantillaInstrumento(PlantillaInstrumento plantillaInstrumento) {
        this.plantillaInstrumento = plantillaInstrumento;
    }

    public TipoVerificacion getTipoVerificacion() {
        return tipoVerificacion;
    }

    public void setTipoVerificacion(TipoVerificacion tipoVerificacion) {
        this.tipoVerificacion = tipoVerificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVerificacion != null ? idVerificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Verificacion)) {
            return false;
        }
        Verificacion other = (Verificacion) object;
        if ((this.idVerificacion == null && other.idVerificacion != null) || (this.idVerificacion != null && !this.idVerificacion.equals(other.idVerificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Verificacion[ idVerificacion=" + idVerificacion + " ]";
    }
    
}
