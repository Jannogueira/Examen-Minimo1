package edu.upc.dsa.services;


import edu.upc.dsa.VolManager;
import edu.upc.dsa.VolManagerImpl;
import edu.upc.dsa.models.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/products", description = "Endpoint to Products Service")
@Path("/products")
public class ProductsService {

    private VolManager tm;

    public ProductsService() {
        this.tm = VolManagerImpl.getInstance();
        if (tm.size()==0) {
            this.tm.addProduct("T1","Chupa-Chups");
            this.tm.addProduct("T2","Coca Cola");
            this.tm.addProduct("T3","Pringles");
        }


    }

    @GET
    @ApiOperation(value = "get all Products", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {

        List<Product> products = this.tm.findAll();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get a Product", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class),
            @ApiResponse(code = 404, message = "Product not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") String id) {
        Product t = this.tm.getProduct(id);
        if (t == null) return Response.status(404).build();
        else  return Response.status(201).entity(t).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Product", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Product not found")
    })
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") String id) {
        Product t = this.tm.getProduct(id);
        if (t == null) return Response.status(404).build();
        else this.tm.deleteProduct(id);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "update a Product", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Product not found")
    })
    @Path("/")
    public Response updateProduct(Product product) {

        Product t = this.tm.updateProduct(product);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }



    @POST
    @ApiOperation(value = "create a new Product", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Product.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newProduct(Product product) {

        if (product.getPrice()== 0|| product.getName()==null)  return Response.status(500).entity(product).build();
        this.tm.addProduct(product);
        return Response.status(201).entity(product).build();
    }

}