package br.com.helpdev.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import br.com.helpdev.Controller.CameraController;
import br.com.helpdev.DataBase;
import br.com.helpdev.Entity.Foto;
import br.com.helpdev.R;
import br.com.helpdev.Adapter.FotoAdapter;

@SuppressWarnings("ALL")
public class MainCamera extends Activity implements View.OnClickListener, ShutterCallback, Camera.PictureCallback {

    private CameraController cameraController;
    private boolean emCamera;
    private Button botaoCamera;
    private ImageView ImageView;
    private ArrayList<Foto> fotos;
    private ListView listView;
    private FotoAdapter adapterListView;

    DataBase.FotoDAO db = new DataBase.FotoDAO(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        emCamera = true;


        listView = (ListView) findViewById(R.id.listView);
        cameraController = new CameraController(this, R.foto.area_view);

        botaoCamera = (Button) findViewById(R.foto.bt_fotografar);
        botaoCamera.setOnClickListener(this);
        //createListView();

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
        Bitmap fttirada = null;
        fttirada = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Foto foto = new Foto("teste",fttirada);
        //byte[] bb = getBytes(foto);
        db.inserirImagem(foto);
        //ImageView.setImageBitmap(foto);
        //ImageView.setScaleType(android.widget.ImageView.ScaleType.CENTER_INSIDE);
        Toast.makeText(getApplicationContext(), "sucesso", Toast.LENGTH_SHORT).show();

        cameraController.pararVisualizacao();
        //createListView();
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

    public void createListView(){
        fotos = db.carregarFotos();
        adapterListView = new FotoAdapter(this, fotos);
        listView.setAdapter(adapterListView);
        listView.setCacheColorHint(Color.TRANSPARENT);

    }

}
