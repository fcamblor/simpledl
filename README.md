# Simpledl

A simple file download API providing utilities to :
- localize proper file to download depending on your current platform
- extract tar.bz2/zip files after download

These classes come from [@dgageot's simplelenium library](https://github.com/dgageot/simplelenium), and are intended to be reused for different needs.

You can use it by subclassing `Downloader` class with your own needs, for instance :

```
package net.codestory.simpledl.samples;

import net.codestory.simpledl.Configuration;
import net.codestory.simpledl.Downloader;

import java.io.*;

public class PhantomJsDownloader extends Downloader {
  private PlatformInstall customPlatformInstall = null;

  public PhantomJsDownloader(PlatformInstall customPlatformInstall) {
    this();
    this.customPlatformInstall = customPlatformInstall;
  }

  public PhantomJsDownloader() {
    this(DEFAULT_RETRY_DOWNLOAD, DEFAULT_RETRY_CONNECT);
  }

  protected PhantomJsDownloader(int retryDownload, int retryConnect) {
    super(retryConnect, retryDownload);
  }

  public File downloadAndExtract() {
    PlatformInstall platformInstall;
    if (isCustomized()) {
      platformInstall = customPlatformInstall;
    } else if (isWindows()) {
      platformInstall = new PlatformInstall(
        "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-1.9.8-windows.zip",
        "phantomjs-1.9.8-windows/phantomjs.exe");
    } else if (isMac()) {
      platformInstall = new PlatformInstall(
        "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-1.9.8-macosx.zip",
        "phantomjs-1.9.8-macosx/bin/phantomjs");
    } else if (isLinux32()) {
      platformInstall = new PlatformInstall(
        "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-1.9.8-linux-i686.tar.bz2",
        "phantomjs-1.9.8-linux-i686/bin/phantomjs");
    } else {
      platformInstall = new PlatformInstall(
        "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-1.9.8-linux-x86_64.tar.bz2",
        "phantomjs-1.9.8-linux-x86_64/bin/phantomjs");
    }

    return downloadAndExtract(new File(Configuration.USER_HOME.get(), ".phantomjs"), platformInstall);
  }

  protected boolean isCustomized() {
    return customPlatformInstall != null;
  }
}
```
