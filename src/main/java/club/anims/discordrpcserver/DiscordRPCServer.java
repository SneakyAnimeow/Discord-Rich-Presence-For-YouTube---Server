package club.anims.discordrpcserver;

import club.anims.discordrpcserver.custom.DiscordRichPresenceBuilder;
import club.anims.discordrpcserver.interfaces.Loggable;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import lombok.Getter;

public class DiscordRPCServer implements Loggable {
    @Getter
    private static DiscordRPCServer instance;

    @Getter
    private static final String VERSION = "1.0.0";

    @Getter
    private long startTimestamp;

    @Getter
    private DiscordRPC rpc;

    private DiscordRPCServer() {
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
                .setLargeImageText("YouTube")
                .setJoinSecret("https://youtube.com/watch?v=dQw4w9WgXcQ");
    }

    public void updatePresence(YouTubeStatus status){
        updatePresence(getDefaultDiscordRichPresenceBuilder()
                .setDetails(status.getTitle())
                .setState(status.getAuthor())
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
