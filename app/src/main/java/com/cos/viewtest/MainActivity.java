package com.cos.viewtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.cos.viewtest.adapter.PersonAdaptor;
import com.cos.viewtest.model.Person;
import com.cos.viewtest.provider.PersonProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// 어댑터와 리사이클러뷰 연결만 하면 됨!!
public class MainActivity extends AppCompatActivity {

    private MainActivity mContext = this; // 내가 만든 변수(컨텍스트)

    private RecyclerView rvPersons;
    private RecyclerView.LayoutManager layoutManager; // 리니어 레이아웃은 방향이 있음. 방향 설정을 위해
    private PersonAdaptor personAdaptor; // 얘는 내가 띄워야함. xml 아니라 클래스니까

    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 인터페이스로 만들어서 써 담에
        init();
        initAdaptor();
        initData(); // 여기서는 원래 통신을 해야함
        initListener(); // 제일 마지막에 와야함
    }

    // 어댑터 관련한 건 여기 다 모았엉
    private void initAdaptor(){
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvPersons.setLayoutManager(layoutManager);
        personAdaptor = new PersonAdaptor(mContext);
        rvPersons.setAdapter(personAdaptor); // 어댑터와 리사이클러뷰 연결
    }

    // 어댑터에다가 통신으로 받은 값을 넣어줘야함
    private void initData(){
        PersonProvider personProvider = new PersonProvider();
        personAdaptor.addItems(personProvider.findAll());
    }

    private void initListener(){
        fabAdd.setOnClickListener((View v) -> {
            personAdaptor.addItem(new Person("이름new", "0102222"));

        });

        // 스와이프로 삭제하기
        // ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT : 방향 잡아주는거
        // 리사이클러 뷰의 각 ViewHolder에 리스너를 달 필요 없다.
        // 왜? 리사이클러 뷰가 있는 액티비티에서 이벤트를 콜백 받을 수 있기 때문
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int index = viewHolder.getAdapterPosition();
                personAdaptor.removeItem(index);
            }
        }).attachToRecyclerView(rvPersons);

    }

    // 메모리에 띄울 것과 관련한 것 여기
    private void init(){

        rvPersons = findViewById(R.id.rvPersons);
        fabAdd = findViewById(R.id.fabAdd);
    }

    public void mRvScroll(){
        // scrollToPosition : 스크롤 위치를 포지션으로 가게 하는..?
        rvPersons.scrollToPosition(personAdaptor.getItemCount()-1); // 카운트가 0부터 시작하기 때문에 -1 해줘야함
    }
}