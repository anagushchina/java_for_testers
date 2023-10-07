package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase{

    @Test
    public void removeContactTest(){
        if(!appMan.initContactHelper().isContactPresent()){
            appMan.initContactHelper().createContact(new ContactData());
        }
        appMan.initContactHelper().deleteContact();
    }

    @Test
    public void removeAllContactsAtOnceTest(){
        if(!appMan.initContactHelper().isContactPresent()){
            appMan.initContactHelper().createContact(new ContactData());
        }
        appMan.initContactHelper().deleteAllContacts();
        Assertions.assertEquals(0, appMan.initContactHelper().getCount());
    }
}
