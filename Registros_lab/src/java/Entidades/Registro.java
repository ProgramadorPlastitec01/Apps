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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author prog.sistemas1
 */
@Entity
@Table(name = "registro")
@NamedQueries({
    @NamedQuery(name = "Registro.findAll", query = "SELECT r FROM Registro r"),
    @NamedQuery(name = "Registro.findByIdRegistro", query = "SELECT r FROM Registro r WHERE r.idRegistro = :idRegistro"),
    @NamedQuery(name = "Registro.findByFechaTurno", query = "SELECT r FROM Registro r WHERE r.fechaTurno = :fechaTurno"),
    @NamedQuery(name = "Registro.findByLoteProducto", query = "SELECT r FROM Registro r WHERE r.loteProducto = :loteProducto"),
    @NamedQuery(name = "Registro.findByLoteCola", query = "SELECT r FROM Registro r WHERE r.loteCola = :loteCola"),
    @NamedQuery(name = "Registro.findByTurno", query = "SELECT r FROM Registro r WHERE r.turno = :turno"),
    @NamedQuery(name = "Registro.findByLoteMangaC", query = "SELECT r FROM Registro r WHERE r.loteMangaC = :loteMangaC"),
    @NamedQuery(name = "Registro.findByLoteMangaP", query = "SELECT r FROM Registro r WHERE r.loteMangaP = :loteMangaP"),
    @NamedQuery(name = "Registro.findByLoteMangaCAlt", query = "SELECT r FROM Registro r WHERE r.loteMangaCAlt = :loteMangaCAlt"),
    @NamedQuery(name = "Registro.findByLoteDtoDrcC", query = "SELECT r FROM Registro r WHERE r.loteDtoDrcC = :loteDtoDrcC"),
    @NamedQuery(name = "Registro.findByLoteDtoDrcP", query = "SELECT r FROM Registro r WHERE r.loteDtoDrcP = :loteDtoDrcP"),
    @NamedQuery(name = "Registro.findByLoteDtoCtlC", query = "SELECT r FROM Registro r WHERE r.loteDtoCtlC = :loteDtoCtlC"),
    @NamedQuery(name = "Registro.findByLoteDtoCtlP", query = "SELECT r FROM Registro r WHERE r.loteDtoCtlP = :loteDtoCtlP"),
    @NamedQuery(name = "Registro.findByLoteDtoIqeC", query = "SELECT r FROM Registro r WHERE r.loteDtoIqeC = :loteDtoIqeC"),
    @NamedQuery(name = "Registro.findByLoteDtoIqeP", query = "SELECT r FROM Registro r WHERE r.loteDtoIqeP = :loteDtoIqeP"),
    @NamedQuery(name = "Registro.findByEnsamble", query = "SELECT r FROM Registro r WHERE r.ensamble = :ensamble"),
    @NamedQuery(name = "Registro.findByEnsamble2", query = "SELECT r FROM Registro r WHERE r.ensamble2 = :ensamble2"),
    @NamedQuery(name = "Registro.findByLoteEnsamble", query = "SELECT r FROM Registro r WHERE r.loteEnsamble = :loteEnsamble"),
    @NamedQuery(name = "Registro.findByLoteEnsamble2", query = "SELECT r FROM Registro r WHERE r.loteEnsamble2 = :loteEnsamble2"),
    @NamedQuery(name = "Registro.findByLoteTinta", query = "SELECT r FROM Registro r WHERE r.loteTinta = :loteTinta"),
    @NamedQuery(name = "Registro.findByColorTinta", query = "SELECT r FROM Registro r WHERE r.colorTinta = :colorTinta"),
    @NamedQuery(name = "Registro.findByEstado", query = "SELECT r FROM Registro r WHERE r.estado = :estado"),
    @NamedQuery(name = "Registro.findByResumido", query = "SELECT r FROM Registro r WHERE r.resumido = :resumido"),
    @NamedQuery(name = "Registro.findByVerificado", query = "SELECT r FROM Registro r WHERE r.verificado = :verificado"),
    @NamedQuery(name = "Registro.findByUsuarioVerificacion", query = "SELECT r FROM Registro r WHERE r.usuarioVerificacion = :usuarioVerificacion"),
    @NamedQuery(name = "Registro.findByParametrosAlternativos", query = "SELECT r FROM Registro r WHERE r.parametrosAlternativos = :parametrosAlternativos"),
    @NamedQuery(name = "Registro.findByControlEstaciones", query = "SELECT r FROM Registro r WHERE r.controlEstaciones = :controlEstaciones"),
    @NamedQuery(name = "Registro.findByFechaRegistro", query = "SELECT r FROM Registro r WHERE r.fechaRegistro = :fechaRegistro")})
public class Registro implements Serializable {

    @OneToMany(mappedBy = "idRegistro")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro")
    private Integer idRegistro;
    @Column(name = "fecha_turno")
    @Temporal(TemporalType.DATE)
    private Date fechaTurno;
    @Column(name = "lote_producto")
    private String loteProducto;
    @Column(name = "lote_cola")
    private String loteCola;
    @Column(name = "turno")
    private String turno;
    @Column(name = "lote_manga_c")
    private String loteMangaC;
    @Column(name = "lote_manga_p")
    private String loteMangaP;
    @Column(name = "lote_manga_c_alt")
    private String loteMangaCAlt;
    @Column(name = "lote_dto_drc_c")
    private String loteDtoDrcC;
    @Column(name = "lote_dto_drc_p")
    private String loteDtoDrcP;
    @Column(name = "lote_dto_ctl_c")
    private String loteDtoCtlC;
    @Column(name = "lote_dto_ctl_p")
    private String loteDtoCtlP;
    @Column(name = "lote_dto_iqe_c")
    private String loteDtoIqeC;
    @Column(name = "lote_dto_iqe_p")
    private String loteDtoIqeP;
    @Column(name = "ensamble")
    private String ensamble;
    @Column(name = "ensamble_2")
    private String ensamble2;
    @Column(name = "lote_ensamble")
    private String loteEnsamble;
    @Column(name = "lote_ensamble_2")
    private String loteEnsamble2;
    @Column(name = "lote_tinta")
    private String loteTinta;
    @Column(name = "color_tinta")
    private String colorTinta;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "resumido")
    private Integer resumido;
    @Column(name = "verificado")
    private Integer verificado;
    @Column(name = "usuario_verificacion")
    private String usuarioVerificacion;
    @Column(name = "parametros_alternativos")
    private Integer parametrosAlternativos;
    @Column(name = "control_estaciones")
    private String controlEstaciones;
    @Lob
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "registro")
    private Collection<RegistroDespeje> registroDespejeCollection;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto producto;
    @JoinColumn(name = "id_linea", referencedColumnName = "id_linea")
    @ManyToOne
    private Linea linea;
    @OneToMany(mappedBy = "registro")
    private Collection<RegistroFrecuenciaMediaHora> registroFrecuenciaMediaHoraCollection;

    public Registro() {
    }

    public Registro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Date getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(Date fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public String getLoteProducto() {
        return loteProducto;
    }

    public void setLoteProducto(String loteProducto) {
        this.loteProducto = loteProducto;
    }

    public String getLoteCola() {
        return loteCola;
    }

    public void setLoteCola(String loteCola) {
        this.loteCola = loteCola;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getLoteMangaC() {
        return loteMangaC;
    }

    public void setLoteMangaC(String loteMangaC) {
        this.loteMangaC = loteMangaC;
    }

    public String getLoteMangaP() {
        return loteMangaP;
    }

    public void setLoteMangaP(String loteMangaP) {
        this.loteMangaP = loteMangaP;
    }

    public String getLoteMangaCAlt() {
        return loteMangaCAlt;
    }

    public void setLoteMangaCAlt(String loteMangaCAlt) {
        this.loteMangaCAlt = loteMangaCAlt;
    }

    public String getLoteDtoDrcC() {
        return loteDtoDrcC;
    }

    public void setLoteDtoDrcC(String loteDtoDrcC) {
        this.loteDtoDrcC = loteDtoDrcC;
    }

    public String getLoteDtoDrcP() {
        return loteDtoDrcP;
    }

    public void setLoteDtoDrcP(String loteDtoDrcP) {
        this.loteDtoDrcP = loteDtoDrcP;
    }

    public String getLoteDtoCtlC() {
        return loteDtoCtlC;
    }

    public void setLoteDtoCtlC(String loteDtoCtlC) {
        this.loteDtoCtlC = loteDtoCtlC;
    }

    public String getLoteDtoCtlP() {
        return loteDtoCtlP;
    }

    public void setLoteDtoCtlP(String loteDtoCtlP) {
        this.loteDtoCtlP = loteDtoCtlP;
    }

    public String getLoteDtoIqeC() {
        return loteDtoIqeC;
    }

    public void setLoteDtoIqeC(String loteDtoIqeC) {
        this.loteDtoIqeC = loteDtoIqeC;
    }

    public String getLoteDtoIqeP() {
        return loteDtoIqeP;
    }

    public void setLoteDtoIqeP(String loteDtoIqeP) {
        this.loteDtoIqeP = loteDtoIqeP;
    }

    public String getEnsamble() {
        return ensamble;
    }

    public void setEnsamble(String ensamble) {
        this.ensamble = ensamble;
    }

    public String getEnsamble2() {
        return ensamble2;
    }

    public void setEnsamble2(String ensamble2) {
        this.ensamble2 = ensamble2;
    }

    public String getLoteEnsamble() {
        return loteEnsamble;
    }

    public void setLoteEnsamble(String loteEnsamble) {
        this.loteEnsamble = loteEnsamble;
    }

    public String getLoteEnsamble2() {
        return loteEnsamble2;
    }

    public void setLoteEnsamble2(String loteEnsamble2) {
        this.loteEnsamble2 = loteEnsamble2;
    }

    public String getLoteTinta() {
        return loteTinta;
    }

    public void setLoteTinta(String loteTinta) {
        this.loteTinta = loteTinta;
    }

    public String getColorTinta() {
        return colorTinta;
    }

    public void setColorTinta(String colorTinta) {
        this.colorTinta = colorTinta;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getResumido() {
        return resumido;
    }

    public void setResumido(Integer resumido) {
        this.resumido = resumido;
    }

    public Integer getVerificado() {
        return verificado;
    }

    public void setVerificado(Integer verificado) {
        this.verificado = verificado;
    }

    public String getUsuarioVerificacion() {
        return usuarioVerificacion;
    }

    public void setUsuarioVerificacion(String usuarioVerificacion) {
        this.usuarioVerificacion = usuarioVerificacion;
    }

    public Integer getParametrosAlternativos() {
        return parametrosAlternativos;
    }

    public void setParametrosAlternativos(Integer parametrosAlternativos) {
        this.parametrosAlternativos = parametrosAlternativos;
    }

    public String getControlEstaciones() {
        return controlEstaciones;
    }

    public void setControlEstaciones(String controlEstaciones) {
        this.controlEstaciones = controlEstaciones;
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

    public Collection<RegistroDespeje> getRegistroDespejeCollection() {
        return registroDespejeCollection;
    }

    public void setRegistroDespejeCollection(Collection<RegistroDespeje> registroDespejeCollection) {
        this.registroDespejeCollection = registroDespejeCollection;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }

    public Collection<RegistroFrecuenciaMediaHora> getRegistroFrecuenciaMediaHoraCollection() {
        return registroFrecuenciaMediaHoraCollection;
    }

    public void setRegistroFrecuenciaMediaHoraCollection(Collection<RegistroFrecuenciaMediaHora> registroFrecuenciaMediaHoraCollection) {
        this.registroFrecuenciaMediaHoraCollection = registroFrecuenciaMediaHoraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistro != null ? idRegistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registro)) {
            return false;
        }
        Registro other = (Registro) object;
        if ((this.idRegistro == null && other.idRegistro != null) || (this.idRegistro != null && !this.idRegistro.equals(other.idRegistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Registro[idRegistro=" + idRegistro + "]";
    }
}
