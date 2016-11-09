/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.convertidores;



import com.appdog.entidades.Maestros;
import com.appdog.excepciones.ConsultarException;
import com.appdog.vistas.MaestrosBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author PATO
 */
@FacesConverter(forClass = Maestros.class)
public class Maestrosc implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        Maestros modulo = null;
        MaestrosBean bean = (MaestrosBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "maestros");
        Integer id = Integer.parseInt(value);
        try {
            modulo = bean.traer(id);
        } catch (ConsultarException ex) {
            Logger.getLogger("Convertidor").log(Level.SEVERE, null, ex);
        }
        return modulo;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Maestros codigo = (Maestros) value;
        if (codigo.getId() == null) {
            return "0";
        }
        return codigo.getId().toString();
    }

}
