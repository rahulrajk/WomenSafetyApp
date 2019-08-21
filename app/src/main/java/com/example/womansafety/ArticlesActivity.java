package com.example.womansafety;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class ArticlesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArticleAdapter articleAdapter;
    FloatingActionButton floatingActionButton;
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);


        try {
//            socket = IO.socket("http://192.168.43.155:3000/");
            socket = IO.socket("https://womansafety.herokuapp.com/");
            socket.connect();
            Log.d("connected","connected");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        floatingActionButton = findViewById(R.id.add);
        articleAdapter = new ArticleAdapter();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        articleAdapter.setList("What is Lorem Ipsum?","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,");
        recyclerView.setAdapter(articleAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ipBuilder = new AlertDialog.Builder(ArticlesActivity.this);
                View v = LayoutInflater.from(ArticlesActivity.this).inflate(R.layout.alert_box, null);
                ipBuilder.setView(v);
                final AlertDialog ipDialog = ipBuilder.create();
                ipDialog.setCancelable(false);
                ipDialog.show();
                final EditText title,content;
                Button post,cancel;
                title = v.findViewById(R.id.new_title);
                content = v.findViewById(R.id.new_desc);
                cancel = v.findViewById(R.id.cancel);
                post = v.findViewById(R.id.post);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ipDialog.dismiss();
                    }
                });
                post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String t,m;
                        t = title.getText().toString();
                        m = content.getText().toString();
                        if (t.isEmpty()&&m.isEmpty()){
                            Toast.makeText(ArticlesActivity.this, "Enter title and content", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            socket.emit("messagesent",t,m);
                            ipDialog.dismiss();
                        }
                    }
                });

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        socket.on("messageDetection", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jsonObject = (JSONObject) args[0];
                try {
                    String t = jsonObject.getString("t");
                    String m = jsonObject.getString("m");
                    articleAdapter.setList(t,m);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            articleAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
