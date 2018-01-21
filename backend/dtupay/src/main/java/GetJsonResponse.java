import com.google.gson.Gson;

import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * @author Jesper
 */
public class GetJsonResponse {

	public  static <T> Response succes(Response.Status AllOkID, T classObject) {

		return Response.status(AllOkID).entity( json().toJson( classObject)).build();
	}
	public static Response error(Response.Status errorID,String message){

		return Response.status(errorID).entity(json().toJson(new ErrorMessage(message))).build();
	}
	private static Gson json(){return new Gson();}


}
class ErrorMessage implements Serializable {
	public final String error;

	public ErrorMessage(String error) {
		this.error = error;
	}
}
