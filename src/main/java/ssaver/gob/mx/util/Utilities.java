package ssaver.gob.mx.util;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpStatus;
import ssaver.gob.mx.dto.ResponseDTO;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;


/**
 * @author Juan Carlos Dominguez
 * @version 1.0
 * @since 18/03/2021
 * <h1>Clase de utilidades</h1>
 * <p>Aqui se encuentran metodos genericos, es decir, varias clases
 * hacen uso uso de ellos</p>
 */

@ApplicationScoped
public class Utilities {

    /**
     * <h1>Método para codificar credenciales</h1>
     * <p>Este método codifíca en Base64 el usuario y password para acceder a NextCloud </p>
     * @param auth user:password
     * @return credenciales en Base64
     */
    public String authEncoder(String auth){

        byte[] encodeAuth = Base64.encodeBase64(auth.getBytes());

        Charset ascii = Charset.forName("US-ASCII");
        String asciiEncoded = new String(encodeAuth, ascii);
        return  asciiEncoded;
    }

    /**
     * <h1>Método para crear una petición</h1>
     * <p>Este método crea una petición con la libreria OkHTTPClient</p>
     * @param url dirección donde se realizará la petición
     * @param method método HTTP o WebDav que necesita la pétición
     * @return Respuesta enviada de la API consultada
     */
    public Response createRequest(String url, String method, String urlBase, String user, String password, RequestBody requestBody) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String auth = authEncoder(user +":"+ password);

        Request request = new Request.Builder()
                .url(urlBase + user + "/" + url)
                .method(method, requestBody)
                .addHeader("Authorization", "Basic " + auth)
                .build();

        Response response = client.newCall(request).execute();

        return response;
    }
    /**
     * Build a javax.ws.rs.core.Response, wrapping a ResponseDTO which in turn wraps the actual data.
     * @param responseType the custom message to return (uses ConstantsGenerals)
     * @param data the response payload
     * @return Response with custom ResponseDTO and data
     */
    public javax.ws.rs.core.Response okResponse(String responseType, Object data) {
        if (data == null) {
            data = Collections.emptyList();
        }
        ResponseDTO responseDTO = new ResponseDTO(
                true,
                HttpStatus.SC_OK,
                responseType,
                data);
        return javax.ws.rs.core.Response.status(HttpStatus.SC_OK).entity(responseDTO).build();
    }
}
