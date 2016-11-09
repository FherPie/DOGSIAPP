/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.vistas;

import com.icesoft.faces.component.menubar.MenuItem;
import com.appdog.entidades.Menusistema;
import com.appdog.entidades.Perfil;
import com.appdog.excepciones.ConsultarException;
import com.appdog.servicios.MenusistemaFacade;
import com.appdog.servicios.PerfilFacade;
import com.appdog.utilitarios.Combos;
import com.appdog.utilitarios.MensajesErrores;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author edwin
 */
@ManagedBean(name = "menusc")
@SessionScoped
public class CargadorMenusBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Creates a new instance of Menus
     */
    @EJB
    private MenusistemaFacade ejbMenus;
    @EJB
    private PerfilFacade ejbPerfil;
    private Perfil perfil;
    private List menu = new ArrayList();
    @ManagedProperty(value = "#{datosLogueo}")
    private DatosLogeoBean seguridadbean;
    private Integer imodulo = Combos.getModuloInt(); //  Seguridad
    private List<Menusistema> despliegue;
    private String formaSeleccionada;

    public void setSeguridadbean(DatosLogeoBean seguridadbean) {
        this.seguridadbean = seguridadbean;
    }

    public DatosLogeoBean getSeguridadbean() {
        return seguridadbean;
    }

    public CargadorMenusBean() {
    }

    /**
     * @return the menu
     */
    public List getMenu() {
        return menu;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(List menu) {
        this.menu = menu;
    }

//    @PostConstruct
//    @PostActivate
    private void carga() {
        try {
            // necesario usario y grupo para ver disponibles
            Map parametros = new HashMap();
            despliegue = new LinkedList<>();
            //        parametros.put("mnssistema", "deportes");

//            parametros.put("modulo", imodulo);
            parametros.put(";where", "  o.idpadre is null");
            parametros.put(";orden", " o.texto asc");
            List<Menusistema> listaMenus = ejbMenus.encontarParametros(parametros);
            for (Menusistema menuIt : listaMenus) {
                MenuItem menuM = new MenuItem();
                menuM.setValue(menuIt.getTexto().trim());
                //menuM.setIcon("xmlhttp/css/rime/css-images/bullet.gif");
                //menuM.setStyleClass("iceMnuItm");
                menuM.setId("menu" + menuIt.getId().toString());
                menu.add(menuM);
                if (seguridadbean.getGrpUsuario() != null) {
                    Map par = new HashMap();
                    par.put("grupo", seguridadbean.getGrpUsuario().getGrupo());
                    par.put("menu", menuIt);
                    par.put(";where", " o.grupo=:grupo and o.menu.idpadre=:menu");
                    par.put(";orden", " o.menu.texto asc");
                    List<Perfil> disponibles = ejbPerfil.encontarParametros(par);
                    //
                    if (!((disponibles == null) || (disponibles.isEmpty()))) {
                        //desp.setMenus(disponibles);
                        menuIt.setPerfilList(disponibles);
                        getDespliegue().add(menuIt);

                    }//fin disponibles
                } else {
                    Map par = new HashMap();
                    par.put("menu", menuIt);
                    par.put(";where", " o.idpadre=:menu");
                     par.put(";orden", " o.texto asc");
                    List<Menusistema> disponibles = ejbMenus.encontarParametros(par);
                    List<Perfil> lp = new LinkedList<>();
                    for (Menusistema ms : disponibles) {
                        Perfil p = new Perfil();
                        p.setBorrado(Boolean.TRUE);
                        p.setConsulta(Boolean.TRUE);
                        p.setModificacion(Boolean.TRUE);
                        p.setNuevo(Boolean.TRUE);
                        p.setMenu(ms);
                        p.setGrupo(null);
                        lp.add(p);
                    }
                    menuIt.setPerfilList(lp);
                    getDespliegue().add(menuIt);
                }
            }//fin for

            //        }
        } catch (ConsultarException ex) {
            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger("CARGAR MENUS").log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the formaSeleccionada
     */
    public String getFormaSeleccionada() {
        return formaSeleccionada;
    }

    /**
     * @param formaSeleccionada the formaSeleccionada to set
     */
    public void setFormaSeleccionada(String formaSeleccionada) {
        this.formaSeleccionada = formaSeleccionada;
    }

    public String ira(Perfil perfil) {
        this.perfil = perfil;
        formaSeleccionada = perfil.getMenu().getTexto();
        return perfil.getMenu().getFormulario().trim() + ".jsf?faces-redirect=true";
    }

    /**
     * @return the despliegue
     */
    public List<Menusistema> getDespliegue() {
        return despliegue;
    }

    public List<Menusistema> getCargarMenu() {
        carga();
        return despliegue;
    }

    /**
     * @param despliegue the despliegue to set
     */
    public void setDespliegue(List<Menusistema> despliegue) {
        this.despliegue = despliegue;
    }

    /**
     * @return the perfil
     */
    public Perfil getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
