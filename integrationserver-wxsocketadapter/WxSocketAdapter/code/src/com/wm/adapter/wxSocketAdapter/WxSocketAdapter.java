/* 
* Copyright © 2019 Software AG, Darmstadt, Germany and/or its licensors 
* 
* SPDX-License-Identifier: Apache-2.0 
* 
* Licensed under the Apache License, Version 2.0 (the "License"); 
* you may not use this file except in compliance with the License. 
* You may obtain a copy of the License at 
* 
* http://www.apache.org/licenses/LICENSE-2.0 
* 
* Unless required by applicable law or agreed to in writing, software 
* distributed under the License is distributed on an "AS IS" BASIS, 
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
* See the License for the specific language governing permissions and 
* limitations under the License. 
* 
*/
package com.wm.adapter.wxSocketAdapter;

import java.util.Locale;

import com.wm.adapter.wxSocketAdapter.connection.WxSocketConnectionFactory;
import com.wm.adapter.wxSocketAdapter.notification.SocketReadAsync;
import com.wm.adk.WmAdapter;
import com.wm.adk.error.AdapterException;
import com.wm.adk.info.AdapterTypeInfo;
import com.wm.adk.log.ARTLogger;

/*
 * Sample Adapter main class
 */
public class WxSocketAdapter extends WmAdapter implements WxSocketAdapterConstants
{
    private static WxSocketAdapter _instance = null;

    private static ARTLogger _logger;

    public WxSocketAdapter() throws AdapterException {
        super();
    }

    public void cleanup()
    {
        if (_logger != null)
            _logger.close();
    } 

    public static ARTLogger retrieveLogger()
    {
        return _logger;
    }

    public String getAdapterJCASpecVersion() {
        return ADAPTER_JCA_VERSION;
    }

    public int getAdapterMajorCode() {
        return ADAPTER_MAJOR_CODE;
    }

    public String getAdapterName() {
        return ADAPTER_NAME;
    }

    public String getAdapterVersion() {
        return ADAPTER_VERSION;
    }

    public String getAdapterResourceBundleName() {
        return ADAPTER_SOURCE_BUNDLE_NAME;
    }

    public void initialize() throws AdapterException {
        _logger = new ARTLogger(getAdapterMajorCode(), 
                                getAdapterName(), 
                                getAdapterResourceBundleName());
    }

    public void fillAdapterTypeInfo (AdapterTypeInfo info, Locale locale) {
        info.addConnectionFactory(WxSocketConnectionFactory.class.getName());
        info.addNotificationType(SocketReadAsync.class.getName());     
    }

    public static WxSocketAdapter getInstance() {
        synchronized(WxSocketAdapter.class) {
            if (_instance != null) {
                return _instance;
            }
            
            try {
                _instance = new WxSocketAdapter();
                return _instance;
            } catch (Throwable t) {
                t.printStackTrace();
                return null;
            }
        }
    }
}