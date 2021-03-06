package io.corbel.resources.rem.restor;

import io.corbel.lib.ws.api.error.ErrorResponseFactory;
import io.corbel.resources.rem.dao.RestorDao;
import io.corbel.resources.rem.model.RestorObject;
import io.corbel.resources.rem.model.RestorResourceUri;
import io.corbel.resources.rem.request.RequestParameters;
import io.corbel.resources.rem.request.ResourceId;
import io.corbel.resources.rem.request.ResourceParameters;

import java.io.InputStream;
import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * @author Rubén Carrasco
 */
public class RestorGetRem extends AbstractRestorRem {

    public RestorGetRem(RestorDao dao) {
        super(dao);
    }

    @Override
    public Response resource(String collection, ResourceId resource, RequestParameters<ResourceParameters> parameters,
                             Optional<InputStream> entity) {

        RestorResourceUri resourceUri = new RestorResourceUri(parameters.getRequestedDomain(), getMediaType(parameters), collection,
                resource.getId());

        RestorObject object = dao.getObject(resourceUri);
        if (object != null) {
            Response.ResponseBuilder responseBuilder = Response.ok().type(object.getMediaType().toString()).entity(object.getInputStream());
            responseBuilder = responseBuilder.header(HttpHeaders.CONTENT_LENGTH, object.getContentLength());
            responseBuilder = responseBuilder.header(HttpHeaders.ETAG, object.getEtag());
            if (parameters.getHeaders() != null) {
                responseBuilder = responseBuilder.header(HttpHeaders.CONTENT_DISPOSITION, parameters.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
            }
            return responseBuilder.build();
        }
        return ErrorResponseFactory.getInstance().notFound();
    }
}
