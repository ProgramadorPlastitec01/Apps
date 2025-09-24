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
@Table(name = "rollo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rollo.findAll", query = "SELECT r FROM Rollo r")
    , @NamedQuery(name = "Rollo.findByIdRollo", query = "SELECT r FROM Rollo r WHERE r.idRollo = :idRollo")
    , @NamedQuery(name = "Rollo.findByNumeroRollo", query = "SELECT r FROM Rollo r WHERE r.numeroRollo = :numeroRollo")
    , @NamedQuery(name = "Rollo.findByDiametroInterno", query = "SELECT r FROM Rollo r WHERE r.diametroInterno = :diametroInterno")
    , @NamedQuery(name = "Rollo.findByDiametroExterno", query = "SELECT r FROM Rollo r WHERE r.diametroExterno = :diametroExterno")
    , @NamedQuery(name = "Rollo.findByEspesorPared1", query = "SELECT r FROM Rollo r WHERE r.espesorPared1 = :espesorPared1")
    , @NamedQuery(name = "Rollo.findByEspesorPared2", query = "SELECT r FROM Rollo r WHERE r.espesorPared2 = :espesorPared2")
    , @NamedQuery(name = "Rollo.findByEspesorPared3", query = "SELECT r FROM Rollo r WHERE r.espesorPared3 = :espesorPared3")
    , @NamedQuery(name = "Rollo.findByEspesorPared4", query = "SELECT r FROM Rollo r WHERE r.espesorPared4 = :espesorPared4")
    , @NamedQuery(name = "Rollo.findByPresionInyectada", query = "SELECT r FROM Rollo r WHERE r.presionInyectada = :presionInyectada")
    , @NamedQuery(name = "Rollo.findByPesoRollo", query = "SELECT r FROM Rollo r WHERE r.pesoRollo = :pesoRollo")
    , @NamedQuery(name = "Rollo.findByRugosidad1", query = "SELECT r FROM Rollo r WHERE r.rugosidad1 = :rugosidad1")
    , @NamedQuery(name = "Rollo.findByRugosidad2", query = "SELECT r FROM Rollo r WHERE r.rugosidad2 = :rugosidad2")
    , @NamedQuery(name = "Rollo.findByRugosidad3", query = "SELECT r FROM Rollo r WHERE r.rugosidad3 = :rugosidad3")
    , @NamedQuery(name = "Rollo.findByRugosidad4", query = "SELECT r FROM Rollo r WHERE r.rugosidad4 = :rugosidad4")
    , @NamedQuery(name = "Rollo.findByInspeccionVisual", query = "SELECT r FROM Rollo r WHERE r.inspeccionVisual = :inspeccionVisual")
    , @NamedQuery(name = "Rollo.findByUsuarioRegistro", query = "SELECT r FROM Rollo r WHERE r.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Rollo.findByFechaRegistro", query = "SELECT r FROM Rollo r WHERE r.fechaRegistro = :fechaRegistro")})
public class Rollo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_rollo")
    private Integer idRollo;
    @Column(name = "numero_rollo")
    private Integer numeroRollo;
    @Column(name = "diametro_interno")
    private Integer diametroInterno;
    @Column(name = "diametro_externo")
    private Integer diametroExterno;
    @Column(name = "espesor_pared_1")
    private Integer espesorPared1;
    @Column(name = "espesor_pared_2")
    private Integer espesorPared2;
    @Column(name = "espesor_pared_3")
    private Integer espesorPared3;
    @Column(name = "espesor_pared_4")
    private Integer espesorPared4;
    @Column(name = "presion_inyectada")
    private Integer presionInyectada;
    @Column(name = "peso_rollo")
    private Integer pesoRollo;
    @Column(name = "rugosidad_1")
    private Integer rugosidad1;
    @Column(name = "rugosidad_2")
    private Integer rugosidad2;
    @Column(name = "rugosidad_3")
    private Integer rugosidad3;
    @Column(name = "rugosidad_4")
    private Integer rugosidad4;
    @Basic(optional = false)
    @Column(name = "inspeccion_visual")
    private int inspeccionVisual;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro idRegistro;

    public Rollo() {
    }

    public Rollo(Integer idRollo) {
        this.idRollo = idRollo;
    }

    public Rollo(Integer idRollo, int inspeccionVisual, Date fechaRegistro) {
        this.idRollo = idRollo;
        this.inspeccionVisual = inspeccionVisual;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdRollo() {
        return idRollo;
    }

    public void setIdRollo(Integer idRollo) {
        this.idRollo = idRollo;
    }

    public Integer getNumeroRollo() {
        return numeroRollo;
    }

    public void setNumeroRollo(Integer numeroRollo) {
        this.numeroRollo = numeroRollo;
    }

    public Integer getDiametroInterno() {
        return diametroInterno;
    }

    public void setDiametroInterno(Integer diametroInterno) {
        this.diametroInterno = diametroInterno;
    }

    public Integer getDiametroExterno() {
        return diametroExterno;
    }

    public void setDiametroExterno(Integer diametroExterno) {
        this.diametroExterno = diametroExterno;
    }

    public Integer getEspesorPared1() {
        return espesorPared1;
    }

    public void setEspesorPared1(Integer espesorPared1) {
        this.espesorPared1 = espesorPared1;
    }

    public Integer getEspesorPared2() {
        return espesorPared2;
    }

    public void setEspesorPared2(Integer espesorPared2) {
        this.espesorPared2 = espesorPared2;
    }

    public Integer getEspesorPared3() {
        return espesorPared3;
    }

    public void setEspesorPared3(Integer espesorPared3) {
        this.espesorPared3 = espesorPared3;
    }

    public Integer getEspesorPared4() {
        return espesorPared4;
    }

    public void setEspesorPared4(Integer espesorPared4) {
        this.espesorPared4 = espesorPared4;
    }

    public Integer getPresionInyectada() {
        return presionInyectada;
    }

    public void setPresionInyectada(Integer presionInyectada) {
        this.presionInyectada = presionInyectada;
    }

    public Integer getPesoRollo() {
        return pesoRollo;
    }

    public void setPesoRollo(Integer pesoRollo) {
        this.pesoRollo = pesoRollo;
    }

    public Integer getRugosidad1() {
        return rugosidad1;
    }

    public void setRugosidad1(Integer rugosidad1) {
        this.rugosidad1 = rugosidad1;
    }

    public Integer getRugosidad2() {
        return rugosidad2;
    }

    public void setRugosidad2(Integer rugosidad2) {
        this.rugosidad2 = rugosidad2;
    }

    public Integer getRugosidad3() {
        return rugosidad3;
    }

    public void setRugosidad3(Integer rugosidad3) {
        this.rugosidad3 = rugosidad3;
    }

    public Integer getRugosidad4() {
        return rugosidad4;
    }

    public void setRugosidad4(Integer rugosidad4) {
        this.rugosidad4 = rugosidad4;
    }

    public int getInspeccionVisual() {
        return inspeccionVisual;
    }

    public void setInspeccionVisual(int inspeccionVisual) {
        this.inspeccionVisual = inspeccionVisual;
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

    public Registro getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Registro idRegistro) {
        this.idRegistro = idRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRollo != null ? idRollo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rollo)) {
            return false;
        }
        Rollo other = (Rollo) object;
        if ((this.idRollo == null && other.idRollo != null) || (this.idRollo != null && !this.idRollo.equals(other.idRollo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Rollo[ idRollo=" + idRollo + " ]";
    }
    
}
