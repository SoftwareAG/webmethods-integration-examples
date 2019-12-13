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

import com.wm.adapter.wxSocketAdapter.connection.WxSocketConnectionFactory;
import com.wm.adapter.wxSocketAdapter.notification.SocketReadAsync;
import com.wm.adapter.wxSocketAdapter.service.SocketWrite;


public interface WxSocketAdapterConstants {

    static final int ADAPTER_MAJOR_CODE = 1099;
    static final String ADAPTER_JCA_VERSION = "1.0";
    static final String ADAPTER_NAME = "WxSocketAdapter";
    static final String ADAPTER_VERSION = "1.1";
    static final String ADAPTER_SOURCE_BUNDLE_NAME =
      "com.wm.adapter.wxSocketAdapter.WxSocketAdapterResourceBundle";
    static final String CONNECTION_TYPE = WxSocketConnectionFactory.class.getName();
    static final String SERVICE_TEMPLATE = SocketWrite.class.getName();
    static final String POLLING_TEMPLATE = SocketReadAsync.class.getName();
    static final String GROUP_SOCKET_CONNECTION = "SocketServerConnection";
    static final String SOCKET_SERVER_HOST_NAME = "socketServerHostName";
    static final String SOCKET_SERVER_PORT_NUMBER = "socketServerPortNumber";
    static final String SOCKET_CONNECTOR_TIMEOUT = "timeout";
    static final String SOCKET_TRANSACTION_TYPE = "transactionType";
    static final String SOCKET_TERMINATION_STRING = "terminationString";
    static final String SOCKET_DEFBUFLEN_STRING = "defBufLen";
    static final String SOCKET_WELCOME_STRING = "welcomeString";
    static final String SOCKET_ZEROLEN_STRING = "zeroLenBuffer";
    static final String SOCKET_ZEROBUFNUM_STRING = "zeroBufferNum";
    static final String SOCKET_DIRCB_STRING = "directCallback";
    static final String GROUP_FUNCTION_INVOCATION = "SocketWrite";
    static final String GROUP_MEMBER_FUNCTION_NAME = "functionName";
    static final String GROUP_MEMBER_INPUT_PARAMETER_NAMES = "inputParameterNames";
    static final String GROUP_MEMBER_INPUT_FIELD_NAMES = "inputFieldNames";
    static final String GROUP_MEMBER_INPUT_FIELD_TYPES = "inputFieldTypes";
    static final String GROUP_MEMBER_OUTPUT_PARAMETER_NAMES = "outputParameterNames";
    static final String GROUP_MEMBER_OUTPUT_FIELD_NAMES = "outputFieldNames";
    static final String GROUP_MEMBER_OUTPUT_FIELD_TYPES = "outputFieldTypes";
    static final String GROUP_MESSAGE_POLLING = "SocketReadAsync";
    static final String GROUP_MEMBER_POLLING_NAME = "pollingName";
    static final String GROUP_MEMBER_INPUT_FIELD_VALUES = "inputFieldValues";
    static final String DOMAIN_FUNCTION_NAMES = "functionNames";
    static final String DOMAIN_INPUT_PARAMETER_NAMES = "inputParameterNames";
    static final String DOMAIN_INPUT_FIELD_NAMES = "inputFieldNames";
    static final String DOMAIN_INPUT_FIELD_TYPES = "inputFieldTypes";
    static final String DOMAIN_OUTPUT_PARAMETER_NAMES = "outputParameterNames";
    static final String DOMAIN_OUTPUT_FIELD_NAMES = "outputFieldNames";
    static final String DOMAIN_OUTPUT_FIELD_TYPES = "outputFieldTypes";
    static final String DOMAIN_POLLING_NAMES = "pollingNames";
}