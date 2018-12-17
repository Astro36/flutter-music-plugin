import 'package:flutter/services.dart';
import 'package:flutter_music_plugin/src/music.dart';

const MethodChannel _channel = const MethodChannel('flutter_music_plugin');

class MusicPlayer {
  factory MusicPlayer() => const MusicPlayer._internal();

  const MusicPlayer._internal();

  Future<int> getCurrentPosition() async {
    return await _channel.invokeMethod("player.getCurrentPosition");
  }

  Future<bool> isPlaying() async {
    return await _channel.invokeMethod("player.isPlaying");
  }

  void mute() {
    _channel.invokeMethod("player.mute");
  }

  void pause() {
    _channel.invokeMethod("player.pause");
  }

  void prepare(Music music) {
    _channel.invokeMethod("player.prepare", music.uri);
  }

  void seekTo(int msec) {
    _channel.invokeMethod("player.seekTo", msec);
  }

  void start() {
    _channel.invokeMethod("player.start");
  }

  void stop() {
    _channel.invokeMethod("player.stop");
  }

  void unmute() {
    _channel.invokeMethod("player.unmute");
  }
}
