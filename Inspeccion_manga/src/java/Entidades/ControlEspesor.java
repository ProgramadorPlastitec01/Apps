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
@Table(name = "control_espesor")
@NamedQueries({
    @NamedQuery(name = "ControlEspesor.findAll", query = "SELECT c FROM ControlEspesor c"),
    @NamedQuery(name = "ControlEspesor.findByIdControlEspesor", query = "SELECT c FROM ControlEspesor c WHERE c.idControlEspesor = :idControlEspesor"),
    @NamedQuery(name = "ControlEspesor.findByToma", query = "SELECT c FROM ControlEspesor c WHERE c.toma = :toma"),
    @NamedQuery(name = "ControlEspesor.findByPs11", query = "SELECT c FROM ControlEspesor c WHERE c.ps11 = :ps11"),
    @NamedQuery(name = "ControlEspesor.findByPs12", query = "SELECT c FROM ControlEspesor c WHERE c.ps12 = :ps12"),
    @NamedQuery(name = "ControlEspesor.findByPs13", query = "SELECT c FROM ControlEspesor c WHERE c.ps13 = :ps13"),
    @NamedQuery(name = "ControlEspesor.findByPs14", query = "SELECT c FROM ControlEspesor c WHERE c.ps14 = :ps14"),
    @NamedQuery(name = "ControlEspesor.findByPs15", query = "SELECT c FROM ControlEspesor c WHERE c.ps15 = :ps15"),
    @NamedQuery(name = "ControlEspesor.findByPs16", query = "SELECT c FROM ControlEspesor c WHERE c.ps16 = :ps16"),
    @NamedQuery(name = "ControlEspesor.findByPs17", query = "SELECT c FROM ControlEspesor c WHERE c.ps17 = :ps17"),
    @NamedQuery(name = "ControlEspesor.findByPs18", query = "SELECT c FROM ControlEspesor c WHERE c.ps18 = :ps18"),
    @NamedQuery(name = "ControlEspesor.findByPs21", query = "SELECT c FROM ControlEspesor c WHERE c.ps21 = :ps21"),
    @NamedQuery(name = "ControlEspesor.findByPs22", query = "SELECT c FROM ControlEspesor c WHERE c.ps22 = :ps22"),
    @NamedQuery(name = "ControlEspesor.findByPs23", query = "SELECT c FROM ControlEspesor c WHERE c.ps23 = :ps23"),
    @NamedQuery(name = "ControlEspesor.findByPs24", query = "SELECT c FROM ControlEspesor c WHERE c.ps24 = :ps24"),
    @NamedQuery(name = "ControlEspesor.findByPs25", query = "SELECT c FROM ControlEspesor c WHERE c.ps25 = :ps25"),
    @NamedQuery(name = "ControlEspesor.findByPs26", query = "SELECT c FROM ControlEspesor c WHERE c.ps26 = :ps26"),
    @NamedQuery(name = "ControlEspesor.findByPs27", query = "SELECT c FROM ControlEspesor c WHERE c.ps27 = :ps27"),
    @NamedQuery(name = "ControlEspesor.findByPs28", query = "SELECT c FROM ControlEspesor c WHERE c.ps28 = :ps28"),
    @NamedQuery(name = "ControlEspesor.findByEquipoMedicion", query = "SELECT c FROM ControlEspesor c WHERE c.equipoMedicion = :equipoMedicion"),
    @NamedQuery(name = "ControlEspesor.findByUsuarioRegistro", query = "SELECT c FROM ControlEspesor c WHERE c.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "ControlEspesor.findByFechaRegistro", query = "SELECT c FROM ControlEspesor c WHERE c.fechaRegistro = :fechaRegistro")})
public class ControlEspesor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_control_espesor")
    private Integer idControlEspesor;
    @Basic(optional = false)
    @Column(name = "toma")
    private int toma;
    @Basic(optional = false)
    @Column(name = "ps_1_1")
    private double ps11;
    @Basic(optional = false)
    @Column(name = "ps_1_2")
    private double ps12;
    @Basic(optional = false)
    @Column(name = "ps_1_3")
    private double ps13;
    @Basic(optional = false)
    @Column(name = "ps_1_4")
    private double ps14;
    @Basic(optional = false)
    @Column(name = "ps_1_5")
    private double ps15;
    @Basic(optional = false)
    @Column(name = "ps_1_6")
    private double ps16;
    @Basic(optional = false)
    @Column(name = "ps_1_7")
    private double ps17;
    @Basic(optional = false)
    @Column(name = "ps_1_8")
    private double ps18;
    @Basic(optional = false)
    @Column(name = "ps_2_1")
    private double ps21;
    @Basic(optional = false)
    @Column(name = "ps_2_2")
    private double ps22;
    @Basic(optional = false)
    @Column(name = "ps_2_3")
    private double ps23;
    @Basic(optional = false)
    @Column(name = "ps_2_4")
    private double ps24;
    @Basic(optional = false)
    @Column(name = "ps_2_5")
    private double ps25;
    @Basic(optional = false)
    @Column(name = "ps_2_6")
    private double ps26;
    @Basic(optional = false)
    @Column(name = "ps_2_7")
    private double ps27;
    @Basic(optional = false)
    @Column(name = "ps_2_8")
    private double ps28;
    @Basic(optional = false)
    @Column(name = "equipo_medicion")
    private String equipoMedicion;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_rollo", referencedColumnName = "id_rollo")
    @ManyToOne(optional = false)
    private Rollo rollo;

    public ControlEspesor() {
    }

    public ControlEspesor(Integer idControlEspesor) {
        this.idControlEspesor = idControlEspesor;
    }

    public ControlEspesor(Integer idControlEspesor, int toma, double ps11, double ps12, double ps13, double ps14, double ps15, double ps16, double ps17, double ps18, double ps21, double ps22, double ps23, double ps24, double ps25, double ps26, double ps27, double ps28, String equipoMedicion, String usuarioRegistro, Date fechaRegistro) {
        this.idControlEspesor = idControlEspesor;
        this.toma = toma;
        this.ps11 = ps11;
        this.ps12 = ps12;
        this.ps13 = ps13;
        this.ps14 = ps14;
        this.ps15 = ps15;
        this.ps16 = ps16;
        this.ps17 = ps17;
        this.ps18 = ps18;
        this.ps21 = ps21;
        this.ps22 = ps22;
        this.ps23 = ps23;
        this.ps24 = ps24;
        this.ps25 = ps25;
        this.ps26 = ps26;
        this.ps27 = ps27;
        this.ps28 = ps28;
        this.equipoMedicion = equipoMedicion;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdControlEspesor() {
        return idControlEspesor;
    }

    public void setIdControlEspesor(Integer idControlEspesor) {
        this.idControlEspesor = idControlEspesor;
    }

    public int getToma() {
        return toma;
    }

    public void setToma(int toma) {
        this.toma = toma;
    }

    public double getPs11() {
        return ps11;
    }

    public void setPs11(double ps11) {
        this.ps11 = ps11;
    }

    public double getPs12() {
        return ps12;
    }

    public void setPs12(double ps12) {
        this.ps12 = ps12;
    }

    public double getPs13() {
        return ps13;
    }

    public void setPs13(double ps13) {
        this.ps13 = ps13;
    }

    public double getPs14() {
        return ps14;
    }

    public void setPs14(double ps14) {
        this.ps14 = ps14;
    }

    public double getPs15() {
        return ps15;
    }

    public void setPs15(double ps15) {
        this.ps15 = ps15;
    }

    public double getPs16() {
        return ps16;
    }

    public void setPs16(double ps16) {
        this.ps16 = ps16;
    }

    public double getPs17() {
        return ps17;
    }

    public void setPs17(double ps17) {
        this.ps17 = ps17;
    }

    public double getPs18() {
        return ps18;
    }

    public void setPs18(double ps18) {
        this.ps18 = ps18;
    }

    public double getPs21() {
        return ps21;
    }

    public void setPs21(double ps21) {
        this.ps21 = ps21;
    }

    public double getPs22() {
        return ps22;
    }

    public void setPs22(double ps22) {
        this.ps22 = ps22;
    }

    public double getPs23() {
        return ps23;
    }

    public void setPs23(double ps23) {
        this.ps23 = ps23;
    }

    public double getPs24() {
        return ps24;
    }

    public void setPs24(double ps24) {
        this.ps24 = ps24;
    }

    public double getPs25() {
        return ps25;
    }

    public void setPs25(double ps25) {
        this.ps25 = ps25;
    }

    public double getPs26() {
        return ps26;
    }

    public void setPs26(double ps26) {
        this.ps26 = ps26;
    }

    public double getPs27() {
        return ps27;
    }

    public void setPs27(double ps27) {
        this.ps27 = ps27;
    }

    public double getPs28() {
        return ps28;
    }

    public void setPs28(double ps28) {
        this.ps28 = ps28;
    }

    public String getEquipoMedicion() {
        return equipoMedicion;
    }

    public void setEquipoMedicion(String equipoMedicion) {
        this.equipoMedicion = equipoMedicion;
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

    public Rollo getRollo() {
        return rollo;
    }

    public void setRollo(Rollo rollo) {
        this.rollo = rollo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idControlEspesor != null ? idControlEspesor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlEspesor)) {
            return false;
        }
        ControlEspesor other = (ControlEspesor) object;
        if ((this.idControlEspesor == null && other.idControlEspesor != null) || (this.idControlEspesor != null && !this.idControlEspesor.equals(other.idControlEspesor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ControlEspesor[idControlEspesor=" + idControlEspesor + "]";
    }

}
