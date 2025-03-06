package student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameList implements IGameList {

    /**
     * A list that stores the unique board games added by the user.
     * This list contains all games currently in the planner.
     */
    private List<BoardGame> gameList;

    /**
     * Constructor for the GameList.
     */
    public GameList() {
        gameList = new ArrayList<>();
    }

    @Override
    public List<String> getGameNames() {
        // TODO Auto-generated method stub
        return gameList.stream()
                .map(BoardGame::getName) // Extract game names
                .sorted(String.CASE_INSENSITIVE_ORDER) // Sort ignoring case
                .collect(Collectors.toList()); // Convert to List
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        gameList = new ArrayList<>();
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        return gameList.size();
    }

    @Override
    public void saveGame(String filename) {
        // TODO Auto-generated method stub
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty.");
        }

        List<String> gameNames = getGameNames(); // Get sorted list of game names

        try {
            // Ensure parent directories exist
            Path filePath = Paths.get(filename);
            Files.createDirectories(filePath.getParent()); // Creates "temp/" if missing

            // Open file safely and overwrite existing content
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (String gameName : gameNames) {
                    writer.write(gameName);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filename, e);
        }
    }

    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        // Convert stream to list for easier indexing
        List<BoardGame> filteredList = filtered.toList();

        // Case 1: Add all
        if (str.equalsIgnoreCase("all")) {
            for (BoardGame game : filteredList) {
                if (!gameList.contains(game)) {
                    gameList.add(game);
                }
            }
            return;
        }

        // Case 2: "1-5" or "3"
        if (str.matches("\\d+(-\\d+)?")) {
            String[] parts = str.split("-");
            int start = Integer.parseInt(parts[0]) - 1; // Convert to zero-based index

            int end = parts.length > 1 ? Integer.parseInt(parts[1]) - 1 : start;
            if (start < 0 || end >= filteredList.size() || start > end) {
                throw new IllegalArgumentException("Invalid range: " + str);
            }

            for (int i = start; i <= end; i++) {
                BoardGame game = filteredList.get(i);
                if (!gameList.contains(game)) {
                    gameList.add(game);
                }
            }
            return;
        }

        // Case 3: Name String
        for (BoardGame game : filteredList) {
            if (game.getName().equalsIgnoreCase(str)) {
                if (!gameList.contains(game)) {
                    gameList.add(game);
                }
                return;
            }
        }

        throw new IllegalArgumentException("Invalid input: " + str);
    }

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input: Cannot be null or empty.");
        }

        str = str.trim().toLowerCase();

        // Case 1: Remove all
        if (str.equals("all")) {
            gameList.clear();
            return;
        }

        // Case 2: Remove by name
        for (BoardGame game : gameList) {
            if (game.getName().equalsIgnoreCase(str)) {
                gameList.remove(game);
                return;
            }
        }

        // Case 3: Remove by single index (1-based)
        try {
            int index = Integer.parseInt(str);

            if (index < 1 || index > gameList.size()) {
                throw new IllegalArgumentException("Invalid index: Out of range.");
            }

            gameList.remove(gameList.get(index - 1)); // Convert to 0-based index
            return;
        } catch (NumberFormatException ignored) {
            // Not a number, proceed to check for range
        }

        // Case 4: Remove by range
        if (str.contains("-")) {
            String[] parts = str.split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid range format.");
            }

            try {
                int start = Integer.parseInt(parts[0]);
                int end = Integer.parseInt(parts[1]);

                if (start > end || start < 1) {
                    throw new IllegalArgumentException("Invalid range: Start must be <= end.");
                }

                if (end > gameList.size()) {
                    throw new IllegalArgumentException("Range out of bounds.");
                }

                for (int i = start; i <= end; i++) {
                    gameList.remove(gameList.get(start - 1));
                }

                return;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid range values.");
            }
        }

        throw new IllegalArgumentException("Invalid input format.");
    }


}
