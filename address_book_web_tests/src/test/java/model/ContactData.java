package model;

public record ContactData(String id, String firstName, String lastName, String address, String homePhone, String mobilePhone,
                          String workPhone, String email, String email2, String email3) {
    public ContactData(){
        this("","","","","","","","","","");
    }

    public ContactData withMinSetOfData(String firstName, String lastName, String address, String mobilePhone, String email){
        return new ContactData(this. id, firstName, lastName, address, this.homePhone, mobilePhone, this.workPhone, email, this.email2, this.email3);
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.firstName, this.lastName, this.address, this.homePhone, this.mobilePhone, this.workPhone, this.email, this.email2, this.email3);
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(this. id, firstName, this.lastName, this.address, this.homePhone, this.mobilePhone, this.workPhone, this.email, this.email2, this.email3);
    }

    public ContactData withLastName(String lastName) {
        return new ContactData(this. id, this.firstName, lastName, this.address, this.homePhone, this.mobilePhone, this.workPhone, this.email, this.email2, this.email3);
    }

    public ContactData withName(String firstName, String lastName) {
        return new ContactData(this. id, firstName, lastName, this.address, this.homePhone, this.mobilePhone, this.workPhone, this.email, this.email2, this.email3);
    }

    public ContactData withAddress(String address) {
        return new ContactData(this. id, this.firstName, this.lastName, address, this.homePhone, this.mobilePhone, this.workPhone, this.email, this.email2, this.email3);
    }

    public ContactData withEmail(String email) {
        return new ContactData(this. id, this.firstName, this.lastName, this.address, this.homePhone, this.mobilePhone, this.workPhone, email, this.email2, this.email3);
    }

    public ContactData withMobilePhone(String mobilePhone) {
        return new ContactData(this. id, this.firstName, this.lastName, this.address, this.homePhone, mobilePhone, this.workPhone, this.email, this.email2, this.email3);
    }
}
