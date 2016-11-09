/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.convertidores;




import com.appdog.entidades.Menusistema;
import com.appdog.excepciones.ConsultarException;
import com.appdog.vistas.MenusBean;
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
@FacesConverter(forClass = Menusistema.class)
public class Menusc implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        Menusistema menu = null;
        MenusBean bean = (MenusBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "menus");
        Integer id = Integer.parseInt(value);
        try {
            menu = bean.traer(id);
        } catch (ConsultarException ex) {
            Logger.getLogger("Convertidor").log(Level.SEVERE, null, ex);
        }
        return menu;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Menusistema codigo = (Menusistema) value;
        if (codigo.getId() == null) {
            return "0";
        }
        return codigo.getId().toString();
    }

}
