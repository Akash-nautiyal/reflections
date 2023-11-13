package org.example;

import org.example.data.Address;
import org.example.data.Company;
import org.example.data.Person;
import org.example.utils.JsonFormatUtils;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Address address = new Address("Main Street",(short)1);
        Company company = new Company("Udemy","Jabalpur",new Address("Garha",(short)600));
        Person person = new Person("John",true,29,100.555f,address,company) ;
        System.out.println(JsonFormatUtils.objectToJson(person,0));
    }
}