/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "ces")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ces.findAll", query = "SELECT c FROM Ces c"),
    @NamedQuery(name = "Ces.findByIdCes", query = "SELECT c FROM Ces c WHERE c.idCes = :idCes"),
    @NamedQuery(name = "Ces.findByDocumento", query = "SELECT c FROM Ces c WHERE c.documento = :documento"),
    @NamedQuery(name = "Ces.findByIdCargo", query = "SELECT c FROM Ces c WHERE c.idCargo = :idCargo"),
    @NamedQuery(name = "Ces.findByEstado", query = "SELECT c FROM Ces c WHERE c.estado = :estado"),
    @NamedQuery(name = "Ces.findByAnio", query = "SELECT c FROM Ces c WHERE c.anio = :anio"),
    @NamedQuery(name = "Ces.findByMes", query = "SELECT c FROM Ces c WHERE c.mes = :mes"),
    @NamedQuery(name = "Ces.findByEnt1", query = "SELECT c FROM Ces c WHERE c.ent1 = :ent1"),
    @NamedQuery(name = "Ces.findBySal1", query = "SELECT c FROM Ces c WHERE c.sal1 = :sal1"),
    @NamedQuery(name = "Ces.findByEnt2", query = "SELECT c FROM Ces c WHERE c.ent2 = :ent2"),
    @NamedQuery(name = "Ces.findBySal3", query = "SELECT c FROM Ces c WHERE c.sal3 = :sal3"),
    @NamedQuery(name = "Ces.findByEnt4", query = "SELECT c FROM Ces c WHERE c.ent4 = :ent4"),
    @NamedQuery(name = "Ces.findBySal4", query = "SELECT c FROM Ces c WHERE c.sal4 = :sal4"),
    @NamedQuery(name = "Ces.findByEnt5", query = "SELECT c FROM Ces c WHERE c.ent5 = :ent5"),
    @NamedQuery(name = "Ces.findBySal5", query = "SELECT c FROM Ces c WHERE c.sal5 = :sal5"),
    @NamedQuery(name = "Ces.findByEnt6", query = "SELECT c FROM Ces c WHERE c.ent6 = :ent6"),
    @NamedQuery(name = "Ces.findBySal6", query = "SELECT c FROM Ces c WHERE c.sal6 = :sal6"),
    @NamedQuery(name = "Ces.findByEnt7", query = "SELECT c FROM Ces c WHERE c.ent7 = :ent7"),
    @NamedQuery(name = "Ces.findBySal7", query = "SELECT c FROM Ces c WHERE c.sal7 = :sal7"),
    @NamedQuery(name = "Ces.findByEnt8", query = "SELECT c FROM Ces c WHERE c.ent8 = :ent8"),
    @NamedQuery(name = "Ces.findBySal8", query = "SELECT c FROM Ces c WHERE c.sal8 = :sal8"),
    @NamedQuery(name = "Ces.findByEnt9", query = "SELECT c FROM Ces c WHERE c.ent9 = :ent9"),
    @NamedQuery(name = "Ces.findBySal9", query = "SELECT c FROM Ces c WHERE c.sal9 = :sal9"),
    @NamedQuery(name = "Ces.findByEnt10", query = "SELECT c FROM Ces c WHERE c.ent10 = :ent10"),
    @NamedQuery(name = "Ces.findBySal10", query = "SELECT c FROM Ces c WHERE c.sal10 = :sal10"),
    @NamedQuery(name = "Ces.findByEnt11", query = "SELECT c FROM Ces c WHERE c.ent11 = :ent11"),
    @NamedQuery(name = "Ces.findBySal11", query = "SELECT c FROM Ces c WHERE c.sal11 = :sal11"),
    @NamedQuery(name = "Ces.findByEnt12", query = "SELECT c FROM Ces c WHERE c.ent12 = :ent12"),
    @NamedQuery(name = "Ces.findBySal12", query = "SELECT c FROM Ces c WHERE c.sal12 = :sal12"),
    @NamedQuery(name = "Ces.findByEnt13", query = "SELECT c FROM Ces c WHERE c.ent13 = :ent13"),
    @NamedQuery(name = "Ces.findBySal13", query = "SELECT c FROM Ces c WHERE c.sal13 = :sal13"),
    @NamedQuery(name = "Ces.findByEnt14", query = "SELECT c FROM Ces c WHERE c.ent14 = :ent14"),
    @NamedQuery(name = "Ces.findBySal14", query = "SELECT c FROM Ces c WHERE c.sal14 = :sal14"),
    @NamedQuery(name = "Ces.findByEnt15", query = "SELECT c FROM Ces c WHERE c.ent15 = :ent15"),
    @NamedQuery(name = "Ces.findBySal15", query = "SELECT c FROM Ces c WHERE c.sal15 = :sal15"),
    @NamedQuery(name = "Ces.findByEnt16", query = "SELECT c FROM Ces c WHERE c.ent16 = :ent16"),
    @NamedQuery(name = "Ces.findBySal16", query = "SELECT c FROM Ces c WHERE c.sal16 = :sal16"),
    @NamedQuery(name = "Ces.findByEnt17", query = "SELECT c FROM Ces c WHERE c.ent17 = :ent17"),
    @NamedQuery(name = "Ces.findBySal17", query = "SELECT c FROM Ces c WHERE c.sal17 = :sal17"),
    @NamedQuery(name = "Ces.findByEnt18", query = "SELECT c FROM Ces c WHERE c.ent18 = :ent18"),
    @NamedQuery(name = "Ces.findBySal18", query = "SELECT c FROM Ces c WHERE c.sal18 = :sal18"),
    @NamedQuery(name = "Ces.findByEnt19", query = "SELECT c FROM Ces c WHERE c.ent19 = :ent19"),
    @NamedQuery(name = "Ces.findBySal19", query = "SELECT c FROM Ces c WHERE c.sal19 = :sal19"),
    @NamedQuery(name = "Ces.findByEnt20", query = "SELECT c FROM Ces c WHERE c.ent20 = :ent20"),
    @NamedQuery(name = "Ces.findBySal20", query = "SELECT c FROM Ces c WHERE c.sal20 = :sal20"),
    @NamedQuery(name = "Ces.findByEnt21", query = "SELECT c FROM Ces c WHERE c.ent21 = :ent21"),
    @NamedQuery(name = "Ces.findBySal21", query = "SELECT c FROM Ces c WHERE c.sal21 = :sal21"),
    @NamedQuery(name = "Ces.findByEnt22", query = "SELECT c FROM Ces c WHERE c.ent22 = :ent22"),
    @NamedQuery(name = "Ces.findBySal22", query = "SELECT c FROM Ces c WHERE c.sal22 = :sal22"),
    @NamedQuery(name = "Ces.findByEnt23", query = "SELECT c FROM Ces c WHERE c.ent23 = :ent23"),
    @NamedQuery(name = "Ces.findBySal23", query = "SELECT c FROM Ces c WHERE c.sal23 = :sal23"),
    @NamedQuery(name = "Ces.findByEnt24", query = "SELECT c FROM Ces c WHERE c.ent24 = :ent24"),
    @NamedQuery(name = "Ces.findBySal24", query = "SELECT c FROM Ces c WHERE c.sal24 = :sal24"),
    @NamedQuery(name = "Ces.findByEnt25", query = "SELECT c FROM Ces c WHERE c.ent25 = :ent25"),
    @NamedQuery(name = "Ces.findBySal25", query = "SELECT c FROM Ces c WHERE c.sal25 = :sal25"),
    @NamedQuery(name = "Ces.findByEnt26", query = "SELECT c FROM Ces c WHERE c.ent26 = :ent26"),
    @NamedQuery(name = "Ces.findBySal26", query = "SELECT c FROM Ces c WHERE c.sal26 = :sal26"),
    @NamedQuery(name = "Ces.findByEnt27", query = "SELECT c FROM Ces c WHERE c.ent27 = :ent27"),
    @NamedQuery(name = "Ces.findBySal27", query = "SELECT c FROM Ces c WHERE c.sal27 = :sal27"),
    @NamedQuery(name = "Ces.findByEnt28", query = "SELECT c FROM Ces c WHERE c.ent28 = :ent28"),
    @NamedQuery(name = "Ces.findBySal28", query = "SELECT c FROM Ces c WHERE c.sal28 = :sal28"),
    @NamedQuery(name = "Ces.findByEnt29", query = "SELECT c FROM Ces c WHERE c.ent29 = :ent29"),
    @NamedQuery(name = "Ces.findBySal29", query = "SELECT c FROM Ces c WHERE c.sal29 = :sal29"),
    @NamedQuery(name = "Ces.findByEnt30", query = "SELECT c FROM Ces c WHERE c.ent30 = :ent30"),
    @NamedQuery(name = "Ces.findBySal30", query = "SELECT c FROM Ces c WHERE c.sal30 = :sal30"),
    @NamedQuery(name = "Ces.findByEnt31", query = "SELECT c FROM Ces c WHERE c.ent31 = :ent31"),
    @NamedQuery(name = "Ces.findBySal31", query = "SELECT c FROM Ces c WHERE c.sal31 = :sal31")})
public class Ces implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ces")
    private Integer idCes;
    @Column(name = "documento")
    private Integer documento;
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Lob
    @Column(name = "datos")
    private String datos;
    @Lob
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "estado")
    private String estado;
    @Column(name = "anio")
    private Integer anio;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "ent_1")
    @Temporal(TemporalType.TIME)
    private Date ent1;
    @Column(name = "sal_1")
    @Temporal(TemporalType.TIME)
    private Date sal1;
    @Column(name = "ent_2")
    @Temporal(TemporalType.TIME)
    private Date ent2;
    @Column(name = "sal_3")
    @Temporal(TemporalType.TIME)
    private Date sal3;
    @Column(name = "ent_4")
    @Temporal(TemporalType.TIME)
    private Date ent4;
    @Column(name = "sal_4")
    @Temporal(TemporalType.TIME)
    private Date sal4;
    @Column(name = "ent_5")
    @Temporal(TemporalType.TIME)
    private Date ent5;
    @Column(name = "sal_5")
    @Temporal(TemporalType.TIME)
    private Date sal5;
    @Column(name = "ent_6")
    @Temporal(TemporalType.TIME)
    private Date ent6;
    @Column(name = "sal_6")
    @Temporal(TemporalType.TIME)
    private Date sal6;
    @Column(name = "ent_7")
    @Temporal(TemporalType.TIME)
    private Date ent7;
    @Column(name = "sal_7")
    @Temporal(TemporalType.TIME)
    private Date sal7;
    @Column(name = "ent_8")
    @Temporal(TemporalType.TIME)
    private Date ent8;
    @Column(name = "sal_8")
    @Temporal(TemporalType.TIME)
    private Date sal8;
    @Column(name = "ent_9")
    @Temporal(TemporalType.TIME)
    private Date ent9;
    @Column(name = "sal_9")
    @Temporal(TemporalType.TIME)
    private Date sal9;
    @Column(name = "ent_10")
    @Temporal(TemporalType.TIME)
    private Date ent10;
    @Column(name = "sal_10")
    @Temporal(TemporalType.TIME)
    private Date sal10;
    @Column(name = "ent_11")
    @Temporal(TemporalType.TIME)
    private Date ent11;
    @Column(name = "sal_11")
    @Temporal(TemporalType.TIME)
    private Date sal11;
    @Column(name = "ent_12")
    @Temporal(TemporalType.TIME)
    private Date ent12;
    @Column(name = "sal_12")
    @Temporal(TemporalType.TIME)
    private Date sal12;
    @Column(name = "ent_13")
    @Temporal(TemporalType.TIME)
    private Date ent13;
    @Column(name = "sal_13")
    @Temporal(TemporalType.TIME)
    private Date sal13;
    @Column(name = "ent_14")
    @Temporal(TemporalType.TIME)
    private Date ent14;
    @Column(name = "sal_14")
    @Temporal(TemporalType.TIME)
    private Date sal14;
    @Column(name = "ent_15")
    @Temporal(TemporalType.TIME)
    private Date ent15;
    @Column(name = "sal_15")
    @Temporal(TemporalType.TIME)
    private Date sal15;
    @Column(name = "ent_16")
    @Temporal(TemporalType.TIME)
    private Date ent16;
    @Column(name = "sal_16")
    @Temporal(TemporalType.TIME)
    private Date sal16;
    @Column(name = "ent_17")
    @Temporal(TemporalType.TIME)
    private Date ent17;
    @Column(name = "sal_17")
    @Temporal(TemporalType.TIME)
    private Date sal17;
    @Column(name = "ent_18")
    @Temporal(TemporalType.TIME)
    private Date ent18;
    @Column(name = "sal_18")
    @Temporal(TemporalType.TIME)
    private Date sal18;
    @Column(name = "ent_19")
    @Temporal(TemporalType.TIME)
    private Date ent19;
    @Column(name = "sal_19")
    @Temporal(TemporalType.TIME)
    private Date sal19;
    @Column(name = "ent_20")
    @Temporal(TemporalType.TIME)
    private Date ent20;
    @Column(name = "sal_20")
    @Temporal(TemporalType.TIME)
    private Date sal20;
    @Column(name = "ent_21")
    @Temporal(TemporalType.TIME)
    private Date ent21;
    @Column(name = "sal_21")
    @Temporal(TemporalType.TIME)
    private Date sal21;
    @Column(name = "ent_22")
    @Temporal(TemporalType.TIME)
    private Date ent22;
    @Column(name = "sal_22")
    @Temporal(TemporalType.TIME)
    private Date sal22;
    @Column(name = "ent_23")
    @Temporal(TemporalType.TIME)
    private Date ent23;
    @Column(name = "sal_23")
    @Temporal(TemporalType.TIME)
    private Date sal23;
    @Column(name = "ent_24")
    @Temporal(TemporalType.TIME)
    private Date ent24;
    @Column(name = "sal_24")
    @Temporal(TemporalType.TIME)
    private Date sal24;
    @Column(name = "ent_25")
    @Temporal(TemporalType.TIME)
    private Date ent25;
    @Column(name = "sal_25")
    @Temporal(TemporalType.TIME)
    private Date sal25;
    @Column(name = "ent_26")
    @Temporal(TemporalType.TIME)
    private Date ent26;
    @Column(name = "sal_26")
    @Temporal(TemporalType.TIME)
    private Date sal26;
    @Column(name = "ent_27")
    @Temporal(TemporalType.TIME)
    private Date ent27;
    @Column(name = "sal_27")
    @Temporal(TemporalType.TIME)
    private Date sal27;
    @Column(name = "ent_28")
    @Temporal(TemporalType.TIME)
    private Date ent28;
    @Column(name = "sal_28")
    @Temporal(TemporalType.TIME)
    private Date sal28;
    @Column(name = "ent_29")
    @Temporal(TemporalType.TIME)
    private Date ent29;
    @Column(name = "sal_29")
    @Temporal(TemporalType.TIME)
    private Date sal29;
    @Column(name = "ent_30")
    @Temporal(TemporalType.TIME)
    private Date ent30;
    @Column(name = "sal_30")
    @Temporal(TemporalType.TIME)
    private Date sal30;
    @Column(name = "ent_31")
    @Temporal(TemporalType.TIME)
    private Date ent31;
    @Column(name = "sal_31")
    @Temporal(TemporalType.TIME)
    private Date sal31;

    public Ces() {
    }

    public Ces(Integer idCes) {
        this.idCes = idCes;
    }

    public Integer getIdCes() {
        return idCes;
    }

    public void setIdCes(Integer idCes) {
        this.idCes = idCes;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Date getEnt1() {
        return ent1;
    }

    public void setEnt1(Date ent1) {
        this.ent1 = ent1;
    }

    public Date getSal1() {
        return sal1;
    }

    public void setSal1(Date sal1) {
        this.sal1 = sal1;
    }

    public Date getEnt2() {
        return ent2;
    }

    public void setEnt2(Date ent2) {
        this.ent2 = ent2;
    }

    public Date getSal3() {
        return sal3;
    }

    public void setSal3(Date sal3) {
        this.sal3 = sal3;
    }

    public Date getEnt4() {
        return ent4;
    }

    public void setEnt4(Date ent4) {
        this.ent4 = ent4;
    }

    public Date getSal4() {
        return sal4;
    }

    public void setSal4(Date sal4) {
        this.sal4 = sal4;
    }

    public Date getEnt5() {
        return ent5;
    }

    public void setEnt5(Date ent5) {
        this.ent5 = ent5;
    }

    public Date getSal5() {
        return sal5;
    }

    public void setSal5(Date sal5) {
        this.sal5 = sal5;
    }

    public Date getEnt6() {
        return ent6;
    }

    public void setEnt6(Date ent6) {
        this.ent6 = ent6;
    }

    public Date getSal6() {
        return sal6;
    }

    public void setSal6(Date sal6) {
        this.sal6 = sal6;
    }

    public Date getEnt7() {
        return ent7;
    }

    public void setEnt7(Date ent7) {
        this.ent7 = ent7;
    }

    public Date getSal7() {
        return sal7;
    }

    public void setSal7(Date sal7) {
        this.sal7 = sal7;
    }

    public Date getEnt8() {
        return ent8;
    }

    public void setEnt8(Date ent8) {
        this.ent8 = ent8;
    }

    public Date getSal8() {
        return sal8;
    }

    public void setSal8(Date sal8) {
        this.sal8 = sal8;
    }

    public Date getEnt9() {
        return ent9;
    }

    public void setEnt9(Date ent9) {
        this.ent9 = ent9;
    }

    public Date getSal9() {
        return sal9;
    }

    public void setSal9(Date sal9) {
        this.sal9 = sal9;
    }

    public Date getEnt10() {
        return ent10;
    }

    public void setEnt10(Date ent10) {
        this.ent10 = ent10;
    }

    public Date getSal10() {
        return sal10;
    }

    public void setSal10(Date sal10) {
        this.sal10 = sal10;
    }

    public Date getEnt11() {
        return ent11;
    }

    public void setEnt11(Date ent11) {
        this.ent11 = ent11;
    }

    public Date getSal11() {
        return sal11;
    }

    public void setSal11(Date sal11) {
        this.sal11 = sal11;
    }

    public Date getEnt12() {
        return ent12;
    }

    public void setEnt12(Date ent12) {
        this.ent12 = ent12;
    }

    public Date getSal12() {
        return sal12;
    }

    public void setSal12(Date sal12) {
        this.sal12 = sal12;
    }

    public Date getEnt13() {
        return ent13;
    }

    public void setEnt13(Date ent13) {
        this.ent13 = ent13;
    }

    public Date getSal13() {
        return sal13;
    }

    public void setSal13(Date sal13) {
        this.sal13 = sal13;
    }

    public Date getEnt14() {
        return ent14;
    }

    public void setEnt14(Date ent14) {
        this.ent14 = ent14;
    }

    public Date getSal14() {
        return sal14;
    }

    public void setSal14(Date sal14) {
        this.sal14 = sal14;
    }

    public Date getEnt15() {
        return ent15;
    }

    public void setEnt15(Date ent15) {
        this.ent15 = ent15;
    }

    public Date getSal15() {
        return sal15;
    }

    public void setSal15(Date sal15) {
        this.sal15 = sal15;
    }

    public Date getEnt16() {
        return ent16;
    }

    public void setEnt16(Date ent16) {
        this.ent16 = ent16;
    }

    public Date getSal16() {
        return sal16;
    }

    public void setSal16(Date sal16) {
        this.sal16 = sal16;
    }

    public Date getEnt17() {
        return ent17;
    }

    public void setEnt17(Date ent17) {
        this.ent17 = ent17;
    }

    public Date getSal17() {
        return sal17;
    }

    public void setSal17(Date sal17) {
        this.sal17 = sal17;
    }

    public Date getEnt18() {
        return ent18;
    }

    public void setEnt18(Date ent18) {
        this.ent18 = ent18;
    }

    public Date getSal18() {
        return sal18;
    }

    public void setSal18(Date sal18) {
        this.sal18 = sal18;
    }

    public Date getEnt19() {
        return ent19;
    }

    public void setEnt19(Date ent19) {
        this.ent19 = ent19;
    }

    public Date getSal19() {
        return sal19;
    }

    public void setSal19(Date sal19) {
        this.sal19 = sal19;
    }

    public Date getEnt20() {
        return ent20;
    }

    public void setEnt20(Date ent20) {
        this.ent20 = ent20;
    }

    public Date getSal20() {
        return sal20;
    }

    public void setSal20(Date sal20) {
        this.sal20 = sal20;
    }

    public Date getEnt21() {
        return ent21;
    }

    public void setEnt21(Date ent21) {
        this.ent21 = ent21;
    }

    public Date getSal21() {
        return sal21;
    }

    public void setSal21(Date sal21) {
        this.sal21 = sal21;
    }

    public Date getEnt22() {
        return ent22;
    }

    public void setEnt22(Date ent22) {
        this.ent22 = ent22;
    }

    public Date getSal22() {
        return sal22;
    }

    public void setSal22(Date sal22) {
        this.sal22 = sal22;
    }

    public Date getEnt23() {
        return ent23;
    }

    public void setEnt23(Date ent23) {
        this.ent23 = ent23;
    }

    public Date getSal23() {
        return sal23;
    }

    public void setSal23(Date sal23) {
        this.sal23 = sal23;
    }

    public Date getEnt24() {
        return ent24;
    }

    public void setEnt24(Date ent24) {
        this.ent24 = ent24;
    }

    public Date getSal24() {
        return sal24;
    }

    public void setSal24(Date sal24) {
        this.sal24 = sal24;
    }

    public Date getEnt25() {
        return ent25;
    }

    public void setEnt25(Date ent25) {
        this.ent25 = ent25;
    }

    public Date getSal25() {
        return sal25;
    }

    public void setSal25(Date sal25) {
        this.sal25 = sal25;
    }

    public Date getEnt26() {
        return ent26;
    }

    public void setEnt26(Date ent26) {
        this.ent26 = ent26;
    }

    public Date getSal26() {
        return sal26;
    }

    public void setSal26(Date sal26) {
        this.sal26 = sal26;
    }

    public Date getEnt27() {
        return ent27;
    }

    public void setEnt27(Date ent27) {
        this.ent27 = ent27;
    }

    public Date getSal27() {
        return sal27;
    }

    public void setSal27(Date sal27) {
        this.sal27 = sal27;
    }

    public Date getEnt28() {
        return ent28;
    }

    public void setEnt28(Date ent28) {
        this.ent28 = ent28;
    }

    public Date getSal28() {
        return sal28;
    }

    public void setSal28(Date sal28) {
        this.sal28 = sal28;
    }

    public Date getEnt29() {
        return ent29;
    }

    public void setEnt29(Date ent29) {
        this.ent29 = ent29;
    }

    public Date getSal29() {
        return sal29;
    }

    public void setSal29(Date sal29) {
        this.sal29 = sal29;
    }

    public Date getEnt30() {
        return ent30;
    }

    public void setEnt30(Date ent30) {
        this.ent30 = ent30;
    }

    public Date getSal30() {
        return sal30;
    }

    public void setSal30(Date sal30) {
        this.sal30 = sal30;
    }

    public Date getEnt31() {
        return ent31;
    }

    public void setEnt31(Date ent31) {
        this.ent31 = ent31;
    }

    public Date getSal31() {
        return sal31;
    }

    public void setSal31(Date sal31) {
        this.sal31 = sal31;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCes != null ? idCes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ces)) {
            return false;
        }
        Ces other = (Ces) object;
        if ((this.idCes == null && other.idCes != null) || (this.idCes != null && !this.idCes.equals(other.idCes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.Ces[ idCes=" + idCes + " ]";
    }
    
}
