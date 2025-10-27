/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aprendiz.sena1
 */
@Entity
@Table(name = "actividad")
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a"),
    @NamedQuery(name = "Actividad.findByIdActividad", query = "SELECT a FROM Actividad a WHERE a.idActividad = :idActividad"),
    @NamedQuery(name = "Actividad.findByConsecutivo", query = "SELECT a FROM Actividad a WHERE a.consecutivo = :consecutivo"),
    @NamedQuery(name = "Actividad.findByUsuRegistro", query = "SELECT a FROM Actividad a WHERE a.usuRegistro = :usuRegistro"),
    @NamedQuery(name = "Actividad.findByFchRegistro", query = "SELECT a FROM Actividad a WHERE a.fchRegistro = :fchRegistro"),
    @NamedQuery(name = "Actividad.findByFecha", query = "SELECT a FROM Actividad a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "Actividad.findByHora", query = "SELECT a FROM Actividad a WHERE a.hora = :hora"),
    @NamedQuery(name = "Actividad.findByCierre", query = "SELECT a FROM Actividad a WHERE a.cierre = :cierre"),
    @NamedQuery(name = "Actividad.findByRevisado", query = "SELECT a FROM Actividad a WHERE a.revisado = :revisado"),
    @NamedQuery(name = "Actividad.findByTArchivo", query = "SELECT a FROM Actividad a WHERE a.tArchivo = :tArchivo"),
    @NamedQuery(name = "Actividad.findByNombreAdjunto", query = "SELECT a FROM Actividad a WHERE a.nombreAdjunto = :nombreAdjunto"),
    @NamedQuery(name = "Actividad.findByTNovedad", query = "SELECT a FROM Actividad a WHERE a.tNovedad = :tNovedad")})
public class Actividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actividad")
    private Integer idActividad;
    @Column(name = "consecutivo")
    private Integer consecutivo;
    @Column(name = "usu_registro")
    private String usuRegistro;
    @Column(name = "fch_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchRegistro;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Column(name = "cierre")
    private String cierre;
    @Column(name = "revisado")
    private String revisado;
    @Column(name = "t_archivo")
    private Integer tArchivo;
    @Column(name = "nombre_adjunto")
    private String nombreAdjunto;
    @Column(name = "t_novedad")
    private Integer tNovedad;
    @Lob
    @Column(name = "campo1")
    private String campo1;
    @Lob
    @Column(name = "campo2")
    private String campo2;
    @Lob
    @Column(name = "campo3")
    private String campo3;
    @Lob
    @Column(name = "campo4")
    private String campo4;
    @Lob
    @Column(name = "campo5")
    private String campo5;
    @Lob
    @Column(name = "campo6")
    private String campo6;
    @Lob
    @Column(name = "campo7")
    private String campo7;
    @Lob
    @Column(name = "campo8")
    private String campo8;
    @Lob
    @Column(name = "campo9")
    private String campo9;
    @Lob
    @Column(name = "campo10")
    private String campo10;
    @Lob
    @Column(name = "campo11")
    private String campo11;
    @Lob
    @Column(name = "campo12")
    private String campo12;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad")
    private Collection<Novedad> novedadCollection;

    public Actividad() {
    }

    public Actividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getUsuRegistro() {
        return usuRegistro;
    }

    public void setUsuRegistro(String usuRegistro) {
        this.usuRegistro = usuRegistro;
    }

    public Date getFchRegistro() {
        return fchRegistro;
    }

    public void setFchRegistro(Date fchRegistro) {
        this.fchRegistro = fchRegistro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getCierre() {
        return cierre;
    }

    public void setCierre(String cierre) {
        this.cierre = cierre;
    }

    public String getRevisado() {
        return revisado;
    }

    public void setRevisado(String revisado) {
        this.revisado = revisado;
    }

    public Integer getTArchivo() {
        return tArchivo;
    }

    public void setTArchivo(Integer tArchivo) {
        this.tArchivo = tArchivo;
    }

    public String getNombreAdjunto() {
        return nombreAdjunto;
    }

    public void setNombreAdjunto(String nombreAdjunto) {
        this.nombreAdjunto = nombreAdjunto;
    }

    public Integer getTNovedad() {
        return tNovedad;
    }

    public void setTNovedad(Integer tNovedad) {
        this.tNovedad = tNovedad;
    }

    public String getCampo1() {
        return campo1;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public String getCampo2() {
        return campo2;
    }

    public void setCampo2(String campo2) {
        this.campo2 = campo2;
    }

    public String getCampo3() {
        return campo3;
    }

    public void setCampo3(String campo3) {
        this.campo3 = campo3;
    }

    public String getCampo4() {
        return campo4;
    }

    public void setCampo4(String campo4) {
        this.campo4 = campo4;
    }

    public String getCampo5() {
        return campo5;
    }

    public void setCampo5(String campo5) {
        this.campo5 = campo5;
    }

    public String getCampo6() {
        return campo6;
    }

    public void setCampo6(String campo6) {
        this.campo6 = campo6;
    }

    public String getCampo7() {
        return campo7;
    }

    public void setCampo7(String campo7) {
        this.campo7 = campo7;
    }

    public String getCampo8() {
        return campo8;
    }

    public void setCampo8(String campo8) {
        this.campo8 = campo8;
    }

    public String getCampo9() {
        return campo9;
    }

    public void setCampo9(String campo9) {
        this.campo9 = campo9;
    }

    public String getCampo10() {
        return campo10;
    }

    public void setCampo10(String campo10) {
        this.campo10 = campo10;
    }

    public String getCampo11() {
        return campo11;
    }

    public void setCampo11(String campo11) {
        this.campo11 = campo11;
    }

    public String getCampo12() {
        return campo12;
    }

    public void setCampo12(String campo12) {
        this.campo12 = campo12;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<Novedad> getNovedadCollection() {
        return novedadCollection;
    }

    public void setNovedadCollection(Collection<Novedad> novedadCollection) {
        this.novedadCollection = novedadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividad != null ? idActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.idActividad == null && other.idActividad != null) || (this.idActividad != null && !this.idActividad.equals(other.idActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Servlets.Actividad[idActividad=" + idActividad + "]";
    }

}
