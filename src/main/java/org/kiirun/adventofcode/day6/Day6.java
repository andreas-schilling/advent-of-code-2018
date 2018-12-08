package org.kiirun.adventofcode.day6;

import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.common.primitives.Ints;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;
import reactor.math.MathFlux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {
    private static final String[] INPUT = {"268,273", "211,325", "320,225", "320,207", "109,222", "267,283",
            "119,70", "138,277", "202,177", "251,233", "305,107", "230,279", "243,137", "74,109", "56,106",
            "258,97", "248,346", "71,199", "332,215", "208,292", "154,80", "74,256", "325,305", "174,133",
            "148,51", "112,71", "243,202", "136,237", "227,90", "191,145", "345,133", "340,299", "322,256",
            "86,323", "341,310", "342,221", "50,172", "284,160", "267,142", "244,153", "131,147", "245,323",
            "42,241", "90,207", "245,167", "335,106", "299,158", "181,186", "349,286", "327,108"};

    public static void main(String[] args) {
        final Flux<Position> positions =
                Flux.fromArray(INPUT)
                        .zipWith(Flux.range(1, INPUT.length))
                        .map(positionAndId -> new Position(positionAndId.getT1(), positionAndId.getT2()))
                        .cache();

        final Position minPosition =
                MathFlux.min(positions.map(Position::getX))
                        .zipWith(MathFlux.min(positions.map(Position::getY)))
                        .map(Position::new)
                        .block();

        final Position maxPosition =
                MathFlux.max(positions.map(Position::getX))
                        .zipWith(MathFlux.max(positions.map(Position::getY)))
                        .map(Position::new)
                        .block();

        final Table<Integer, Integer, Integer> distances = TreeBasedTable.create();

        Flux.range(minPosition.getX(), maxPosition.getX() - minPosition.getX())
                .repeat(maxPosition.getY() - minPosition.getY())
                .flatMap(x -> {
                    return Flux.range(minPosition.getY(), maxPosition.getY() - minPosition.getY())
                                   .map(y -> Tuples.of(x, y))
                                   .map(Position::new)
                                   .flatMap(pos -> positions.sort(Comparator.comparing(p -> p.manhattanDistance(pos)))
                                                           .next()
                                                           .map(closest -> Tuples.of(pos, closest.getId())))
                                   .doOnNext(posWithClosest -> distances.put(posWithClosest.getT1().getY(), posWithClosest.getT1().getX(), posWithClosest.getT2()));
                })
                .then(Mono.just(distances))
                .map(areaTable -> Tuples.of(areaTable,
                        Stream.of(areaTable.row(minPosition.getY()).values(),
                                areaTable.row(maxPosition.getY() - 1).values(),
                                areaTable.column(minPosition.getX()).values(),
                                areaTable.column(maxPosition.getX() - 1).values())
                                .flatMap(Collection::stream)
                                .collect(Collectors.toSet())))
                .map(TupleUtils.function((table, infiniteAreas) -> {
                    final HashMultiset<Integer> closestIds = HashMultiset.create(table.values());
                    closestIds.removeAll(infiniteAreas);
                    final Integer id = Multisets.copyHighestCountFirst(closestIds).iterator().next();
                    return Tuples.of(id, closestIds.count(id));
                }))
                .subscribe(largestAreaId -> System.out.println(largestAreaId.getT1() + " is the largest area of size " + largestAreaId.getT2()));

        Flux.range(minPosition.getX(), maxPosition.getX() - minPosition.getX())
                .repeat(maxPosition.getY() - minPosition.getY())
                .flatMap(x -> {
                    return Flux.range(minPosition.getY(), maxPosition.getY() - minPosition.getY())
                                   .map(y -> Tuples.of(x, y))
                                   .map(Position::new)
                                   .flatMap(pos -> MathFlux.sumInt(positions.map(p -> p.manhattanDistance(pos)))
                                                           .map(dist -> Tuples.of(pos, dist)))
                                   .doOnNext(posWithDistance -> distances.put(posWithDistance.getT1().getY(),
                                           posWithDistance.getT1().getX(),
                                           posWithDistance.getT2()));
                })
                .then(Mono.just(distances))
                .map(areaTable -> {
                    final HashMultiset<Integer> distanceSums = HashMultiset.create(areaTable.values());
                    distanceSums.removeIf(d -> d >= 10000);
                    return distanceSums.size();
                })
                .subscribe(sizeClosestToAll -> System.out.println("Largest area closest to all: " + sizeClosestToAll));
    }

    private static class Position {
        private Integer id;

        private final Integer x;

        private final Integer y;

        public Position(final String rawPosition, final Integer id) {
            this(Tuples.<Integer, Integer>fn2().apply(Splitter.on(",").splitToList(rawPosition)
                                                              .stream()
                                                              .map(Ints::tryParse)
                                                              .toArray(Integer[]::new)));
            this.id = id;
        }

        public Position(final Tuple2<Integer, Integer> position) {
            x = position.getT1();
            y = position.getT2();
        }

        public Integer getX() {
            return x;
        }

        public Integer getY() {
            return y;
        }

        public Integer getId() {
            return id;
        }

        public int manhattanDistance(Position otherPos) {
            return Math.abs(x - otherPos.x) + Math.abs(y - otherPos.y);
        }
    }
}
