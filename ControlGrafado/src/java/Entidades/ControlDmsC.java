/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author prog.sistemas2
 */
@Entity
@Table(name = "control_dms_c")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlDmsC.findAll", query = "SELECT c FROM ControlDmsC c"),
    @NamedQuery(name = "ControlDmsC.findByIdDimensionalC", query = "SELECT c FROM ControlDmsC c WHERE c.idDimensionalC = :idDimensionalC"),
    @NamedQuery(name = "ControlDmsC.findByFechaRegistro", query = "SELECT c FROM ControlDmsC c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ControlDmsC.findByFechaTurno", query = "SELECT c FROM ControlDmsC c WHERE c.fechaTurno = :fechaTurno"),
    @NamedQuery(name = "ControlDmsC.findByTurno", query = "SELECT c FROM ControlDmsC c WHERE c.turno = :turno"),
    @NamedQuery(name = "ControlDmsC.findByLoteGrafadoC", query = "SELECT c FROM ControlDmsC c WHERE c.loteGrafadoC = :loteGrafadoC"),
    @NamedQuery(name = "ControlDmsC.findByLoteGrafadoP", query = "SELECT c FROM ControlDmsC c WHERE c.loteGrafadoP = :loteGrafadoP"),
    @NamedQuery(name = "ControlDmsC.findByLotePistonC", query = "SELECT c FROM ControlDmsC c WHERE c.lotePistonC = :lotePistonC"),
    @NamedQuery(name = "ControlDmsC.findByLotePistonP", query = "SELECT c FROM ControlDmsC c WHERE c.lotePistonP = :lotePistonP"),
    @NamedQuery(name = "ControlDmsC.findByLoteEnsamble", query = "SELECT c FROM ControlDmsC c WHERE c.loteEnsamble = :loteEnsamble"),
    @NamedQuery(name = "ControlDmsC.findByPrueba", query = "SELECT c FROM ControlDmsC c WHERE c.prueba = :prueba"),
    @NamedQuery(name = "ControlDmsC.findByEstado", query = "SELECT c FROM ControlDmsC c WHERE c.estado = :estado"),
    @NamedQuery(name = "ControlDmsC.findByTipo", query = "SELECT c FROM ControlDmsC c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "ControlDmsC.findByEst1", query = "SELECT c FROM ControlDmsC c WHERE c.est1 = :est1"),
    @NamedQuery(name = "ControlDmsC.findByEst2", query = "SELECT c FROM ControlDmsC c WHERE c.est2 = :est2"),
    @NamedQuery(name = "ControlDmsC.findByEst3", query = "SELECT c FROM ControlDmsC c WHERE c.est3 = :est3"),
    @NamedQuery(name = "ControlDmsC.findByEst4", query = "SELECT c FROM ControlDmsC c WHERE c.est4 = :est4"),
    @NamedQuery(name = "ControlDmsC.findByEst5", query = "SELECT c FROM ControlDmsC c WHERE c.est5 = :est5"),
    @NamedQuery(name = "ControlDmsC.findByEst6", query = "SELECT c FROM ControlDmsC c WHERE c.est6 = :est6"),
    @NamedQuery(name = "ControlDmsC.findByMolde", query = "SELECT c FROM ControlDmsC c WHERE c.molde = :molde"),
    @NamedQuery(name = "ControlDmsC.findByConsecutivo", query = "SELECT c FROM ControlDmsC c WHERE c.consecutivo = :consecutivo"),
    @NamedQuery(name = "ControlDmsC.findByCalidad", query = "SELECT c FROM ControlDmsC c WHERE c.calidad = :calidad"),
    @NamedQuery(name = "ControlDmsC.findByResumen", query = "SELECT c FROM ControlDmsC c WHERE c.resumen = :resumen"),
    @NamedQuery(name = "ControlDmsC.findByEst7", query = "SELECT c FROM ControlDmsC c WHERE c.est7 = :est7"),
    @NamedQuery(name = "ControlDmsC.findByEst8", query = "SELECT c FROM ControlDmsC c WHERE c.est8 = :est8"),
    @NamedQuery(name = "ControlDmsC.findByIdResumen", query = "SELECT c FROM ControlDmsC c WHERE c.idResumen = :idResumen")})
public class ControlDmsC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dimensional_c")
    private Integer idDimensionalC;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "fecha_turno")
    @Temporal(TemporalType.DATE)
    private Date fechaTurno;
    @Column(name = "turno")
    private String turno;
    @Column(name = "lote_grafado_c")
    private String loteGrafadoC;
    @Column(name = "lote_grafado_p")
    private String loteGrafadoP;
    @Column(name = "lote_piston_c")
    private String lotePistonC;
    @Column(name = "lote_piston_p")
    private String lotePistonP;
    @Column(name = "lote_ensamble")
    private String loteEnsamble;
    @Lob
    @Column(name = "obs_est1")
    private String obsEst1;
    @Lob
    @Column(name = "obs_est2")
    private String obsEst2;
    @Lob
    @Column(name = "obs_est3")
    private String obsEst3;
    @Lob
    @Column(name = "obs_est4")
    private String obsEst4;
    @Lob
    @Column(name = "obs_est5")
    private String obsEst5;
    @Lob
    @Column(name = "obs_est6")
    private String obsEst6;
    @Column(name = "prueba")
    private Integer prueba;
    @Column(name = "estado")
    private String estado;
    @Lob
    @Column(name = "serial")
    private String serial;
    @Lob
    @Column(name = "fch_v_c")
    private String fchVC;
    @Lob
    @Column(name = "fch_p")
    private String fchP;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "est1")
    private Integer est1;
    @Column(name = "est2")
    private Integer est2;
    @Column(name = "est3")
    private Integer est3;
    @Column(name = "est4")
    private Integer est4;
    @Column(name = "est5")
    private Integer est5;
    @Column(name = "est6")
    private Integer est6;
    @Column(name = "molde")
    private String molde;
    @Column(name = "consecutivo")
    private Integer consecutivo;
    @Column(name = "calidad")
    private String calidad;
    @Column(name = "resumen")
    private Integer resumen;
    @Column(name = "est7")
    private Integer est7;
    @Column(name = "est8")
    private Integer est8;
    @Column(name = "id_resumen")
    private Integer idResumen;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente idCliente;
    @JoinColumn(name = "id_maquina", referencedColumnName = "id_maquina")
    @ManyToOne
    private Maquina idMaquina;
    @JoinColumn(name = "id_orden", referencedColumnName = "id_orden")
    @ManyToOne
    private Orden idOrden;
    @OneToMany(mappedBy = "idDimensionalC")
    private Collection<ControlDmsD> controlDmsDCollection;
    @OneToMany(mappedBy = "idDimensionalC")
    private Collection<Dimensional> dimensionalCollection;
    @OneToMany(mappedBy = "idDimensionalC")
    private Collection<Cuarentena> cuarentenaCollection;
    @OneToMany(mappedBy = "idControlDmsC")
    private Collection<Visual> visualCollection;

    public ControlDmsC() {
    }

    public ControlDmsC(Integer idDimensionalC) {
        this.idDimensionalC = idDimensionalC;
    }

    public ControlDmsC(Integer idDimensionalC, Date fechaRegistro) {
        this.idDimensionalC = idDimensionalC;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdDimensionalC() {
        return idDimensionalC;
    }

    public void setIdDimensionalC(Integer idDimensionalC) {
        this.idDimensionalC = idDimensionalC;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(Date fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getLoteGrafadoC() {
        return loteGrafadoC;
    }

    public void setLoteGrafadoC(String loteGrafadoC) {
        this.loteGrafadoC = loteGrafadoC;
    }

    public String getLoteGrafadoP() {
        return loteGrafadoP;
    }

    public void setLoteGrafadoP(String loteGrafadoP) {
        this.loteGrafadoP = loteGrafadoP;
    }

    public String getLotePistonC() {
        return lotePistonC;
    }

    public void setLotePistonC(String lotePistonC) {
        this.lotePistonC = lotePistonC;
    }

    public String getLotePistonP() {
        return lotePistonP;
    }

    public void setLotePistonP(String lotePistonP) {
        this.lotePistonP = lotePistonP;
    }

    public String getLoteEnsamble() {
        return loteEnsamble;
    }

    public void setLoteEnsamble(String loteEnsamble) {
        this.loteEnsamble = loteEnsamble;
    }

    public String getObsEst1() {
        return obsEst1;
    }

    public void setObsEst1(String obsEst1) {
        this.obsEst1 = obsEst1;
    }

    public String getObsEst2() {
        return obsEst2;
    }

    public void setObsEst2(String obsEst2) {
        this.obsEst2 = obsEst2;
    }

    public String getObsEst3() {
        return obsEst3;
    }

    public void setObsEst3(String obsEst3) {
        this.obsEst3 = obsEst3;
    }

    public String getObsEst4() {
        return obsEst4;
    }

    public void setObsEst4(String obsEst4) {
        this.obsEst4 = obsEst4;
    }

    public String getObsEst5() {
        return obsEst5;
    }

    public void setObsEst5(String obsEst5) {
        this.obsEst5 = obsEst5;
    }

    public String getObsEst6() {
        return obsEst6;
    }

    public void setObsEst6(String obsEst6) {
        this.obsEst6 = obsEst6;
    }

    public Integer getPrueba() {
        return prueba;
    }

    public void setPrueba(Integer prueba) {
        this.prueba = prueba;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getFchVC() {
        return fchVC;
    }

    public void setFchVC(String fchVC) {
        this.fchVC = fchVC;
    }

    public String getFchP() {
        return fchP;
    }

    public void setFchP(String fchP) {
        this.fchP = fchP;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getEst1() {
        return est1;
    }

    public void setEst1(Integer est1) {
        this.est1 = est1;
    }

    public Integer getEst2() {
        return est2;
    }

    public void setEst2(Integer est2) {
        this.est2 = est2;
    }

    public Integer getEst3() {
        return est3;
    }

    public void setEst3(Integer est3) {
        this.est3 = est3;
    }

    public Integer getEst4() {
        return est4;
    }

    public void setEst4(Integer est4) {
        this.est4 = est4;
    }

    public Integer getEst5() {
        return est5;
    }

    public void setEst5(Integer est5) {
        this.est5 = est5;
    }

    public Integer getEst6() {
        return est6;
    }

    public void setEst6(Integer est6) {
        this.est6 = est6;
    }

    public String getMolde() {
        return molde;
    }

    public void setMolde(String molde) {
        this.molde = molde;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public Integer getResumen() {
        return resumen;
    }

    public void setResumen(Integer resumen) {
        this.resumen = resumen;
    }

    public Integer getEst7() {
        return est7;
    }

    public void setEst7(Integer est7) {
        this.est7 = est7;
    }

    public Integer getEst8() {
        return est8;
    }

    public void setEst8(Integer est8) {
        this.est8 = est8;
    }

    public Integer getIdResumen() {
        return idResumen;
    }

    public void setIdResumen(Integer idResumen) {
        this.idResumen = idResumen;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Maquina getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Maquina idMaquina) {
        this.idMaquina = idMaquina;
    }

    public Orden getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Orden idOrden) {
        this.idOrden = idOrden;
    }

    @XmlTransient
    public Collection<ControlDmsD> getControlDmsDCollection() {
        return controlDmsDCollection;
    }

    public void setControlDmsDCollection(Collection<ControlDmsD> controlDmsDCollection) {
        this.controlDmsDCollection = controlDmsDCollection;
    }

    @XmlTransient
    public Collection<Dimensional> getDimensionalCollection() {
        return dimensionalCollection;
    }

    public void setDimensionalCollection(Collection<Dimensional> dimensionalCollection) {
        this.dimensionalCollection = dimensionalCollection;
    }

    @XmlTransient
    public Collection<Cuarentena> getCuarentenaCollection() {
        return cuarentenaCollection;
    }

    public void setCuarentenaCollection(Collection<Cuarentena> cuarentenaCollection) {
        this.cuarentenaCollection = cuarentenaCollection;
    }

    @XmlTransient
    public Collection<Visual> getVisualCollection() {
        return visualCollection;
    }

    public void setVisualCollection(Collection<Visual> visualCollection) {
        this.visualCollection = visualCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDimensionalC != null ? idDimensionalC.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlDmsC)) {
            return false;
        }
        ControlDmsC other = (ControlDmsC) object;
        if ((this.idDimensionalC == null && other.idDimensionalC != null) || (this.idDimensionalC != null && !this.idDimensionalC.equals(other.idDimensionalC))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ControlDmsC[ idDimensionalC=" + idDimensionalC + " ]";
    }
    
}
