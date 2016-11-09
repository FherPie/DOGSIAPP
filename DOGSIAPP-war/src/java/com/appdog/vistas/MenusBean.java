/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.vistas;



import com.appdog.entidades.Menusistema;
import com.appdog.excepciones.BorrarException;
import com.appdog.excepciones.ConsultarException;
import com.appdog.excepciones.GrabarException;
import com.appdog.excepciones.InsertarException;
import com.appdog.servicios.MenusistemaFacade;
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
@ManagedBean(name = "menus")
@ViewScoped
public class MenusBean implements Serializable {

    private static long serialVersionUID = 1L;

    private List<Menusistema> listamenus = new LinkedList<>();
    private Formulario formulario = new Formulario();
    private Menusistema menu;
    private String nombrebuscar = null;
    @EJB
    private MenusistemaFacade ejbMenu;
    @ManagedProperty(value = "#{seguridad}")
    private SeguridadBean seguridadBean;

    /**
     * Creates a new instance of MenusBean
     */
    public MenusBean() {
    }

    public String buscar() {
        Map parametros = new HashMap();
        String where = "";

        if ((nombrebuscar != null) && !(nombrebuscar.isEmpty())) {
            where += " upper(o.nombre) like:nombre ";
            parametros.put("nombre", "%" + nombrebuscar.toUpperCase() + "%");
        }
        if (!(where.isEmpty())) {
            where += " and ";
        }

        where += " o.idpadre is null";
        parametros.put(";where", where);
        try {
            listamenus = ejbMenu.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            MensajesErrores.fatal(ex.getMessage() + " " + ex.getCause());
            Logger.getLogger(MaestrosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String nuevo() {
        setMenu(new Menusistema());
        formulario.insertar();
        return null;
    }

    public String modifica(Menusistema menu) {
        this.menu = menu;
        formulario.editar();
        return null;
    }

    public String borra(Menusistema menu) {
        this.menu = menu;
        formulario.eliminar();
        return null;
    }

    public boolean validar() {

        if (menu.getTexto() == null || (menu.getTexto().isEmpty())) {
            MensajesErrores.error("Ingrese el Nombre");
            return true;
        }
        return false;
    }

    public String insertar() {

        if (validar()) {
            return null;
        }

        try {
            menu.setModuloid(0);
            ejbMenu.create(menu, null);

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
            ejbMenu.edit(menu, null);

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
            ejbMenu.remove(menu, null);
        } catch (BorrarException ex) {
            MensajesErrores.fatal(ex.getMessage() + " " + ex.getCause());
            Logger.getLogger(MaestrosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        buscar();
        formulario.cancelar();
        return null;
    }

    //Menus principales
    public SelectItem[] getComboMenus() {
        buscar();
        return Combos.getSelectItems(listamenus, true);
    }

    public Menusistema traer(Integer id) throws ConsultarException {
        return ejbMenu.find(id);
    }

    /**
     * @return the listamenus
     */
    public List<Menusistema> getListamenus() {
        return listamenus;
    }

    /**
     * @param listamenus the listamenus to set
     */
    public void setListamenus(List<Menusistema> listamenus) {
        this.listamenus = listamenus;
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
     * @return the menu
     */
    public Menusistema getMenu() {
        return menu;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(Menusistema menu) {
        this.menu = menu;
    }

}
