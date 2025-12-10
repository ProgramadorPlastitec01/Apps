/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

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
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "personal_datos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonalDatos.findAll", query = "SELECT p FROM PersonalDatos p"),
    @NamedQuery(name = "PersonalDatos.findByIdPersonalDatos", query = "SELECT p FROM PersonalDatos p WHERE p.idPersonalDatos = :idPersonalDatos"),
    @NamedQuery(name = "PersonalDatos.findByIdCargo", query = "SELECT p FROM PersonalDatos p WHERE p.idCargo = :idCargo"),
    @NamedQuery(name = "PersonalDatos.findByFechaIngreso", query = "SELECT p FROM PersonalDatos p WHERE p.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "PersonalDatos.findBySalario", query = "SELECT p FROM PersonalDatos p WHERE p.salario = :salario"),
    @NamedQuery(name = "PersonalDatos.findByContrato", query = "SELECT p FROM PersonalDatos p WHERE p.contrato = :contrato"),
    @NamedQuery(name = "PersonalDatos.findByEstado", query = "SELECT p FROM PersonalDatos p WHERE p.estado = :estado"),
    @NamedQuery(name = "PersonalDatos.findByNumeroHijos", query = "SELECT p FROM PersonalDatos p WHERE p.numeroHijos = :numeroHijos"),
    @NamedQuery(name = "PersonalDatos.findByBrigadista", query = "SELECT p FROM PersonalDatos p WHERE p.brigadista = :brigadista"),
    @NamedQuery(name = "PersonalDatos.findByVigencia", query = "SELECT p FROM PersonalDatos p WHERE p.vigencia = :vigencia"),
    @NamedQuery(name = "PersonalDatos.findByGrupoSanguineo", query = "SELECT p FROM PersonalDatos p WHERE p.grupoSanguineo = :grupoSanguineo"),
    @NamedQuery(name = "PersonalDatos.findByContactoUrgencias", query = "SELECT p FROM PersonalDatos p WHERE p.contactoUrgencias = :contactoUrgencias"),
    @NamedQuery(name = "PersonalDatos.findByNivelEducativo", query = "SELECT p FROM PersonalDatos p WHERE p.nivelEducativo = :nivelEducativo"),
    @NamedQuery(name = "PersonalDatos.findByFechaContrato", query = "SELECT p FROM PersonalDatos p WHERE p.fechaContrato = :fechaContrato"),
    @NamedQuery(name = "PersonalDatos.findByFechaRegistro", query = "SELECT p FROM PersonalDatos p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "PersonalDatos.findByUsuarioRegistro", query = "SELECT p FROM PersonalDatos p WHERE p.usuarioRegistro = :usuarioRegistro")})
public class PersonalDatos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_personal_datos")
    private Integer idPersonalDatos;
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "salario")
    private Integer salario;
    @Column(name = "contrato")
    private Integer contrato;
    @Column(name = "estado")
    private Integer estado;
    @Lob
    @Column(name = "correo")
    private String correo;
    @Lob
    @Column(name = "telefonos")
    private String telefonos;
    @Column(name = "numero_hijos")
    private Integer numeroHijos;
    @Column(name = "brigadista")
    private Integer brigadista;
    @Column(name = "vigencia")
    private Integer vigencia;
    @Column(name = "grupo_sanguineo")
    private String grupoSanguineo;
    @Column(name = "contacto_urgencias")
    private String contactoUrgencias;
    @Lob
    @Column(name = "restriccion_fisica")
    private String restriccionFisica;
    @Lob
    @Column(name = "restriccion_medica")
    private String restriccionMedica;
    @Column(name = "nivel_educativo")
    private String nivelEducativo;
    @Column(name = "fecha_contrato")
    @Temporal(TemporalType.DATE)
    private Date fechaContrato;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @JoinColumn(name = "documento", referencedColumnName = "documento")
    @ManyToOne
    private Personal documento;

    public PersonalDatos() {
    }

    public PersonalDatos(Integer idPersonalDatos) {
        this.idPersonalDatos = idPersonalDatos;
    }

    public Integer getIdPersonalDatos() {
        return idPersonalDatos;
    }

    public void setIdPersonalDatos(Integer idPersonalDatos) {
        this.idPersonalDatos = idPersonalDatos;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    public Integer getContrato() {
        return contrato;
    }

    public void setContrato(Integer contrato) {
        this.contrato = contrato;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public Integer getNumeroHijos() {
        return numeroHijos;
    }

    public void setNumeroHijos(Integer numeroHijos) {
        this.numeroHijos = numeroHijos;
    }

    public Integer getBrigadista() {
        return brigadista;
    }

    public void setBrigadista(Integer brigadista) {
        this.brigadista = brigadista;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getContactoUrgencias() {
        return contactoUrgencias;
    }

    public void setContactoUrgencias(String contactoUrgencias) {
        this.contactoUrgencias = contactoUrgencias;
    }

    public String getRestriccionFisica() {
        return restriccionFisica;
    }

    public void setRestriccionFisica(String restriccionFisica) {
        this.restriccionFisica = restriccionFisica;
    }

    public String getRestriccionMedica() {
        return restriccionMedica;
    }

    public void setRestriccionMedica(String restriccionMedica) {
        this.restriccionMedica = restriccionMedica;
    }

    public String getNivelEducativo() {
        return nivelEducativo;
    }

    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Personal getDocumento() {
        return documento;
    }

    public void setDocumento(Personal documento) {
        this.documento = documento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersonalDatos != null ? idPersonalDatos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonalDatos)) {
            return false;
        }
        PersonalDatos other = (PersonalDatos) object;
        if ((this.idPersonalDatos == null && other.idPersonalDatos != null) || (this.idPersonalDatos != null && !this.idPersonalDatos.equals(other.idPersonalDatos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.PersonalDatos[ idPersonalDatos=" + idPersonalDatos + " ]";
    }
    
}
