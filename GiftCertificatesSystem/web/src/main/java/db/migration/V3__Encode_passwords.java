package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class V3__Encode_passwords extends BaseJavaMigration {
    private static final String SELECT_USER_PASSWORDS = "SELECT id, password FROM user_account";
    private static final String UPDATE_PASSWORD = "UPDATE user_account SET password=? WHERE id=?";
    private static final String USER_ID_COLUMN = "id";
    private static final String USER_PASSWORD_COLUMN = "password";

    @Override
    public void migrate(Context context) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
        try (Statement select = context.getConnection().createStatement()) {
            try (ResultSet rows = select.executeQuery(SELECT_USER_PASSWORDS)) {
                while (rows.next()) {
                    byte[] password = rows.getBytes(USER_PASSWORD_COLUMN);
                    char[] chars = new char[password.length];
                    for (int i = 0; i < password.length; i++) {
                        chars[i] = (char) password[i];
                    }
                    String encodedPassword = passwordEncoder.encode(CharBuffer.wrap(chars));

                    try (PreparedStatement statement = context.getConnection().prepareStatement(UPDATE_PASSWORD)) {
                        statement.setString(1, encodedPassword);
                        statement.setLong(2, rows.getLong(USER_ID_COLUMN));
                        statement.executeUpdate();
                    }
                }
            }
        }
    }
}