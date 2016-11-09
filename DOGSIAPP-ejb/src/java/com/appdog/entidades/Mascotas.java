/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.entidades;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andres
 */
@Entity
@Table(name = "mascotas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mascotas.findAll", query = "SELECT m FROM Mascotas m"),
    @NamedQuery(name = "Mascotas.findById", query = "SELECT m FROM Mascotas m WHERE m.id = :id"),
    @NamedQuery(name = "Mascotas.findByNombres", query = "SELECT m FROM Mascotas m WHERE m.nombres = :nombres"),
    @NamedQuery(name = "Mascotas.findByApellidos", query = "SELECT m FROM Mascotas m WHERE m.apellidos = :apellidos"),
    @NamedQuery(name = "Mascotas.findByRaza", query = "SELECT m FROM Mascotas m WHERE m.raza = :raza"),
    @NamedQuery(name = "Mascotas.findByCollar", query = "SELECT m FROM Mascotas m WHERE m.collar = :collar"),
    @NamedQuery(name = "Mascotas.findByColor1", query = "SELECT m FROM Mascotas m WHERE m.color1 = :color1"),
    @NamedQuery(name = "Mascotas.findByColor2", query = "SELECT m FROM Mascotas m WHERE m.color2 = :color2"),
    @NamedQuery(name = "Mascotas.findByTipopelo", query = "SELECT m FROM Mascotas m WHERE m.tipopelo = :tipopelo"),
    @NamedQuery(name = "Mascotas.findByFechanac", query = "SELECT m FROM Mascotas m WHERE m.fechanac = :fechanac"),
    @NamedQuery(name = "Mascotas.findByActivo", query = "SELECT m FROM Mascotas m WHERE m.activo = :activo"),
    @NamedQuery(name = "Mascotas.findByPin", query = "SELECT m FROM Mascotas m WHERE m.pin = :pin"),
    @NamedQuery(name = "Mascotas.findByDueno", query = "SELECT m FROM Mascotas m WHERE m.dueno = :dueno"),
    @NamedQuery(name = "Mascotas.findByResponsable", query = "SELECT m FROM Mascotas m WHERE m.responsable = :responsable")})
public class Mascotas implements Serializable {
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Size(max = 2147483647)
    @Column(name = "observacion")
    private String observacion;
    @Size(max = 2147483647)
    @Column(name = "status")
    private String status;
    @Size(max = 2147483647)
    @Column(name = "edad")
    private String edad;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 2147483647)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 2147483647)
    @Column(name = "raza")
    private String raza;
    @Column(name = "collar")
    private Integer collar;
    @Size(max = 2147483647)
    @Column(name = "color1")
    private String color1;
    @Size(max = 2147483647)
    @Column(name = "color2")
    private String color2;
    @Size(max = 2147483647)
    @Column(name = "tipopelo")
    private String tipopelo;
    @Column(name = "fechanac")
    @Temporal(TemporalType.DATE)
    private Date fechanac;
    @Column(name = "activo")
    private Boolean activo;
    @Size(max = 2147483647)
    @Column(name = "pin")
    private String pin;
    @Size(max = 2147483647)
    @Column(name = "dueno")
    private String dueno;
    @Size(max = 2147483647)
    @Column(name = "responsable")
    private String responsable;

    public Mascotas() {
    }

    public Mascotas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getCollar() {
        return collar;
    }

    public void setCollar(Integer collar) {
        this.collar = collar;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getTipopelo() {
        return tipopelo;
    }

    public void setTipopelo(String tipopelo) {
        this.tipopelo = tipopelo;
    }

    public Date getFechanac() {
        return fechanac;
    }

    public void setFechanac(Date fechanac) {
        this.fechanac = fechanac;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mascotas)) {
            return false;
        }
        Mascotas other = (Mascotas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.appdog.entidades.Mascotas[ id=" + id + " ]";
    }


    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
}
