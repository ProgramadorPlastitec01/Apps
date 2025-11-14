/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "personal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personal.findAll", query = "SELECT p FROM Personal p"),
    @NamedQuery(name = "Personal.findByDocumento", query = "SELECT p FROM Personal p WHERE p.documento = :documento"),
    @NamedQuery(name = "Personal.findByNombres", query = "SELECT p FROM Personal p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Personal.findByApellidos", query = "SELECT p FROM Personal p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Personal.findByGenero", query = "SELECT p FROM Personal p WHERE p.genero = :genero"),
    @NamedQuery(name = "Personal.findByFechaNacimiento", query = "SELECT p FROM Personal p WHERE p.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Personal.findByCodigoFirma", query = "SELECT p FROM Personal p WHERE p.codigoFirma = :codigoFirma"),
    @NamedQuery(name = "Personal.findByEspecialidad", query = "SELECT p FROM Personal p WHERE p.especialidad = :especialidad"),
    @NamedQuery(name = "Personal.findByFechaRegistro", query = "SELECT p FROM Personal p WHERE p.fechaRegistro = :fechaRegistro")})
public class Personal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "documento")
    private Long documento;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "genero")
    private Character genero;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(name = "codigo_firma")
    private Integer codigoFirma;
    @Column(name = "especialidad")
    private String especialidad;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "documento")
    private Collection<PersonalDatos> personalDatosCollection;

    public Personal() {
    }

    public Personal(Long documento) {
        this.documento = documento;
    }

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
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

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getCodigoFirma() {
        return codigoFirma;
    }

    public void setCodigoFirma(Integer codigoFirma) {
        this.codigoFirma = codigoFirma;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @XmlTransient
    public Collection<PersonalDatos> getPersonalDatosCollection() {
        return personalDatosCollection;
    }

    public void setPersonalDatosCollection(Collection<PersonalDatos> personalDatosCollection) {
        this.personalDatosCollection = personalDatosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documento != null ? documento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personal)) {
            return false;
        }
        Personal other = (Personal) object;
        if ((this.documento == null && other.documento != null) || (this.documento != null && !this.documento.equals(other.documento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.Personal[ documento=" + documento + " ]";
    }
    
}
