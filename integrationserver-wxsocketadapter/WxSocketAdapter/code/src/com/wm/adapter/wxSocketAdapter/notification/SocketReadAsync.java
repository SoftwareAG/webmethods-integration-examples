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
package com.wm.adapter.wxSocketAdapter.notification;

import com.wm.adapter.wxSocketAdapter.WxSocketAdapter;
import com.wm.adapter.wxSocketAdapter.WxSocketAdapterConstants;
import com.wm.adapter.wxSocketAdapter.connection.WxSocketConnection;
import com.wm.adk.cci.record.WmRecord;
import com.wm.adk.cci.record.WmRecordFactory;
import com.wm.adk.error.AdapterException;
import com.wm.adk.metadata.WmTemplateDescriptor;
import com.wm.adk.notification.WmPollingNotification;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import javax.resource.ResourceException;

public class SocketReadAsync extends WmPollingNotification
  implements WxSocketAdapterConstants, Runnable
{
    
    private String _pollingName;
    private String[] _inputParameterNames;
    private String[] _inputFieldValues;
    private String[] _inputFieldTypes;
    private String[] _outputParameterNames;
    private String[] _outputFieldNames;
    private String[] _outputFieldTypes;
    private IData mainIData = null;
    private IDataCursor mainCursor = null;
    private boolean threadRunning = false;
    private volatile Thread myThread = null;
    private WxSocketConnection wxcon = null;
    private int bytesAvailable = -2;
    private int bytesRead = -1;
    private int zeroBufCounter = -2;
    private boolean returnZeroBuf = false;
    private byte[] buf = null;
    private InputStream myInputStream = null;
    private boolean isInitialized = false;

    
    public SocketReadAsync() {
    	super();
    }
    
    public void setPollingName(String pollingName) {
        _pollingName = pollingName;
    }

    public String getPollingName() {
        return _pollingName;
    }

    public void setInputParameterNames(String[] inputParameterNames) {
        _inputParameterNames = inputParameterNames;
    }

    public String[] getInputParameterNames() {
        return _inputParameterNames;
    }

    public void setInputFieldValues(String[] inputFieldValues) {
        _inputFieldValues = inputFieldValues;
    }

    public String[] getInputFieldValues() {
        return _inputFieldValues;
    }

    public void setInputFieldTypes(String[] inputFieldTypes) {
        _inputFieldTypes = inputFieldTypes;
    }

    public String[] getInputFieldTypes() {
        return _inputFieldTypes;
    }

    public void setOutputParameterNames(String[] outputParameterNames) {
        _outputParameterNames = outputParameterNames;
    }

    public String[] getOutputParameterNames() {
        return _outputParameterNames;
    }

    public void setOutputFieldNames(String[] outputFieldNames) {
        _outputFieldNames = outputFieldNames;
    }

    public String[] getOutputFieldNames() {
        return _outputFieldNames;
    }

    public void setOutputFieldTypes(String[] outputFieldTypes) {
        _outputFieldTypes = outputFieldTypes;
    }

    public String[] getOutputFieldTypes() {
        return _outputFieldTypes;
    }

    public void fillWmTemplateDescriptor(WmTemplateDescriptor d, Locale l)
      throws AdapterException {

        d.createGroup(GROUP_MESSAGE_POLLING,
          new String[] {GROUP_MEMBER_POLLING_NAME,
                        GROUP_MEMBER_INPUT_PARAMETER_NAMES, 
                        GROUP_MEMBER_INPUT_FIELD_VALUES,
                        GROUP_MEMBER_INPUT_FIELD_TYPES,
                        GROUP_MEMBER_OUTPUT_PARAMETER_NAMES,
                        GROUP_MEMBER_OUTPUT_FIELD_NAMES,
                        GROUP_MEMBER_OUTPUT_FIELD_TYPES});

        d.setRequired(GROUP_MEMBER_POLLING_NAME);

        d.createFieldMap(new String[] {GROUP_MEMBER_INPUT_PARAMETER_NAMES, 
                                       GROUP_MEMBER_INPUT_FIELD_VALUES, 
                                       GROUP_MEMBER_INPUT_FIELD_TYPES}, false);

        d.createFieldMap(new String[] {GROUP_MEMBER_OUTPUT_PARAMETER_NAMES, 
                                       GROUP_MEMBER_OUTPUT_FIELD_NAMES,
                                       GROUP_MEMBER_OUTPUT_FIELD_TYPES}, false);
                                       
        d.createTuple(new String[] {GROUP_MEMBER_INPUT_PARAMETER_NAMES, 
                                    GROUP_MEMBER_INPUT_FIELD_TYPES});

        d.createTuple(new String[] {GROUP_MEMBER_OUTPUT_PARAMETER_NAMES, 
                                    GROUP_MEMBER_OUTPUT_FIELD_TYPES});
                                    
        d.setResourceDomain(GROUP_MEMBER_POLLING_NAME, 
                            DOMAIN_POLLING_NAMES, null);

        d.setResourceDomain(GROUP_MEMBER_INPUT_PARAMETER_NAMES,
                            DOMAIN_INPUT_PARAMETER_NAMES,
                            new String[] {GROUP_MEMBER_POLLING_NAME});

        d.setResourceDomain(GROUP_MEMBER_INPUT_FIELD_TYPES,
                            DOMAIN_INPUT_FIELD_TYPES,
                            new String[] {GROUP_MEMBER_POLLING_NAME});

        d.setResourceDomain(GROUP_MEMBER_OUTPUT_PARAMETER_NAMES,
                            DOMAIN_OUTPUT_PARAMETER_NAMES,
                            new String[] {GROUP_MEMBER_POLLING_NAME});

        d.setResourceDomain(GROUP_MEMBER_OUTPUT_FIELD_TYPES,
                            DOMAIN_OUTPUT_FIELD_TYPES,
                            new String[] {GROUP_MEMBER_POLLING_NAME});

        d.setResourceDomain(GROUP_MEMBER_OUTPUT_FIELD_NAMES, 
                            WmTemplateDescriptor.OUTPUT_FIELD_NAMES,
                            new String[] {GROUP_MEMBER_OUTPUT_PARAMETER_NAMES, 
                                          GROUP_MEMBER_OUTPUT_FIELD_TYPES});
                                          
        d.setDescriptions(
          WxSocketAdapter.getInstance().getAdapterResourceBundleManager(), l);
    }
    
    private void startMyThread() {
    	if (myThread == null) {
    		WxSocketAdapter.retrieveLogger().logDebug(9999,"starting thread ...");
    		threadRunning=true;
    		myThread = new Thread(this);
    		myThread.start();
    	}
    }
    
    private void stopMyThread() {
    	threadRunning = false;
    	Thread tmp = myThread;
    	tmp.interrupt();
    	myThread = null;
    }
    
    private WmRecord createOutput(byte[] buffer, int bytesRead) {
    	WmRecord result = WmRecordFactory.getFactory().createWmRecord("noNameUsed");
    	mainIData = IDataFactory.create();
        mainCursor = mainIData.getCursor();
        if (bytesRead > 0) {
        	IDataUtil.put(mainCursor, this.getOutputFieldNames()[0], buffer);
	        IDataUtil.put(mainCursor, this.getOutputFieldNames()[1], ""+bytesRead);
        } else {
        	IDataUtil.put(mainCursor, this.getOutputFieldNames()[0], new byte[0]);
	        IDataUtil.put(mainCursor, this.getOutputFieldNames()[1], "0");
        }
        mainCursor.destroy();
        result.setIData(mainIData);
    	return result;
    }
    
    private void initNotifier() {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"************ initNotifier ******************* ");
    	if (!isInitialized) {
	    	wxcon = (WxSocketConnection) this.retrieveConnection();
	    	if (wxcon != null) {
	    		this.returnZeroBuf = wxcon.getZeroLenBuffer();
	    		this.zeroBufCounter = wxcon.getZeroBufNum();
	    		WxSocketAdapter.retrieveLogger().logDebug(9999,"************ initNotifier: ret zero len: "+this.returnZeroBuf+" | zero len buf count: "+this.zeroBufCounter); 
	    	} else {
	    		WxSocketAdapter.retrieveLogger().logDebug(9999,"!!! initNotifier: retrieve connection is null            !!!");
	    		WxSocketAdapter.retrieveLogger().logDebug(9999,"!!! initNotifier: this must not happen -> terminating    !!!");
	    		WxSocketAdapter.retrieveLogger().logDebug(9999,"!!! Make sure you enabled the connection and polling ntf !!!!");
	    		WxSocketAdapter.retrieveLogger().logDebug(9999,"!!! Also reload the package with the notifier            !!!!");
	    	}
	    	isInitialized = true;
    	}
    }
    
    private boolean isTerminationString(byte[] tmp) {
    	//print("term string: >"+new String(tmp)+"<");
    	if (tmp == null || tmp == null || tmp.length == 0 || wxcon.getTerminationString() == null || wxcon.getTerminationString().length() == 0) {
    		return false;
    	}
    	if ((new String(tmp)).startsWith(wxcon.getTerminationString())) {
    		return true;
		} else {
    		return false;
    	}
    }
    
    public void deleteCallBack() throws ResourceException {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"************ deleteCallBack");
    	this.stopMyThread();
    }
    
    public void disableCallBack() throws ResourceException {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"************ disableCallBack");
    	if ( this.myThread != null ) {
    		this.stopMyThread();
    	}
    }
    
    public void suspendCallBack() throws ResourceException {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"************ suspendCallBack");
    	this.stopMyThread();
    }
    
    public void initCallBack() throws ResourceException {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"************ initCallBack :"+this.enabled());
    	this.initNotifier();
    	if (this.enabled()) {
    		this.startMyThread();
    	}
    }
    
    public void startupCallBack() throws ResourceException {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"************ startupCallBack");
    	this.initNotifier();
    	this.startMyThread();
    }

    public void shutdownCallBack() {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"************ shutdownCallBack");
    	this.stopMyThread();
    }
    
    public void resumeCallBack() throws ResourceException {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"************ resumeCallBack");
    	this.startMyThread();
    }
    
    public void enableCallBack() throws ResourceException {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"************ resumeCallBack");
    	this.initNotifier();
    	this.startMyThread();
    }
    
    public void runNotification() {
    	//print(" runNotification ... ");
    	if (wxcon == null) {
    		isInitialized = false;
    		this.initNotifier();
    	}
    }
    
    private void print(String str) {
    	WxSocketAdapter.retrieveLogger().logDebug(9999,"SocketRead: "+str);
    }
    
    
    private int checkInputStream() {
    	bytesAvailable = -4;	// -2: no InputStream, -3: socket closed, -4: input stream null
    	if (myInputStream == null) {
    		if (wxcon.getIn() != null) {
    			myInputStream = wxcon.getIn();
    		} else {
    			return bytesAvailable;
    		}
    	}
    	try { bytesAvailable = myInputStream.available(); } catch (Exception e) {
    		if (e instanceof NullPointerException) {
    			// noop
    		}
    		if (e instanceof IOException ) {
    			bytesAvailable = -3;
    		}
    	}
    	return bytesAvailable;
    }
    
    
    private void reconnectServerConnection() throws IOException {
    	myInputStream.close();
		wxcon.getOut().close();
		wxcon.setClientSocket(wxcon.getServerSocket().accept());
		print("----------------- new client connection received ---------------------------------- ");
		wxcon.setIn(wxcon.getClientSocket().getInputStream());
		wxcon.setOut(wxcon.getClientSocket().getOutputStream());
		//wxcon.getOut().flush();
		myInputStream = wxcon.getIn();
		buf = null;
		bytesRead = -4;
		bytesAvailable = -4;
		returnZeroBuf = wxcon.getZeroLenBuffer();
		zeroBufCounter = wxcon.getZeroBufNum();
    }
    
	public void run() {
		print("thread started");
		while ( this.threadRunning ) {
			bytesAvailable = this.checkInputStream();
			switch (bytesAvailable) {
				case -4:
					// ignore me branch ...
					break;
				case -3:
					print("socket closed");
					//this.threadRunning = false;
					//Thread.currentThread().interrupt();
					break;
				case -2:
					print("null pointer exception happened -> getIn() is still null");
					break;
				case -1: 
					print("eos");
					break;
				case 0:
					if (this.returnZeroBuf && this.zeroBufCounter > 0 && myInputStream != null) {
						//print("return empty buffer ("+this.zeroBufCounter+")");
						this.zeroBufCounter--;
						try { this.doNotify(this.createOutput(null, 0)); } catch (Exception e ) {}
					}
					break;
				default:
					buf = new byte[bytesAvailable];
					try { 
						bytesRead = myInputStream.read(buf);
						if (bytesRead > 0 && this.isTerminationString(buf)) {
							print(" **** termination string recognized ****"+bytesRead);
							try { this.doNotify(this.createOutput(buf, bytesRead)); } catch (Exception e ) {}
							this.reconnectServerConnection();
						}
					} catch (Exception e2) { 
						print(" buffer read exception "+e2.getMessage()); 
					}
					//print("call notifer with buffer size: "+bytesRead);
					try {
						if (bytesRead > 0) { // reconnectServerConnection is changing bytesRead -> so testing before call
							this.doNotify(this.createOutput(buf, bytesRead));
						}
					} catch (Exception e3 ) {print("********** doNotify exception "+e3.getMessage());}
					break;
			}
			try { Thread.sleep(100); } catch (Exception e4) {
				//print(" sleep 100 exception: "+e4.getMessage());
				/*
				if (e4 instanceof InterruptedException) {
					//print(" prepare for thread to stop ...");
					//print(" **** trans mode == "+wxcon.getLocalTransactionMode());
					this.threadRunning = false;
					this.stopMyThread();
				}
				*/
			}
		}
		print("thread terminated");
		myThread = null;
		isInitialized = false;
		threadRunning = false;
	}
}