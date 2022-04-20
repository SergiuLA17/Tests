package edu.java8.practice.service.impl;

import edu.java8.practice.domain.Privilege;
import edu.java8.practice.domain.User;
import edu.java8.practice.service.UserService;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    @Override
    public List<User> sortByAgeDescAndNameAsc(final List<User> users) {
        return users.stream()
                .sorted(Comparator.comparing(User::getFirstName))//sorty by name asc
                .peek(System.out::println)
                .sorted(Comparator.comparing(User::getAge) //sort by age desc
                        .reversed())
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    @Override
    public List<Privilege> getAllDistinctPrivileges(final List<User> users) {
        return users.stream()
                .map(User::getPrivileges)
                .peek(System.out::print)
                .flatMap(Collection::stream)//?
                .peek(System.out::println).
                collect(Collectors.toList());

    }

    @Override
    public Optional<User> getUpdateUserWithAgeHigherThan(final List<User> users, final int age) {
        return users.stream()
                .filter(user -> user.getAge() > age) //sort by age
                .findFirst(); //return first element
    }

    @Override
    public Map<Integer, List<User>> groupByCountOfPrivileges(final List<User> users) {
        return users.stream()
                .collect(Collectors.groupingBy(user -> user.getPrivileges().size()) //return user with hight number of privileges
                );
    }

    @Override
    public double getAverageAgeForUsers(final List<User> users) {
//
        return users.stream()
                .collect(Collectors.averagingInt(User::getAge));
    }

    @Override
    public Optional<String> getMostFrequentLastName(final List<User> users) {
        Map<String, Long> map =
                users.stream()
                        .collect(Collectors.groupingBy(
                                User::getLastName,
                                Collectors.counting()

                        ));
        System.out.println("test-1" + map);

        return Optional.of( // of - if there is an object null will throw exception
                map.entrySet() //return object map
                        .stream()
                        .max((e1, e2) -> e1.getValue() < e2.getValue() ? -1 :1)
                        .get()
                        .getKey()

        );
        ///???
    }

    @Override
    public List<User> filterBy(final List<User> users, final Predicate<User>... predicates) {
        Predicate<User> allPredicates = Arrays.stream(predicates) //get array with all predicates
                .reduce(w -> true, Predicate::and);//get predicates with true

        return users.stream()
                .filter(allPredicates)//get our reduced predicates in our steam
                .peek(System.out::println)
                .collect(Collectors.toList());
        //???
    }

    @Override
    public String convertTo(final List<User> users, final String delimiter, final Function<User, String> mapFun) {
        return users.stream()
                .map(mapFun)
                .peek(System.out::println)
                .collect(Collectors.joining(delimiter));
    }

    @Override
    public Map<Privilege, List<User>> groupByPrivileges(List<User> users) {
        return users.stream()
                .flatMap(
                        user -> user.getPrivileges().stream()
                                .map(privilege -> new AbstractMap.SimpleEntry<>(privilege, user))
                )
                .peek(System.out::println)
                .collect(
                        Collectors.groupingBy(
                                Map.Entry::getKey,
                                Collectors.mapping(
                                        Map.Entry::getValue,
                                        Collectors.toList()
                                )
                        )
                );
    }

    @Override
    public Map<String, Long> getNumberOfLastNames(final List<User> users) {

        return users.stream()
                .collect(Collectors.groupingBy(
                        User::getLastName,
                        Collectors.counting()
                ));
    }
}

//collect - return a collection(arraylist or hashset)