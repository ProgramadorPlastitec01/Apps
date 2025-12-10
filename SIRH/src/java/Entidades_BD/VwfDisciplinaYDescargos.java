/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "vwf_disciplina_y_descargos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VwfDisciplinaYDescargos.findAll", query = "SELECT v FROM VwfDisciplinaYDescargos v"),
    @NamedQuery(name = "VwfDisciplinaYDescargos.findByDocumento", query = "SELECT v FROM VwfDisciplinaYDescargos v WHERE v.documento = :documento"),
    @NamedQuery(name = "VwfDisciplinaYDescargos.findByApellidos", query = "SELECT v FROM VwfDisciplinaYDescargos v WHERE v.apellidos = :apellidos"),
    @NamedQuery(name = "VwfDisciplinaYDescargos.findByNombre", query = "SELECT v FROM VwfDisciplinaYDescargos v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VwfDisciplinaYDescargos.findByFecha", query = "SELECT v FROM VwfDisciplinaYDescargos v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "VwfDisciplinaYDescargos.findByTipo", query = "SELECT v FROM VwfDisciplinaYDescargos v WHERE v.tipo = :tipo"),
    @NamedQuery(name = "VwfDisciplinaYDescargos.findByD\u00edas", query = "SELECT v FROM VwfDisciplinaYDescargos v WHERE v.d\u00edas = :d\u00edas")})
public class VwfDisciplinaYDescargos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "Documento")
    private String documento;
    @Column(name = "Apellidos")
    private String apellidos;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Fecha")
    private String fecha;
    @Column(name = "Tipo")
    private String tipo;
    @Lob
    @Column(name = "Motivo")
    private String motivo;
    @Column(name = "# D\u00edas")
    private String días;
    @Lob
    @Column(name = "Observaci\u00f3n")
    private String observación;

    public VwfDisciplinaYDescargos() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDías() {
        return días;
    }

    public void setDías(String días) {
        this.días = días;
    }

    public String getObservación() {
        return observación;
    }

    public void setObservación(String observación) {
        this.observación = observación;
    }
    
}
