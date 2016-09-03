package somitsolutions.unit.unitconverter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.io.IOException;




/**
 * Created by MCIS-SRINIVASRAO-19 on 08-06-2016.
 */
public class Flashmain extends Activity implements SurfaceHolder.Callback{
    private static final String TAG = Flashmain.class.getSimpleName();
    ImageView unitconverter, compass, stopwatch;
    ToggleButton flashoff;
    private boolean hasFlash;
    public String camera;
    Camera.Parameters p;
    static Camera cam = null;
    Integer modeint;
    String ON;
    String checkOn;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    private static boolean isFlashOn = false;
    SeekBar seekbar;
    public volatile boolean requestStop = false;
    public volatile boolean isRunning = false;
    public volatile int delayoff = 300;
    public volatile String errorMessage = "";
    String progres;
    int milliseconds;
    TextView progressing;
    TextView count5,count0,count1,count2,count3,count4;
    String  text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_main);
        unitconverter = (ImageView) findViewById(R.id.imageViewunitconverter);
        compass = (ImageView) findViewById(R.id.imageViewcompass);
        stopwatch = (ImageView) findViewById(R.id.imageViewstopwatch);
        count0=(TextView)findViewById(R.id.count0);
        count1=(TextView)findViewById(R.id.count1);
        count2=(TextView)findViewById(R.id.count2);
        count3=(TextView)findViewById(R.id.count3);
        count4=(TextView)findViewById(R.id.count4);
//        count5=(TextView)findViewById(R.id.count5);
        modeint = 0;
        count0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeint=0;
            }
        });

        count1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeint = 2;
            }
        });
        count2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeint = 5;
            }
        });
        count3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeint = 7;
            }
        });
        count4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeint=10;

            }
        });

        hasFlash = Flashmain.this.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH);
        initCamera();

        cam = Camera.open();
        p = cam.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        cam.setParameters(p);
        flashoff = (ToggleButton) findViewById(R.id.imageViewflash);

        flashoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (flashoff.isChecked()) {
                    flashoff.setBackgroundResource(R.drawable.flash_on);
                    try {
                        new FlashThread().start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    requestStop = true;
                    new FlashThread().interrupt();
//                    p = cam.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//                    cam.setParameters(p);
//                    cam.stopPreview();
                    try {
                        flashoff.setBackgroundResource(R.drawable.flash_off);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        flashoff.setChecked(false);
        compass.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent compassIntent = new Intent(Flashmain.this,
                        CompassActivity.class);
                startActivity(compassIntent);
            }
        });
        unitconverter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent unitIntent = new Intent(Flashmain.this,
                        MainActivity.class);
                startActivity(unitIntent);
            }
        });
        stopwatch.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent watchIntent = new Intent(Flashmain.this,
                        StopWatchActivity.class);
                startActivity(watchIntent);
            }
        });
    }


    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);


    }
    class FlashThread extends Thread {
        // Default values

        int times = 100; // Times on (off not included)
        //        int milliseconds1; // Time between on-off (and off-on)
        int progress;

        // Empty constructor, to keep default values
        public FlashThread() {
//
        }

        // Set values to use
        public FlashThread(int times, int milliseconds1) {
            this.times = times;
//            this.milliseconds1 = milliseconds1;
        }

        @Override
        public void run() {
            try {
                requestStop = false;
                isRunning = true;
                // Initialize the Camera

                if (cam == null||mSurfaceHolder.getSurface()==null) {
                    cam = Camera.open();
                    cam.setPreviewDisplay(mSurfaceHolder);
                    cam.startPreview();
                }
                while (!requestStop) {

                    Camera.Parameters pon = cam.getParameters(), poff = cam.getParameters();
                    pon.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    poff.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//                    flashoff.setTextOn(String.valueOf(modeint));

                    try {
                        if (modeint == 0) {
                            if (cam == null || p == null) {
                                return;
                            }
                            if(flashoff.isChecked()) {
                                p = cam.getParameters();
                                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//                                flashoff.setBackgroundResource(R.drawable.flash_on);
                                cam.setParameters(p);
                                cam.startPreview();
                                isFlashOn = true;
                            }else{
                                new FlashThread().interrupt();
                                p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//                                flashoff.setBackgroundResource(R.drawable.flash_off);
                            }
                        } else if(modeint>0){
                            if (modeint == 1) {
                                milliseconds = 1000;
                            } else if (modeint == 2) {
                                milliseconds = 800;
                            } else if (modeint == 3) {
                                milliseconds = 600;
                            } else if (modeint == 4) {
                                milliseconds = 420;
                            } else if (modeint == 5) {
                                milliseconds = 330;
                            } else if (modeint == 6) {
                                milliseconds = 280;
                            } else if (modeint == 7) {
                                milliseconds = 200;
                            } else if (modeint == 8) {
                                milliseconds = 150;
                            } else if (modeint == 9) {
                                milliseconds = 100;
                            } else if (modeint == 10) {
                                milliseconds = 50;
                            }

                            Log.d("hiihihi", "" + milliseconds);
                            cam.setParameters(pon);
                            Thread.sleep(milliseconds);
                            cam.setParameters(poff);
                            Thread.sleep(delayoff);
                        }

                    } catch (InterruptedException e) {
                    } catch (RuntimeException ex) {
                        requestStop = true;
                        errorMessage = "Error setting camera flash status. Your device may be unsupported.";
                    }


                }
//                cam.release();
                releaseCamera();


                isRunning = false;
                requestStop = false;

            } catch (Exception e) {

            }
        }
    }
    private void toggleFlashLight() {

        System.err.println("#toggleFlashLight()");

        // If we have an reference to the camera
        if (cam != null) {
            // Get the parameters
            p = cam.getParameters();
            // Set the flash mode to the appropriate one
//            try {
//                cam.setPreviewTexture(surface);
//                Log.d("cam", "cam");
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
            p.setFlashMode((requestStop) ? Camera.Parameters.FLASH_MODE_OFF
                    : Camera.Parameters.FLASH_MODE_TORCH);
            // Set the Parameters to the camera
            cam.setParameters(p);
            // Flip the on state
            requestStop = !requestStop;
        }
    }



    private void releaseCamera() {
        if (cam != null) {
            cam.setPreviewCallback(null);
//            cam.stopPreview();
            mSurfaceView.getHolder().removeCallback(this);
            cam.release(); // release the camera for other applications
            cam = null;
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub

        requestStop = true;
        flashoff.setBackgroundResource(R.drawable.flash_off);
        flashoff.setChecked(false);
        super.onStop();
    }

    public void cameraFlashOff() {
        isFlashOn = false;
        try {
            Log.d("clicked false", "*************");
            flashoff.setBackgroundResource(R.drawable.flash_off);
            cam.stopPreview();
            cam.release();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    private void initCamera() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("OnResume", "True");
        if (cam != null) {
            Log.v("OnResume", "Camera is in use");
        } else {
            Log.v("OnResume", "Camera isn't in use");
            // layout = (RelativeLayout) findViewById(R.id.mainLayout);
            cam = Camera.open();
            Log.v("OnResume", "Camera has been opened");
            cam.startPreview();
            Log.v("OnResume", "CamPreview started");
            p = cam.getParameters();
            Log.v("OnResume", "Wrote parameters to variable p");
            // p.setFlashMode(Parameters.FLASH_MODE_TORCH);
            // Log.v("OnResume", "Set flashmode OFF");
            // cam.setParameters(p);
            // Log.v("OnResume", "set camera parameters");
            Log.d("Function", "surfaceChanged iniciado");
            if (mSurfaceHolder.getSurface() == null) {
                // preview surface does not exist
                return;
            }

            // stop preview before making changes
            try {
                cam.stopPreview();
            } catch (Exception e) {
                // ignore: tried to stop a non-existent preview
            }
            // set preview size and make any resize, rotate or
            // reformatting changes here

            // start preview with new settings
            try {
                cam.setPreviewDisplay(mSurfaceHolder);
                cam.startPreview();

            } catch (Exception e) {
                Log.d(TAG, "Error starting camera preview: " +
                        e.getMessage());
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ON = "off";
        if (flashoff.isChecked()) {
            checkOn = "ON";
        } else {
            checkOn = "OFF";
        }
        if (cam != null) {
            releaseCamera();
            cam = null;
        }
    }

    // Turning Off flash
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || p == null) {
                return;
            }
            p = cam.getParameters();
            flashoff.setBackgroundResource(R.drawable.flash_off);
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            cam.setParameters(p);
            cam.stopPreview();
            isFlashOn = false;


        }
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        Log.d("Function", "surfaceChanged iniciado");
        if (mSurfaceHolder.getSurface() == null) {
            // preview surface does not exist
            Log.d("Functionbfsfsaf", "surfaceChanged");
            return;
        }

        // stop preview before making changes
        try {
            cam.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            Log.d("ionbfsfsaf", "surfaceChanged");
//            cam.setPreviewDisplay(mSurfaceHolder);
            cam.setPreviewCallback(null);
            cam.startPreview();

        } catch (Exception e) {
            Log.d("dzzdv", "Error starting camera preview: " + e.getMessage());
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {

        try {
            cam.setPreviewDisplay(holder);
        } catch (IOException e) {
            releaseCamera();
            Log.e("fdvcdbCXBc", "Sigh", e);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

        if(cam!=null){
            releaseCamera();
            cam=null;
        }

    }

}

