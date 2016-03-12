package br.com.helpdev.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.helpdev.Entity.Foto;
import br.com.helpdev.R;

/**
 * Created by Lucas on 12/03/2016.
 */
public class FotoAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context contexto;
    private ArrayList<Foto> lista;

    public FotoAdapter(Context contexto, ArrayList<Foto> lista) {
        this.contexto = contexto;
        this.lista = lista;
        mInflater = LayoutInflater.from(contexto);
         }

    public int getCount()
    {
        return lista.size();
    }

    public Foto getItem(int position)
    {
        return lista.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Foto ft;
        ImageView foto;
        TextView nome;
        TextView id;
        Bitmap raw;
        byte[] fotoArray;
        if (view == null) {
            view = mInflater.inflate(R.layout.line_lv, null);
        }

        id = (TextView) view.findViewById(R.id.textView2);
        nome = (TextView) view.findViewById(R.id.textView);
        foto = (ImageView) view.findViewById(R.id.imageView);

        ft = lista.get(position);

        id.setText(ft.getId());
        nome.setText(ft.getNome());
        raw = ft.getImagem();

        if(raw != null){
            foto.setImageBitmap(raw);
        }

        /*Bitmap bmp = intent.getExtras().get("data");
ByteArrayOutputStream stream = new ByteArrayOutputStream();
bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
byte[] byteArray = stream.toByteArray();*/

        return view;

    }







}
