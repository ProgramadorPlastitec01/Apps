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
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoBocas", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoBocas = :prmSelladoBocas"),
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoBocasMax", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoBocasMax = :prmSelladoBocasMax"),
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoBocasMin", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoBocasMin = :prmSelladoBocasMin"),
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoBocasAlt", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoBocasAlt = :prmSelladoBocasAlt"),
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoBocasMaxAlt", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoBocasMaxAlt = :prmSelladoBocasMaxAlt"),
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoBocasMinAlt", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoBocasMinAlt = :prmSelladoBocasMinAlt"),
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoColas", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoColas = :prmSelladoColas"),
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoColasMax", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoColasMax = :prmSelladoColasMax"),
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoColasMin", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoColasMin = :prmSelladoColasMin"),
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoColasAlt", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoColasAlt = :prmSelladoColasAlt"),
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoColasMaxAlt", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoColasMaxAlt = :prmSelladoColasMaxAlt"),
    @NamedQuery(name = "FichaTecnica.findByPrmSelladoColasMinAlt", query = "SELECT f FROM FichaTecnica f WHERE f.prmSelladoColasMinAlt = :prmSelladoColasMinAlt"),
    @NamedQuery(name = "FichaTecnica.findByLongCuerpoSellado", query = "SELECT f FROM FichaTecnica f WHERE f.longCuerpoSellado = :longCuerpoSellado"),
    @NamedQuery(name = "FichaTecnica.findByLongCuerpoSelladoMax", query = "SELECT f FROM FichaTecnica f WHERE f.longCuerpoSelladoMax = :longCuerpoSelladoMax"),
    @NamedQuery(name = "FichaTecnica.findByLongCuerpoSelladoMin", query = "SELECT f FROM FichaTecnica f WHERE f.longCuerpoSelladoMin = :longCuerpoSelladoMin"),
    @NamedQuery(name = "FichaTecnica.findByDuctoDrcLong", query = "SELECT f FROM FichaTecnica f WHERE f.ductoDrcLong = :ductoDrcLong"),
    @NamedQuery(name = "FichaTecnica.findByDuctoDrcLongMax", query = "SELECT f FROM FichaTecnica f WHERE f.ductoDrcLongMax = :ductoDrcLongMax"),
    @NamedQuery(name = "FichaTecnica.findByDuctoDrcLongMin", query = "SELECT f FROM FichaTecnica f WHERE f.ductoDrcLongMin = :ductoDrcLongMin"),
    @NamedQuery(name = "FichaTecnica.findByDuctoCtlLong", query = "SELECT f FROM FichaTecnica f WHERE f.ductoCtlLong = :ductoCtlLong"),
    @NamedQuery(name = "FichaTecnica.findByDuctoCtlLongMax", query = "SELECT f FROM FichaTecnica f WHERE f.ductoCtlLongMax = :ductoCtlLongMax"),
    @NamedQuery(name = "FichaTecnica.findByDuctoCtlLongMin", query = "SELECT f FROM FichaTecnica f WHERE f.ductoCtlLongMin = :ductoCtlLongMin"),
    @NamedQuery(name = "FichaTecnica.findByDuctoIqeLong", query = "SELECT f FROM FichaTecnica f WHERE f.ductoIqeLong = :ductoIqeLong"),
    @NamedQuery(name = "FichaTecnica.findByDuctoIqeLongMax", query = "SELECT f FROM FichaTecnica f WHERE f.ductoIqeLongMax = :ductoIqeLongMax"),
    @NamedQuery(name = "FichaTecnica.findByDuctoIqeLongMin", query = "SELECT f FROM FichaTecnica f WHERE f.ductoIqeLongMin = :ductoIqeLongMin"),
    @NamedQuery(name = "FichaTecnica.findByDiametroItrDuctoDrc", query = "SELECT f FROM FichaTecnica f WHERE f.diametroItrDuctoDrc = :diametroItrDuctoDrc"),
    @NamedQuery(name = "FichaTecnica.findByDiametroItrDuctoDrcMax", query = "SELECT f FROM FichaTecnica f WHERE f.diametroItrDuctoDrcMax = :diametroItrDuctoDrcMax"),
    @NamedQuery(name = "FichaTecnica.findByDiametroItrDuctoDrcMin", query = "SELECT f FROM FichaTecnica f WHERE f.diametroItrDuctoDrcMin = :diametroItrDuctoDrcMin"),
    @NamedQuery(name = "FichaTecnica.findByDiametroEtrDuctoDrc", query = "SELECT f FROM FichaTecnica f WHERE f.diametroEtrDuctoDrc = :diametroEtrDuctoDrc"),
    @NamedQuery(name = "FichaTecnica.findByDiametroEtrDuctoDrcMax", query = "SELECT f FROM FichaTecnica f WHERE f.diametroEtrDuctoDrcMax = :diametroEtrDuctoDrcMax"),
    @NamedQuery(name = "FichaTecnica.findByDiametroEtrDuctoDrcMin", query = "SELECT f FROM FichaTecnica f WHERE f.diametroEtrDuctoDrcMin = :diametroEtrDuctoDrcMin"),
    @NamedQuery(name = "FichaTecnica.findByDiametroItrDuctoCtl", query = "SELECT f FROM FichaTecnica f WHERE f.diametroItrDuctoCtl = :diametroItrDuctoCtl"),
    @NamedQuery(name = "FichaTecnica.findByDiametroItrDuctoCtlMax", query = "SELECT f FROM FichaTecnica f WHERE f.diametroItrDuctoCtlMax = :diametroItrDuctoCtlMax"),
    @NamedQuery(name = "FichaTecnica.findByDiametroItrDuctoCtlMin", query = "SELECT f FROM FichaTecnica f WHERE f.diametroItrDuctoCtlMin = :diametroItrDuctoCtlMin"),
    @NamedQuery(name = "FichaTecnica.findByDiametroEtrDuctoCtl", query = "SELECT f FROM FichaTecnica f WHERE f.diametroEtrDuctoCtl = :diametroEtrDuctoCtl"),
    @NamedQuery(name = "FichaTecnica.findByDiametroEtrDuctoCtlMax", query = "SELECT f FROM FichaTecnica f WHERE f.diametroEtrDuctoCtlMax = :diametroEtrDuctoCtlMax"),
    @NamedQuery(name = "FichaTecnica.findByDiametroEtrDuctoCtlMin", query = "SELECT f FROM FichaTecnica f WHERE f.diametroEtrDuctoCtlMin = :diametroEtrDuctoCtlMin"),
    @NamedQuery(name = "FichaTecnica.findByDiametroItrDuctoIqe", query = "SELECT f FROM FichaTecnica f WHERE f.diametroItrDuctoIqe = :diametroItrDuctoIqe"),
    @NamedQuery(name = "FichaTecnica.findByDiametroItrDuctoIqeMax", query = "SELECT f FROM FichaTecnica f WHERE f.diametroItrDuctoIqeMax = :diametroItrDuctoIqeMax"),
    @NamedQuery(name = "FichaTecnica.findByDiametroItrDuctoIqeMin", query = "SELECT f FROM FichaTecnica f WHERE f.diametroItrDuctoIqeMin = :diametroItrDuctoIqeMin"),
    @NamedQuery(name = "FichaTecnica.findByDiametroEtrDuctoIqe", query = "SELECT f FROM FichaTecnica f WHERE f.diametroEtrDuctoIqe = :diametroEtrDuctoIqe"),
    @NamedQuery(name = "FichaTecnica.findByDiametroEtrDuctoIqeMax", query = "SELECT f FROM FichaTecnica f WHERE f.diametroEtrDuctoIqeMax = :diametroEtrDuctoIqeMax"),
    @NamedQuery(name = "FichaTecnica.findByDiametroEtrDuctoIqeMin", query = "SELECT f FROM FichaTecnica f WHERE f.diametroEtrDuctoIqeMin = :diametroEtrDuctoIqeMin"),
    @NamedQuery(name = "FichaTecnica.findByAnchoManga", query = "SELECT f FROM FichaTecnica f WHERE f.anchoManga = :anchoManga"),
    @NamedQuery(name = "FichaTecnica.findByAnchoMangaMax", query = "SELECT f FROM FichaTecnica f WHERE f.anchoMangaMax = :anchoMangaMax"),
    @NamedQuery(name = "FichaTecnica.findByAnchoMangaMin", query = "SELECT f FROM FichaTecnica f WHERE f.anchoMangaMin = :anchoMangaMin"),
    @NamedQuery(name = "FichaTecnica.findByEstado", query = "SELECT f FROM FichaTecnica f WHERE f.estado = :estado"),
    @NamedQuery(name = "FichaTecnica.findByUsuarioRegistro", query = "SELECT f FROM FichaTecnica f WHERE f.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "FichaTecnica.findByFechaRegistro", query = "SELECT f FROM FichaTecnica f WHERE f.fechaRegistro = :fechaRegistro")})
public class FichaTecnica implements Serializable {

    @Column(name = "ancho_ventana")
    private Double anchoVentana;
    @Column(name = "ancho_ventana_max")
    private Double anchoVentanaMax;
    @Column(name = "ancho_ventana_min")
    private Double anchoVentanaMin;
    @Column(name = "ducto_bicapa_espesor_itn")
    private Double ductoBicapaEspesorItn;
    @Column(name = "ducto_bicapa_espesor_itn_max")
    private Double ductoBicapaEspesorItnMax;
    @Column(name = "ducto_bicapa_espesor_itn_min")
    private Double ductoBicapaEspesorItnMin;
    @Column(name = "ducto_bicapa_espesor_etn")
    private Double ductoBicapaEspesorEtn;
    @Column(name = "ducto_bicapa_espesor_etn_max")
    private Double ductoBicapaEspesorEtnMax;
    @Column(name = "ducto_bicapa_espesor_etn_min")
    private Double ductoBicapaEspesorEtnMin;
    @Column(name = "ft_eva")
    private String ftEva;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ficha_tecnica")
    private Integer idFichaTecnica;
    @Column(name = "producto")
    private String producto;
    @Lob
    @Column(name = "materiales")
    private String materiales;
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
    @Column(name = "prm_sellado_bocas")
    private Double prmSelladoBocas;
    @Column(name = "prm_sellado_bocas_max")
    private Double prmSelladoBocasMax;
    @Column(name = "prm_sellado_bocas_min")
    private Double prmSelladoBocasMin;
    @Column(name = "prm_sellado_bocas_alt")
    private Double prmSelladoBocasAlt;
    @Column(name = "prm_sellado_bocas_max_alt")
    private Double prmSelladoBocasMaxAlt;
    @Column(name = "prm_sellado_bocas_min_alt")
    private Double prmSelladoBocasMinAlt;
    @Column(name = "prm_sellado_colas")
    private Double prmSelladoColas;
    @Column(name = "prm_sellado_colas_max")
    private Double prmSelladoColasMax;
    @Column(name = "prm_sellado_colas_min")
    private Double prmSelladoColasMin;
    @Column(name = "prm_sellado_colas_alt")
    private Double prmSelladoColasAlt;
    @Column(name = "prm_sellado_colas_max_alt")
    private Double prmSelladoColasMaxAlt;
    @Column(name = "prm_sellado_colas_min_alt")
    private Double prmSelladoColasMinAlt;
    @Column(name = "long_cuerpo_sellado")
    private Double longCuerpoSellado;
    @Column(name = "long_cuerpo_sellado_max")
    private Double longCuerpoSelladoMax;
    @Column(name = "long_cuerpo_sellado_min")
    private Double longCuerpoSelladoMin;
    @Column(name = "ducto_drc_long")
    private Double ductoDrcLong;
    @Column(name = "ducto_drc_long_max")
    private Double ductoDrcLongMax;
    @Column(name = "ducto_drc_long_min")
    private Double ductoDrcLongMin;
    @Column(name = "ducto_ctl_long")
    private Double ductoCtlLong;
    @Column(name = "ducto_ctl_long_max")
    private Double ductoCtlLongMax;
    @Column(name = "ducto_ctl_long_min")
    private Double ductoCtlLongMin;
    @Column(name = "ducto_iqe_long")
    private Double ductoIqeLong;
    @Column(name = "ducto_iqe_long_max")
    private Double ductoIqeLongMax;
    @Column(name = "ducto_iqe_long_min")
    private Double ductoIqeLongMin;
    @Column(name = "diametro_itr_ducto_drc")
    private Double diametroItrDuctoDrc;
    @Column(name = "diametro_itr_ducto_drc_max")
    private Double diametroItrDuctoDrcMax;
    @Column(name = "diametro_itr_ducto_drc_min")
    private Double diametroItrDuctoDrcMin;
    @Column(name = "diametro_etr_ducto_drc")
    private Double diametroEtrDuctoDrc;
    @Column(name = "diametro_etr_ducto_drc_max")
    private Double diametroEtrDuctoDrcMax;
    @Column(name = "diametro_etr_ducto_drc_min")
    private Double diametroEtrDuctoDrcMin;
    @Column(name = "diametro_itr_ducto_ctl")
    private Double diametroItrDuctoCtl;
    @Column(name = "diametro_itr_ducto_ctl_max")
    private Double diametroItrDuctoCtlMax;
    @Column(name = "diametro_itr_ducto_ctl_min")
    private Double diametroItrDuctoCtlMin;
    @Column(name = "diametro_etr_ducto_ctl")
    private Double diametroEtrDuctoCtl;
    @Column(name = "diametro_etr_ducto_ctl_max")
    private Double diametroEtrDuctoCtlMax;
    @Column(name = "diametro_etr_ducto_ctl_min")
    private Double diametroEtrDuctoCtlMin;
    @Column(name = "diametro_itr_ducto_iqe")
    private Double diametroItrDuctoIqe;
    @Column(name = "diametro_itr_ducto_iqe_max")
    private Double diametroItrDuctoIqeMax;
    @Column(name = "diametro_itr_ducto_iqe_min")
    private Double diametroItrDuctoIqeMin;
    @Column(name = "diametro_etr_ducto_iqe")
    private Double diametroEtrDuctoIqe;
    @Column(name = "diametro_etr_ducto_iqe_max")
    private Double diametroEtrDuctoIqeMax;
    @Column(name = "diametro_etr_ducto_iqe_min")
    private Double diametroEtrDuctoIqeMin;
    @Column(name = "ancho_manga")
    private Double anchoManga;
    @Column(name = "ancho_manga_max")
    private Double anchoMangaMax;
    @Column(name = "ancho_manga_min")
    private Double anchoMangaMin;
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

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
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

    public Double getPrmSelladoBocas() {
        return prmSelladoBocas;
    }

    public void setPrmSelladoBocas(Double prmSelladoBocas) {
        this.prmSelladoBocas = prmSelladoBocas;
    }

    public Double getPrmSelladoBocasMax() {
        return prmSelladoBocasMax;
    }

    public void setPrmSelladoBocasMax(Double prmSelladoBocasMax) {
        this.prmSelladoBocasMax = prmSelladoBocasMax;
    }

    public Double getPrmSelladoBocasMin() {
        return prmSelladoBocasMin;
    }

    public void setPrmSelladoBocasMin(Double prmSelladoBocasMin) {
        this.prmSelladoBocasMin = prmSelladoBocasMin;
    }

    public Double getPrmSelladoBocasAlt() {
        return prmSelladoBocasAlt;
    }

    public void setPrmSelladoBocasAlt(Double prmSelladoBocasAlt) {
        this.prmSelladoBocasAlt = prmSelladoBocasAlt;
    }

    public Double getPrmSelladoBocasMaxAlt() {
        return prmSelladoBocasMaxAlt;
    }

    public void setPrmSelladoBocasMaxAlt(Double prmSelladoBocasMaxAlt) {
        this.prmSelladoBocasMaxAlt = prmSelladoBocasMaxAlt;
    }

    public Double getPrmSelladoBocasMinAlt() {
        return prmSelladoBocasMinAlt;
    }

    public void setPrmSelladoBocasMinAlt(Double prmSelladoBocasMinAlt) {
        this.prmSelladoBocasMinAlt = prmSelladoBocasMinAlt;
    }

    public Double getPrmSelladoColas() {
        return prmSelladoColas;
    }

    public void setPrmSelladoColas(Double prmSelladoColas) {
        this.prmSelladoColas = prmSelladoColas;
    }

    public Double getPrmSelladoColasMax() {
        return prmSelladoColasMax;
    }

    public void setPrmSelladoColasMax(Double prmSelladoColasMax) {
        this.prmSelladoColasMax = prmSelladoColasMax;
    }

    public Double getPrmSelladoColasMin() {
        return prmSelladoColasMin;
    }

    public void setPrmSelladoColasMin(Double prmSelladoColasMin) {
        this.prmSelladoColasMin = prmSelladoColasMin;
    }

    public Double getPrmSelladoColasAlt() {
        return prmSelladoColasAlt;
    }

    public void setPrmSelladoColasAlt(Double prmSelladoColasAlt) {
        this.prmSelladoColasAlt = prmSelladoColasAlt;
    }

    public Double getPrmSelladoColasMaxAlt() {
        return prmSelladoColasMaxAlt;
    }

    public void setPrmSelladoColasMaxAlt(Double prmSelladoColasMaxAlt) {
        this.prmSelladoColasMaxAlt = prmSelladoColasMaxAlt;
    }

    public Double getPrmSelladoColasMinAlt() {
        return prmSelladoColasMinAlt;
    }

    public void setPrmSelladoColasMinAlt(Double prmSelladoColasMinAlt) {
        this.prmSelladoColasMinAlt = prmSelladoColasMinAlt;
    }

    public Double getLongCuerpoSellado() {
        return longCuerpoSellado;
    }

    public void setLongCuerpoSellado(Double longCuerpoSellado) {
        this.longCuerpoSellado = longCuerpoSellado;
    }

    public Double getLongCuerpoSelladoMax() {
        return longCuerpoSelladoMax;
    }

    public void setLongCuerpoSelladoMax(Double longCuerpoSelladoMax) {
        this.longCuerpoSelladoMax = longCuerpoSelladoMax;
    }

    public Double getLongCuerpoSelladoMin() {
        return longCuerpoSelladoMin;
    }

    public void setLongCuerpoSelladoMin(Double longCuerpoSelladoMin) {
        this.longCuerpoSelladoMin = longCuerpoSelladoMin;
    }

    public Double getDuctoDrcLong() {
        return ductoDrcLong;
    }

    public void setDuctoDrcLong(Double ductoDrcLong) {
        this.ductoDrcLong = ductoDrcLong;
    }

    public Double getDuctoDrcLongMax() {
        return ductoDrcLongMax;
    }

    public void setDuctoDrcLongMax(Double ductoDrcLongMax) {
        this.ductoDrcLongMax = ductoDrcLongMax;
    }

    public Double getDuctoDrcLongMin() {
        return ductoDrcLongMin;
    }

    public void setDuctoDrcLongMin(Double ductoDrcLongMin) {
        this.ductoDrcLongMin = ductoDrcLongMin;
    }

    public Double getDuctoCtlLong() {
        return ductoCtlLong;
    }

    public void setDuctoCtlLong(Double ductoCtlLong) {
        this.ductoCtlLong = ductoCtlLong;
    }

    public Double getDuctoCtlLongMax() {
        return ductoCtlLongMax;
    }

    public void setDuctoCtlLongMax(Double ductoCtlLongMax) {
        this.ductoCtlLongMax = ductoCtlLongMax;
    }

    public Double getDuctoCtlLongMin() {
        return ductoCtlLongMin;
    }

    public void setDuctoCtlLongMin(Double ductoCtlLongMin) {
        this.ductoCtlLongMin = ductoCtlLongMin;
    }

    public Double getDuctoIqeLong() {
        return ductoIqeLong;
    }

    public void setDuctoIqeLong(Double ductoIqeLong) {
        this.ductoIqeLong = ductoIqeLong;
    }

    public Double getDuctoIqeLongMax() {
        return ductoIqeLongMax;
    }

    public void setDuctoIqeLongMax(Double ductoIqeLongMax) {
        this.ductoIqeLongMax = ductoIqeLongMax;
    }

    public Double getDuctoIqeLongMin() {
        return ductoIqeLongMin;
    }

    public void setDuctoIqeLongMin(Double ductoIqeLongMin) {
        this.ductoIqeLongMin = ductoIqeLongMin;
    }

    public Double getDiametroItrDuctoDrc() {
        return diametroItrDuctoDrc;
    }

    public void setDiametroItrDuctoDrc(Double diametroItrDuctoDrc) {
        this.diametroItrDuctoDrc = diametroItrDuctoDrc;
    }

    public Double getDiametroItrDuctoDrcMax() {
        return diametroItrDuctoDrcMax;
    }

    public void setDiametroItrDuctoDrcMax(Double diametroItrDuctoDrcMax) {
        this.diametroItrDuctoDrcMax = diametroItrDuctoDrcMax;
    }

    public Double getDiametroItrDuctoDrcMin() {
        return diametroItrDuctoDrcMin;
    }

    public void setDiametroItrDuctoDrcMin(Double diametroItrDuctoDrcMin) {
        this.diametroItrDuctoDrcMin = diametroItrDuctoDrcMin;
    }

    public Double getDiametroEtrDuctoDrc() {
        return diametroEtrDuctoDrc;
    }

    public void setDiametroEtrDuctoDrc(Double diametroEtrDuctoDrc) {
        this.diametroEtrDuctoDrc = diametroEtrDuctoDrc;
    }

    public Double getDiametroEtrDuctoDrcMax() {
        return diametroEtrDuctoDrcMax;
    }

    public void setDiametroEtrDuctoDrcMax(Double diametroEtrDuctoDrcMax) {
        this.diametroEtrDuctoDrcMax = diametroEtrDuctoDrcMax;
    }

    public Double getDiametroEtrDuctoDrcMin() {
        return diametroEtrDuctoDrcMin;
    }

    public void setDiametroEtrDuctoDrcMin(Double diametroEtrDuctoDrcMin) {
        this.diametroEtrDuctoDrcMin = diametroEtrDuctoDrcMin;
    }

    public Double getDiametroItrDuctoCtl() {
        return diametroItrDuctoCtl;
    }

    public void setDiametroItrDuctoCtl(Double diametroItrDuctoCtl) {
        this.diametroItrDuctoCtl = diametroItrDuctoCtl;
    }

    public Double getDiametroItrDuctoCtlMax() {
        return diametroItrDuctoCtlMax;
    }

    public void setDiametroItrDuctoCtlMax(Double diametroItrDuctoCtlMax) {
        this.diametroItrDuctoCtlMax = diametroItrDuctoCtlMax;
    }

    public Double getDiametroItrDuctoCtlMin() {
        return diametroItrDuctoCtlMin;
    }

    public void setDiametroItrDuctoCtlMin(Double diametroItrDuctoCtlMin) {
        this.diametroItrDuctoCtlMin = diametroItrDuctoCtlMin;
    }

    public Double getDiametroEtrDuctoCtl() {
        return diametroEtrDuctoCtl;
    }

    public void setDiametroEtrDuctoCtl(Double diametroEtrDuctoCtl) {
        this.diametroEtrDuctoCtl = diametroEtrDuctoCtl;
    }

    public Double getDiametroEtrDuctoCtlMax() {
        return diametroEtrDuctoCtlMax;
    }

    public void setDiametroEtrDuctoCtlMax(Double diametroEtrDuctoCtlMax) {
        this.diametroEtrDuctoCtlMax = diametroEtrDuctoCtlMax;
    }

    public Double getDiametroEtrDuctoCtlMin() {
        return diametroEtrDuctoCtlMin;
    }

    public void setDiametroEtrDuctoCtlMin(Double diametroEtrDuctoCtlMin) {
        this.diametroEtrDuctoCtlMin = diametroEtrDuctoCtlMin;
    }

    public Double getDiametroItrDuctoIqe() {
        return diametroItrDuctoIqe;
    }

    public void setDiametroItrDuctoIqe(Double diametroItrDuctoIqe) {
        this.diametroItrDuctoIqe = diametroItrDuctoIqe;
    }

    public Double getDiametroItrDuctoIqeMax() {
        return diametroItrDuctoIqeMax;
    }

    public void setDiametroItrDuctoIqeMax(Double diametroItrDuctoIqeMax) {
        this.diametroItrDuctoIqeMax = diametroItrDuctoIqeMax;
    }

    public Double getDiametroItrDuctoIqeMin() {
        return diametroItrDuctoIqeMin;
    }

    public void setDiametroItrDuctoIqeMin(Double diametroItrDuctoIqeMin) {
        this.diametroItrDuctoIqeMin = diametroItrDuctoIqeMin;
    }

    public Double getDiametroEtrDuctoIqe() {
        return diametroEtrDuctoIqe;
    }

    public void setDiametroEtrDuctoIqe(Double diametroEtrDuctoIqe) {
        this.diametroEtrDuctoIqe = diametroEtrDuctoIqe;
    }

    public Double getDiametroEtrDuctoIqeMax() {
        return diametroEtrDuctoIqeMax;
    }

    public void setDiametroEtrDuctoIqeMax(Double diametroEtrDuctoIqeMax) {
        this.diametroEtrDuctoIqeMax = diametroEtrDuctoIqeMax;
    }

    public Double getDiametroEtrDuctoIqeMin() {
        return diametroEtrDuctoIqeMin;
    }

    public void setDiametroEtrDuctoIqeMin(Double diametroEtrDuctoIqeMin) {
        this.diametroEtrDuctoIqeMin = diametroEtrDuctoIqeMin;
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

    public Double getAnchoVentana() {
        return anchoVentana;
    }

    public void setAnchoVentana(Double anchoVentana) {
        this.anchoVentana = anchoVentana;
    }

    public Double getAnchoVentanaMax() {
        return anchoVentanaMax;
    }

    public void setAnchoVentanaMax(Double anchoVentanaMax) {
        this.anchoVentanaMax = anchoVentanaMax;
    }

    public Double getAnchoVentanaMin() {
        return anchoVentanaMin;
    }

    public void setAnchoVentanaMin(Double anchoVentanaMin) {
        this.anchoVentanaMin = anchoVentanaMin;
    }

    public Double getDuctoBicapaEspesorItn() {
        return ductoBicapaEspesorItn;
    }

    public void setDuctoBicapaEspesorItn(Double ductoBicapaEspesorItn) {
        this.ductoBicapaEspesorItn = ductoBicapaEspesorItn;
    }

    public Double getDuctoBicapaEspesorItnMax() {
        return ductoBicapaEspesorItnMax;
    }

    public void setDuctoBicapaEspesorItnMax(Double ductoBicapaEspesorItnMax) {
        this.ductoBicapaEspesorItnMax = ductoBicapaEspesorItnMax;
    }

    public Double getDuctoBicapaEspesorItnMin() {
        return ductoBicapaEspesorItnMin;
    }

    public void setDuctoBicapaEspesorItnMin(Double ductoBicapaEspesorItnMin) {
        this.ductoBicapaEspesorItnMin = ductoBicapaEspesorItnMin;
    }

    public Double getDuctoBicapaEspesorEtn() {
        return ductoBicapaEspesorEtn;
    }

    public void setDuctoBicapaEspesorEtn(Double ductoBicapaEspesorEtn) {
        this.ductoBicapaEspesorEtn = ductoBicapaEspesorEtn;
    }

    public Double getDuctoBicapaEspesorEtnMax() {
        return ductoBicapaEspesorEtnMax;
    }

    public void setDuctoBicapaEspesorEtnMax(Double ductoBicapaEspesorEtnMax) {
        this.ductoBicapaEspesorEtnMax = ductoBicapaEspesorEtnMax;
    }

    public Double getDuctoBicapaEspesorEtnMin() {
        return ductoBicapaEspesorEtnMin;
    }

    public void setDuctoBicapaEspesorEtnMin(Double ductoBicapaEspesorEtnMin) {
        this.ductoBicapaEspesorEtnMin = ductoBicapaEspesorEtnMin;
    }

    public String getFtEva() {
        return ftEva;
    }

    public void setFtEva(String ftEva) {
        this.ftEva = ftEva;
    }

}
