package galaga;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.Era;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@ToString
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
	private long timeEpoch = ZonedDateTime.now().toEpochSecond();


    public String formatedText(){
    	ZoneId zoneId = ZoneId.systemDefault();
    	Instant instant = Instant.ofEpochSecond(timeEpoch);
    	ZonedDateTime time = instant.atZone(zoneId);
        return (MyResourceBundle.getDateTimeFormatter().format(time)+"\t\t"+ score+ "\t\t"  +name );
    }
}
