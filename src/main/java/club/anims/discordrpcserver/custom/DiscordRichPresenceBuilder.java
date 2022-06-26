package club.anims.discordrpcserver.custom;

import club.minnced.discord.rpc.DiscordRichPresence;

public class DiscordRichPresenceBuilder {
    private String state;
    private String details;
    private long startTimestamp;
    private long endTimestamp;
    private String largeImageKey;
    private String largeImageText;
    private String smallImageKey;
    private String smallImageText;
    private String partyId;
    private int partySize;
    private int partyMax;
    private String matchSecret;
    private String joinSecret;
    private String spectateSecret;
    private byte instance;

    public DiscordRichPresenceBuilder() {
    }

    public DiscordRichPresenceBuilder(String state, String details, long startTimestamp, long endTimestamp, String largeImageKey, String largeImageText, String smallImageKey, String smallImageText, String partyId, int partySize, int partyMax, String matchSecret, String joinSecret, String spectateSecret, byte instance) {
        this.state = state;
        this.details = details;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.largeImageKey = largeImageKey;
        this.largeImageText = largeImageText;
        this.smallImageKey = smallImageKey;
        this.smallImageText = smallImageText;
        this.partyId = partyId;
        this.partySize = partySize;
        this.partyMax = partyMax;
        this.matchSecret = matchSecret;
        this.joinSecret = joinSecret;
        this.spectateSecret = spectateSecret;
        this.instance = instance;
    }

    public DiscordRichPresence toDiscordRichPresence(){
        var presence = new DiscordRichPresence();
        presence.state = state;
        presence.details = details;
        presence.startTimestamp = startTimestamp;
        presence.endTimestamp = endTimestamp;
        presence.largeImageKey = largeImageKey;
        presence.largeImageText = largeImageText;
        presence.smallImageKey = smallImageKey;
        presence.smallImageText = smallImageText;
        presence.partyId = partyId;
        presence.partySize = partySize;
        presence.partyMax = partyMax;
        presence.matchSecret = matchSecret;
        presence.joinSecret = joinSecret;
        presence.spectateSecret = spectateSecret;
        presence.instance = instance;
        return presence;
    }

    public DiscordRichPresence build(){
        return toDiscordRichPresence();
    }

    public String getState() {
        return state;
    }

    public DiscordRichPresenceBuilder setState(String state) {
        this.state = state;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public DiscordRichPresenceBuilder setDetails(String details) {
        this.details = details;
        return this;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public DiscordRichPresenceBuilder setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
        return this;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public DiscordRichPresenceBuilder setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
        return this;
    }

    public String getLargeImageKey() {
        return largeImageKey;
    }

    public DiscordRichPresenceBuilder setLargeImageKey(String largeImageKey) {
        this.largeImageKey = largeImageKey;
        return this;
    }

    public String getLargeImageText() {
        return largeImageText;
    }

    public DiscordRichPresenceBuilder setLargeImageText(String largeImageText) {
        this.largeImageText = largeImageText;
        return this;
    }

    public String getSmallImageKey() {
        return smallImageKey;
    }

    public DiscordRichPresenceBuilder setSmallImageKey(String smallImageKey) {
        this.smallImageKey = smallImageKey;
        return this;
    }

    public String getSmallImageText() {
        return smallImageText;
    }

    public DiscordRichPresenceBuilder setSmallImageText(String smallImageText) {
        this.smallImageText = smallImageText;
        return this;
    }

    public String getPartyId() {
        return partyId;
    }

    public DiscordRichPresenceBuilder setPartyId(String partyId) {
        this.partyId = partyId;
        return this;
    }

    public int getPartySize() {
        return partySize;
    }

    public DiscordRichPresenceBuilder setPartySize(int partySize) {
        this.partySize = partySize;
        return this;
    }

    public int getPartyMax() {
        return partyMax;
    }

    public DiscordRichPresenceBuilder setPartyMax(int partyMax) {
        this.partyMax = partyMax;
        return this;
    }

    public String getMatchSecret() {
        return matchSecret;
    }

    public DiscordRichPresenceBuilder setMatchSecret(String matchSecret) {
        this.matchSecret = matchSecret;
        return this;
    }

    public String getJoinSecret() {
        return joinSecret;
    }

    public DiscordRichPresenceBuilder setJoinSecret(String joinSecret) {
        this.joinSecret = joinSecret;
        return this;
    }

    public String getSpectateSecret() {
        return spectateSecret;
    }

    public DiscordRichPresenceBuilder setSpectateSecret(String spectateSecret) {
        this.spectateSecret = spectateSecret;
        return this;
    }

    public byte getInstance() {
        return instance;
    }

    public DiscordRichPresenceBuilder setInstance(byte instance) {
        this.instance = instance;
        return this;
    }
}