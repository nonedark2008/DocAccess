/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package docaccess;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author NoneDark
 */
@Stateless
public class DocAccessInterface implements DocAccessInterfaceRemote, DocAccessInterfaceLocal {
    @PersistenceContext(unitName = "DocAccess-ejbPU")
    private EntityManager em;
    
    @Override
    public Documents getDocument(Users user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Documents> cq = cb.createQuery(Documents.class);
        Root<Documents> docs = cq.from(Documents.class);
        cq.select(docs);
        Query q = em.createQuery(cq);
        List docList = q.getResultList();
        Documents doc;
        if (docList.isEmpty()) {
            System.out.println("!!! getDocument: Creating document...");
            doc = new Documents("myDocument", (short)0, "", new Date());
            doc.setLastChanger(user);
            em.persist(doc);
            return getDocument(user);
        }
        
        doc = (Documents)docList.get(0);
        return doc;
    }
    
    @Override
    public Boolean checkUsername(String username) {
        Query q = em.createNamedQuery("Users.findByUsername");
        q.setParameter("username", username);
        return !q.getResultList().isEmpty();
    }
    
    @Override
    public Users getUser(UserSession session) throws UserException {
        Query q = em.createNamedQuery("Users.findBySession");
        q.setParameter("session", session.getSessionToken());
        List<Users> resultList = q.getResultList();
        if (resultList.isEmpty())
            throw new UserException("Session not exists");
        Users user = resultList.get(0);
        return resultList.get(0);
    }
    
    @Override
    public Users getUser(String username) throws UserException {
        Query q = em.createNamedQuery("Users.findByUsername");
        q.setParameter("username", username);
        List<Users> resultList = q.getResultList();
        if (resultList.isEmpty())
            throw new UserException("User not exists");
        Users user = resultList.get(0);
        return resultList.get(0);
    }
    
    @Override
    public Boolean createUser(String username, String password) throws UserException {
        System.out.println("!!! createUser: " + username);
        if (checkUsername(username))
            throw new UserException("User already exists");
        try {
            Users new_user = new Users(username, password);
            em.persist(new_user);
        } catch(Exception ex) {
            throw new UserException("User already exists");
        }
        
        return true;
    }

    @Override
    public UserSession authorizeUser(String username, String password) throws UserException {
        Users user = getUser(username);
        
        SecureRandom random = new SecureRandom();
        boolean goodSession = false;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        while(!goodSession) {
            String newSession = new BigInteger(130, random).toString(32);
            try {
                CriteriaQuery<Long> cq = cb.createQuery(Long.class);
                Root<Users> users_root = cq.from(Users.class);
                cq.select(cb.count(users_root));
                cq.where(cb.equal(users_root.get(Users_.session), newSession));
                Query q = em.createQuery(cq);
                if ((Long)q.getSingleResult() > 0) {
                    System.out.println("!!! Dublicate token created " + q.getSingleResult().toString());
                    continue;
                }
                user.setSession(newSession);
                em.persist(user);
                goodSession = true;
            } catch(PersistenceException ex) {
                System.out.println("!!! Exception in authorizeUser");
            }
        }
        UserSession session = new UserSession();
        session.setSessionToken(user.getSession());
        return session;
    }
    
    @Override
    public String getUsername(UserSession session) throws UserException {
        Users me = getUser(session);
        return me.getUsername();
    }
    
    
    

    
    @Override
    public Boolean isDocumentLocked(UserSession session) throws UserException {
        Users me = getUser(session);
        Documents doc = getDocument(me);
        return doc.getLockState() > 0;
    }
    
    @Override
    public void setDocumentLock(UserSession session, Boolean lock) throws UserException {
        System.out.println("setDocumentLock");
        Users me = getUser(session);
        System.out.println("111111111111111111111111");
        Documents doc = getDocument(me);
        System.out.println("111111111111111111111111");
        if (doc.getLockState() > 0) {
            Users owner = doc.getLockOwner();
            if (owner != null && !owner.getSession().equals(session.getSessionToken())) {
                System.err.println("Already locked by " + owner.getUsername());
                throw new UserException("Already locked by " + owner.getUsername());
            }
        }

        doc.setLockState((short)(lock ? 1: 0));
        if (!lock.equals(Boolean.FALSE))
            doc.setLockOwner(me);
        else
            doc.setLockOwner(null);
        System.out.println("111111111111111111111111");
        em.persist(doc);
    }
    
    @Override
    public String getDocument(UserSession session) throws UserException {
        Users me = getUser(session);
        Documents doc = getDocument(me);
        return doc.getDocData();
    }
    
    @Override
    public void saveDocument(UserSession session, String data) throws UserException {
        Users me = getUser(session);
        Documents doc = getDocument(me);
        if (doc.getLockState() == 0 || !doc.getLockOwner().equals(me))
            throw new UserException("document must be locked beforehand");
        doc.setDocData(data);
        doc.setChangeTime(new Date());
        em.persist(doc);
    }

    @Override
    public String getDocOwner(UserSession session) throws UserException {
        Users me = getUser(session);
        Documents doc = getDocument(me);
        if (doc.getLockState() == 0 || doc.getLockOwner() == null)
            return null;
        return doc.getLockOwner().getUsername();
    }
}
