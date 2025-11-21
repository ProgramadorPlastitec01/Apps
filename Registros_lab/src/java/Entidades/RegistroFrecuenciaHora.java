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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author asistemas2
 */
@Entity
@Table(name = "registro_frecuencia_hora")
@NamedQueries({
    @NamedQuery(name = "RegistroFrecuenciaHora.findAll", query = "SELECT r FROM RegistroFrecuenciaHora r"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByIdRegistroFrecuenciaHora", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.idRegistroFrecuenciaHora = :idRegistroFrecuenciaHora"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByToma1", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.toma1 = :toma1"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByUsuarioToma1", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.usuarioToma1 = :usuarioToma1"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByToma2", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.toma2 = :toma2"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByUsuarioToma2", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.usuarioToma2 = :usuarioToma2"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByToma3", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.toma3 = :toma3"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByUsuarioToma3", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.usuarioToma3 = :usuarioToma3"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByToma4", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.toma4 = :toma4"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByUsuarioToma4", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.usuarioToma4 = :usuarioToma4"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByToma5", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.toma5 = :toma5"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByUsuarioToma5", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.usuarioToma5 = :usuarioToma5"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByToma6", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.toma6 = :toma6"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByUsuarioToma6", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.usuarioToma6 = :usuarioToma6"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByToma7", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.toma7 = :toma7"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByUsuarioToma7", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.usuarioToma7 = :usuarioToma7"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByToma8", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.toma8 = :toma8"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByUsuarioToma8", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.usuarioToma8 = :usuarioToma8"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByToma9", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.toma9 = :toma9"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByUsuarioToma9", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.usuarioToma9 = :usuarioToma9"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByToma10", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.toma10 = :toma10"),
    @NamedQuery(name = "RegistroFrecuenciaHora.findByUsuarioToma10", query = "SELECT r FROM RegistroFrecuenciaHora r WHERE r.usuarioToma10 = :usuarioToma10")})
public class RegistroFrecuenciaHora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_frecuencia_hora")
    private Integer idRegistroFrecuenciaHora;
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
    @Column(name = "toma9")
    private String toma9;
    @Column(name = "usuario_toma9")
    private String usuarioToma9;
    @Column(name = "toma10")
    private String toma10;
    @Column(name = "usuario_toma10")
    private String usuarioToma10;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro registro;
    @JoinColumn(name = "id_parametro", referencedColumnName = "id_parametro")
    @ManyToOne
    private Parametro parametro;

    public RegistroFrecuenciaHora() {
    }

    public RegistroFrecuenciaHora(Integer idRegistroFrecuenciaHora) {
        this.idRegistroFrecuenciaHora = idRegistroFrecuenciaHora;
    }

    public Integer getIdRegistroFrecuenciaHora() {
        return idRegistroFrecuenciaHora;
    }

    public void setIdRegistroFrecuenciaHora(Integer idRegistroFrecuenciaHora) {
        this.idRegistroFrecuenciaHora = idRegistroFrecuenciaHora;
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

    public String getToma9() {
        return toma9;
    }

    public void setToma9(String toma9) {
        this.toma9 = toma9;
    }

    public String getUsuarioToma9() {
        return usuarioToma9;
    }

    public void setUsuarioToma9(String usuarioToma9) {
        this.usuarioToma9 = usuarioToma9;
    }

    public String getToma10() {
        return toma10;
    }

    public void setToma10(String toma10) {
        this.toma10 = toma10;
    }

    public String getUsuarioToma10() {
        return usuarioToma10;
    }

    public void setUsuarioToma10(String usuarioToma10) {
        this.usuarioToma10 = usuarioToma10;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroFrecuenciaHora != null ? idRegistroFrecuenciaHora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroFrecuenciaHora)) {
            return false;
        }
        RegistroFrecuenciaHora other = (RegistroFrecuenciaHora) object;
        if ((this.idRegistroFrecuenciaHora == null && other.idRegistroFrecuenciaHora != null) || (this.idRegistroFrecuenciaHora != null && !this.idRegistroFrecuenciaHora.equals(other.idRegistroFrecuenciaHora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroFrecuenciaHora[idRegistroFrecuenciaHora=" + idRegistroFrecuenciaHora + "]";
    }

}
