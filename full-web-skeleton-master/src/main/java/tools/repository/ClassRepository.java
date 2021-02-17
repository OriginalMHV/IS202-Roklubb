package tools.repository;

import models.ClassResultModel;
import models.TestBatchModel;
import models.TestModel;
import models.UserInfoModel;
import tools.DbTool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClassRepository {

    /**
     * legger til bruker til databasen.
     * Denne er ikke implementert. Her må dere gjerne prøve å lage en egen servlet som kan kommunisere med
     * denne metoden.
     *
     * @param user bruker objekt som inneholder all informasjon om personen.
     *             Tips: Objektet må instansieres i en servlet før man kaller på addUser().
     */

    private static final Logger logger = Logger.getLogger(ClassRepository.class.getName());
    private static final String errorMessage = "SQLException error";

    public static void addUser(UserInfoModel user) {
        Connection db = null;
        PreparedStatement insertNewUser = null;
        try {
            db = DbTool.getINSTANCE().dbLoggIn();
            db.setCatalog("roklubb");
            String query = "INSERT INTO roklubb.user (id,email, userType_name, class_name, club_name,password, fname, lname, dob, bio) " +
                    "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
            insertNewUser = db.prepareStatement(query);
            insertNewUser.setString(1, user.getID());
            insertNewUser.setString(2, user.getEmail());
            insertNewUser.setString(3, user.getUserType());
            insertNewUser.setString(4, user.getClassName());
            insertNewUser.setString(5, user.getClub());
            insertNewUser.setString(6, user.getPassword());
            insertNewUser.setString(7, user.getFirstName());
            insertNewUser.setString(8, user.getLastName());
            insertNewUser.setString(9, user.getDob());
            insertNewUser.setString(10, user.getBio());

            insertNewUser.execute();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, errorMessage, e);
        } finally {
            try {
                db.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, errorMessage, e);
            }
            try {
                insertNewUser.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, errorMessage, e);
            }
        }
    }

    public static void addTestSenior(TestModel test) {
        Connection db = null;
        PreparedStatement insertNewUser = null;
        try {
            db = DbTool.getINSTANCE().dbLoggIn();
            db.setCatalog("roklubb");
            String query = "INSERT INTO roklubb.testResult (user_id, testBatch_id, `rank`, score, class_name_static, 5kmT, 5kmW, 2kmT, 2kmW, 60sW, percentLieRow, kgLieRow, percentSquat, kgSquat) " +
                    "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)";
            insertNewUser = db.prepareStatement(query);
            insertNewUser.setString(1, test.getNameId());
            insertNewUser.setString(2, test.getTestBatchId());
            insertNewUser.setString(3, test.getRank());
            insertNewUser.setString(4, test.getScore());
            insertNewUser.setString(5, test.getClassNameStatic());
            insertNewUser.setString(6, test.getKmt5());
            insertNewUser.setString(7, test.getKmW5());
            insertNewUser.setString(8, test.getKmT2());
            insertNewUser.setString(9, test.getKmW2());
            insertNewUser.setString(10, test.getW60s());
            insertNewUser.setString(11, test.getPercentLieRow());
            insertNewUser.setString(12, test.getKgLieRow());
            insertNewUser.setString(13, test.getPercentSquat());
            insertNewUser.setString(14, test.getKgSquat());

            insertNewUser.execute();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, errorMessage, e);
        } finally {
            try {
                db.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, errorMessage, e);
            }
            try {
                insertNewUser.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, errorMessage, e);
            }
        }
    }

    public static void addClub(UserInfoModel user) {
        Connection db = null;
        PreparedStatement insertNewUser = null;
        try {
            db = DbTool.getINSTANCE().dbLoggIn();
            db.setCatalog("roklubb");
            String query = "INSERT INTO roklubb.club (name) VALUES (?)";
            insertNewUser = db.prepareStatement(query);
            insertNewUser.setString(1, user.getClub());
            insertNewUser.execute();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, errorMessage, e);
        } finally {
            try {
                db.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, errorMessage, e);
            }
            try {
                insertNewUser.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, errorMessage, e);
            }
        }
    }

    /**
     * inserter en ny testbatch.
     *
     * @param test: et test objekt som inneholder info om testen.(datoen)
     */
    public static void addTestBatch(TestBatchModel test) {
        Connection db = null;
        PreparedStatement insertNewUser = null;
        try {
            db = DbTool.getINSTANCE().dbLoggIn();
            db.setCatalog("roklubb");
            String query = "INSERT INTO roklubb.testBatch (startDate) VALUES (?)";
            insertNewUser = db.prepareStatement(query);
            insertNewUser.setString(1, test.getTestdate());
            insertNewUser.execute();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, errorMessage, e);
        } finally {
            try {
                db.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, errorMessage, e);
            }
            try {
                insertNewUser.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, errorMessage, e);
            }
        }
    }

    /*
    Henter ut alle testbatchene
     */
    public static List<TestBatchModel> getTestBatch() {
        Connection db = null;
        PreparedStatement prepareStatement = null;
        List<TestBatchModel> toReturn = new ArrayList<>();
        try {
            db = DbTool.getINSTANCE().dbLoggIn();
            ResultSet rs = null;
            String query = "SELECT * FROM roklubb.testBatch order by startDate desc ";
            prepareStatement = db.prepareStatement(query);
            rs = prepareStatement.executeQuery();
            while (rs.next()) {
                TestBatchModel getTestModel = new
                        TestBatchModel(rs.getString("id"), rs.getString("startDate"), rs.getString("endDate"));
                toReturn.add(getTestModel);
            }
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, errorMessage, e);
        }
        return toReturn;
    }

    /**
     * henter ut spesifikk person fra databasen
     * <p>
     * brukerens epost-addresse ("trym@example.com");
     *
     * @return et String objekt med eposten til brukeren.
     */

    public static List<ClassResultModel> getClassResultat() {
        Connection db = null;
        PreparedStatement prepareStatement = null;
        List<ClassResultModel> toReturn = new ArrayList<>();
        try {
            db = DbTool.getINSTANCE().dbLoggIn();
            ResultSet rs = null;
            String query = "SELECT roklubb.testResult.rank, roklubb.testResult.score,roklubb.user.fname, roklubb.user.lname, roklubb.user.club_name, roklubb.testResult.class_name_static,roklubb.testBatch.startDate, 5kmW, 5kmT, 2kmW, 2kmT, 60sW, percentLieRow, kgLieRow, percentSquat, kgSquat, flexibility\n" +
                    "                    FROM roklubb.testResult\n" +
                    "                             INNER JOIN roklubb.user ON testResult.user_id = user.id\n" +
                    "                            INNER JOIN roklubb.testBatch on roklubb.testBatch.id = testBatch_id\n" +
                    "                           where class_name_static = 'Senior Mann';";
            prepareStatement = db.prepareStatement(query);
            rs = prepareStatement.executeQuery();
            while (rs.next()) {
                ClassResultModel getTableModel = new
                        ClassResultModel(rs.getString("rank"), rs.getString("score"), rs.getString("fname"), rs.getString("lname"), rs.getString("club_name"), rs.getString("class_name_static"), rs.getDate("startDate"), rs.getDouble("5kmW")
                        , rs.getTime("5kmT"), rs.getDouble("2kmW"), rs.getTime("2kmT"), rs.getDouble("60sW"), rs.getDouble("percentLieRow"),
                        rs.getDouble("kgLieRow"), rs.getDouble("percentSquat"), rs.getDouble("kgSquat"), rs.getInt("flexibility"));
                toReturn.add(getTableModel);
            }
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, errorMessage, e);
        }
        return toReturn;
    }

    public static List<UserInfoModel> getUser() {
        Connection db = null;
        PreparedStatement prepareStatement = null;
        List<UserInfoModel> toReturn = new ArrayList<>();
        try {
            db = DbTool.getINSTANCE().dbLoggIn();
            ResultSet rs = null;
            String query = "SELECT id, fname, lname, dob, userType_name, class_name, club_name\n" +
                    "from roklubb.user;";
            prepareStatement = db.prepareStatement(query);
            rs = prepareStatement.executeQuery();
            while (rs.next()) {
                UserInfoModel getTableModel = new
                        UserInfoModel(rs.getString("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("dob"), rs.getString("userType_name")
                        , rs.getString("class_name"), rs.getString("club_name"));
                toReturn.add(getTableModel);
            }
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, errorMessage, e);
        }
        return toReturn;
    }

    public static List<UserInfoModel> newList() {
        Connection db = null;
        PreparedStatement prepareStatement = null;
        List<UserInfoModel> toReturn = new ArrayList<>();
        try {
            db = DbTool.getINSTANCE().dbLoggIn();
            ResultSet rs = null;
            String query = "SELECT * From roklubb.user";
            prepareStatement = db.prepareStatement(query);
            rs = prepareStatement.executeQuery();
            while (rs.next()) {
                UserInfoModel getModel = new
                        UserInfoModel(rs.getString("id"), rs.getString("email"), rs.getString("password"), rs.getString("fname"),
                        rs.getString("lname"), rs.getString("dob"), rs.getString("bio"), rs.getString("usertype"), rs.getString("classname"), rs.getString("club"));
                toReturn.add(getModel);
            }
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, errorMessage, e);
        }
        return toReturn;
    }
}


