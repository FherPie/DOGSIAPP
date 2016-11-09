/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.vistas;


import com.appdog.entidades.Codigos;
import com.appdog.entidades.Maestros;
import com.appdog.excepciones.BorrarException;
import com.appdog.excepciones.ConsultarException;
import com.appdog.excepciones.GrabarException;
import com.appdog.excepciones.InsertarException;
import com.appdog.servicios.CodigosFacade;
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
 */
@ManagedBean(name = "tablaDeCodigos")
@ViewScoped
public class CodigosBean implements Serializable {

    /**
     * Creates a new instance of CodigosBean
     */
    public CodigosBean() {
    }
    @ManagedProperty(value = "#{datosLogueo}")
    private DatosLogeoBean seguridadbean;
    @ManagedProperty(value = "#{menusc}")
    private CargadorMenusBean menusBean;
    private Formulario formulario = new Formulario();
    private List<Codigos> codigos;
    private Codigos codigo;
    private Integer maestro;
    private Maestros maestroEntidad;
    private String codigoMaestro;
    @EJB
    private CodigosFacade ejbCodigos;

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
     * @return the codigos
     */
    public List<Codigos> getCodigos() {
        return codigos;
    }

    /**
     * @param codigos the codigos to set
     */
    public void setCodigos(List<Codigos> codigos) {
        this.codigos = codigos;
    }

    /**
     * @return the codigo
     */
    public Codigos getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Codigos codigo) {
        this.codigo = codigo;
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
    // colocamos los metodos en verbo

    public String crear() {
        // se deberia chequear perfil?
        if (!menusBean.getPerfil().getNuevo()) {
            MensajesErrores.advertencia("No tiene autorización para crear un registro");
            return null;
        }
        
        if (maestroEntidad == null) {
            MensajesErrores.advertencia("Seleccione una tabla maestra primero");
            return null;
        }

        codigo = new Codigos();
        setCodigoMaestro(getMaestroEntidad().getCodigo());
        codigo.setMaestro(getMaestroEntidad());
//        

        formulario.insertar();
        return null;
    }

    public String modificar() {
        if (!menusBean.getPerfil().getModificacion()) {
            MensajesErrores.advertencia("No tiene autorización para modificar un registro");
        }
        codigo = codigos.get(formulario.getFila().getRowIndex());
        formulario.editar();
        return null;
    }

    public String eliminar() {
        if (!menusBean.getPerfil().getBorrado()) {
            MensajesErrores.advertencia("No tiene autorización para borrar un registro");
        }
        codigo = codigos.get(formulario.getFila().getRowIndex());
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
//        if ((codigoMaestro == null) || (codigoMaestro.isEmpty())) {
//            MensajesErrores.advertencia("Seleccione una tabla maestra primero");
//            return null;
//        }
//        try {
//
//            maestroEntidad = ejbCodigos.traerMaestroCodigo(codigoMaestro,Combos.getModuloStr());
//        } catch (ConsultarException ex) {
//            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
//            Logger.getLogger(CodigosBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
        if (!menusBean.getPerfil().getConsulta()) {
            MensajesErrores.advertencia("No tiene autorización para consultar");
            return null;
        }
        if (getMaestroEntidad() == null) {
            MensajesErrores.advertencia("Seleccione una tabla maestra primero");
            return null;
        }
        try {
            Map parametros = new HashMap();
            parametros.put(";where", "o.maestro=:maestroParametro");
            parametros.put("maestroParametro", getMaestroEntidad());
//            parametros.put("modulo", maestroEntidad.getCodigo());
            codigos = ejbCodigos.encontarParametros(parametros);
        } catch (ConsultarException ex) {
            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(CodigosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    // acciones de base de datos

    private boolean validar() {
        if ((codigo.getCodigo() == null) || (codigo.getCodigo().isEmpty())) {
            MensajesErrores.advertencia("Es necesario código");
            return true;
        }
        if ((codigo.getNombre() == null) || (codigo.getNombre().isEmpty())) {
            MensajesErrores.advertencia("Es necesario nombre");
            return true;
        }
        if ((codigo.getDescripcion() == null) || (codigo.getDescripcion().isEmpty())) {
            MensajesErrores.advertencia("Es necesario descripción");
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
            codigo.setMaestro(maestroEntidad);
            ejbCodigos.create(codigo, null);
        } catch (InsertarException ex) {
            Logger.getLogger(CodigosBean.class.getName()).log(Level.SEVERE, null, ex);
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
            ejbCodigos.edit(codigo, null);

        } catch (GrabarException ex) {
            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(CodigosBean.class.getName()).log(Level.SEVERE, null, ex);
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
            ejbCodigos.remove(codigo, null);
        } catch (BorrarException ex) {
            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
            Logger.getLogger(CodigosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        formulario.cancelar();
        buscar();
        return null;
    }
    // falta el combo

    public Codigos traer(Integer id) throws ConsultarException {
        return ejbCodigos.find(id);
    }

    /**
     * @return the maestro
     */
    public Integer getMaestro() {
        return maestro;
    }

    /**
     * @param maestro the maestro to set
     */
    public void setMaestro(Integer maestro) {
        this.maestro = maestro;
    }

    /**
     * @return the codigoMaestro
     */
    public String getCodigoMaestro() {
        return codigoMaestro;
    }

    /**
     * @param codigoMaestro the codigoMaestro to set
     */
    public void setCodigoMaestro(String codigoMaestro) {
        this.codigoMaestro = codigoMaestro;
    }

    /**
     * @return the maestroEntidad
     */
    public Maestros getMaestroEntidad() {
        return maestroEntidad;
    }

    /**
     * @param maestroEntidad the maestroEntidad to set
     */
    public void setMaestroEntidad(Maestros maestroEntidad) {
        this.maestroEntidad = maestroEntidad;
    }

//    public static SelectItem[] getSelectItems(List<Codigos> entities, boolean selectOne) {
//        if (entities == null) {
//            return null;
//        }
//        int size = selectOne ? entities.size() + 1 : entities.size();
//        SelectItem[] items = new SelectItem[size];
//        int i = 0;
//        if (selectOne) {
//            items[0] = new SelectItem(null, "---");
//            i++;
//        }
//        for (Codigos x : entities) {
//            items[i++] = new SelectItem(x.getCodigo(), x.toString());
//        }
//        return items;
//    }
    private List<Codigos> traer(String maestro, String modulo) {
//        try {
////            return ejbCodigos.traerCodigos(maestro, modulo);
//
//        } catch (ConsultarException ex) {
//            MensajesErrores.fatal(ex.getMessage() + "-" + ex.getCause());
//            Logger.getLogger(CodigosBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return null;
    }

    public SelectItem[] getComboGrupoUsuariosF() {

        return Combos.getSelectItems(traer("GRPUSR", null), false);
    }

    public SelectItem[] getComboGrupoUsuarios() {
        return Combos.getSelectItems(traer("GRPUSR", null), true);
    }

    public SelectItem[] getComboModulos() {
        return Combos.getSelectItems(traer("MOD", null), true);
    }

    public SelectItem[] getComboModulosF() {
        return Combos.getSelectItems(traer("MOD", null), false);
    }

    public SelectItem[] getComboRegionF() {
        return Combos.getSelectItems(traer("REG", null), false);
    }

    public SelectItem[] getComboRegion() {
        return Combos.getSelectItems(traer("REG", null), true);
    }

    public SelectItem[] getComboClasificacioncF() {
        return Combos.getSelectItems(traer("CLACLI", null), false);
    }

    public SelectItem[] getComboClasificacionc() {
        return Combos.getSelectItems(traer("CLACLI", null), true);
    }

    public SelectItem[] getComboSegmentos() {
        return Combos.getSelectItems(traer("SEGM", null), true);
    }

    public SelectItem[] getComboClasificacion2c() {
        return Combos.getSelectItems(traer("CLACLI2", null), true);
    }

    public SelectItem[] getComboClasificacionc2F() {
        return Combos.getSelectItems(traer("CLACLI2", null), false);
    }

    public SelectItem[] getComboSegmentosF() {
        return Combos.getSelectItems(traer("SEGM", null), false);
    }

    public SelectItem[] getComboTipoActividad() {
        return Combos.getSelectItems(traer("TIAC", null), true);
    }

    public SelectItem[] getComboTipoActividadF() {
        return Combos.getSelectItems(traer("TIAC", null), false);
    }

    public SelectItem[] getComboActividadEmpresa() {
        return Combos.getSelectItems(traer("ACTEMP", null), true);
    }

    public SelectItem[] getComboActividadEmpresaF() {
        return Combos.getSelectItems(traer("ACTEMP", null), false);
    }

    public SelectItem[] getComboNivelIBM() {
        return Combos.getSelectItems(traer("IBM", null), true);
    }

    public SelectItem[] getComboNivelIBMF() {
        return Combos.getSelectItems(traer("IBM", null), false);
    }
    public SelectItem[] getComboTipoCuenta() {
        return Combos.getSelectItems(traer("TIPOCTA", null), true);
    }

    public SelectItem[] getComboTipoCuentaF() {
        return Combos.getSelectItems(traer("TIPOCTA", null), false);
    }
    //dpt
    public SelectItem[] getComboFormaPago() {
        return Combos.getSelectItems(traer("FORPAG", null), true);
    }

    public SelectItem[] getComboFormaPagoF() {
        return Combos.getSelectItems(traer("FORPAG", null), false);
    }
    public SelectItem[] getComboTipoDocumento() {
        return Combos.getSelectItems(traer("TIPDOC", null), true);
    }

    public SelectItem[] getComboTipoDocumentoF() {
        return Combos.getSelectItems(traer("TIPDOC", null), false);
    }
    public SelectItem[] getComboValoresAdicionales() {
        return Combos.getSelectItems(traer("VALADIC", null), true);
    }

    public SelectItem[] getComboValoresAdicionalesF() {
        return Combos.getSelectItems(traer("VALADIC", null), false);
    }
        
        
   public SelectItem[] getComboProfesiones() {
        return Combos.getSelectItems(traer("PRO", null), true);
    }
   
      public SelectItem[] getComboTipoEscritos() {
        return Combos.getSelectItems(traer("TIPESC", null), true);
    }
   
            
   public SelectItem[] getComboTiposConceptosPago() {
        return Combos.getSelectItems(traer("TIPCONPAG", null), true);
    }
          
   
     public SelectItem[] getComboTiposArchivos() {
        return Combos.getSelectItems(traer("TIPARCH", null), false);
    }
          
    //fin dpt
}
