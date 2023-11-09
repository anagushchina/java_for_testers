package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import common.Utils;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class GroupCreationTests extends TestBase{

    //get list of groups from UI (getting attribute 'text' from elements)
    @ParameterizedTest
    @MethodSource("groupsProvider")
    public void createMultipleGroupsTest(GroupData group) {
        List<GroupData> oldGroups = appMan.initGroupHelper().getList();
        appMan.initGroupHelper().createGroup(group);
        List<GroupData> newGroups = appMan.initGroupHelper().getList();
        Comparator<GroupData> compareById = (o1, o2) ->
        {return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));};
        newGroups.sort(compareById);
        var expectedGroups = new ArrayList<>(oldGroups);
        expectedGroups.add(group.withId(newGroups.get(newGroups.size()-1).id()).withHeader("").withFooter(""));
        expectedGroups.sort(compareById);
        Assertions.assertEquals(expectedGroups, newGroups);
    }

    //get list of groups from DB using jdbc library and SQL
    @ParameterizedTest
    @MethodSource("singleRandomGroup")
    public void createGroupsJdbcTest(GroupData group) {
        List<GroupData> oldGroups = appMan.initJdbcHelper().getGroupList();
        appMan.initGroupHelper().createGroup(group);
        List<GroupData> newGroups = appMan.initJdbcHelper().getGroupList();
        Comparator<GroupData> compareById = (o1, o2) ->
        {return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));};
        newGroups.sort(compareById);
        var maxId = newGroups.get(newGroups.size()-1).id();
        var expectedGroups = new ArrayList<>(oldGroups);
        expectedGroups.add(group.withId(maxId));
        expectedGroups.sort(compareById);
        Assertions.assertEquals(expectedGroups, newGroups);
    }

    //get list of groups from DB using Hibernate library
    @ParameterizedTest
    @MethodSource("singleRandomGroup")
    public void createGroupsHbmTest(GroupData group) {
        List<GroupData> oldGroups = appMan.initHbm().getGroupList();
        appMan.initGroupHelper().createGroup(group);
        List<GroupData> newGroups = appMan.initHbm().getGroupList();
        Comparator<GroupData> compareById = (o1, o2) ->
        {return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));};
        newGroups.sort(compareById);
        var maxId = newGroups.get(newGroups.size()-1).id();
        var expectedGroups = new ArrayList<>(oldGroups);
        expectedGroups.add(group.withId(maxId));
        expectedGroups.sort(compareById);
        Assertions.assertEquals(expectedGroups, newGroups);
    }

    public static Stream<GroupData> singleRandomGroup() {
        Supplier<GroupData> randomGroup = () -> new GroupData().withName(Utils.randomString(6))
                .withHeader(Utils.randomString(5))
                .withFooter(Utils.randomString(8));
        return Stream.generate(randomGroup).limit(3);
    }



    @ParameterizedTest
    @MethodSource("negativeGroupsProvider")
    public void cannotCreateGroupTest(GroupData group) {
        List<GroupData> oldGroups = appMan.initGroupHelper().getList();
        appMan.initGroupHelper().createGroup(group);
        List<GroupData> newGroups = appMan.initGroupHelper().getList();
        Assertions.assertEquals(newGroups, oldGroups);
    }

    public static List<GroupData> groupsProvider() throws IOException {
        var result = new ArrayList<GroupData>();
//        for(var name: List.of("", "name")){
//            for(var header: List.of("", "header")){
//                for(var footer: List.of("", "footer")){
//                    result.add(new GroupData().withName(name).withHeader(header).withFooter(footer));
//                }
//            }
//        }

//        Ways of reading a file and save it in the variable 'data':
//        1. read and save the whole file using java.io.File library:
//        var data = new File(fileName);

//        2. read and save the whole file using java.nio.file.Files library:
//        var data = Files.readString(Paths.get(fileName));

//        3. read by strings
        var data = "";
        try (var reader = new FileReader("groups.json");
             var bReader = new BufferedReader(reader);){
            var line = bReader.readLine();
            while (line!=null){
                data = data + line;
                line = bReader.readLine();
            }
        }

//        Analyzes data and converts strings into objects of specified type:
        ObjectMapper objectMapper = new ObjectMapper();
        var value = objectMapper.readValue(data, new TypeReference<List<GroupData>>() {
        });
        result.addAll(value);
        return result;
    }

    public static List<GroupData> negativeGroupsProvider() {
        return new ArrayList<>(List.of(
                new GroupData("", "name'", "","")));
    }





    @ParameterizedTest
    @MethodSource("groupNamesProvider")
    public void createGroupsWithRandomNameTest(String name) {
        int groupCount = appMan.initGroupHelper().getCount();
        appMan.initGroupHelper().createGroup(new GroupData("", name, "header", "footer"));
        int newGroupCount = appMan.initGroupHelper().getCount();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }
    public static List<String> groupNamesProvider() {
        var result = new ArrayList<>(List.of("some name 1", "some name 2"));
        for(int i=1; i<=5; i++ ){
            result.add(Utils.randomString(7));
        }
        return result;
    }




    @Test
    public void createGroupTest() {
        int groupCount = appMan.initGroupHelper().getCount();
        appMan.initGroupHelper().createGroup(new GroupData("", "name", "header", "footer"));
        int newGroupCount = appMan.initGroupHelper().getCount();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }

    @Test
    public void createGroupWithEmptyNameTest() {
        appMan.initGroupHelper().createGroup(new GroupData());
    }

    @Test
    public void createGroupWithNameOnlyTest() {
        appMan.initGroupHelper().createGroup(new GroupData().withName("name only"));
    }




}
