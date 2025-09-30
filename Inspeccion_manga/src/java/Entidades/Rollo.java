/*
 * To change this template, choose Tools | Templates
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
 * @author prog.sistemas1
 */
@Entity
@Table(name = "rollo")
@NamedQueries({
    @NamedQuery(name = "Rollo.findAll", query = "SELECT r FROM Rollo r"),
    @NamedQuery(name = "Rollo.findByIdRollo", query = "SELECT r FROM Rollo r WHERE r.idRollo = :idRollo"),
    @NamedQuery(name = "Rollo.findByNumero", query = "SELECT r FROM Rollo r WHERE r.numero = :numero"),
    @NamedQuery(name = "Rollo.findByEstadoCalidad", query = "SELECT r FROM Rollo r WHERE r.estadoCalidad = :estadoCalidad"),
    @NamedQuery(name = "Rollo.findByParedDobleInicio", query = "SELECT r FROM Rollo r WHERE r.paredDobleInicio = :paredDobleInicio"),
    @NamedQuery(name = "Rollo.findByParedDobleCentro", query = "SELECT r FROM Rollo r WHERE r.paredDobleCentro = :paredDobleCentro"),
    @NamedQuery(name = "Rollo.findByParedDobleFin", query = "SELECT r FROM Rollo r WHERE r.paredDobleFin = :paredDobleFin"),
    @NamedQuery(name = "Rollo.findByParedSencillaMin", query = "SELECT r FROM Rollo r WHERE r.paredSencillaMin = :paredSencillaMin"),
    @NamedQuery(name = "Rollo.findByParedSencillaMax", query = "SELECT r FROM Rollo r WHERE r.paredSencillaMax = :paredSencillaMax"),
    @NamedQuery(name = "Rollo.findByAnchoManga", query = "SELECT r FROM Rollo r WHERE r.anchoManga = :anchoManga"),
    @NamedQuery(name = "Rollo.findByAnchoBobina", query = "SELECT r FROM Rollo r WHERE r.anchoBobina = :anchoBobina"),
    @NamedQuery(name = "Rollo.findByPesoBruto", query = "SELECT r FROM Rollo r WHERE r.pesoBruto = :pesoBruto"),
    @NamedQuery(name = "Rollo.findByParticulas", query = "SELECT r FROM Rollo r WHERE r.particulas = :particulas"),
    @NamedQuery(name = "Rollo.findByPerimetroCalidad1", query = "SELECT r FROM Rollo r WHERE r.perimetroCalidad1 = :perimetroCalidad1"),
    @NamedQuery(name = "Rollo.findByPerimetroCalidad2", query = "SELECT r FROM Rollo r WHERE r.perimetroCalidad2 = :perimetroCalidad2"),
    @NamedQuery(name = "Rollo.findByPerimetroExtrusion1", query = "SELECT r FROM Rollo r WHERE r.perimetroExtrusion1 = :perimetroExtrusion1"),
    @NamedQuery(name = "Rollo.findByPerimetroExtrusion2", query = "SELECT r FROM Rollo r WHERE r.perimetroExtrusion2 = :perimetroExtrusion2"),
    @NamedQuery(name = "Rollo.findByAlgoritmo", query = "SELECT r FROM Rollo r WHERE r.algoritmo = :algoritmo"),
    @NamedQuery(name = "Rollo.findByUsuarioProduccionRollo", query = "SELECT r FROM Rollo r WHERE r.usuarioProduccionRollo = :usuarioProduccionRollo"),
    @NamedQuery(name = "Rollo.findByFechaProduccionRollo", query = "SELECT r FROM Rollo r WHERE r.fechaProduccionRollo = :fechaProduccionRollo"),
    @NamedQuery(name = "Rollo.findByUsuarioCalidadRollo", query = "SELECT r FROM Rollo r WHERE r.usuarioCalidadRollo = :usuarioCalidadRollo"),
    @NamedQuery(name = "Rollo.findByMicrometroDigital", query = "SELECT r FROM Rollo r WHERE r.micrometroDigital = :micrometroDigital"),
    @NamedQuery(name = "Rollo.findBySensorEspesor", query = "SELECT r FROM Rollo r WHERE r.sensorEspesor = :sensorEspesor"),
    @NamedQuery(name = "Rollo.findByTension", query = "SELECT r FROM Rollo r WHERE r.tension = :tension"),
    @NamedQuery(name = "Rollo.findByResumido", query = "SELECT r FROM Rollo r WHERE r.resumido = :resumido"),
    @NamedQuery(name = "Rollo.findByUsuarioCalidadSensor", query = "SELECT r FROM Rollo r WHERE r.usuarioCalidadSensor = :usuarioCalidadSensor"),
    @NamedQuery(name = "Rollo.findByFechaRegistroSensor", query = "SELECT r FROM Rollo r WHERE r.fechaRegistroSensor = :fechaRegistroSensor")})
public class Rollo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rollo")
    private Integer idRollo;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "estado_calidad")
    private String estadoCalidad;
    @Column(name = "pared_doble_inicio")
    private Double paredDobleInicio;
    @Column(name = "pared_doble_centro")
    private Double paredDobleCentro;
    @Column(name = "pared_doble_fin")
    private Double paredDobleFin;
    @Column(name = "pared_sencilla_min")
    private Double paredSencillaMin;
    @Column(name = "pared_sencilla_max")
    private Double paredSencillaMax;
    @Column(name = "ancho_manga")
    private Double anchoManga;
    @Column(name = "ancho_bobina")
    private Double anchoBobina;
    @Column(name = "peso_bruto")
    private Double pesoBruto;
    @Column(name = "particulas")
    private String particulas;
    @Column(name = "perimetro_calidad_1")
    private Double perimetroCalidad1;
    @Column(name = "perimetro_calidad_2")
    private Double perimetroCalidad2;
    @Column(name = "perimetro_extrusion_1")
    private Double perimetroExtrusion1;
    @Column(name = "perimetro_extrusion_2")
    private Double perimetroExtrusion2;
    @Column(name = "algoritmo")
    private String algoritmo;
    @Column(name = "usuario_produccion_rollo")
    private String usuarioProduccionRollo;
    @Column(name = "fecha_produccion_rollo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProduccionRollo;
    @Column(name = "usuario_calidad_rollo")
    private String usuarioCalidadRollo;
    @Column(name = "micrometro_digital")
    private Double micrometroDigital;
    @Column(name = "sensor_espesor")
    private Double sensorEspesor;
    @Column(name = "tension")
    private String tension;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "resumido")
    private Short resumido;
    @Column(name = "usuario_calidad_sensor")
    private String usuarioCalidadSensor;
    @Column(name = "fecha_registro_sensor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistroSensor;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro registro;

    public Rollo() {
    }

    public Rollo(Integer idRollo) {
        this.idRollo = idRollo;
    }

    public Integer getIdRollo() {
        return idRollo;
    }

    public void setIdRollo(Integer idRollo) {
        this.idRollo = idRollo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getEstadoCalidad() {
        return estadoCalidad;
    }

    public void setEstadoCalidad(String estadoCalidad) {
        this.estadoCalidad = estadoCalidad;
    }

    public Double getParedDobleInicio() {
        return paredDobleInicio;
    }

    public void setParedDobleInicio(Double paredDobleInicio) {
        this.paredDobleInicio = paredDobleInicio;
    }

    public Double getParedDobleCentro() {
        return paredDobleCentro;
    }

    public void setParedDobleCentro(Double paredDobleCentro) {
        this.paredDobleCentro = paredDobleCentro;
    }

    public Double getParedDobleFin() {
        return paredDobleFin;
    }

    public void setParedDobleFin(Double paredDobleFin) {
        this.paredDobleFin = paredDobleFin;
    }

    public Double getParedSencillaMin() {
        return paredSencillaMin;
    }

    public void setParedSencillaMin(Double paredSencillaMin) {
        this.paredSencillaMin = paredSencillaMin;
    }

    public Double getParedSencillaMax() {
        return paredSencillaMax;
    }

    public void setParedSencillaMax(Double paredSencillaMax) {
        this.paredSencillaMax = paredSencillaMax;
    }

    public Double getAnchoManga() {
        return anchoManga;
    }

    public void setAnchoManga(Double anchoManga) {
        this.anchoManga = anchoManga;
    }

    public Double getAnchoBobina() {
        return anchoBobina;
    }

    public void setAnchoBobina(Double anchoBobina) {
        this.anchoBobina = anchoBobina;
    }

    public Double getPesoBruto() {
        return pesoBruto;
    }

    public void setPesoBruto(Double pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    public String getParticulas() {
        return particulas;
    }

    public void setParticulas(String particulas) {
        this.particulas = particulas;
    }

    public Double getPerimetroCalidad1() {
        return perimetroCalidad1;
    }

    public void setPerimetroCalidad1(Double perimetroCalidad1) {
        this.perimetroCalidad1 = perimetroCalidad1;
    }

    public Double getPerimetroCalidad2() {
        return perimetroCalidad2;
    }

    public void setPerimetroCalidad2(Double perimetroCalidad2) {
        this.perimetroCalidad2 = perimetroCalidad2;
    }

    public Double getPerimetroExtrusion1() {
        return perimetroExtrusion1;
    }

    public void setPerimetroExtrusion1(Double perimetroExtrusion1) {
        this.perimetroExtrusion1 = perimetroExtrusion1;
    }

    public Double getPerimetroExtrusion2() {
        return perimetroExtrusion2;
    }

    public void setPerimetroExtrusion2(Double perimetroExtrusion2) {
        this.perimetroExtrusion2 = perimetroExtrusion2;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public String getUsuarioProduccionRollo() {
        return usuarioProduccionRollo;
    }

    public void setUsuarioProduccionRollo(String usuarioProduccionRollo) {
        this.usuarioProduccionRollo = usuarioProduccionRollo;
    }

    public Date getFechaProduccionRollo() {
        return fechaProduccionRollo;
    }

    public void setFechaProduccionRollo(Date fechaProduccionRollo) {
        this.fechaProduccionRollo = fechaProduccionRollo;
    }

    public String getUsuarioCalidadRollo() {
        return usuarioCalidadRollo;
    }

    public void setUsuarioCalidadRollo(String usuarioCalidadRollo) {
        this.usuarioCalidadRollo = usuarioCalidadRollo;
    }

    public Double getMicrometroDigital() {
        return micrometroDigital;
    }

    public void setMicrometroDigital(Double micrometroDigital) {
        this.micrometroDigital = micrometroDigital;
    }

    public Double getSensorEspesor() {
        return sensorEspesor;
    }

    public void setSensorEspesor(Double sensorEspesor) {
        this.sensorEspesor = sensorEspesor;
    }

    public String getTension() {
        return tension;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Short getResumido() {
        return resumido;
    }

    public void setResumido(Short resumido) {
        this.resumido = resumido;
    }

    public String getUsuarioCalidadSensor() {
        return usuarioCalidadSensor;
    }

    public void setUsuarioCalidadSensor(String usuarioCalidadSensor) {
        this.usuarioCalidadSensor = usuarioCalidadSensor;
    }

    public Date getFechaRegistroSensor() {
        return fechaRegistroSensor;
    }

    public void setFechaRegistroSensor(Date fechaRegistroSensor) {
        this.fechaRegistroSensor = fechaRegistroSensor;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRollo != null ? idRollo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rollo)) {
            return false;
        }
        Rollo other = (Rollo) object;
        if ((this.idRollo == null && other.idRollo != null) || (this.idRollo != null && !this.idRollo.equals(other.idRollo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Rollo[idRollo=" + idRollo + "]";
    }

}
