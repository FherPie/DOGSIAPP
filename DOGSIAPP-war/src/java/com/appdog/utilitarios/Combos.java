/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.utilitarios;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author edwin
 */
public class Combos implements Serializable {

    private static final long serialVersionUID = 1L;

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        if (entities == null) {
            return null;
        }
        int size = selectOne ? entities.size() + 1 : entities.size();
        int i = 0;
        SelectItem[] items = new SelectItem[size];
        if (selectOne) {
            items[i++] = new SelectItem(null, "---");
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static SelectItem[] getSelectItemsToString(List<?> entities, boolean selectOne) {
        if (entities == null) {
            return null;
        }
        int size = selectOne ? entities.size() + 1 : entities.size();
        int i = 0;
        SelectItem[] items = new SelectItem[size];
        if (selectOne) {
            items[i++] = new SelectItem(null, "---");
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x.toString(), x.toString());
        }
        return items;
    }
    
    
  public static String getModuloStr(){
        return "0";
    }
    public static int getModuloInt(){
        return 0;
    }
}
