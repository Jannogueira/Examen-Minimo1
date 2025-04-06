package edu.upc.dsa.services;

import edu.upc.dsa.VolManager;
import edu.upc.dsa.VolManagerImpl;
import edu.upc.dsa.models.Avio;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/avions", description = "Endpoint to Avions Service")
@Path("/avions")
public class AvionsService {

    private VolManager vm;

    public AvionsService() {
        this.vm = VolManagerImpl.getInstance();
        if (vm.sizeAvions()==0) {
            this.vm.addAvio("A320", "Airbus A320", "Iberia");
            this.vm.addAvio("A380", "Airbus A380", "Emirates");
            this.vm.addAvio("A350", "Airbus A350", "Lufthansa");
            this.vm.addAvio("B777", "Boeing 777", "Singapore Airlines");
        }
    }

    @GET
    @ApiOperation(value = "get all Avions", notes = "Dona una llista dels avions afegits")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Avio.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvions() {

        List<Avio> avions = this.vm.findAllAvions();

        GenericEntity<List<Avio>> entity = new GenericEntity<List<Avio>>(avions) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get a avio", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Avio.class),
            @ApiResponse(code = 404, message = "Avio not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvio(@PathParam("id") String id) {
        Avio a = this.vm.getAvio(id);
        if (a == null) return Response.status(404).build();
        else  return Response.status(201).entity(a).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Avio", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Avio not found")
    })
    @Path("/{id}")
    public Response deleteAvio(@PathParam("id") String id) {
        Avio a = this.vm.getAvio(id);
        if (a == null) return Response.status(404).build();
        else this.vm.deleteAvio(id);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "update a Avio", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Product not found")
    })
    @Path("/")
    public Response updateAvio(Avio avio) {

        Avio a = this.vm.updateAvio(avio);

        if (a == null) return Response.status(404).build();

        return Response.status(201).build();
    }



    @POST
    @ApiOperation(value = "create a new Avio", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Avio.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newAvio(Avio avio) {
        if (avio.getModel()== null|| avio.getCompanyia()==null)  return Response.status(500).entity(avio).build();
        this.vm.addAvio(avio);
        return Response.status(201).entity(avio).build();
    }


}