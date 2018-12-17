import 'package:flutter/services.dart';
import 'package:flutter_music_plugin/src/music.dart';

const MethodChannel _channel = const MethodChannel('flutter_music_plugin');

class MusicFinder {
  List<Music> _musicList;
  bool _isPending = false;

  factory MusicFinder() => MusicFinder._internal();

  MusicFinder._internal();

  Future<List<Music>> getAll() async {
    if (_musicList == null) {
      if (_isPending) { // Waiting for music list
        while (_musicList != null) {
          await new Future.delayed(Duration(seconds: 1));
        }
        return _musicList;
      } else { // Getting music list from media store
        _isPending = true;
        List<dynamic> musicMapList =
        await _channel.invokeMethod('finder.getAll');
        List<Music> musicList =
        musicMapList.map((musicMap) => Music.fromMap(musicMap)).toList();
        _musicList = musicList;
        _isPending = false;
        return musicList;
      }
    } else { // Getting music list from cache
      return _musicList;
    }
  }

  void removeCache() {
    _musicList = null;
  }
}
