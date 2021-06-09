package org.apache.cloudstack.api;

import org.apache.cloudstack.api.command.admin.feature.createCoffeeCmd;
import org.apache.cloudstack.api.command.admin.feature.listCoffeeCmd;
import org.apache.cloudstack.api.command.admin.feature.removeCoffeeCmd;
import org.apache.cloudstack.api.command.admin.feature.updateCoffeeCmd;
import org.apache.cloudstack.feature.Coffee;

import java.util.Collections;
import java.util.List;

public interface CoffeeManager {

    List<Coffee> Coffees = null;

    static List<Coffee> listCoffees(listCoffeeCmd listCoffeeCmd) {
        return Coffees;
    }

    static Coffee updateCoffee(updateCoffeeCmd updateCoffeeCmd) {

    }

    static boolean removeCoffee(removeCoffeeCmd removeCoffeeCmd) {
        Coffee coffee = new Coffee();
        coffee.setId(removeCoffeeCmd.getId());
        Coffees.remove(coffee);
        return true;
    }

    static boolean createCoffee(createCoffeeCmd createCoffeeCmd) {
        Coffee coffee = new Coffee();
        coffee.setId(removeCoffeeCmd.getId());
        Coffees.add(coffee);
        return true;
    }
}
