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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "memoria_d")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MemoriaD.findAll", query = "SELECT m FROM MemoriaD m")
    , @NamedQuery(name = "MemoriaD.findByIdMemoriaD", query = "SELECT m FROM MemoriaD m WHERE m.idMemoriaD = :idMemoriaD")
    , @NamedQuery(name = "MemoriaD.findByFchRegistro", query = "SELECT m FROM MemoriaD m WHERE m.fchRegistro = :fchRegistro")
    , @NamedQuery(name = "MemoriaD.findByUsuRegistro", query = "SELECT m FROM MemoriaD m WHERE m.usuRegistro = :usuRegistro")
    , @NamedQuery(name = "MemoriaD.findByCorreoActividad", query = "SELECT m FROM MemoriaD m WHERE m.correoActividad = :correoActividad")
    , @NamedQuery(name = "MemoriaD.findByUsuRespuesta", query = "SELECT m FROM MemoriaD m WHERE m.usuRespuesta = :usuRespuesta")
    , @NamedQuery(name = "MemoriaD.findByFchRespuesta", query = "SELECT m FROM MemoriaD m WHERE m.fchRespuesta = :fchRespuesta")
    , @NamedQuery(name = "MemoriaD.findByCorreoRespuesta", query = "SELECT m FROM MemoriaD m WHERE m.correoRespuesta = :correoRespuesta")
    , @NamedQuery(name = "MemoriaD.findByCorreoAutor", query = "SELECT m FROM MemoriaD m WHERE m.correoAutor = :correoAutor")
    , @NamedQuery(name = "MemoriaD.findByEstado", query = "SELECT m FROM MemoriaD m WHERE m.estado = :estado")})
public class MemoriaD implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_memoria_d")
    private Integer idMemoriaD;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.DATE)
    private Date fchRegistro;
    @Column(name = "usu_registro")
    private String usuRegistro;
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
    @Column(name = "estado")
    private Integer estado;
    @JoinColumn(name = "fk_memoria_c", referencedColumnName = "id_memoria_c")
    @ManyToOne
    private MemoriaC fkMemoriaC;

    public MemoriaD() {
    }

    public MemoriaD(Integer idMemoriaD) {
        this.idMemoriaD = idMemoriaD;
    }

    public Integer getIdMemoriaD() {
        return idMemoriaD;
    }

    public void setIdMemoriaD(Integer idMemoriaD) {
        this.idMemoriaD = idMemoriaD;
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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public MemoriaC getFkMemoriaC() {
        return fkMemoriaC;
    }

    public void setFkMemoriaC(MemoriaC fkMemoriaC) {
        this.fkMemoriaC = fkMemoriaC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMemoriaD != null ? idMemoriaD.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemoriaD)) {
            return false;
        }
        MemoriaD other = (MemoriaD) object;
        if ((this.idMemoriaD == null && other.idMemoriaD != null) || (this.idMemoriaD != null && !this.idMemoriaD.equals(other.idMemoriaD))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.MemoriaD[ idMemoriaD=" + idMemoriaD + " ]";
    }
    
}
