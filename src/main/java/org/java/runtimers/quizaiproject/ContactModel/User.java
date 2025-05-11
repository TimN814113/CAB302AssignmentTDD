package org.java.runtimers.quizaiproject.ContactModel;

import java.util.*;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private final String specialCharacters = "@!#$%&'*+-/=?^_`{|}~.";
    private final Set<String> allowedEmailDomains = new HashSet<>(Arrays.asList(
            "mail.com", "mail-archive.com", "mail.org", "mail.cc",
            "gmail.com", "gmail-archive.com", "gmail.org", "gmail.cc",
            "email.com", "email-archive.com", "email.org", "email.cc"));

    public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if(!checkValidEmail(email)){
            throw new IllegalArgumentException("Invalid Email!");
        }
        this.email = Hash(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws IllegalArgumentException {
        if(!checkValidPassword(password)){
            throw new IllegalArgumentException("Invalid Password! Password must be at least " +
                    "8 characters long, with at least 1 lowercase, 1 uppercase, 1 special character and 1 number");
        }
        this.password = Hash(password);
    }

    public String getContactSummary() {
        return firstName + " " + lastName + " (" + email + ")";
    }

    public static String Hash (String string){
        String currHash = String.valueOf(29);
        for (int i = 0; i < 37; i++) {
            int hash = 13;
            String hashData = currHash + string;
            for (int j = 0; j < string.length(); j++) {
                hash = hash * 31 + hashData.charAt(j);
            }
            currHash = String.valueOf(hash);
        }
        return currHash;
    }

    public boolean checkValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false; // Password too short or null
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char character : password.toCharArray()) {
            if (Character.isUpperCase(character)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(character)) {
                hasLowerCase = true;
            } else if (Character.isDigit(character)) {
                hasDigit = true;
            } else if (specialCharacters.contains(String.valueOf(character))) {
                hasSpecialChar = true;
            }
        }
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    public boolean checkValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        for (int i = 0; i < email.length(); i++) {
            char currentChar = email.charAt(i);

            // Check for invalid prefixes
            if (specialCharacters.contains(String.valueOf(currentChar))) {
                if (i + 1 >= email.length() || !Character.isLetter(email.charAt(i + 1))) {
                    return false;
                }
            }

            // Check for valid suffixes
            if (currentChar == '@') {
                if (i == 0 || i == email.length() - 1) {
                    return false; // "@" at the beginning or end is invalid
                }
                String domain = email.substring(i + 1);
                return allowedEmailDomains.contains(domain); // Domain must be allowed, and no further checks needed
            }
        }

        // If the loop completes without finding "@", the email is likely invalid
        return false;
    }

}
