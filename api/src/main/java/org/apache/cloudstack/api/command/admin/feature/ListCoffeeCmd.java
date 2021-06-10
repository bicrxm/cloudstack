package org.apache.cloudstack.api.command.admin.feature;

import org.apache.cloudstack.api.*;
import org.apache.cloudstack.api.response.ListResponse;
import org.apache.cloudstack.api.response.CoffeeResponse;
import org.apache.cloudstack.feature.Coffee;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@APICommand(name = "listCoffee", description = "Lists coffee", responseObject = CoffeeResponse.class,
        requestHasSensitiveInfo = false, responseHasSensitiveInfo = true)
public class ListCoffeeCmd extends BaseListCmd {
    public static final Logger s_logger = Logger.getLogger(ListCoffeeCmd.class.getName());

    private static final String s_name = "listcoffeeresponse";


    @Parameter(name = ApiConstants.ID,
            type = BaseCmd.CommandType.UUID,
            required = false,
            entityType = CoffeeResponse.class,
            description = "ID of my coffee")
    private Long id;


    public Long getId() {
        return id;
    }


    @Override
    public String getCommandName() {
        return s_name;
    }

    @Override
    public void execute() {
        final List<Coffee> coffees = CoffeeManager.ListCoffees(this);

        final List<CoffeeResponse> responseList = new ArrayList<>();
        for (final Coffee coffee : coffees) {
            CoffeeResponse response = new CoffeeResponse();
            response.setId(coffee.getUuid());
        }
        final ListResponse<CoffeeResponse> coffeeResponses = new ListResponse<>();
        coffeeResponses.setObjectName("coffee");
        coffeeResponses.setResponses(responseList);
        coffeeResponses.setResponseName(getCommandName());
        setResponseObject(coffeeResponses);
    }
}
