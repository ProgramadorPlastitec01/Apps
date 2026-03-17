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
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Programador.TI2
 */
@Entity
@Table(name = "instrumento_medicion")
@NamedQueries({
    @NamedQuery(name = "InstrumentoMedicion.findAll", query = "SELECT i FROM InstrumentoMedicion i")})
public class InstrumentoMedicion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_instrumento_medicion")
    private Integer idInstrumentoMedicion;
    @Column(name = "id_plantilla_verificacion")
    private Integer idPlantillaVerificacion;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "instrumento")
    private String instrumento;
    @Column(name = "fabricante")
    private String fabricante;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "numero_serial")
    private String numeroSerial;
    @Column(name = "rango_medida")
    private String rangoMedida;
    @Column(name = "division_escala")
    private String divisionEscala;
    @Column(name = "exactitud")
    private String exactitud;
    @Column(name = "clasificacion")
    private String clasificacion;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fch_ultima_verificacion_int")
    @Temporal(TemporalType.DATE)
    private Date fchUltimaVerificacionInt;
    @Column(name = "fch_ultima_verificacion_ext")
    @Temporal(TemporalType.DATE)
    private Date fchUltimaVerificacionExt;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    @ManyToOne
    private Tipo tipo;
    @JoinColumn(name = "id_tipo_instrumento", referencedColumnName = "id_tipo_instrumento")
    @ManyToOne
    private TipoInstrumento tipoInstrumento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instrumentoMedicion")
    private Collection<Traslado> trasladoCollection;
    @OneToMany(mappedBy = "instrumentoMedicion")
    private Collection<Verificacion> verificacionCollection;
    @OneToMany(mappedBy = "instrumentoMedicion")
    private Collection<NoConformidad> noConformidadCollection;

    public InstrumentoMedicion() {
    }

    public InstrumentoMedicion(Integer idInstrumentoMedicion) {
        this.idInstrumentoMedicion = idInstrumentoMedicion;
    }

    public Integer getIdInstrumentoMedicion() {
        return idInstrumentoMedicion;
    }

    public void setIdInstrumentoMedicion(Integer idInstrumentoMedicion) {
        this.idInstrumentoMedicion = idInstrumentoMedicion;
    }

    public Integer getIdPlantillaVerificacion() {
        return idPlantillaVerificacion;
    }

    public void setIdPlantillaVerificacion(Integer idPlantillaVerificacion) {
        this.idPlantillaVerificacion = idPlantillaVerificacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerial() {
        return numeroSerial;
    }

    public void setNumeroSerial(String numeroSerial) {
        this.numeroSerial = numeroSerial;
    }

    public String getRangoMedida() {
        return rangoMedida;
    }

    public void setRangoMedida(String rangoMedida) {
        this.rangoMedida = rangoMedida;
    }

    public String getDivisionEscala() {
        return divisionEscala;
    }

    public void setDivisionEscala(String divisionEscala) {
        this.divisionEscala = divisionEscala;
    }

    public String getExactitud() {
        return exactitud;
    }

    public void setExactitud(String exactitud) {
        this.exactitud = exactitud;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFchUltimaVerificacionInt() {
        return fchUltimaVerificacionInt;
    }

    public void setFchUltimaVerificacionInt(Date fchUltimaVerificacionInt) {
        this.fchUltimaVerificacionInt = fchUltimaVerificacionInt;
    }

    public Date getFchUltimaVerificacionExt() {
        return fchUltimaVerificacionExt;
    }

    public void setFchUltimaVerificacionExt(Date fchUltimaVerificacionExt) {
        this.fchUltimaVerificacionExt = fchUltimaVerificacionExt;
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

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public TipoInstrumento getTipoInstrumento() {
        return tipoInstrumento;
    }

    public void setTipoInstrumento(TipoInstrumento tipoInstrumento) {
        this.tipoInstrumento = tipoInstrumento;
    }

    public Collection<Traslado> getTrasladoCollection() {
        return trasladoCollection;
    }

    public void setTrasladoCollection(Collection<Traslado> trasladoCollection) {
        this.trasladoCollection = trasladoCollection;
    }

    public Collection<Verificacion> getVerificacionCollection() {
        return verificacionCollection;
    }

    public void setVerificacionCollection(Collection<Verificacion> verificacionCollection) {
        this.verificacionCollection = verificacionCollection;
    }

    public Collection<NoConformidad> getNoConformidadCollection() {
        return noConformidadCollection;
    }

    public void setNoConformidadCollection(Collection<NoConformidad> noConformidadCollection) {
        this.noConformidadCollection = noConformidadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInstrumentoMedicion != null ? idInstrumentoMedicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstrumentoMedicion)) {
            return false;
        }
        InstrumentoMedicion other = (InstrumentoMedicion) object;
        if ((this.idInstrumentoMedicion == null && other.idInstrumentoMedicion != null) || (this.idInstrumentoMedicion != null && !this.idInstrumentoMedicion.equals(other.idInstrumentoMedicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.InstrumentoMedicion[ idInstrumentoMedicion=" + idInstrumentoMedicion + " ]";
    }
    
}
