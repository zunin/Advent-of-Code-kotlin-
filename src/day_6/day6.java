package day_6;

import java.io.*;
import java.util.function.Consumer;

public class day6 {
    public static void main(String[] args) {
        File inputFile = new File("./src/day_6/input.txt");
        try {
            LineParser parser = new LineParser();

            FileReader reader = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(reader);

            Grid<Boolean> grid = new BooleanSquareGrid(1000);

            bufferedReader.lines().forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    LineParser.Instruction instruction = parser.parse(s);
                    Coordinate fromCoordinate = instruction.getFromCoordinate();
                    Coordinate toCoordinate = instruction.getToCoordinate();
                    for (int x = fromCoordinate.getX(); x < toCoordinate.getX(); x++) {
                        for (int y = fromCoordinate.getY(); y < toCoordinate.getY(); y++) {
                            switch (instruction.getCommand()) {
                                case ON:
                                    grid.get(x).set(y, true);
                                case OFF:
                                    grid.get(x).set(y, false);
                                case TOGGLE:
                                    grid.get(x).set(y, !grid.get(x).get(y));
                            }
                        }
                    }
                }
            });

            int litLights = 0;
            for (Column<Boolean> column : grid) {
                for (Boolean isLit : column) {
                    if (isLit) {
                        litLights += 1;
                    }
                }
            }
            System.out.println("There are "+Integer.toString(litLights)+" lit lights!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
