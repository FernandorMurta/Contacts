package contacts;

import java.time.LocalDate;

public class User extends Base{

    private String surname;
    private LocalDate birthday;
    private Gender gender;

    public User() {
    }

    public User(String surname,  LocalDate date, Gender gender) {
        this.surname = surname;
        this.birthday = date;
        this.gender = gender;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public void info(){
        System.out.println("Name: " + this.getName());
        System.out.println("Surname: " + this.getSurname());
        String birth = this.getBirthday() == null ? "[no data]" : this.getBirthday().toString();
        System.out.println("Birth date: " + birth);
        String gender = this.getGender() == null ? "[no data]" : this.getGender().toString();
        System.out.println("Gender: " + gender);
        System.out.println("Number: " + this.getPhone());
        System.out.println("Time created: " + this.getCreate());
        System.out.println("Time last edit: " + this.getEdit());
    }

    @Override
    public void printBase(int index) {
        System.out.println(index + ". " + super.getName() + " " + this.getSurname());
    }
}
