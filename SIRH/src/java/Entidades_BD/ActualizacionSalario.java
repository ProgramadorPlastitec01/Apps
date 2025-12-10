/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "actualizacion_salario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActualizacionSalario.findAll", query = "SELECT a FROM ActualizacionSalario a"),
    @NamedQuery(name = "ActualizacionSalario.findByIdActualizacionSalario", query = "SELECT a FROM ActualizacionSalario a WHERE a.idActualizacionSalario = :idActualizacionSalario"),
    @NamedQuery(name = "ActualizacionSalario.findByFecha", query = "SELECT a FROM ActualizacionSalario a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "ActualizacionSalario.findByEstado", query = "SELECT a FROM ActualizacionSalario a WHERE a.estado = :estado"),
    @NamedQuery(name = "ActualizacionSalario.findByFechaRegisto", query = "SELECT a FROM ActualizacionSalario a WHERE a.fechaRegisto = :fechaRegisto"),
    @NamedQuery(name = "ActualizacionSalario.findByUsuarioRegistro", query = "SELECT a FROM ActualizacionSalario a WHERE a.usuarioRegistro = :usuarioRegistro")})
public class ActualizacionSalario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actualizacion_salario")
    private Integer idActualizacionSalario;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Column(name = "archivo_plano")
    private String archivoPlano;
    @Lob
    @Column(name = "concepto")
    private String concepto;
    @Lob
    @Column(name = "personal")
    private String personal;
    @Lob
    @Column(name = "old_salarios")
    private String oldSalarios;
    @Lob
    @Column(name = "new_salarios")
    private String newSalarios;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fecha_registo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegisto;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;

    public ActualizacionSalario() {
    }

    public ActualizacionSalario(Integer idActualizacionSalario) {
        this.idActualizacionSalario = idActualizacionSalario;
    }

    public Integer getIdActualizacionSalario() {
        return idActualizacionSalario;
    }

    public void setIdActualizacionSalario(Integer idActualizacionSalario) {
        this.idActualizacionSalario = idActualizacionSalario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getArchivoPlano() {
        return archivoPlano;
    }

    public void setArchivoPlano(String archivoPlano) {
        this.archivoPlano = archivoPlano;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getOldSalarios() {
        return oldSalarios;
    }

    public void setOldSalarios(String oldSalarios) {
        this.oldSalarios = oldSalarios;
    }

    public String getNewSalarios() {
        return newSalarios;
    }

    public void setNewSalarios(String newSalarios) {
        this.newSalarios = newSalarios;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaRegisto() {
        return fechaRegisto;
    }

    public void setFechaRegisto(Date fechaRegisto) {
        this.fechaRegisto = fechaRegisto;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActualizacionSalario != null ? idActualizacionSalario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActualizacionSalario)) {
            return false;
        }
        ActualizacionSalario other = (ActualizacionSalario) object;
        if ((this.idActualizacionSalario == null && other.idActualizacionSalario != null) || (this.idActualizacionSalario != null && !this.idActualizacionSalario.equals(other.idActualizacionSalario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.ActualizacionSalario[ idActualizacionSalario=" + idActualizacionSalario + " ]";
    }
    
}
