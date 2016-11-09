/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author andres
 */
@Entity
@Table(name = "menusistema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menusistema.findAll", query = "SELECT m FROM Menusistema m"),
    @NamedQuery(name = "Menusistema.findById", query = "SELECT m FROM Menusistema m WHERE m.id = :id"),
    @NamedQuery(name = "Menusistema.findByModuloid", query = "SELECT m FROM Menusistema m WHERE m.moduloid = :moduloid"),
    @NamedQuery(name = "Menusistema.findByTexto", query = "SELECT m FROM Menusistema m WHERE m.texto = :texto"),
    @NamedQuery(name = "Menusistema.findByFormulario", query = "SELECT m FROM Menusistema m WHERE m.formulario = :formulario")})
public class Menusistema implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "moduloid")
    private Integer moduloid;
    @Size(max = 2147483647)
    @Column(name = "texto")
    private String texto;
    @Size(max = 2147483647)
    @Column(name = "formulario")
    private String formulario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
    private List<Perfil> perfilList;
    @OneToMany(mappedBy = "idpadre")
    private List<Menusistema> menusistemaList;
    @JoinColumn(name = "idpadre", referencedColumnName = "id")
    @ManyToOne
    private Menusistema idpadre;

    public Menusistema() {
    }

    public Menusistema(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModuloid() {
        return moduloid;
    }

    public void setModuloid(Integer moduloid) {
        this.moduloid = moduloid;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    @XmlTransient
    public List<Perfil> getPerfilList() {
        return perfilList;
    }

    public void setPerfilList(List<Perfil> perfilList) {
        this.perfilList = perfilList;
    }

    @XmlTransient
    public List<Menusistema> getMenusistemaList() {
        return menusistemaList;
    }

    public void setMenusistemaList(List<Menusistema> menusistemaList) {
        this.menusistemaList = menusistemaList;
    }

    public Menusistema getIdpadre() {
        return idpadre;
    }

    public void setIdpadre(Menusistema idpadre) {
        this.idpadre = idpadre;
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
        if (!(object instanceof Menusistema)) {
            return false;
        }
        Menusistema other = (Menusistema) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.appdog.entidades.Menusistema[ id=" + id + " ]";
    }
    
}
