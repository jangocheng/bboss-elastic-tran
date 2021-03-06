package org.frameworkset.tran.db.output;

import org.frameworkset.tran.ESDataImportException;
import org.frameworkset.tran.TranResultSet;
import org.frameworkset.tran.context.ImportContext;
import org.frameworkset.tran.schedule.TaskContext;

import java.util.concurrent.CountDownLatch;

public class AsynDBOutPutDataTran extends DBOutPutDataTran {
	private CountDownLatch countDownLatch;


	public AsynDBOutPutDataTran(TaskContext taskContext, TranResultSet jdbcResultSet, ImportContext importContext, ImportContext targetImportContext) {
		super( taskContext,jdbcResultSet,importContext,   targetImportContext);
	}
	public AsynDBOutPutDataTran(TaskContext taskContext,TranResultSet jdbcResultSet, ImportContext importContext, ImportContext targetImportContext, CountDownLatch countDownLatch) {
		super( taskContext,jdbcResultSet,importContext,   targetImportContext);
		this.countDownLatch = countDownLatch;
	}
//	public void appendData(ESDatas datas){
//		esTranResultSet.appendData(new ESDatasWraper(datas));
//	}



	public void stop(){
		esTranResultSet.stop();
		super.stop();
	}

	@Override
	public String tran() throws ESDataImportException {
		try {
			return super.tran();
		}
		finally {
			if(this.countDownLatch != null)
				countDownLatch.countDown();
		}
	}
}
