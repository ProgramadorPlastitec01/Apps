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
import javax.persistence.Lob;
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
@Table(name = "plantilla_instrumento")
@NamedQueries({
    @NamedQuery(name = "PlantillaInstrumento.findAll", query = "SELECT p FROM PlantillaInstrumento p")})
public class PlantillaInstrumento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_plantilla_instrumento")
    private Integer idPlantillaInstrumento;
    @Basic(optional = false)
    @Column(name = "id_instrumento")
    private int idInstrumento;
    @Column(name = "id_tipo_plantilla")
    private Integer idTipoPlantilla;
    @Lob
    @Column(name = "plantilla")
    private String plantilla;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "plantillaInstrumento")
    private Collection<Verificacion> verificacionCollection;

    public PlantillaInstrumento() {
    }

    public PlantillaInstrumento(Integer idPlantillaInstrumento) {
        this.idPlantillaInstrumento = idPlantillaInstrumento;
    }

    public PlantillaInstrumento(Integer idPlantillaInstrumento, int idInstrumento) {
        this.idPlantillaInstrumento = idPlantillaInstrumento;
        this.idInstrumento = idInstrumento;
    }

    public Integer getIdPlantillaInstrumento() {
        return idPlantillaInstrumento;
    }

    public void setIdPlantillaInstrumento(Integer idPlantillaInstrumento) {
        this.idPlantillaInstrumento = idPlantillaInstrumento;
    }

    public int getIdInstrumento() {
        return idInstrumento;
    }

    public void setIdInstrumento(int idInstrumento) {
        this.idInstrumento = idInstrumento;
    }

    public Integer getIdTipoPlantilla() {
        return idTipoPlantilla;
    }

    public void setIdTipoPlantilla(Integer idTipoPlantilla) {
        this.idTipoPlantilla = idTipoPlantilla;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Collection<Verificacion> getVerificacionCollection() {
        return verificacionCollection;
    }

    public void setVerificacionCollection(Collection<Verificacion> verificacionCollection) {
        this.verificacionCollection = verificacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlantillaInstrumento != null ? idPlantillaInstrumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlantillaInstrumento)) {
            return false;
        }
        PlantillaInstrumento other = (PlantillaInstrumento) object;
        if ((this.idPlantillaInstrumento == null && other.idPlantillaInstrumento != null) || (this.idPlantillaInstrumento != null && !this.idPlantillaInstrumento.equals(other.idPlantillaInstrumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.PlantillaInstrumento[ idPlantillaInstrumento=" + idPlantillaInstrumento + " ]";
    }
    
}
