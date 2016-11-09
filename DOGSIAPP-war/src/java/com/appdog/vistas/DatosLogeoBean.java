/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.vistas;


import com.appdog.entidades.Entidades;
import com.appdog.entidades.Grupousuario;
import com.appdog.excepciones.ConsultarException;
import com.appdog.excepciones.GrabarException;
import com.appdog.servicios.EntidadesFacade;
import com.appdog.servicios.GrupousuarioFacade;
import com.appdog.utilitarios.Codificador;
import com.appdog.utilitarios.Formulario;
import com.appdog.utilitarios.MensajesErrores;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author edwin
 *
 *
 * Bean para guardar la informacion sucursales usurario logeado y punto de venta
 * hasta el momento
 */
@ManagedBean(name = "datosLogueo")
@SessionScoped
public class DatosLogeoBean implements Serializable {

    /**
     * Creates a new instance of ParametrosSeguridadBean
     */
    public DatosLogeoBean() {
    }
//    private Usuarioempresa usrEmpresa;
    private Entidades logueado;
    private Entidades vendedor;
//    private Clientes cliente;
    private Entidades contrario;
    private Entidades entidadCambiar;
    private Formulario formulario = new Formulario();
    private Grupousuario grpUsuario;
    @EJB
    private GrupousuarioFacade ejbGrpUsr;
    @EJB
    protected EntidadesFacade ejbEntidad;
    private String clave;
    private String claveNueva;
    private String claveNuevaRetipeada;

    /**
     * @return the logueado
     */
    public Entidades getLogueado() {
        return logueado;
    }

    /**
     * @param logueado the logueado to set
     */
    public void setLogueado(Entidades logueado) {
        // traer grupo usuario
        if (logueado != null) {
            try {
                Map parametros = new HashMap();
                parametros.put(";where", "o.usuario=:usuario and o.modulo=0");
                parametros.put("usuario", logueado.getPin());

                List<Grupousuario> lgu = ejbGrpUsr.encontarParametros(parametros);
                if (!((lgu == null) || (lgu.isEmpty()))) {
                    grpUsuario = lgu.get(0);
                }
            } catch (ConsultarException ex) {
                MensajesErrores.fatal(ex.getMessage() + " : " + ex.getCause());
                Logger.getLogger("ERROR").log(Level.SEVERE, null, ex);
            }
            if (logueado.getRol().contains("1")) {
                vendedor = logueado;
            }
        }
        this.logueado = logueado;
    }

    public String cerraSession() {
        FacesContext context = FacesContext.getCurrentInstance();

        ExternalContext externalContext = context.getExternalContext();

        Object session = externalContext.getSession(false);

        HttpSession httpSession = (HttpSession) session;

        httpSession.invalidate();
        return null;
//        return "../LoginVista.jsf";
    }

    public void logout() {
        ExternalContext ctx =
                FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath =
                ((ServletContext) ctx.getContext()).getContextPath();

        try {
            // Usar el contexto de JSF para invalidar la sesión,
            // NO EL DE SERVLETS (nada de HttpServletRequest)
            ((HttpSession) ctx.getSession(false)).invalidate();

            // Redirección de nuevo con el contexto de JSF,
            // si se usa una HttpServletResponse fallará.
            // Sin embargo, como ya está fuera del ciclo de vida 
            // de JSF se debe usar la ruta completa -_-U
            ctx.redirect(ctxPath + "/LoginVista.jsf");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
     * @return the vendedor
     */
    public Entidades getVendedor() {
        return vendedor;
    }

    /**
     * @param vendedor the vendedor to set
     */
    public void setVendedor(Entidades vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
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
     * @return the claveNuevaRetipeada
     */
    public String getClaveNuevaRetipeada() {
        return claveNuevaRetipeada;
    }

    /**
     * @param claveNuevaRetipeada the claveNuevaRetipeada to set
     */
    public void setClaveNuevaRetipeada(String claveNuevaRetipeada) {
        this.claveNuevaRetipeada = claveNuevaRetipeada;
    }

    public String cambiar() {
        return "CambioClaveVista.jsf?faces-redirect=true";
    }

    public String cambiarClave() {
        if ((getClave() == null) || (getClave().isEmpty())) {
            MensajesErrores.advertencia("Ingrese la clave actual");
            return null;
        }
        if ((getClaveNueva() == null) || (getClaveNueva().isEmpty())) {
            MensajesErrores.advertencia("Ingrese la clave nueva");
            return null;
        }
        if ((claveNuevaRetipeada == null) || (claveNuevaRetipeada.isEmpty())) {
            MensajesErrores.advertencia("retipee la clave nueva");
            return null;
        }
        if (!(claveNueva.equals(claveNuevaRetipeada))) {
            MensajesErrores.advertencia("Clave nueva debe ser igual a la clave retipeada");
            return null;
        }
        Codificador c = new Codificador();
        String claveCodificada = c.getEncoded(getClave(), "MD5");
        if (!(logueado.getPwd().equals(claveCodificada))) {
            MensajesErrores.advertencia("Clave anterior no es la correcta");
            return null;
        }
        claveCodificada = c.getEncoded(getClaveNueva(), "MD5");
        logueado.setPwd(claveCodificada);
        try {
            ejbEntidad.edit(logueado, null);
            
         
        } catch (GrabarException ex) {
            Logger.getLogger("").log(Level.SEVERE, null, ex);
        }
        MensajesErrores.advertencia("Clave se cambio correctamente");
        return null;
    }
    public String cambiarClaves(Entidades entidadCambiar) {
        this.entidadCambiar = entidadCambiar;
        formulario.insertar();
        return null;
    }

    public String grabarClave() {
        try {
            Codificador c = new Codificador();
            String claveCodificada = c.getEncoded(getClaveNueva(), "MD5");
            entidadCambiar.setPwd(claveCodificada);
            ejbEntidad.edit(entidadCambiar, null);
        } catch (GrabarException ex) {
            Logger.getLogger("").log(Level.SEVERE, null, ex);
        }
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
     * @return the cliente
     */
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
     * @return the contrario
     */
    public Entidades getContrario() {
        return contrario;
    }

    /**
     * @param contrario the contrario to set
     */
    public void setContrario(Entidades contrario) {
        this.contrario = contrario;
    }
}
