package net.chiragaggarwal.contactsapp;

import com.google.gson.annotations.SerializedName;

public class Contact {
    public static final String ID = "net.chiragaggarwal.contactsapp.Contact";

    @SerializedName("id")
    public int id;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("email")
    public String email;
    @SerializedName("phone_number")
    public String phoneNumber;
    @SerializedName("profile_pic")
    public String profilePictureUrl;
    @SerializedName("favorite")
    public boolean isFavorite;

    public Contact(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != contact.id) return false;
        if (isFavorite != contact.isFavorite) return false;
        if (firstName != null ? !firstName.equals(contact.firstName) : contact.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null)
            return false;
        if (email != null ? !email.equals(contact.email) : contact.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(contact.phoneNumber) : contact.phoneNumber != null)
            return false;
        return profilePictureUrl != null ? profilePictureUrl.equals(contact.profilePictureUrl) : contact.profilePictureUrl == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (profilePictureUrl != null ? profilePictureUrl.hashCode() : 0);
        result = 31 * result + (isFavorite ? 1 : 0);
        return result;
    }
}
