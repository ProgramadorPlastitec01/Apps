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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author prog.sistemas1
 */
@Entity
@Table(name = "registro_campo")
@NamedQueries({
    @NamedQuery(name = "RegistroCampo.findAll", query = "SELECT r FROM RegistroCampo r"),
    @NamedQuery(name = "RegistroCampo.findByRegistroCampo", query = "SELECT r FROM RegistroCampo r WHERE r.registroCampo = :registroCampo"),
    @NamedQuery(name = "RegistroCampo.findByIdRegistro", query = "SELECT r FROM RegistroCampo r WHERE r.idRegistro = :idRegistro"),
    @NamedQuery(name = "RegistroCampo.findByIdRegistroPrueba", query = "SELECT r FROM RegistroCampo r WHERE r.idRegistroPrueba = :idRegistroPrueba"),
    @NamedQuery(name = "RegistroCampo.findByTitulo", query = "SELECT r FROM RegistroCampo r WHERE r.titulo = :titulo"),
    @NamedQuery(name = "RegistroCampo.findByUsuarioRegistro", query = "SELECT r FROM RegistroCampo r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "RegistroCampo.findByFechaRegistro", query = "SELECT r FROM RegistroCampo r WHERE r.fechaRegistro = :fechaRegistro")})
public class RegistroCampo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "registro_campo")
    private Integer registroCampo;
    @Basic(optional = false)
    @Column(name = "id_registro")
    private int idRegistro;
    @Basic(optional = false)
    @Column(name = "id_registro_prueba")
    private int idRegistroPrueba;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Lob
    @Column(name = "valor_1")
    private String valor1;
    @Basic(optional = false)
    @Lob
    @Column(name = "valor_2")
    private String valor2;
    @Basic(optional = false)
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public RegistroCampo() {
    }

    public RegistroCampo(Integer registroCampo) {
        this.registroCampo = registroCampo;
    }

    public RegistroCampo(Integer registroCampo, int idRegistro, int idRegistroPrueba, String titulo, String valor1, String valor2, String usuarioRegistro, Date fechaRegistro) {
        this.registroCampo = registroCampo;
        this.idRegistro = idRegistro;
        this.idRegistroPrueba = idRegistroPrueba;
        this.titulo = titulo;
        this.valor1 = valor1;
        this.valor2 = valor2;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getRegistroCampo() {
        return registroCampo;
    }

    public void setRegistroCampo(Integer registroCampo) {
        this.registroCampo = registroCampo;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getIdRegistroPrueba() {
        return idRegistroPrueba;
    }

    public void setIdRegistroPrueba(int idRegistroPrueba) {
        this.idRegistroPrueba = idRegistroPrueba;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getValor1() {
        return valor1;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public String getValor2() {
        return valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registroCampo != null ? registroCampo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroCampo)) {
            return false;
        }
        RegistroCampo other = (RegistroCampo) object;
        if ((this.registroCampo == null && other.registroCampo != null) || (this.registroCampo != null && !this.registroCampo.equals(other.registroCampo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroCampo[registroCampo=" + registroCampo + "]";
    }

}
