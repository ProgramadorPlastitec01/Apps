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
 * @author asistemas2
 */
@Entity
@Table(name = "registro_implemento")
@NamedQueries({
    @NamedQuery(name = "RegistroImplemento.findAll", query = "SELECT r FROM RegistroImplemento r"),
    @NamedQuery(name = "RegistroImplemento.findByIdRegistroImplemento", query = "SELECT r FROM RegistroImplemento r WHERE r.idRegistroImplemento = :idRegistroImplemento"),
    @NamedQuery(name = "RegistroImplemento.findBySerialComparador", query = "SELECT r FROM RegistroImplemento r WHERE r.serialComparador = :serialComparador"),
    @NamedQuery(name = "RegistroImplemento.findBySerialReglaLarga", query = "SELECT r FROM RegistroImplemento r WHERE r.serialReglaLarga = :serialReglaLarga"),
    @NamedQuery(name = "RegistroImplemento.findBySerialReglaCorta", query = "SELECT r FROM RegistroImplemento r WHERE r.serialReglaCorta = :serialReglaCorta"),
    @NamedQuery(name = "RegistroImplemento.findByElectrodoBocas", query = "SELECT r FROM RegistroImplemento r WHERE r.electrodoBocas = :electrodoBocas"),
    @NamedQuery(name = "RegistroImplemento.findByElectrodoColas", query = "SELECT r FROM RegistroImplemento r WHERE r.electrodoColas = :electrodoColas"),
    @NamedQuery(name = "RegistroImplemento.findByTijera", query = "SELECT r FROM RegistroImplemento r WHERE r.tijera = :tijera"),
    @NamedQuery(name = "RegistroImplemento.findByEspatula", query = "SELECT r FROM RegistroImplemento r WHERE r.espatula = :espatula"),
    @NamedQuery(name = "RegistroImplemento.findByLlave", query = "SELECT r FROM RegistroImplemento r WHERE r.llave = :llave"),
    @NamedQuery(name = "RegistroImplemento.findByPinza", query = "SELECT r FROM RegistroImplemento r WHERE r.pinza = :pinza"),
    @NamedQuery(name = "RegistroImplemento.findByUsuarioRegistro", query = "SELECT r FROM RegistroImplemento r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "RegistroImplemento.findByFechaRegistro", query = "SELECT r FROM RegistroImplemento r WHERE r.fechaRegistro = :fechaRegistro")})
public class RegistroImplemento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_implemento")
    private Integer idRegistroImplemento;
    @Column(name = "serial_comparador")
    private String serialComparador;
    @Column(name = "serial_regla_larga")
    private String serialReglaLarga;
    @Column(name = "serial_regla_corta")
    private String serialReglaCorta;
    @Column(name = "electrodo_bocas")
    private String electrodoBocas;
    @Column(name = "electrodo_colas")
    private String electrodoColas;
    @Column(name = "tijera")
    private Short tijera;
    @Column(name = "espatula")
    private Short espatula;
    @Column(name = "llave")
    private Short llave;
    @Column(name = "pinza")
    private Short pinza;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro registro;

    public RegistroImplemento() {
    }

    public RegistroImplemento(Integer idRegistroImplemento) {
        this.idRegistroImplemento = idRegistroImplemento;
    }

    public Integer getIdRegistroImplemento() {
        return idRegistroImplemento;
    }

    public void setIdRegistroImplemento(Integer idRegistroImplemento) {
        this.idRegistroImplemento = idRegistroImplemento;
    }

    public String getSerialComparador() {
        return serialComparador;
    }

    public void setSerialComparador(String serialComparador) {
        this.serialComparador = serialComparador;
    }

    public String getSerialReglaLarga() {
        return serialReglaLarga;
    }

    public void setSerialReglaLarga(String serialReglaLarga) {
        this.serialReglaLarga = serialReglaLarga;
    }

    public String getSerialReglaCorta() {
        return serialReglaCorta;
    }

    public void setSerialReglaCorta(String serialReglaCorta) {
        this.serialReglaCorta = serialReglaCorta;
    }

    public String getElectrodoBocas() {
        return electrodoBocas;
    }

    public void setElectrodoBocas(String electrodoBocas) {
        this.electrodoBocas = electrodoBocas;
    }

    public String getElectrodoColas() {
        return electrodoColas;
    }

    public void setElectrodoColas(String electrodoColas) {
        this.electrodoColas = electrodoColas;
    }

    public Short getTijera() {
        return tijera;
    }

    public void setTijera(Short tijera) {
        this.tijera = tijera;
    }

    public Short getEspatula() {
        return espatula;
    }

    public void setEspatula(Short espatula) {
        this.espatula = espatula;
    }

    public Short getLlave() {
        return llave;
    }

    public void setLlave(Short llave) {
        this.llave = llave;
    }

    public Short getPinza() {
        return pinza;
    }

    public void setPinza(Short pinza) {
        this.pinza = pinza;
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

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroImplemento != null ? idRegistroImplemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroImplemento)) {
            return false;
        }
        RegistroImplemento other = (RegistroImplemento) object;
        if ((this.idRegistroImplemento == null && other.idRegistroImplemento != null) || (this.idRegistroImplemento != null && !this.idRegistroImplemento.equals(other.idRegistroImplemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroImplemento[idRegistroImplemento=" + idRegistroImplemento + "]";
    }

}
