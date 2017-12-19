package com.example.aidldemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Roshine
 * @date 2017/12/7 16:45
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class Person implements Parcelable{
    public Person() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Person(Parcel parcel) {
        name = parcel.readString();
        age = parcel.readInt();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;
    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel parcel) {
            return new Person(parcel);
        }

        @Override
        public Person[] newArray(int i) {
            return new Person[i];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    public void readFromParcel(Parcel parcel){
        name = parcel.readString();
        age = parcel.readInt();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
