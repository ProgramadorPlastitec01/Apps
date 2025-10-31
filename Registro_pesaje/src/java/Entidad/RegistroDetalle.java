/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "registro_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroDetalle.findAll", query = "SELECT r FROM RegistroDetalle r")
    , @NamedQuery(name = "RegistroDetalle.findByIdregistroDetalle", query = "SELECT r FROM RegistroDetalle r WHERE r.idregistroDetalle = :idregistroDetalle")
    , @NamedQuery(name = "RegistroDetalle.findByTurno", query = "SELECT r FROM RegistroDetalle r WHERE r.turno = :turno")
    , @NamedQuery(name = "RegistroDetalle.findByGrupo", query = "SELECT r FROM RegistroDetalle r WHERE r.grupo = :grupo")
    , @NamedQuery(name = "RegistroDetalle.findByEstado", query = "SELECT r FROM RegistroDetalle r WHERE r.estado = :estado")
    , @NamedQuery(name = "RegistroDetalle.findByPeso1", query = "SELECT r FROM RegistroDetalle r WHERE r.peso1 = :peso1")
    , @NamedQuery(name = "RegistroDetalle.findByFechaInicio1", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaInicio1 = :fechaInicio1")
    , @NamedQuery(name = "RegistroDetalle.findByFechaFinal1", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaFinal1 = :fechaFinal1")
    , @NamedQuery(name = "RegistroDetalle.findByPeso2", query = "SELECT r FROM RegistroDetalle r WHERE r.peso2 = :peso2")
    , @NamedQuery(name = "RegistroDetalle.findByFechaInicio2", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaInicio2 = :fechaInicio2")
    , @NamedQuery(name = "RegistroDetalle.findByFechaFinal2", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaFinal2 = :fechaFinal2")
    , @NamedQuery(name = "RegistroDetalle.findByPeso3", query = "SELECT r FROM RegistroDetalle r WHERE r.peso3 = :peso3")
    , @NamedQuery(name = "RegistroDetalle.findByFechaInicio3", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaInicio3 = :fechaInicio3")
    , @NamedQuery(name = "RegistroDetalle.findByFechaFinal3", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaFinal3 = :fechaFinal3")
    , @NamedQuery(name = "RegistroDetalle.findByPeso4", query = "SELECT r FROM RegistroDetalle r WHERE r.peso4 = :peso4")
    , @NamedQuery(name = "RegistroDetalle.findByFechaInicio4", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaInicio4 = :fechaInicio4")
    , @NamedQuery(name = "RegistroDetalle.findByFechaFinal4", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaFinal4 = :fechaFinal4")
    , @NamedQuery(name = "RegistroDetalle.findByPeso5", query = "SELECT r FROM RegistroDetalle r WHERE r.peso5 = :peso5")
    , @NamedQuery(name = "RegistroDetalle.findByFechaInicio5", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaInicio5 = :fechaInicio5")
    , @NamedQuery(name = "RegistroDetalle.findByFechaFinal5", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaFinal5 = :fechaFinal5")
    , @NamedQuery(name = "RegistroDetalle.findByPeso6", query = "SELECT r FROM RegistroDetalle r WHERE r.peso6 = :peso6")
    , @NamedQuery(name = "RegistroDetalle.findByFechaInicio6", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaInicio6 = :fechaInicio6")
    , @NamedQuery(name = "RegistroDetalle.findByFechaFinal6", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaFinal6 = :fechaFinal6")
    , @NamedQuery(name = "RegistroDetalle.findByPeso7", query = "SELECT r FROM RegistroDetalle r WHERE r.peso7 = :peso7")
    , @NamedQuery(name = "RegistroDetalle.findByFechaInicio7", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaInicio7 = :fechaInicio7")
    , @NamedQuery(name = "RegistroDetalle.findByFechaFinal7", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaFinal7 = :fechaFinal7")
    , @NamedQuery(name = "RegistroDetalle.findByPeso8", query = "SELECT r FROM RegistroDetalle r WHERE r.peso8 = :peso8")
    , @NamedQuery(name = "RegistroDetalle.findByFechaInicio8", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaInicio8 = :fechaInicio8")
    , @NamedQuery(name = "RegistroDetalle.findByFechaFinal8", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaFinal8 = :fechaFinal8")
    , @NamedQuery(name = "RegistroDetalle.findByUsuarioRegistro", query = "SELECT r FROM RegistroDetalle r WHERE r.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "RegistroDetalle.findByFechaRegistro", query = "SELECT r FROM RegistroDetalle r WHERE r.fechaRegistro = :fechaRegistro")})
public class RegistroDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registroDetalle")
    private Integer idregistroDetalle;
    @Column(name = "turno")
    private String turno;
    @Column(name = "grupo")
    private String grupo;
    @Lob
    @Column(name = "personal")
    private String personal;
    @Column(name = "estado")
    private Integer estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "peso_1")
    private BigDecimal peso1;
    @Column(name = "fecha_inicio_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio1;
    @Column(name = "fecha_final_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal1;
    @Basic(optional = false)
    @Column(name = "peso_2")
    private BigDecimal peso2;
    @Column(name = "fecha_inicio_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio2;
    @Column(name = "fecha_final_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal2;
    @Basic(optional = false)
    @Column(name = "peso_3")
    private BigDecimal peso3;
    @Column(name = "fecha_inicio_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio3;
    @Column(name = "fecha_final_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal3;
    @Basic(optional = false)
    @Column(name = "peso_4")
    private BigDecimal peso4;
    @Column(name = "fecha_inicio_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio4;
    @Column(name = "fecha_final_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal4;
    @Basic(optional = false)
    @Column(name = "peso_5")
    private BigDecimal peso5;
    @Column(name = "fecha_inicio_5")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio5;
    @Column(name = "fecha_final_5")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal5;
    @Basic(optional = false)
    @Column(name = "peso_6")
    private BigDecimal peso6;
    @Column(name = "fecha_inicio_6")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio6;
    @Column(name = "fecha_final_6")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal6;
    @Basic(optional = false)
    @Column(name = "peso_7")
    private BigDecimal peso7;
    @Column(name = "fecha_inicio_7")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio7;
    @Column(name = "fecha_final_7")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal7;
    @Basic(optional = false)
    @Column(name = "peso_8")
    private BigDecimal peso8;
    @Column(name = "fecha_inicio_8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio8;
    @Column(name = "fecha_final_8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal8;
    @Lob
    @Column(name = "tiempo")
    private String tiempo;
    @Lob
    @Column(name = "defecto")
    private String defecto;
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne(optional = false)
    private Registro idRegistro;

    public RegistroDetalle() {
    }

    public RegistroDetalle(Integer idregistroDetalle) {
        this.idregistroDetalle = idregistroDetalle;
    }

    public RegistroDetalle(Integer idregistroDetalle, BigDecimal peso1, BigDecimal peso2, BigDecimal peso3, BigDecimal peso4, BigDecimal peso5, BigDecimal peso6, BigDecimal peso7, BigDecimal peso8) {
        this.idregistroDetalle = idregistroDetalle;
        this.peso1 = peso1;
        this.peso2 = peso2;
        this.peso3 = peso3;
        this.peso4 = peso4;
        this.peso5 = peso5;
        this.peso6 = peso6;
        this.peso7 = peso7;
        this.peso8 = peso8;
    }

    public Integer getIdregistroDetalle() {
        return idregistroDetalle;
    }

    public void setIdregistroDetalle(Integer idregistroDetalle) {
        this.idregistroDetalle = idregistroDetalle;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public BigDecimal getPeso1() {
        return peso1;
    }

    public void setPeso1(BigDecimal peso1) {
        this.peso1 = peso1;
    }

    public Date getFechaInicio1() {
        return fechaInicio1;
    }

    public void setFechaInicio1(Date fechaInicio1) {
        this.fechaInicio1 = fechaInicio1;
    }

    public Date getFechaFinal1() {
        return fechaFinal1;
    }

    public void setFechaFinal1(Date fechaFinal1) {
        this.fechaFinal1 = fechaFinal1;
    }

    public BigDecimal getPeso2() {
        return peso2;
    }

    public void setPeso2(BigDecimal peso2) {
        this.peso2 = peso2;
    }

    public Date getFechaInicio2() {
        return fechaInicio2;
    }

    public void setFechaInicio2(Date fechaInicio2) {
        this.fechaInicio2 = fechaInicio2;
    }

    public Date getFechaFinal2() {
        return fechaFinal2;
    }

    public void setFechaFinal2(Date fechaFinal2) {
        this.fechaFinal2 = fechaFinal2;
    }

    public BigDecimal getPeso3() {
        return peso3;
    }

    public void setPeso3(BigDecimal peso3) {
        this.peso3 = peso3;
    }

    public Date getFechaInicio3() {
        return fechaInicio3;
    }

    public void setFechaInicio3(Date fechaInicio3) {
        this.fechaInicio3 = fechaInicio3;
    }

    public Date getFechaFinal3() {
        return fechaFinal3;
    }

    public void setFechaFinal3(Date fechaFinal3) {
        this.fechaFinal3 = fechaFinal3;
    }

    public BigDecimal getPeso4() {
        return peso4;
    }

    public void setPeso4(BigDecimal peso4) {
        this.peso4 = peso4;
    }

    public Date getFechaInicio4() {
        return fechaInicio4;
    }

    public void setFechaInicio4(Date fechaInicio4) {
        this.fechaInicio4 = fechaInicio4;
    }

    public Date getFechaFinal4() {
        return fechaFinal4;
    }

    public void setFechaFinal4(Date fechaFinal4) {
        this.fechaFinal4 = fechaFinal4;
    }

    public BigDecimal getPeso5() {
        return peso5;
    }

    public void setPeso5(BigDecimal peso5) {
        this.peso5 = peso5;
    }

    public Date getFechaInicio5() {
        return fechaInicio5;
    }

    public void setFechaInicio5(Date fechaInicio5) {
        this.fechaInicio5 = fechaInicio5;
    }

    public Date getFechaFinal5() {
        return fechaFinal5;
    }

    public void setFechaFinal5(Date fechaFinal5) {
        this.fechaFinal5 = fechaFinal5;
    }

    public BigDecimal getPeso6() {
        return peso6;
    }

    public void setPeso6(BigDecimal peso6) {
        this.peso6 = peso6;
    }

    public Date getFechaInicio6() {
        return fechaInicio6;
    }

    public void setFechaInicio6(Date fechaInicio6) {
        this.fechaInicio6 = fechaInicio6;
    }

    public Date getFechaFinal6() {
        return fechaFinal6;
    }

    public void setFechaFinal6(Date fechaFinal6) {
        this.fechaFinal6 = fechaFinal6;
    }

    public BigDecimal getPeso7() {
        return peso7;
    }

    public void setPeso7(BigDecimal peso7) {
        this.peso7 = peso7;
    }

    public Date getFechaInicio7() {
        return fechaInicio7;
    }

    public void setFechaInicio7(Date fechaInicio7) {
        this.fechaInicio7 = fechaInicio7;
    }

    public Date getFechaFinal7() {
        return fechaFinal7;
    }

    public void setFechaFinal7(Date fechaFinal7) {
        this.fechaFinal7 = fechaFinal7;
    }

    public BigDecimal getPeso8() {
        return peso8;
    }

    public void setPeso8(BigDecimal peso8) {
        this.peso8 = peso8;
    }

    public Date getFechaInicio8() {
        return fechaInicio8;
    }

    public void setFechaInicio8(Date fechaInicio8) {
        this.fechaInicio8 = fechaInicio8;
    }

    public Date getFechaFinal8() {
        return fechaFinal8;
    }

    public void setFechaFinal8(Date fechaFinal8) {
        this.fechaFinal8 = fechaFinal8;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getDefecto() {
        return defecto;
    }

    public void setDefecto(String defecto) {
        this.defecto = defecto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
        hash += (idregistroDetalle != null ? idregistroDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroDetalle)) {
            return false;
        }
        RegistroDetalle other = (RegistroDetalle) object;
        if ((this.idregistroDetalle == null && other.idregistroDetalle != null) || (this.idregistroDetalle != null && !this.idregistroDetalle.equals(other.idregistroDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.RegistroDetalle[ idregistroDetalle=" + idregistroDetalle + " ]";
    }
    
}
