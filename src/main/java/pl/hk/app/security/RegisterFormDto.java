package pl.hk.app.security;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterFormDto {

    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 3, message = "Pole musi mieć co najmniej 3 znaki")
    private String firstName;
    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 3, message = "Pole musi mieć co najmniej 3 znaki")
    private String lastName;
    @NotBlank(message = "Pole nie może być puste")
    private String address;
    @NotBlank(message = "Pole nie może być puste")
    private String username;
    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 8, message = "Hasło musi mieć co najmniej 8 znaków")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
