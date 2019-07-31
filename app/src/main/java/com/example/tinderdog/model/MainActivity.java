package com.example.tinderdog.model;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tinderdog.R;
import com.example.tinderdog.api.DogService;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btnDog;
    private TextView result;
    private Retrofit retrofit;
    Dog dog;
    private ImageView catioro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDog = findViewById(R.id.btnDog);
        catioro = findViewById(R.id.catioro);
        this.dog = new Dog();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/breed/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recuperarDogRetrofit();

            }
        });

    }

    private void recuperarDogRetrofit() {

        DogService dogService = retrofit.create(DogService.class);
        Call<Dog> call = dogService.recuperarDog();

        call.enqueue(new Callback<Dog>() {
            @Override
            //resposta
            public void onResponse(Call<Dog> call, Response<Dog> response) {


                if (response.isSuccessful()) {
                    Dog dog = response.body();
                    Picasso.with(MainActivity.this)
                            .load(dog.getMessage())
                            .into(catioro);

                    catioro.setScaleType(ImageView.ScaleType.FIT_XY);
                }

            }

            @Override
            //caso aconteça algum erro
            public void onFailure(Call<Dog> call, Throwable t) {

            }
        });


    }

    //codigo apenas para consulta quando necessário
    class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // faz requisição e carrega os dados
        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                //recupera os dados em bytes
                inputStream = conexao.getInputStream();

                //le os dados em bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //leitura dos caracteres
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";

                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            String status = null;
            String message = null;

            try {
                JSONObject jsonObject = new JSONObject(resultado);
                status = jsonObject.getString("status");
                message = jsonObject.getString("mesage");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            //result.setText(resultado);
            result.setText(status);


        }
    }
}
