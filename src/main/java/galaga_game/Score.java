package galaga_game;

import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class Score {
	
	
	@Getter
	@Setter
	private Long id;
	
	@Setter
	@Getter
	@NonNull
    private	String name;
	
	@Getter
	@Setter
    private  int score = 0;
	
	@Getter
	@Setter
	private ZonedDateTime time;

    @Override
    public String toString(){
        return (MyResourceBundle.getDateTimeFormatter().format(time)+"\t\t" +name + "\t\t\t\t\t" + score);
    }
}
