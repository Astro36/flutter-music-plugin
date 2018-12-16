package astro36.github.io.fluttermusicplugin;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

public class MusicFinder {
    private ContentResolver contentResolver;
    private ArrayList<Music> musicList = new ArrayList<>();

    public MusicFinder(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        update();
    }

    public ArrayList<Music> getAll() {
        return musicList;
    }

    private void update() {
        musicList.clear();
        String albumSelection = MediaStore.Audio.Albums.ALBUM_ID + "=?";
        String genreSelection = MediaStore.Audio.Genres.Members.ALBUM_ID + "=?";
        String mediaSelection = MediaStore.Audio.Media.IS_MUSIC + "=?";
        String[] mediaSelectionArgs = {"1"};
        Cursor mediaCursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, mediaSelection, mediaSelectionArgs, null);
        if (mediaCursor != null) {
            int albumColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int albumIdColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int artistColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int artistIdColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
            int durationColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int titleColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int uriColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            while (mediaCursor.moveToNext()) {
                String album = mediaCursor.getString(albumColumn);
                long albumId = mediaCursor.getLong(albumIdColumn);
                String artist = mediaCursor.getString(artistColumn);
                long artistId = mediaCursor.getLong(artistIdColumn);
                long duration = mediaCursor.getLong(durationColumn);
                String title = mediaCursor.getString(titleColumn);
                String uri = mediaCursor.getString(uriColumn);
                Music.Builder builder = new Music.Builder()
                        .setAlbum(album)
                        .setAlbumId(albumId)
                        .setArtist(artist)
                        .setArtistId(artistId)
                        .setDuration(duration)
                        .setTitle(title)
                        .setUri(uri);
                Cursor albumCursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, albumSelection, new String[]{String.valueOf(albumId)}, null);
                if (albumCursor != null && albumCursor.moveToFirst()) {
                    builder.setAlbumArt(albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                    albumCursor.close();
                }
                Cursor genreCursor = contentResolver.query(MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI, null, genreSelection, new String[]{String.valueOf(albumId)}, null);
                if (genreCursor != null && genreCursor.moveToFirst()) {
                    builder.setGenre(genreCursor.getString(genreCursor.getColumnIndex(MediaStore.Audio.Genres.NAME)));
                    builder.setGenreId(genreCursor.getLong(genreCursor.getColumnIndex(MediaStore.Audio.Genres.Members.GENRE_ID)));
                    genreCursor.close();
                }
                musicList.add(builder.build());
            }
            mediaCursor.close();
        }
    }
}
