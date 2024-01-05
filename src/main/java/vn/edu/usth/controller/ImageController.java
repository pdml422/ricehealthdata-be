package vn.edu.usth.controller;

import io.quarkus.logging.Log;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import vn.edu.usth.payload.ImageRGBFromHyper;
import vn.edu.usth.payload.ViewMap;
import vn.edu.usth.service.image.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequestScoped
@Path("/image")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "Basic Auth", type = SecuritySchemeType.HTTP, scheme = "basic")
public class ImageController {
    private final ImageService imageService;

    @Inject
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @POST
    @RolesAllowed({"USER"})
    @Path("/rgb")
    public Response getImageRGBFromHyper(@RequestBody ImageRGBFromHyper request) throws IOException {
        String filePath = "src/main/resources/Image/Output/" +
                "hyper_" + request.id + "_" + request.red + "_" + request.green + "_" + request.blue + ".png";
        if (Files.exists(Paths.get(filePath))) {
            return Response.ok("http://100.96.184.148:8888/src/main/resources/Image/Output/" +
                    "hyper_" + request.id + "_" + request.red + "_" + request.green + "_" + request.blue + ".png").build();
        }
        Process p = Runtime.getRuntime().exec("python -m http.server 8888");
        return Response.ok(imageService.getImageRGBFromHyper(request)).build();
    }

    @GET
    @RolesAllowed({"USER"})
    @Path("/map/{id}")
    public Response getMapPoint(@PathParam("id") int id) {
        return Response.ok(imageService.getMapPointFromImageId(id)).build();
    }

}
