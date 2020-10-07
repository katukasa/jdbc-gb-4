package db;

import com.mysql.cj.jdbc.MysqlDataSource;
import model.Cat;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sandbox {


    public static void main(String[] args) throws SQLException {
        String id = "1";
        DataSource dataSource = prepareDataSource();
        List<Cat> cats = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cat WHERE id=?")) {
                statement.setString(0, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Cat cat = new Cat();

                        cat.setId(resultSet.getLong("id"));
                        cat.setName(resultSet.getString("name"));
                        cat.setAge(resultSet.getLong("age"));

                        System.out.println(cat);
                        cats.add(cat);
                    }
                }
            }
        }
        System.out.println(cats);
    }

    private static DataSource prepareDataSource() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("yuri");
        dataSource.setPassword("pass");
        dataSource.setUrl("jdbc:mysql://localhost:3306/geekbrains");
        dataSource.setServerTimezone("UTC");
        return dataSource;
    }
}
