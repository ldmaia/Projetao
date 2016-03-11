package br.com.helpdev;

import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import br.com.helpdev.hardware.camera.CameraController;
import br.com.helpdev.hardware.camera.DAOdb;

@SuppressWarnings("ALL")
public class MainCamera extends Activity implements View.OnClickListener, ShutterCallback, Camera.PictureCallback {

    private CameraController cameraController;
    private boolean emCamera;
    private Button botaoCamera;
    private ImageView ImageView;
    private DataBase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        emCamera = true;
        cameraController = new CameraController(this, R.foto.area_view);

        botaoCamera = (Button) findViewById(R.foto.bt_fotografar);
        //ImageView =(ImageView) findViewById(R.id.imageView);
        botaoCamera.setOnClickListener(this);
        db = new DataBase(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.foto.bt_fotografar:
                if (emCamera) {
                    cameraController.tirarFoto(this, null, this);

                } else {
                    emCamera = true;
                    botaoCamera.setText(R.string.foto_bt_fotografar);
                    cameraController.iniciarVisualizacao();
                }
                break;
        }
    }

    /**
     * Ação do click tirar foto
     */
    public void onShutter() {
        botaoCamera.setText(R.string.foto_bt_camera);
        emCamera = false;
    }

    /**
     * Retorno da ação de tirar foto
     *
     * @param bytes
     * @param camera
     */
    public void onPictureTaken(byte[] bytes, Camera camera) {

        Bitmap foto = null;
        foto = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        //byte[] bb = getBytes(foto);
        db.addImage(foto);
        //ImageView.setImageBitmap(foto);
        //ImageView.setScaleType(android.widget.ImageView.ScaleType.CENTER_INSIDE);
        Toast.makeText(getApplicationContext(), "sucesso", Toast.LENGTH_SHORT).show();

        cameraController.pararVisualizacao();
    }


        // convert from bitmap to byte array
        public static byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }

        // convert from byte array to bitmap
        public static Bitmap getImage(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }

}
