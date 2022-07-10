package club.anims.discordrpcserver;

import club.anims.discordrpcserver.interfaces.Jsonable;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class YouTubeStatus implements Jsonable<YouTubeStatus> {
    private String link;

    public void loadFromJson(String json) {
        var status = fromJson(json);
        link = status.link;
    }
}
