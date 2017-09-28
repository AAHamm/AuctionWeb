/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnterpriseJavaBeans;

import Entities.AuctionUser;
import javax.ejb.Remote;

/**
 *
 * @author raugz
 */
@Remote
public interface LoginBeanRemote {

    Boolean login(String username, String password);

    AuctionUser getLoggedInUser();

    public void logout();
}
