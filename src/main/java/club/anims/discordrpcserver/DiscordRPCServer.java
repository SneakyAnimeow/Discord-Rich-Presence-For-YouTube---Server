package club.anims.discordrpcserver;

import club.anims.discordrpcserver.custom.DiscordRichPresenceBuilder;
import club.anims.discordrpcserver.interfaces.Loggable;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import lombok.Getter;

import java.net.URL;

public class DiscordRPCServer implements Loggable {
    @Getter
    private static DiscordRPCServer instance;

    @Getter
    private static final String VERSION = "1.0.0";

    @Getter
    private long startTimestamp;

    @Getter
    private DiscordRPC rpc;

    private YoutubeDownloader downloader;

    private DiscordRPCServer() {
        downloader = new YoutubeDownloader();
        initializePresence();
        initializeWebServer();
    }

    private void initializeWebServer(){
        WebServer.init(36789);
        WebServer.getInstance().start();
    }

    private DiscordRichPresenceBuilder getDefaultDiscordRichPresenceBuilder(){
        return new DiscordRichPresenceBuilder()
                .setStartTimestamp(startTimestamp)
                .setLargeImageKey("vanced")
                .setDetails("Awaiting presence update...")
                .setLargeImageText("YouTube");
    }

    public void updatePresence(YouTubeStatus status){
        var videoId = "";

        try{
            var url = new URL(status.getLink());

            //get parameter v from url
            var query = url.getQuery();
            var params = query.split("&");
            for (var param : params) {
                var keyValue = param.split("=");
                if (keyValue[0].equals("v")) {
                    videoId = keyValue[1];
                    break;
                }
            }
        }catch (Exception e){
            getLogger().info("Invalid URL: " + status.getLink());
            return;
        }

        var infoRequest = new RequestVideoInfo(videoId);
        var infoRequestResponse = downloader.getVideoInfo(infoRequest);
        var videoInfo = infoRequestResponse.data();

        getLogger().info("Updating presence with video: " + videoInfo.details().title());

        updatePresence(getDefaultDiscordRichPresenceBuilder()
                .setDetails(videoInfo.details().title())
                .setState(videoInfo.details().author())
                .build());
    }

    public void updatePresence(DiscordRichPresence presence){
        rpc.Discord_UpdatePresence(presence);
    }

    private void initializePresence(){
        rpc = DiscordRPC.INSTANCE;
        var applicationId = "922183322766049300";
        var handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> getLogger().info("Discord RPC Server, version " + VERSION + " is ready!");
        handlers.joinRequest = (joinRequest) -> getLogger().info("Join request: " + joinRequest.username);
        rpc.Discord_Initialize(applicationId, handlers, true, "");
        startTimestamp = System.currentTimeMillis() / 1000;
        var presence = getDefaultDiscordRichPresenceBuilder().build();
        updatePresence(presence);
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                rpc.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void init() {
        instance = new DiscordRPCServer();
    }
}
