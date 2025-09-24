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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Programador.TI1
 */
@Entity
@Table(name = "control_interno_bobina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlInternoBobina.findAll", query = "SELECT c FROM ControlInternoBobina c")
    , @NamedQuery(name = "ControlInternoBobina.findByIdControl", query = "SELECT c FROM ControlInternoBobina c WHERE c.idControl = :idControl")
    , @NamedQuery(name = "ControlInternoBobina.findByTurnoHora", query = "SELECT c FROM ControlInternoBobina c WHERE c.turnoHora = :turnoHora")
    , @NamedQuery(name = "ControlInternoBobina.findByRollo", query = "SELECT c FROM ControlInternoBobina c WHERE c.rollo = :rollo")
    , @NamedQuery(name = "ControlInternoBobina.findByDiametroMedida1", query = "SELECT c FROM ControlInternoBobina c WHERE c.diametroMedida1 = :diametroMedida1")
    , @NamedQuery(name = "ControlInternoBobina.findByDiametroMedida2", query = "SELECT c FROM ControlInternoBobina c WHERE c.diametroMedida2 = :diametroMedida2")
    , @NamedQuery(name = "ControlInternoBobina.findByCodigoGalga", query = "SELECT c FROM ControlInternoBobina c WHERE c.codigoGalga = :codigoGalga")
    , @NamedQuery(name = "ControlInternoBobina.findByCodigoTambor", query = "SELECT c FROM ControlInternoBobina c WHERE c.codigoTambor = :codigoTambor")
    , @NamedQuery(name = "ControlInternoBobina.findByConcepto", query = "SELECT c FROM ControlInternoBobina c WHERE c.concepto = :concepto")
    , @NamedQuery(name = "ControlInternoBobina.findByUsuarioRegistros", query = "SELECT c FROM ControlInternoBobina c WHERE c.usuarioRegistros = :usuarioRegistros")
    , @NamedQuery(name = "ControlInternoBobina.findByFechaRegistro", query = "SELECT c FROM ControlInternoBobina c WHERE c.fechaRegistro = :fechaRegistro")})
public class ControlInternoBobina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_control")
    private Integer idControl;
    @Column(name = "turno_hora")
    private Integer turnoHora;
    @Column(name = "rollo")
    private Integer rollo;
    @Column(name = "diametro_medida_1")
    private Integer diametroMedida1;
    @Column(name = "diametro_medida_2")
    private Integer diametroMedida2;
    @Column(name = "codigo_galga")
    private Integer codigoGalga;
    @Column(name = "codigo_tambor")
    private String codigoTambor;
    @Column(name = "concepto")
    private Integer concepto;
    @Column(name = "usuario_registros")
    private String usuarioRegistros;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro idRegistro;

    public ControlInternoBobina() {
    }

    public ControlInternoBobina(Integer idControl) {
        this.idControl = idControl;
    }

    public Integer getIdControl() {
        return idControl;
    }

    public void setIdControl(Integer idControl) {
        this.idControl = idControl;
    }

    public Integer getTurnoHora() {
        return turnoHora;
    }

    public void setTurnoHora(Integer turnoHora) {
        this.turnoHora = turnoHora;
    }

    public Integer getRollo() {
        return rollo;
    }

    public void setRollo(Integer rollo) {
        this.rollo = rollo;
    }

    public Integer getDiametroMedida1() {
        return diametroMedida1;
    }

    public void setDiametroMedida1(Integer diametroMedida1) {
        this.diametroMedida1 = diametroMedida1;
    }

    public Integer getDiametroMedida2() {
        return diametroMedida2;
    }

    public void setDiametroMedida2(Integer diametroMedida2) {
        this.diametroMedida2 = diametroMedida2;
    }

    public Integer getCodigoGalga() {
        return codigoGalga;
    }

    public void setCodigoGalga(Integer codigoGalga) {
        this.codigoGalga = codigoGalga;
    }

    public String getCodigoTambor() {
        return codigoTambor;
    }

    public void setCodigoTambor(String codigoTambor) {
        this.codigoTambor = codigoTambor;
    }

    public Integer getConcepto() {
        return concepto;
    }

    public void setConcepto(Integer concepto) {
        this.concepto = concepto;
    }

    public String getUsuarioRegistros() {
        return usuarioRegistros;
    }

    public void setUsuarioRegistros(String usuarioRegistros) {
        this.usuarioRegistros = usuarioRegistros;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Registro getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Registro idRegistro) {
        this.idRegistro = idRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idControl != null ? idControl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlInternoBobina)) {
            return false;
        }
        ControlInternoBobina other = (ControlInternoBobina) object;
        if ((this.idControl == null && other.idControl != null) || (this.idControl != null && !this.idControl.equals(other.idControl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ControlInternoBobina[ idControl=" + idControl + " ]";
    }
    
}
