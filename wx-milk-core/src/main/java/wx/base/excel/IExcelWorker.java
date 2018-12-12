package wx.base.excel;

import java.io.IOException;
import java.io.OutputStream;

public interface IExcelWorker {

	public void write(Object vals);
	
	public Integer getRowIndex();
	
	public void flush(OutputStream stream) throws IOException;
	
	public void close() throws Exception;
}

