package com.cos.viewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cos.viewtest.adapter.PersonAdaptor;
import com.cos.viewtest.provider.PersonProvider;

// 어댑터와 리사이클러뷰 연결만 하면 됨!!
public class MainActivity extends AppCompatActivity {

    private RecyclerView rvPersons;
    private RecyclerView.LayoutManager layoutManager; // 리니어 레이아웃은 방향이 있음. 방향 설정을 위해
    private PersonAdaptor personAdaptor; // 얘는 내가 띄워야함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initAdaptor();
        initData(); // 여기서는 원래 통신을 해야함
    }


    // 어댑터 관련한 건 여기 다 모았엉
    private void initAdaptor(){
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvPersons.setLayoutManager(layoutManager);
        personAdaptor = new PersonAdaptor();
        rvPersons.setAdapter(personAdaptor); // 어댑터와 리사이클러뷰 연결
    }


    // 어댑터에다가 통신으로 받은 값을 넣어줘야함
    private void initData(){
        PersonProvider personProvider = new PersonProvider();
        personAdaptor.addItems(personProvider.findAll());
    }

    private void init(){
        rvPersons = findViewById(R.id.rvPersons);
    }


}