package model;

/**
 * This class is the one that models the client objects. It is used mainly to take the data from and to put it into the
 * table of clients.
 * As attributes, we have an id, name, address, email and age of a client, information that would be important regarding
 * the client's identity.
 */

public class Client {
    private int id;
    private String name;
    private String adress;
    private String email;
    private int age;

    public Client() {

    }

    public Client(int id, String name, String adress, String email, int age) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String address) {
        this.adress = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + adress + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
