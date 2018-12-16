package astro36.github.io.fluttermusicplugin;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class FlutterMusicPlugin implements MethodCallHandler {
    MusicFinder musicFinder;

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_music_plugin");
        channel.setMethodCallHandler(new FlutterMusicPlugin(registrar.activeContext()));
    }

    private FlutterMusicPlugin(Context context) {
        musicFinder = new MusicFinder(context.getContentResolver());
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        switch (call.method) {
            case "getAllMusicList":
                ArrayList<HashMap<String, Object>> musicMapList = new ArrayList<>();
                for (Music music : musicFinder.getAll()) {
                    musicMapList.add(music.toHashMap());
                }
                result.success(musicMapList);
                break;
            case "getPlatformVersion":
                result.success("Android " + android.os.Build.VERSION.RELEASE);
                break;
            default:
                result.notImplemented();
        }
    }
}
