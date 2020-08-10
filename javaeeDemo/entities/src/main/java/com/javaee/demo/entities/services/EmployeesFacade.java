/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaee.demo.entities.services;

import com.javaee.demo.entities.Employees;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author user
 */
@Stateless
public class EmployeesFacade extends AbstractFacade<Employees> implements EmployeesFacadeLocal {

    @PersistenceContext(unitName = "com.javaee.demo_entities_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeesFacade() {
        super(Employees.class);
    }
    public List<Employees> excuteNamedQueryFindAll()
    {
      TypedQuery<Employees> query =
      em.createNamedQuery("Employees.findAll", Employees.class);
      
      List<Employees> results = query.getResultList();
      return results;
    }
    public List<Employees> excuteNamedQueryFindAllGeneric()
    {
        return excuteNamedQuerymultipleResult("Employees.findAll", null);
    }
    public List<Employees> excuteNamedQueryFindByName()
    {
        Map map=new HashMap();
        map.put("name", "xyz");
        return excuteNamedQuerymultipleResult("Employees.findByName", map);
    }
    public Employees excuteNamedQueryFindById()
    {
        Map map=new HashMap();
        map.put("id", 1);
        return excuteNamedQuerySingleResult("Employees.findById", map);
    }
    public List findAllEmployeesJpql()
    {
        Query query = em.createQuery("Select UPPER(e.ename) from Employees e");
        List<String> list = query.getResultList();
        return list;
    }
}
