import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_music_plugin/flutter_music_plugin.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  MusicFinder _musicFinder = MusicFinder();
  MusicPlayer _musicPlayer = MusicPlayer();

  Widget _buildAlbumArt(Music music) {
    if (music.albumArt != null) {
      return CircleAvatar(backgroundImage: FileImage(File(music.albumArt)));
    } else {
      return CircleAvatar(child: Text(music.title[0]));
    }
  }

  Widget _buildTitle(Music music) {
    return Text(
      music.title,
      overflow: TextOverflow.ellipsis,
    );
  }

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Music Plugin Example App',
        home: Scaffold(
            appBar: AppBar(
              title: const Text('Flutter Music Plugin Example App'),
            ),
            body: FutureBuilder(
              future: _musicFinder.getAll(),
              builder:
                  (BuildContext context, AsyncSnapshot<List<Music>> snapshot) {
                if (snapshot.connectionState == ConnectionState.done &&
                    snapshot.hasData &&
                    snapshot.data != null) {
                  List<Music> musicList = snapshot.data;
                  return ListView.builder(
                      itemCount: musicList.length,
                      itemBuilder: (BuildContext context, int index) {
                        Music music = musicList[index];
                        return ListTile(
                            leading: _buildAlbumArt(music),
                            title: _buildTitle(music),
                            subtitle: Text(
                                '${music.artist}\n${music.album} / ${music.genre}'),
                            isThreeLine: true,
                            onTap: () {
                              _musicPlayer.prepare(music);
                              _musicPlayer.start();
                              showModalBottomSheet(
                                  context: context,
                                  builder: (BuildContext builder) {
                                    return ListTile(
                                      leading: _buildAlbumArt(music),
                                      title: _buildTitle(music),
                                      subtitle: Text(music.artist),
                                      trailing: Icon(Icons.play_arrow),
                                    );
                                  }).then((value) => _musicPlayer.stop());
                            });
                      });
                } else {
                  return Center(
                      child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      CircularProgressIndicator(),
                      Padding(
                          padding: EdgeInsets.only(top: 16),
                          child: Text('Fetching music list...')),
                    ],
                  ));
                }
              },
            )));
  }
}
