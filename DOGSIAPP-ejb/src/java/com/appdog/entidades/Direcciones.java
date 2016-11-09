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
@Table(name = "direcciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direcciones.findAll", query = "SELECT d FROM Direcciones d"),
    @NamedQuery(name = "Direcciones.findById", query = "SELECT d FROM Direcciones d WHERE d.id = :id"),
    @NamedQuery(name = "Direcciones.findByPrimaria", query = "SELECT d FROM Direcciones d WHERE d.primaria = :primaria"),
    @NamedQuery(name = "Direcciones.findBySecundaria", query = "SELECT d FROM Direcciones d WHERE d.secundaria = :secundaria"),
    @NamedQuery(name = "Direcciones.findByNumero", query = "SELECT d FROM Direcciones d WHERE d.numero = :numero"),
    @NamedQuery(name = "Direcciones.findByPiso", query = "SELECT d FROM Direcciones d WHERE d.piso = :piso"),
    @NamedQuery(name = "Direcciones.findByReferencia", query = "SELECT d FROM Direcciones d WHERE d.referencia = :referencia"),
    @NamedQuery(name = "Direcciones.findByCodigopostal", query = "SELECT d FROM Direcciones d WHERE d.codigopostal = :codigopostal"),
    @NamedQuery(name = "Direcciones.findByTelefono1", query = "SELECT d FROM Direcciones d WHERE d.telefono1 = :telefono1"),
    @NamedQuery(name = "Direcciones.findByTelefono2", query = "SELECT d FROM Direcciones d WHERE d.telefono2 = :telefono2")})
public class Direcciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "primaria")
    private String primaria;
    @Size(max = 2147483647)
    @Column(name = "secundaria")
    private String secundaria;
    @Size(max = 2147483647)
    @Column(name = "numero")
    private String numero;
    @Size(max = 2147483647)
    @Column(name = "piso")
    private String piso;
    @Size(max = 2147483647)
    @Column(name = "referencia")
    private String referencia;
    @Size(max = 2147483647)
    @Column(name = "codigopostal")
    private String codigopostal;
    @Size(max = 2147483647)
    @Column(name = "telefono1")
    private String telefono1;
    @Size(max = 2147483647)
    @Column(name = "telefono2")
    private String telefono2;
    @JoinColumn(name = "parroquia", referencedColumnName = "id")
    @ManyToOne
    private Ubicaciones parroquia;
    @OneToMany(mappedBy = "direccion")
    private List<Entidades> entidadesList;

    public Direcciones() {
    }

    public Direcciones(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrimaria() {
        return primaria;
    }

    public void setPrimaria(String primaria) {
        this.primaria = primaria;
    }

    public String getSecundaria() {
        return secundaria;
    }

    public void setSecundaria(String secundaria) {
        this.secundaria = secundaria;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public Ubicaciones getParroquia() {
        return parroquia;
    }

    public void setParroquia(Ubicaciones parroquia) {
        this.parroquia = parroquia;
    }

    @XmlTransient
    public List<Entidades> getEntidadesList() {
        return entidadesList;
    }

    public void setEntidadesList(List<Entidades> entidadesList) {
        this.entidadesList = entidadesList;
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
        if (!(object instanceof Direcciones)) {
            return false;
        }
        Direcciones other = (Direcciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.appdog.entidades.Direcciones[ id=" + id + " ]";
    }
    
}
