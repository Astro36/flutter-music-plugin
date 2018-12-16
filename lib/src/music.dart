class Music {
  String album;
  String albumArt;
  int albumId;
  String artist;
  int artistId;
  int duration;
  String genre;
  int genreId;
  String title;
  String uri;

  Music.fromMap(Map map) {
    album = map["album"];
    albumArt = map["albumArt"];
    albumId = map["albumId"];
    artist = map["artist"];
    artistId = map["artistId"];
    duration = map["duration"];
    genre = map["genre"];
    genreId = map["genreId"];
    title = map["title"];
    uri = map["uri"];
  }
}
