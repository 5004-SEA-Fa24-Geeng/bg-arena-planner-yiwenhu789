package student;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Planner implements IPlanner {

    /**
     * Original full dataset
     */
    private final Set<BoardGame> allGames;

    /**
     * Current filtered games
     */
    private Set<BoardGame> filteredGames;

    public Planner(Set<BoardGame> games) {
        // TODO Auto-generated method stub
        this.allGames = new HashSet<>(games);
        this.filteredGames = new HashSet<>(games);
    }

    @Override
    public Stream<BoardGame> filter(String filter) {
        // TODO Auto-generated method stub
        return filter(filter, GameData.NAME, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        // TODO Auto-generated method stub
        return filter(filter, sortOn, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        // TODO Auto-generated method stub
        Set<BoardGame> filteredSet = new HashSet<>(filteredGames);

        // Apply filters if any
        if (filter != null && !filter.trim().isEmpty()) {
            String[] conditions = filter.split(",");
            for (String condition : conditions) {
                filteredSet = filterOnce(condition.trim(), filteredSet.stream()).collect(Collectors.toSet());
            }
        }

        // Sort
        List<BoardGame> sortedList = new ArrayList<>(filteredSet);

        // **Apply sorting based on GameData column**
        if (sortOn != null) {
            sortedList.sort((game1, game2) -> {
                int comparison = compareGames(game1, game2, sortOn);
                return ascending ? comparison : -comparison; // Reverse order if descending
            });
        }

        // Update to store filtered results
        filteredGames = new HashSet<>(sortedList);

        return sortedList.stream();
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        this.filteredGames = new HashSet<>(allGames);
    }

    public Set<BoardGame> getFilteredGames() {
        return new HashSet<>(filteredGames);
    }

    private Stream<BoardGame> filterOnce(String filter, Stream<BoardGame> filteredGames) {
        Operations operator = Operations.getOperatorFromStr(filter);
        if (operator == null) {
            return filteredGames;
        }

        // Remove spaces for consistency
        filter = filter.replaceAll(" ", "");

        // Split filter string by the operator
        String[] parts = filter.split(operator.getOperator());
        if (parts.length != 2) {
            return filteredGames;
        }

        GameData column;
        try {
            column = GameData.fromString(parts[0]);
        } catch (IllegalArgumentException e) {
            return filteredGames;
        }

        String value = parts[1].trim();

        return filteredGames.filter(game -> applyFilter(game, column, operator, value));
    }

    private boolean applyFilter(BoardGame game, GameData column, Operations operator, String value) {
        if (column == GameData.ID) return true;

        try {
            // Handle Name Case
            if (column == GameData.NAME) {
                if (operator == Operations.CONTAINS) {
                    // Only applies to string-based NAME filter
                    return game.getName().toLowerCase().contains(value.toLowerCase());
                }

                if (operator == Operations.EQUALS) {
                    return game.getName().toLowerCase().equals(value.toLowerCase());
                }

                // Ignore contains operation for non-string fields
                return true;
            }

            // HANDLE NUMERIC COMPARISONS (==, !=, >, <, >=, <=)
            // Convert value to double
            double numericValue = Double.parseDouble(value);

            // HANDLE NUMERIC COMPARISONS (Cast int fields to double)
            switch (column) {
                case RANK:
                    return operatorCompare((double) game.getRank(), numericValue, operator);
                case MIN_PLAYERS:
                    return operatorCompare((double) game.getMinPlayers(), numericValue, operator);
                case MAX_PLAYERS:
                    return operatorCompare((double) game.getMaxPlayers(), numericValue, operator);
                case MIN_TIME:
                    return operatorCompare((double) game.getMinPlayTime(), numericValue, operator);
                case MAX_TIME:
                    return operatorCompare((double) game.getMaxPlayTime(), numericValue, operator);
                case YEAR:
                    return operatorCompare((double) game.getYearPublished(), numericValue, operator);
                case RATING:
                    return operatorCompare(game.getRating(), numericValue, operator);
                case DIFFICULTY:
                    return operatorCompare(game.getDifficulty(), numericValue, operator);
                default:
                    return true; // Ignore unsupported filters
            }
        } catch (NumberFormatException e) {
            return true; // Ignore if value is not a valid number
        }
    }

    private boolean operatorCompare(double val1, double val2, Operations operator) {
        switch (operator) {
            case EQUALS:
                return val1 == val2;
            case NOT_EQUALS:
                return val1 != val2;
            case GREATER_THAN:
                return val1 > val2;
            case LESS_THAN:
                return val1 < val2;
            case GREATER_THAN_EQUALS:
                return val1 >= val2;
            case LESS_THAN_EQUALS:
                return val1 <= val2;
            default:
                return true; // Ignore invalid cases
        }
    }

    private int compareGames(BoardGame game1, BoardGame game2, GameData sortOn) {
        switch (sortOn) {
            case NAME:
                return game1.getName().compareToIgnoreCase(game2.getName());
            case MIN_PLAYERS:
                return Integer.compare(game1.getMinPlayers(), game2.getMinPlayers());
            case MAX_PLAYERS:
                return Integer.compare(game1.getMaxPlayers(), game2.getMaxPlayers());
            case MIN_TIME:
                return Integer.compare(game1.getMinPlayTime(), game2.getMinPlayTime());
            case MAX_TIME:
                return Integer.compare(game1.getMaxPlayTime(), game2.getMaxPlayTime());
            case DIFFICULTY:
                return Double.compare(game1.getDifficulty(), game2.getDifficulty());
            case RANK:
                return Integer.compare(game1.getRank(), game2.getRank());
            case RATING:
                return Double.compare(game1.getRating(), game2.getRating());
            case YEAR:
                return Integer.compare(game1.getYearPublished(), game2.getYearPublished());
            default:
                throw new IllegalArgumentException("Unsupported sort column: " + sortOn);
        }
    }

}
