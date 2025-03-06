package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameListTest {
    private GameList gameList;
    private List<BoardGame> sampleGames;

    @BeforeEach
    void setUp() {
        gameList = new GameList();
        sampleGames = List.of(
                new BoardGame("Dominion", 3, 2, 4, 30, 45, 2.2, 20, 8.0, 2008),
                new BoardGame("Catan", 1, 3, 4, 60, 90, 2.5, 10, 8.2, 1995),
                new BoardGame("Gloomhaven", 4, 1, 4, 60, 120, 3.9, 1, 8.8, 2017),
                new BoardGame("Carcassonne", 2, 2, 5, 30, 45, 1.8, 15, 7.9, 2000)
        );
    }

    @Test
    void getGameNames() {
        gameList.addToList("all", sampleGames.stream());
        List<String> names = gameList.getGameNames();
        assertEquals(names.get(0), "Carcassonne");
        assertEquals(names.get(1), "Catan");
        assertEquals(names.get(2), "Dominion");
        assertEquals(names.get(3), "Gloomhaven");
    }

    @Test
    void clear() {
        gameList.addToList("all", sampleGames.stream());
        gameList.clear();
        assertEquals(0, gameList.count());
    }

    @Test
    void count() {
        gameList.addToList("all", sampleGames.stream());
        assertEquals(4, gameList.count());
    }

    @Test
    void testAddByName() {
        gameList.addToList("Catan", sampleGames.stream());
        assertEquals(1, gameList.count());
    }

    @Test
    void testAddByIndex() {
        gameList.addToList("2", sampleGames.stream());
        assertEquals(1, gameList.count());
        assertTrue(gameList.getGameNames().contains("Catan"));
    }

    @Test
    void testAddByRange() {
        gameList.addToList("1-3", sampleGames.stream());
        assertEquals(3, gameList.count());
    }

    @Test
    void testAddAllGames() {
        gameList.addToList("all", sampleGames.stream());
        assertEquals(4, gameList.count());
    }

    @Test
    public void testAddToList_DuplicateGameIgnored() {
        BoardGame game1 = new BoardGame("Catan", 101, 3, 4, 30, 60, 2.5, 10, 8.2, 1995);
        BoardGame game2 = new BoardGame("Catan", 101, 3, 4, 30, 60, 2.5, 10, 8.2, 1995);
        BoardGame game3 = new BoardGame("Pandemic", 102, 2, 4, 45, 60, 2.8, 20, 7.8, 2008);

        Stream<BoardGame> filteredGames = Stream.of(game1, game2, game3);

        gameList.addToList("all", filteredGames);

        assertEquals(2, gameList.count());

        assertTrue(gameList.getGameNames().contains("Catan"));

        assertTrue(gameList.getGameNames().contains("Pandemic"));
    }

    @Test
    void testInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> gameList.addToList("Monopoly", sampleGames.stream()));
    }

    @Test
    void testInvalidNumber() {
        assertThrows(IllegalArgumentException.class, () -> gameList.addToList("10", sampleGames.stream()));
    }

    @Test
    void testInvalidRange() {
        assertThrows(IllegalArgumentException.class, () -> gameList.addToList("3-1", sampleGames.stream()));
    }

    @Test
    void testRemoveSingleGameByName() {
        gameList.addToList("all", sampleGames.stream());
        gameList.removeFromList("Catan");

        List<String> expected = List.of("Carcassonne", "Dominion", "Gloomhaven"); // Sorted list after removal
        assertEquals(expected, gameList.getGameNames());
    }

    @Test
    void testRemoveSingleGameByIndex() {
        gameList.addToList("all", sampleGames.stream());
        gameList.removeFromList("1"); // Removes "Domination" (1-based index)

        List<String> expected = List.of("Carcassonne", "Catan", "Gloomhaven");
        assertEquals(expected, gameList.getGameNames());
    }

    @Test
    void testRemoveRangeOfGames() {
        gameList.addToList("all", sampleGames.stream());
        gameList.removeFromList("2-4");

        List<String> expected = List.of("Dominion");
        assertEquals(expected, gameList.getGameNames());
    }

    @Test
    void testRemoveAllGames() {
        gameList.addToList("all", sampleGames.stream());
        gameList.removeFromList("all");

        assertTrue(gameList.getGameNames().isEmpty());
    }

    @Test
    void testRemoveInvalidGameThrowsException() {
        gameList.addToList("all", sampleGames.stream());
        assertThrows(IllegalArgumentException.class, () -> gameList.removeFromList("Nonexistent"));
    }

    @Test
    void testRemoveInvalidIndexThrowsException() {
        gameList.addToList("all", sampleGames.stream());
        assertThrows(IllegalArgumentException.class, () -> gameList.removeFromList("5")); // Out of range
    }

    @Test
    void testRemoveInvalidRangeThrowsException() {
        gameList.addToList("all", sampleGames.stream());
        assertThrows(IllegalArgumentException.class, () -> gameList.removeFromList("3-1")); // Invalid range
    }
}