package me.xbones.reportplus.core.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.NonNull;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;

public class GsonConfig<T extends GsonConfigData> {

    @NonNull
    @Getter
    private T gsonConfigData;

    @NonNull
    @Getter
    private File file;

    public GsonConfig(@NonNull T gsonConfigData, @NonNull File file) {
        this.gsonConfigData = gsonConfigData;
        this.file = file;

        load();
    }

    public void save() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedSink sink = Okio.buffer(Okio.sink(file))) {
                sink.writeUtf8(new GsonBuilder().setPrettyPrinting().create().toJson(gsonConfigData));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (!file.exists()) {
                save();
            }

            StringBuilder json = new StringBuilder();
            try (Source fileSource = Okio.source(file);
                 BufferedSource bufferedSource = Okio.buffer(fileSource)) {

                while (true) {
                    String line = bufferedSource.readUtf8Line();
                    if (line == null) break;

                    json.append(line);
                }
            }

            gsonConfigData = (T) new Gson().fromJson(json.toString(), gsonConfigData.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
