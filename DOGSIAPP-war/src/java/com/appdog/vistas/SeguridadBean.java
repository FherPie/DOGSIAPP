/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.vistas;






import com.appdog.entidades.Entidades;
import com.appdog.entidades.Grupousuario;
import com.appdog.entidades.Menusistema;
import com.appdog.entidades.Perfil;
import com.appdog.excepciones.ConsultarException;
import com.appdog.excepciones.GrabarException;
import com.appdog.servicios.EntidadesFacade;
import com.appdog.servicios.GrupousuarioFacade;
import com.appdog.servicios.MenusistemaFacade;
import com.appdog.servicios.PerfilFacade;
import com.appdog.utilitarios.Codificador;
import com.appdog.utilitarios.Formulario;
import com.appdog.utilitarios.MensajesErrores;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.MethodExpressionActionListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.icefaces.ace.component.menuitem.MenuItem;
import org.icefaces.ace.component.submenu.Submenu;
import org.icefaces.ace.model.DefaultMenuModel;
import org.icefaces.ace.model.MenuModel;

/**
 *
 * @author PATO
 */
@ManagedBean(name = "seguridad")
@SessionScoped
public class SeguridadBean  implements Serializable {

    private static final long serialVersionUID = 1L;
    private String usuario;
    private String contrasena;
    private Formulario formulario= new Formulario();
    private Entidades user;
    private Grupousuario grpUsuario;
    private MenuModel modelo;
//    private Clientes cliente;
    private String claveNueva;
    private String claveRetpeada;
    private String claveAnterior;
    
    @EJB
    private EntidadesFacade ejbEntidades;
    @EJB
    private MenusistemaFacade ejbMenus;
    @EJB
    private PerfilFacade ejbVistas;
    @EJB
    private GrupousuarioFacade ejbGrpUsr;

    private String message;
    
    @ManagedProperty(value = "#{menusc}")
    private CargadorMenusBean menusBean;
   @ManagedProperty(value = "#{datosLogueo}")
    private DatosLogeoBean setlogeadobean;

    /**
     * Creates a new instance of SeguridadBean
     */
    public SeguridadBean() {
    }

    public String login() {
        if (getUsuario() == null || getUsuario().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el Usuario", ""));
        }

        if (contrasena == null || contrasena.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Contraseña", ""));
        }
        Codificador c;
        c = new Codificador();
        try {
            user = ejbEntidades.login(getUsuario(), c.getEncoded(contrasena, "MD5"));
        } catch (ConsultarException ex) {
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (user == null) {
            MensajesErrores.advertencia("Usuario no registrado, o clave invalida");
            return null;
        }
        if (user.getActivo() == false) {
            MensajesErrores.advertencia("Usuario no activo");
            setUsuario(null);
            return null;
        }
        c = new Codificador();
        if (user.getPwd().equals(c.getEncoded(user.getPin(), "MD5"))) {
              formulario.editar();
            return null;
        }

        Map parametros = new HashMap();
        parametros.put(";where", "o.usuario=:usuario and o.modulo=0");
        parametros.put("usuario", user.getPin());

        List<Grupousuario> lgu;
        try {
            lgu = ejbGrpUsr.encontarParametros(parametros);
            if (!((lgu == null) || (lgu.isEmpty()))) {
                setGrpUsuario(lgu.get(0));
            }
        } catch (ConsultarException ex) {
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (getGrpUsuario() == null) {
            MensajesErrores.advertencia("Usuario no autorizado para este módulo");
            return null;
        }
        setlogeadobean.setLogueado(user);

        modelo = getMenuPrincipal();
       message="Inicio";
        return "/sistema/InicioVista.jsf?faces-redirect=true";

    }

//    public String logout() throws ServletException, IOException {
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        request.getSession().invalidate();
//        request.logout();
//        return "";
//       
//    }
    public void logout() throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().invalidate();
        request.logout();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../login.jsf?faces-redirect=true");

    }

    public String getLoggedUser() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.getUserPrincipal() != null) {
            return request.getUserPrincipal().getName();
        }
        return "";
    }

    public MenuModel getMenuPrincipal() {

        if (user == null) {
            return null;
        }

        MenuModel retorno = new DefaultMenuModel();

        try {

            Map parametros = new HashMap();
            parametros.put(";where", "  o.idpadre is null");
            parametros.put(";orden", " o.texto asc");
            List<Menusistema> me = new LinkedList<>();
            me = ejbMenus.encontarParametros(parametros);
            Submenu nuevosubmenu1 = new Submenu();
            Submenu nuevosubmenu2;
            MenuItem nuevo;
            for (Menusistema m : me) {
                nuevosubmenu1 = new Submenu();
                nuevosubmenu1.setId("sm" + (m.getId()));
                nuevosubmenu1.setLabel(m.getTexto());
                // traer los perfiles

                parametros = new HashMap();
                parametros.put(";where", "o.grupo=:grupo and o.menu.idpadre=:idpadre");
                parametros.put(";orden", " o.menu.texto asc");
                parametros.put("grupo", grpUsuario.getGrupo());
                parametros.put("idpadre", m);
                List<Perfil> vistas = ejbVistas.encontarParametros(parametros);
                for (Perfil v : vistas) {
                    nuevo = new MenuItem();
                    nuevo.setId(nuevosubmenu1.getId() + "_mmi_" + v.getId());
                    nuevo.setValue(v.getMenu().getTexto());
              
//                    nuevo.setUrl(v.getMenu().getFormulario() + ".jsf?faces-redirect=true");
                    String idmenu=v.getId().toString();
                      FacesContext context = FacesContext.getCurrentInstance();
                     MethodExpression action = context.getApplication().getExpressionFactory()
                        .createMethodExpression(context.getELContext(), "#{seguridad.fireAction("+idmenu+")}", String.class, new Class[]{String.class});
                    nuevo.setActionExpression(action );
          
                  
                    nuevosubmenu1.getChildren().add(nuevo);
                }
                
                retorno.addSubmenu(nuevosubmenu1);
            }

            nuevosubmenu2 = new Submenu();
            nuevosubmenu2.setLabel("Bienvenido, " + user.getUserid());
            nuevo = new MenuItem();
            nuevo.setId("logout");
            nuevo.setValue("Cerrar Sesión");
            nuevo.setIcon("ui-icon ui-icon-power");
            
            FacesContext context = FacesContext.getCurrentInstance();
            MethodExpression methodExpression = context.getApplication().getExpressionFactory().createMethodExpression(
                    context.getELContext(), "#{seguridad.logout()}", null, new Class[]{ActionEvent.class});
            nuevo.addActionListener(new MethodExpressionActionListener(methodExpression));

            nuevosubmenu2.getChildren().add(nuevo);
            retorno.addSubmenu(nuevosubmenu1);
            retorno.addSubmenu(nuevosubmenu2);

        } catch (ConsultarException ex) {
            MensajesErrores.fatal(ex.getMessage() + " : " + ex.getCause());
            Logger.getLogger("").log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    
     public String fireAction(String id){

        try {
//            MenuItem menu= (MenuItem)event.getComponent();
//            
            
            Map parametros = new HashMap();
            parametros.put(";where", "o.id=:id");
            parametros.put("id", Integer.parseInt(id));         
           
            List<Perfil> vistas = ejbVistas.encontarParametros(parametros);
            for (Perfil v : vistas) {
                parametros= new HashMap();
                 parametros.put(";where", "o.id=:id");
               parametros.put("id", v.getMenu().getIdpadre().getId()); 
                List<Menusistema> mpadre= ejbMenus.encontarParametros(parametros);
                Menusistema padre= new Menusistema();
                for(Menusistema pad: mpadre){
                 padre=pad;
                }
                
                message= padre.getTexto() +" > "+v.getMenu().getTexto();
             return   menusBean.ira(v);
            }
//        Perfil perfil= (Perfil) event.getComponent();
        
//     return v.getMenu().getFormulario() + ".jsf?faces-redirect=true";
        } catch (ConsultarException ex) {
            Logger.getLogger(SeguridadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            return null;
    } 
    
     
  public String irinicio(){
    message="Inicio";
    return "/sistema/InicioVista.jsf?faces-redirect=true";
   } 
  
  
  
      public String cambio() {
        // dos password nuva y una anterior
        try {
            
            if ((getClaveAnterior() == null) || (getClaveAnterior().isEmpty())) {
                MensajesErrores.advertencia("Ingrese una Contraseña  anterior válida");
                return null;
            }
            if ((getClaveNueva() == null) || (getClaveNueva().isEmpty())) {
                MensajesErrores.advertencia("Ingrese una Contraseña nueva válida");
                return null;
            }
     
            if ((getClaveRetpeada() == null) || (getClaveRetpeada().isEmpty())) {
                MensajesErrores.advertencia("Ingrese  la Contraseña nueva nuevamente");
                return null;
            }
            Codificador c = new Codificador();
            String cnCodificada = c.getEncoded(getClaveNueva(), "MD5");
            String caCodificada = c.getEncoded(getClaveAnterior(), "MD5");
            String cnrCodificada = c.getEncoded(getClaveRetpeada(), "MD5");
            if (!(caCodificada.equals(user.getPwd()))) {
                MensajesErrores.advertencia("Ingrese una clave anterior válida");
                return null;
            }
            if (!(cnCodificada.equals(cnrCodificada))) {
                MensajesErrores.advertencia("Ingrese una clave retipeada igual a la nueva clave");
                return null;
            }
            user.setPwd(cnCodificada);
            ejbEntidades.edit(user, null);
        } catch (GrabarException ex) {
            MensajesErrores.fatal(ex.getMessage() + " : " + ex.getCause());
            Logger.getLogger("LOGIN").log(Level.SEVERE, null, ex);
        }
        formulario.cancelar();
        return null;
    }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
    
    
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the user
     */
    public Entidades getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Entidades user) {
        this.user = user;
    }

    /**
     * @return the modelo
     */
    public MenuModel getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(MenuModel modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the grpUsuario
     */
    public Grupousuario getGrpUsuario() {
        return grpUsuario;
    }

    /**
     * @param grpUsuario the grpUsuario to set
     */
    public void setGrpUsuario(Grupousuario grpUsuario) {
        this.grpUsuario = grpUsuario;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
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

    /**
     * @return the setlogeadobean
     */
    public DatosLogeoBean getSetlogeadobean() {
        return setlogeadobean;
    }

    /**
     * @param setlogeadobean the setlogeadobean to set
     */
    public void setSetlogeadobean(DatosLogeoBean setlogeadobean) {
        this.setlogeadobean = setlogeadobean;
    }

//    /**
//     * @return the cliente
//     */
//    public Clientes getCliente() {
//        return cliente;
//    }
//
//    /**
//     * @param cliente the cliente to set
//     */
//    public void setCliente(Clientes cliente) {
//        this.cliente = cliente;
//    }

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
     * @return the claveNueva
     */
    public String getClaveNueva() {
        return claveNueva;
    }

    /**
     * @param claveNueva the claveNueva to set
     */
    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    /**
     * @return the claveRetpeada
     */
    public String getClaveRetpeada() {
        return claveRetpeada;
    }

    /**
     * @param claveRetpeada the claveRetpeada to set
     */
    public void setClaveRetpeada(String claveRetpeada) {
        this.claveRetpeada = claveRetpeada;
    }

    /**
     * @return the claveAnterior
     */
    public String getClaveAnterior() {
        return claveAnterior;
    }

    /**
     * @param claveAnterior the claveAnterior to set
     */
    public void setClaveAnterior(String claveAnterior) {
        this.claveAnterior = claveAnterior;
    }





}
