package com.example.demo.ht.com;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author heteng
 * @date 2024/1/22
 * @description
 */
@RestController
public class TestController {


    @GetMapping("/test")
    public void test(Test test) {
        System.out.println(test);
    }


    public static class TetsA{
        private Integer a;

        public void setA(Integer a){
            this.a = a;
        }

        public Integer getA(){
            return this.a;
        }
    }

    public static void main(String[] args) {
        List<TetsA> list = new ArrayList<>();
        TetsA a = new TetsA();
        a.setA(1);
        list.add(a);

        TetsA a2 = new TetsA();
        a2.setA(2);
        list.add(a2);
        List<TetsA> collect = list.stream().sorted(Comparator.comparing(TetsA::getA)).collect(Collectors.toList());
        collect.addAll(list);
        System.out.println(collect);

        String aa = "11344";
        String[] split = aa.split(",");
        System.out.println(split);
    }


}
