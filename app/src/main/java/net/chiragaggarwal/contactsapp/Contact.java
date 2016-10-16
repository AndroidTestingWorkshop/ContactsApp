package net.chiragaggarwal.contactsapp;

import com.google.gson.annotations.SerializedName;

public class Contact {
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

    public Contact(String firstName, String lastName, String email, String phoneNumber, String profilePictureUrl, boolean isFavorite) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profilePictureUrl = profilePictureUrl;
        this.isFavorite = isFavorite;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Contact)) return false;
        if (this == obj) return true;
        Contact thatContact = (Contact) obj;
        return this.firstName.equals(thatContact.firstName) &&
                this.lastName.equals(thatContact.lastName) &&
                this.email.equals(thatContact.email) &&
                this.phoneNumber.equals(thatContact.phoneNumber) &&
                this.isFavorite == thatContact.isFavorite;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (profilePictureUrl != null ? profilePictureUrl.hashCode() : 0);
        result = 31 * result + (isFavorite ? 1 : 0);
        return result;
    }
}
