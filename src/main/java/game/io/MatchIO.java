package game.io;

import game.Match;
import model.environment.Board;
import model.environment.Direction;
import model.environment.Square;
import model.moves.March;
import model.moves.Move;
import model.moves.Roundup;
import model.moves.Take;
import model.pieces.Piece;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class MatchIO {

    public static void write(Match match, String type) {
        String fileName = Instant.now().toString();
        fileName = fileName.replaceAll("[T\\-:]", "_");
        fileName = fileName.replaceAll("\\.[0-9]*Z", "");
        String filePath = String.format("res/matches/%s/%s.txt", type, fileName);
        try {
            new File(filePath).createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            List<Move> history = match.getHistory();
            for (int i = 0; i < history.size(); i++) {
                if (i % 2 == 0) {
                    writer.newLine();
                    writer.write((i/2 + 1) + ". ");
                }
                writer.write(history.get(i)+ " ");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Match read(String fileName, String type) {
        String filePath = String.format("res/matches/%s/%s.txt", type, fileName);
        Match match = new Match();
        Board board = match.getBoard();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                Arrays.stream(line.split(" "))
                        .filter(string -> !string.isEmpty())
                        .skip(1)
                        .forEach(string -> match.addMove(parseMove(string, board)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return match;
    }

    public static Move parseMove(String string, Board board) {
        LinkedList<Integer> positions = Arrays.stream(string.split("[^0-9]"))
                .map(Integer::parseInt).collect(Collectors.toCollection(LinkedList::new));

        int from = positions.getFirst();
        Piece mover = board.getSquare(from).getPiece();

        Move.Type type = string.contains("-") ? Move.Type.MARCH : Move.Type.ROUNDUP;
        if (type == Move.Type.MARCH) {
            int to = positions.getLast();
            return new March(mover, from, to);
        } else {
            LinkedList<Take> route = new LinkedList<>();
            for (int i = 1; i < positions.size(); i++) {
                int takeFrom = positions.get(i - 1);
                int takeTo = positions.get(i);
                Map<Direction, LinkedList<Integer>> directions = Move.mapDirections(takeFrom);
                for (Direction direction : directions.keySet()) {
                    LinkedList<Integer> way = directions.get(direction);
                    int index = way.indexOf(takeTo);
                    if (index == -1) continue;
                    Piece taken = way.subList(0, index).stream()
                            .map(board::getSquare)
                            .map(Square::getPiece)
                            .filter(Objects::nonNull)
                            .findAny().orElseThrow();
                    route.add(new Take(mover, takeFrom, takeTo, taken));
                }
            }
            return new Roundup(mover, route);
        }
    }
}
