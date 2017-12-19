// ITestAidlInterface.aidl
package com.example.aidldemo;

// Declare any non-default types here with import statements
import com.example.aidldemo.Person;
interface ITestAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//      int testAdd(int a,int b);
    String inPerson(in Person p);
    String outPerson(out Person p);
    String inOutPerson(inout Person p);
}
