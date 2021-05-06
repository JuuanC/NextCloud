package ssaver.gob.mx.resource;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import ssaver.gob.mx.dto.RequestDTO;
import ssaver.gob.mx.exception.CustomException;
import ssaver.gob.mx.service.NextCloudService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Juan Carlos Dominguez
 * @version 1.0
 * @since 18/03/2021
 * <h1>Clase de peticiones con el protocolo WebDav</h1>
 */

@Path("v2/")
@ApplicationScoped
public class NextCloudResource {

    @Inject
    NextCloudService nextCloudService;

    /**
     * <h1>Método para obtener la lista de archivos de determinada carpeta</h1>
     * @param url ruta donde se encuentra la carpeta
     * @return lista de nombre de archivos
     */
    @POST
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response listFile(@MultipartForm RequestDTO file) throws CustomException, IOException {
        return nextCloudService.listFiles(file);
    }

    @POST
    @Path("get")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public byte[] getfile(@MultipartForm RequestDTO file) throws CustomException {
        return nextCloudService.getFile(file);
    }

    @PUT
    @Path("")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@MultipartForm RequestDTO file) throws CustomException, IOException {
        return nextCloudService.upload(file);
    }

}
