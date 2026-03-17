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
 * @author Programador.TI1
 */
@Entity
@Table(name = "movimientos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movimientos.findAll", query = "SELECT m FROM Movimientos m")
    , @NamedQuery(name = "Movimientos.findByIdMovimientos", query = "SELECT m FROM Movimientos m WHERE m.idMovimientos = :idMovimientos")
    , @NamedQuery(name = "Movimientos.findByFechaEntrada", query = "SELECT m FROM Movimientos m WHERE m.fechaEntrada = :fechaEntrada")
    , @NamedQuery(name = "Movimientos.findByPieza", query = "SELECT m FROM Movimientos m WHERE m.pieza = :pieza")
    , @NamedQuery(name = "Movimientos.findByTipoEntrada", query = "SELECT m FROM Movimientos m WHERE m.tipoEntrada = :tipoEntrada")
    , @NamedQuery(name = "Movimientos.findByUsuarioRegistroEntrada", query = "SELECT m FROM Movimientos m WHERE m.usuarioRegistroEntrada = :usuarioRegistroEntrada")
    , @NamedQuery(name = "Movimientos.findByFechaRegistroEntrada", query = "SELECT m FROM Movimientos m WHERE m.fechaRegistroEntrada = :fechaRegistroEntrada")
    , @NamedQuery(name = "Movimientos.findByFechaSalida", query = "SELECT m FROM Movimientos m WHERE m.fechaSalida = :fechaSalida")
    , @NamedQuery(name = "Movimientos.findByTipoSalida", query = "SELECT m FROM Movimientos m WHERE m.tipoSalida = :tipoSalida")
    , @NamedQuery(name = "Movimientos.findByUsuarioRegistroSalida", query = "SELECT m FROM Movimientos m WHERE m.usuarioRegistroSalida = :usuarioRegistroSalida")
    , @NamedQuery(name = "Movimientos.findByFechaRegistroSalida", query = "SELECT m FROM Movimientos m WHERE m.fechaRegistroSalida = :fechaRegistroSalida")
    , @NamedQuery(name = "Movimientos.findByEncargadoEntrega", query = "SELECT m FROM Movimientos m WHERE m.encargadoEntrega = :encargadoEntrega")})
public class Movimientos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_movimientos")
    private Integer idMovimientos;
    @Column(name = "fecha_entrada")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrada;
    @Column(name = "pieza")
    private String pieza;
    @Column(name = "tipo_entrada")
    private String tipoEntrada;
    @Lob
    @Column(name = "descripcion_entrada")
    private String descripcionEntrada;
    @Column(name = "usuario_registro_entrada")
    private String usuarioRegistroEntrada;
    @Column(name = "fecha_registro_entrada")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistroEntrada;
    @Column(name = "fecha_salida")
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
    @Column(name = "tipo_salida")
    private String tipoSalida;
    @Lob
    @Column(name = "descripcion_salida")
    private String descripcionSalida;
    @Column(name = "usuario_registro_salida")
    private String usuarioRegistroSalida;
    @Column(name = "fecha_registro_salida")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistroSalida;
    @Column(name = "encargado_entrega")
    private String encargadoEntrega;
    @JoinColumn(name = "id_defecto", referencedColumnName = "id_defecto")
    @ManyToOne
    private Defecto idDefecto;
    @JoinColumn(name = "id_solicitud", referencedColumnName = "idSolicitud")
    @ManyToOne
    private Solicitud idSolicitud;

    public Movimientos() {
    }

    public Movimientos(Integer idMovimientos) {
        this.idMovimientos = idMovimientos;
    }

    public Integer getIdMovimientos() {
        return idMovimientos;
    }

    public void setIdMovimientos(Integer idMovimientos) {
        this.idMovimientos = idMovimientos;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getPieza() {
        return pieza;
    }

    public void setPieza(String pieza) {
        this.pieza = pieza;
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public String getDescripcionEntrada() {
        return descripcionEntrada;
    }

    public void setDescripcionEntrada(String descripcionEntrada) {
        this.descripcionEntrada = descripcionEntrada;
    }

    public String getUsuarioRegistroEntrada() {
        return usuarioRegistroEntrada;
    }

    public void setUsuarioRegistroEntrada(String usuarioRegistroEntrada) {
        this.usuarioRegistroEntrada = usuarioRegistroEntrada;
    }

    public Date getFechaRegistroEntrada() {
        return fechaRegistroEntrada;
    }

    public void setFechaRegistroEntrada(Date fechaRegistroEntrada) {
        this.fechaRegistroEntrada = fechaRegistroEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getTipoSalida() {
        return tipoSalida;
    }

    public void setTipoSalida(String tipoSalida) {
        this.tipoSalida = tipoSalida;
    }

    public String getDescripcionSalida() {
        return descripcionSalida;
    }

    public void setDescripcionSalida(String descripcionSalida) {
        this.descripcionSalida = descripcionSalida;
    }

    public String getUsuarioRegistroSalida() {
        return usuarioRegistroSalida;
    }

    public void setUsuarioRegistroSalida(String usuarioRegistroSalida) {
        this.usuarioRegistroSalida = usuarioRegistroSalida;
    }

    public Date getFechaRegistroSalida() {
        return fechaRegistroSalida;
    }

    public void setFechaRegistroSalida(Date fechaRegistroSalida) {
        this.fechaRegistroSalida = fechaRegistroSalida;
    }

    public String getEncargadoEntrega() {
        return encargadoEntrega;
    }

    public void setEncargadoEntrega(String encargadoEntrega) {
        this.encargadoEntrega = encargadoEntrega;
    }

    public Defecto getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(Defecto idDefecto) {
        this.idDefecto = idDefecto;
    }

    public Solicitud getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Solicitud idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMovimientos != null ? idMovimientos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimientos)) {
            return false;
        }
        Movimientos other = (Movimientos) object;
        if ((this.idMovimientos == null && other.idMovimientos != null) || (this.idMovimientos != null && !this.idMovimientos.equals(other.idMovimientos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Movimientos[ idMovimientos=" + idMovimientos + " ]";
    }
    
}
