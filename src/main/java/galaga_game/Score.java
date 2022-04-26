package galaga_game;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
public class Score {
	@Getter
	@NonNull
    private final  String name;
	@Getter
	@Setter
    private  int score = 0;
	@Setter
	private ZonedDateTime time;

    @Override
    public String toString(){
        return (MyResourceBundle.getDateTimeFormatter().format(time)+"\t\t" +name + "\t\t\t\t\t" + score);
    }
}
