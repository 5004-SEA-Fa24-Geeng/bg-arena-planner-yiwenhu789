package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PlannerTest {
    private Planner planner;
    private Set<BoardGame> testGames;

    @BeforeEach
    void setUp() {
        Set<BoardGame> games = new HashSet<>();
        games.add(new BoardGame("Catan", 1, 3, 10, 45, 60, 2.5, 50, 8.2, 1995));
        games.add(new BoardGame("Pandemic", 2, 2, 7, 60, 120, 2.8, 30, 8.5, 2008));
        games.add(new BoardGame("Terraforming Mars", 3, 4, 5, 120, 180, 3.2, 10, 8.9, 2016));
        games.add(new BoardGame("Chess", 4, 2, 2, 10, 30, 3.8, 1, 9.0, 1500));

        planner = new Planner(games);
    }

    @Test
    void testFilterByMinPlayers() {
        Stream<BoardGame> result = planner.filter("minPlayers>2");
        Set<String> filteredGames = result.map(BoardGame::getName).collect(Collectors.toSet());
        assertTrue(filteredGames.contains("Catan"));
        assertFalse(filteredGames.contains("Pandemic"));
        assertTrue(filteredGames.contains("Terraforming Mars"));
        assertFalse(filteredGames.contains("Chess"));
    }

    @Test
    void testFilterByMaxPlayers() {
        Stream<BoardGame> result = planner.filter("maxPlayers<6");
        Set<String> filteredGames = result.map(BoardGame::getName).collect(Collectors.toSet());
        assertFalse(filteredGames.contains("Catan"));
        assertFalse(filteredGames.contains("Pandemic"));
        assertTrue(filteredGames.contains("Chess"));
        assertTrue(filteredGames.contains("Terraforming Mars"));
    }

    @Test
    void testFilterByYearPublished() {
        Stream<BoardGame> result = planner.filter("year>2000");
        Set<String> filteredGames = result.map(BoardGame::getName).collect(Collectors.toSet());
        assertTrue(filteredGames.contains("Pandemic"));
        assertTrue(filteredGames.contains("Terraforming Mars"));
        assertFalse(filteredGames.contains("Catan"));
        assertFalse(filteredGames.contains("Chess"));
    }

    @Test
    void testFilterByDifficulty() {
        Stream<BoardGame> result = planner.filter("difficulty>=3.0");
        Set<String> filteredGames = result.map(BoardGame::getName).collect(Collectors.toSet());
        assertTrue(filteredGames.contains("Terraforming Mars"));
        assertTrue(filteredGames.contains("Chess"));
        assertFalse(filteredGames.contains("Catan"));
        assertFalse(filteredGames.contains("Pandemic"));
    }

    @Test
    void testFilterByNameContains() {
        Stream<BoardGame> result = planner.filter("name~=catan");
        Set<String> filteredGames = result.map(BoardGame::getName).collect(Collectors.toSet());
        assertTrue(filteredGames.contains("Catan"));
        assertFalse(filteredGames.contains("Pandemic"));
    }

    @Test
    void testFilterMultipleConditions() {
        Stream<BoardGame> result = planner.filter("minPlayers>2,maxPlayers<6");
        Set<String> filteredGames = result.map(BoardGame::getName).collect(Collectors.toSet());
        assertFalse(filteredGames.contains("Catan"));
        assertFalse(filteredGames.contains("Pandemic"));
        assertTrue(filteredGames.contains("Terraforming Mars")); // Only this passes
        assertFalse(filteredGames.contains("Chess"));
    }

    @Test
    void testSortByYearPublishedAscending() {
        Stream<BoardGame> result = planner.filter("", GameData.YEAR, true);
        BoardGame[] sortedGames = result.toArray(BoardGame[]::new);

        assertEquals("Chess", sortedGames[0].getName());
        assertEquals("Catan", sortedGames[1].getName());
        assertEquals("Pandemic", sortedGames[2].getName());
        assertEquals("Terraforming Mars", sortedGames[3].getName());
    }

    @Test
    void testSortByYearPublishedDescending() {
        Stream<BoardGame> result = planner.filter("", GameData.YEAR, false);
        BoardGame[] sortedGames = result.toArray(BoardGame[]::new);

        assertEquals("Terraforming Mars", sortedGames[0].getName());
        assertEquals("Pandemic", sortedGames[1].getName());
        assertEquals("Catan", sortedGames[2].getName());
        assertEquals("Chess", sortedGames[3].getName());
    }

    @Test
    void testSortByDifficultyAscending() {
        Stream<BoardGame> result = planner.filter("", GameData.DIFFICULTY, true);
        BoardGame[] sortedGames = result.toArray(BoardGame[]::new);

        assertEquals("Catan", sortedGames[0].getName());
        assertEquals("Pandemic", sortedGames[1].getName());
        assertEquals("Terraforming Mars", sortedGames[2].getName());
        assertEquals("Chess", sortedGames[3].getName());
    }

    @Test
    void testSortByDifficultyDescending() {
        Stream<BoardGame> result = planner.filter("", GameData.DIFFICULTY, false);
        BoardGame[] sortedGames = result.toArray(BoardGame[]::new);

        assertEquals("Chess", sortedGames[0].getName());
        assertEquals("Terraforming Mars", sortedGames[1].getName());
        assertEquals("Pandemic", sortedGames[2].getName());
        assertEquals("Catan", sortedGames[3].getName());
    }


    @Test
    void reset() {
        planner.filter("minPlayers>2");
        assertEquals(planner.getFilteredGames().size(), 2);

        planner.reset();

        assertEquals(planner.getFilteredGames().size(), 4);
    }

}
