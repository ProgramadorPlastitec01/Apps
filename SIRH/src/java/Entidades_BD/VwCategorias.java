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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "vw_categorias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwCategorias.findAll", query = "SELECT v FROM VwCategorias v"),
    @NamedQuery(name = "VwCategorias.findByTipo", query = "SELECT v FROM VwCategorias v WHERE v.tipo = :tipo"),
    @NamedQuery(name = "VwCategorias.findByCategoria", query = "SELECT v FROM VwCategorias v WHERE v.categoria = :categoria"),
    @NamedQuery(name = "VwCategorias.findByMaternidad", query = "SELECT v FROM VwCategorias v WHERE v.maternidad = :maternidad")})
public class VwCategorias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Tipo")
    private String tipo;
    @Column(name = "Categoria")
    private String categoria;
    @Basic(optional = false)
    @Column(name = "Maternidad")
    private String maternidad;

    public VwCategorias() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMaternidad() {
        return maternidad;
    }

    public void setMaternidad(String maternidad) {
        this.maternidad = maternidad;
    }
    
}
