package galaga_game;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


public interface ScoreServerClient {
	@GET
	@Path("score")
	@Produces(MediaType.APPLICATION_JSON)
	List<Score> getScore();
	
	@GET
	@Path("score/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Score getScore(String name);
	
	@POST
	@Path("score")
	@Consumes(MediaType.APPLICATION_JSON)
	Long createScore(Score score);

	@PUT
	@Path("score")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateScore(Score score); 

	@DELETE
	@Path("score/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean deleteScore(@PathParam("id") Long id);
	
	@DELETE
	@Path("score")
	@Produces(MediaType.TEXT_PLAIN)
	public long deleteScore();

}
