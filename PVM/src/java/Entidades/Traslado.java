/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
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

/**
 *
 * @author Programador.TI2
 */
@Entity
@Table(name = "traslado")
@NamedQueries({
    @NamedQuery(name = "Traslado.findAll", query = "SELECT t FROM Traslado t")})
public class Traslado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_traslado")
    private Integer idTraslado;
    @Basic(optional = false)
    @Lob
    @Column(name = "accesorio")
    private String accesorio;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Lob
    @Column(name = "plantilla")
    private String plantilla;
    @Basic(optional = false)
    @Column(name = "usuario_sal")
    private String usuarioSal;
    @Basic(optional = false)
    @Column(name = "usuario_ent")
    private String usuarioEnt;
    @JoinColumn(name = "id_instrumento", referencedColumnName = "id_instrumento_medicion")
    @ManyToOne(optional = false)
    private InstrumentoMedicion instrumentoMedicion;

    public Traslado() {
    }

    public Traslado(Integer idTraslado) {
        this.idTraslado = idTraslado;
    }

    public Traslado(Integer idTraslado, String accesorio, String tipo, String plantilla, String usuarioSal, String usuarioEnt) {
        this.idTraslado = idTraslado;
        this.accesorio = accesorio;
        this.tipo = tipo;
        this.plantilla = plantilla;
        this.usuarioSal = usuarioSal;
        this.usuarioEnt = usuarioEnt;
    }

    public Integer getIdTraslado() {
        return idTraslado;
    }

    public void setIdTraslado(Integer idTraslado) {
        this.idTraslado = idTraslado;
    }

    public String getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(String accesorio) {
        this.accesorio = accesorio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public String getUsuarioSal() {
        return usuarioSal;
    }

    public void setUsuarioSal(String usuarioSal) {
        this.usuarioSal = usuarioSal;
    }

    public String getUsuarioEnt() {
        return usuarioEnt;
    }

    public void setUsuarioEnt(String usuarioEnt) {
        this.usuarioEnt = usuarioEnt;
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
        hash += (idTraslado != null ? idTraslado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Traslado)) {
            return false;
        }
        Traslado other = (Traslado) object;
        if ((this.idTraslado == null && other.idTraslado != null) || (this.idTraslado != null && !this.idTraslado.equals(other.idTraslado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Traslado[ idTraslado=" + idTraslado + " ]";
    }
    
}
