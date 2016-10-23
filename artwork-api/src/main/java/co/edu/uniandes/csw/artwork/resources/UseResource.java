/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.auth.service.AuthService;
import co.edu.uniandes.csw.artwork.api.IClientLogic;
import co.edu.uniandes.csw.artwork.api.IArtistLogic;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.group.Group;
import com.stormpath.sdk.resource.ResourceException;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author asistente
 */
public class UseResource extends AuthService {
    
    @Inject private IClientLogic clientLogic;
    @Inject private IArtistLogic artistLogic;
    private static final String CLIENT_HREF = "https://api.stormpath.com/v1/groups/5xJfg140VZoCF2Ny36Y7k1";
    private static final String ARTIST_HREF = "https://api.stormpath.com/v1/groups/5ngCSuH2ZdGyMahEUbmsz3";    
    private static final String CLIENT_CD = "client_id";
    private static final String ARTIST_CD = "artist_id";
    
    @Override
    public void register(UserDTO user) {
        try {
           Account acct = createUser(user);
        for (Group gr : acct.getGroups()) {
            switch(gr.getHref()){
                case CLIENT_HREF:
                ClientEntity client = new ClientEntity();
                client.setName(user.getUserName());
                client = clientLogic.createClient(client);
                acct.getCustomData().put(CLIENT_CD, client.getId());
                break;
                case ARTIST_HREF:
                ArtistEntity provider = new ArtistEntity();
                provider.setName(user.getUserName());                
                provider = artistLogic.createArtist(provider);
                acct.getCustomData().put(ARTIST_CD, provider.getId());  
                break;
                default:
                 throw new WebApplicationException("Group link doen's match ");
            }
                
            }
        acct.getCustomData().save();
        } catch (ResourceException e) {
            throw new WebApplicationException(e, e.getStatus());
        }
    }
    
}
