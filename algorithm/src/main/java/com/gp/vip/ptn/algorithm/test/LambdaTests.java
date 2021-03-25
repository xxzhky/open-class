package com.gp.vip.ptn.algorithm.test;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Fred
 * @date: 2020/10/28 3:26 下午
 * @description: (类的描述)
 */
public class LambdaTests {


    static List<Student> students = new ArrayList<Student>() {
        {
            add(new Student(20160001, "孔明", 20, 1, "土木工程", "武汉大学"));
            add(new Student(20160002, "伯约", 21, 2, "信息安全", "武汉大学"));
            add(new Student(20160003, "玄德", 22, 3, "经济管理", "武汉大学"));
            add(new Student(20160004, "云长", 21, 2, "信息安全", "武汉大学"));
            add(new Student(20161001, "翼德", 21, 2, "机械与自动化", "华中科技大学"));
            add(new Student(20161002, "元直", 23, 4, "土木工程", "华中科技大学"));
            add(new Student(20161003, "奉孝", 23, 4, "计算机科学", "华中科技大学"));
            add(new Student(20162001, "仲谋", 22, 3, "土木工程", "浙江大学"));
            add(new Student(20162002, "鲁肃", 23, 4, "计算机科学", "浙江大学"));
            add(new Student(20163001, "丁奉", 24, 5, "土木工程", "南京大学"));
        }
    };


    public static void main(String[] args) {

        testPartition();
        Supplier t = new Supplier<String>() {
            @Override
            public String get() {
                return null;
            }
        };

        t = () -> "";

    }

    public static void testPartition() {
        Map<Boolean, List<Student>> partition = students.stream().collect(Collectors.partitioningBy(student -> "武汉大学".equals(student.getSchool())));
        System.out.println(JSON.toJSONString(partition));
    }

    public static void testOneInAll() {
        IntSummaryStatistics statistics = students.stream().collect(Collectors.summarizingInt(Student::getAge));

        statistics.getAverage();
        statistics.getCount();

    }

    public static void testReduce() {
        // 前面例子中的方法
        int totalAge = students.stream()
                .filter(student -> "计算机科学".equals(student.getMajor()))
                .mapToInt(Student::getAge).sum();
// 归约操作
        int totalAge1 = students.stream()
                .filter(student -> "计算机科学".equals(student.getMajor()))
                .map(Student::getAge)
                .reduce(0, (a, b) -> a + b);

// 进一步简化
        int totalAge2 = students.stream()
                .filter(student -> "计算机科学".equals(student.getMajor()))
                .map(Student::getAge)
                .reduce(0, Integer::sum);

// 采用无初始值的重载版本，需要注意返回Optional
        Optional<Integer> totalAge3 = students.stream()
                .filter(student -> "计算机科学".equals(student.getMajor()))
                .map(Student::getAge)
                .reduce(Integer::sum);

    }

    public static void testFlatmap() {
        String[] strs = {"java8", "is", "easy", "to", "use"};
        //flatMap是将一个流中的每个值都转成一个个流，然后再将这些流扁平化成为一个流
        List<String> distinctStrs = Arrays.stream(strs)
                .map(str -> str.split(""))  // 映射成为Stream<String[]>
                .flatMap(Arrays::stream)  // 扁平化为Stream<String>
                .distinct()
                .collect(Collectors.toList());

        System.out.println(distinctStrs);
    }

}

@Data
@AllArgsConstructor
class Student {

    /**
     * 学号
     */
    private long id;

    private String name;

    private int age;

    /**
     * 年级
     */
    private int grade;

    /**
     * 专业
     */
    private String major;

    /**
     * 学校
     */
    private String school;

    // 省略getter和setter
}

