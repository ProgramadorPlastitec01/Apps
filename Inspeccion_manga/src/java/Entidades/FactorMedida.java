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
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "factor_medida")
@NamedQueries({
    @NamedQuery(name = "FactorMedida.findAll", query = "SELECT f FROM FactorMedida f"),
    @NamedQuery(name = "FactorMedida.findByIdFactorMedida", query = "SELECT f FROM FactorMedida f WHERE f.idFactorMedida = :idFactorMedida"),
    @NamedQuery(name = "FactorMedida.findByFactorMedida", query = "SELECT f FROM FactorMedida f WHERE f.factorMedida = :factorMedida"),
    @NamedQuery(name = "FactorMedida.findByPromedioSensor", query = "SELECT f FROM FactorMedida f WHERE f.promedioSensor = :promedioSensor"),
    @NamedQuery(name = "FactorMedida.findByPromedioMicrometro", query = "SELECT f FROM FactorMedida f WHERE f.promedioMicrometro = :promedioMicrometro"),
    @NamedQuery(name = "FactorMedida.findByPromedioDiferencia", query = "SELECT f FROM FactorMedida f WHERE f.promedioDiferencia = :promedioDiferencia"),
    @NamedQuery(name = "FactorMedida.findByUsuarioRegistro", query = "SELECT f FROM FactorMedida f WHERE f.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "FactorMedida.findByFechaRegistro", query = "SELECT f FROM FactorMedida f WHERE f.fechaRegistro = :fechaRegistro")})
public class FactorMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_factor_medida")
    private Integer idFactorMedida;
    @Column(name = "factor_medida")
    private Double factorMedida;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "promedio_sensor")
    private String promedioSensor;
    @Column(name = "promedio_micrometro")
    private String promedioMicrometro;
    @Column(name = "promedio_diferencia")
    private String promedioDiferencia;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro registro;

    public FactorMedida() {
    }

    public FactorMedida(Integer idFactorMedida) {
        this.idFactorMedida = idFactorMedida;
    }

    public Integer getIdFactorMedida() {
        return idFactorMedida;
    }

    public void setIdFactorMedida(Integer idFactorMedida) {
        this.idFactorMedida = idFactorMedida;
    }

    public Double getFactorMedida() {
        return factorMedida;
    }

    public void setFactorMedida(Double factorMedida) {
        this.factorMedida = factorMedida;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getPromedioSensor() {
        return promedioSensor;
    }

    public void setPromedioSensor(String promedioSensor) {
        this.promedioSensor = promedioSensor;
    }

    public String getPromedioMicrometro() {
        return promedioMicrometro;
    }

    public void setPromedioMicrometro(String promedioMicrometro) {
        this.promedioMicrometro = promedioMicrometro;
    }

    public String getPromedioDiferencia() {
        return promedioDiferencia;
    }

    public void setPromedioDiferencia(String promedioDiferencia) {
        this.promedioDiferencia = promedioDiferencia;
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

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFactorMedida != null ? idFactorMedida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FactorMedida)) {
            return false;
        }
        FactorMedida other = (FactorMedida) object;
        if ((this.idFactorMedida == null && other.idFactorMedida != null) || (this.idFactorMedida != null && !this.idFactorMedida.equals(other.idFactorMedida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.FactorMedida[idFactorMedida=" + idFactorMedida + "]";
    }

}
