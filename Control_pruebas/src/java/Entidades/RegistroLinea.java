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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author prog.sistemas1
 */
@Entity
@Table(name = "registro_linea")
@NamedQueries({
    @NamedQuery(name = "RegistroLinea.findAll", query = "SELECT r FROM RegistroLinea r"),
    @NamedQuery(name = "RegistroLinea.findByIdRegistroLinea", query = "SELECT r FROM RegistroLinea r WHERE r.idRegistroLinea = :idRegistroLinea")})
public class RegistroLinea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_linea")
    private Integer idRegistroLinea;
    @Basic(optional = false)
    @Lob
    @Column(name = "lineas_trabajo")
    private String lineasTrabajo;
    @Basic(optional = false)
    @Lob
    @Column(name = "lineas_paradas")
    private String lineasParadas;
    @Basic(optional = false)
    @Lob
    @Column(name = "usuario_registro")
    private String usuarioRegistro;
    @Basic(optional = false)
    @Lob
    @Column(name = "fecha_registro")
    private String fechaRegistro;
    @JoinColumn(name = "id_registro", referencedColumnName = "id_registro")
    @ManyToOne(optional = false)
    private Registro registro;
    @JoinColumn(name = "id_tipo_linea", referencedColumnName = "id_tipo_linea")
    @ManyToOne(optional = false)
    private TipoLinea tipoLinea;

    public RegistroLinea() {
    }

    public RegistroLinea(Integer idRegistroLinea) {
        this.idRegistroLinea = idRegistroLinea;
    }

    public RegistroLinea(Integer idRegistroLinea, String lineasTrabajo, String lineasParadas, String usuarioRegistro, String fechaRegistro) {
        this.idRegistroLinea = idRegistroLinea;
        this.lineasTrabajo = lineasTrabajo;
        this.lineasParadas = lineasParadas;
        this.usuarioRegistro = usuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdRegistroLinea() {
        return idRegistroLinea;
    }

    public void setIdRegistroLinea(Integer idRegistroLinea) {
        this.idRegistroLinea = idRegistroLinea;
    }

    public String getLineasTrabajo() {
        return lineasTrabajo;
    }

    public void setLineasTrabajo(String lineasTrabajo) {
        this.lineasTrabajo = lineasTrabajo;
    }

    public String getLineasParadas() {
        return lineasParadas;
    }

    public void setLineasParadas(String lineasParadas) {
        this.lineasParadas = lineasParadas;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public TipoLinea getTipoLinea() {
        return tipoLinea;
    }

    public void setTipoLinea(TipoLinea tipoLinea) {
        this.tipoLinea = tipoLinea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroLinea != null ? idRegistroLinea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroLinea)) {
            return false;
        }
        RegistroLinea other = (RegistroLinea) object;
        if ((this.idRegistroLinea == null && other.idRegistroLinea != null) || (this.idRegistroLinea != null && !this.idRegistroLinea.equals(other.idRegistroLinea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroLinea[idRegistroLinea=" + idRegistroLinea + "]";
    }

}
