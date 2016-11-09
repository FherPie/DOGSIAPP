/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.vistas;


import com.appdog.entidades.Ubicaciones;
import com.appdog.excepciones.BorrarException;
import com.appdog.excepciones.ConsultarException;
import com.appdog.excepciones.GrabarException;
import com.appdog.excepciones.InsertarException;
import com.appdog.servicios.UbicacionesFacade;
import com.appdog.utilitarios.Combos;
import com.appdog.utilitarios.Formulario;
import com.appdog.utilitarios.MensajesErrores;
import java.io.Serializable;
import java.util.HashMap;
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
 * @author edwin
 *
 *
 * Roles [0 - Clientes]
 *
 */
@ManagedBean(name = "ubicacionAbstracto")
@ViewScoped
public abstract class UbicacionAbstractoBean implements Serializable {

    /**
     * Creates a new instance of MaestrosBean
     */
    @ManagedProperty(value = "#{datosLogueo}")
    private DatosLogeoBean seguridadbean;
    @ManagedProperty(value = "#{menusc}")
    private CargadorMenusBean menusBean;
    protected int nivel;
    protected Formulario formulario = new Formulario();
    private List<Ubicaciones> ubicaciones;
    private Ubicaciones ubicacion;
    private Ubicaciones padre;
    @EJB
    protected UbicacionesFacade ejbUbicaion;

    //Fin autocompletar
    public UbicacionAbstractoBean() {
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

    // colocamos los metodos en verbo
    public String crear() {
        // se deberia chequear perfil?
        if (!menusBean.getPerfil().getNuevo()) {
            MensajesErrores.advertencia("No tiene autorización para crear un registro");
            return null;
        }
        if (nivel > 0) {
            if (padre == null) {
                MensajesErrores.advertencia("Seleccione un nivel anterior");
                return null;
            }
        }
        ubicacion = new Ubicaciones();
        ubicacion.setPadre(padre);
        formulario.insertar();
        return null;
    }

    public String modificar() {
        if (!menusBean.getPerfil().getModificacion()) {
            MensajesErrores.advertencia("No tiene autorización para modificar un registro");
            return null;
        }
        ubicacion = ubicaciones.get(formulario.getFila().getRowIndex());
        formulario.editar();
        return null;
    }

    public String eliminar() {
        if (!menusBean.getPerfil().getBorrado()) {
            MensajesErrores.advertencia("No tiene autorización para borrar un registro");
            return null;
        }

        ubicacion = ubicaciones.get(formulario.getFila().getRowIndex());
        formulario.eliminar();
        return null;
    }

    public String cancelar() {
        formulario.cancelar();
        buscar();
        return null;
    }
    // buscar

    public String buscar() {
       if (!menusBean.getPerfil().getConsulta()) {
            MensajesErrores.advertencia("No tiene autorización para consultar");
            return null;
        }
        Map parametros = new HashMap();
        parametros.put(";orden", "o.nombre asc");
        if (nivel > 0) {
            if (padre == null) {
                MensajesErrores.advertencia("Seleccione un nivel anterior");
                return null;
            }
            parametros.put(";where", "o.padre=:padre");
            parametros.put("padre", padre);
        } else {
            parametros.put(";where", "o.nivel=:nivel");
            parametros.put("nivel", 0);
        }
        try {
            ubicaciones = ejbUbicaion.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger("").log(Level.SEVERE, null, ex);
        }
        return null;

    }

    // acciones de base de datos
    private boolean validar() {
        if ((ubicacion.getCodigo() == null) || (ubicacion.getCodigo().isEmpty())) {
            MensajesErrores.advertencia("Código es obligatorio");
            return true;
        }
        if ((ubicacion.getNombre() == null) || (ubicacion.getNombre().isEmpty())) {
            MensajesErrores.advertencia("Nombre es obligatorio");
            return true;
        }

        return false;
    }

    public String insertar() {
        if (!menusBean.getPerfil().getNuevo()) {
            MensajesErrores.advertencia("No tiene autorización para crear un registro");
            return null;
        }

        if (validar()) {
            return null;
        }

        try {
            ubicacion.setNivel(nivel);
            ejbUbicaion.create(ubicacion, null);
        } catch (InsertarException ex) {
            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger("").log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        buscar();
        return null;
    }

    public String grabar() {
        if (!menusBean.getPerfil().getModificacion()) {
            MensajesErrores.advertencia("No tiene autorización para modificar un registro");
            return null;
        }
        if (validar()) {
            return null;
        }
        try {
            ejbUbicaion.edit(ubicacion, null);
        } catch (GrabarException ex) {
            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger("").log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        buscar();
        return null;
    }

    public String borrar() {
        if (!menusBean.getPerfil().getBorrado()) {
            MensajesErrores.advertencia("No tiene autorización para borrar un registro");
            return null;
        }
        try {
            ejbUbicaion.remove(ubicacion, null);
        } catch (BorrarException ex) {
            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger("").log(Level.SEVERE, null, ex);
        }

        formulario.cancelar();
        buscar();
        return null;
    }
    // falta el combo

    /**
     * @return the seguridadbean
     */
    public DatosLogeoBean getSeguridadbean() {
        return seguridadbean;
    }

    /**
     * @param seguridadbean the seguridadbean to set
     */
    public void setSeguridadbean(DatosLogeoBean seguridadbean) {
        this.seguridadbean = seguridadbean;
    }



    /**
     * @return the ubicaciones
     */
    public List<Ubicaciones> getUbicaciones() {
        return ubicaciones;
    }

    /**
     * @param ubicaciones the ubicaciones to set
     */
    public void setUbicaciones(List<Ubicaciones> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    /**
     * @return the ubicacion
     */
    public Ubicaciones getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(Ubicaciones ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * @return the padre
     */
    public Ubicaciones getPadre() {
        return padre;
    }

    /**
     * @param padre the padre to set
     */
    public void setPadre(Ubicaciones padre) {
        this.padre = padre;
    }

    public Ubicaciones traer(Integer id) throws ConsultarException {
        return ejbUbicaion.find(id);
    }

    public List<Ubicaciones> buscar(Ubicaciones superior) {
        Map parametros = new HashMap();
        parametros.put(";orden", "o.nombre asc");

        if (superior == null) {
            parametros.put(";where", "o.nivel=:nivel");
            parametros.put("nivel", 0);
        } else {
            parametros.put(";where", "o.padre=:padre");
            parametros.put("padre", superior);
        }
        try {
            return ejbUbicaion.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger("").log(Level.SEVERE, null, ex);
        }
        return null;

    }
     public SelectItem[] getUbicacionF(){
        return Combos.getSelectItems(buscar(padre), false);
    }
    public SelectItem[] getUbicacionV(){
        return Combos.getSelectItems(buscar(padre), true);
    }

    /**
     * @return the menusBean
     */
    public CargadorMenusBean getMenusBean() {
        return menusBean;
    }

    /**
     * @param menusBean the menusBean to set
     */
    public void setMenusBean(CargadorMenusBean menusBean) {
        this.menusBean = menusBean;
    }


}
