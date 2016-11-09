/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.vistas;

//
import com.appdog.entidades.Ubicaciones;
import com.appdog.utilitarios.Combos;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author edwin
 */
@ManagedBean(name = "parroquias")
@ViewScoped
public class ParroquiasBean extends UbicacionAbstractoBean{

    /**
     * Creates a new instance of PaisesBean
     */
    public ParroquiasBean() {
       nivel=3;
    }
    private Ubicaciones parroquia;
    public SelectItem[] getParroquiasF(){
        return Combos.getSelectItems(super.buscar(parroquia), false);
    }
    public SelectItem[] getParroquiasV(){
        return Combos.getSelectItems(super.buscar(parroquia), true);
    }

    

    /**
     * @return the parroquia
     */
    public Ubicaciones getParroquia() {
        return parroquia;
    }

    /**
     * @param parroquia the parroquia to set
     */
    public void setParroquia(Ubicaciones parroquia) {
        this.parroquia = parroquia;
    }

    
}
