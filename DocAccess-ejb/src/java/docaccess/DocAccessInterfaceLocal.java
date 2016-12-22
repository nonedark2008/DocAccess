/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package docaccess;

import javax.ejb.Local;
import javax.ejb.LockType;
import javax.persistence.LockModeType;

/**
 *
 * @author NoneDark
 */
@Local
public interface DocAccessInterfaceLocal {
    public Users getUser(DocAccessInterfaceRemote.UserSession session) throws DocAccessInterfaceRemote.UserException;
    public Users getUser(String username) throws DocAccessInterfaceRemote.UserException;
    public Documents getDocument(Users user);
}
