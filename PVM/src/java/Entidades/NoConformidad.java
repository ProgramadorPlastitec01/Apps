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
@Table(name = "no_conformidad")
@NamedQueries({
    @NamedQuery(name = "NoConformidad.findAll", query = "SELECT n FROM NoConformidad n")})
public class NoConformidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_no_\u001fconformidad")
    private Integer idNoConformidad;
    @Column(name = "consecutivo")
    private Integer consecutivo;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Column(name = "plantilla")
    private String plantilla;
    @Column(name = "correo")
    private Integer correo;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @JoinColumn(name = "id_instrumento_medicion", referencedColumnName = "id_instrumento_medicion")
    @ManyToOne
    private InstrumentoMedicion instrumentoMedicion;

    public NoConformidad() {
    }

    public NoConformidad(Integer idNoConformidad) {
        this.idNoConformidad = idNoConformidad;
    }

    public Integer getIdNoConformidad() {
        return idNoConformidad;
    }

    public void setIdNoConformidad(Integer idNoConformidad) {
        this.idNoConformidad = idNoConformidad;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public Integer getCorreo() {
        return correo;
    }

    public void setCorreo(Integer correo) {
        this.correo = correo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNoConformidad != null ? idNoConformidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NoConformidad)) {
            return false;
        }
        NoConformidad other = (NoConformidad) object;
        if ((this.idNoConformidad == null && other.idNoConformidad != null) || (this.idNoConformidad != null && !this.idNoConformidad.equals(other.idNoConformidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.NoConformidad[ idNoConformidad=" + idNoConformidad + " ]";
    }
    
}
