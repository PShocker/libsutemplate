package com.shocker.libsutemplate;

import android.content.Intent;
import android.os.IBinder;
import android.os.Process;

import androidx.annotation.NonNull;

import com.topjohnwu.superuser.ipc.RootService;

class AIDLService extends RootService {
   static {
      // Only load the library when this class is loaded in a root process.
      // The classloader will load this class (and call this static block) in the non-root
      // process because we accessed it when constructing the Intent to send.
      // Add this check so we don't unnecessarily load native code that'll never be used.
      if (Process.myUid() == 0)
         System.loadLibrary("libsutemplate");
   }

   native int nativeGetUid();

   class TestIPC extends ITestService.Stub {
      @Override
      public int getPid() {
         return Process.myPid();
      }

      @Override
      public int getUid() {
         return nativeGetUid();
      }
   }


   @Override
   public IBinder onBind(@NonNull Intent intent) {
      return new TestIPC();
   }
}
