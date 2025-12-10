/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "menu_opcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MenuOpcion.findAll", query = "SELECT m FROM MenuOpcion m"),
    @NamedQuery(name = "MenuOpcion.findByIdMenuOpcion", query = "SELECT m FROM MenuOpcion m WHERE m.idMenuOpcion = :idMenuOpcion"),
    @NamedQuery(name = "MenuOpcion.findByPermisos", query = "SELECT m FROM MenuOpcion m WHERE m.permisos = :permisos")})
public class MenuOpcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_menu_opcion")
    private Integer idMenuOpcion;
    @Basic(optional = false)
    @Column(name = "permisos")
    private String permisos;
    @JoinColumn(name = "id_menu", referencedColumnName = "id_menu")
    @ManyToOne(optional = false)
    private Menu idMenu;
    @JoinColumn(name = "id_opcion", referencedColumnName = "id_opcion")
    @ManyToOne(optional = false)
    private Opcion idOpcion;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public MenuOpcion() {
    }

    public MenuOpcion(Integer idMenuOpcion) {
        this.idMenuOpcion = idMenuOpcion;
    }

    public MenuOpcion(Integer idMenuOpcion, String permisos) {
        this.idMenuOpcion = idMenuOpcion;
        this.permisos = permisos;
    }

    public Integer getIdMenuOpcion() {
        return idMenuOpcion;
    }

    public void setIdMenuOpcion(Integer idMenuOpcion) {
        this.idMenuOpcion = idMenuOpcion;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    public Menu getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Menu idMenu) {
        this.idMenu = idMenu;
    }

    public Opcion getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Opcion idOpcion) {
        this.idOpcion = idOpcion;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenuOpcion != null ? idMenuOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuOpcion)) {
            return false;
        }
        MenuOpcion other = (MenuOpcion) object;
        if ((this.idMenuOpcion == null && other.idMenuOpcion != null) || (this.idMenuOpcion != null && !this.idMenuOpcion.equals(other.idMenuOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.MenuOpcion[ idMenuOpcion=" + idMenuOpcion + " ]";
    }
    
}
