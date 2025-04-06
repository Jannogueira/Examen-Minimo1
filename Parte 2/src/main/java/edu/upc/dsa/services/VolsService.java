package edu.upc.dsa.services;

import edu.upc.dsa.VolManager;
import edu.upc.dsa.VolManagerImpl;
import edu.upc.dsa.models.Avio;
import edu.upc.dsa.models.Vol;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/vols", description = "Endpoint to vols Service")
@Path("/vols")
public class VolsService {

    private VolManager vm;

    public VolsService() {
        this.vm = VolManagerImpl.getInstance();
        if (vm.sizeAvions()==0) {
            this.vm.addAvio("A320", "Airbus A320", "Iberia");
            this.vm.addAvio("A380", "Airbus A380", "Emirates");
            this.vm.addAvio("A350", "Airbus A350", "Lufthansa");
            this.vm.addAvio("B777", "Boeing 777", "Singapore Airlines");
        }
        if (vm.sizeVols()==0) {
            this.vm.addVol("V123", "08:30", "12:45", "A320", "Barcelona", "Madrid");
            this.vm.addVol("V010", "16:20", "23:50", "A380", "Los Ángeles", "Sídney");
            this.vm.addVol("V012", "11:15", "17:45", "A350", "París", "Seúl");
            this.vm.addVol("V015", "23:55", "06:20", "B777", "Hong Kong", "Londres");
        }
    }

    @GET
    @ApiOperation(value = "get all Vols", notes = "Dona una llista dels vols afegits")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Vol.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVols() {

        List<Vol> vols = this.vm.findAllVols();

        GenericEntity<List<Vol>> entity = new GenericEntity<List<Vol>>(vols) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get a vol", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Vol.class),
            @ApiResponse(code = 404, message = "Vol not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVol(@PathParam("id") String id) {
        Vol v = this.vm.getVol(id);
        if (v == null) return Response.status(404).build();
        else  return Response.status(201).entity(v).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Vol", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Vol not found")
    })
    @Path("/{id}")
    public Response deleteVol(@PathParam("id") String id) {
        Vol v = this.vm.getVol(id);
        if (v == null) return Response.status(404).build();
        else this.vm.deleteVol(id);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "update a Vol", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Vol not found")
    })
    @Path("/")
    public Response updateVol(Vol vol) {

        Vol v = this.vm.updateVol(vol);

        if (v == null) return Response.status(404).build();

        return Response.status(201).build();
    }



    @POST
    @ApiOperation(value = "create a new Vol", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Vol.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newVol(Vol vol) {
        if (vol.getDesti()== null|| vol.getOrigen()==null)  return Response.status(500).entity(vol).build();
        this.vm.addVol(vol);
        return Response.status(201).entity(vol).build();
    }

    @POST
    @ApiOperation(value = "Facturar maleta", notes = "Afegir una maleta a un vol")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Maleta afegida correctament"),
            @ApiResponse(code = 404, message = "Vol no trobat"),
            @ApiResponse(code = 400, message = "Paràmetres invàlids")
    })
    @Path("/{idVol}/maletes/{propietari}")
    public Response facturarMaleta(@PathParam("idVol") String idVol, @PathParam("propietari") String propietari) {
        if (idVol == null || propietari == null || propietari.trim().isEmpty()) {
            return Response.status(400).entity("Falten dades per facturar la maleta").build();
        }

        try {
            this.vm.facturarMaleta(idVol, propietari);
            return Response.status(201).entity("Maleta facturada correctament").build();
        } catch (Exception e) {
            return Response.status(404).entity("Error: El vol no existeix").build();
        }
    }


}