/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.vistas;


import com.appdog.entidades.Maestros;
import com.appdog.excepciones.BorrarException;
import com.appdog.excepciones.ConsultarException;
import com.appdog.excepciones.GrabarException;
import com.appdog.excepciones.InsertarException;
import com.appdog.servicios.MaestrosFacade;
import com.appdog.utilitarios.Combos;
import com.appdog.utilitarios.Formulario;
import com.appdog.utilitarios.MensajesErrores;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author PATO
 */
@ManagedBean(name = "maestros")
@ViewScoped
public class MaestrosBean implements Serializable {

    private static long serialVersionUID = 1L;

    private List<Maestros> listamaestros = new LinkedList<>();
    private Formulario formulario = new Formulario();
    private Maestros maestro;
    private String nombrebuscar = null;
    @EJB
    private MaestrosFacade ejbMaestro;
    @ManagedProperty(value = "#{seguridad}")
    private SeguridadBean seguridadBean;

    /**
     * Creates a new instance of ModulosBean
     */
    public MaestrosBean() {
    }

    public String buscar() {
        Map parametros = new HashMap();
        String where = "";

        if ((nombrebuscar != null) && !(nombrebuscar.isEmpty())) {
            where += "upper(o.nombre) like:nombre";
            parametros.put("nombre", "%" + nombrebuscar.toUpperCase() + "%");
        }

        parametros.put(";where", where);
        try {
            listamaestros = ejbMaestro.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            MensajesErrores.fatal(ex.getMessage() + " " + ex.getCause());
            Logger.getLogger(MaestrosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String nuevo() {
        maestro = new Maestros();
        formulario.insertar();
        return null;
    }

    public String insertar() {
        try {
            ejbMaestro.create(maestro, null);

        } catch (InsertarException ex) {
            MensajesErrores.fatal(ex.getMessage() + " " + ex.getCause());
            Logger.getLogger(MaestrosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        buscar();
        formulario.cancelar();
        return null;
    }

    public String grabar() {
        try {
            ejbMaestro.edit(maestro, null);

        } catch (GrabarException ex) {
            MensajesErrores.fatal(ex.getMessage() + " " + ex.getCause());
            Logger.getLogger(MaestrosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        buscar();
        formulario.cancelar();
        return null;
    }

    public String borrar() {
        try {
            ejbMaestro.remove(maestro, null);
        } catch (BorrarException ex) {
            MensajesErrores.fatal(ex.getMessage() + " " + ex.getCause());
            Logger.getLogger(MaestrosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        buscar();
        formulario.cancelar();
        return null;
    }

    public String modifica(Maestros maest) {
        this.maestro = maest;
        formulario.editar();
        return null;
    }

    public String borra(Maestros maest) {
        this.maestro = maest;
        formulario.eliminar();
        return null;
    }

    public SelectItem[] getcomboMaestroEspacio () {
        buscar();
        return Combos.getSelectItems(listamaestros, true);
    }

    public Maestros traer(Integer id) throws ConsultarException {
        return ejbMaestro.find(id);
    }

    public String cancelar() {
        formulario.cancelar();
        return null;
    }

    /**
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * @return the nombrebuscar
     */
    public String getNombrebuscar() {
        return nombrebuscar;
    }

    /**
     * @param nombrebuscar the nombrebuscar to set
     */
    public void setNombrebuscar(String nombrebuscar) {
        this.nombrebuscar = nombrebuscar;
    }

    /**
     * @return the seguridadBean
     */
    public SeguridadBean getSeguridadBean() {
        return seguridadBean;
    }

    /**
     * @param seguridadBean the seguridadBean to set
     */
    public void setSeguridadBean(SeguridadBean seguridadBean) {
        this.seguridadBean = seguridadBean;
    }

    /**
     * @return the listamaestros
     */
    public List<Maestros> getListamaestros() {
        return listamaestros;
    }

    /**
     * @param listamaestros the listamaestros to set
     */
    public void setListamaestros(List<Maestros> listamaestros) {
        this.listamaestros = listamaestros;
    }

    /**
     * @return the maestro
     */
    public Maestros getMaestro() {
        return maestro;
    }

    /**
     * @param maestro the maestro to set
     */
    public void setMaestro(Maestros maestro) {
        this.maestro = maestro;
    }

}
