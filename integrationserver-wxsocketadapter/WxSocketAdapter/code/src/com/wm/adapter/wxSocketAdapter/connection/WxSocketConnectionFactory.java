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
package com.wm.adapter.wxSocketAdapter.connection;

import com.wm.adapter.wxSocketAdapter.WxSocketAdapter;
import com.wm.adapter.wxSocketAdapter.WxSocketAdapterConstants;
import com.wm.adapter.wxSocketAdapter.service.SocketWrite;
import com.wm.adk.connection.WmManagedConnection;
import com.wm.adk.connection.WmManagedConnectionFactory;
import com.wm.adk.error.AdapterException;
import com.wm.adk.info.ResourceAdapterMetadataInfo;
import com.wm.adk.metadata.WmDescriptor;

import java.util.Locale;

public class WxSocketConnectionFactory extends WmManagedConnectionFactory
  implements WxSocketAdapterConstants
{
	public static final long serialVersionUID = 0x004917615921435L;
	
    private String _socketServerHostName = "localhost";
    private String _socketServerPortNumber = "4711";
    private int _timeout = 0;
    private int _transactionMode = 0; /* client = 0, server = 1, serial = 2 */
    private String _terminationString = "";
    private String _welcomeString = "";
    private boolean _zeroLenBuffer = false;
    private int _zeroBufferNum = 1;

	public int getZeroBufferNum() {
		return _zeroBufferNum;
	}

	public void setZeroBufferNum(int _zeroBufNum) {
		this._zeroBufferNum = _zeroBufNum;
	}

    public String getZeroLenBuffer() {
		return (new Boolean(_zeroLenBuffer)).toString();
	}

	public void setZeroLenBuffer(String _zeroLenBuffer) {
		this._zeroLenBuffer = Boolean.parseBoolean(_zeroLenBuffer);
	}

	public String getWelcomeString() {
		return _welcomeString;
	}

	public void setWelcomeString(String string) {
		_welcomeString = string;
	}

	public String getTerminationString() {
		return _terminationString;
	}

	public void setTerminationString(String string) {
		_terminationString = string;
	}

    public void setSocketServerHostName(String serverHostName) {
        _socketServerHostName = serverHostName;
    }

    public String getSocketServerHostName() {
        return _socketServerHostName;
    }

    
    public void setSocketServerPortNumber(String serverPortNumber) {
        _socketServerPortNumber = serverPortNumber;
    }

    
    public String getSocketServerPortNumber() {
        return _socketServerPortNumber;
    }

    
    public void setTimeout(int timeout) {
        _timeout = timeout;
    }

    
    public int getTimeout() {
        return _timeout;
    }

    public void setTransactionType(String transactionControl) {
        if (transactionControl.equals("Client"))
            _transactionMode = 0;
        else if (transactionControl.equals("Server"))
            _transactionMode = 1;
        else if (transactionControl.equals("Serial"))
        	_transactionMode = 2;
        else
        	_transactionMode = 3;
    }

    public String getTransactionType() {
        if (_transactionMode == 0)
            return "Client";
        else if (_transactionMode == 1)
            return "Server";
        else if (_transactionMode == 2)
            return "Serial";
        else
        	return "unknown";
    }

    public WmManagedConnection createManagedConnectionObject(
      javax.security.auth.Subject subject,
      javax.resource.spi.ConnectionRequestInfo connectionRequestInfo)
      throws AdapterException {
        return new WxSocketConnection(
          _socketServerHostName, 
          _socketServerPortNumber, 
          _timeout, 
          _transactionMode, 
          _terminationString,
          _welcomeString,
          _zeroLenBuffer,
          _zeroBufferNum
          );
    }

    
    public int queryTransactionSupportLevel() {
        if (_transactionMode == 0)
            return NO_TRANSACTION_SUPPORT;
        else
            return NO_TRANSACTION_SUPPORT;
    }
   
    public void fillWmDescriptor(WmDescriptor d, Locale l)
      throws AdapterException {

        d.createGroup(GROUP_SOCKET_CONNECTION,
          new String[] {SOCKET_SERVER_HOST_NAME, SOCKET_SERVER_PORT_NUMBER,
                        SOCKET_TRANSACTION_TYPE, SOCKET_CONNECTOR_TIMEOUT,
                        SOCKET_TERMINATION_STRING, SOCKET_WELCOME_STRING,
                        SOCKET_ZEROLEN_STRING, SOCKET_ZEROBUFNUM_STRING
                        });
 
        d.setMinStringLength(SOCKET_SERVER_HOST_NAME, 1);

        d.setValidValues(SOCKET_TRANSACTION_TYPE, new String[] {"Client", "Server", "Serial"});
        d.setValidValues(SOCKET_ZEROLEN_STRING, new String[] {"false","true"});
        
        d.setRequired(SOCKET_SERVER_PORT_NUMBER);
        d.setRequired(SOCKET_SERVER_HOST_NAME);
        d.setRequired(SOCKET_TRANSACTION_TYPE);
       
        d.setDescriptions(WxSocketAdapter.getInstance().getAdapterResourceBundleManager(), l);  
    }

    public void fillResourceAdapterMetadataInfo(
      ResourceAdapterMetadataInfo info, Locale locale) throws AdapterException {
        WxSocketAdapter.getInstance().fillResourceAdapterMetadataInfo(info, locale);
        info.addServiceTemplate(SocketWrite.class.getName());
    }
}