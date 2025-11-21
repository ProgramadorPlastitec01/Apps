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
 * @author prog.sistemas1
 */
@Entity
@Table(name = "registro_frecuencia_media_hora")
@NamedQueries({
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findAll", query = "SELECT r FROM RegistroFrecuenciaMediaHora r"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByIdRegistroFrecuenciaMediaHora", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.idRegistroFrecuenciaMediaHora = :idRegistroFrecuenciaMediaHora"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma1", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma1 = :toma1"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma1", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma1 = :usuarioToma1"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma2", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma2 = :toma2"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma2", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma2 = :usuarioToma2"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma3", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma3 = :toma3"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma3", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma3 = :usuarioToma3"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma4", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma4 = :toma4"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma4", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma4 = :usuarioToma4"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma5", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma5 = :toma5"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma5", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma5 = :usuarioToma5"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma6", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma6 = :toma6"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma6", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma6 = :usuarioToma6"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma7", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma7 = :toma7"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma7", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma7 = :usuarioToma7"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma8", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma8 = :toma8"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma8", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma8 = :usuarioToma8"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma9", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma9 = :toma9"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma9", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma9 = :usuarioToma9"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma10", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma10 = :toma10"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma10", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma10 = :usuarioToma10"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma11", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma11 = :toma11"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma11", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma11 = :usuarioToma11"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma12", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma12 = :toma12"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma12", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma12 = :usuarioToma12"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma13", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma13 = :toma13"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma13", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma13 = :usuarioToma13"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma14", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma14 = :toma14"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma14", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma14 = :usuarioToma14"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma15", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma15 = :toma15"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma15", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma15 = :usuarioToma15"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma16", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma16 = :toma16"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma16", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma16 = :usuarioToma16"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma17", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma17 = :toma17"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma17", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma17 = :usuarioToma17"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByToma18", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.toma18 = :toma18"),
    @NamedQuery(name = "RegistroFrecuenciaMediaHora.findByUsuarioToma18", query = "SELECT r FROM RegistroFrecuenciaMediaHora r WHERE r.usuarioToma18 = :usuarioToma18")})
public class RegistroFrecuenciaMediaHora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_frecuencia_media_hora")
    private Integer idRegistroFrecuenciaMediaHora;
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
    @Column(name = "toma11")
    private String toma11;
    @Column(name = "usuario_toma11")
    private String usuarioToma11;
    @Column(name = "toma12")
    private String toma12;
    @Column(name = "usuario_toma12")
    private String usuarioToma12;
    @Column(name = "toma13")
    private String toma13;
    @Column(name = "usuario_toma13")
    private String usuarioToma13;
    @Column(name = "toma14")
    private String toma14;
    @Column(name = "usuario_toma14")
    private String usuarioToma14;
    @Column(name = "toma15")
    private String toma15;
    @Column(name = "usuario_toma15")
    private String usuarioToma15;
    @Column(name = "toma16")
    private String toma16;
    @Column(name = "usuario_toma16")
    private String usuarioToma16;
    @Column(name = "toma17")
    private String toma17;
    @Column(name = "usuario_toma17")
    private String usuarioToma17;
    @Column(name = "toma18")
    private String toma18;
    @Column(name = "usuario_toma18")
    private String usuarioToma18;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne
    private Registro registro;
    @JoinColumn(name = "id_parametro", referencedColumnName = "id_parametro")
    @ManyToOne
    private Parametro parametro;

    public RegistroFrecuenciaMediaHora() {
    }

    public RegistroFrecuenciaMediaHora(Integer idRegistroFrecuenciaMediaHora) {
        this.idRegistroFrecuenciaMediaHora = idRegistroFrecuenciaMediaHora;
    }

    public Integer getIdRegistroFrecuenciaMediaHora() {
        return idRegistroFrecuenciaMediaHora;
    }

    public void setIdRegistroFrecuenciaMediaHora(Integer idRegistroFrecuenciaMediaHora) {
        this.idRegistroFrecuenciaMediaHora = idRegistroFrecuenciaMediaHora;
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

    public String getToma11() {
        return toma11;
    }

    public void setToma11(String toma11) {
        this.toma11 = toma11;
    }

    public String getUsuarioToma11() {
        return usuarioToma11;
    }

    public void setUsuarioToma11(String usuarioToma11) {
        this.usuarioToma11 = usuarioToma11;
    }

    public String getToma12() {
        return toma12;
    }

    public void setToma12(String toma12) {
        this.toma12 = toma12;
    }

    public String getUsuarioToma12() {
        return usuarioToma12;
    }

    public void setUsuarioToma12(String usuarioToma12) {
        this.usuarioToma12 = usuarioToma12;
    }

    public String getToma13() {
        return toma13;
    }

    public void setToma13(String toma13) {
        this.toma13 = toma13;
    }

    public String getUsuarioToma13() {
        return usuarioToma13;
    }

    public void setUsuarioToma13(String usuarioToma13) {
        this.usuarioToma13 = usuarioToma13;
    }

    public String getToma14() {
        return toma14;
    }

    public void setToma14(String toma14) {
        this.toma14 = toma14;
    }

    public String getUsuarioToma14() {
        return usuarioToma14;
    }

    public void setUsuarioToma14(String usuarioToma14) {
        this.usuarioToma14 = usuarioToma14;
    }

    public String getToma15() {
        return toma15;
    }

    public void setToma15(String toma15) {
        this.toma15 = toma15;
    }

    public String getUsuarioToma15() {
        return usuarioToma15;
    }

    public void setUsuarioToma15(String usuarioToma15) {
        this.usuarioToma15 = usuarioToma15;
    }

    public String getToma16() {
        return toma16;
    }

    public void setToma16(String toma16) {
        this.toma16 = toma16;
    }

    public String getUsuarioToma16() {
        return usuarioToma16;
    }

    public void setUsuarioToma16(String usuarioToma16) {
        this.usuarioToma16 = usuarioToma16;
    }

    public String getToma17() {
        return toma17;
    }

    public void setToma17(String toma17) {
        this.toma17 = toma17;
    }

    public String getUsuarioToma17() {
        return usuarioToma17;
    }

    public void setUsuarioToma17(String usuarioToma17) {
        this.usuarioToma17 = usuarioToma17;
    }

    public String getToma18() {
        return toma18;
    }

    public void setToma18(String toma18) {
        this.toma18 = toma18;
    }

    public String getUsuarioToma18() {
        return usuarioToma18;
    }

    public void setUsuarioToma18(String usuarioToma18) {
        this.usuarioToma18 = usuarioToma18;
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
        hash += (idRegistroFrecuenciaMediaHora != null ? idRegistroFrecuenciaMediaHora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroFrecuenciaMediaHora)) {
            return false;
        }
        RegistroFrecuenciaMediaHora other = (RegistroFrecuenciaMediaHora) object;
        if ((this.idRegistroFrecuenciaMediaHora == null && other.idRegistroFrecuenciaMediaHora != null) || (this.idRegistroFrecuenciaMediaHora != null && !this.idRegistroFrecuenciaMediaHora.equals(other.idRegistroFrecuenciaMediaHora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroFrecuenciaMediaHora[idRegistroFrecuenciaMediaHora=" + idRegistroFrecuenciaMediaHora + "]";
    }

}
