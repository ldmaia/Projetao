package br.com.helpdev.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import br.com.helpdev.Entity.Photo;
import br.com.helpdev.PhotoDAO;
import br.com.helpdev.R;

/**
 * Created by Lucas on 12/03/2016.
 */
public class FotoAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context contexto;
    private ArrayList<Photo> lista;


    public FotoAdapter(Context contexto, ArrayList<Photo> lista) {
        this.contexto = contexto;
        this.lista = lista;
        mInflater = LayoutInflater.from(contexto);
    }

    public int getCount() {
        return lista.size();
    }

    public Photo getItem(int position) {
        return lista.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        PhotoDAO dao = new PhotoDAO(contexto);
        Photo ft = lista.get(position);
        //Bitmap raw;
        //byte[] fotoArray;
        if (view == null) {
            view = mInflater.inflate(R.layout.line_lv, null);
        }

        String photoPath = dao.getReportPicturePath(lista.get(position));
        File imgFile = new File(photoPath);

        if (photoPath.length() == 0) {
                photoPath = "sem_foto";
        }

        if(imgFile.exists()) {
            Log.i("Debug", photoPath);
            ((TextView) view.findViewById(R.id.textView2)).setText("" + ft.getId());;

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView foto = (ImageView) view.findViewById(R.id.imageView);
            foto.setImageBitmap(myBitmap);

        }




       // ((TextView) view.findViewById(R.id.textView)).setText("" + ft.getNome());;



        //id.setText(ft.getId());
        //nome.setText(ft.getNome());
        //raw = ft.getImagem();

        //if (raw != null) {
         //   foto.setImageBitmap(raw);
        /*Bitmap bmp = intent.getExtras().get("data");
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();*/

            return view;

        }

    }

