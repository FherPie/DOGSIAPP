/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdog.servicios;

import com.appdog.entidades.Maestros;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andres
 */
@Stateless
public class MaestrosFacade extends AbstractFacade<Maestros> {
    @PersistenceContext(unitName = "DOGSIAPP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaestrosFacade() {
        super(Maestros.class);
    }
    
}
