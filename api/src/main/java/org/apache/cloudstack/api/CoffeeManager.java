package org.apache.cloudstack.api;

import org.apache.cloudstack.api.command.admin.feature.CreateCoffeeCmd;
import org.apache.cloudstack.api.command.admin.feature.ListCoffeeCmd;
import org.apache.cloudstack.api.command.admin.feature.RemoveCoffeeCmd;
import org.apache.cloudstack.api.command.admin.feature.UpdateCoffeeCmd;
import org.apache.cloudstack.feature.Coffee;

import java.util.Collections;
import java.util.List;

public interface CoffeeManager {

    List<Coffee> Coffees = null;

    static List<Coffee> ListCoffees(ListCoffeeCmd listCoffeeCmd) {
        return Coffees;
    }

    static Coffee UpdateCoffee(UpdateCoffeeCmd updateCoffeeCmd) {
        Coffee coffee = new Coffee();
        return coffee;
    }

    static boolean RemoveCoffee(RemoveCoffeeCmd removeCoffeeCmd) {
        Coffee coffee = new Coffee();
        coffee.setId(RemoveCoffeeCmd.getId());
        Coffees.remove(coffee);
        return true;
    }

    static boolean createCoffee(CreateCoffeeCmd createCoffeeCmd) {
        Coffee coffee = new Coffee();
        coffee.setId(RemoveCoffeeCmd.getId());
        Coffees.add(coffee);
        return true;
    }
}
