package astro36.github.io.fluttermusicplugin;

import android.content.Context;
import android.media.AudioManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener;

public class FlutterMusicPlugin implements MethodCallHandler, RequestPermissionsResultListener {
    private MusicFinder musicFinder;
    private MusicPlayer musicPlayer;

    private FlutterMusicPlugin(Context context) {
        musicFinder = new MusicFinder(context.getContentResolver());
        musicPlayer = new MusicPlayer((AudioManager) context.getSystemService(Context.AUDIO_SERVICE));
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_music_plugin");
        channel.setMethodCallHandler(new FlutterMusicPlugin(registrar.activeContext()));
    }

    @Override
    public void onMethodCall(MethodCall call, final Result result) {
        switch (call.method) {
            case "finder.getAll":
                ExecutorService executor = Executors.newCachedThreadPool();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<HashMap<String, Object>> musicMapList = new ArrayList<>();
                        for (Music music : musicFinder.getAll()) {
                            musicMapList.add(music.toHashMap());
                        }
                        System.out.println(musicMapList.size());
                        result.success(musicMapList);
                    }
                });
                executor.shutdown();
                break;
            case "player.getCurrentPosition":
                result.success(musicPlayer.getCurrentPosition());
                break;
            case "player.isPlaying":
                result.success(musicPlayer.isPlaying());
                break;
            case "player.mute":
                musicPlayer.mute();
                break;
            case "player.pause":
                musicPlayer.pause();
                break;
            case "player.prepare":
                musicPlayer.prepare((String) call.arguments);
                break;
            case "player.seekTo":
                musicPlayer.seekTo((int) call.arguments);
                break;
            case "player.start":
                musicPlayer.start();
                break;
            case "player.stop":
                musicPlayer.stop();
                break;
            case "player.unmute":
                musicPlayer.unmute();
                break;
            default:
                result.notImplemented();
        }
    }

    @Override
    public boolean onRequestPermissionsResult(int i, String[] strings, int[] ints) {
        return false;
    }
}
