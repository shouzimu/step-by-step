package com.dh.serialize.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddressBook implements Serializable{

    private List<Person> persons;

    public AddressBook() {
        persons = new ArrayList<>();
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public List<Person> getPersons() {
        return persons;
    }

    public enum PhoneType {
        MOBILE,
        HOME,
        WORK
    }


    public static final class Phone implements Serializable {
        private String number;
        private PhoneType type;

        public Phone() {

        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getNumber() {
            return number;
        }

        public void setType(PhoneType phoneType) {
            this.type = phoneType;
        }

        public PhoneType getType() {
            return type;
        }
    }


    public static final class Person implements Serializable{
        private String name;
        private int id;
        private String email;

        private List<Phone> phones;

        public Person() {
            phones = new ArrayList<>();
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void addPhone(Phone phone) {
            phones.add(phone);
        }

        public List<Phone> getPhones() {
            return phones;
        }
    }


}
