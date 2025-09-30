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
 * @author prog.sistemas1
 */
@Entity
@Table(name = "registro")
@NamedQueries({
    @NamedQuery(name = "Registro.findAll", query = "SELECT r FROM Registro r"),
    @NamedQuery(name = "Registro.findByIdRegistro", query = "SELECT r FROM Registro r WHERE r.idRegistro = :idRegistro"),
    @NamedQuery(name = "Registro.findByFechaTurno", query = "SELECT r FROM Registro r WHERE r.fechaTurno = :fechaTurno"),
    @NamedQuery(name = "Registro.findByTurnoProduccion", query = "SELECT r FROM Registro r WHERE r.turnoProduccion = :turnoProduccion"),
    @NamedQuery(name = "Registro.findByResponsablesProduccion", query = "SELECT r FROM Registro r WHERE r.responsablesProduccion = :responsablesProduccion"),
    @NamedQuery(name = "Registro.findByLoteProducto", query = "SELECT r FROM Registro r WHERE r.loteProducto = :loteProducto"),
    @NamedQuery(name = "Registro.findByLoteC", query = "SELECT r FROM Registro r WHERE r.loteC = :loteC"),
    @NamedQuery(name = "Registro.findByLoteP", query = "SELECT r FROM Registro r WHERE r.loteP = :loteP"),
    @NamedQuery(name = "Registro.findByFactorMedida", query = "SELECT r FROM Registro r WHERE r.factorMedida = :factorMedida"),
    @NamedQuery(name = "Registro.findByTurnoCalidad", query = "SELECT r FROM Registro r WHERE r.turnoCalidad = :turnoCalidad"),
    @NamedQuery(name = "Registro.findByResponsablesCalidad", query = "SELECT r FROM Registro r WHERE r.responsablesCalidad = :responsablesCalidad"),
    @NamedQuery(name = "Registro.findByDureza", query = "SELECT r FROM Registro r WHERE r.dureza = :dureza"),
    @NamedQuery(name = "Registro.findByCurvatura", query = "SELECT r FROM Registro r WHERE r.curvatura = :curvatura"),
    @NamedQuery(name = "Registro.findByPruebaFuncional", query = "SELECT r FROM Registro r WHERE r.pruebaFuncional = :pruebaFuncional"),
    @NamedQuery(name = "Registro.findByEstadoPi", query = "SELECT r FROM Registro r WHERE r.estadoPi = :estadoPi"),
    @NamedQuery(name = "Registro.findByEstadoGc", query = "SELECT r FROM Registro r WHERE r.estadoGc = :estadoGc"),
    @NamedQuery(name = "Registro.findByFechaRegistroProduccion", query = "SELECT r FROM Registro r WHERE r.fechaRegistroProduccion = :fechaRegistroProduccion"),
    @NamedQuery(name = "Registro.findByFechaRegistroCalidad", query = "SELECT r FROM Registro r WHERE r.fechaRegistroCalidad = :fechaRegistroCalidad")})
public class Registro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro")
    private Integer idRegistro;
    @Column(name = "fecha_turno")
    @Temporal(TemporalType.DATE)
    private Date fechaTurno;
    @Column(name = "turno_produccion")
    private String turnoProduccion;
    @Column(name = "responsables_produccion")
    private String responsablesProduccion;
    @Column(name = "lote_producto")
    private String loteProducto;
    @Column(name = "lote_c")
    private String loteC;
    @Column(name = "lote_p")
    private String loteP;
    @Column(name = "factor_medida")
    private String factorMedida;
    @Column(name = "turno_calidad")
    private String turnoCalidad;
    @Column(name = "responsables_calidad")
    private String responsablesCalidad;
    @Column(name = "dureza")
    private Double dureza;
    @Column(name = "curvatura")
    private Double curvatura;
    @Column(name = "prueba_funcional")
    private String pruebaFuncional;
    @Column(name = "estado_pi")
    private Integer estadoPi;
    @Column(name = "estado_gc")
    private Integer estadoGc;
    @Column(name = "fecha_registro_produccion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistroProduccion;
    @Column(name = "fecha_registro_calidad")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistroCalidad;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto producto;
    @JoinColumn(name = "id_linea", referencedColumnName = "id_linea")
    @ManyToOne
    private Linea linea;
    @OneToMany(mappedBy = "registro")
    private Collection<Rollo> rolloCollection;

    public Registro() {
    }

    public Registro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Date getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(Date fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public String getTurnoProduccion() {
        return turnoProduccion;
    }

    public void setTurnoProduccion(String turnoProduccion) {
        this.turnoProduccion = turnoProduccion;
    }

    public String getResponsablesProduccion() {
        return responsablesProduccion;
    }

    public void setResponsablesProduccion(String responsablesProduccion) {
        this.responsablesProduccion = responsablesProduccion;
    }

    public String getLoteProducto() {
        return loteProducto;
    }

    public void setLoteProducto(String loteProducto) {
        this.loteProducto = loteProducto;
    }

    public String getLoteC() {
        return loteC;
    }

    public void setLoteC(String loteC) {
        this.loteC = loteC;
    }

    public String getLoteP() {
        return loteP;
    }

    public void setLoteP(String loteP) {
        this.loteP = loteP;
    }

    public String getFactorMedida() {
        return factorMedida;
    }

    public void setFactorMedida(String factorMedida) {
        this.factorMedida = factorMedida;
    }

    public String getTurnoCalidad() {
        return turnoCalidad;
    }

    public void setTurnoCalidad(String turnoCalidad) {
        this.turnoCalidad = turnoCalidad;
    }

    public String getResponsablesCalidad() {
        return responsablesCalidad;
    }

    public void setResponsablesCalidad(String responsablesCalidad) {
        this.responsablesCalidad = responsablesCalidad;
    }

    public Double getDureza() {
        return dureza;
    }

    public void setDureza(Double dureza) {
        this.dureza = dureza;
    }

    public Double getCurvatura() {
        return curvatura;
    }

    public void setCurvatura(Double curvatura) {
        this.curvatura = curvatura;
    }

    public String getPruebaFuncional() {
        return pruebaFuncional;
    }

    public void setPruebaFuncional(String pruebaFuncional) {
        this.pruebaFuncional = pruebaFuncional;
    }

    public Integer getEstadoPi() {
        return estadoPi;
    }

    public void setEstadoPi(Integer estadoPi) {
        this.estadoPi = estadoPi;
    }

    public Integer getEstadoGc() {
        return estadoGc;
    }

    public void setEstadoGc(Integer estadoGc) {
        this.estadoGc = estadoGc;
    }

    public Date getFechaRegistroProduccion() {
        return fechaRegistroProduccion;
    }

    public void setFechaRegistroProduccion(Date fechaRegistroProduccion) {
        this.fechaRegistroProduccion = fechaRegistroProduccion;
    }

    public Date getFechaRegistroCalidad() {
        return fechaRegistroCalidad;
    }

    public void setFechaRegistroCalidad(Date fechaRegistroCalidad) {
        this.fechaRegistroCalidad = fechaRegistroCalidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }

    public Collection<Rollo> getRolloCollection() {
        return rolloCollection;
    }

    public void setRolloCollection(Collection<Rollo> rolloCollection) {
        this.rolloCollection = rolloCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistro != null ? idRegistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registro)) {
            return false;
        }
        Registro other = (Registro) object;
        if ((this.idRegistro == null && other.idRegistro != null) || (this.idRegistro != null && !this.idRegistro.equals(other.idRegistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Registro[idRegistro=" + idRegistro + "]";
    }

}
