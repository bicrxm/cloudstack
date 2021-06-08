package org.apache.cloudstack.api.command.admin.feature;

import com.cloud.user.Account;
import org.apache.cloudstack.api.APICommand;
import org.apache.cloudstack.api.ApiConstants;
import org.apache.cloudstack.api.BaseCmd;
import org.apache.cloudstack.api.Parameter;
import org.apache.cloudstack.api.response.coffeeResponse;
import org.apache.cloudstack.context.CallContext;
import org.apache.log4j.Logger;

@APICommand(name = "createCoffee", description = "Creates a Coffee.", responseObject = coffeeResponse.class,
        requestHasSensitiveInfo = false, responseHasSensitiveInfo = false)
public class createCoffeeCmd extends BaseCmd {
    public static final Logger s_logger = Logger.getLogger(createCoffeeCmd.class.getName());

    private static final String s_name = "createcoffeeresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////


    @Parameter(name = ApiConstants.ID,
            type = CommandType.UUID,
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
    public long getEntityOwnerId() {
        return Account.ACCOUNT_ID_SYSTEM;
    }

    @Override
    public void execute() {
        CallContext.current().setEventDetails("Id: " + getId());

        final coffeeResponse response = new coffeeResponse();
        response.setResponseName(getCommandName());
        response.setObjectName("coffee-object-name");
        setResponseObject(response);
        }
    }
}