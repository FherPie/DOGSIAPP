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
@Table(name = "noticias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Noticias.findAll", query = "SELECT n FROM Noticias n"),
    @NamedQuery(name = "Noticias.findById", query = "SELECT n FROM Noticias n WHERE n.id = :id"),
    @NamedQuery(name = "Noticias.findByCuerpo", query = "SELECT n FROM Noticias n WHERE n.cuerpo = :cuerpo"),
    @NamedQuery(name = "Noticias.findByAsunto", query = "SELECT n FROM Noticias n WHERE n.asunto = :asunto"),
    @NamedQuery(name = "Noticias.findByFecha", query = "SELECT n FROM Noticias n WHERE n.fecha = :fecha"),
    @NamedQuery(name = "Noticias.findByActivo", query = "SELECT n FROM Noticias n WHERE n.activo = :activo"),
    @NamedQuery(name = "Noticias.findByEntidad", query = "SELECT n FROM Noticias n WHERE n.entidad = :entidad"),
    @NamedQuery(name = "Noticias.findByMascota", query = "SELECT n FROM Noticias n WHERE n.mascota = :mascota")})
public class Noticias implements Serializable {
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "cuerpo")
    private String cuerpo;
    @Size(max = 2147483647)
    @Column(name = "asunto")
    private String asunto;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "activo")
    private Boolean activo;
    @Size(max = 2147483647)
    @Column(name = "entidad")
    private String entidad;
    @Size(max = 2147483647)
    @Column(name = "mascota")
    private String mascota;

    public Noticias() {
    }

    public Noticias(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
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
        if (!(object instanceof Noticias)) {
            return false;
        }
        Noticias other = (Noticias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.appdog.entidades.Noticias[ id=" + id + " ]";
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
}
