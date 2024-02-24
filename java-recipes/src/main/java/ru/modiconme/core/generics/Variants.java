package ru.modiconme.core.generics;

import java.util.ArrayList;
import java.util.List;

public class Variants {
    public static void main(String[] args) {
        Device device = new Device();
        Device keyboardDevice = new Keyboard(); // ковариантность
//        Keyboard keyboard = new Device();

        List<Device> devices = new ArrayList<>();
        List<Keyboard> keyboards = new ArrayList<>();

        foo1(devices);
        foo1(keyboards);

        foo2(devices);
//        foo2(keyboards);

        foo3(devices);
//        foo3(keyboards);
    }

    // read superclass
    static void foo1(List<? extends Device> devices) { // контрковариантность
//        devices.add(new Device()); -> не знаем что конкретно там лежит
        Device device = devices.get(0); // знаем что там точно лежат наследники Device
//        Keyboard device = devices.get(0);
    }

    // write class or subclass
    static void foo2(List<? super Device> devices) { // контрковариантность
        devices.add(new Keyboard()); // как минимум лист Device, т.е. можем добавлять Device или его наследников
        Object object = devices.get(0); // получаем только Object, так как находится вверху иерархии
    }

    static void foo3(List<Device> devices) { // инвариантность
        devices.add(new Keyboard()); // Device или наследники
        Object object = devices.get(0); // Device или предки
    }
}

class Device {

}

class Mouse extends Device {

}

class Keyboard extends Device {

}

class WirelessMouse extends Mouse {

}
