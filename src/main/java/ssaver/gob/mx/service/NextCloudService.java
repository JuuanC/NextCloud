package ssaver.gob.mx.service;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ssaver.gob.mx.dto.RequestDTO;
import ssaver.gob.mx.exception.CustomException;
import ssaver.gob.mx.model.Documento;
import ssaver.gob.mx.repository.DocumentoRepository;
import ssaver.gob.mx.util.ConstansException;
import ssaver.gob.mx.util.ConstansGeneral;
import ssaver.gob.mx.util.Utilities;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Carlos Dominguez
 * @version 1.0
 * @since 18/03/2021
 * <h1>Clase NextCloudService</h1>
 * <p>Aquí se encuentran los métodos que usan la WebDav</p>
 */

@ApplicationScoped
public class NextCloudService {

    @Inject
    Utilities utilities;

    @Inject
    DocumentoRepository documentoRepository;

    @ConfigProperty(name = "nextcloud.propfind")
    String PROPFIND;

    @ConfigProperty(name = "nextcloud.mkcol")
    String MKCOL;

    @ConfigProperty(name = "nextcloud.get")
    String GET;

    @ConfigProperty(name = "nextcloud.put")
    String PUT;

    /**
     * <h1>Método para listar los archivos de determinada carpeta</h1>
     *
     * @return lista de archivos
     */
    public javax.ws.rs.core.Response listFiles(RequestDTO file) throws CustomException {
        //Se crea la peticion con método PRODFIND
        Response response;
        try {
            response = utilities.createRequest(file.getUrl_file(), PROPFIND,file.getUrl_base(), file.getUser(), file.getPassword(), null);
        } catch (IOException e) {
            throw new CustomException(ConstansException.ERROR_INFO);
        }

        String responseXML;
        try {
            //Se guarda la respuesta XML en un string
            responseXML = response.body().string();
        } catch (IOException e) {
            throw new CustomException(ConstansException.ERROR_INFO);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;

        try {
            builder = factory.newDocumentBuilder();
            //Elaboración de un documento xml desde el string xml
            doc = builder.parse(new InputSource(new StringReader(responseXML)));
        } catch (ParserConfigurationException e) {
            throw new CustomException(ConstansException.ERROR_INFO);
        } catch (SAXException e) {
            throw new CustomException(ConstansException.ERROR_INFO);
        } catch (IOException e) {
            throw new CustomException(ConstansException.ERROR_INFO);
        }

        //Se accede al elemento d:response del XML para poder acceder a los nodos que se requieren
        NodeList nodes = doc.getElementsByTagName("d:response");

        List<String> files = new ArrayList<>();

        //i empieza en 1 por que el elemento 0 es la raiz de la carpeta y no es relevante
        for(int i = 1; i < nodes.getLength(); i++){
            //Se obtiene el primer nodo del primer elemento que es donde se encuentra el nombre del archivo/carpeta
            String fileName = nodes.item(i).getFirstChild().getTextContent();
            //Se separan todas las carpetas, porque no interesa la ruta completa hasta momento
            String[] folders = fileName.split("/");
            //Se manda el ultimo elemento porque es el importante
            files.add(folders[folders.length-1]);
        }

        return utilities.okResponse(ConstansGeneral.GET_SUCCESS, files);
    }

    public byte[] getFile(RequestDTO file) throws CustomException {
        Response response;
        try {
            response = utilities.createRequest(file.getUrl_file(), GET, file.getUrl_base(), file.getUser(), file.getPassword(), null);
            if(!response.isSuccessful()){
                throw new CustomException(ConstansException.ERROR_INFO);
            }
            return response.body().bytes();
        } catch (IOException e) {
            throw new CustomException(ConstansException.ERROR_INFO);
        }
    }

    @Transactional
    public javax.ws.rs.core.Response upload(RequestDTO file) throws CustomException, IOException {
        createFolder(file);

        RequestBody requestBody;
        try {
            requestBody = RequestBody.create(MediaType.parse(
                    javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM),file.getFile().readAllBytes());
        } catch (IOException e) {
            throw new CustomException(ConstansException.ERROR_SAVE, e);
        }

        Documento documento = new Documento();
        documento.setSistema(file.getSystem());
        documento.setUrl(file.getUrl_base() + "/" + file.getUser() + "/" + file.getUrl_file());
        documento.setFecha(new Timestamp(System.currentTimeMillis()));
        documento.setUsuario(file.getUser());

        utilities.createRequest(file.getUrl_file(), PUT, file.getUrl_base(), file.getUser(), file.getPassword(), requestBody);

        try{
            documentoRepository.persist(documento);
        }catch (NoResultException e){
            throw new CustomException(ConstansException.ERROR_INFO, e);
        }catch (PersistenceException e){
            throw new CustomException(ConstansException.ERROR_NO_DB_CONEXION, e);
        }catch (Exception e){
            throw new CustomException(ConstansException.ERROR_INESPERADO, e);
        }

        return utilities.okResponse(ConstansGeneral.SAVE_SUCCESS,null);
    }

    /**
     * <h1>Método para crear una carpeta en Nextcloud</h1>
     *
     */
    public void createFolder(RequestDTO file) throws IOException {
        //Se separa la ruta para crear las carpetas de manera individual
        //No se pueden crear todas en una sola petición
        String[] listFolders = file.getUrl_file().split("/");

        String folders = "";

        for (int i = 0; i < listFolders.length-1; i++){
            //folders es un acumodado de las carpetas para crear la jerarquia
            folders = folders + listFolders[i] + "/";
            utilities.createRequest(folders, MKCOL, file.getUrl_base(), file.getUser(), file.getPassword(), null);
        }
    }
}
