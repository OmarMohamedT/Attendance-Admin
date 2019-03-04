package com.example.attendanceadmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;

import java.io.File;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  private static final String SHARE_CHANNEL = "channel:me.ammar.share/share";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    new MethodChannel(this.getFlutterView(), SHARE_CHANNEL).setMethodCallHandler(new MethodChannel.MethodCallHandler() {
      public final void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        if (methodCall.method.equals("shareFile")) {
          shareFile((String) methodCall.arguments);
        }
        if (methodCall.method.equals("shareCSV")) {
          shareCSV((String) methodCall.arguments);
        }
      }
    });
  }
  private void shareFile(String path) {
    File imageFile = new File(this.getApplicationContext().getCacheDir(), path);
    Uri contentUri = FileProvider.getUriForFile(this, "me.ammar.share", imageFile);
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("image/png");

    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
    this.startActivity(Intent.createChooser(shareIntent, "Share image using"));
  }
  private void shareCSV(String path) {
    File csvFile = new File(this.getApplicationContext().getCacheDir(), path);
    Uri contentUri = FileProvider.getUriForFile(this, "me.ammar.share", csvFile);
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("text/*");

    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
    this.startActivity(Intent.createChooser(shareIntent, "Share CSV using"));
  }

}
