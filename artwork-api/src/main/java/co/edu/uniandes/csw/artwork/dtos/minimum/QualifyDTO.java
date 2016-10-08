/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.minimum;
import co.edu.uniandes.csw.artwork.entities.QualifyEntity;


/**
 *
 * @author am.osorio
 */
public class QualifyDTO {
    private Long id;
    private String name;
    private Long score;
    private String message;
    
    
    public QualifyDTO() {
    }
    public QualifyDTO(QualifyEntity entity) {
	if (entity!=null){
            this.id=entity.getId();
            this.name=entity.getName();
            this.score=entity.getScore();
            this.message=entity.getMessage();
       }
    }

     /**
     * Convierte un objeto QualifyDTO a QualifyEntity.
     *
     * @return Nueva objeto QualifyEntity.
     * @generated
     */
    public QualifyEntity toEntity() {
        QualifyEntity entity = new QualifyEntity();
        entity.setId(this.getId());
        entity.setName(this.getName());
        entity.setScore(this.getScore());
        entity.setMessage(this.getMessage());
        return entity;
    }

    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

   
    /**
     * @return the score
     */
    public Long getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(Long score) {
        this.score = score;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
