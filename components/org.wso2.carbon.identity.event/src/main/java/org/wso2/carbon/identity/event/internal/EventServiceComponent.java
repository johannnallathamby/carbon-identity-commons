/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.event.internal;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.common.base.handler.MessageHandlerComparator;
import org.wso2.carbon.identity.event.AbstractEventHandler;
import org.wso2.carbon.identity.event.EventException;
import org.wso2.carbon.identity.event.EventService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EventServiceComponent {

    private static Logger logger = LoggerFactory.getLogger(EventServiceComponent.class);

    private ServiceRegistration serviceRegistration = null;

    // list of all registered event handlers
    public static List<AbstractEventHandler> eventHandlerList = new ArrayList();

    protected void activate(ComponentContext componentContext, BundleContext bundleContext, Map<String, ?> properties) {

        try {

            serviceRegistration = bundleContext.registerService(EventService.class.getName(),
                                                                new EventServiceImpl(
                                                                        eventHandlerList, Integer.parseInt
                                                                        (ConfigParser.getInstance().getThreadPoolSize())),
                                                                null);

        } catch (Throwable e) {
            logger.error("Error while initiating IdentityMgtService.", e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Identity Management Listener is enabled.");
        }
    }


    protected void deactivate(ComponentContext context) {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Identity Management bundle is deactivated.");
        }
    }

    protected void registerEventHandler(AbstractEventHandler eventHandler) throws EventException {
        String handlerName = eventHandler.getName();
        eventHandler.init(ConfigParser.getInstance().getModuleConfigurations(handlerName));
        eventHandlerList.add(eventHandler);

        MessageHandlerComparator messageHandlerComparator = new MessageHandlerComparator(null);
        Collections.sort(eventHandlerList, messageHandlerComparator);
    }

    protected void unRegisterEventHandler(AbstractEventHandler eventHandler) {
    }

}
