package com.example.crudapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados; //Cria Banco de Dados
    public ListView listViewDados; //Cria Tela de  layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewDados = (ListView) findViewById(R.id.listViewDados);

        criarBancoDados();    // Criar método Banco de Dados
        inserirDadosTemp();  // Criar método Inserir dados
        Listardados();      // Criar método listar dados
    }
    public void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS coisa (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", nome VARCHAR)");
            bancoDados.close();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
    public void Listardados(){
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id,nome FROM coisa", null);
            ArrayList<String> linhas = new ArrayList <String>();
            ArrayAdapter meuAdapter = new ArrayAdapter <String>(

                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
);
            listViewDados.setAdapter(meuAdapter);
            meuCursor.moveToFirst();
            while(meuCursor!=null){
                linhas.add(meuCursor.getString(1));
                meuCursor.moveToNext();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void inserirDadosTemp() {
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            String sql = "INSERT INTO coisa (nome) VALUES (?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1, "coisa 1");
            stmt.executeInsert();
            stmt.bindString(1, "coisa ABC");
            stmt.executeInsert();
            stmt.bindString(1, "coisa Terceira");
            stmt.executeInsert();
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}