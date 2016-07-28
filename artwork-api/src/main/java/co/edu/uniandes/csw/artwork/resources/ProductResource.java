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
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.auth.provider.StatusCreated;
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
import co.edu.uniandes.csw.artwork.api.IProductLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.ProductDetailDTO;
import co.edu.uniandes.csw.artwork.entities.ProductEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: products/
 * @generated
 */
@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject private IProductLogic productLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;

   
    /**
     * Convierte una lista de ProductEntity a una lista de ProductDetailDTO.
     *
     * @param entityList Lista de ProductEntity a convertir.
     * @return Lista de ProductDetailDTO convertida.
     * @generated
     */
    private List<ProductDetailDTO> listEntity2DTO(List<ProductEntity> entityList){
        List<ProductDetailDTO> list = new ArrayList<>();
        for (ProductEntity entity : entityList) {
            list.add(new ProductDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Product
     *
     * @return Colección de objetos de ProductDetailDTO
     * @generated
     */
    @GET
    public List<ProductDetailDTO> getProducts() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", productLogic.countProducts());
            return listEntity2DTO(productLogic.getProducts(page, maxRecords));
        }
        return listEntity2DTO(productLogic.getProducts());
    }

    /**
     * Obtiene los datos de una instancia de Product a partir de su ID
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ProductDetailDTO con los datos del Product consultado
     * @generated
     */
    @GET
    @Path("{id: \\d+}")
    public ProductDetailDTO getProduct(@PathParam("id") Long id) {
        return new ProductDetailDTO(productLogic.getProduct(id));
    }

    /**
     * Se encarga de crear un Product en la base de datos
     *
     * @param dto Objeto de ProductDetailDTO con los datos nuevos
     * @return Objeto de ProductDetailDTOcon los datos nuevos y su ID
     * @generated
     */
    @POST
    @StatusCreated
    public ProductDetailDTO createProduct(ProductDetailDTO dto) {
        return new ProductDetailDTO(productLogic.createProduct(dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Product
     *
     * @param id Identificador de la instancia de Product a modificar
     * @param dto Instancia de ProductDetailDTO con los nuevos datos
     * @return Instancia de ProductDetailDTO con los datos actualizados
     * @generated
     */
    @PUT
    @Path("{id: \\d+}")
    public ProductDetailDTO updateProduct(@PathParam("id") Long id, ProductDetailDTO dto) {
        ProductEntity entity = dto.toEntity();
        entity.setId(id);
        ProductEntity oldEntity = productLogic.getProduct(id);
        return new ProductDetailDTO(productLogic.updateProduct(entity));
    }

    /**
     * Elimina una instancia de Product de la base de datos
     *
     * @param id Identificador de la instancia a eliminar
     * @generated
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteProduct(@PathParam("id") Long id) {
        productLogic.deleteProduct(id);
    }
    
}
