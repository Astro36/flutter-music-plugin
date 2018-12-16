import 'dart:async';

import 'package:flutter/services.dart';

class FlutterMusicPlugin {
  static const MethodChannel _channel =
      const MethodChannel('flutter_music_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}