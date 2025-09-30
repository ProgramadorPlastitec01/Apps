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
@Table(name = "serial")
@NamedQueries({
    @NamedQuery(name = "Serial.findAll", query = "SELECT s FROM Serial s"),
    @NamedQuery(name = "Serial.findByIdSerial", query = "SELECT s FROM Serial s WHERE s.idSerial = :idSerial"),
    @NamedQuery(name = "Serial.findByNombre", query = "SELECT s FROM Serial s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Serial.findByTipoSerial", query = "SELECT s FROM Serial s WHERE s.tipoSerial = :tipoSerial"),
    @NamedQuery(name = "Serial.findByFechaVerificacion", query = "SELECT s FROM Serial s WHERE s.fechaVerificacion = :fechaVerificacion"),
    @NamedQuery(name = "Serial.findByFechaProximaVerificacion", query = "SELECT s FROM Serial s WHERE s.fechaProximaVerificacion = :fechaProximaVerificacion"),
    @NamedQuery(name = "Serial.findByEstado", query = "SELECT s FROM Serial s WHERE s.estado = :estado"),
    @NamedQuery(name = "Serial.findByUsuarioRegistro", query = "SELECT s FROM Serial s WHERE s.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "Serial.findByFechaRegistro", query = "SELECT s FROM Serial s WHERE s.fechaRegistro = :fechaRegistro")})
public class Serial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_serial")
    private Integer idSerial;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo_serial")
    private String tipoSerial;
    @Column(name = "fecha_verificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaVerificacion;
    @Column(name = "fecha_proxima_verificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaProximaVerificacion;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public Serial() {
    }

    public Serial(Integer idSerial) {
        this.idSerial = idSerial;
    }

    public Integer getIdSerial() {
        return idSerial;
    }

    public void setIdSerial(Integer idSerial) {
        this.idSerial = idSerial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoSerial() {
        return tipoSerial;
    }

    public void setTipoSerial(String tipoSerial) {
        this.tipoSerial = tipoSerial;
    }

    public Date getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Date fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public Date getFechaProximaVerificacion() {
        return fechaProximaVerificacion;
    }

    public void setFechaProximaVerificacion(Date fechaProximaVerificacion) {
        this.fechaProximaVerificacion = fechaProximaVerificacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSerial != null ? idSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Serial)) {
            return false;
        }
        Serial other = (Serial) object;
        if ((this.idSerial == null && other.idSerial != null) || (this.idSerial != null && !this.idSerial.equals(other.idSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Serial[idSerial=" + idSerial + "]";
    }

}
