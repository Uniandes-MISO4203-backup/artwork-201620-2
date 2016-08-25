/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.artwork.api.INationalityLogic;
import co.edu.uniandes.csw.artwork.dtos.minimum.NationalityDTO;
import co.edu.uniandes.csw.artwork.entities.NationalityEntity;
import co.edu.uniandes.csw.auth.provider.StatusCreated;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author am.osorio
 */
@Path("/nationalitys")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NationalityResources {

    @Inject
    private INationalityLogic nationalityLogic;
    @Context
    private HttpServletResponse response;
    @QueryParam("page")
    private Integer page;
    @QueryParam("maxRecords")
    private Integer maxRecords;

    /**
     *
     * @param entity
     * @return
     */
    public static NationalityDTO refEntity2DTO(NationalityEntity entity) {
        if (entity != null) {
            NationalityDTO dto = new NationalityDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());

            return dto;
        } else {
            return null;
        }
    }

    public static NationalityEntity refDTO2Entity(NationalityDTO dto) {
        if (dto != null) {
            NationalityEntity entity = new NationalityEntity();
            entity.setId(dto.getId());

            return entity;
        } else {
            return null;
        }
    }

    public static NationalityDTO basicEntity2DTO(NationalityEntity entity) {
        if (entity != null) {
            NationalityDTO dto = new NationalityDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());

            return dto;
        } else {
            return null;
        }
    }

    public static NationalityEntity basicDTO2Entity(NationalityDTO dto) {
        if (dto != null) {
            NationalityEntity entity = new NationalityEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());

            return entity;
        } else {
            return null;
        }
    }

    public static NationalityDTO fullEntity2DTO(NationalityEntity entity) {
        if (entity != null) {
            NationalityDTO dto = basicEntity2DTO(entity);
            return dto;
        } else {
            return null;
        }
    }

    public static NationalityEntity fullDTO2Entity(NationalityDTO dto) {
        if (dto != null) {
            NationalityEntity entity = basicDTO2Entity(dto);
            return entity;
        } else {
            return null;
        }
    }

    public static List<NationalityDTO> listEntity2DTO(List<NationalityEntity> entities) {
        List<NationalityDTO> dtos = new ArrayList<>();
        if (entities != null) {
            for (NationalityEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }

    public static List<NationalityEntity> listDTO2Entity(List<NationalityDTO> dtos) {
        List<NationalityEntity> entities = new ArrayList<>();
        if (dtos != null) {
            for (NationalityDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }

    @GET
    public List<NationalityDTO> getNationalitys() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", nationalityLogic.countNationalitys());
            return listEntity2DTO(nationalityLogic.getNationalitys(page, maxRecords));
        }
        return listEntity2DTO(nationalityLogic.getNationalitys());
    }

    @GET
    @Path("{id: \\d+}")
    public NationalityDTO getNationality(@PathParam("id") Long id) {
        return fullEntity2DTO(nationalityLogic.getNationality(id));
    }

    @POST
    @StatusCreated
    public NationalityDTO createNationality(NationalityDTO dto) {
        NationalityEntity entity = fullDTO2Entity(dto);
        return fullEntity2DTO(nationalityLogic.createNationality(entity));
    }

    @PUT
    @Path("{id: \\d+}")
    public NationalityDTO updateNationality(@PathParam("id") Long id, NationalityDTO dto) {
        NationalityEntity entity = fullDTO2Entity(dto);
        entity.setId(id);
        return fullEntity2DTO(nationalityLogic.updateNationality(entity));
    }

    @DELETE
    @Path("{id: \\d+}")
    public void deleteNationality(@PathParam("id") Long id) {
        nationalityLogic.deleteNationality(id);
    }
}
