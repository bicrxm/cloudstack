package org.apache.cloudstack.feature;

// ...declare imports...

import com.cloud.utils.component.ManagerBase;
import com.cloud.utils.component.PluggableService;
import org.apache.cloudstack.api.CoffeeManager;
import org.apache.cloudstack.framework.config.ConfigKey;
import org.apache.cloudstack.framework.config.Configurable;
import org.apache.log4j.Logger;

import javax.naming.ConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoffeeManagerImpl extends ManagerBase implements CoffeeManager, Configurable, PluggableService {

    private static Logger LOGGER = Logger.getLogger(CoffeeManagerImpl.class);

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
        // add API cmd classes here
        return cmdList;
    }

    @Override
    public String getConfigComponentName() {
        return CoffeeManager.class.getSimpleName();
    }

    @Override
    public ConfigKey<?>[] getConfigKeys() {
        return new ConfigKey[]{
                // Add ConfigKeys here
        };
    }
}
