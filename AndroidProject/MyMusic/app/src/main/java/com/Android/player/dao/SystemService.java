package com.Android.player.dao;



import org.apache.http.util.EncodingUtils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.MediaStore;

public class SystemService {

	private Context context;
	private Cursor cursor;

	public SystemService(Context context) {
		this.context = context;
	}

	public Cursor allSongs() {
		if (cursor != null)
			return cursor;
		ContentResolver resolver = context.getContentResolver();
		cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		return cursor;

	}

	/**
	 * 读取正在播放歌曲的艺术家
	 * @return
	 */
	public String getArtist() {

		return cursor.getString(cursor
				.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
	}

	/**
	 * 读取正在播放歌曲名字
	 * @return 歌曲名字
	 */
	public String getTitle() {
		String title = cursor.getString(cursor
				.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
		try {
			title=EncodingUtils.getString(title.getBytes(), "UTF-8");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return title;
	}

	/**
	 *  读取正在播放歌曲的专辑
	 * @return 专辑名
	 * @throws RemoteException
	 */
	public String getAlbum() throws RemoteException {
		return cursor.getString(cursor
				.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
	}

	/*public int getDuration() throws RemoteException {
		// 获得当前歌曲的时长
		return player.getDuration();
	}public int getTime() throws RemoteException {
		// 获得当前的媒体时间
		return player.getCurrentPosition();
	}*/

}
