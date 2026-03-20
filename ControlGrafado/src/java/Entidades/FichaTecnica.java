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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author prog.sistemas2
 */
@Entity
@Table(name = "ficha_tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FichaTecnica.findAll", query = "SELECT f FROM FichaTecnica f"),
    @NamedQuery(name = "FichaTecnica.findByIdFicha", query = "SELECT f FROM FichaTecnica f WHERE f.idFicha = :idFicha"),
    @NamedQuery(name = "FichaTecnica.findByFecha", query = "SELECT f FROM FichaTecnica f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "FichaTecnica.findByCodigoFicha", query = "SELECT f FROM FichaTecnica f WHERE f.codigoFicha = :codigoFicha"),
    @NamedQuery(name = "FichaTecnica.findByVersion", query = "SELECT f FROM FichaTecnica f WHERE f.version = :version"),
    @NamedQuery(name = "FichaTecnica.findByCodigoProducto", query = "SELECT f FROM FichaTecnica f WHERE f.codigoProducto = :codigoProducto"),
    @NamedQuery(name = "FichaTecnica.findByNombreProducto", query = "SELECT f FROM FichaTecnica f WHERE f.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "FichaTecnica.findByY1", query = "SELECT f FROM FichaTecnica f WHERE f.y1 = :y1"),
    @NamedQuery(name = "FichaTecnica.findByX1", query = "SELECT f FROM FichaTecnica f WHERE f.x1 = :x1"),
    @NamedQuery(name = "FichaTecnica.findByY2", query = "SELECT f FROM FichaTecnica f WHERE f.y2 = :y2"),
    @NamedQuery(name = "FichaTecnica.findByX2", query = "SELECT f FROM FichaTecnica f WHERE f.x2 = :x2"),
    @NamedQuery(name = "FichaTecnica.findByX3", query = "SELECT f FROM FichaTecnica f WHERE f.x3 = :x3"),
    @NamedQuery(name = "FichaTecnica.findByDesvY1", query = "SELECT f FROM FichaTecnica f WHERE f.desvY1 = :desvY1"),
    @NamedQuery(name = "FichaTecnica.findByDesvY11", query = "SELECT f FROM FichaTecnica f WHERE f.desvY11 = :desvY11"),
    @NamedQuery(name = "FichaTecnica.findByDesvX1", query = "SELECT f FROM FichaTecnica f WHERE f.desvX1 = :desvX1"),
    @NamedQuery(name = "FichaTecnica.findByDesvX11", query = "SELECT f FROM FichaTecnica f WHERE f.desvX11 = :desvX11"),
    @NamedQuery(name = "FichaTecnica.findByDesvY2", query = "SELECT f FROM FichaTecnica f WHERE f.desvY2 = :desvY2"),
    @NamedQuery(name = "FichaTecnica.findByDesvY21", query = "SELECT f FROM FichaTecnica f WHERE f.desvY21 = :desvY21"),
    @NamedQuery(name = "FichaTecnica.findByDesvX2", query = "SELECT f FROM FichaTecnica f WHERE f.desvX2 = :desvX2"),
    @NamedQuery(name = "FichaTecnica.findByDesvX21", query = "SELECT f FROM FichaTecnica f WHERE f.desvX21 = :desvX21"),
    @NamedQuery(name = "FichaTecnica.findByDesvX3", query = "SELECT f FROM FichaTecnica f WHERE f.desvX3 = :desvX3"),
    @NamedQuery(name = "FichaTecnica.findByDesvX31", query = "SELECT f FROM FichaTecnica f WHERE f.desvX31 = :desvX31"),
    @NamedQuery(name = "FichaTecnica.findByAltura", query = "SELECT f FROM FichaTecnica f WHERE f.altura = :altura"),
    @NamedQuery(name = "FichaTecnica.findByAtr", query = "SELECT f FROM FichaTecnica f WHERE f.atr = :atr"),
    @NamedQuery(name = "FichaTecnica.findByAtr1", query = "SELECT f FROM FichaTecnica f WHERE f.atr1 = :atr1"),
    @NamedQuery(name = "FichaTecnica.findByTipo", query = "SELECT f FROM FichaTecnica f WHERE f.tipo = :tipo"),
    @NamedQuery(name = "FichaTecnica.findByEstado", query = "SELECT f FROM FichaTecnica f WHERE f.estado = :estado")})
public class FichaTecnica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ficha")
    private Integer idFicha;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "codigo_ficha")
    private String codigoFicha;
    @Column(name = "version")
    private String version;
    @Column(name = "codigo_producto")
    private String codigoProducto;
    @Column(name = "nombre_producto")
    private String nombreProducto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "y1")
    private Double y1;
    @Column(name = "x1")
    private Double x1;
    @Column(name = "y2")
    private Double y2;
    @Column(name = "x2")
    private Double x2;
    @Column(name = "x3")
    private Double x3;
    @Column(name = "desv_y1+")
    private Double desvY1;
    @Column(name = "desv_y1-")
    private Double desvY11;
    @Column(name = "desv_x1+")
    private Double desvX1;
    @Column(name = "desv_x1-")
    private Double desvX11;
    @Column(name = "desv_y2+")
    private Double desvY2;
    @Column(name = "desv_y2-")
    private Double desvY21;
    @Column(name = "desv_x2+")
    private Double desvX2;
    @Column(name = "desv_x2-")
    private Double desvX21;
    @Column(name = "desv_x3+")
    private Double desvX3;
    @Column(name = "desv_x3-")
    private Double desvX31;
    @Column(name = "altura")
    private Double altura;
    @Column(name = "atr+")
    private Double atr;
    @Column(name = "atr-")
    private Double atr1;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "estado")
    private Integer estado;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente idCliente;
    @OneToMany(mappedBy = "idFicha")
    private Collection<Orden> ordenCollection;

    public FichaTecnica() {
    }

    public FichaTecnica(Integer idFicha) {
        this.idFicha = idFicha;
    }

    public FichaTecnica(Integer idFicha, Date fecha) {
        this.idFicha = idFicha;
        this.fecha = fecha;
    }

    public Integer getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(Integer idFicha) {
        this.idFicha = idFicha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigoFicha() {
        return codigoFicha;
    }

    public void setCodigoFicha(String codigoFicha) {
        this.codigoFicha = codigoFicha;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getY2() {
        return y2;
    }

    public void setY2(Double y2) {
        this.y2 = y2;
    }

    public Double getX2() {
        return x2;
    }

    public void setX2(Double x2) {
        this.x2 = x2;
    }

    public Double getX3() {
        return x3;
    }

    public void setX3(Double x3) {
        this.x3 = x3;
    }

    public Double getDesvY1() {
        return desvY1;
    }

    public void setDesvY1(Double desvY1) {
        this.desvY1 = desvY1;
    }

    public Double getDesvY11() {
        return desvY11;
    }

    public void setDesvY11(Double desvY11) {
        this.desvY11 = desvY11;
    }

    public Double getDesvX1() {
        return desvX1;
    }

    public void setDesvX1(Double desvX1) {
        this.desvX1 = desvX1;
    }

    public Double getDesvX11() {
        return desvX11;
    }

    public void setDesvX11(Double desvX11) {
        this.desvX11 = desvX11;
    }

    public Double getDesvY2() {
        return desvY2;
    }

    public void setDesvY2(Double desvY2) {
        this.desvY2 = desvY2;
    }

    public Double getDesvY21() {
        return desvY21;
    }

    public void setDesvY21(Double desvY21) {
        this.desvY21 = desvY21;
    }

    public Double getDesvX2() {
        return desvX2;
    }

    public void setDesvX2(Double desvX2) {
        this.desvX2 = desvX2;
    }

    public Double getDesvX21() {
        return desvX21;
    }

    public void setDesvX21(Double desvX21) {
        this.desvX21 = desvX21;
    }

    public Double getDesvX3() {
        return desvX3;
    }

    public void setDesvX3(Double desvX3) {
        this.desvX3 = desvX3;
    }

    public Double getDesvX31() {
        return desvX31;
    }

    public void setDesvX31(Double desvX31) {
        this.desvX31 = desvX31;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getAtr() {
        return atr;
    }

    public void setAtr(Double atr) {
        this.atr = atr;
    }

    public Double getAtr1() {
        return atr1;
    }

    public void setAtr1(Double atr1) {
        this.atr1 = atr1;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    @XmlTransient
    public Collection<Orden> getOrdenCollection() {
        return ordenCollection;
    }

    public void setOrdenCollection(Collection<Orden> ordenCollection) {
        this.ordenCollection = ordenCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFicha != null ? idFicha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FichaTecnica)) {
            return false;
        }
        FichaTecnica other = (FichaTecnica) object;
        if ((this.idFicha == null && other.idFicha != null) || (this.idFicha != null && !this.idFicha.equals(other.idFicha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FichaTecnica[ idFicha=" + idFicha + " ]";
    }
    
}
