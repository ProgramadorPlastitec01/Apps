/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author asistemas2
 */
@Entity
@Table(name = "registro_prueba_calidad")
@NamedQueries({
    @NamedQuery(name = "RegistroPruebaCalidad.findAll", query = "SELECT r FROM RegistroPruebaCalidad r"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByIdRegistroPruebaCalidad", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.idRegistroPruebaCalidad = :idRegistroPruebaCalidad"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByIdRegistro", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.idRegistro = :idRegistro"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByIdParametro", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.idParametro = :idParametro"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByFrecuencia", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.frecuencia = :frecuencia"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByToma1", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.toma1 = :toma1"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByUsuarioToma1", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.usuarioToma1 = :usuarioToma1"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByToma2", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.toma2 = :toma2"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByUsuarioToma2", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.usuarioToma2 = :usuarioToma2"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByToma3", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.toma3 = :toma3"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByUsuarioToma3", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.usuarioToma3 = :usuarioToma3"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByToma4", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.toma4 = :toma4"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByUsuarioToma4", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.usuarioToma4 = :usuarioToma4"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByToma5", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.toma5 = :toma5"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByUsuarioToma5", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.usuarioToma5 = :usuarioToma5"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByToma6", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.toma6 = :toma6"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByUsuarioToma6", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.usuarioToma6 = :usuarioToma6"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByToma7", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.toma7 = :toma7"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByUsuarioToma7", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.usuarioToma7 = :usuarioToma7"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByToma8", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.toma8 = :toma8"),
    @NamedQuery(name = "RegistroPruebaCalidad.findByUsuarioToma8", query = "SELECT r FROM RegistroPruebaCalidad r WHERE r.usuarioToma8 = :usuarioToma8")})
public class RegistroPruebaCalidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_prueba_calidad")
    private Integer idRegistroPruebaCalidad;
    @Column(name = "id_registro")
    private Integer idRegistro;
    @Column(name = "id_parametro")
    private Integer idParametro;
    @Column(name = "frecuencia")
    private Integer frecuencia;
    @Column(name = "toma1")
    private String toma1;
    @Column(name = "usuario_toma1")
    private String usuarioToma1;
    @Column(name = "toma2")
    private String toma2;
    @Column(name = "usuario_toma2")
    private String usuarioToma2;
    @Column(name = "toma3")
    private String toma3;
    @Column(name = "usuario_toma3")
    private String usuarioToma3;
    @Column(name = "toma4")
    private String toma4;
    @Column(name = "usuario_toma4")
    private String usuarioToma4;
    @Column(name = "toma5")
    private String toma5;
    @Column(name = "usuario_toma5")
    private String usuarioToma5;
    @Column(name = "toma6")
    private String toma6;
    @Column(name = "usuario_toma6")
    private String usuarioToma6;
    @Column(name = "toma7")
    private String toma7;
    @Column(name = "usuario_toma7")
    private String usuarioToma7;
    @Column(name = "toma8")
    private String toma8;
    @Column(name = "usuario_toma8")
    private String usuarioToma8;

    public RegistroPruebaCalidad() {
    }

    public RegistroPruebaCalidad(Integer idRegistroPruebaCalidad) {
        this.idRegistroPruebaCalidad = idRegistroPruebaCalidad;
    }

    public Integer getIdRegistroPruebaCalidad() {
        return idRegistroPruebaCalidad;
    }

    public void setIdRegistroPruebaCalidad(Integer idRegistroPruebaCalidad) {
        this.idRegistroPruebaCalidad = idRegistroPruebaCalidad;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getToma1() {
        return toma1;
    }

    public void setToma1(String toma1) {
        this.toma1 = toma1;
    }

    public String getUsuarioToma1() {
        return usuarioToma1;
    }

    public void setUsuarioToma1(String usuarioToma1) {
        this.usuarioToma1 = usuarioToma1;
    }

    public String getToma2() {
        return toma2;
    }

    public void setToma2(String toma2) {
        this.toma2 = toma2;
    }

    public String getUsuarioToma2() {
        return usuarioToma2;
    }

    public void setUsuarioToma2(String usuarioToma2) {
        this.usuarioToma2 = usuarioToma2;
    }

    public String getToma3() {
        return toma3;
    }

    public void setToma3(String toma3) {
        this.toma3 = toma3;
    }

    public String getUsuarioToma3() {
        return usuarioToma3;
    }

    public void setUsuarioToma3(String usuarioToma3) {
        this.usuarioToma3 = usuarioToma3;
    }

    public String getToma4() {
        return toma4;
    }

    public void setToma4(String toma4) {
        this.toma4 = toma4;
    }

    public String getUsuarioToma4() {
        return usuarioToma4;
    }

    public void setUsuarioToma4(String usuarioToma4) {
        this.usuarioToma4 = usuarioToma4;
    }

    public String getToma5() {
        return toma5;
    }

    public void setToma5(String toma5) {
        this.toma5 = toma5;
    }

    public String getUsuarioToma5() {
        return usuarioToma5;
    }

    public void setUsuarioToma5(String usuarioToma5) {
        this.usuarioToma5 = usuarioToma5;
    }

    public String getToma6() {
        return toma6;
    }

    public void setToma6(String toma6) {
        this.toma6 = toma6;
    }

    public String getUsuarioToma6() {
        return usuarioToma6;
    }

    public void setUsuarioToma6(String usuarioToma6) {
        this.usuarioToma6 = usuarioToma6;
    }

    public String getToma7() {
        return toma7;
    }

    public void setToma7(String toma7) {
        this.toma7 = toma7;
    }

    public String getUsuarioToma7() {
        return usuarioToma7;
    }

    public void setUsuarioToma7(String usuarioToma7) {
        this.usuarioToma7 = usuarioToma7;
    }

    public String getToma8() {
        return toma8;
    }

    public void setToma8(String toma8) {
        this.toma8 = toma8;
    }

    public String getUsuarioToma8() {
        return usuarioToma8;
    }

    public void setUsuarioToma8(String usuarioToma8) {
        this.usuarioToma8 = usuarioToma8;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroPruebaCalidad != null ? idRegistroPruebaCalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroPruebaCalidad)) {
            return false;
        }
        RegistroPruebaCalidad other = (RegistroPruebaCalidad) object;
        if ((this.idRegistroPruebaCalidad == null && other.idRegistroPruebaCalidad != null) || (this.idRegistroPruebaCalidad != null && !this.idRegistroPruebaCalidad.equals(other.idRegistroPruebaCalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroPruebaCalidad[idRegistroPruebaCalidad=" + idRegistroPruebaCalidad + "]";
    }

}
