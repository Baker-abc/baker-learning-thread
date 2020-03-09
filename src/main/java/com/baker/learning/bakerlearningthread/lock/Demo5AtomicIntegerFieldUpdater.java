package com.baker.learning.bakerlearningthread.lock;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Description Atomic 原子级别操作包
 * @Author
 * @Date 2020/3/9 23:18
 **/
public class Demo5AtomicIntegerFieldUpdater {

//    原子更新字段类都是抽象类，只能通过静态方法newUpdater来创建一个更新器，并且需要设置想要更新的类和属性；
//    更新类的属性必须使用public volatile进行修饰
    private static AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    public static void main(String[] args) {
        //原子更新引用类型
        User user = new User("a", 1);
        int oldValue = updater.getAndAdd(user, 5);
        System.out.println(oldValue);
        System.out.println(updater.get(user));
    }

    static class User {
        private String userName;
        public volatile int age;

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
