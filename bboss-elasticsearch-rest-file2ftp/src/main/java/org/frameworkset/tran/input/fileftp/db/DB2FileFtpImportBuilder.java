package org.frameworkset.tran.input.fileftp.db;/*
 *  Copyright 2008 biaoping.yin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.frameworkset.tran.*;
import org.frameworkset.tran.config.BaseImportConfig;
import org.frameworkset.tran.context.ImportContext;
import org.frameworkset.tran.db.DBExportBuilder;
import org.frameworkset.tran.db.DBImportConfig;
import org.frameworkset.tran.db.DBImportContext;
import org.frameworkset.tran.output.fileftp.FileFtpOupputConfig;
import org.frameworkset.tran.output.fileftp.FileFtpOupputContextImpl;

public class DB2FileFtpImportBuilder extends DBExportBuilder {

	@JsonIgnore
	private FileFtpOupputConfig fileFtpOupputConfig;
	public DB2FileFtpImportBuilder(){

	}
	public void setFileFtpOupputConfig(FileFtpOupputConfig fileFtpOupputConfig) {
		this.fileFtpOupputConfig = fileFtpOupputConfig;
	}
	@Override
	public DataTranPlugin buildDataTranPlugin(ImportContext importContext,ImportContext targetImportContext){
		return new DB2FileFtpDataTranPlugin(  importContext,  targetImportContext);
	}


	protected ImportContext buildImportContext(BaseImportConfig importConfig){
		return new DBImportContext(importConfig);
	}


	protected ImportContext buildTargetImportContext(BaseImportConfig importConfig){
		return new FileFtpOupputContextImpl(fileFtpOupputConfig);
	}




	public DataStream builder(){
		super.builderConfig();
		try {
			if(logger.isInfoEnabled()) {
				logger.info("DB2FileFtp Import Configs:");
				logger.info(this.toString());
			}
		}
		catch (Exception e){

		}
		DBImportConfig importConfig = new DBImportConfig();

		super.buildImportConfig(importConfig);

		super.buildDBImportConfig(importConfig);
		DataStream  dataStream = this.createDataStream();
		dataStream.setImportConfig(importConfig);
		dataStream.setConfigString(this.toString());
		dataStream.setImportContext(this.buildImportContext(importConfig));
		dataStream.setTargetImportContext(buildTargetImportContext(fileFtpOupputConfig));
		dataStream.setDataTranPlugin(this.buildDataTranPlugin(dataStream.getImportContext(),dataStream.getTargetImportContext()));
		return dataStream;
	}





	@Override
	protected WrapedExportResultHandler buildExportResultHandler(ExportResultHandler exportResultHandler) {
		return new DefualtExportResultHandler<String,String>(exportResultHandler);
	}



}
