package com.example.jtomas.exameniot2021;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

//5. RECICLERVIEW
public class Adaptador extends
        FirestoreRecyclerAdapter<Lectura, Adaptador.ViewHolder> {

   private Context context;

   public Adaptador(Context context,
                    @NonNull FirestoreRecyclerOptions<Lectura> options) {
      super(options);
      this.context = context.getApplicationContext();
   }

   public static class ViewHolder extends RecyclerView.ViewHolder {
      public final ImageView imagen;
      public final TextView titulo;
      public final TextView tiempo;
      public final TextView valor;
      public ViewHolder(View itemView) {
         super(itemView);
         this.imagen = (ImageView) itemView.findViewById(R.id.imageView1);
         this.titulo = (TextView) itemView.findViewById(R.id.textView1);
         this.tiempo = (TextView) itemView.findViewById(R.id.textView2);
         this.valor = (TextView) itemView.findViewById(R.id.textView3);
      }
   }

   @Override public ViewHolder onCreateViewHolder(
           ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.elemento_lista, parent, false);
      return new ViewHolder(view);
   }

   @Override protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Lectura lectura) {
      holder.titulo.setText(lectura.getSensor());
      holder.tiempo.setText(""+lectura.getTiempo());
      holder.valor.setText(""+lectura.getValor());
      Glide.with(context)
              .load(lectura.getUrlFoto())
              .placeholder(R.drawable.ic_launcher_foreground)
              .into(holder.imagen);
   }

}
