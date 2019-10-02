package contacts;

public class Organization extends Base{

    private String address;

    public Organization() {
    }

    public Organization( String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public void info() {
        System.out.println("Organization name: " + this.getName());
        System.out.println("Address: " + this.getAddress());
        System.out.println("Number: " + this.getPhone());
        System.out.println("Time created: " + this.getCreate());
        System.out.println("Time last edit:" + this.getEdit());
    }

    @Override
    public void printBase(int index) {
        System.out.println(index + ". " + super.getName());
    }
}
