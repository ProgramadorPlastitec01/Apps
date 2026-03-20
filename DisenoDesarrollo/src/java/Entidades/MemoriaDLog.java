/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.Aprendiz1
 */
@Entity
@Table(name = "memoria_d_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MemoriaDLog.findAll", query = "SELECT m FROM MemoriaDLog m")
    , @NamedQuery(name = "MemoriaDLog.findByIdMemoriaDLog", query = "SELECT m FROM MemoriaDLog m WHERE m.idMemoriaDLog = :idMemoriaDLog")
    , @NamedQuery(name = "MemoriaDLog.findByFkMemoriaD", query = "SELECT m FROM MemoriaDLog m WHERE m.fkMemoriaD = :fkMemoriaD")
    , @NamedQuery(name = "MemoriaDLog.findByTipoLog", query = "SELECT m FROM MemoriaDLog m WHERE m.tipoLog = :tipoLog")
    , @NamedQuery(name = "MemoriaDLog.findByFchRegistro", query = "SELECT m FROM MemoriaDLog m WHERE m.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "MemoriaDLog.findByUsuRegistro", query = "SELECT m FROM MemoriaDLog m WHERE m.usuRegistro = :usuRegistro")
    , @NamedQuery(name = "MemoriaDLog.findByFkMemoriaC", query = "SELECT m FROM MemoriaDLog m WHERE m.fkMemoriaC = :fkMemoriaC")
    , @NamedQuery(name = "MemoriaDLog.findByCorreoActividad", query = "SELECT m FROM MemoriaDLog m WHERE m.correoActividad = :correoActividad")
    , @NamedQuery(name = "MemoriaDLog.findByUsuRespuesta", query = "SELECT m FROM MemoriaDLog m WHERE m.usuRespuesta = :usuRespuesta")
    , @NamedQuery(name = "MemoriaDLog.findByFchRespuesta", query = "SELECT m FROM MemoriaDLog m WHERE m.fchRespuesta = :fchRespuesta")
    , @NamedQuery(name = "MemoriaDLog.findByCorreoRespuesta", query = "SELECT m FROM MemoriaDLog m WHERE m.correoRespuesta = :correoRespuesta")
    , @NamedQuery(name = "MemoriaDLog.findByCorreoAutor", query = "SELECT m FROM MemoriaDLog m WHERE m.correoAutor = :correoAutor")
    , @NamedQuery(name = "MemoriaDLog.findByFechaRegistro", query = "SELECT m FROM MemoriaDLog m WHERE m.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "MemoriaDLog.findByEstado", query = "SELECT m FROM MemoriaDLog m WHERE m.estado = :estado")})
public class MemoriaDLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_memoria_d_log")
    private Integer idMemoriaDLog;
    @Basic(optional = false)
    @Column(name = "fk_memoria_d")
    private int fkMemoriaD;
    @Basic(optional = false)
    @Column(name = "tipo_log")
    private String tipoLog;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.DATE)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "fk_memoria_c")
    private Integer fkMemoriaC;
    @Lob
    @Column(name = "memoria")
    private String memoria;
    @Column(name = "correo_actividad")
    private Integer correoActividad;
    @Lob
    @Column(name = "autoridad")
    private String autoridad;
    @Lob
    @Column(name = "respuesta")
    private String respuesta;
    @Column(name = "usu_respuesta")
    private String usuRespuesta;
    @Column(name = "fch_respuesta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRespuesta;
    @Column(name = "correo_respuesta")
    private Integer correoRespuesta;
    @Column(name = "correo_autor")
    private Integer correoAutor;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "estado")
    private Integer estado;

    public MemoriaDLog() {
    }

    public MemoriaDLog(Integer idMemoriaDLog) {
        this.idMemoriaDLog = idMemoriaDLog;
    }

    public MemoriaDLog(Integer idMemoriaDLog, int fkMemoriaD, String tipoLog) {
        this.idMemoriaDLog = idMemoriaDLog;
        this.fkMemoriaD = fkMemoriaD;
        this.tipoLog = tipoLog;
    }

    public Integer getIdMemoriaDLog() {
        return idMemoriaDLog;
    }

    public void setIdMemoriaDLog(Integer idMemoriaDLog) {
        this.idMemoriaDLog = idMemoriaDLog;
    }

    public int getFkMemoriaD() {
        return fkMemoriaD;
    }

    public void setFkMemoriaD(int fkMemoriaD) {
        this.fkMemoriaD = fkMemoriaD;
    }

    public String getTipoLog() {
        return tipoLog;
    }

    public void setTipoLog(String tipoLog) {
        this.tipoLog = tipoLog;
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

    public Integer getFkMemoriaC() {
        return fkMemoriaC;
    }

    public void setFkMemoriaC(Integer fkMemoriaC) {
        this.fkMemoriaC = fkMemoriaC;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public Integer getCorreoActividad() {
        return correoActividad;
    }

    public void setCorreoActividad(Integer correoActividad) {
        this.correoActividad = correoActividad;
    }

    public String getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(String autoridad) {
        this.autoridad = autoridad;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getUsuRespuesta() {
        return usuRespuesta;
    }

    public void setUsuRespuesta(String usuRespuesta) {
        this.usuRespuesta = usuRespuesta;
    }

    public Date getFchRespuesta() {
        return fchRespuesta;
    }

    public void setFchRespuesta(Date fchRespuesta) {
        this.fchRespuesta = fchRespuesta;
    }

    public Integer getCorreoRespuesta() {
        return correoRespuesta;
    }

    public void setCorreoRespuesta(Integer correoRespuesta) {
        this.correoRespuesta = correoRespuesta;
    }

    public Integer getCorreoAutor() {
        return correoAutor;
    }

    public void setCorreoAutor(Integer correoAutor) {
        this.correoAutor = correoAutor;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMemoriaDLog != null ? idMemoriaDLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemoriaDLog)) {
            return false;
        }
        MemoriaDLog other = (MemoriaDLog) object;
        if ((this.idMemoriaDLog == null && other.idMemoriaDLog != null) || (this.idMemoriaDLog != null && !this.idMemoriaDLog.equals(other.idMemoriaDLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.MemoriaDLog[ idMemoriaDLog=" + idMemoriaDLog + " ]";
    }
    
}
