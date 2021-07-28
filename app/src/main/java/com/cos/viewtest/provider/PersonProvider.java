package com.cos.viewtest.provider;

import com.cos.viewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

// 레파지토리 같은 애라고 생각해
public class PersonProvider {

    public List<Person> findAll(){
        List<Person> persons = new ArrayList<>();
        for (int i=1; i<21; i++){
            persons.add(new Person("이름"+i, "0102222"));
        }

        return persons;
    }
}
