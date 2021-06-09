package org.apache.cloudstack.api;

import org.apache.cloudstack.api.command.admin.feature.createCoffeeCmd;
import org.apache.cloudstack.api.command.admin.feature.listCoffeeCmd;
import org.apache.cloudstack.api.command.admin.feature.removeCoffeeCmd;
import org.apache.cloudstack.api.command.admin.feature.updateCoffeeCmd;
import org.apache.cloudstack.feature.Coffee;

import java.util.Collections;
import java.util.List;

public interface CoffeeManager {
    static List<Coffee> listCoffees(listCoffeeCmd listCoffeeCmd) {
        return Collections.emptyList();
    }

    static Coffee updateCoffee(updateCoffeeCmd updateCoffeeCmd) {
        Coffee coffee = new Coffee();

        return coffee;
    }

    static boolean removeCoffee(removeCoffeeCmd removeCoffeeCmd) {
        Coffee coffee = new Coffee();
        coffee.id = null;
        return true;
    }

    static Coffee createCoffee(createCoffeeCmd id) {
        Coffee coffee = new Coffee();

        return coffee;
    }
}
