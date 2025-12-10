/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
 * @author a.sistemas2
 */
@Entity
@Table(name = "cabecera")
@NamedQueries({
    @NamedQuery(name = "Cabecera.findAll", query = "SELECT c FROM Cabecera c"),
    @NamedQuery(name = "Cabecera.findByIdCabecera", query = "SELECT c FROM Cabecera c WHERE c.idCabecera = :idCabecera"),
    @NamedQuery(name = "Cabecera.findByFechaIngreso", query = "SELECT c FROM Cabecera c WHERE c.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Cabecera.findByAnalisis", query = "SELECT c FROM Cabecera c WHERE c.analisis = :analisis"),
    @NamedQuery(name = "Cabecera.findByMediosCultivo", query = "SELECT c FROM Cabecera c WHERE c.mediosCultivo = :mediosCultivo"),
    @NamedQuery(name = "Cabecera.findByFechaMuestreo", query = "SELECT c FROM Cabecera c WHERE c.fechaMuestreo = :fechaMuestreo"),
    @NamedQuery(name = "Cabecera.findByFechaResultado", query = "SELECT c FROM Cabecera c WHERE c.fechaResultado = :fechaResultado"),
    @NamedQuery(name = "Cabecera.findByMuestreadoPor", query = "SELECT c FROM Cabecera c WHERE c.muestreadoPor = :muestreadoPor"),
    @NamedQuery(name = "Cabecera.findByHoraMuestreo", query = "SELECT c FROM Cabecera c WHERE c.horaMuestreo = :horaMuestreo"),
    @NamedQuery(name = "Cabecera.findByLaboratorio", query = "SELECT c FROM Cabecera c WHERE c.laboratorio = :laboratorio")})
public class Cabecera implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCabecera")
    private Integer idCabecera;
    @Basic(optional = false)
    @Column(name = "Fecha_Ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Basic(optional = false)
    @Column(name = "Analisis")
    private String analisis;
    @Basic(optional = false)
    @Column(name = "Medios_Cultivo")
    private String mediosCultivo;
    @Basic(optional = false)
    @Column(name = "Fecha_Muestreo")
    @Temporal(TemporalType.DATE)
    private Date fechaMuestreo;
    @Basic(optional = false)
    @Column(name = "Fecha_Resultado")
    @Temporal(TemporalType.DATE)
    private Date fechaResultado;
    @Basic(optional = false)
    @Lob
    @Column(name = "Especificaciones")
    private String especificaciones;
    @Basic(optional = false)
    @Column(name = "Muestreado_Por")
    private String muestreadoPor;
    @Basic(optional = false)
    @Column(name = "Hora_Muestreo")
    private String horaMuestreo;
    @Basic(optional = false)
    @Lob
    @Column(name = "Observaciones")
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "Laboratorio")
    private String laboratorio;
    @Lob
    @Column(name = "Observaciones2")
    private String observaciones2;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cabecera")
    private List<AnalisisPorArea> analisisPorAreaList;

    public Cabecera() {
    }

    public Cabecera(Integer idCabecera) {
        this.idCabecera = idCabecera;
    }

    public Cabecera(Integer idCabecera, Date fechaIngreso, String analisis, String mediosCultivo, Date fechaMuestreo, Date fechaResultado, String especificaciones, String muestreadoPor, String horaMuestreo, String observaciones, String laboratorio) {
        this.idCabecera = idCabecera;
        this.fechaIngreso = fechaIngreso;
        this.analisis = analisis;
        this.mediosCultivo = mediosCultivo;
        this.fechaMuestreo = fechaMuestreo;
        this.fechaResultado = fechaResultado;
        this.especificaciones = especificaciones;
        this.muestreadoPor = muestreadoPor;
        this.horaMuestreo = horaMuestreo;
        this.observaciones = observaciones;
        this.laboratorio = laboratorio;
    }

    public Integer getIdCabecera() {
        return idCabecera;
    }

    public void setIdCabecera(Integer idCabecera) {
        this.idCabecera = idCabecera;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getAnalisis() {
        return analisis;
    }

    public void setAnalisis(String analisis) {
        this.analisis = analisis;
    }

    public String getMediosCultivo() {
        return mediosCultivo;
    }

    public void setMediosCultivo(String mediosCultivo) {
        this.mediosCultivo = mediosCultivo;
    }

    public Date getFechaMuestreo() {
        return fechaMuestreo;
    }

    public void setFechaMuestreo(Date fechaMuestreo) {
        this.fechaMuestreo = fechaMuestreo;
    }

    public Date getFechaResultado() {
        return fechaResultado;
    }

    public void setFechaResultado(Date fechaResultado) {
        this.fechaResultado = fechaResultado;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public String getMuestreadoPor() {
        return muestreadoPor;
    }

    public void setMuestreadoPor(String muestreadoPor) {
        this.muestreadoPor = muestreadoPor;
    }

    public String getHoraMuestreo() {
        return horaMuestreo;
    }

    public void setHoraMuestreo(String horaMuestreo) {
        this.horaMuestreo = horaMuestreo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getObservaciones2() {
        return observaciones2;
    }

    public void setObservaciones2(String observaciones2) {
        this.observaciones2 = observaciones2;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<AnalisisPorArea> getAnalisisPorAreaList() {
        return analisisPorAreaList;
    }

    public void setAnalisisPorAreaList(List<AnalisisPorArea> analisisPorAreaList) {
        this.analisisPorAreaList = analisisPorAreaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCabecera != null ? idCabecera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cabecera)) {
            return false;
        }
        Cabecera other = (Cabecera) object;
        if ((this.idCabecera == null && other.idCabecera != null) || (this.idCabecera != null && !this.idCabecera.equals(other.idCabecera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Cabecera[idCabecera=" + idCabecera + "]";
    }

}
