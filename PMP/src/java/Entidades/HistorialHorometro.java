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
@Table(name = "historial_horometro")
@NamedQueries({
    @NamedQuery(name = "HistorialHorometro.findAll", query = "SELECT h FROM HistorialHorometro h"),
    @NamedQuery(name = "HistorialHorometro.findByIdHistorialHorometro", query = "SELECT h FROM HistorialHorometro h WHERE h.idHistorialHorometro = :idHistorialHorometro"),
    @NamedQuery(name = "HistorialHorometro.findByHorometroAnterior", query = "SELECT h FROM HistorialHorometro h WHERE h.horometroAnterior = :horometroAnterior"),
    @NamedQuery(name = "HistorialHorometro.findByHorometroActual", query = "SELECT h FROM HistorialHorometro h WHERE h.horometroActual = :horometroActual"),
    @NamedQuery(name = "HistorialHorometro.findByUsuarioRegistro", query = "SELECT h FROM HistorialHorometro h WHERE h.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "HistorialHorometro.findByFechaRegistro", query = "SELECT h FROM HistorialHorometro h WHERE h.fechaRegistro = :fechaRegistro")})
public class HistorialHorometro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_historial_horometro")
    private Integer idHistorialHorometro;
    @Basic(optional = false)
    @Column(name = "horometro_anterior")
    private int horometroAnterior;
    @Basic(optional = false)
    @Column(name = "horometro_actual")
    private int horometroActual;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo")
    @ManyToOne(optional = false)
    private Equipo equipo;

    public HistorialHorometro() {
    }

    public HistorialHorometro(Integer idHistorialHorometro) {
        this.idHistorialHorometro = idHistorialHorometro;
    }

    public HistorialHorometro(Integer idHistorialHorometro, int horometroAnterior, int horometroActual, String usuarioRegistro, Date fechaRegistro) {
        this.idHistorialHorometro = idHistorialHorometro;
        this.horometroAnterior = horometroAnterior;
        this.horometroActual = horometroActual;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdHistorialHorometro() {
        return idHistorialHorometro;
    }

    public void setIdHistorialHorometro(Integer idHistorialHorometro) {
        this.idHistorialHorometro = idHistorialHorometro;
    }

    public int getHorometroAnterior() {
        return horometroAnterior;
    }

    public void setHorometroAnterior(int horometroAnterior) {
        this.horometroAnterior = horometroAnterior;
    }

    public int getHorometroActual() {
        return horometroActual;
    }

    public void setHorometroActual(int horometroActual) {
        this.horometroActual = horometroActual;
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

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistorialHorometro != null ? idHistorialHorometro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialHorometro)) {
            return false;
        }
        HistorialHorometro other = (HistorialHorometro) object;
        if ((this.idHistorialHorometro == null && other.idHistorialHorometro != null) || (this.idHistorialHorometro != null && !this.idHistorialHorometro.equals(other.idHistorialHorometro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.HistorialHorometro[idHistorialHorometro=" + idHistorialHorometro + "]";
    }

}
