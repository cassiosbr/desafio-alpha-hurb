package com.example.desafioalphahurb.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.desafioalphahurb.R;
import com.example.desafioalphahurb.activitites.DetalheHotelActivity;
import com.example.desafioalphahurb.function.CompararStars;
import com.example.desafioalphahurb.layout.BuscarHotelAdapter;
import com.example.desafioalphahurb.model.Hotel;
import com.example.desafioalphahurb.task.HotelAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BuscarFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static ArrayList<Hotel> hotelNameArrayList = new ArrayList<Hotel>();

    //LIST COM INFORMAÇÕES DOS HOTEIS, SERA ORDENADO NA ORDEM DESCRESCENTE
    public List<Hotel> listHotelsDecrecente = new ArrayList<>();

    //LIST COM INFORMAÇÕES DOS HOTEIS, CRIADO PORQUE É NECESSÁRIO TER 2 LISTAS, UMA PARA LISTAR OS HOTEIS NA ORDEM
    //DECRESCENTE E OUTRA PARA PEGAR OS INDICES DESSES HOTEIS
    List<Hotel> Hotels = new ArrayList<>();
    private List<Hotel> listConteudoHoteis;

    private ListView listView;
    private BuscarHotelAdapter adapter;
    private SearchView editsearch;
    TextView mensagem;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_buscar, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        listView = (ListView) rootView.findViewById(R.id.listViewBuscar);

        mensagem = (TextView) rootView.findViewById(R.id.mensagem);

        carregarJsonBuscarHotel();

        listConteudoHoteis = new ArrayList<>();

        carregarHoteis();

        //EXECUTA O MÉTODO 'CompararStars', QUE ORDENA AS ESTRELAS DOS HOTEIS
        Collections.sort(listHotelsDecrecente, new CompararStars());
        Collections.sort(listConteudoHoteis, new CompararStars());

        hotelNameArrayList = new ArrayList<>();

        for (int i = 0; i < listHotelsDecrecente.size(); i++) {

            Hotel hoteis = new Hotel(
                    listHotelsDecrecente.get(i).getName() + " _limitador; " +
                            listHotelsDecrecente.get(i).getCity() + ", " + listHotelsDecrecente.get(i).getState().replace("Rio de Janeiro","RJ") + "_limitador;" +
                            listHotelsDecrecente.get(i).getPrice() + "_limitador;" +
                            listHotelsDecrecente.get(i).getStars() + "_limitador;" +
                            listHotelsDecrecente.get(i).getImage() + "_limitador;");

            // Binds all strings into an array
            hotelNameArrayList.add(hoteis);
        }

        Log.d("Json Buscar", listHotelsDecrecente.toString());

        // PASSA OS RESULTADOS DPS DADOS PARA A CLASSE 'BuscarHotelAdapter'
        adapter = new BuscarHotelAdapter(getContext());

        // VINCULA O ADAPDADOR AO 'ListView'
        listView.setAdapter(adapter);

        // LOCALIZA O 'EditText' no 'ListView'
        editsearch = (SearchView) rootView.findViewById(R.id.buscar_hotel);
        editsearch.setOnQueryTextListener(this);

        //LISTA AS INFORMAÇÕES DOS HOTEIS NO 'ListView', E PREPARA A NAVEGAÇÃO DE TELAS APÓS CLICAR EM UM ITEM
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), DetalheHotelActivity.class);
                Bundle params = new Bundle();
                params.putInt("index", listConteudoHoteis.get(position).getIndex());
                intent.putExtras(params);
                startActivity(intent);
            }
        });

        new verificarBusca().execute();

        return rootView;
    }

    private class verificarBusca extends AsyncTask<Void, Void, Void> {

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {

            try {

                if (listHotelsDecrecente.size() == 0) {

                    Log.e("MENSAGEM", "Registro não encontrado 1");

                   getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            editsearch.setVisibility(View.INVISIBLE);

                            mensagem.setText("Não foram encontrados registros.");
                        }
                    });

                } else {

                    Log.e("MENSAGEM", "Conteúdo disponível");
                    //mensagem.setVisibility(TextView.INVISIBLE);
                    //mensagem.setText("Não foram encontrados registros.");
                }
            } catch (final Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //TextView mensagem = (TextView) findViewById(R.id.mensagem);
                        mensagem.setText("Programação indisponível.");
                        Log.e("catch", e.getMessage());
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //OCULTA A PROGRESSBAR APÓS A PROGRAMAÇÃO SER CARREGADA
            //if (progressBarProgramacao.isShown())
            //progressBarProgramacao.setVisibility(View.INVISIBLE);
        }
    }

    //MÉTODO CRIADO PARA LISTAR OS INDICES DOS HOTEIS
    private void carregarHoteis(){

        Hotel hotel;

        for(int i=0; i <= Hotels.size() - 1; i++) {
            hotel = new Hotel(
                    i,
                    Hotels.get(i).getStars());

            listConteudoHoteis.add(hotel);
        }
    }

    //MÉTODO QUE CARRAGA O JSON
    public void carregarJsonBuscarHotel() {
        try {
            Hotels = new HotelAsyncTask().execute().get();
            listHotelsDecrecente = new HotelAsyncTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //MÉTODO CRIADO PARA IMPLEMENTAR A BUSCA
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    //MÉTODO CRIADO PARA IMPLEMENTAR A BUSCA
    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}
