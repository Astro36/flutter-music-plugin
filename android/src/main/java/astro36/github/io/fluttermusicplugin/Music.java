package astro36.github.io.fluttermusicplugin;

import java.util.HashMap;

public class Music {
    private final String album;
    private final String albumArt;
    private final long albumId;
    private final String artist;
    private final long artistId;
    private final long duration;
    private final String genre;
    private final long genreId;
    private final String title;
    private final String uri;

    public static class Builder {
        private String album = null;
        private String albumArt = null;
        private long albumId = -1;
        private String artist = null;
        private long artistId = -1;
        private long duration = -1;
        private String genre = null;
        private long genreId = -1;
        private String title = null;
        private String uri = null;

        public Builder setAlbum(String album) {
            this.album = album;
            return this;
        }

        public Builder setAlbumArt(String albumArt) {
            this.albumArt = albumArt;
            return this;
        }

        public Builder setAlbumId(long albumId) {
            this.albumId = albumId;
            return this;
        }

        public Builder setArtist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder setArtistId(long artistId) {
            this.artistId = artistId;
            return this;
        }

        public Builder setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        public Builder setGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder setGenreId(long genreId) {
            this.genreId = genreId;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setUri(String uri) {
            this.uri = uri;
            return this;
        }

        public Music build() {
            return new Music(this);
        }
    }

    private Music(Builder builder) {
        album = builder.album;
        albumArt = builder.albumArt;
        albumId = builder.albumId;
        artist = builder.artist;
        artistId = builder.artistId;
        duration = builder.duration;
        genre = builder.genre;
        genreId = builder.genreId;
        title = builder.title;
        uri = builder.uri;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> songMap = new HashMap<>();
        songMap.put("album", album);
        songMap.put("albumArt", albumArt);
        songMap.put("albumId", albumId);
        songMap.put("artist", artist);
        songMap.put("artistId", artistId);
        songMap.put("duration", duration);
        songMap.put("genre", genre);
        songMap.put("genreId", genreId);
        songMap.put("title", title);
        songMap.put("uri", uri);
        return songMap;
    }
}
