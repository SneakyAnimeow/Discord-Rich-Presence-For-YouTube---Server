package club.anims.discordrpcserver;

import club.anims.discordrpcserver.interfaces.Jsonable;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class YouTubeStatus implements Jsonable<YouTubeStatus> {
    private String author;
    private String title;
    private String link;

    public void loadFromJson(String json) {
        var status = fromJson(json);
        author = status.author;
        title = status.title;
        link = status.link;
    }
}
