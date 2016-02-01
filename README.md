# Simpledl

A simple file download API providing utilities to :
- localize proper file to download depending on your current platform
- extract tar.bz2/zip files after download

These classes come from [@dgageot's simplelenium library](https://github.com/dgageot/simplelenium), and are intended to be reused for different needs.

You can use it by subclassing `Downloader` class with your own needs, for instance :

```
import net.codestory.simplelenium.driver.Configuration;
import net.codestory.simplelenium.driver.Downloader;
import net.codestory.simplelenium.driver.LockFile;

import java.io.*;

public class PhantomJsDownloader extends Downloader {
  String customPhantomJSUrl;
  String customPhantomJSExe;

  public PhantomJsDownloader(String customPhantomJSUrl, String customPhantomJSExe) {
    this();
    this.customPhantomJSUrl = customPhantomJSUrl;
    this.customPhantomJSExe = customPhantomJSExe;
  }

  public PhantomJsDownloader() {
    this(DEFAULT_RETRY_DOWNLOAD, DEFAULT_RETRY_CONNECT);
  }

  protected PhantomJsDownloader(int retryDownload, int retryConnect) {
    super(retryConnect, retryDownload);
  }

  protected synchronized File downloadAndExtract() {
    File installDir = new File(Configuration.USER_HOME.get(), ".phantomjs");
    installDir.mkdirs();

    LockFile lock = new LockFile(new File(installDir, "lock"));
    lock.waitLock();
    try {
      String url;
      File phantomJsExe;
      if (isCustomized()) {
        url = customPhantomJSUrl;
        phantomJsExe = new File(installDir, customPhantomJSExe);
      } else if (isWindows()) {
        url = "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-1.9.8-windows.zip";
        phantomJsExe = new File(installDir, "phantomjs-1.9.8-windows/phantomjs.exe");
      } else if (isMac()) {
        url = "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-1.9.8-macosx.zip";
        phantomJsExe = new File(installDir, "phantomjs-1.9.8-macosx/bin/phantomjs");
      } else if (isLinux32()) {
        url = "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-1.9.8-linux-i686.tar.bz2";
        phantomJsExe = new File(installDir, "phantomjs-1.9.8-linux-i686/bin/phantomjs");
      } else {
        url = "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-1.9.8-linux-x86_64.tar.bz2";
        phantomJsExe = new File(installDir, "phantomjs-1.9.8-linux-x86_64/bin/phantomjs");
      }

      extractExe("phantomJs", url, installDir, phantomJsExe);

      return phantomJsExe;
    } finally {
      lock.release();
    }
  }

  protected boolean isCustomized() {
    return customPhantomJSUrl != null
        && customPhantomJSExe != null;
  }

}
```
