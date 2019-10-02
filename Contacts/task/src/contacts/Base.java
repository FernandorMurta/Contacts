package contacts;

import java.time.LocalDateTime;

public abstract class Base {

    private Boolean person;
    private String name;
    private String phone;
    private LocalDateTime create;
    private LocalDateTime edit;


    public Base() {
    }

    public Base(Boolean person, String phone, String name, LocalDateTime create, LocalDateTime edit) {
        this.name = name;
        this.phone = phone;
        this.person = person;
        this.create = create;
        this.edit = edit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isPerson() {
        return person;
    }

    public void setPerson(Boolean person) {
        this.person = person;
    }

    public Boolean getPerson() {
        return person;
    }

    public LocalDateTime getCreate() {
        return create;
    }

    public void setCreate(LocalDateTime create) {
        this.create = create;
    }

    public LocalDateTime getEdit() {
        return edit;
    }

    public void setEdit(LocalDateTime edit) {
        this.edit = edit;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public abstract void printBase(int index);

    public abstract void info();
}
