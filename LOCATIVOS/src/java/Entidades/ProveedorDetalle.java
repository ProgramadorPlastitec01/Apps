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
 * @author Aprendiz.sena1
 */
@Entity
@Table(name = "proveedor_detalle")
@NamedQueries({
    @NamedQuery(name = "ProveedorDetalle.findAll", query = "SELECT p FROM ProveedorDetalle p"),
    @NamedQuery(name = "ProveedorDetalle.findByIdProveedorDetalle", query = "SELECT p FROM ProveedorDetalle p WHERE p.idProveedorDetalle = :idProveedorDetalle")})
public class ProveedorDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proveedor_detalle")
    private Integer idProveedorDetalle;
    @Basic(optional = false)
    @Lob
    @Column(name = "personal")
    private String personal;
    @Basic(optional = false)
    @Lob
    @Column(name = "carta")
    private String carta;
    @Basic(optional = false)
    @Lob
    @Column(name = "solicitudes")
    private String solicitudes;
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    @ManyToOne(optional = false)
    private Proveedor proveedor;
    @JoinColumn(name = "id_programacion", referencedColumnName = "id_programacion")
    @ManyToOne(optional = false)
    private Programacion programacion;

    public ProveedorDetalle() {
    }

    public ProveedorDetalle(Integer idProveedorDetalle) {
        this.idProveedorDetalle = idProveedorDetalle;
    }

    public ProveedorDetalle(Integer idProveedorDetalle, String personal, String carta, String solicitudes) {
        this.idProveedorDetalle = idProveedorDetalle;
        this.personal = personal;
        this.carta = carta;
        this.solicitudes = solicitudes;
    }

    public Integer getIdProveedorDetalle() {
        return idProveedorDetalle;
    }

    public void setIdProveedorDetalle(Integer idProveedorDetalle) {
        this.idProveedorDetalle = idProveedorDetalle;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getCarta() {
        return carta;
    }

    public void setCarta(String carta) {
        this.carta = carta;
    }

    public String getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(String solicitudes) {
        this.solicitudes = solicitudes;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedorDetalle != null ? idProveedorDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProveedorDetalle)) {
            return false;
        }
        ProveedorDetalle other = (ProveedorDetalle) object;
        if ((this.idProveedorDetalle == null && other.idProveedorDetalle != null) || (this.idProveedorDetalle != null && !this.idProveedorDetalle.equals(other.idProveedorDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ProveedorDetalle[idProveedorDetalle=" + idProveedorDetalle + "]";
    }

}
