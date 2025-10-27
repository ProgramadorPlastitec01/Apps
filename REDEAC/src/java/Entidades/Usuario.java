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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario")
    , @NamedQuery(name = "Usuario.findByNombres", query = "SELECT u FROM Usuario u WHERE u.nombres = :nombres")
    , @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos")
    , @NamedQuery(name = "Usuario.findByDocumento", query = "SELECT u FROM Usuario u WHERE u.documento = :documento")
    , @NamedQuery(name = "Usuario.findByCodigo", query = "SELECT u FROM Usuario u WHERE u.codigo = :codigo")
    , @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario")
    , @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")
    , @NamedQuery(name = "Usuario.findByFirma", query = "SELECT u FROM Usuario u WHERE u.firma = :firma")
    , @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo")
    , @NamedQuery(name = "Usuario.findByEstado", query = "SELECT u FROM Usuario u WHERE u.estado = :estado")
    , @NamedQuery(name = "Usuario.findByTurno", query = "SELECT u FROM Usuario u WHERE u.turno = :turno")
    , @NamedQuery(name = "Usuario.findByUsuarioRegistro", query = "SELECT u FROM Usuario u WHERE u.usuarioRegistro = :usuarioRegistro")
    , @NamedQuery(name = "Usuario.findByFechaRegistro", query = "SELECT u FROM Usuario u WHERE u.fechaRegistro = :fechaRegistro")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "documento")
    private Integer documento;
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "password")
    private String password;
    @Column(name = "firma")
    private String firma;
    @Column(name = "correo")
    private String correo;
    @Column(name = "estado")
    private Short estado;
    @Column(name = "turno")
    private Short turno;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idUsuarioEnvia")
    private Collection<Pendiente> pendienteCollection;
    @OneToMany(mappedBy = "idUsuarioSolucion")
    private Collection<Pendiente> pendienteCollection1;
    @OneToMany(mappedBy = "idUsuarioRevisa")
    private Collection<Pendiente> pendienteCollection2;
    @OneToMany(mappedBy = "usuarioRegistro")
    private Collection<ActividadReportada> actividadReportadaCollection;
    @OneToMany(mappedBy = "idTecnicoAsignado")
    private Collection<Caso> casoCollection;
    @OneToMany(mappedBy = "idTecnicoSolucion")
    private Collection<Caso> casoCollection1;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne
    private Rol idRol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioRegistro")
    private Collection<ActividadGeneral> actividadGeneralCollection;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public Short getTurno() {
        return turno;
    }

    public void setTurno(Short turno) {
        this.turno = turno;
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
    public Collection<Pendiente> getPendienteCollection() {
        return pendienteCollection;
    }

    public void setPendienteCollection(Collection<Pendiente> pendienteCollection) {
        this.pendienteCollection = pendienteCollection;
    }

    @XmlTransient
    public Collection<Pendiente> getPendienteCollection1() {
        return pendienteCollection1;
    }

    public void setPendienteCollection1(Collection<Pendiente> pendienteCollection1) {
        this.pendienteCollection1 = pendienteCollection1;
    }

    @XmlTransient
    public Collection<Pendiente> getPendienteCollection2() {
        return pendienteCollection2;
    }

    public void setPendienteCollection2(Collection<Pendiente> pendienteCollection2) {
        this.pendienteCollection2 = pendienteCollection2;
    }

    @XmlTransient
    public Collection<ActividadReportada> getActividadReportadaCollection() {
        return actividadReportadaCollection;
    }

    public void setActividadReportadaCollection(Collection<ActividadReportada> actividadReportadaCollection) {
        this.actividadReportadaCollection = actividadReportadaCollection;
    }

    @XmlTransient
    public Collection<Caso> getCasoCollection() {
        return casoCollection;
    }

    public void setCasoCollection(Collection<Caso> casoCollection) {
        this.casoCollection = casoCollection;
    }

    @XmlTransient
    public Collection<Caso> getCasoCollection1() {
        return casoCollection1;
    }

    public void setCasoCollection1(Collection<Caso> casoCollection1) {
        this.casoCollection1 = casoCollection1;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    @XmlTransient
    public Collection<ActividadGeneral> getActividadGeneralCollection() {
        return actividadGeneralCollection;
    }

    public void setActividadGeneralCollection(Collection<ActividadGeneral> actividadGeneralCollection) {
        this.actividadGeneralCollection = actividadGeneralCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Usuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
