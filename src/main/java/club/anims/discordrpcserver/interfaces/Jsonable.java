package club.anims.discordrpcserver.interfaces;

import com.google.gson.Gson;

public interface Jsonable<T> {
    default String toJson() {
        return new Gson().toJson(this);
    }

    default T fromJson(String json) {
        return (T) new Gson().fromJson(json, getClass());
    }
}
