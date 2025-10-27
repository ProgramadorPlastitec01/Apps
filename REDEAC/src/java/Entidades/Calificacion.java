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
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "calificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calificacion.findAll", query = "SELECT c FROM Calificacion c")
    , @NamedQuery(name = "Calificacion.findByIdCalificacion", query = "SELECT c FROM Calificacion c WHERE c.idCalificacion = :idCalificacion")
    , @NamedQuery(name = "Calificacion.findByIdEquipo", query = "SELECT c FROM Calificacion c WHERE c.idEquipo = :idEquipo")
    , @NamedQuery(name = "Calificacion.findByIdUsuario", query = "SELECT c FROM Calificacion c WHERE c.idUsuario = :idUsuario")
    , @NamedQuery(name = "Calificacion.findByPregunta1", query = "SELECT c FROM Calificacion c WHERE c.pregunta1 = :pregunta1")
    , @NamedQuery(name = "Calificacion.findByPregunta2", query = "SELECT c FROM Calificacion c WHERE c.pregunta2 = :pregunta2")
    , @NamedQuery(name = "Calificacion.findByPregunta3", query = "SELECT c FROM Calificacion c WHERE c.pregunta3 = :pregunta3")
    , @NamedQuery(name = "Calificacion.findByPregunta4", query = "SELECT c FROM Calificacion c WHERE c.pregunta4 = :pregunta4")
    , @NamedQuery(name = "Calificacion.findByPregunta5", query = "SELECT c FROM Calificacion c WHERE c.pregunta5 = :pregunta5")
    , @NamedQuery(name = "Calificacion.findByUsuarioRegistro", query = "SELECT c FROM Calificacion c WHERE c.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Calificacion.findByFechaRegistro", query = "SELECT c FROM Calificacion c WHERE c.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Calificacion.findByCopia", query = "SELECT c FROM Calificacion c WHERE c.copia = :copia")})
public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_calificacion")
    private Integer idCalificacion;
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Lob
    @Column(name = "preguntas")
    private String preguntas;
    @Column(name = "pregunta_1")
    private Integer pregunta1;
    @Column(name = "pregunta_2")
    private Integer pregunta2;
    @Column(name = "pregunta_3")
    private Integer pregunta3;
    @Column(name = "pregunta_4")
    private Integer pregunta4;
    @Column(name = "pregunta_5")
    private Integer pregunta5;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "copia")
    private Integer copia;
    @JoinColumn(name = "id_programacion", referencedColumnName = "id_programacion")
    @ManyToOne
    private Programacion idProgramacion;

    public Calificacion() {
    }

    public Calificacion(Integer idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public Integer getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Integer idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(String preguntas) {
        this.preguntas = preguntas;
    }

    public Integer getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(Integer pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public Integer getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(Integer pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public Integer getPregunta3() {
        return pregunta3;
    }

    public void setPregunta3(Integer pregunta3) {
        this.pregunta3 = pregunta3;
    }

    public Integer getPregunta4() {
        return pregunta4;
    }

    public void setPregunta4(Integer pregunta4) {
        this.pregunta4 = pregunta4;
    }

    public Integer getPregunta5() {
        return pregunta5;
    }

    public void setPregunta5(Integer pregunta5) {
        this.pregunta5 = pregunta5;
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

    public Integer getCopia() {
        return copia;
    }

    public void setCopia(Integer copia) {
        this.copia = copia;
    }

    public Programacion getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Programacion idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCalificacion != null ? idCalificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calificacion)) {
            return false;
        }
        Calificacion other = (Calificacion) object;
        if ((this.idCalificacion == null && other.idCalificacion != null) || (this.idCalificacion != null && !this.idCalificacion.equals(other.idCalificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Calificacion[ idCalificacion=" + idCalificacion + " ]";
    }
    
}
