package testPackage;

import dataAccess.SingletonConnection;
import dataAccess.TeamDataAccess.TeamDBAccess;
import exceptionPackage.Team.*;
import modelPackage.Club;
import modelPackage.Region;
import modelPackage.Team;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeamTest {
    private TeamDBAccess teamDBAccess;
    private Connection connection;

    @BeforeAll
    public void setupDatabase() throws Exception {
        connection = SingletonConnection.getInstance();
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS team (
                    name VARCHAR(255) PRIMARY KEY,
                    club VARCHAR(255),
                    region VARCHAR(255),
                    founding_date DATE,
                    has_been_world_champion BOOLEAN,
                    description TEXT,
                    nb_followers INT
                );
            """);
        statement.execute("""
                CREATE TABLE IF NOT EXISTS club (
                    name VARCHAR(255) PRIMARY KEY,
                    CEO VARCHAR(255),
                    nationality VARCHAR(255)
                );
            """);
        statement.execute("""
                INSERT INTO Club (name, CEO, nationality) VALUES ('Test Club', 'Test CEO', 'Test Nationality');
            """);
        teamDBAccess = new TeamDBAccess();
    }

    @AfterAll
    public void cleanupDatabase() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE team;");
        statement.execute("DROP TABLE club;");
    }

    @BeforeEach
    public void setup() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM team;");
    }

    @Test
    public void testAddTeam() throws Exception {
        Team team = new Team("Test Team", new Club("Test Club", "Test CEO", "Test Nationality"), new Region("Test Region"),
                LocalDate.of(2020, 1, 1), LocalDate.of(2019, 1, 1), true, "Test Description", 1000);
        assertDoesNotThrow(() -> teamDBAccess.addTeam(team));
    }

    @Test
    public void testGetTeam() throws Exception {
        Team team = new Team("Test Team", new Club("Test Club", "Test CEO", "Test Nationality"), new Region("Test Region"),
                LocalDate.of(2020, 1, 1), LocalDate.of(2019, 1, 1), true, "Test Description", 1000);
        teamDBAccess.addTeam(team);

        Team fetchedTeam = teamDBAccess.getTeam("Test Team");
        assertNotNull(fetchedTeam);
        assertEquals("Test Team", fetchedTeam.getName());
        assertEquals("Test Club", fetchedTeam.getClub().getName());
    }

    @Test
    public void testUpdateTeam() throws Exception {
        Team team = new Team("Test Team", new Club("Test Club", "Test CEO", "Test Nationality"), new Region("Test Region"),
                LocalDate.of(2020, 1, 1), LocalDate.of(2019, 1, 1), true, "Test Description", 1000);
        teamDBAccess.addTeam(team);

        Team updatedTeam = new Team("Updated Team", new Club("Test Club", "Test CEO", "Test Nationality"), new Region("Updated Region"),
                LocalDate.of(2021, 1, 1), LocalDate.of(2020, 1, 1), false, "Updated Description", 2000);
        assertDoesNotThrow(() -> teamDBAccess.updateTeam("Test Team", updatedTeam));

        Team fetchedTeam = teamDBAccess.getTeam("Updated Team");
        assertNotNull(fetchedTeam);
        assertEquals("Updated Team", fetchedTeam.getName());
        assertEquals("Updated Region", fetchedTeam.getRegion().getName());
    }

/*    @Test
    public void testDeleteTeams() throws Exception {
        Team team1 = new Team("Test Team 1", new Club("Test Club", "Test CEO", "Test Nationality"), new Region("Test Region"),
                LocalDate.of(2020, 1, 1), LocalDate.of(2019, 1, 1), true, "Test Description", 1000);
        Team team2 = new Team("Test Team 2", new Club("Test Club", "Test CEO", "Test Nationality"), new Region("Test Region"),
                LocalDate.of(2020, 1, 1), LocalDate.of(2019, 1, 1), true, "Test Description", 1000);
        teamDBAccess.addTeam(team1);
        teamDBAccess.addTeam(team2);

        ArrayList<String> teamNames = new ArrayList<>();
        teamNames.add("Test Team 1");
        teamNames.add("Test Team 2");
        assertDoesNotThrow(() -> teamDBAccess.deleteTeams(teamNames));

        assertThrows(ReadTeamException.class, () -> teamDBAccess.getTeam("Test Team 1"));
        assertThrows(ReadTeamException.class, () -> teamDBAccess.getTeam("Test Team 2"));
    }
*/
    @Test
    public void testGetAllTeams() throws Exception {
        Team team1 = new Team("Test Team 1", new Club("Test Club", "Test CEO", "Test Nationality"), new Region("Test Region"),
                LocalDate.of(2020, 1, 1), LocalDate.of(2019, 1, 1), true, "Test Description", 1000);
        Team team2 = new Team("Test Team 2", new Club("Test Club", "Test CEO", "Test Nationality"), new Region("Test Region"),
                LocalDate.of(2020, 1, 1), LocalDate.of(2019, 1, 1), true, "Test Description", 1000);
        teamDBAccess.addTeam(team1);
        teamDBAccess.addTeam(team2);

        ArrayList<Team> teams = teamDBAccess.getAllTeams();
        assertEquals(2, teams.size());
    }
    @Test
    void testSetNbFollowerNegativeValueThrowsException() {
        Team team = new Team();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            team.setNbFollower(-5);
        });
        assertEquals("Le nombre de followers ne peut pas être négatif.", exception.getMessage());
    }

    @Test
    void testSetNbFollowerNullValueAccepted() {
        Team team = new Team();
        team.setNbFollower(null);
        assertNull(team.getNbFollower());
    }

    @Test
    void testSetNbFollowerPositiveValueAccepted() {
        Team team = new Team();
        team.setNbFollower(42);
        assertEquals(42, team.getNbFollower());
    }
}
