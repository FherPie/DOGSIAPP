/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.entidades;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "codigos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codigos.findAll", query = "SELECT c FROM Codigos c"),
    @NamedQuery(name = "Codigos.findById", query = "SELECT c FROM Codigos c WHERE c.id = :id"),
    @NamedQuery(name = "Codigos.findByNombre", query = "SELECT c FROM Codigos c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Codigos.findByCodigo", query = "SELECT c FROM Codigos c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Codigos.findByDescripcion", query = "SELECT c FROM Codigos c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Codigos.findByParametros", query = "SELECT c FROM Codigos c WHERE c.parametros = :parametros")})
public class Codigos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2147483647)
    @Column(name = "parametros")
    private String parametros;
    @OneToMany(mappedBy = "grupo")
    private List<Perfil> perfilList;
    @JoinColumn(name = "maestro", referencedColumnName = "id")
    @ManyToOne
    private Maestros maestro;
    @OneToMany(mappedBy = "grupo")
    private List<Grupousuario> grupousuarioList;

    public Codigos() {
    }

    public Codigos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    @XmlTransient
    public List<Perfil> getPerfilList() {
        return perfilList;
    }

    public void setPerfilList(List<Perfil> perfilList) {
        this.perfilList = perfilList;
    }

    public Maestros getMaestro() {
        return maestro;
    }

    public void setMaestro(Maestros maestro) {
        this.maestro = maestro;
    }

    @XmlTransient
    public List<Grupousuario> getGrupousuarioList() {
        return grupousuarioList;
    }

    public void setGrupousuarioList(List<Grupousuario> grupousuarioList) {
        this.grupousuarioList = grupousuarioList;
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
        if (!(object instanceof Codigos)) {
            return false;
        }
        Codigos other = (Codigos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.appdog.entidades.Codigos[ id=" + id + " ]";
    }
    
}
