package org.apache.cloudstack.api.command.admin.feature;

import org.apache.cloudstack.api.*;
import org.apache.cloudstack.api.response.ListResponse;
import org.apache.cloudstack.api.response.coffeeResponse;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@APICommand(name = "listCoffee", description = "Lists coffee", responseObject = coffeeResponse.class,
        requestHasSensitiveInfo = false, responseHasSensitiveInfo = true)
public class listCoffeeCmd extends BaseListCmd {
    public static final Logger s_logger = Logger.getLogger(listCoffeeCmd.class.getName());

    private static final String s_name = "listcoffeeresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @Parameter(name = ApiConstants.ID,
            type = BaseCmd.CommandType.UUID,
            required = false,
            entityType = coffeeResponse.class,
            description = "ID of my coffee")
    private Long id;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////



    @Override
    public String getCommandName() {
        return s_name;
    }

    @Override
    public void execute() {
        final List<Coffee> coffees = coffeeManager.listCoffees(this);

        final List<coffeeResponse> responseList = new ArrayList<>();
        for (final Coffee coffee : coffees) {
            coffeeResponse response = new coffeeResponse();
            response.setId(coffee.getUuid());
        }
        final ListResponse<coffeeResponse> coffeeResponses = new ListResponse<>();
        coffeeResponses.setObjectName("coffee");
        coffeeResponses.setResponses(responseList);
        coffeeResponses.setResponseName(getCommandName());
        setResponseObject(coffeeResponses);
    }
}
