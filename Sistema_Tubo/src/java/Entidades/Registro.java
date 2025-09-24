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
 * @author Programador.TI1
 */
@Entity
@Table(name = "registro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registro.findAll", query = "SELECT r FROM Registro r")
    , @NamedQuery(name = "Registro.findByIdRegistro", query = "SELECT r FROM Registro r WHERE r.idRegistro = :idRegistro")
    , @NamedQuery(name = "Registro.findByFecha", query = "SELECT r FROM Registro r WHERE r.fecha = :fecha")
    , @NamedQuery(name = "Registro.findByTurno", query = "SELECT r FROM Registro r WHERE r.turno = :turno")
    , @NamedQuery(name = "Registro.findByLoteProducto", query = "SELECT r FROM Registro r WHERE r.loteProducto = :loteProducto")
    , @NamedQuery(name = "Registro.findByLoteC", query = "SELECT r FROM Registro r WHERE r.loteC = :loteC")
    , @NamedQuery(name = "Registro.findByLoteP", query = "SELECT r FROM Registro r WHERE r.loteP = :loteP")
    , @NamedQuery(name = "Registro.findByConsecutivoCalidad", query = "SELECT r FROM Registro r WHERE r.consecutivoCalidad = :consecutivoCalidad")
    , @NamedQuery(name = "Registro.findByEstado", query = "SELECT r FROM Registro r WHERE r.estado = :estado")
    , @NamedQuery(name = "Registro.findByUsuarioRegistro", query = "SELECT r FROM Registro r WHERE r.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Registro.findByFechaRegistro", query = "SELECT r FROM Registro r WHERE r.fechaRegistro = :fechaRegistro")})
public class Registro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_registro")
    private Integer idRegistro;
    @Column(name = "fecha")
    private Integer fecha;
    @Column(name = "turno")
    private Integer turno;
    @Column(name = "lote_producto")
    private Integer loteProducto;
    @Column(name = "lote_c")
    private Integer loteC;
    @Column(name = "lote_p")
    private Integer loteP;
    @Column(name = "consecutivo_calidad")
    private Integer consecutivoCalidad;
    @Lob
    @Column(name = "serial")
    private String serial;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idRegistro")
    private Collection<ControlInternoBobina> controlInternoBobinaCollection;
    @OneToMany(mappedBy = "idRegistro")
    private Collection<Rollo> rolloCollection;
    @JoinColumn(name = "id_ficha_tecnica", referencedColumnName = "id_ficha_tecnica")
    @ManyToOne
    private FichaTecnica idFichaTecnica;
    @OneToMany(mappedBy = "idRegistro")
    private Collection<VerificacionMetraje> verificacionMetrajeCollection;
    @OneToMany(mappedBy = "idRegistro")
    private Collection<RegistroDespeje> registroDespejeCollection;

    public Registro() {
    }

    public Registro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getFecha() {
        return fecha;
    }

    public void setFecha(Integer fecha) {
        this.fecha = fecha;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public Integer getLoteProducto() {
        return loteProducto;
    }

    public void setLoteProducto(Integer loteProducto) {
        this.loteProducto = loteProducto;
    }

    public Integer getLoteC() {
        return loteC;
    }

    public void setLoteC(Integer loteC) {
        this.loteC = loteC;
    }

    public Integer getLoteP() {
        return loteP;
    }

    public void setLoteP(Integer loteP) {
        this.loteP = loteP;
    }

    public Integer getConsecutivoCalidad() {
        return consecutivoCalidad;
    }

    public void setConsecutivoCalidad(Integer consecutivoCalidad) {
        this.consecutivoCalidad = consecutivoCalidad;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    @XmlTransient
    public Collection<ControlInternoBobina> getControlInternoBobinaCollection() {
        return controlInternoBobinaCollection;
    }

    public void setControlInternoBobinaCollection(Collection<ControlInternoBobina> controlInternoBobinaCollection) {
        this.controlInternoBobinaCollection = controlInternoBobinaCollection;
    }

    @XmlTransient
    public Collection<Rollo> getRolloCollection() {
        return rolloCollection;
    }

    public void setRolloCollection(Collection<Rollo> rolloCollection) {
        this.rolloCollection = rolloCollection;
    }

    public FichaTecnica getIdFichaTecnica() {
        return idFichaTecnica;
    }

    public void setIdFichaTecnica(FichaTecnica idFichaTecnica) {
        this.idFichaTecnica = idFichaTecnica;
    }

    @XmlTransient
    public Collection<VerificacionMetraje> getVerificacionMetrajeCollection() {
        return verificacionMetrajeCollection;
    }

    public void setVerificacionMetrajeCollection(Collection<VerificacionMetraje> verificacionMetrajeCollection) {
        this.verificacionMetrajeCollection = verificacionMetrajeCollection;
    }

    @XmlTransient
    public Collection<RegistroDespeje> getRegistroDespejeCollection() {
        return registroDespejeCollection;
    }

    public void setRegistroDespejeCollection(Collection<RegistroDespeje> registroDespejeCollection) {
        this.registroDespejeCollection = registroDespejeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistro != null ? idRegistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registro)) {
            return false;
        }
        Registro other = (Registro) object;
        if ((this.idRegistro == null && other.idRegistro != null) || (this.idRegistro != null && !this.idRegistro.equals(other.idRegistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Registro[ idRegistro=" + idRegistro + " ]";
    }
    
}
