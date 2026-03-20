package Entidad;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "log_manual")
@NamedQueries({
    @NamedQuery(name = "LogManual.findAll", query = "SELECT l FROM LogManual l"),
    @NamedQuery(name = "LogManual.findByIdLogManual", query = "SELECT l FROM LogManual l WHERE l.idLogManual = :idLogManual"),
    @NamedQuery(name = "LogManual.findByVersionOld", query = "SELECT l FROM LogManual l WHERE l.versionOld = :versionOld"),
    @NamedQuery(name = "LogManual.findByVersionNew", query = "SELECT l FROM LogManual l WHERE l.versionNew = :versionNew"),
    @NamedQuery(name = "LogManual.findByJustificacion", query = "SELECT l FROM LogManual l WHERE l.justificacion = :justificacion"),
    @NamedQuery(name = "LogManual.findByFechaInsercion", query = "SELECT l FROM LogManual l WHERE l.fechaInsercion = :fechaInsercion"),
    @NamedQuery(name = "LogManual.findByUsuario", query = "SELECT l FROM LogManual l WHERE l.usuario = :usuario")})
public class LogManual implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_log_manual")
    private Integer idLogManual;
    @Column(name = "version_old")
    private String versionOld;
    @Column(name = "version_new")
    private String versionNew;
    @Column(name = "justificacion")
    private String justificacion;
    @Column(name = "fecha_insercion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "usuario")
    private String usuario;
    @OneToMany(mappedBy = "logManual")
    private Collection<LogManual> logManualCollection;
    @JoinColumn(name = "fk_log_manual", referencedColumnName = "id_log_manual")
    @ManyToOne
    private LogManual logManual;

    public LogManual() {
    }

    public LogManual(Integer idLogManual) {
        this.idLogManual = idLogManual;
    }

    public Integer getIdLogManual() {
        return idLogManual;
    }

    public void setIdLogManual(Integer idLogManual) {
        this.idLogManual = idLogManual;
    }

    public String getVersionOld() {
        return versionOld;
    }

    public void setVersionOld(String versionOld) {
        this.versionOld = versionOld;
    }

    public String getVersionNew() {
        return versionNew;
    }

    public void setVersionNew(String versionNew) {
        this.versionNew = versionNew;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Collection<LogManual> getLogManualCollection() {
        return logManualCollection;
    }

    public void setLogManualCollection(Collection<LogManual> logManualCollection) {
        this.logManualCollection = logManualCollection;
    }

    public LogManual getLogManual() {
        return logManual;
    }

    public void setLogManual(LogManual logManual) {
        this.logManual = logManual;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLogManual != null ? idLogManual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogManual)) {
            return false;
        }
        LogManual other = (LogManual) object;
        if ((this.idLogManual == null && other.idLogManual != null) || (this.idLogManual != null && !this.idLogManual.equals(other.idLogManual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.LogManual[idLogManual=" + idLogManual + "]";
    }

}
