package org.kiirun.adventofcode.day2;

import com.google.common.collect.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day2 {
    private static final String[] INPUT = {"qcsnyvpigkxmrdawlfdefotxbh", "qcsnyvligkymrdawljujfotxbh",
            "qmsnyvpigkzmrnawzjuefotxbh", "qosnyvpigkzmrnawljuefouxbh", "qcsnhlpigkzmrtawljuefotxbh",
            "qcsnyvpigkzmrdapljuyfotxih", "qcsnbvpiokzmrdawljuerotxbh", "qcfnyvmigkzmrdawljuefotdbh",
            "qcsnynpigkzmrdawljuefptxbp", "qcsgyapigkzmrdawljuafotxbh", "qcsnyvpigkzmrdapljueeotibh",
            "qcfnyvpigkzmndawljuwfotxbh", "qzsayvpigkzmrdawijuefotxbh", "qcsnsvpiekzmrdawljfefotxbh",
            "ncsnyvpigkzmrdaaljuefotxzh", "qssnyvpigkzmrdawljuefotobg", "qcshyipigkzmrdajljuefotxbh",
            "qcsnyvtigkzmrdawljgeaotxbh", "qcsnkvpxgkzmrdawljuefltxbh", "qcsnyvpiikzmrdawljuwfoqxbh",
            "qcsnybpigwzmqdawljuefotxbh", "qcsiyvpipkzbrdawljuefotxbh", "qldnyvpigkzmrdzwljuefotxbh",
            "qcsnyvpwgkzcrdawljuefmtxbh", "qcsnyvnigkzmrdahmjuefotxbh", "qcsnydpigkzmrdazljuefotxnh",
            "qcsqyvavgkzmrdawljuefotxbh", "ucsnyvpigkzmrdawljuefocxwh", "qcsnivpigrzmrdawljuefouxbh",
            "tcsnyvpibkzmrdawlkuefotxbh", "qcstytpigkzmrdawsjuefotxbh", "qcynyvpigkzmrdawlluefotjbh",
            "qcstyvpigkqrrdawljuefotxbh", "icsnyvpizkzmrcawljuefotxbh", "qcsnyvpimkzmrdavljuezotxbh",
            "qvsnoupigkzmrdawljuefotxbh", "qcsnyvpigkzmrdawwjuefftxgh", "qcpnyvpijkzmrdvwljuefotxbh",
            "qcsnyvpigkzmxdakdjuefotxbh", "jcsvyvpigkqmrdawljuefotxbh", "qcwnyvpigczmrsawljuefotxbh",
            "qcsnyvpdgkzmrdawljuefoixbm", "qysnyvpigkzmrdmwljuefotxbp", "qcsnavpigkzmrdaxajuefotxbh",
            "qcsfkvpigkzmrdawlcuefotxbh", "qcsnyvpigkvmrdawljcefotpbh", "qcsnyvpiqkkmrdawlvuefotxbh",
            "qhsnyvpigkzmrdawnjuedotxbh", "qasnlvpigkzmrdawljuefotxkh", "qgsnyvpigkzmrdabpjuefotxbh",
            "jcsnyvdigkzmrmawljuefotxbh", "qcsnivpigkzmrdawljuefonxth", "qcsnyjpigkzmrdawljgefotxmh",
            "qcstyvpigkzmrdacljuefovxbh", "qcsnvvpigkzmrdawljuewotrbh", "qcsnyvaigdzmrdawljueuotxbh",
            "qcsnyvpegkzmwdawljzefotxbh", "qcsnevpngkzmrdawlouefotxbh", "qcsnuvpigozmrdawljuefotdbh",
            "qgsnyvpigkzmqdayljuefotxbh", "qcsnyvpigkzmrdcwdjuofotxbh", "qcnnyvpigkzmrzawljuefstxbh",
            "qlsgyvpigkzmrdtwljuefotxbh", "qcsnyfpigkzlroawljuefotxbh", "qcsnkvwigkzmrdowljuefotxbh",
            "qcsnrvpigkzmrdawljuvfltxbh", "qcsnyvpigkzvreawljuefotxmh", "qcsrgvpigkzmrdawliuefotxbh",
            "qysnyvpigkzmrdawlxaefotxbh", "qcsnyvpigizmrdlwljuefotxbi", "qzsnyvpitkzmrdawljuefbtxbh",
            "qzgnyvpigkzmrdawljuefotxih", "qcsnyvpigkzmrdawlguefvtxbb", "qcsnyvpigkzmidawljuefouxjh",
            "qksnyvpigkzmrdawlruefotxhh", "qcsnyvpinkzmrdaaljuefotxah", "qcsnxvpigkzjrdawljuefhtxbh",
            "qcsnyvpigkzardawlgueuotxbh", "qcsnyvpiakzmrdpwljuefotxbt", "qcsnyvpigkzmrdawkjuefotxgb",
            "qcsnyvpigkzmrdawljuehocsbh", "qcsnsvpigktmrdawljuefotxvh", "qusnrvpigkzrrdawljuefotxbh",
            "qcsnyhiigkzmrdawrjuefotxbh", "qcsnavpigkzmrdawlfuefotxbz", "qcsnyvpigkzmmdamsjuefotxbh",
            "qcsnyvzigkzmrdcwljmefotxbh", "qcsnyvpigkzmriawljuefotbbe", "qcsnyvpigksmrdawljaefotxbd",
            "qcsnyvpigkzfrdawljuefoxxmh", "qcsnyvpygkrmrdawljuefotxbi", "qcsngvwigfzmrdawljuefotxbh",
            "qcsnyvpigkmkrdauljuefotxbh", "qcsnyvpigxzmrdgwljuefwtxbh", "qconyapigkzmrdaxljuefotxbh",
            "qcsnydpigkzwrdawljulfotxbh", "qcsnyvpimkzmmdawljuefotxch", "qcsnkspigkzmrdawgjuefotxbh",
            "qcsnyvpigkzmrdhwljfefbtxbh", "qcsnyipijkztrdawljuefotxbh", "qcseyvpigkrhrdawljuefotxbh",
            "qcsnyvpivkzmrdawljuefottbb", "qcsnyvpigkzmrdawlouefcjxbh", "qcsnyvpigkzmrgayljuefotxbm",
            "qcsnyvpvgkzmrdawrjujfotxbh", "qcsnyvpigkzmndawljuefqtxch", "qcsnyvpigbzmrdawljuefotibg",
            "qcsnyvpigkzmseawljuefotxbv", "qcsnwvpigkzmraawnjuefotxbh", "mcsnyvpiqkzmrdawljuefotlbh",
            "bcsnyvpigczmrdmwljuefotxbh", "qcsnyvpigkzmrtawljuegntxbh", "qcsnyvpijkzmrdawlmrefotxbh",
            "qdsnyvpfgkzmrdawljuekotxbh", "qcsnyvpigkzmrdawcjfegotxbh", "qcslyvphgkrmddawljuefotxbh",
            "qcsnyvpigkzmsdawkjuefojxbh", "qzsnyvpigkzmrzawljuefmtxbh", "qcsnyvpqgkzmcdawljuefttxbh",
            "qcsnyvpbgkpmrdawljuefoqxbh", "qcsnyvpigkemrdywljmefotxbh", "qcsnyypigkzmrdawljmefotxwh",
            "jcsnyvhwgkzmrdawljuefotxbh", "qcsnyvpigkzmrdawljurlotxwh", "qcsnnvpigzzmrdawljuefotwbh",
            "hcsnyvpigkzmrdarljuefitxbh", "qcsnyvpilkzmrfawljuefotsbh", "qcsnynpigkzmldawijuefotxbh",
            "qcsnyvpkgkjmrdawljuefotxlh", "qcsnylpigkzprdawljgefotxbh", "qcsnyvpigkzmrrawljnefohxbh",
            "qcsnivpigkzmrqawlbuefotxbh", "qcsgyvpigkzmrfawljuefotbbh", "qccuyvpigkzmrdawyjuefotxbh",
            "gcsnyvpigkzjrdawljuefotxby", "qcsmyvpiekzbrdawljuefotxbh", "qcsnyvpzgkrmrdawljuefotxbs",
            "qesnyvpigkzmpdqwljuefotxbh", "qcsnyvpigqzmrdawljuefutibh", "qcdnyvpigkzirdawljfefotxbh",
            "qcsnyvpiukzmrcrwljuefotxbh", "qcsnbvpickzmrdswljuefotxbh", "qcsnyvpighzmrpadljuefotxbh",
            "qccnyvpigkzmrdawljudxotxbh", "qcsnyvpigkzmrdabljuesotxlh", "qcsnyvpigkzmrrawlruefozxbh",
            "qconyzpigkzmrdawljuefotjbh", "qclnyvpigkzmrdxwljuefotbbh", "qcsnygpigkzmrdawlhuefooxbh",
            "qcsnyvpigkzmvdawljuefntxnh", "qcskyvpigkzmreawljuefotubh", "qrsnyvpxgkzmrdawljuefotxbz",
            "qclnyvpigtamrdawljuefotxbh", "qcsnyvpigkzmrdawojxefoyxbh", "qcsnyvpinkzmrdakljuwfotxbh",
            "qcsnyvpiykzmedawljuefgtxbh", "qcsayvpigkcmrdawijuefotxbh", "qcsnyvuiekzmrdamljuefotxbh",
            "qcdnyvpigkzmrdawnjuefoxxbh", "qcsnfvpwgszmrdawljuefotxbh", "qcsnycpigkzmrdawljqefotxih",
            "qcslyvphgkrmrdawljuefotxbh", "ecsnyvpigkzmrdawykuefotxbh", "qcsayvpigkzmraawljuekotxbh",
            "qcsnyvpigkdmrdawljuewofxbh", "qcznyvpigkzqrdawljuefotxnh", "qcsnyvplgkzmrdawljiefotlbh",
            "qcsnyvpigkzmroewljuefotbbh", "qcvnyvpigkzvrdawujuefotxbh", "qcanyypigkzmrdaeljuefotxbh",
            "qcsnyvwigkzmrdewljuefotxqh", "qcsryvpigkvmrdawljuefotabh", "pcsnyvpigkwmrdawljueforxbh",
            "qcsncvpigkzmrdawljuefotwmh", "qcsnyvpigozmrdawljudfozxbh", "qcsnynpigkzmrbawhjuefotxbh",
            "qcsnyvuigkzmrqawljuefotxch", "qcsnyvpickzmrdawljueeofxbh", "qcsnyvpigkzgrdawljueiouxbh",
            "qcsnyvpigkztrdawljuxnotxbh", "qcsnyvpigwzvrdawljfefotxbh", "qcsnyvpilkzmrdawljuefotxcz",
            "qcsnjvpigkzmrdawljuefoywbh", "qhsnyvpigyzmrdawljuhfotxbh", "qcsnyvpirkzmfdawljuffotxbh",
            "qcsjyvpigkzmvdawljuefotxzh", "qcszivpirkzmrdawljuefotxbh", "qwsnyvpigkzmtdawljuefetxbh",
            "qcrntvpigkzordawljuefotxbh", "qrsnyvpigkzmsdawljrefotxbh", "qcsnyviivkzmrdazljuefotxbh",
            "ecsnyvpigkzmrdawyjuefotxbw", "qnsnyvpkgkzmrdawljueqotxbh", "qcsyyppigkzmrdawljuefotxba",
            "qcsnyvpigkzhrdpwljuefouxbh", "ucsnyvpigkzmrdawojuefouxbh", "qysnyvpigkzmrdawljukfotxbd",
            "qcjnyvpigkzmrdalljfefotxbh", "fcsnyapigkmmrdawljuefotxbh", "qcnnkvpigkzmrdawljuefctxbh",
            "ocsnyvpigkzmsdawljuefotxbl", "qcsnyvpiakomrdawpjuefotxbh", "qcsnyvpigkzmrdawljvefbtxwh",
            "qcsnuvpigkzmvdfwljuefotxbh", "qcsnyapihkzmrdagljuefotxbh", "qzsnyvpigkzmrdawtjuefotxgh",
            "qcsnyvpigkzmrdawljuefomyah", "ocsnyvpigkzqrdawljuefotxbt", "qnsnyvpigkzmrdawljvevotxbh",
            "icsnyvpigkzmrdawljuefntxbt", "qcsnyvpigkzdrdawljuefotbbm", "scsnyvpigkzmrgawljuofotxbh",
            "qcsnydpigkzmrdowljuefotkbh", "qcsnyvtikkzmrdawljuefolxbh", "qcsiyvpigkcmrddwljuefotxbh",
            "qyrnyvpigkzmodawljuefotxbh", "pcsndvpfgkzmrdawljuefotxbh", "qcsnyvkigkhmriawljuefotxbh",
            "qcsnyvpigkzmsdmwlkuefotxbh", "dosnyvpigkzmrdawdjuefotxbh", "qcnnnvpigkzmrdzwljuefotxbh",
            "qcsnyvpivkumrdailjuefotxbh", "qcsnyvpigkzmrdswljuzfotxbz", "qcscynpigkzmrdawljuefotxbc",
            "qeanyvpigkzmrdawijuefotxbh", "qclnylpigkzmrdawljuefotxyh", "qcsnyvpigkzmrdawljbefowxbp",
            "qcsnyvpagkzmrdawljuefolebh", "qxsiyvpigkzmrdawljuefotxgh", "qcsnyvpigkynrdawljuefoqxbh",
            "qcsnevpigkzmrdxwgjuefotxbh", "qcsnyvpdgkzlrdawljeefotxbh", "qcsnyvpigkzmrgawljxbfotxbh",
            "ecsnyvpigkzmrdbwbjuefotxbh", "qcsnyvpigkzmraawujuefocxbh", "qcsnyvpihkzmrdawljuefouxbn",
            "fgsqyvpigkzmrdawljuefotxbh", "qcsnyvpigkmmrdawajuefotnbh", "qcsnyvvigkzmrdahljudfotxbh",
            "qcsnyvpixkzmrdqwljutfotxbh", "ncsnyvpickzmrdawljuehotxbh", "qcsnyvpizkzmrdawlpuefotxbp",
            "wcsnyvfigkzmrdakljuefotxbh", "qcsnyvpigkznrdhwljupfotxbh", "jcsnyvpigkpmzdawljuefotxbh",
            "qcsnyppigkkmrdawljujfotxbh", "qcsnyvpigkumrdaeljuefodxbh", "qcsnyvhigkzmrdrwljuefodxbh",
            "qcsnyvpigkacrdawtjuefotxbh", "qcsnyvpigkzmylawlquefotxbh"};

    public static void main(String[] args) {
        Flux<Appearance> appearances = Flux.fromArray(INPUT)
                .map(Lists::charactersOf)
                .map(chars -> Multimaps.index(chars, c -> c))
                .map(Multimaps::asMap)
                .map(mappedChars -> Maps.transformValues(mappedChars, List::size).values())
                .map(Sets::newHashSet)
                .flatMapIterable(counts -> counts.stream()
                        .map(Appearance::from)
                        .filter(appearance -> !appearance.equals(Appearance.OTHER))
                        .collect(Collectors.toList()))
                .cache();
        appearances
                .filter(Appearance.TWICE::equals).count()
                .zipWith(appearances.filter(Appearance.THREE_TIMES::equals).count())
                .map(TupleUtils.function((countTwice, countThreeTimes) ->
                        countTwice * countThreeTimes
                ))
                .subscribe(System.out::println);

        Flux.fromArray(INPUT)
                .flatMap(code ->
                        Flux.fromArray(INPUT)
                                .map(otherCode -> differenceIndices(code, otherCode))
                                .filter(diff -> diff.size() == 1)
                                .single(Collections.emptyList())
                                .zipWith(Mono.just(code)))
                .takeUntil(tuple -> !tuple.getT1().isEmpty())
                .last()
                .map(diffIndexAndId -> diffIndexAndId.mapT1(Iterables::getOnlyElement))
                .map(TupleUtils.function((diffPos, id) -> id.substring(0, diffPos) + id.substring(diffPos + 1)))
                .subscribe(System.out::println);
    }

    private enum Appearance {
        TWICE,

        THREE_TIMES,

        OTHER;

        public static Appearance from(Integer count) {
            if (count == 2) {
                return TWICE;
            }
            if (count == 3) {
                return THREE_TIMES;
            }
            return OTHER;
        }
    }

    private static List<Integer> differenceIndices(final String s1, final String s2) {
        List<Character> c1 = Lists.newArrayList(Lists.charactersOf(s1));
        List<Character> c2 = Lists.newArrayList(Lists.charactersOf(s2));
        List<Integer> diffIndices = Lists.newArrayList();
        for (int i = 0; i < c2.size(); i++) {
            if (!c1.get(i).equals(c2.get(i))) {
                diffIndices.add(i);
            }
        }
        return diffIndices;
    }
}
