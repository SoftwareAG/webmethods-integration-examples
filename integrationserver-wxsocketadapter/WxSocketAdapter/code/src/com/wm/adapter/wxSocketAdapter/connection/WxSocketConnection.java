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
import com.wm.adk.connection.WmManagedConnection;
import com.wm.adk.error.AdapterConnectionException;
import com.wm.adk.error.AdapterException;
import com.wm.adk.metadata.WmAdapterAccess;
import com.wm.adk.metadata.ResourceDomainValues;
import com.wm.adk.notification.WmPollingNotification;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.resource.spi.ConnectionRequestInfo;
import javax.security.auth.Subject;
import javax.resource.ResourceException;
import javax.resource.spi.LocalTransaction;

import com.fazecast.jSerialComm.*;

public class WxSocketConnection extends WmManagedConnection
  implements WxSocketAdapterConstants, Runnable
{
    
    private Socket cs = null;
    public Socket getClientSocket() {
		return cs;
	}
    public void setClientSocket(Socket _cs) {
		cs = _cs;
	}
	private ServerSocket ss = null;
	public ServerSocket getServerSocket() {
		return ss;
	}
	public void setServcerSocket(ServerSocket _ss) {
		ss = _ss;
	}
	private InputStream in = null;
    public InputStream getIn() {
		return in;
	}
    public void setIn(InputStream _in) {
		in = _in;
	}
    private OutputStream out = null;
	public OutputStream getOut() {
		return out;
	}
	public void setOut(OutputStream _out) {
		out = _out;
	}
	
	private String _serverHost = null;
    private String _serverPort = "4711";
    private int _timeout = 20000;
    private int _localTransactionMode = 0; /* client == 0, server == 1 */
    private String _welcomeString = "";
    private boolean _zeroLenBuffer = false;
    private int _zeroBufNum = 1;
	private boolean threadRunning = false;
    private volatile Thread myThread = null;
    private boolean needStopFlag = false;
    private LocalTransaction _localTransaction = null;
    private String _terminationString = null;
    private WmPollingNotification pn = null;
    private boolean isConnected = false;
	private Subject subject;
	private ConnectionRequestInfo requestInfo;
    
    public boolean getConnected() {
    	return isConnected;
    }
    
    public int getZeroBufNum() {
		return _zeroBufNum;
	}

	public void setZeroBufNum(int _zeroBufNum) {
		this._zeroBufNum = _zeroBufNum;
	}
    
    public boolean getZeroLenBuffer() {
		return _zeroLenBuffer;
	}

	public void setZeroLenBuffer(boolean _zeroLenBuffer) {
		this._zeroLenBuffer = _zeroLenBuffer;
	}

	public String getWelcomeString() {
		return _welcomeString;
	}

	public void setWelcomeString(String welcomeString) {
		this._welcomeString = welcomeString;
	}
    
    public WmPollingNotification getPn() {
		return pn;
	}

	public void setPn(WmPollingNotification pn) {
		this.pn = pn;
	}

	public String getTerminationString() {
		return _terminationString;
	}

	public void setTerminationString(String string) {
		_terminationString = string;
	}
	
	public int getLocalTransactionMode() {
		return _localTransactionMode;
	}

	public void setLocalTransactionMode(int _localTransactionMode) {
		this._localTransactionMode = _localTransactionMode;
	}
    
    public boolean getNeedStopFlag() {
		return needStopFlag;
	}

	public void setNeedStopFlag(boolean needStopFlag) {
		this.needStopFlag = needStopFlag;
	}

    public WxSocketConnection(
    		String serverHost, 
    		String serverPort, 
    		int timeout,
    		int localTransactionMode,
    		String terminationString,
    		String welcomeString,
    		boolean zeroLenBuffer,
    		int zeroBufNum) 
    {
    	WxSocketAdapter.retrieveLogger().logInfo(
    			9999,
    			"WxSocketConnection called with "+serverHost+" "+serverPort+" "+timeout+" "+localTransactionMode+" zlb:"+zeroLenBuffer
    	);
        _serverHost = serverHost;
        _serverPort = serverPort;
        _timeout = timeout;
        _localTransactionMode = localTransactionMode;
        _terminationString = terminationString;
        _welcomeString = welcomeString;
        _zeroLenBuffer = zeroLenBuffer;
        _zeroBufNum = zeroBufNum;
        /*
        csKey = _serverHost+"_"+_serverPort+"_CS";
        ssKey = _serverHost+"_"+_serverPort+"_SS";
        */
    }

    public void destroyConnection() throws ResourceException {
    		WxSocketAdapter.retrieveLogger().logInfo(9999, "*** destroy connection called ***");
    		isConnected = false;
        	try {in.close();} catch (Exception xxx) {};
        	try {out.close();} catch (Exception xxx) {};
        	try {cs.close();} catch (Exception xxx) {};
        	// don't close server socket connection with this function ...
        	// nothing to do for CommPortIdentifier ...
        	cs = null;
        	/*
        	try { connectionPool.remove(csKey); } catch (Throwable t) {};
        	try { connectionPool.remove(ssKey); } catch (Throwable t) {};
        	*/
    }
    
    public void destroy() {
    	WxSocketAdapter.retrieveLogger().logInfo(9999, "*** destroy ***");
    	try { 
    		this.destroyConnection();
    		if (this._localTransactionMode == 1) { // server socket ...
    			try { ss.close();} catch (Exception xxx) {};
    		}
    		this.stopMyThread();
    	} catch (Exception e) {}
    }
    
    private void writeWelcomeMessage() {
    	if (!_welcomeString.equals("")) {
			try {
				out.write(_welcomeString.getBytes());
			} catch (Throwable t) {
				WxSocketAdapter.retrieveLogger().logDebug(9999,"cannot write welcome string >"+_welcomeString+"<");
			}
		}
    }
    
    public void restartServerSocket() throws IOException {
    	setNeedStopFlag(true);
    	try { getIn().close(); } catch (Exception e) {}
    	try { getOut().flush(); } catch (Exception e) {}
    	try { getOut().close(); } catch (Exception e) {}
    	setClientSocket(this.ss.accept());
    	WxSocketAdapter.retrieveLogger().logInfo(9999, "************ new client connection received **************");
    	isConnected = true;
    	setIn(this.getClientSocket().getInputStream());
    	setOut(this.getClientSocket().getOutputStream());
    	writeWelcomeMessage();
    }
    
    protected boolean initializationRequired() {
        return true;
    }
    
    private void initClientConn() throws UnknownHostException, IOException {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"Creating client socket to host "+this._serverHost+" on port "+this._serverPort+" with to = "+this._timeout);
		cs = new Socket(this._serverHost, Integer.parseInt(this._serverPort));
		if (this._timeout != -1) {
			cs.setSoTimeout(this._timeout);
		}
		in = cs.getInputStream();
		out = cs.getOutputStream();
		//connectionPool.put(csKey, cs);
		isConnected = true;
		WxSocketAdapter.retrieveLogger().logDebug(9999,"...connected!");
    }
    
    private void initServerConn() throws IOException {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"Creating server socket on port "+this._serverPort+" with to = "+this._timeout);
		ss = new ServerSocket(Integer.parseInt(this._serverPort));
		if (this._timeout != -1) {
			ss.setSoTimeout(this._timeout);
		}
		//connectionPool.put(ssKey, ss);
		WxSocketAdapter.retrieveLogger().logDebug(9999,"...waiting for connections ...");
		this.threadRunning = true;
		this.myThread = new Thread(this);
		this.myThread.start();
    }
    
    private void initCommPort() {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"Creating comm port    -----------> tbd ");
    	WxSocketAdapter.retrieveLogger().logDebug(9999," initCommPort: connecting to :"+this._serverHost);
    	SerialPort sp = SerialPort.getCommPort(_serverHost);
    	StringTokenizer st = new StringTokenizer(_serverPort,"|");
    	int baud = Integer.parseInt(st.nextToken());
    	int databits = Integer.parseInt(st.nextToken());
    	int stopbits = Integer.parseInt(st.nextToken());
    	int parity = Integer.parseInt(st.nextToken());
    	sp.setComPortParameters(baud, databits, stopbits, parity);
    	WxSocketAdapter.retrieveLogger().logDebug(9999," initCommPort: sp = "+sp);
    	boolean isOpen = sp.openPort();
    	if (isOpen) {
    		WxSocketAdapter.retrieveLogger().logDebug(9999," initCommPort: serial port is ready now! ("+
    				"baud:"+baud+
    			    "|databits:"+databits+
    				"|stopbits:|"+stopbits+
    				"|parity:|"+parity
    		);
    		in = sp.getInputStream();
    		out = sp.getOutputStream();
    	}
    }

    protected void initializeConnection(Subject subject,
      ConnectionRequestInfo requestInfo) throws ResourceException {
        WxSocketAdapter.retrieveLogger().logDebug(101);
        this.subject = (this.subject == null) ? subject : this.subject;
        this.requestInfo = (this.requestInfo == null) ? requestInfo : this.requestInfo;
        
        try {
        	if (_localTransactionMode == 0) {
        		this.initClientConn();
        	}
        	if (_localTransactionMode == 1) {
        		this.initServerConn();
        	}
        	if (_localTransactionMode == 2) {
        		this.initCommPort();
        	}
        } catch (IOException e) {
            AdapterConnectionException ace = WxSocketAdapter.getInstance().createAdapterConnectionException(1001, null, e);
            ace.setFatal(true);
            throw ace;
        }
    }

    public void registerResourceDomain(WmAdapterAccess access)
      throws AdapterException {
        try 
        {
            // register all the resource domain look up here
            access.addResourceDomainLookup(null, DOMAIN_FUNCTION_NAMES, this);
            access.addResourceDomainLookup(null, DOMAIN_INPUT_PARAMETER_NAMES, this);
            access.addResourceDomainLookup(null, DOMAIN_INPUT_FIELD_TYPES, this);
            access.addResourceDomainLookup(null, DOMAIN_INPUT_FIELD_NAMES, this);
            access.addResourceDomainLookup(null, DOMAIN_OUTPUT_PARAMETER_NAMES, this);
            access.addResourceDomainLookup(null, DOMAIN_OUTPUT_FIELD_TYPES, this);
            access.addResourceDomainLookup(null, DOMAIN_OUTPUT_FIELD_NAMES, this);
            access.addResourceDomainLookup(null, DOMAIN_POLLING_NAMES, this);
        } catch (Exception ex) {
            throw WxSocketAdapter.getInstance().createAdapterException(1002, ex);
        }
    }

    public ResourceDomainValues[] adapterResourceDomainLookup(
      String serviceName, String resourceDomainName, String[][] values)
      throws AdapterException {

    	String[] fieldNames = null;
        String[] fieldTypes = null;
        
 
        //WxSocketAdapter.getInstance().retrieveLogger().logDebug(9999, "### resourceDomain: "+resourceDomainName);
        if (resourceDomainName.equals(DOMAIN_FUNCTION_NAMES)) {
           return new ResourceDomainValues[] {
                   new ResourceDomainValues(resourceDomainName, new String[]{"Socket Write"})};
        }
        // added at Phase 4 to support message polling notification
        else if (resourceDomainName.equals(DOMAIN_POLLING_NAMES)) {
        	return new ResourceDomainValues[] {
                    new ResourceDomainValues(resourceDomainName, new String[] {"Socket Read"})};
        }
        // because input parameters and input field types have been declared as
        // tuple, they are looked up together
        else if (resourceDomainName.equals(GROUP_MEMBER_INPUT_PARAMETER_NAMES) ||
                 resourceDomainName.equals(GROUP_MEMBER_INPUT_FIELD_TYPES)) {
            
            if (serviceName.equals(SERVICE_TEMPLATE)) {
            	fieldNames = new String[] {"writeBuffer"};
            	fieldTypes = new String[] {"java.lang.Object"};
            } else {
            	fieldNames = new String[] {};
            	fieldTypes = new String[] {};
            }
            return new ResourceDomainValues[] {
                    new ResourceDomainValues(GROUP_MEMBER_INPUT_PARAMETER_NAMES, fieldNames),
                    new ResourceDomainValues(GROUP_MEMBER_INPUT_FIELD_TYPES, fieldTypes)};
        }
        else if (resourceDomainName.equals(GROUP_MEMBER_OUTPUT_PARAMETER_NAMES) ||
                 resourceDomainName.equals(GROUP_MEMBER_OUTPUT_FIELD_TYPES)) {
            
            if (serviceName.equals(SERVICE_TEMPLATE)) {
            	fieldNames = new String[] {"success"};
            	fieldTypes = new String[] {"java.lang.String"};
            } else {
            	fieldNames = new String[] {"readBuffer","bytesRead"};
            	fieldTypes = new String[] {"java.lang.Object","java.lang.String"};
            }
            return new ResourceDomainValues[] {
            		new ResourceDomainValues(GROUP_MEMBER_OUTPUT_FIELD_TYPES, fieldTypes),
            		new ResourceDomainValues(GROUP_MEMBER_OUTPUT_FIELD_NAMES, fieldNames),
            		new ResourceDomainValues(GROUP_MEMBER_OUTPUT_PARAMETER_NAMES, fieldNames)};
            }
            else
                return null;
    }
    
    public Boolean adapterCheckValue(String serviceName,
      String resourceDomainName, String[][] values, String testValue)
      throws AdapterException {
        return null;
    }  

    public LocalTransaction getLocalTransaction() {
        return _localTransaction;
    }
    
    public void stopMyThread() {
    	Thread tmp = this.myThread;
    	this.myThread = null;
    	tmp.interrupt();
    }
    
    public void cleanup() {
    	//WxSocketAdapter.retrieveLogger().logDebug(9999,"WxSocketConnection: cleanup called ...");
    }

	public void run() {
		try {
			restartServerSocket();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			WxSocketAdapter.retrieveLogger().logDebug(9999,"WxSocketConnection: restart Exception: "+e1.getMessage());
		}
		while (this.threadRunning) {
			try { 
				Thread.sleep(10000); } catch (Exception e) {
				WxSocketAdapter.retrieveLogger().logDebug(9999,"WxSocketConnection: sleep 10000 exception: "+e.getMessage());
				myThread.interrupt();
			}
		}
		WxSocketAdapter.retrieveLogger().logDebug(9999,"WxSocketConnection: ### current thread terminated ... ");
	}
}
