/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaee.demo.entities.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author user
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    public List<T> excuteNamedQuerymultipleResult(String namedQueryName, Map params)
           {
        Query q = getEntityManager().createNamedQuery(namedQueryName, entityClass);
        String key;
        if (params != null) {
            for (Iterator<Object> itr = params.keySet().iterator(); itr.hasNext();) {
                key = (String) itr.next();
                q.setParameter(key, params.get(key));
            }

        }

        List<T> nResult = q.getResultList();
//        if (nResult.isEmpty()) {
            //return new ArrayList<>();
            //throw new EmptyResultSetException("error.no.data.found");
       // }
        return nResult;
    }

public T excuteNamedQuerySingleResult(String namedQueryName, Map params)
            {

        try {
            Query q = getEntityManager().createNamedQuery(namedQueryName, entityClass);

            String key;
            
            if (params != null) {
                for (Iterator<Object> itr = params.keySet().iterator(); itr.hasNext();) {
                    key = (String) itr.next();
                    q.setParameter(key, params.get(key));
                }

            }

            return (T) q.getSingleResult();
        } catch (NoResultException e) {
            //throw new EmptyResultSetException("error.no.data.found");
             return null;
        }

    }
}