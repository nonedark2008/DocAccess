/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package docaccess;

import java.io.Serializable;
import javax.ejb.ApplicationException;
import javax.ejb.Remote;

/**
 *
 * @author NoneDark
 */
@Remote
public interface DocAccessInterfaceRemote  {

    public String getUsername(UserSession session) throws UserException;
    public String getDocument(UserSession session) throws UserException;

    public void saveDocument(UserSession session, String data) throws UserException;
    public class UserSession implements Serializable {
        private String _sessionToken;

        public UserSession() {
            _sessionToken = "";
        }
        
        
        /**
         * @return the _sessionToken
         */
        public String getSessionToken() {
            return _sessionToken;
        }

        /**
         * @param _sessionToken the _sessionToken to set
         */
        public void setSessionToken(String _sessionToken) {
            this._sessionToken = _sessionToken;
        }  
    }
    
    @ApplicationException(rollback=false)
    public class UserException extends Exception {
        public UserException(String message) {
            super(message, null, false, false);
        }
    }
    
    Boolean checkUsername(String username);
    Boolean createUser(String username, String password)  throws UserException;
    UserSession authorizeUser(String username, String password) throws UserException;
    Boolean isDocumentLocked(UserSession session) throws UserException;
    public void setDocumentLock(UserSession session, Boolean lock) throws UserException;

    String getDocOwner(UserSession session) throws UserException;
}
