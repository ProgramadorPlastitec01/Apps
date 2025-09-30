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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author prog.sistemas1
 */
@Entity
@Table(name = "ficha_tecnica")
@NamedQueries({
    @NamedQuery(name = "FichaTecnica.findAll", query = "SELECT f FROM FichaTecnica f"),
    @NamedQuery(name = "FichaTecnica.findByIdFichaTecnica", query = "SELECT f FROM FichaTecnica f WHERE f.idFichaTecnica = :idFichaTecnica"),
    @NamedQuery(name = "FichaTecnica.findByProducto", query = "SELECT f FROM FichaTecnica f WHERE f.producto = :producto"),
    @NamedQuery(name = "FichaTecnica.findByCodigo", query = "SELECT f FROM FichaTecnica f WHERE f.codigo = :codigo"),
    @NamedQuery(name = "FichaTecnica.findByVersion", query = "SELECT f FROM FichaTecnica f WHERE f.version = :version"),
    @NamedQuery(name = "FichaTecnica.findByParedDoble", query = "SELECT f FROM FichaTecnica f WHERE f.paredDoble = :paredDoble"),
    @NamedQuery(name = "FichaTecnica.findByParedDobleMax", query = "SELECT f FROM FichaTecnica f WHERE f.paredDobleMax = :paredDobleMax"),
    @NamedQuery(name = "FichaTecnica.findByParedDobleMin", query = "SELECT f FROM FichaTecnica f WHERE f.paredDobleMin = :paredDobleMin"),
    @NamedQuery(name = "FichaTecnica.findByParedSencilla", query = "SELECT f FROM FichaTecnica f WHERE f.paredSencilla = :paredSencilla"),
    @NamedQuery(name = "FichaTecnica.findByParedSencillaMax", query = "SELECT f FROM FichaTecnica f WHERE f.paredSencillaMax = :paredSencillaMax"),
    @NamedQuery(name = "FichaTecnica.findByParedSencillaMin", query = "SELECT f FROM FichaTecnica f WHERE f.paredSencillaMin = :paredSencillaMin"),
    @NamedQuery(name = "FichaTecnica.findByAnchoManga", query = "SELECT f FROM FichaTecnica f WHERE f.anchoManga = :anchoManga"),
    @NamedQuery(name = "FichaTecnica.findByAnchoMangaMax", query = "SELECT f FROM FichaTecnica f WHERE f.anchoMangaMax = :anchoMangaMax"),
    @NamedQuery(name = "FichaTecnica.findByAnchoMangaMin", query = "SELECT f FROM FichaTecnica f WHERE f.anchoMangaMin = :anchoMangaMin"),
    @NamedQuery(name = "FichaTecnica.findByAnchoBobina", query = "SELECT f FROM FichaTecnica f WHERE f.anchoBobina = :anchoBobina"),
    @NamedQuery(name = "FichaTecnica.findByAnchoBobinaMax", query = "SELECT f FROM FichaTecnica f WHERE f.anchoBobinaMax = :anchoBobinaMax"),
    @NamedQuery(name = "FichaTecnica.findByAnchoBobinaMin", query = "SELECT f FROM FichaTecnica f WHERE f.anchoBobinaMin = :anchoBobinaMin"),
    @NamedQuery(name = "FichaTecnica.findByDureza", query = "SELECT f FROM FichaTecnica f WHERE f.dureza = :dureza"),
    @NamedQuery(name = "FichaTecnica.findByDurezaMax", query = "SELECT f FROM FichaTecnica f WHERE f.durezaMax = :durezaMax"),
    @NamedQuery(name = "FichaTecnica.findByDurezaMin", query = "SELECT f FROM FichaTecnica f WHERE f.durezaMin = :durezaMin"),
    @NamedQuery(name = "FichaTecnica.findByVariacionEspesor", query = "SELECT f FROM FichaTecnica f WHERE f.variacionEspesor = :variacionEspesor"),
    @NamedQuery(name = "FichaTecnica.findByCurvatura", query = "SELECT f FROM FichaTecnica f WHERE f.curvatura = :curvatura"),
    @NamedQuery(name = "FichaTecnica.findByDiferenciaPerimetro", query = "SELECT f FROM FichaTecnica f WHERE f.diferenciaPerimetro = :diferenciaPerimetro"),
    @NamedQuery(name = "FichaTecnica.findByPeso", query = "SELECT f FROM FichaTecnica f WHERE f.peso = :peso"),
    @NamedQuery(name = "FichaTecnica.findByPesoMax", query = "SELECT f FROM FichaTecnica f WHERE f.pesoMax = :pesoMax"),
    @NamedQuery(name = "FichaTecnica.findByPesoMin", query = "SELECT f FROM FichaTecnica f WHERE f.pesoMin = :pesoMin"),
    @NamedQuery(name = "FichaTecnica.findByPesoAmarre", query = "SELECT f FROM FichaTecnica f WHERE f.pesoAmarre = :pesoAmarre"),
    @NamedQuery(name = "FichaTecnica.findByPesoNucleo", query = "SELECT f FROM FichaTecnica f WHERE f.pesoNucleo = :pesoNucleo"),
    @NamedQuery(name = "FichaTecnica.findByPesoBolsa", query = "SELECT f FROM FichaTecnica f WHERE f.pesoBolsa = :pesoBolsa"),
    @NamedQuery(name = "FichaTecnica.findByFrecuenciaControl", query = "SELECT f FROM FichaTecnica f WHERE f.frecuenciaControl = :frecuenciaControl"),
    @NamedQuery(name = "FichaTecnica.findByCantidadTomas", query = "SELECT f FROM FichaTecnica f WHERE f.cantidadTomas = :cantidadTomas"),
    @NamedQuery(name = "FichaTecnica.findByCantidadEvaluar", query = "SELECT f FROM FichaTecnica f WHERE f.cantidadEvaluar = :cantidadEvaluar"),
    @NamedQuery(name = "FichaTecnica.findByEstado", query = "SELECT f FROM FichaTecnica f WHERE f.estado = :estado"),
    @NamedQuery(name = "FichaTecnica.findByUsuarioRegistro", query = "SELECT f FROM FichaTecnica f WHERE f.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "FichaTecnica.findByFechaRegistro", query = "SELECT f FROM FichaTecnica f WHERE f.fechaRegistro = :fechaRegistro")})
public class FichaTecnica implements Serializable {

    @Column(name = "aplica_pd")
    private Short aplicaPd;
    @Column(name = "material")
    private Short material;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ficha_tecnica")
    private Integer idFichaTecnica;
    @Column(name = "producto")
    private String producto;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "version")
    private Integer version;
    @Column(name = "pared_doble")
    private Double paredDoble;
    @Column(name = "pared_doble_max")
    private Double paredDobleMax;
    @Column(name = "pared_doble_min")
    private Double paredDobleMin;
    @Column(name = "pared_sencilla")
    private Double paredSencilla;
    @Column(name = "pared_sencilla_max")
    private Double paredSencillaMax;
    @Column(name = "pared_sencilla_min")
    private Double paredSencillaMin;
    @Column(name = "ancho_manga")
    private Double anchoManga;
    @Column(name = "ancho_manga_max")
    private Double anchoMangaMax;
    @Column(name = "ancho_manga_min")
    private Double anchoMangaMin;
    @Column(name = "ancho_bobina")
    private Double anchoBobina;
    @Column(name = "ancho_bobina_max")
    private Double anchoBobinaMax;
    @Column(name = "ancho_bobina_min")
    private Double anchoBobinaMin;
    @Column(name = "dureza")
    private Double dureza;
    @Column(name = "dureza_max")
    private Double durezaMax;
    @Column(name = "dureza_min")
    private Double durezaMin;
    @Column(name = "variacion_espesor")
    private Double variacionEspesor;
    @Column(name = "curvatura")
    private Double curvatura;
    @Column(name = "diferencia_perimetro")
    private Double diferenciaPerimetro;
    @Column(name = "peso")
    private Double peso;
    @Column(name = "peso_max")
    private Double pesoMax;
    @Column(name = "peso_min")
    private Double pesoMin;
    @Column(name = "peso_amarre")
    private Double pesoAmarre;
    @Column(name = "peso_nucleo")
    private Double pesoNucleo;
    @Column(name = "peso_bolsa")
    private Double pesoBolsa;
    @Column(name = "frecuencia_control")
    private Integer frecuenciaControl;
    @Column(name = "cantidad_tomas")
    private Integer cantidadTomas;
    @Column(name = "cantidad_evaluar")
    private Integer cantidadEvaluar;
    @Column(name = "estado")
    private Integer estado;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "fichaTecnica")
    private Collection<Producto> productoCollection;

    public FichaTecnica() {
    }

    public FichaTecnica(Integer idFichaTecnica) {
        this.idFichaTecnica = idFichaTecnica;
    }

    public Integer getIdFichaTecnica() {
        return idFichaTecnica;
    }

    public void setIdFichaTecnica(Integer idFichaTecnica) {
        this.idFichaTecnica = idFichaTecnica;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Double getParedDoble() {
        return paredDoble;
    }

    public void setParedDoble(Double paredDoble) {
        this.paredDoble = paredDoble;
    }

    public Double getParedDobleMax() {
        return paredDobleMax;
    }

    public void setParedDobleMax(Double paredDobleMax) {
        this.paredDobleMax = paredDobleMax;
    }

    public Double getParedDobleMin() {
        return paredDobleMin;
    }

    public void setParedDobleMin(Double paredDobleMin) {
        this.paredDobleMin = paredDobleMin;
    }

    public Double getParedSencilla() {
        return paredSencilla;
    }

    public void setParedSencilla(Double paredSencilla) {
        this.paredSencilla = paredSencilla;
    }

    public Double getParedSencillaMax() {
        return paredSencillaMax;
    }

    public void setParedSencillaMax(Double paredSencillaMax) {
        this.paredSencillaMax = paredSencillaMax;
    }

    public Double getParedSencillaMin() {
        return paredSencillaMin;
    }

    public void setParedSencillaMin(Double paredSencillaMin) {
        this.paredSencillaMin = paredSencillaMin;
    }

    public Double getAnchoManga() {
        return anchoManga;
    }

    public void setAnchoManga(Double anchoManga) {
        this.anchoManga = anchoManga;
    }

    public Double getAnchoMangaMax() {
        return anchoMangaMax;
    }

    public void setAnchoMangaMax(Double anchoMangaMax) {
        this.anchoMangaMax = anchoMangaMax;
    }

    public Double getAnchoMangaMin() {
        return anchoMangaMin;
    }

    public void setAnchoMangaMin(Double anchoMangaMin) {
        this.anchoMangaMin = anchoMangaMin;
    }

    public Double getAnchoBobina() {
        return anchoBobina;
    }

    public void setAnchoBobina(Double anchoBobina) {
        this.anchoBobina = anchoBobina;
    }

    public Double getAnchoBobinaMax() {
        return anchoBobinaMax;
    }

    public void setAnchoBobinaMax(Double anchoBobinaMax) {
        this.anchoBobinaMax = anchoBobinaMax;
    }

    public Double getAnchoBobinaMin() {
        return anchoBobinaMin;
    }

    public void setAnchoBobinaMin(Double anchoBobinaMin) {
        this.anchoBobinaMin = anchoBobinaMin;
    }

    public Double getDureza() {
        return dureza;
    }

    public void setDureza(Double dureza) {
        this.dureza = dureza;
    }

    public Double getDurezaMax() {
        return durezaMax;
    }

    public void setDurezaMax(Double durezaMax) {
        this.durezaMax = durezaMax;
    }

    public Double getDurezaMin() {
        return durezaMin;
    }

    public void setDurezaMin(Double durezaMin) {
        this.durezaMin = durezaMin;
    }

    public Double getVariacionEspesor() {
        return variacionEspesor;
    }

    public void setVariacionEspesor(Double variacionEspesor) {
        this.variacionEspesor = variacionEspesor;
    }

    public Double getCurvatura() {
        return curvatura;
    }

    public void setCurvatura(Double curvatura) {
        this.curvatura = curvatura;
    }

    public Double getDiferenciaPerimetro() {
        return diferenciaPerimetro;
    }

    public void setDiferenciaPerimetro(Double diferenciaPerimetro) {
        this.diferenciaPerimetro = diferenciaPerimetro;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getPesoMax() {
        return pesoMax;
    }

    public void setPesoMax(Double pesoMax) {
        this.pesoMax = pesoMax;
    }

    public Double getPesoMin() {
        return pesoMin;
    }

    public void setPesoMin(Double pesoMin) {
        this.pesoMin = pesoMin;
    }

    public Double getPesoAmarre() {
        return pesoAmarre;
    }

    public void setPesoAmarre(Double pesoAmarre) {
        this.pesoAmarre = pesoAmarre;
    }

    public Double getPesoNucleo() {
        return pesoNucleo;
    }

    public void setPesoNucleo(Double pesoNucleo) {
        this.pesoNucleo = pesoNucleo;
    }

    public Double getPesoBolsa() {
        return pesoBolsa;
    }

    public void setPesoBolsa(Double pesoBolsa) {
        this.pesoBolsa = pesoBolsa;
    }

    public Integer getFrecuenciaControl() {
        return frecuenciaControl;
    }

    public void setFrecuenciaControl(Integer frecuenciaControl) {
        this.frecuenciaControl = frecuenciaControl;
    }

    public Integer getCantidadTomas() {
        return cantidadTomas;
    }

    public void setCantidadTomas(Integer cantidadTomas) {
        this.cantidadTomas = cantidadTomas;
    }

    public Integer getCantidadEvaluar() {
        return cantidadEvaluar;
    }

    public void setCantidadEvaluar(Integer cantidadEvaluar) {
        this.cantidadEvaluar = cantidadEvaluar;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFichaTecnica != null ? idFichaTecnica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichaTecnica)) {
            return false;
        }
        FichaTecnica other = (FichaTecnica) object;
        if ((this.idFichaTecnica == null && other.idFichaTecnica != null) || (this.idFichaTecnica != null && !this.idFichaTecnica.equals(other.idFichaTecnica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FichaTecnica[idFichaTecnica=" + idFichaTecnica + "]";
    }

    public Short getAplicaPd() {
        return aplicaPd;
    }

    public void setAplicaPd(Short aplicaPd) {
        this.aplicaPd = aplicaPd;
    }

    public Short getMaterial() {
        return material;
    }

    public void setMaterial(Short material) {
        this.material = material;
    }

}
