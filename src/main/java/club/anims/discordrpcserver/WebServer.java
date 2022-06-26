package club.anims.discordrpcserver;

import club.anims.discordrpcserver.interfaces.Loggable;
import lombok.Getter;
import spark.Spark;

import static spark.Spark.*;

public class WebServer implements Loggable {
    @Getter
    private static WebServer instance;

    @Getter
    private final int port;

    private WebServer(int port){
        this.port = port;
    }

    private void registerRoutes(){
        path("/", () -> {
            before((request, response) -> {
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
            });

            get("/", (request, response) -> String.format("Discord RPC Server, version %s", DiscordRPCServer.getVERSION()));

            path("api", () -> {
                before("*", (request, response) -> getLogger().info("Request to api: " + request.pathInfo()));
                path("/presence", () -> {
                    get("/test", (request, response) -> {
                        getLogger().info("Request to api/presence/test");
                        var status = new YouTubeStatus(
                                request.queryParams("author"),
                                request.queryParams("title"),
                                request.queryParams("link")
                        );
                        DiscordRPCServer.getInstance().updatePresence(status);
                        return "OK";
                    });

                    post("/post", (request, response) -> {
                        getLogger().info("Received presence update: " + request.body());
                        var status = new YouTubeStatus();
                        try{
                            status.loadFromJson(request.body());
                        }catch (Exception e){
                            var message = "Error parsing JSON: " + e.getMessage();
                            getLogger().error(message);
                            return String.format(message);
                        }
                        DiscordRPCServer.getInstance().updatePresence(status);
                        return "OK";
                    });
                });
            });
        });
    }

    public void start(){
        port(getPort());
        registerRoutes();
        getLogger().info("Web server started on port {}", getPort());
    }

    public void stop(){
        Spark.stop();
    }

    public static void init(int port){
        instance = new WebServer(port);
    }
}
