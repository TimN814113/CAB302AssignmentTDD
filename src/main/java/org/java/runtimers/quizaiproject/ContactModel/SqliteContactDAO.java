package org.java.runtimers.quizaiproject.ContactModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteContactDAO implements IContactDAO{

    private Connection connection;

    public SqliteContactDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS contacts ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "firstName VARCHAR NOT NULL,"
                    + "lastName VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isUserExist(String firstName, String lastName, String email){
        try {
            String query = "SELECT * FROM contacts WHERE (firstName = ? AND lastName = ?) OR email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();  // Returns true if user exists, false otherwise
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        try {
            String query = "SELECT COUNT(*) FROM contacts WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.getInt(1) > 0; //retrieves the count of matching rows
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override public int getUserId(String email, String password) {
        String query = "SELECT id FROM contacts WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id"); // Return the user's ID if a match is found
            } else {
                return 0; // No matching user found
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user ID", e);
        }
    }

    @Override
    public void addContact(User contact) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO contacts (firstName, lastName, email, password) VALUES (?, ?, ?, ?)");
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getPassword());
            statement.executeUpdate();
            // Set the id of the new contact
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                contact.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateContact(User contact) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE contacts SET firstName = ?, lastName = ?, email = ?, password = ? WHERE id = ?");
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getPassword());
            statement.setInt(5, contact.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteContact(User contact) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM contacts WHERE id = ?");
            statement.setInt(1, contact.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getContact(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM contacts WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User contact = new User(firstName, lastName, email, password);
                contact.setId(id);
                return contact;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllContacts() {
        List<User> contacts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM contacts";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User contact = new User(firstName, lastName, email, password);
                contact.setId(id);
                contacts.add(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public String Hash (String string){
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
}
