/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "opcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Opcion.findAll", query = "SELECT o FROM Opcion o"),
    @NamedQuery(name = "Opcion.findByIdOpcion", query = "SELECT o FROM Opcion o WHERE o.idOpcion = :idOpcion"),
    @NamedQuery(name = "Opcion.findByNombre", query = "SELECT o FROM Opcion o WHERE o.nombre = :nombre"),
    @NamedQuery(name = "Opcion.findByPermisos", query = "SELECT o FROM Opcion o WHERE o.permisos = :permisos"),
    @NamedQuery(name = "Opcion.findByEstado", query = "SELECT o FROM Opcion o WHERE o.estado = :estado")})
public class Opcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_opcion")
    private Integer idOpcion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "html_opcion")
    private String htmlOpcion;
    @Basic(optional = false)
    @Column(name = "permisos")
    private String permisos;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOpcion")
    private Collection<MenuOpcion> menuOpcionCollection;
    @JoinColumn(name = "id_menu", referencedColumnName = "id_menu")
    @ManyToOne(optional = false)
    private Menu idMenu;

    public Opcion() {
    }

    public Opcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public Opcion(Integer idOpcion, String nombre, String htmlOpcion, String permisos, int estado) {
        this.idOpcion = idOpcion;
        this.nombre = nombre;
        this.htmlOpcion = htmlOpcion;
        this.permisos = permisos;
        this.estado = estado;
    }

    public Integer getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHtmlOpcion() {
        return htmlOpcion;
    }

    public void setHtmlOpcion(String htmlOpcion) {
        this.htmlOpcion = htmlOpcion;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<MenuOpcion> getMenuOpcionCollection() {
        return menuOpcionCollection;
    }

    public void setMenuOpcionCollection(Collection<MenuOpcion> menuOpcionCollection) {
        this.menuOpcionCollection = menuOpcionCollection;
    }

    public Menu getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Menu idMenu) {
        this.idMenu = idMenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpcion != null ? idOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Opcion)) {
            return false;
        }
        Opcion other = (Opcion) object;
        if ((this.idOpcion == null && other.idOpcion != null) || (this.idOpcion != null && !this.idOpcion.equals(other.idOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.Opcion[ idOpcion=" + idOpcion + " ]";
    }
    
}
