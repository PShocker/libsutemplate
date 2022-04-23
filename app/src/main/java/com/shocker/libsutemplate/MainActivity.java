package com.shocker.libsutemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.shocker.libsutemplate.databinding.ActivityMainBinding;
import com.topjohnwu.superuser.ipc.RootService;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'libsutemplate' library on application startup.
    static {
        System.loadLibrary("libsutemplate");
    }

    public static ITestService iTestService;
    class AIDLConnection implements ServiceConnection {

        private final boolean isDaemon;

        AIDLConnection(boolean b) {
            isDaemon = b;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iTestService= ITestService.Stub.asInterface(service);
            Log.d("Shocker","Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("Shocker","Disconnected");

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button t=findViewById(R.id.sample_text);

        RootService.bind(new Intent(this, AIDLService.class), new AIDLConnection(false));
        t.setOnClickListener(view -> {
            try {
                Log.d("Shocker","getPid:"+iTestService.getPid());
                Log.d("Shocker","getUid:"+iTestService.getUid());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * A native method that is implemented by the 'libsutemplate' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}