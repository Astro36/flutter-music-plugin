import 'package:flutter/services.dart';
import 'package:flutter_music_plugin/src/music.dart';

class MusicFinder {
  MethodChannel _channel;

  MusicFinder() {
    _channel = MethodChannel('flutter_music_plugin');
  }

  Future<List<Music>> getAllMusicList() async {
    List<Map<String, dynamic>> musicMapList = await _channel.invokeMethod('getAllMusicList');
    List<Music> musicList = musicMapList.map((musicMap) => Music.fromMap(musicMap));
    return musicList;
  }
}
