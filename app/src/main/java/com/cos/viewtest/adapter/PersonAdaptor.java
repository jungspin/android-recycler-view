package com.cos.viewtest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.viewtest.MainActivity;
import com.cos.viewtest.R;
import com.cos.viewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

// 2. 어댑터 만들기
public class PersonAdaptor extends RecyclerView.Adapter<PersonAdaptor.MyViewHolder> {

    private static final String TAG = "PersonAdaptor";

    private MainActivity mContext;
    private PersonAdaptor personAdaptor = this;

    public PersonAdaptor(MainActivity mContext){
        this.mContext = mContext; // 어댑터에서 화면에 접근할 수 있다
    }

    // 3. 컬렉션
    private List<Person> persons = new ArrayList<>();
    private int createCount = 1;
    private int bindingCount = 1;

    // 4. 컬렉션 데이터 세팅 -> 삭제와 추가를 위한 것도 만들어야함 (필요하면)
    public void addItems(List<Person> persons){
        this.persons = persons;
        notifyDataSetChanged();
        // 화면에 그림이 다 그려진 후 데이터가 변경되면 이 함수 없음 화면 다시 안그려짐
        // 최초에는 필요없음
    }

    public void addItem(Person person){
        this.persons.add(person);
        notifyDataSetChanged();
        mContext.mRvScroll();
    }

    public List<Person> getItems(){
        return persons;
    }

    public void removeItem(int index){
        persons.remove(index);
        notifyDataSetChanged(); // 화면 동기화
    }

    public void updateItem(int index, Person person){
        // 인덱스 번호 받아서, 해당하는 데이터에 강제로 데이터 덮어씌우기
        Person p = persons.get(index);
        Log.d(TAG, "updateItem: " + p);
        p.setName(person.getName());
        p.setTel(person.getTel());
        notifyDataSetChanged();
        //mContext.mRvScroll();

    }

    // ViewHolder 객체 만드는 친구 -> 앱이 최초로 구동할 때 그 이후에는 데이터만 갈아끼울꺼니까
    // MyViewHolder 여기서 실행
    // 콜백 메소드 -> 스택을 제공함
    // parent -> 리사이클러뷰가 들어옴 (지가 알아서 들어옴) : person_item 의 부모
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: 그림그려짐 "+ createCount);
        createCount++;
        LayoutInflater layoutInflater = // xml 을 메모리에 띄워주는 클래스
                (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 리사이클러 뷰의 모든 것이 여기 담김
        // 정확하게 리니어 레이아웃임
        View view =  layoutInflater.inflate(R.layout.person_item, parent, false);
        return new MyViewHolder(view);
    }

    // 뷰홀더 데이터 갈아 끼우는 친구 -> 스크롤할때는 이것만 동작하겠지
    // holder : 우리가 만든 홀더
    // setItem이 여기서 실행되어야한다. setItem 함수로 만든이유 : 쓰기 쉬우려고
    @Override
    public void onBindViewHolder(PersonAdaptor.MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: 데이터 끼워짐 "+ bindingCount);
        bindingCount++;
        Person person = persons.get(position);
        holder.setItem(person); // 그림에 데이터 끼워줌
    }

    // 데이터가 몇개인지 알아야하나? 응 사이즈를 알아야함. 내가 알려줘야해
    // 화면의 크기는 얘가 알고 있음. 사이즈 지가 찾아냄
    // 어댑터가 알아서 호출해서 사이즈 10이네?
    // 화면 크기가 600이네?
    // 아이템 크기가 200이네?
    @Override
    public int getItemCount() {
        return persons.size();
    }

    // 1. 뷰홀더를 만든다 -> 내부 클래스로 만듬 : 데이터 갈아끼우는 친구!!
    public class MyViewHolder extends RecyclerView.ViewHolder {

        // 전역변수
        private TextView tvName, tvTel;
        //private PersonAdaptor personAdaptor;

        // 앱 구동시에 발동
        // 뷰 들고와서 데이터 갈아 끼우는 애
        // person_item을 메모리에 띄워서 view에 넣어줘야함
        public MyViewHolder(View itemView) { // view : 리니어 레이아웃(person_item)
            super(itemView);
            // 메모리에 떴을거라고 가정하고 연결해놓는 것임
            tvName = itemView.findViewById(R.id.tvName);
            tvTel = itemView.findViewById(R.id.tvTel);

            initListener();
        }

        private void initListener(){
            itemView.setOnClickListener(v -> {
                Log.d(TAG, "onCreateViewHolder: " + getAdapterPosition());
                int index = getAdapterPosition();
                // 다른 클래스라서 persons 못찾아 -> 얘한테 접근할 수 있는 함수 만들면 됨
                Log.d(TAG, "initListener: " + personAdaptor.getItems().get(index).getName());
		       /* TextView t = v.findViewById(R.id.tvName);
		        Log.d(TAG, "initListener: "+ t.getText());*/
                personAdaptor.removeItem(index);

            });
            itemView.setOnLongClickListener(v -> {
                Log.d(TAG, "initListener: " + getAdapterPosition());
                int index = getAdapterPosition();
                updateItem(index, new Person("jungspin", "0103333"));
                return true;
            });
        }

        // 앱 구동시 + 스크롤할 때 발동
        public void setItem(Person person){
            tvName.setText(person.getName());
            tvTel.setText(person.getTel());
        }
    }
}
