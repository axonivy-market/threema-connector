package ch.ivyteam.threema.mocks;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import ch.ivyteam.ivy.environment.Ivy;
import io.swagger.v3.oas.annotations.Hidden;

@Path(ThreemaServiceMock.PATH_SUFFIX)
@PermitAll
@Hidden
@SuppressWarnings("all")
public class ThreemaServiceMock {

  static final String PATH_SUFFIX = "mock";
  private static final String THREEMA_ID = "validId";
  public static final String URI = "{ivy.app.baseurl}/api/" + PATH_SUFFIX;


  @GET
  @Path("/lookup/{type}/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public Response getThreemaIdByMail(@PathParam("type") String type, @PathParam("id") String id) {
    Response resp;
    if (id.equals("validId")) {
      resp = Response.ok().entity(THREEMA_ID).build();
    } else {
      resp = Response.status(404).build();
    }
    return resp;
  }

  @GET
  @Path("/pubkeys/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public Response getPublicKey(@PathParam("id") String id) {
    Response resp;
    String pubKey = "ffbb40cfced42f75c4d83c7d35300c0698bf3ef1ab49ace323a1bbc38ee23f36";
    if (id.equals(THREEMA_ID)) {
      resp = Response.ok().entity(pubKey).build();
    } else {
      resp = Response.status(404).build();
    }
    return resp;
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Path("/send_e2e")
  @Produces(MediaType.TEXT_PLAIN)
  public Response sendMessage(
          @FormParam("from") String from,
          @FormParam("box") String box,
          @FormParam("to") String to,
          @FormParam("secret") String secret,
          @FormParam("nonce") String nonce) {
    String msgId = "b2885aa81e9b9c93";
    Response resp;
    if (to.equals("validId")) {
      resp = Response.ok().entity(msgId).build();
    } else {
      resp = Response.status(404).build();
    }
    return resp;
  }
}
