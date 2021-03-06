/*
 *  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.common.base.handler;

public interface Handler {

    /**
     * Initializes the handler
     *
     */
    public void init(InitConfig initConfig);

    /**
     * Name of the handler.
     *
     * @return Name of the handler
     */
    public String getName();

    /**
     * Tells if the handler is enabled or not. Based on the result {@Code canHandle()} and {@code handle()} may be
     * called.
     *
     */
    public boolean isEnabled();

    /**
     * Used to sort the set of handlers
     *
     * @return The priority value of the handler
     */
    public int getPriority();

}
