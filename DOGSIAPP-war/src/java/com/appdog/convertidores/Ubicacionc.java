/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.convertidores;





import com.appdog.entidades.Ubicaciones;
import com.appdog.excepciones.ConsultarException;
import com.appdog.vistas.ParroquiasBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author edwin
 */
@FacesConverter(forClass = Ubicaciones.class)
public class Ubicacionc implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Ubicaciones codigo = null;
        try {
            if (value == null) {
                return null;
            }
            ParroquiasBean bean = (ParroquiasBean)
                    context.getELContext().getELResolver().
                    getValue(context.getELContext(), null, "parroquias");
            Integer id = Integer.parseInt(value);
            codigo= bean.traer(id);
        } catch (ConsultarException ex) {
            Logger.getLogger(Ubicacionc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Ubicaciones codigo=(Ubicaciones) value;
        if (codigo.getId()==null)
                return "0";
        return ((Ubicaciones) value).getId().toString();
    }
}