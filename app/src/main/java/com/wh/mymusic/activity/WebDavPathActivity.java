package com.wh.mymusic.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wh.mymusic.R;
import com.wh.mymusic.adapter.WebDavPathListAdapter;
import com.wh.mymusic.viewmodel.WebDavPathListViewModel;

public class WebDavPathActivity extends AppCompatActivity {
    private String TAG = "WH_" + getClass().getSimpleName();
    String url = "https://nas.fuckpve.site:5006";
    private WebDavPathListViewModel webDavPathListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_dav_path);

        webDavPathListViewModel = ViewModelProviders.of(this).get(WebDavPathListViewModel.class);

        TextView tv_current_path = findViewById(R.id.tv_web_dav_path);

        RecyclerView rv_web_dav_path_list = findViewById(R.id.rv_web_dav_path);
        WebDavPathListAdapter adapter = new WebDavPathListAdapter(webDavPathListViewModel, url);
        rv_web_dav_path_list.setLayoutManager(new LinearLayoutManager(this));
        rv_web_dav_path_list.setAdapter(adapter);

        webDavPathListViewModel.getWebDavPathList().observe(this, adapter::submitList);

        webDavPathListViewModel.getCurrentFullPath().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_current_path.setText(s);
                url += s;
//                getPaths(url);
            }
        });

//        getPaths(url);
        webDavPathListViewModel.setCurrentFullPath(url);

    }

//    private void getPaths(String url) {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                private Sardine sardine;
//                if (sardine == null) {
//                    sardine = new OkHttpSardine();
//                    sardine.setCredentials("wolf", "1qaz2wsx");
//                }
//                try {
//                    List<DavResource> resources = sardine.list(url);
//                    List<String> paths = new ArrayList<>();
//                    for (DavResource d : resources) {
//                        paths.add(d.getPath());
//                    }
//                    webDavPathListViewModel.setWebDavPathList(paths);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }

}