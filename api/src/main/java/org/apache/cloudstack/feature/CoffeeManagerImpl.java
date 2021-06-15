package org.apache.cloudstack.feature;

import com.cloud.utils.component.ManagerBase;
import org.apache.cloudstack.api.CoffeeManager;
import org.apache.cloudstack.api.command.admin.feature.ListCoffeeCmd;
import org.apache.log4j.Logger;

import javax.naming.ConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CoffeeManagerImpl extends ManagerBase implements CoffeeManager {
    private static final Logger LOGGER = Logger.getLogger(CoffeeManagerImpl.class);

    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        super.configure(name, params);
        // Add code on how to handle when this is configured
        return true;
    }

    @Override
    public boolean start() {
        // Add code on how to handle when this is started
        return true;
    }

    @Override
    public boolean stop() {
        // Add code on how to handle when this is stopped
        return true;
    }
    @Override
    public List<Class<?>> getCommands() {
        final List<Class<?>> cmdList = new ArrayList<>();
        cmdList.add(ListCoffeeCmd.class);
        return cmdList;
    }
}
