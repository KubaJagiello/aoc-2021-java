package se.jakub.day16;

import java.math.BigInteger;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {

    public static void main(String[] args) {
        var s = inputReader(16, 2).collect(toList()).get(0);
        var bitSequence = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            bitSequence.append(format("%4s", new BigInteger(valueOf(s.charAt(i)), 16).toString(2)).replace(' ', '0'));
        }
        System.out.println(partOne(0, bitSequence.toString()).value()); // 895
        System.out.println(partTwo(0, bitSequence.toString()).value()); // 1148595959144
    }

    private static Tuple partOne(int i, String bitSequence) {
        var savingInitialIndex = i;
        var version = parseInt(bitSequence.substring(i, i + 3), 2);
        var id = bitSequence.substring(i + 3, i + 6);
        i += 6;

        if (id.equals("100")) {
            var offset = 6;

            while (true) {
                var sequence = bitSequence.substring(i, i + 5);
                i += 5;
                offset += 5;
                if (sequence.charAt(0) == '0') {
                    return new Tuple(version, offset);
                }
            }
        } else {
            if (bitSequence.charAt(i) == '0') {
                var bitsLeft = parseInt(bitSequence.substring(i + 1, i + 16), 2);
                i += 16;

                do {
                    var tuple = partOne(i, bitSequence);
                    bitsLeft -= tuple.numberOfBitsRed();
                    version += tuple.value();
                    i += tuple.numberOfBitsRed();
                } while (bitsLeft > 0);
            } else {
                var packetsLeft = parseInt(bitSequence.substring(i + 1, i + 12), 2);
                i += 12;

                for (int packets = 0; packets < packetsLeft; packets++) {
                    var tuple = partOne(i, bitSequence);
                    i += tuple.numberOfBitsRed();
                    version += tuple.value();
                }
            }
        }
        return new Tuple(version, i - savingInitialIndex);
    }

    private static Tuple partTwo(int i, String bitSequence) {
        var j = i;
        var id = bitSequence.substring(i + 3, i + 6);
        i += 6;

        if (id.equals("100")) {
            var number = new StringBuilder();
            var nrOfBitsRed = 6;

            while (true) {
                var sequence = bitSequence.substring(i, i + 5);
                number.append(bitSequence, i + 1, i + 5);
                i += 5;
                nrOfBitsRed += 5;
                if (sequence.charAt(0) == '0') {
                    return new Tuple(Long.parseLong(number.toString(), 2), nrOfBitsRed);
                }
            }
        } else {
            var values = new ArrayList<Long>();

            if (bitSequence.charAt(i) == '0') {
                var bitsLeft = parseInt(bitSequence.substring(i + 1, i + 16), 2);
                i += 16;

                do {
                    var tuple = partTwo(i, bitSequence);
                    bitsLeft -= tuple.numberOfBitsRed();
                    values.add(tuple.value());
                    i += tuple.numberOfBitsRed();
                } while (bitsLeft > 0);
            } else {
                var packetsLeft = parseInt(bitSequence.substring(i + 1, i + 12), 2);
                i += 12;

                for (int packets = 0; packets < packetsLeft; packets++) {
                    var tuple = partTwo(i, bitSequence);
                    values.add(tuple.value());
                    i += tuple.numberOfBitsRed();
                }
            }

            return switch (Integer.parseInt(id, 2)) {
                case 0 -> new Tuple(values.stream().mapToLong(n -> n).sum(), i - j);
                case 1 -> new Tuple(values.stream().mapToLong(n -> n).reduce(1, (a, b) -> a * b), i - j);
                case 2 -> new Tuple(values.stream().mapToLong(v -> v).min().getAsLong(), i - j);
                case 3 -> new Tuple(values.stream().mapToLong(v -> v).max().getAsLong(), i - j);
                case 5 -> new Tuple(values.get(0) > values.get(1) ? 1 : 0, i - j);
                case 6 -> new Tuple(values.get(0) < values.get(1) ? 1 : 0, i - j);
                default -> new Tuple(values.get(0) == values.get(1) ? 1 : 0, i - j);
            };
        }
    }

}

record Tuple(long value, int numberOfBitsRed) {
}
