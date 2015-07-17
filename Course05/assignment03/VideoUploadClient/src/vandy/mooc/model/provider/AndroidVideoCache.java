package vandy.mooc.model.provider;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import vandy.mooc.model.mediator.webdata.Video;

public class AndroidVideoCache {
	
	private Context mContext;
	
	public AndroidVideoCache(Context ctx)
	{
		mContext = ctx;
	}
	
	public Video getVideoById(Long videoId)
	{
		Uri videoUri = ContentUris.withAppendedId(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 
				videoId);
		
		try(Cursor cursor = 
				mContext.getContentResolver().query(videoUri, 
												null, 
												null, 
												null, 
												null)){
				if(cursor.moveToFirst())
					return getVideo(cursor);
				else
					return null;
				
		}
	}
	
	public String getVideoFilePath(String videoTitle){
		String mSelectionClause =
				MediaStore.Video.Media.DISPLAY_NAME + "=?";
		
		String[] args = { videoTitle };
		
		try(Cursor cursor =
				mContext.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 
						null, 
						mSelectionClause, 
						args, 
						null)) {
			if(cursor.moveToFirst())
				return cursor.getString
						(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
			else
				return null;
		}
	}
	
	private Video getVideo(Cursor cursor) 
			throws IllegalArgumentException{
		String name =
				cursor.getString
				(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
		
		Long duration =
				cursor.getLong
				(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
		
		String contentType =
				cursor.getString
				(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
				
		
		
		return new Video(name,
						duration,
						contentType);
	}
	
}