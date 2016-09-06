/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.artwork.ejbs;

import co.edu.uniandes.csw.artwork.api.IArtworkLogic;
import co.edu.uniandes.csw.artwork.api.ICategoryLogic;
import co.edu.uniandes.csw.artwork.api.IClientLogic;
import co.edu.uniandes.csw.artwork.api.IQualifyLogic;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.CategoryEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.QualifyEntity;
import co.edu.uniandes.csw.artwork.persistence.QualifyPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @generated
 */
@Stateless
public class QualifyLogic implements IQualifyLogic {

    @Inject
    private QualifyPersistence persistence;
    @Inject
    private IArtworkLogic artwork;
    @Inject
    private IClientLogic client;
    

    @Override
    public Long getQualifys(Long artworkId) {
        Long fullScore=0l;
        List<QualifyEntity> scores = persistence.findAll(artworkId);
        for (QualifyEntity qualify : scores) {
            fullScore+=qualify.getScore();
        }
        if(fullScore != 0l){
            fullScore = fullScore/scores.size();
        }
        return fullScore;
    }

    @Override
    public QualifyEntity addQualify(Long artworkId, Long clientId,  QualifyEntity entity) {
        ArtworkEntity artworkEntity = artwork.getArtwork(artworkId);
        ClientEntity clientEntity = client.getClient(clientId);

        entity.setArtwork(artworkEntity);
        entity.setClient(clientEntity);
        persistence.create(entity);
        return entity;
    }

}
