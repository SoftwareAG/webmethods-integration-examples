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
package wm.wxSocketAdapter;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2003-05-15 08:56:02 EDT

import com.wm.data.*;
import com.wm.app.b2b.server.ServiceException;
import com.wm.adapter.wxSocketAdapter.WxSocketAdapter;
// --- <<IS-START-IMPORTS>> ---
import com.wm.adk.admin.AdapterAdmin;

public final class admin

{
    // ---( internal utility methods )---

    final static admin _instance = new admin();

    static admin _newInstance() { return new admin(); }

    static admin _cast(Object o) { return (admin)o; }

    // ---( server methods )---

    public static final void shutDown (IData pipeline)
        throws ServiceException
    {
        // --- <<IS-START(shutDown)>> ---
        // @subtype unknown
        // @sigtype java 3.5
        WxSocketAdapter instance = WxSocketAdapter.getInstance();
        instance.cleanup();
        AdapterAdmin.unregisterAdapter(instance);
        // --- <<IS-END>> ---                
    }

    public static final void startUp (IData pipeline)
        throws ServiceException
    {
        // --- <<IS-START(startUp)>> ---
        // @subtype unknown
        // @sigtype java 3.5
        AdapterAdmin.registerAdapter(WxSocketAdapter.getInstance());
        // --- <<IS-END>> ---         
    }
}