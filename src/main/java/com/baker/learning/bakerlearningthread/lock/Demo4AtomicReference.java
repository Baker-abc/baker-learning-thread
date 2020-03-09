package com.baker.learning.bakerlearningthread.lock;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description Atomic 原子级别操作包
 * @Author
 * @Date 2020/3/9 23:18
 **/
public class Demo4AtomicReference {

    private static AtomicReference<User> reference = new AtomicReference<>();

    public static void main(String[] args) {
        //原子更新引用类型
        User user1 = new User("a", 1);
        reference.set(user1);
        User user2 = new User("b", 2);
        User user = reference.getAndSet(user2);
        System.out.println(user);
        System.out.println(reference.get());
    }

    static class User {
        private String userName;
        private int age;

        public User(String userName, int age) {
            this.userName = userName;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userName='" + userName + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
