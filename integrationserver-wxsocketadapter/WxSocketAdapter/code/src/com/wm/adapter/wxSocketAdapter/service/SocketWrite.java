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
package com.wm.adapter.wxSocketAdapter.service;

import java.util.Locale;
import javax.resource.ResourceException;

import com.wm.adapter.wxSocketAdapter.WxSocketAdapter;
import com.wm.adapter.wxSocketAdapter.WxSocketAdapterConstants;
import com.wm.adapter.wxSocketAdapter.connection.WxSocketConnection;
import com.wm.adk.cci.interaction.WmAdapterService;
import com.wm.adk.cci.record.WmRecord;
import com.wm.adk.cci.record.WmRecordFactory;
import com.wm.adk.connection.WmManagedConnection;
import com.wm.adk.error.AdapterException;
import com.wm.adk.metadata.WmTemplateDescriptor;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;


public class SocketWrite extends WmAdapterService implements WxSocketAdapterConstants
{
	public static final long serialVersionUID = 0x004917615921435L;

    private String _functionName;


    private String[] _inputParameterNames;


    private String[] _inputFieldNames;


    private String[] _inputFieldTypes;


    private String[] _outputParameterNames;


    private String[] _outputFieldNames;


    private String[] _outputFieldTypes;

    public SocketWrite() {
    }

    public void setFunctionName(String functionName) {
        _functionName = functionName;
    }

    public String getFunctionName() {
        return _functionName;
    }

    public void setInputParameterNames(String[] inputParameterNames) {
        _inputParameterNames = inputParameterNames;
    }

    public String[] getInputParameterNames() {
        return _inputParameterNames;
    }

    public void setInputFieldNames(String[] inputFieldNames) {
        _inputFieldNames = inputFieldNames;
    }

    public String[] getInputFieldNames() {
        return _inputFieldNames;
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
    
    public WmRecord execute(WmManagedConnection connection, WmRecord input)
      throws ResourceException {

        WmRecord output = WmRecordFactory.getFactory().createWmRecord("nameNotUsed");
        byte[] writeBuffer = null;
        boolean success = false;

        IData mainIData = input.getIData();
        IDataCursor mainCursor = mainIData.getCursor();
        
        try {
        	if (mainCursor.first("writeBuffer")) {
        		writeBuffer = (byte[]) mainCursor.getValue();
        	}
        } catch (Throwable t) {};
        
        mainCursor.destroy();
        mainIData = null;
        
        try {
        	((WxSocketConnection)connection).getOut().write(writeBuffer);
        	((WxSocketConnection)connection).getOut().flush();
        	success = true;
        	WxSocketAdapter.retrieveLogger().logDebug(9999,"<"+new String(writeBuffer)+"> written");
        } catch (Throwable t) {
        	success = false;
        	((WxSocketConnection)connection).destroyConnection();
        }
        
        mainIData = IDataFactory.create();
        mainCursor = mainIData.getCursor();
        IDataUtil.put(mainCursor, this.getOutputFieldNames()[0], ""+success);
        output.setIData(mainIData);
        mainCursor.destroy();
        return output;
    }

    public void fillWmTemplateDescriptor(WmTemplateDescriptor d, Locale l)
      throws AdapterException {

    
        d.createGroup(GROUP_FUNCTION_INVOCATION,
          new String[] {GROUP_MEMBER_FUNCTION_NAME,
                        GROUP_MEMBER_INPUT_PARAMETER_NAMES,
                        GROUP_MEMBER_INPUT_FIELD_NAMES,
                        GROUP_MEMBER_INPUT_FIELD_TYPES,
                        GROUP_MEMBER_OUTPUT_PARAMETER_NAMES,
                        GROUP_MEMBER_OUTPUT_FIELD_NAMES,
                        GROUP_MEMBER_OUTPUT_FIELD_TYPES});

    
        d.setRequired(GROUP_MEMBER_FUNCTION_NAME);

    
        d.createFieldMap(new String[] {GROUP_MEMBER_INPUT_PARAMETER_NAMES,
                                       GROUP_MEMBER_INPUT_FIELD_NAMES,
                                       GROUP_MEMBER_INPUT_FIELD_TYPES}, false);

    
        d.createFieldMap(new String[] {GROUP_MEMBER_OUTPUT_PARAMETER_NAMES,
                                       GROUP_MEMBER_OUTPUT_FIELD_NAMES,
                                       GROUP_MEMBER_OUTPUT_FIELD_TYPES}, false);

    
        d.createTuple(new String[] {GROUP_MEMBER_INPUT_PARAMETER_NAMES,
                                    GROUP_MEMBER_INPUT_FIELD_TYPES});

    
        d.createTuple(new String[] {GROUP_MEMBER_OUTPUT_PARAMETER_NAMES,
                                    GROUP_MEMBER_OUTPUT_FIELD_TYPES});

    
        d.setResourceDomain(GROUP_MEMBER_FUNCTION_NAME,
                            DOMAIN_FUNCTION_NAMES, null);

        d.setResourceDomain(GROUP_MEMBER_INPUT_PARAMETER_NAMES,
                            DOMAIN_INPUT_PARAMETER_NAMES,
                            new String[] {GROUP_MEMBER_FUNCTION_NAME});

        d.setResourceDomain(GROUP_MEMBER_INPUT_FIELD_TYPES,
                            DOMAIN_INPUT_FIELD_TYPES,
                            new String[] {GROUP_MEMBER_FUNCTION_NAME});

        d.setResourceDomain(GROUP_MEMBER_INPUT_FIELD_NAMES,
                            WmTemplateDescriptor.INPUT_FIELD_NAMES,
                            new String[] {GROUP_MEMBER_INPUT_PARAMETER_NAMES,
                                          GROUP_MEMBER_INPUT_FIELD_TYPES});

        d.setResourceDomain(GROUP_MEMBER_OUTPUT_PARAMETER_NAMES,
                            DOMAIN_OUTPUT_PARAMETER_NAMES,
                            new String[] {GROUP_MEMBER_FUNCTION_NAME});

        d.setResourceDomain(GROUP_MEMBER_OUTPUT_FIELD_TYPES,
                            DOMAIN_OUTPUT_FIELD_TYPES,
                            new String[] {GROUP_MEMBER_FUNCTION_NAME});

        d.setResourceDomain(GROUP_MEMBER_OUTPUT_FIELD_NAMES,
                            WmTemplateDescriptor.OUTPUT_FIELD_NAMES,
                            new String[] {GROUP_MEMBER_OUTPUT_PARAMETER_NAMES,
                                          GROUP_MEMBER_OUTPUT_FIELD_TYPES});
        d.setDescriptions(
          WxSocketAdapter.getInstance().getAdapterResourceBundleManager(), l);
    }
}