package com.guo.gongjiao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.util.Log;

class CountingInputStream extends BufferedInputStream {

	private long bytesReadMark = 0;
	private long bytesRead = 0;

	public CountingInputStream(InputStream in, int size) {
		super(in, size);
	}

	public CountingInputStream(InputStream in) {
		super(in);
	}

	public long getBytesRead() {
		return bytesRead;
	}

	public synchronized int read() throws IOException {

		int read = super.read();
		if (read >= 0) {
			bytesRead++;
		}
		return read;
	}

	public synchronized int read(byte[] b, int off, int len) throws IOException {

		int read = super.read(b, off, len);
		if (read >= 0) {
			bytesRead += read;
		}
		return read;
	}

	public synchronized long skip(long n) throws IOException {

		long skipped = super.skip(n);
		if (skipped >= 0) {
			bytesRead += skipped;
		}
		return skipped;
	}

	public synchronized void mark(int readlimit) {
		super.mark(readlimit);
		bytesReadMark = bytesRead;
	}

	public synchronized void reset() throws IOException {
		super.reset();
		bytesRead = bytesReadMark;
	}
}

class DataDownloader extends Thread
{
	public StatusWriter Status;
	public boolean DownloadComplete = false;
	public boolean DownloadFailed = false;
	private MainActivity Parent;
	private String outFilesDir = null;
	class StatusWriter
	{
		private ProgressDialog Status;
		private MainActivity Parent;
		private String oldText = "";

		public StatusWriter( ProgressDialog _Status, MainActivity _Parent )
		{
			Status = _Status;
			Parent = _Parent;
		}
		public void setParent( ProgressDialog _Status, MainActivity _Parent )
		{

			synchronized(DataDownloader.this) {
				Status = _Status;
				Parent = _Parent;
				setText( oldText );
			}
		}
		
		public void setText(final String str)
		{

			class Callback implements Runnable
			{
				public ProgressDialog Status;
				public String text;
				public void run()
				{
					Status.setMessage(text);
				}
			}
			synchronized(DataDownloader.this) {
				Callback cb = new Callback();
				oldText = new String(str);
				cb.text = new String(str);
				cb.Status = Status;

				if( Parent != null && Status != null )
					Parent.runOnUiThread(cb);
			}
		}
		
	}

	public DataDownloader( MainActivity _Parent, ProgressDialog _Status )
	{
		Parent = _Parent;
		Status = new StatusWriter( _Status, _Parent );
		//Status.setText( "Connecting to " + Globals.DataDownloadUrl );
		outFilesDir = Globals.DataDir;
		DownloadComplete = false;
		this.start();
	}
	
	public void setStatusField(ProgressDialog _Status)
	{
		synchronized(this) {
			Status.setParent( _Status, Parent );
		}
	}

	@Override
	public void run()
	{	

		if( ! DownloadDataFile(Globals.DataDownloadUrl, "DownloadFinished.flag") )
		{
			DownloadFailed = true;
			return;
		}

		DownloadComplete = true;

		initParent();
	}

	public boolean DownloadDataFile(final String DataDownloadUrl, final String DownloadFlagFileName)
	{	

		Resources res = Parent.getResources();

		String path = getOutFilePath(DownloadFlagFileName);
		InputStream checkFile = null;
		try {
			checkFile = new FileInputStream( path );
		} catch( FileNotFoundException e ) {
		} catch( SecurityException e ) { };
		if( checkFile != null )
		{
			try {

				byte b[] = new byte[ Globals.DataDownloadUrl.getBytes("UTF-8").length + 1 ];
				int readed = checkFile.read(b);
				String compare = new String( b, 0, readed, "UTF-8" );  //DataDownloadUrl=data.zip
				boolean matched = false;

				if( compare.compareTo(DataDownloadUrl) == 0 )
					matched = true;

				if( ! matched )
					throw new IOException();
				Status.setText( res.getString(R.string.download_unneeded) );
				return true;
			} catch ( IOException e ) {};
		}
		checkFile = null;  //----1

		try {
			(new File( outFilesDir )).mkdirs();
			OutputStream out = new FileOutputStream( getOutFilePath(".nomedia") );
			out.flush();
			out.close();
		}
		catch( SecurityException e ) {}
		catch( FileNotFoundException e ) {}
		catch( IOException e ) {};

		long totalLen = 0;
		CountingInputStream stream;
		byte[] buf = new byte[16384];

		String url = DataDownloadUrl;
		System.out.println("Unpacking from assets: '" + url + "'");
		try {

			stream = new CountingInputStream(Parent.getAssets().open(url), 8192);

			while( stream.skip(65536) > 0 ) { };

			totalLen = stream.getBytesRead();
			stream.close();

			stream = new CountingInputStream(Parent.getAssets().open(url), 8192);
		} catch( IOException e ) {
			System.out.println("Unpacking from assets '" + url + "' - error: " + e.toString());
			Status.setText( res.getString(R.string.error_dl_from, url) );
			return false;
		}
		ZipInputStream zip = new ZipInputStream(stream);
		
		while(true)
		{
			ZipEntry entry = null;
			try {

				entry = zip.getNextEntry();
				if( entry != null )
					System.out.println("Reading from zip file '" + url + "' entry '" + entry.getName() + "'");
			} catch( java.io.IOException e ) {
				Status.setText( res.getString(R.string.error_dl_from, url) );
				System.out.println("Error reading from zip file '" + url + "': " + e.toString());
				return false;
			}

			if( entry == null )
			{
				System.out.println("Reading from zip file '" + url + "' finished");
				break;
			}

			if( entry.isDirectory() )
			{
				System.out.println("Creating dir '" + getOutFilePath(entry.getName()) + "'");
				try {
					(new File( getOutFilePath(entry.getName()) )).mkdirs();
				} catch( SecurityException e ) { };
				continue;
			}

			OutputStream out = null;
			path = getOutFilePath(entry.getName());

			try {
				(new File( path.substring(0, path.lastIndexOf("/") ))).mkdirs();
			} catch( SecurityException e ) { };
			
			try {

				CheckedInputStream check = new CheckedInputStream( new FileInputStream(path), new CRC32() );
				while( check.read(buf, 0, buf.length) > 0 ) {};
				check.close();

				if( check.getChecksum().getValue() != entry.getCrc() )
				{
					File ff = new File(path);
					ff.delete();
					throw new Exception();
				}
				System.out.println("File '" + path + "' exists and passed CRC check - not overwriting it");

				continue;
			} catch( Exception e )
			{
			}
			try {
				out = new FileOutputStream( path );
			} catch( FileNotFoundException e ) {
				System.out.println("Saving file '" + path + "' - cannot create file: " + e.toString());
			} catch( SecurityException e ) {
				System.out.println("Saving file '" + path + "' - cannot create file: " + e.toString());
			};

			if( out == null )
			{
				Status.setText( res.getString(R.string.error_write, path) );
				System.out.println("Saving file '" + path + "' - cannot create file");
				return false;
			}

			float percent = 0.0f;
			if( totalLen > 0 )
				percent = stream.getBytesRead() * 100.0f / totalLen;
			Status.setText( res.getString(R.string.dl_progress, percent, path) );
			
			try {

				int len = zip.read(buf);
				while (len >= 0)
				{
					if(len > 0)
						out.write(buf, 0, len);
					len = zip.read(buf);

					percent = 0.0f;
					if( totalLen > 0 )
						percent = stream.getBytesRead() * 100.0f / totalLen;
					Status.setText( res.getString(R.string.dl_progress, percent, path) );
				}
				out.flush();
				out.close();
				out = null;
			} catch( java.io.IOException e ) {
				Status.setText( res.getString(R.string.error_write, path) );
				System.out.println("Saving file '" + path + "' - error writing or downloading: " + e.toString());
				return false;
			}
			
			try {

				CheckedInputStream check = new CheckedInputStream( new FileInputStream(path), new CRC32() );
				while( check.read(buf, 0, buf.length) > 0 ) {};
				check.close();
				if( check.getChecksum().getValue() != entry.getCrc() )
				{
					File ff = new File(path);
					ff.delete();
					throw new Exception();
				}
			} catch( Exception e )
			{
				Status.setText( res.getString(R.string.error_write, path) );
				System.out.println("Saving file '" + path + "' - CRC check failed");
				return false;
			}
			System.out.println("Saving file '" + path + "' done");
		}

		OutputStream out = null;

		path = getOutFilePath(DownloadFlagFileName);
		try {
			out = new FileOutputStream( path );
			out.write(DataDownloadUrl.getBytes("UTF-8"));
			out.flush();
			out.close();
		} catch( FileNotFoundException e ) {
		} catch( SecurityException e ) {
		} catch( java.io.IOException e ) {
			Status.setText( res.getString(R.string.error_write, path) );
			return false;
		};
		Status.setText( res.getString(R.string.dl_finished) );

		try {
			stream.close();
		} catch( java.io.IOException e ) {
		};

		return true;
	};

	private void initParent()
	{
		class Callback implements Runnable
		{
			public MainActivity Parent;
			public void run()
			{
				Parent.getFileList();
				Log.e("guojs","initParent!");
			}
		}
		Callback cb = new Callback();
		synchronized(this) {
			cb.Parent = Parent;
			if(Parent != null)
				Parent.runOnUiThread(cb);
		}
	}

	private String getOutFilePath(final String filename)
	{
		return outFilesDir + "/" + filename;
	};
	

}

