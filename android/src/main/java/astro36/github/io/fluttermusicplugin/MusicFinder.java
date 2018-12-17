package astro36.github.io.fluttermusicplugin;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.util.ArrayList;

public class MusicFinder {
    private ContentResolver contentResolver;

    public MusicFinder(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public ArrayList<Music> getAll() {
        ArrayList<Music> musicList = new ArrayList<>();
        String mediaSelection = MediaStore.Audio.Media.IS_MUSIC + "=?";
        String[] mediaSelectionArgs = {"1"};
        Cursor mediaCursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, mediaSelection, mediaSelectionArgs, null);
        if (mediaCursor != null) {
            int idColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int albumColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int albumIdColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int artistColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int artistIdColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
            int durationColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int titleColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int uriColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            Uri genreIdUri = Uri.parse("content://media/external/audio/genres/all/members");
            while (mediaCursor.moveToNext()) {
                String album = mediaCursor.getString(albumColumn);
                long albumId = mediaCursor.getLong(albumIdColumn);
                String artist = mediaCursor.getString(artistColumn);
                long artistId = mediaCursor.getLong(artistIdColumn);
                long audioId = mediaCursor.getLong(idColumn);
                long duration = mediaCursor.getLong(durationColumn);
                String title = mediaCursor.getString(titleColumn);
                String uri = mediaCursor.getString(uriColumn);
                Music.Builder builder = new Music.Builder()
                        .setAlbum(album)
                        .setAlbumId(albumId)
                        .setArtist(artist)
                        .setArtistId(artistId)
                        .setAudioId(audioId)
                        .setDuration(duration)
                        .setTitle(title)
                        .setUri(uri);
                String albumSelection = MediaStore.Audio.Albums._ID + "=?";
                String[] albumSelectionArgs = {String.valueOf(albumId)};
                Cursor albumCursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, albumSelection, albumSelectionArgs, null);
                if (albumCursor != null && albumCursor.moveToFirst()) {
                    builder.setAlbumArt(albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                    albumCursor.close();
                }
                if (Build.VERSION.SDK_INT >= 11) {
                    Cursor genreCursor = contentResolver.query(MediaStore.Audio.Genres.getContentUriForAudioId("external", (int) audioId), null, null, null, null);
                    if (genreCursor != null && genreCursor.moveToFirst()) {
                        builder.setGenre(genreCursor.getString(genreCursor.getColumnIndex(MediaStore.Audio.Genres.NAME)));
                        genreCursor.close();
                    }
                }
                String genreIdSelection = MediaStore.Audio.Genres.Members.AUDIO_ID + "=?";
                String[] genreIdSelectionArgs = {String.valueOf(audioId)};
                Cursor genreIdCursor = contentResolver.query(genreIdUri, null, genreIdSelection, genreIdSelectionArgs, null);
                if (genreIdCursor != null && genreIdCursor.moveToFirst()) {
                    builder.setGenreId(genreIdCursor.getLong(genreIdCursor.getColumnIndex(MediaStore.Audio.Genres.Members.GENRE_ID)));
                    genreIdCursor.close();
                }
                musicList.add(builder.build());
            }
            mediaCursor.close();
        }
        return musicList;
    }
}
