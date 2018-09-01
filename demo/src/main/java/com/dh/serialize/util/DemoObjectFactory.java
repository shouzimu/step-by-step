package com.dh.serialize.util;

import com.dh.serialize.model.AddressBook;
import com.dh.serialize.model.AddressBookProtos;

public class DemoObjectFactory {

    public static AddressBookProtos.AddressBook newProtoAddressBook() {
        AddressBookProtos.AddressBook addressBook =
            AddressBookProtos.AddressBook.newBuilder().addPeople(AddressBookProtos.Person.newBuilder().setId(1234).setName("John Doe").setEmail("jdoe@example.com").addPhones(AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("555-4321").setType(AddressBookProtos.Person.PhoneType.HOME))).build();

        return addressBook;
    }

    public static AddressBook newNormalAddressBook() {
        AddressBook addressBook = new AddressBook();
        AddressBook.Person person = new AddressBook.Person();
        person.setName("John Doe");
        person.setEmail("jdoe@example.com");
        person.setId(13958235);

        AddressBook.Phone phone = new AddressBook.Phone();
        phone.setNumber("555-4321");
        phone.setType(AddressBook.PhoneType.HOME);
        person.addPhone(phone);
        addressBook.addPerson(person);
        return addressBook;
    }

}
