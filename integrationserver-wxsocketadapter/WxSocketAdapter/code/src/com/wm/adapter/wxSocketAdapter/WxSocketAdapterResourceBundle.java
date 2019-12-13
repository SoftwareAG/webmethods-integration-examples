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

import java.util.ListResourceBundle;

import com.wm.adk.ADKGLOBAL;

/*
 * This is the reource bundle for the Sample adapter
 */
public class WxSocketAdapterResourceBundle extends ListResourceBundle
  implements WxSocketAdapterConstants
{
    static final String IS_PKG_NAME = "/WxSocketAdapter/";

    static final Object[][] _contents = {
        {ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "WxSocketAdapter"}

        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION,
          "Adapter for Socket Communication"}

        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_VENDORNAME ,  "SAG"}

        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_THIRDPARTYCOPYRIGHTURL,
          IS_PKG_NAME + "copyright.html" }

        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_COPYRIGHTENCODING,
          "UTF-8" }

        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_RELEASENOTEURL,
          IS_PKG_NAME + "ReleaseNotes.html" }

        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL, 
          IS_PKG_NAME + "doc/OnlineHelp/WxSocketAdapterHelp.html"}


        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_ABOUT + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL, 
          IS_PKG_NAME + "doc/OnlineHelp/WxSocketAdapterAboutHelp.html"}

 
        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_LISTCONNECTIONTYPES + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL, 
          IS_PKG_NAME + "doc/OnlineHelp/WxSocketAdapterConnectionTypeListHelp.html"}

        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_LISTRESOURCES + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL, 
          IS_PKG_NAME + "doc/OnlineHelp/WxSocketAdapterResourceListHelp.html"}

        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_LISTPOLLINGNOTIFICATIONS + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL, 
          IS_PKG_NAME + "doc/OnlineHelp/WxSocketAdapterPollingNotificationListHelp.html"}
        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_LISTPOLLINGNOTIFICATIONDETAILS + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL, 
          IS_PKG_NAME + "doc/OnlineHelp/WxSocketAdapterPollingNotificationDetailsHelp.html"}
        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_LISTLISTENERS + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL, 
            IS_PKG_NAME + "doc/OnlineHelp/WxSocketAdapterListenerDetailsHelp.html"}
        ,{ADAPTER_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_LISTLISTENERTYPES + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL, 
            IS_PKG_NAME + "doc/OnlineHelp/WxSocketAdapterListenerTypeListHelp.html"}

        ,{CONNECTION_TYPE + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME,
          "Socket Connection"}

        ,{CONNECTION_TYPE + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION,
          "socket server or client connection"}
 
        ,{CONNECTION_TYPE + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL,
          IS_PKG_NAME + "doc/OnlineHelp/WxSocketAdapterConnectionConfigurationHelp.html"}

        ,{GROUP_SOCKET_CONNECTION + ADKGLOBAL.RESOURCEBUNDLEKEY_GROUP, "SOCKET Connection" }
        ,{GROUP_SOCKET_CONNECTION + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Connection to a SOCKET Peer" }
        ,{SOCKET_SERVER_HOST_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "SOCKET Hostname | Serial CommPort Name" }
        ,{SOCKET_SERVER_HOST_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "socket hostname or serial commport name" }
        ,{SOCKET_SERVER_PORT_NUMBER + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "SOCKET Port | Serial CommPort Settings (e.g. 9600|8|1|0)" }
        ,{SOCKET_SERVER_PORT_NUMBER + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "socket port or serial commport settings (e.g. 9600|8|1|0)" }
        ,{SOCKET_CONNECTOR_TIMEOUT + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "SOCKET Connector Timeout (in Millis)" }
        ,{SOCKET_CONNECTOR_TIMEOUT + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "socket connector timeout (in millis)" }
        ,{SOCKET_TRANSACTION_TYPE + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Connect Using Mode?" }
        ,{SOCKET_TRANSACTION_TYPE + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "socket client, socket server, or serial mode" }
        ,{SOCKET_TERMINATION_STRING + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "SOCKET Termination String (Server Only)" }
        ,{SOCKET_TERMINATION_STRING + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "string that terminates to socket connection" }
        ,{SOCKET_WELCOME_STRING + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Welcome Message for server connection" }
        ,{SOCKET_WELCOME_STRING + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "the welcome message sent by the server socket after the client" }
        ,{SOCKET_ZEROLEN_STRING + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Return zero length buffer?" }
        ,{SOCKET_ZEROLEN_STRING + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "if length of read bytes are 0, the buffer is returned. E.g. the rtsp can return 0 length buffer." }
        ,{SOCKET_ZEROBUFNUM_STRING + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Number of repeats to send zero length buffer" }
        ,{SOCKET_ZEROBUFNUM_STRING + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "if zero len buffer sending is enabled, how many times to send it." }
        
        ,{SERVICE_TEMPLATE + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Socket Write" }
        ,{SERVICE_TEMPLATE + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Writes to a socket" }
        ,{SERVICE_TEMPLATE + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL,
          IS_PKG_NAME + "doc/OnlineHelp/WxSOCKETAdapterServiceTemplateHelp.html"}

        ,{GROUP_FUNCTION_INVOCATION + ADKGLOBAL.RESOURCEBUNDLEKEY_GROUP, "Socket Write" }
        ,{GROUP_FUNCTION_INVOCATION + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Writes a byte[] to a socket" }

        ,{GROUP_MEMBER_FUNCTION_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Function Name" }
        ,{GROUP_MEMBER_FUNCTION_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "SOCKET function name" }
        
        ,{GROUP_MEMBER_INPUT_PARAMETER_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Input Parameter" }
        ,{GROUP_MEMBER_INPUT_PARAMETER_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION,
          "Input for SOCKET function invocation" }
        ,{GROUP_MEMBER_INPUT_FIELD_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Input Field" }
        ,{GROUP_MEMBER_INPUT_FIELD_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Input for SOCKET function invocation" }
        ,{GROUP_MEMBER_INPUT_FIELD_TYPES + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Input Field Type" }
        ,{GROUP_MEMBER_INPUT_FIELD_TYPES + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Input type for SOCKET function invocation" }
        ,{GROUP_MEMBER_OUTPUT_PARAMETER_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Output Parameter" }
        ,{GROUP_MEMBER_OUTPUT_PARAMETER_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Output for SOCKET function invocation" }
        ,{GROUP_MEMBER_OUTPUT_FIELD_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Output Field" }
        ,{GROUP_MEMBER_OUTPUT_FIELD_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Output for SOCKET function invocation" }
        ,{GROUP_MEMBER_OUTPUT_FIELD_TYPES + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Output Field Type" }
        ,{GROUP_MEMBER_OUTPUT_FIELD_TYPES + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Output type for SOCKET function invocation" }

        ,{POLLING_TEMPLATE + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Socket Read Async" }
        ,{POLLING_TEMPLATE + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Reads a byte[] from a SOCKET" }
        ,{POLLING_TEMPLATE + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL,
          IS_PKG_NAME + "doc/OnlineHelp/WxSOCKETAdapterPollingTemplateHelp.html"}

        ,{GROUP_MESSAGE_POLLING + ADKGLOBAL.RESOURCEBUNDLEKEY_GROUP, "Socket Read Async" }
        ,{GROUP_MESSAGE_POLLING + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Reads a byte[] from a SOCKET" }
        ,{GROUP_MESSAGE_POLLING + ADKGLOBAL.RESOURCEBUNDLEKEY_HELPURL,
          IS_PKG_NAME + "doc/OnlineHelp/WxSOCKETAdapterHelp10.html"}

        ,{GROUP_MEMBER_POLLING_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Socket Read Async" }
        ,{GROUP_MEMBER_POLLING_NAME + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Reads a byte[] from a SOCKET" }
        ,{GROUP_MEMBER_INPUT_FIELD_VALUES + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Input Field Value" }
        ,{GROUP_MEMBER_INPUT_FIELD_VALUES + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Input for SOCKET read" }
        ,{GROUP_MEMBER_OUTPUT_PARAMETER_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Output Parameter" }
        ,{GROUP_MEMBER_OUTPUT_PARAMETER_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Output for SOCKET notifier" }
        ,{GROUP_MEMBER_OUTPUT_FIELD_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Output Field" }
        ,{GROUP_MEMBER_OUTPUT_FIELD_NAMES + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Output for SOCKET notifier" }
        ,{GROUP_MEMBER_OUTPUT_FIELD_TYPES + ADKGLOBAL.RESOURCEBUNDLEKEY_DISPLAYNAME, "Output Field Type" }
        ,{GROUP_MEMBER_OUTPUT_FIELD_TYPES + ADKGLOBAL.RESOURCEBUNDLEKEY_DESCRIPTION, "Output type for SOCKET notifier" }
        
         ,{"101", "Initializing SOCKET Connection"}
         ,{"102", "Please disable related Polling Notification!!!"}
    
        ,{"1001", "Resource Connection Exception:"}

        ,{"1002", "Register Resource Domain Exception:"}
        ,{"1003", "Invalid Input Parameters"}
        ,{"1004", "Function Invocation Exception: \"{0}\"."}

        ,{"1005", "Message Polling Exception: \"{0}\"."}
        ,{"9999", "WxSocketAdapter Info: \"{0}\"."}
    };

    public Object[][] getContents() {
        return _contents;
    }
}